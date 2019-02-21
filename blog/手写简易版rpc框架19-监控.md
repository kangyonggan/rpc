## 监控
在远程调用完成后，把请求、响应等信息异步发送到监控服务端。

## 实现源码
github: [https://github.com/kangyonggan/rpc.git](https://github.com/kangyonggan/rpc.git)
分支：19-monitor

## 使用说明
### 第一步
下载源码
```
git clone https://github.com/kangyonggan/rpc.git
```

### 第二步
切换分支
```
git checkout 19-monitor
```

### 第三步
编译源码
```
mvn clean install
```

### 第四步
运行测试用例，`ProviderTest.testPublish()`
```
[INFO ] 2019-02-21 09:48:17,970 com.kangyonggan.rpc.core.RpcServer.run(RpcServer.java:35) : RPC服务端正在启动...
[INFO ] 2019-02-21 09:48:17,979 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:37) : 正在连接zookeeper[122.112.204.190:2181]
[INFO ] 2019-02-21 09:48:18,177 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:39) : 连接zookeeper[122.112.204.190:2181]成功
[INFO ] 2019-02-21 09:48:18,341 com.kangyonggan.rpc.core.RpcServer.run(RpcServer.java:62) : RPC服务端启动完成，监听【9203】端口
[INFO ] 2019-02-21 09:48:18,624 com.kangyonggan.rpc.pojo.Service.registerService(Service.java:102) : 服务发布成功:[/rpc/com.kangyonggan.rpc.service.UserService/provider/10.156.7.176_9203]
[INFO ] 2019-02-21 09:48:25,671 com.kangyonggan.rpc.handler.RpcServerHandler.channelRead(RpcServerHandler.java:27) : RPC服务端收到消息:RpcRequest(uuid=f6877f31-57e6-4a98-a6de-41474fad5b2c, className=com.kangyonggan.rpc.service.UserService, methodName=existsMobileNo, types=[java.lang.String], args=[18516690317], clientApplicationName=RPC_CONSUMER, clientIp=10.156.7.176, service=Service{id='null', name='com.kangyonggan.rpc.service.UserService', impl='null', ref='userServiceImpl', ip='10.156.7.176', port=9203, version=null})
[INFO ] 2019-02-21 09:48:25,673 com.kangyonggan.rpc.handler.RpcServerHandler.channelRead(RpcServerHandler.java:64) : 服务端响应内容:RpcResponse(isSuccess=true, result=true, throwable=null)
```

### 第五步
运行测试用例，`ConsumerTest.testRemoteCall()`
```
[INFO ] 2019-02-21 09:48:24,933 com.kangyonggan.rpc.util.ServiceDegradeUtil.getDegradeServices(ServiceDegradeUtil.java:52) : 正在从注册中心获取降级服务:[/rpc/degrade]
[INFO ] 2019-02-21 09:48:24,943 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:37) : 正在连接zookeeper[122.112.204.190:2181]
[INFO ] 2019-02-21 09:48:25,071 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:39) : 连接zookeeper[122.112.204.190:2181]成功
[INFO ] 2019-02-21 09:48:25,091 com.kangyonggan.rpc.util.ServiceDegradeUtil.getDegradeServices(ServiceDegradeUtil.java:63) : 从注册中心获取降级服务完成[/rpc/degrade]:[]
[INFO ] 2019-02-21 09:48:25,092 com.kangyonggan.rpc.util.ServiceDegradeUtil.listenerDegradeServices(ServiceDegradeUtil.java:72) : 订阅降级服务:[/rpc/degrade]
[INFO ] 2019-02-21 09:48:25,239 com.kangyonggan.rpc.pojo.Refrence.registerRefrence(Refrence.java:158) : 客户端引用发布成功:[/rpc/com.kangyonggan.rpc.service.UserService/consumer/10.156.7.176]
[INFO ] 2019-02-21 09:48:25,240 com.kangyonggan.rpc.pojo.Refrence.getRefrences(Refrence.java:169) : 正在获取引用服务:[/rpc/com.kangyonggan.rpc.service.UserService/provider]
[INFO ] 2019-02-21 09:48:25,310 com.kangyonggan.rpc.pojo.Refrence.getRefrences(Refrence.java:187) : 引用服务获取完成[/rpc/com.kangyonggan.rpc.service.UserService/provider]:[Service{id='null', name='com.kangyonggan.rpc.service.UserService', impl='null', ref='userServiceImpl', ip='10.156.7.176', port=9203, version=null}]
[INFO ] 2019-02-21 09:48:25,311 com.kangyonggan.rpc.pojo.Refrence.subscribeServiceChange(Refrence.java:132) : 订阅服务变化:[/rpc/com.kangyonggan.rpc.service.UserService/provider]
[INFO ] 2019-02-21 09:48:25,346 com.kangyonggan.rpc.core.RpcClient.connectRemoteService(RpcClient.java:55) : 正在连接远程服务端:Refrence{id='null', name='com.kangyonggan.rpc.service.UserService', directServerIp='null', directServerPort=0, version=null, timeout=10000, useCache=false, cacheTime=60, async=false, fault=fail_fast, interceptor=null, refCount=0, services=[Service{id='null', name='com.kangyonggan.rpc.service.UserService', impl='null', ref='userServiceImpl', ip='10.156.7.176', port=9203, version=null}], ip=10.156.7.176}
[INFO ] 2019-02-21 09:48:25,494 com.kangyonggan.rpc.core.RpcClient.connectRemoteService(RpcClient.java:90) : 客户端负载均衡策略:poll
[INFO ] 2019-02-21 09:48:25,613 com.kangyonggan.rpc.core.RpcClient.connectRemoteService(RpcClient.java:94) : 连接远程服务端成功:Service{id='null', name='com.kangyonggan.rpc.service.UserService', impl='null', ref='userServiceImpl', ip='10.156.7.176', port=9203, version=null}
[INFO ] 2019-02-21 09:48:25,685 com.kangyonggan.rpc.core.RpcClient.remoteCall(RpcClient.java:141) : 服务端响应：RpcResponse(isSuccess=true, result=true, throwable=null)
[INFO ] 2019-02-21 09:48:25,690 com.kangyonggan.rpc.core.MonitorClient.init(MonitorClient.java:43) : 正在连接监控服务端:Monitor(id=null, ip=122.112.204.190, port=80)
[INFO ] 2019-02-21 09:48:25,702 com.kangyonggan.rpc.core.MonitorClient.init(MonitorClient.java:72) : 连接监控服务端成功:Monitor(id=null, ip=122.112.204.190, port=80)
[INFO ] 2019-02-21 09:48:25,719 com.kangyonggan.rpc.core.MonitorClient.send(MonitorClient.java:107) : 监控内容发送成功:MonitorObject(refrence=Refrence{id='null', name='com.kangyonggan.rpc.service.UserService', directServerIp='null', directServerPort=0, version=null, timeout=10000, useCache=false, cacheTime=60, async=false, fault=fail_fast, interceptor=null, refCount=1, services=[Service{id='null', name='com.kangyonggan.rpc.service.UserService', impl='null', ref='userServiceImpl', ip='10.156.7.176', port=9203, version=null}], ip=10.156.7.176}, rpcRequest=RpcRequest(uuid=f6877f31-57e6-4a98-a6de-41474fad5b2c, className=com.kangyonggan.rpc.service.UserService, methodName=existsMobileNo, types=[java.lang.String], args=[18516690317], clientApplicationName=RPC_CONSUMER, clientIp=10.156.7.176, service=Service{id='null', name='com.kangyonggan.rpc.service.UserService', impl='null', ref='userServiceImpl', ip='10.156.7.176', port=9203, version=null}), rpcResponse=RpcResponse(isSuccess=true, result=true, throwable=null), beginTime=Thu Feb 21 09:48:25 CST 2019, endTime=Thu Feb 21 09:48:25 CST 2019)
```

> 监控服务端自行实现