package com.kangyonggan.rpc.core;

import lombok.Getter;

import java.util.UUID;
import java.util.concurrent.FutureTask;

/**
 * 上下文
 *
 * @author kangyonggan
 * @since 2019-02-18
 */
public class RpcContext {

    private static String applicationName;

    private static String localIp;

    /**
     * 线程uuid
     */
    @Getter
    private static ThreadLocal<String> uuid = ThreadLocal.withInitial(() -> UUID.randomUUID().toString());

    /**
     * 异步任务
     */
    @Getter
    private static ThreadLocal<FutureTask<Object>> futureTask = new ThreadLocal<>();

    public static String getApplicationName() {
        return applicationName;
    }

    public static void setApplicationName(String applicationName) {
        RpcContext.applicationName = applicationName;
    }

    public static String getLocalIp() {
        return localIp;
    }

    public static void setLocalIp(String localIp) {
        RpcContext.localIp = localIp;
    }
}
