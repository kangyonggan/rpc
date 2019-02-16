package com.kangyonggan.rpc.handler;

import com.kangyonggan.rpc.core.RpcClient;
import com.kangyonggan.rpc.pojo.Refrence;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 动态代理。重新invoke，从远程获取服务
 *
 * @author kangyonggan
 * @since 2019-02-15
 */
public class ServiceHandler implements InvocationHandler {

    private Refrence refrence;

    public ServiceHandler(Refrence refrence) {
        this.refrence = refrence;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return new RpcClient(refrence).send(method, args);
    }
}
