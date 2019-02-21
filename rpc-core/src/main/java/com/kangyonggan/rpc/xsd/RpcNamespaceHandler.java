package com.kangyonggan.rpc.xsd;

import com.kangyonggan.rpc.constants.RpcPojo;
import com.kangyonggan.rpc.pojo.*;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * RPC命名空间解析
 *
 * @author kangyonggan
 * @since 2019-02-13
 */
public class RpcNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser(RpcPojo.application.name(), new SimpleBeanDefinitionParser(Application.class));
        registerBeanDefinitionParser(RpcPojo.server.name(), new SimpleBeanDefinitionParser(Server.class));
        registerBeanDefinitionParser(RpcPojo.register.name(), new SimpleBeanDefinitionParser(Register.class));
        registerBeanDefinitionParser(RpcPojo.service.name(), new SimpleBeanDefinitionParser(Service.class));
        registerBeanDefinitionParser(RpcPojo.client.name(), new SimpleBeanDefinitionParser(Client.class));
        registerBeanDefinitionParser(RpcPojo.refrence.name(), new SimpleBeanDefinitionParser(Refrence.class));
        registerBeanDefinitionParser(RpcPojo.telnet.name(), new SimpleBeanDefinitionParser(Telnet.class));
        registerBeanDefinitionParser(RpcPojo.monitor.name(), new SimpleBeanDefinitionParser(Monitor.class));
    }
}
