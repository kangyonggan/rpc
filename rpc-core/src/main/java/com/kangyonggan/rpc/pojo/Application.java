package com.kangyonggan.rpc.pojo;

import com.kangyonggan.rpc.constants.RpcPojo;
import com.kangyonggan.rpc.core.RpcContext;
import com.kangyonggan.rpc.util.SpringUtils;
import com.kangyonggan.rpc.util.ZookeeperClient;
import lombok.Data;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.net.InetAddress;
import java.util.Map;

/**
 * 应用
 *
 * @author kangyonggan
 * @since 2019-02-13
 */
@Data
public class Application implements ApplicationContextAware, InitializingBean {

    private Logger logger = Logger.getLogger(Application.class);

    private String id;

    private String name;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.setApplicationContext(applicationContext);
    }

    /**
     * 在spring实例化全部的bean之后执行
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // 优雅停机
        addShutdownHook();

        // 上下文环境
        RpcContext.setApplicationName(name);
        RpcContext.setLocalIp(InetAddress.getLocalHost().getHostAddress());
    }

    /**
     * 停机钩子
     */
    private void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                if (SpringUtils.getApplicationContext().containsBean(RpcPojo.server.name())) {
                    // 停掉服务端相关的
                    unregisterServer();
                }
                if (SpringUtils.getApplicationContext().containsBean(RpcPojo.client.name())) {
                    // 停掉客户端相关的
                    unregisterClient();
                }
            }
        });
    }

    /**
     * 停掉客户端相关的
     */
    private void unregisterClient() {
        Register register = (Register) SpringUtils.getApplicationContext().getBean(RpcPojo.register.name());
        if (register == null) {
            return;
        }
        Map<String, Refrence> refrenceMap = SpringUtils.getApplicationContext().getBeansOfType(Refrence.class);

        for (String key : refrenceMap.keySet()) {
            // 客户端引用从注册中心下掉
            ZookeeperClient.getInstance(register.getIp(), register.getPort()).unregisterRefrence(refrenceMap.get(key));
        }
        logger.info("客户端相关的全部注销");
    }

    /**
     * 停掉服务端相关的
     */
    private void unregisterServer() {
        Register register = (Register) SpringUtils.getApplicationContext().getBean(RpcPojo.register.name());
        if (register == null) {
            return;
        }
        Map<String, Service> serviceMap = SpringUtils.getApplicationContext().getBeansOfType(Service.class);

        for (String key : serviceMap.keySet()) {
            // 服务从注册中心下掉
            ZookeeperClient.getInstance(register.getIp(), register.getPort()).unregisterService(serviceMap.get(key));
        }
        logger.info("服务端相关的全部注销");
    }
}
