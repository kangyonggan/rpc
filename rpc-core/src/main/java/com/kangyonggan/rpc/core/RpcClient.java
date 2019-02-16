package com.kangyonggan.rpc.core;

import com.kangyonggan.rpc.constants.RpcPojo;
import com.kangyonggan.rpc.handler.RpcClientHandler;
import com.kangyonggan.rpc.pojo.Application;
import com.kangyonggan.rpc.pojo.Refrence;
import com.kangyonggan.rpc.pojo.Service;
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

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.UUID;

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
                ch.pipeline().addLast(handler);
            }
        });

        try {
            Service service = refrence.getServices().get(0);
            channelFuture = bootstrap.connect(service.getIp(), service.getPort()).sync();

            logger.info("连接远程服务端成功:" + service);
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
        request.setUuid(UUID.randomUUID().toString());
        Application application = (Application) SpringUtils.getApplicationContext().getBean(RpcPojo.application.name());
        request.setClientApplicationName(application.getName());
        request.setClientIp(InetAddress.getLocalHost().getHostAddress());

        // 必要参数
        request.setClassName(refrence.getName());
        request.setMethodName(method.getName());
        request.setTypes(getTypes(method));
        request.setArgs(args);

        // 发送请求
        channelFuture.channel().writeAndFlush(request).sync();
        channelFuture.channel().closeFuture().sync();

        // 接收响应
        RpcResponse response = handler.getResponse();
        logger.info("服务端响应：" + response);

        if (response.getIsSuccess()) {
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
