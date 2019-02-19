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
     * 查看Application名称
     */
    String APP = "app";

    /**
     * 查看本地IP
     */
    String IP = "ip";

    /**
     * 查看引用列表
     */
    String REFRENCES = "refrences";

    /**
     * 查看服务列表
     */
    String SERVICES = "services ";

    /**
     * 查看引用调用次数
     */
    String COUNT = "count ";

}
