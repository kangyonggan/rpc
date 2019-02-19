package com.kangyonggan.rpc.util;

import com.kangyonggan.rpc.constants.LoadBalancePolicy;
import com.kangyonggan.rpc.pojo.Service;

import java.net.InetAddress;
import java.util.List;
import java.util.Random;

/**
 * 负载均衡
 *
 * @author kangyonggan
 * @since 2019-02-18
 */
public final class LoadBalance {

    /**
     * 引用次数
     */
    private static long refCount;

    private LoadBalance() {
    }

    /**
     * 根据负载均衡策略获取服务
     *
     * @param services
     * @param loadBalance
     * @return
     * @throws Exception
     */
    public static Service getService(List<Service> services, String loadBalance) throws Exception {
        if (services.isEmpty()) {
            throw new RuntimeException("没有可用的服务");
        }

        refCount++;

        if (LoadBalancePolicy.POLL.getName().equals(loadBalance)) {
            // 轮询
            return poll(services);
        } else if (LoadBalancePolicy.RANDOM.getName().equals(loadBalance)) {
            // 随机
            return random(services);
        } else if (LoadBalancePolicy.SOURCE_HASH.getName().equals(loadBalance)) {
            // 源地址哈希
            return sourceHash(services);
        } else if (LoadBalancePolicy.WEIGHT_POLL.getName().equals(loadBalance)) {
            // 加权轮询
            throw new RuntimeException("暂不支持加权轮询策略");
        } else if (LoadBalancePolicy.WEIGHT_RANDOM.getName().equals(loadBalance)) {
            // 加权随机
            throw new RuntimeException("暂不支持加权随机策略");
        }
        return null;
    }

    /**
     * 获取引用次数
     *
     * @return
     */
    public static long getRefCount() {
        return refCount;
    }

    /**
     * 源地址哈希
     *
     * @param services
     * @return
     * @throws Exception
     */
    private static Service sourceHash(List<Service> services) throws Exception {
        String localIp = InetAddress.getLocalHost().getHostAddress();
        return services.get(localIp.hashCode() % services.size());
    }

    /**
     * 随机
     *
     * @param services
     * @return
     */
    private static Service random(List<Service> services) {
        return services.get(new Random().nextInt(services.size()));
    }

    /**
     * 轮询
     *
     * @param services
     * @return
     */
    private static Service poll(List<Service> services) {
        long index = refCount % services.size();
        return services.get((int) index);
    }
}
