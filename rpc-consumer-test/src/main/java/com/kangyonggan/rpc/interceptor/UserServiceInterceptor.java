package com.kangyonggan.rpc.interceptor;

import com.kangyonggan.rpc.core.RpcInterceptorAdaptor;
import com.kangyonggan.rpc.core.RpcRequest;
import com.kangyonggan.rpc.pojo.Refrence;
import org.apache.log4j.Logger;

/**
 * @author kangyonggan
 * @since 2019-02-19
 */
public class UserServiceInterceptor extends RpcInterceptorAdaptor {

    private Logger logger = Logger.getLogger(UserServiceInterceptor.class);

    @Override
    public void preHandle(Refrence refrence, RpcRequest rpcRequest) {
        logger.info("===================== 拦截器-方法执行之前 =====================");
    }

    @Override
    public void postHandle(Refrence refrence, RpcRequest rpcRequest) {
        logger.info("===================== 拦截器-方法执行之后 =====================");
    }

}
