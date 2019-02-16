package com.kangyonggan.rpc.pojo;

import com.kangyonggan.rpc.constants.RegisterType;
import com.kangyonggan.rpc.constants.RpcPojo;
import com.kangyonggan.rpc.util.SpringUtils;
import com.kangyonggan.rpc.util.ZookeeperClient;
import lombok.Data;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * @author kangyonggan
 * @since 2019-02-13
 */
@Data
public class Service implements InitializingBean, ApplicationContextAware, Serializable {

    private transient Logger logger = Logger.getLogger(Service.class);

    private transient ApplicationContext applicationContext;

    private String id;

    private String name;

    private String impl;

    private String ref;

    private String ip;

    private int port;

    /**
     * 在spring实例化全部的bean之后执行
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (!applicationContext.containsBean(RpcPojo.application.name())) {
            logger.info("没有配置application，不发布到注册中心");
            return;
        }
        if (!applicationContext.containsBean(RpcPojo.server.name())) {
            logger.info("没有配置server，不发布到注册中心");
            return;
        }
        if (!applicationContext.containsBean(RpcPojo.register.name())) {
            logger.info("没有配置register，不发布到注册中心");
            return;
        }

        // 发布服务到注册中心
        registerService();
    }

    /**
     * 获取spring上下文对象
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 发布服务到注册中心
     *
     * @throws Exception
     */
    private void registerService() throws Exception {
        Register register = (Register) SpringUtils.getApplicationContext().getBean(RpcPojo.register.name());
        Server server = (Server) applicationContext.getBean(RpcPojo.server.name());

        // 设置服务端ip和port，以便给client端直接调用。
        this.setIp(InetAddress.getLocalHost().getHostAddress());
        this.setPort(server.getPort());

        if (RegisterType.zookeeper.name().equals(register.getType())) {
            // zookeeper
            String basePath = "/rpc/" + this.getName() + "/provider";
            String path = basePath + "/" + InetAddress.getLocalHost().getHostAddress() + "_" + server.getPort();

            ZookeeperClient client = ZookeeperClient.getInstance(register.getIp(), register.getPort());

            // 应用（路径）永久保存
            client.createPath(basePath);

            // 服务(数据)不永久保存，当与zookeeper断开连接20s左右自动删除
            client.saveNode(path, this);
            logger.info("服务发布成功:[" + path + "]");
        }
    }

    @Override
    public String toString() {
        return "Service{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", impl='" + impl + '\'' +
                ", ref='" + ref + '\'' +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                '}';
    }
}
