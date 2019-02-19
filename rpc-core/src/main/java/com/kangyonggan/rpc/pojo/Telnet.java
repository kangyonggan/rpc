package com.kangyonggan.rpc.pojo;

import com.kangyonggan.rpc.core.RpcTelnet;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author kangyonggan
 * @since 2019-02-19
 */
@Data
public class Telnet implements InitializingBean {

    private int port;

    @Override
    public void afterPropertiesSet() throws Exception {
        new RpcTelnet(port).start();
    }
}
