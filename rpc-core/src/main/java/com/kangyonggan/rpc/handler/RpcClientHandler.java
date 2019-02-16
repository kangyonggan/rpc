package com.kangyonggan.rpc.handler;

import com.kangyonggan.rpc.core.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.Data;

/**
 * @author kangyonggan
 * @since 2019-02-15
 */
@Data
public class RpcClientHandler extends ChannelInboundHandlerAdapter {

    private RpcResponse response;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        this.response = (RpcResponse) msg;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 每次用完要关闭，不然拿不到response，我也不知道为啥（目测得了解netty才行）
        ctx.flush();
        ctx.close();
    }
}
