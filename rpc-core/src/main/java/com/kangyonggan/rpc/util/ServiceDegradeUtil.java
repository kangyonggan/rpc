package com.kangyonggan.rpc.util;

import com.kangyonggan.rpc.constants.RegisterType;
import com.kangyonggan.rpc.constants.RpcPojo;
import com.kangyonggan.rpc.core.DegradeServiceChangeListener;
import com.kangyonggan.rpc.pojo.Register;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kangyonggan
 * @since 2019-02-19
 */
public final class ServiceDegradeUtil {

    private static Logger logger = Logger.getLogger(ServiceDegradeUtil.class);

    /**
     * 降级服务
     */
    private static List<String> degradeServices = new ArrayList<>();

    private ServiceDegradeUtil() {
    }

    /**
     * 获取降级服务列表
     *
     * @return
     */
    public static List<String> getDegradeList() {
        return degradeServices;
    }

    /**
     * 判断服务是否降级
     *
     * @param refrenceName
     * @return
     */
    public static boolean exists(String refrenceName) {
        return degradeServices.contains(refrenceName);
    }

    /**
     * 从注册中心获取降级服务
     */
    public static void getDegradeServices() {
        String path = "/rpc/degrade";
        logger.info("正在从注册中心获取降级服务:[" + path + "]");
        Register register = (Register) SpringUtils.getApplicationContext().getBean(RpcPojo.register.name());

        if (RegisterType.zookeeper.name().equals(register.getType())) {
            ZookeeperClient zookeeperClient = ZookeeperClient.getInstance(register.getIp(), register.getPort());
            degradeServices = zookeeperClient.getChildNodes(path);
            if (degradeServices == null) {
                degradeServices = new ArrayList<>();
            }
        }

        logger.info("从注册中心获取降级服务完成[" + path + "]:" + degradeServices);
    }

    /**
     * 订阅降级服务
     */
    public static void listenerDegradeServices() {
        Register register = (Register) SpringUtils.getApplicationContext().getBean(RpcPojo.register.name());
        String path = "/rpc/degrade";
        logger.info("订阅降级服务:[" + path + "]");
        // 订阅子目录变化
        ZookeeperClient.getInstance(register.getIp(), register.getPort()).subscribeChildChange(path, new DegradeServiceChangeListener());
    }

    /**
     * 添加降级服务
     *
     * @param name
     */
    public static void add(String name) {
        degradeServices.add(name);
    }

    /**
     * 删除降级服务
     *
     * @param name
     */
    public static void del(String name) {
        degradeServices.remove(name);
    }
}
