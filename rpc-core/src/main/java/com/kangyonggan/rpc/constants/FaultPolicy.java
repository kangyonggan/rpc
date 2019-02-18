package com.kangyonggan.rpc.constants;

import lombok.Getter;

/**
 * 容错策略
 *
 * @author kangyonggan
 * @since 2019-02-18
 */
public enum FaultPolicy {

    /**
     * 快速失败（缺省）
     */
    FAIL_FAST("fail_fast", "快速失败"),

    /**
     * 失败重试
     */
    FAIL_RETRY("fail_retry", "失败重试"),

    /**
     * 失败安全
     */
    FAIL_SAFE("fail_safe", "失败安全"),

    /**
     * 广播失败
     */
    BROADCAST_FAIL("broadcast_fail", "广播失败"),

    /**
     * 广播安全
     */
    BROADCAST_SAFE("broadcast_safe", "广播安全"),

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

    FaultPolicy(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

}
