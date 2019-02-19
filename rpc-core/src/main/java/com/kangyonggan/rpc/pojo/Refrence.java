package com.kangyonggan.rpc.pojo;

import com.kangyonggan.rpc.constants.RegisterType;
import com.kangyonggan.rpc.constants.RpcPojo;
import com.kangyonggan.rpc.handler.ServiceHandler;
import com.kangyonggan.rpc.util.SpringUtils;
import com.kangyonggan.rpc.util.ZookeeperClient;
import lombok.Data;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Proxy;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * 服务引用
 *
 * @author kangyonggan
 * @since 2019-02-15
 */
@Data
public class Refrence implements InitializingBean, ApplicationContextAware, FactoryBean, Serializable {

    private transient Logger logger = Logger.getLogger(Refrence.class);

    private transient ApplicationContext applicationContext;

    private String id;

    private String name;

    private String directServerIp;

    private int directServerPort;

    private String version;

    private long timeout;

    private boolean useCache;

    private long cacheTime;

    private boolean async;

    private String fault;

    private String interceptor;

    private transient List<Service> services;

    private String ip;

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

        // 如果是点对点服务，不需要配置注册中心
        if (!StringUtils.isEmpty(directServerIp)) {
            logger.info("点对点服务，" + directServerIp + ":" + directServerPort);
            return;
        }

        if (!applicationContext.containsBean(RpcPojo.register.name())) {
            logger.info("没有配置register，无法获取引用");
            return;
        }

        // 发布客户端引用到注册中心
        registerRefrence();

        // 获取引用
        getRefrences();
    }

    /**
     * 发布客户端引用到注册中心
     *
     * @throws Exception
     */
    private void registerRefrence() throws Exception {
        Register register = (Register) SpringUtils.getApplicationContext().getBean(RpcPojo.register.name());
        ip = InetAddress.getLocalHost().getHostAddress();

        if (RegisterType.zookeeper.name().equals(register.getType())) {
            // zookeeper
            String basePath = "/rpc/" + this.getName() + "/consumer";
            String path = basePath + "/" + ip;

            ZookeeperClient client = ZookeeperClient.getInstance(register.getIp(), register.getPort());

            // 应用（路径）永久保存
            client.createPath(basePath);

            // 服务(数据)不永久保存，当与zookeeper断开连接20s左右自动删除
            client.saveNode(path, this);
            logger.info("客户端引用发布成功:[" + path + "]");
        }
    }

    /**
     * 获取引用
     *
     * @throws Exception
     */
    public void getRefrences() throws Exception {
        String path = "/rpc/" + name + "/provider";
        logger.info("正在获取引用服务:[" + path + "]");
        Register register = (Register) SpringUtils.getApplicationContext().getBean(RpcPojo.register.name());
        services = new ArrayList<>();

        if (RegisterType.zookeeper.name().equals(register.getType())) {
            ZookeeperClient zookeeperClient = ZookeeperClient.getInstance(register.getIp(), register.getPort());
            List<String> nodes = zookeeperClient.getChildNodes(path);

            for (String node : nodes) {
                Service service = (Service) zookeeperClient.getNode(path + "/" + node);
                // 版本为空，则可以匹配任意版本，都在必须匹配一致的版本
                if (!StringUtils.isEmpty(version) && !version.equals(service.getVersion())) {
                    continue;
                }
                services.add(service);
            }
        }

        logger.info("引用服务获取完成[" + path + "]:" + services);
    }

    @Override
    public Object getObject() throws Exception {
        Class<?> clazz = getObjectType();
        // 动态代理，获取远程服务实例
        return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new ServiceHandler(this));
    }

    @Override
    public Class<?> getObjectType() {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            logger.error("没有对应的服务", e);
        }
        return null;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public String toString() {
        return "Refrence{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", directServerIp='" + directServerIp + '\'' +
                ", directServerPort=" + directServerPort +
                ", version=" + version +
                ", timeout=" + timeout +
                ", useCache=" + useCache +
                ", cacheTime=" + cacheTime +
                ", async=" + async +
                ", fault=" + fault +
                ", interceptor=" + interceptor +
                ", services=" + services +
                ", ip=" + ip +
                '}';
    }
}
