package com.kangyonggan.rpc.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;

/**
 * @author kangyonggan
 * @since 2019-02-14
 */
public class RpcServerHandler extends ChannelInboundHandlerAdapter {

    Logger logger = Logger.getLogger(RpcServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        logger.info("RPC服务端收到消息");
    }

}
