package com.kangyonggan.rpc.constants;

import lombok.Getter;

/**
 * 负载均衡策略
 *
 * @author kangyonggan
 * @since 2019-02-18
 */
public enum LoadBalancePolicy {

    /**
     * 轮询（缺省）
     */
    POLL("poll", "轮询"),

    /**
     * 随机
     */
    RANDOM("random", "随机"),

    /**
     * 加权轮询
     */
    WEIGHT_POLL("weight_poll", "加权轮询"),

    /**
     * 加权随机
     */
    WEIGHT_RANDOM("weight_random", "加权随机"),

    /**
     * 源地址哈希
     */
    SOURCE_HASH("source_hash", "源地址哈希"),

    /**
     * 最小连接数
     */
    SMALL_CONNECT("small_connect", "最小连接数");

    /**
     * 策略名称
     */
    @Getter
    private String name;

    /**
     * 策略描述
     */
    @Getter
    private String desc;

    LoadBalancePolicy(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

}
