package com.kangyonggan.rpc.core;

import com.kangyonggan.rpc.constants.RpcPojo;
import com.kangyonggan.rpc.pojo.Monitor;
import com.kangyonggan.rpc.util.SpringUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.apache.log4j.Logger;

import java.util.concurrent.TimeUnit;

/**
 * 监控客户端
 *
 * @author kangyonggan
 * @since 2019-02-21
 */
public class MonitorClient {

    private Logger logger = Logger.getLogger(MonitorClient.class);

    private static MonitorClient instance;

    private EventLoopGroup workerGroup;

    private ChannelFuture channelFuture;

    private MonitorClient() {
        init();
    }

    private synchronized void init() {
        Monitor monitor = SpringUtils.getApplicationContext().getBean(RpcPojo.monitor.name(), Monitor.class);
        logger.info("正在连接监控服务端:" + monitor);

        // 客户端线程
        workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);

        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));

                ch.pipeline().addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));

                ch.pipeline().addLast(new LengthFieldPrepender(2));

                ch.pipeline().addLast(new ObjectEncoder());

                ch.pipeline().addLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS));

                // 收发消息
                ch.pipeline().addLast(new ChannelInboundHandlerAdapter());
            }
        });

        try {
            channelFuture = bootstrap.connect(monitor.getIp(), monitor.getPort()).sync();
            logger.info("连接监控服务端成功:" + monitor);
        } catch (Exception e) {
            channelFuture = null;
            throw new RuntimeException(e);
        }
    }

    public static MonitorClient getInstance() {
        if (instance == null) {
            synchronized (MonitorClient.class) {
                if (instance == null) {
                    instance = new MonitorClient();
                }
            }
        }
        return instance;
    }

    /**
     * 发送到监控服务端
     *
     * @param monitorObject
     * @throws Exception
     */
    public void send(MonitorObject monitorObject) throws Exception {
        if (channelFuture == null) {
            init();
        }
        channelFuture.channel().writeAndFlush(monitorObject).sync();
        channelFuture.channel().closeFuture().sync();

        // 关闭资源
        if (null != workerGroup) {
            workerGroup.shutdownGracefully();
        }
        logger.info("监控内容发送成功:" + monitorObject);
    }
}
