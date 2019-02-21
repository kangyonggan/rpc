package com.kangyonggan.rpc.core;

import com.kangyonggan.rpc.pojo.Refrence;
import com.kangyonggan.rpc.pojo.Service;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author kangyonggan
 * @since 2019-02-21
 */
@Data
public class MonitorObject implements Serializable {

    private Refrence refrence;

    private RpcRequest rpcRequest;

    private RpcResponse rpcResponse;

    private Date beginTime;

    private Date endTime;

}
