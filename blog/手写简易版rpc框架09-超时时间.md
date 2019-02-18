## 超时时间
在客户端和服务端通讯时，我们可能需要配置超时时间（缺省10秒，单位毫秒）。

## 实现源码
github: [https://github.com/kangyonggan/rpc.git](https://github.com/kangyonggan/rpc.git)
分支：09-timeout

## 使用说明
### 第一步
下载源码
```
git clone https://github.com/kangyonggan/rpc.git
```

### 第二步
切换分支
```
git checkout 09-timeout
```

### 第三步
编译源码
```
mvn clean install
```

### 第四步
运行测试用例，`ProviderTest.testPublish()`
```
[INFO ] 2019-02-18 11:31:12,151 com.kangyonggan.rpc.core.RpcServer.run(RpcServer.java:35) : RPC服务端正在启动...
[INFO ] 2019-02-18 11:31:12,168 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:34) : 正在连接zookeeper[122.112.204.190:2181]
[INFO ] 2019-02-18 11:31:12,397 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:36) : 连接zookeeper[122.112.204.190:2181]成功
[INFO ] 2019-02-18 11:31:12,466 com.kangyonggan.rpc.core.RpcServer.run(RpcServer.java:62) : RPC服务端启动完成，监听【9203】端口
[INFO ] 2019-02-18 11:31:12,494 com.kangyonggan.rpc.pojo.Service.registerService(Service.java:102) : 服务发布成功:[/rpc/com.kangyonggan.rpc.service.UserService/provider/10.156.7.54_9203]
[INFO ] 2019-02-18 11:31:17,465 com.kangyonggan.rpc.handler.RpcServerHandler.channelRead(RpcServerHandler.java:27) : RPC服务端收到消息:RpcRequest(uuid=100c36c6-5dc0-4823-8027-5a0a2c11275b, className=com.kangyonggan.rpc.service.UserService, methodName=existsMobileNo, types=[java.lang.String], args=[18516690317], clientApplicationName=RPC_CONSUMER, clientIp=10.156.7.54)
[INFO ] 2019-02-18 11:32:22,629 com.kangyonggan.rpc.handler.RpcServerHandler.channelRead(RpcServerHandler.java:64) : 服务端响应内容:RpcResponse(isSuccess=true, result=true, throwable=null)
```

> 在RpcServerHandler类中打个断点，用于模拟超时。

### 第五步
运行测试用例，`ConsumerTest.testRemoveCall()`
```
[INFO ] 2019-02-18 11:31:16,813 com.kangyonggan.rpc.pojo.Refrence.getRefrences(Refrence.java:97) : 正在获取引用服务:[/rpc/com.kangyonggan.rpc.service.UserService/provider]
[INFO ] 2019-02-18 11:31:16,817 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:34) : 正在连接zookeeper[122.112.204.190:2181]
[INFO ] 2019-02-18 11:31:17,049 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:36) : 连接zookeeper[122.112.204.190:2181]成功
[INFO ] 2019-02-18 11:31:17,112 com.kangyonggan.rpc.pojo.Refrence.getRefrences(Refrence.java:116) : 引用服务获取完成[/rpc/com.kangyonggan.rpc.service.UserService/provider]:[Service{id='null', name='com.kangyonggan.rpc.service.UserService', impl='null', ref='userServiceImpl', ip='10.156.7.54', port=9203, version=1.0.0}]
[INFO ] 2019-02-18 11:31:17,120 com.kangyonggan.rpc.core.RpcClient.connectRemoteService(RpcClient.java:57) : 正在连接远程服务端:Refrence{id='null', name='com.kangyonggan.rpc.service.UserService', directServerIp='null', directServerPort=0, version=1.0.0, timeout=10000, services=[Service{id='null', name='com.kangyonggan.rpc.service.UserService', impl='null', ref='userServiceImpl', ip='10.156.7.54', port=9203, version=1.0.0}]}
[INFO ] 2019-02-18 11:31:17,266 com.kangyonggan.rpc.core.RpcClient.connectRemoteService(RpcClient.java:92) : 客户端负载均衡策略:poll
[INFO ] 2019-02-18 11:31:17,378 com.kangyonggan.rpc.core.RpcClient.connectRemoteService(RpcClient.java:96) : 连接远程服务端成功:Service{id='null', name='com.kangyonggan.rpc.service.UserService', impl='null', ref='userServiceImpl', ip='10.156.7.54', port=9203, version=1.0.0}
[WARN ] 2019-02-18 11:31:27,387 io.netty.channel.DefaultChannelPipeline.onUnhandledInboundException(DefaultChannelPipeline.java:1163) : An exceptionCaught() event was fired, and it reached at the tail of the pipeline. It usually means the last handler in the pipeline did not handle the exception.
io.netty.handler.timeout.ReadTimeoutException
[INFO ] 2019-02-18 11:31:27,391 com.kangyonggan.rpc.core.RpcClient.send(RpcClient.java:133) : 服务端响应：RpcResponse(isSuccess=false, result=null, throwable=java.lang.RuntimeException: 访问服务端超时,timeout=10000ms)

java.lang.RuntimeException: 访问服务端超时,timeout=10000ms

	at com.kangyonggan.rpc.handler.RpcReadTimeoutHandler.readTimedOut(RpcReadTimeoutHandler.java:37)
	at io.netty.handler.timeout.ReadTimeoutHandler.channelIdle(ReadTimeoutHandler.java:90)
	at io.netty.handler.timeout.IdleStateHandler$ReaderIdleTimeoutTask.run(IdleStateHandler.java:494)
	at io.netty.handler.timeout.IdleStateHandler$AbstractIdleTask.run(IdleStateHandler.java:466)
	at io.netty.util.concurrent.PromiseTask$RunnableAdapter.call(PromiseTask.java:38)
	at io.netty.util.concurrent.ScheduledFutureTask.run(ScheduledFutureTask.java:127)
	at io.netty.util.concurrent.AbstractEventExecutor.safeExecute$$$capture(AbstractEventExecutor.java:163)
	at io.netty.util.concurrent.AbstractEventExecutor.safeExecute(AbstractEventExecutor.java)
	at io.netty.util.concurrent.SingleThreadEventExecutor.runAllTasks(SingleThreadEventExecutor.java:404)
	at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:495)
	at io.netty.util.concurrent.SingleThreadEventExecutor$5.run(SingleThreadEventExecutor.java:905)
	at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
	at java.lang.Thread.run(Thread.java:748)
```

