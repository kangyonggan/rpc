package com.kangyonggan.rpc.core;

import com.kangyonggan.rpc.pojo.Service;
import lombok.Data;

import java.io.Serializable;

/**
 * rpc请求
 *
 * @author kangyonggan
 * @since 2019-02-15
 */
@Data
public class RpcRequest implements Serializable {

    /**
     * 唯一键
     */
    private String uuid;

    /**
     * 服务名称
     */
    private String className;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 参数类型
     */
    private String[] types;

    /**
     * 参数
     */
    private Object[] args;

    /**
     * 客户端应用名称
     */
    private String clientApplicationName;

    /**
     * 客户端ip
     */
    private String clientIp;

    /**
     * 服务
     */
    private Service service;

}
