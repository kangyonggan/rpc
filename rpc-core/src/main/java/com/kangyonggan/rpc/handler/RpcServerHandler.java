package com.kangyonggan.rpc.handler;

import com.kangyonggan.rpc.core.RpcRequest;
import com.kangyonggan.rpc.core.RpcResponse;
import com.kangyonggan.rpc.pojo.Service;
import com.kangyonggan.rpc.util.SpringUtils;
import com.kangyonggan.rpc.util.TypeParseUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * @author kangyonggan
 * @since 2019-02-14
 */
public class RpcServerHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = Logger.getLogger(RpcServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        RpcResponse response = new RpcResponse();
        try {
            RpcRequest rpcRequest = (RpcRequest) msg;
            logger.info("RPC服务端收到消息:" + rpcRequest);

            // 获取本地服务
            Map<String, Service> serviceMap = SpringUtils.getApplicationContext().getBeansOfType(Service.class);
            Service service = null;
            for (String key : serviceMap.keySet()) {
                if (serviceMap.get(key).getName().equals(rpcRequest.getClassName())) {
                    service = serviceMap.get(key);
                    break;
                }
            }
            if (service == null) {
                throw new RuntimeException("没有找到服务:" + rpcRequest.getClassName());
            }

            // 获取服务的实现类
            Object serviceImpl = SpringUtils.getApplicationContext().getBean(service.getRef());
            if (serviceImpl == null) {
                throw new RuntimeException("没有找到服务:" + rpcRequest.getClassName());
            }

            // 转换参数和参数类型
            Map<String, Object> map = TypeParseUtil.parseTypeString2Class(rpcRequest.getTypes(), rpcRequest.getArgs());
            Class<?>[] classTypes = (Class<?>[]) map.get("classTypes");
            Object[] args = (Object[]) map.get("args");

            // 反射获取返回值
            Object result = serviceImpl.getClass().getMethod(rpcRequest.getMethodName(), classTypes).invoke(serviceImpl, args);
            response.setResult(result);
            response.setIsSuccess(true);
        } catch (Throwable e) {
            logger.error("服务端接收消息发送异常", e);
            response.setIsSuccess(false);
            response.setThrowable(e);
        }

        // 写响应
        logger.info("服务端响应内容:" + response);
        ctx.write(response);
        ctx.flush();
    }

}
