package com.kangyonggan.rpc.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * 异步调用工具
 *
 * @author kangyonggan
 * @since 2019-02-18
 */
public final class AsynUtils {

    /**
     * 异步任务
     */
    private static List<FutureTask<Object>> futureTasks = new ArrayList();

    /**
     * 线程池
     */
    private static ExecutorService executorService = Executors.newFixedThreadPool(3);

    private AsynUtils() {
    }

    /**
     * 异步执行任务-提交任务
     */
    public static FutureTask<Object> submitTask(Callable<Object> callable) {
        FutureTask<Object> futureTask = new FutureTask<>(callable);
        futureTasks.add(futureTask);
        executorService.submit(futureTask);
        return futureTask;
    }

}
