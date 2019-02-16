package com.kangyonggan.rpc.core;

import lombok.Data;

import java.io.Serializable;

/**
 * rpc响应
 *
 * @author kangyonggan
 * @since 2019-02-15
 */
@Data
public class RpcResponse implements Serializable {

    /**
     * 是否成功
     */
    private Boolean isSuccess;

    /**
     * 响应结果
     */
    private Object result;

    /**
     * 异常信息
     */
    private Throwable throwable;

}
