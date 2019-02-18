package com.kangyonggan.rpc.core;

import com.kangyonggan.rpc.constants.FaultPolicy;
import com.kangyonggan.rpc.handler.ServiceHandler;
import com.kangyonggan.rpc.pojo.Refrence;

/**
 * 泛化调用服务
 *
 * @author kangyonggan
 * @since 2019-02-18
 */
public final class GeneralService {

    /**
     * 泛化调用
     *
     * @param className  类的全路径
     * @param methodName 方法名
     * @param argTypes   参数类型
     * @param args       参数的值
     * @return
     * @throws Throwable
     */
    public static Object invoke(String className, String methodName, Class[] argTypes, Object[] args) throws Throwable {
        Refrence refrence = new Refrence();
        refrence.setName(className);
        refrence.setFault(FaultPolicy.FAIL_FAST.getName());
        return invoke(refrence, methodName, argTypes, args, null);
    }

    /**
     * 泛化调用
     *
     * @param className  类的全路径
     * @param methodName 方法名
     * @param argTypes   参数类型
     * @param args       参数的值
     * @param returnType 返回值类型
     * @param <E>
     * @return
     * @throws Throwable
     */
    public static <E> E invoke(String className, String methodName, Class[] argTypes, Object[] args, Class<E> returnType) throws Throwable {
        Refrence refrence = new Refrence();
        refrence.setName(className);
        refrence.setFault(FaultPolicy.FAIL_FAST.getName());
        return (E) invoke(refrence, methodName, argTypes, args, returnType);
    }

    /**
     * 泛化调用
     *
     * @param refrence   服务引用
     * @param methodName 方法名称
     * @param argTypes   参数类型
     * @param args       参数的值
     * @param returnType 返回值类型
     * @return
     * @throws Throwable
     */
    public static Object invoke(Refrence refrence, String methodName, Class[] argTypes, Object[] args, Class<?> returnType) throws Throwable {
        refrence.getRefrences();
        return new ServiceHandler(refrence).invoke(methodName, argTypes, args, returnType);
    }

}
