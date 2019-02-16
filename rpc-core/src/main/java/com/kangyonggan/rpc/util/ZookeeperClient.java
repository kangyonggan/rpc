package com.kangyonggan.rpc.util;

import com.kangyonggan.rpc.core.RpcServer;
import org.I0Itec.zkclient.ZkClient;
import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kangyonggan
 * @since 2019-02-14
 */
public final class ZookeeperClient {

    private Logger logger = Logger.getLogger(RpcServer.class);

    /**
     * 单例
     */
    private static ZookeeperClient instance;

    private ZkClient zkClient;

    /**
     * 私有化构造
     *
     * @param ip
     * @param port
     */
    private ZookeeperClient(String ip, String port) {
        logger.info("正在连接zookeeper[" + ip + ":" + port + "]");
        zkClient = new ZkClient(ip + ":" + port);
        logger.info("连接zookeeper[" + ip + ":" + port + "]成功");
    }

    /**
     * 获取zkClient单例
     *
     * @param ip
     * @param port
     * @return
     */
    public static ZookeeperClient getInstance(String ip, String port) {
        if (null == instance) {
            instance = new ZookeeperClient(ip, port);
        }
        return instance;
    }

    /**
     * 判断路径是否存在
     *
     * @param path
     */
    private boolean exists(String path) {
        return zkClient.exists(path);
    }

    /**
     * 应用（路径）永久保存
     *
     * @param path 路径
     */
    public void createPath(String path) {
        if (!exists(path)) {
            String[] paths = path.substring(1).split("/");
            String temp = "";
            for (String dir : paths) {
                temp += "/" + dir;
                if (!exists(temp)) {
                    zkClient.create(temp, null, CreateMode.PERSISTENT);
                }
            }
        }
    }

    /**
     * 服务(数据)不永久保存，当与zookeeper断开连接20s左右自动删除
     *
     * @param path 数据保存路径
     * @param data 数据
     */
    public void saveNode(String path, Object data) {
        if (exists(path)) {
            zkClient.writeData(path, data);
        } else {
            String[] paths = path.substring(1).split("/");
            String temp = "";
            for (String dir : paths) {
                temp += "/" + dir;
                if (!exists(temp)) {
                    zkClient.create(temp, null, CreateMode.EPHEMERAL);
                }
            }
            zkClient.writeData(path, data);
        }
    }

    /**
     * 获取子节点
     *
     * @param path
     * @return
     */
    public List<String> getChildNodes(String path) {
        if (!exists(path)) {
            return new ArrayList<>();
        }
        return zkClient.getChildren(path);
    }

    /**
     * 获取节点
     *
     * @param path
     * @return
     */
    public Object getNode(String path) {
        if (!exists(path)) {
            return null;
        }
        return zkClient.readData(path, new Stat());
    }
}
