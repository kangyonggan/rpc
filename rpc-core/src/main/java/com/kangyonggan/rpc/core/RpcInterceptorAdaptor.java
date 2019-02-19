package com.kangyonggan.rpc.core;

import com.kangyonggan.rpc.pojo.Refrence;

/**
 * @author kangyonggan
 * @since 2019-02-19
 */
public abstract class RpcInterceptorAdaptor implements RpcInterceptor {

    @Override
    public void preHandle(Refrence refrence, RpcRequest rpcRequest) {

    }

    @Override
    public void postHandle(Refrence refrence, RpcRequest rpcRequest) {

    }

    @Override
    public void exceptionHandle(Refrence refrence, RpcRequest rpcRequest, Throwable throwable) {

    }
}
