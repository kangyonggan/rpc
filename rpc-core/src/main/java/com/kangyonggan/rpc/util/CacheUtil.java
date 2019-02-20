package com.kangyonggan.rpc.util;

import com.kangyonggan.rpc.core.RpcRequest;
import com.kangyonggan.rpc.core.RpcResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 缓存工具类
 *
 * @author kangyonggan
 * @since 2019-02-18
 */
public class CacheUtil {
    /**
     * 缓存大小，超过则使用FIFO缓存失效策略
     */
    private static final int CACHE_SIZE = 1000;

    /**
     * 缓存集合
     */
    private static Map<String, CacheObject> cacheMap = new HashMap<>();

    /**
     * 键值集合，用于FIFO缓存失效策略
     */
    private static List<String> cacheKeyList = new ArrayList<>();

    /**
     * 获取缓存
     *
     * @param rpcRequest
     * @return
     */
    public synchronized static CacheObject getCache(RpcRequest rpcRequest) {
        CacheObject cacheObject = cacheMap.get(rpcRequest.toString());
        if (null == cacheObject) {
            return null;
        }
        long nowTime = System.currentTimeMillis();
        if (nowTime > cacheObject.getEndTime()) {
            // 缓存失效，清除
            cacheKeyList.remove(rpcRequest.toString());
            cacheMap.remove(rpcRequest.toString());
            return null;
        }
        return cacheObject;
    }

    /**
     * 设置缓存
     *
     * @param rpcRequest  请求
     * @param rpcResponse 响应
     * @param cacheTime   缓存时间
     */
    public synchronized static void setCache(RpcRequest rpcRequest, RpcResponse rpcResponse, long cacheTime) {
        long startTime = System.currentTimeMillis();
        long endTime = startTime + cacheTime * 1000;
        CacheObject cacheObject = new CacheObject(rpcRequest, rpcResponse, startTime, endTime, cacheTime);
        cacheKeyList.add(rpcRequest.toString());
        cacheMap.put(rpcRequest.toString(), cacheObject);
        clearCache();
    }

    /**
     * 清除缓存到指定大小
     */
    private synchronized static void clearCache() {
        if (CACHE_SIZE <= 0) {
            cacheKeyList.clear();
            cacheMap.clear();
        } else {
            while (cacheKeyList.size() > CACHE_SIZE) {
                String key = cacheKeyList.remove(0);
                cacheMap.remove(key);
            }
        }
    }

    /**
     * 缓存大小
     *
     * @return
     */
    public static int size() {
        return cacheKeyList.size();
    }

    /**
     * 清空缓存
     */
    public static void clear() {
        cacheMap = new HashMap<>();
        cacheKeyList = new ArrayList<>();
    }

    /**
     * 缓存对象
     */
    @Data
    @AllArgsConstructor
    public static class CacheObject {

        /**
         * 请求
         */
        private RpcRequest rpcRequest;

        /**
         * 结果
         */
        private RpcResponse rpcResponse;

        /**
         * 开始时间
         */
        private long startTime;

        /**
         * 结束时间
         */
        private long endTime;

        /**
         * 缓存时间
         */
        private long cacheTime;
    }
}
