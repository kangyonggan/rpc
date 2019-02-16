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

import java.util.ArrayList;
import java.util.List;

/**
 * 服务引用
 *
 * @author kangyonggan
 * @since 2019-02-15
 */
@Data
public class Refrence implements InitializingBean, ApplicationContextAware {

    private Logger logger = Logger.getLogger(Refrence.class);

    private ApplicationContext applicationContext;

    private String id;

    private String name;

    private List<Service> services;

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
     * 在spring实例化全部的bean之后执行
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (!applicationContext.containsBean(RpcPojo.application.name())) {
            logger.info("没有配置application，无法获取引用");
            return;
        }
        if (!applicationContext.containsBean(RpcPojo.client.name())) {
            logger.info("没有配置client，无法获取引用");
            return;
        }
        if (!applicationContext.containsBean(RpcPojo.register.name())) {
            logger.info("没有配置register，无法获取引用");
            return;
        }

        // 获取引用
        getRefrences();
    }

    /**
     * 获取引用
     *
     * @throws Exception
     */
    private void getRefrences() throws Exception {
        String path = "/rpc/" + name + "/provider";
        logger.info("正在获取引用服务:[" + path + "]");
        Register register = (Register) SpringUtils.getApplicationContext().getBean(RpcPojo.register.name());
        services = new ArrayList<>();

        if (RegisterType.zookeeper.name().equals(register.getType())) {
            ZookeeperClient zookeeperClient = ZookeeperClient.getInstance(register.getIp(), register.getPort());
            List<String> nodes = zookeeperClient.getChildNodes(path);

            for (String node : nodes) {
                services.add((Service) zookeeperClient.getNode(path + "/" + node));
            }
        }

        logger.info("引用服务获取完成[" + path + "]:" + services);
    }

    @Override
    public String toString() {
        return "Refrence{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", services=" + services +
                '}';
    }
}
