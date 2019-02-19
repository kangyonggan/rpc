package com.kangyonggan.rpc.core;

import com.kangyonggan.rpc.pojo.Refrence;

/**
 * RPC拦截器
 *
 * @author kangyonggan
 * @since 2019-02-19
 */
public interface RpcInterceptor {

    /**
     * 远程方法调用之前
     *
     * @param refrence
     * @param rpcRequest
     */
    void preHandle(Refrence refrence, RpcRequest rpcRequest);

    /**
     * 远程方法调用之后
     *
     * @param refrence
     * @param rpcRequest
     */
    void postHandle(Refrence refrence, RpcRequest rpcRequest);

    /**
     * 远程方法调用异常
     *
     * @param refrence
     * @param rpcRequest
     * @param throwable
     */
    void exceptionHandle(Refrence refrence, RpcRequest rpcRequest, Throwable throwable);

}
