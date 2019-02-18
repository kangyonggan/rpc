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

}
