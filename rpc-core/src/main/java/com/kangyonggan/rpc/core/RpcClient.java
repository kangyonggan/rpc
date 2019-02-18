package com.kangyonggan.rpc.core;

import com.kangyonggan.rpc.constants.RpcPojo;
import com.kangyonggan.rpc.handler.RpcClientHandler;
import com.kangyonggan.rpc.handler.RpcReadTimeoutHandler;
import com.kangyonggan.rpc.pojo.Application;
import com.kangyonggan.rpc.pojo.Client;
import com.kangyonggan.rpc.pojo.Refrence;
import com.kangyonggan.rpc.pojo.Service;
import com.kangyonggan.rpc.util.*;
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

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
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
        connectRemoteService();
    }

    /**
     * 连接远程服务
     */
    private void connectRemoteService() {
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

                Service service = LoadBalance.getService(refrence.getServices(), client.getLoadBalance());
                channelFuture = bootstrap.connect(service.getIp(), service.getPort()).sync();
                logger.info("连接远程服务端成功:" + service);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 发送消息
     *
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    public Object send(Method method, Object[] args) throws Throwable {
        // 准备请求参数
        RpcRequest request = new RpcRequest();

        // 通用参数
        request.setUuid(RpcContext.getUuid().get());
        Application application = (Application) SpringUtils.getApplicationContext().getBean(RpcPojo.application.name());
        request.setClientApplicationName(application.getName());
        request.setClientIp(InetAddress.getLocalHost().getHostAddress());

        // 必要参数
        request.setClassName(refrence.getName());
        request.setMethodName(method.getName());
        request.setTypes(getTypes(method));
        request.setArgs(args);

        // 判断是否异步调用
        if (refrence.isAsync()) {
            Object result = null;
            FutureTask<Object> futureTask = AsynUtils.submitTask(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    try {
                        return remoteCall(request);
                    } catch (Throwable e) {
                        logger.error("异步调用发生异常", e);
                        throw new RuntimeException(e);
                    }
                }
            });

            // 异步放入上下文中，提交调用方法后，可以从上下文中获取结果
            RpcContext.getFutureTask().set(futureTask);

            // 判断基础类型，返回默认值，否则自动转换会报空指针
            if(TypeParseUtil.isBasicType(method.getReturnType())) {
                result = TypeParseUtil.getBasicTypeDefaultValue(method.getReturnType());
            }

            logger.info("异步调用直接返回:" + result);
            return result;
        }
        return remoteCall(request);
    }

    /**
     * 远程调用
     *
     * @param request
     * @return
     * @throws Throwable
     */
    private Object remoteCall(RpcRequest request) throws Throwable {
        // 判断是否使用缓存
        if (refrence.isUseCache()) {
            CacheUtil.CacheObject cacheObject = CacheUtil.getCache(request);
            if (cacheObject != null) {
                logger.info("结果走缓存：" + cacheObject.getRpcResponse());
                return cacheObject.getRpcResponse().getResult();
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

            return response.getResult();
        }

        throw response.getThrowable();
    }

    /**
     * 获取方法的参数类型
     *
     * @param method
     * @return
     */
    private String[] getTypes(Method method) {
        String[] types = new String[method.getParameterTypes().length];
        for (int i = 0; i < method.getParameterTypes().length; i++) {
            types[i] = method.getParameterTypes()[i].getName();
        }
        return types;
    }
}
