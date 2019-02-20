package com.kangyonggan.rpc.constants;

/**
 * telnet命令
 *
 * @author kangyonggan
 * @since 2019-02-19
 */
public interface TelnetCommand {

    /**
     * 退出
     */
    String EXIT = "exit";

    /**
     * 帮助
     */
    String HELP = "help";

    /**
     * 客户端
     */
    String CLIENT = "client";

    /**
     * 引用列表
     */
    String REFS = "refs";

    /**
     * 引用详情
     */
    String REF = "ref";

    /**
     * 服务列表
     */
    String SERVICES = "services";

    /**
     * 服务详情
     */
    String SERVICE = "service";

    /**
     * 缓存
     */
    String CACHE = "cache";

    /**
     * 降级服务列表
     */
    String DEGRADES = "degrades";

    /**
     * 添加/删除降级服务
     */
    String DEGRADE = "degrade";

}
