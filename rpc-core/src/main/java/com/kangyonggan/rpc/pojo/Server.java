package com.kangyonggan.rpc.pojo;

import com.kangyonggan.rpc.core.RpcServer;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author kangyonggan
 * @since 2019-02-13
 */
@Data
public class Server implements InitializingBean {

    private String id;

    private Integer port;

    /**
     * 在spring实例化全部的bean之后执行
     */
    @Override
    public void afterPropertiesSet() {
        // 启动服务
        new RpcServer(port).start();
    }
}
