package com.kangyonggan.rpc.handler;

import com.kangyonggan.rpc.constants.TelnetCommand;
import com.kangyonggan.rpc.core.RpcContext;
import com.kangyonggan.rpc.pojo.Refrence;
import com.kangyonggan.rpc.pojo.Service;
import com.kangyonggan.rpc.util.LoadBalance;
import com.kangyonggan.rpc.util.RefrenceUtil;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * @author kangyonggan
 * @since 2019-02-19
 */
public class RpcTelnetHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 欢迎词
     */
    private static final String WELCOME_MSG = "";

    private Logger logger = Logger.getLogger(RpcTelnetHandler.class);

    /**
     * 创建链接时触发
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.write("欢迎使用telnet治理rpc服务[" + RpcContext.getApplicationName() + "-" + RpcContext.getLocalIp() + "]!\r\n");
        ctx.write(WELCOME_MSG);
        ctx.flush();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) {
        logger.info("telnet服务治理收到消息:" + msg);

        String response;
        try {
            if (TelnetCommand.EXIT.equals(msg)) {
                logger.info("退出telnet终端");
                ChannelFuture future = ctx.write("goodbye!!!\r\n");
                ctx.flush();
                future.addListener(ChannelFutureListener.CLOSE);
                return;
            } else if (TelnetCommand.APP.equals(msg)) {
                response = "本地应用名称:" + RpcContext.getApplicationName();
            } else if (TelnetCommand.IP.equals(msg)) {
                response = "本地IP:" + RpcContext.getLocalIp();
            } else if (msg.equals(TelnetCommand.REFRENCES)) {
                List<Refrence> refrences = RefrenceUtil.getAll();
                response = "引用列表:" + refrences.toString();
            } else if (msg.startsWith(TelnetCommand.SERVICES)) {
                String refrenceName = msg.substring(msg.indexOf(" ") + 1);
                Refrence refrence = RefrenceUtil.get(refrenceName);
                if (refrence == null) {
                    response = refrenceName + "引用不存在";
                } else {
                    List<Service> services = refrence.getServices();
                    response = refrenceName + "的引用服务列表:" + services.toString();
                }
            } else if (msg.startsWith(TelnetCommand.COUNT)) {
                String refrenceName = msg.substring(msg.indexOf(" ") + 1);
                response = refrenceName + "引用的调用次数:" + LoadBalance.getRefCount(refrenceName);
            } else {
                response = WELCOME_MSG;
            }
        } catch (Exception e) {
            logger.error("telnet命令解析错误", e);
            response = WELCOME_MSG;
        }

        // 换行
        response += "\r\n";

        // 写响应
        logger.info("telnet服务治理端响应内容:" + response);
        ctx.write(response);
        ctx.flush();
    }
}
