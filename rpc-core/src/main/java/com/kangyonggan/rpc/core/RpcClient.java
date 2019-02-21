package com.kangyonggan.rpc.core;

import com.kangyonggan.rpc.constants.RpcPojo;
import com.kangyonggan.rpc.handler.RpcClientHandler;
import com.kangyonggan.rpc.handler.RpcReadTimeoutHandler;
import com.kangyonggan.rpc.pojo.Client;
import com.kangyonggan.rpc.pojo.Refrence;
import com.kangyonggan.rpc.pojo.Service;
import com.kangyonggan.rpc.util.CacheUtil;
import com.kangyonggan.rpc.util.InterceptorUtil;
import com.kangyonggan.rpc.util.LoadBalance;
import com.kangyonggan.rpc.util.SpringUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * 客户端
 *
 * @author kangyonggan
 * @since 2019-02-15
 */
public class RpcClient {

    private Logger logger = Logger.getLogger(RpcClient.class);

    private Refrence refrence;

    private ChannelFuture channelFuture;

    private RpcClientHandler handler;

    public RpcClient(Refrence refrence) {
        this.refrence = refrence;
    }

    /**
     * 连接远程服务
     *
     * @return
     */
    public Service connectRemoteService() {
        logger.info("正在连接远程服务端:" + refrence);
        // 客户端线程
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);

        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                // 解码
                ch.pipeline().addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));

                // 编码
                ch.pipeline().addLast(new ObjectEncoder());

                // 收发消息
                handler = new RpcClientHandler();

                // 超时
                ch.pipeline().addLast(new RpcReadTimeoutHandler(handler, refrence.getTimeout(), TimeUnit.MILLISECONDS));

                ch.pipeline().addLast(handler);
            }
        });

        try {
            // 如果是点对点服务，不走负载均衡
            if (!StringUtils.isEmpty(refrence.getDirectServerIp())) {
                channelFuture = bootstrap.connect(refrence.getDirectServerIp(), refrence.getDirectServerPort()).sync();
                logger.info("点对点服务连接成功");
            } else {
                // 获取负载均衡策略
                Client client = (Client) SpringUtils.getApplicationContext().getBean(RpcPojo.client.name());
                logger.info("客户端负载均衡策略:" + client.getLoadBalance());

                Service service = LoadBalance.getService(refrence, client.getLoadBalance());
                channelFuture = bootstrap.connect(service.getIp(), service.getPort()).sync();
                logger.info("连接远程服务端成功:" + service);
                return service;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    /**
     * 远程调用
     *
     * @param request
     * @return
     * @throws Throwable
     */
    public RpcResponse remoteCall(RpcRequest request) throws Throwable {
        RpcInterceptor interceptor = null;
        if (!StringUtils.isEmpty(refrence.getInterceptor())) {
            interceptor = InterceptorUtil.get(refrence.getInterceptor());

            // 拦截器
            interceptor.preHandle(refrence, request);
        }

        // 判断是否使用缓存
        if (refrence.isUseCache()) {
            CacheUtil.CacheObject cacheObject = CacheUtil.getCache(request);
            if (cacheObject != null) {
                logger.info("结果走缓存：" + cacheObject.getRpcResponse());

                // 拦截器
                if (!StringUtils.isEmpty(refrence.getInterceptor())) {
                    interceptor.postHandle(refrence, request);
                }

                return cacheObject.getRpcResponse();
            }
        }

        // 发送请求
        channelFuture.channel().writeAndFlush(request).sync();
        channelFuture.channel().closeFuture().sync();

        // 接收响应
        RpcResponse response = handler.getResponse();
        logger.info("服务端响应：" + response);

        if (response.getIsSuccess()) {
            // 判断是否缓存结果
            if (refrence.isUseCache()) {
                CacheUtil.setCache(request, response, refrence.getCacheTime());
                logger.info("结果已缓存");
            }

            // 拦截器
            if (!StringUtils.isEmpty(refrence.getInterceptor())) {
                interceptor.postHandle(refrence, request);
            }

            return response;
        }

        // 拦截器
        if (!StringUtils.isEmpty(refrence.getInterceptor())) {
            interceptor.postHandle(refrence, request);
        }

        throw response.getThrowable();
    }
}
