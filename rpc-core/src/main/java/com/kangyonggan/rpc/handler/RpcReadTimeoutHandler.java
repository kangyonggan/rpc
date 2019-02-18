package com.kangyonggan.rpc.handler;

import com.kangyonggan.rpc.core.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author kangyonggan
 * @since 2019-02-18
 */
public class RpcReadTimeoutHandler extends ReadTimeoutHandler {

    private RpcClientHandler handler;

    private long timeout;

    public RpcReadTimeoutHandler(int timeoutSeconds) {
        super(timeoutSeconds);
    }

    public RpcReadTimeoutHandler(long timeout, TimeUnit unit) {
        super(timeout, unit);
    }

    public RpcReadTimeoutHandler(RpcClientHandler handler, long timeout, TimeUnit milliseconds) {
        super(timeout, milliseconds);
        this.handler = handler;
        this.timeout = timeout;
    }

    @Override
    protected void readTimedOut(ChannelHandlerContext ctx) throws Exception {
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setIsSuccess(false);
        rpcResponse.setThrowable(new RuntimeException("访问服务端超时,timeout=" + timeout + "ms"));
        handler.setResponse(rpcResponse);
        super.readTimedOut(ctx);
    }
}
