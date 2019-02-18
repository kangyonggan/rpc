## 异步调用
实时返回null（基础类似返回默认值），异步从上下文RpcContext中获取调用结果。

## 实现源码
github: [https://github.com/kangyonggan/rpc.git](https://github.com/kangyonggan/rpc.git)
分支：11-async

## 使用说明
### 第一步
下载源码
```
git clone https://github.com/kangyonggan/rpc.git
```

### 第二步
切换分支
```
git checkout 11-async
```

### 第三步
编译源码
```
mvn clean install
```

### 第四步
运行测试用例，`ProviderTest.testPublish()`
```
[INFO ] 2019-02-18 14:25:06,455 com.kangyonggan.rpc.core.RpcServer.run(RpcServer.java:35) : RPC服务端正在启动...
[INFO ] 2019-02-18 14:25:06,482 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:34) : 正在连接zookeeper[122.112.204.190:2181]
[INFO ] 2019-02-18 14:25:06,759 com.kangyonggan.rpc.core.RpcServer.run(RpcServer.java:62) : RPC服务端启动完成，监听【9203】端口
[INFO ] 2019-02-18 14:25:06,776 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:36) : 连接zookeeper[122.112.204.190:2181]成功
[INFO ] 2019-02-18 14:25:06,861 com.kangyonggan.rpc.pojo.Service.registerService(Service.java:102) : 服务发布成功:[/rpc/com.kangyonggan.rpc.service.UserService/provider/10.156.7.54_9203]
[INFO ] 2019-02-18 14:30:04,541 com.kangyonggan.rpc.handler.RpcServerHandler.channelRead(RpcServerHandler.java:27) : RPC服务端收到消息:RpcRequest(uuid=8e4aba39-47d6-49aa-a96e-9b59d138d823, className=com.kangyonggan.rpc.service.UserService, methodName=existsMobileNo, types=[java.lang.String], args=[18516690317], clientApplicationName=RPC_CONSUMER, clientIp=10.156.7.54)
[INFO ] 2019-02-18 14:30:04,542 com.kangyonggan.rpc.handler.RpcServerHandler.channelRead(RpcServerHandler.java:64) : 服务端响应内容:RpcResponse(isSuccess=true, result=true, throwable=null)
```

### 第五步
运行测试用例，`ConsumerTest.testRemoveCall()`
```
[INFO ] 2019-02-18 14:32:06,025 com.kangyonggan.rpc.pojo.Refrence.getRefrences(Refrence.java:103) : 正在获取引用服务:[/rpc/com.kangyonggan.rpc.service.UserService/provider]
[INFO ] 2019-02-18 14:32:06,032 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:34) : 正在连接zookeeper[122.112.204.190:2181]
[INFO ] 2019-02-18 14:32:06,258 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:36) : 连接zookeeper[122.112.204.190:2181]成功
[INFO ] 2019-02-18 14:32:06,315 com.kangyonggan.rpc.pojo.Refrence.getRefrences(Refrence.java:122) : 引用服务获取完成[/rpc/com.kangyonggan.rpc.service.UserService/provider]:[Service{id='null', name='com.kangyonggan.rpc.service.UserService', impl='null', ref='userServiceImpl', ip='10.156.7.54', port=9203, version=null}]
[INFO ] 2019-02-18 14:32:06,323 com.kangyonggan.rpc.core.RpcClient.connectRemoteService(RpcClient.java:56) : 正在连接远程服务端:Refrence{id='null', name='com.kangyonggan.rpc.service.UserService', directServerIp='null', directServerPort=0, version=null, timeout=10000, useCache=false, cacheTime=60, async=true, services=[Service{id='null', name='com.kangyonggan.rpc.service.UserService', impl='null', ref='userServiceImpl', ip='10.156.7.54', port=9203, version=null}]}
[INFO ] 2019-02-18 14:32:06,450 com.kangyonggan.rpc.core.RpcClient.connectRemoteService(RpcClient.java:91) : 客户端负载均衡策略:poll
[INFO ] 2019-02-18 14:32:06,578 com.kangyonggan.rpc.core.RpcClient.connectRemoteService(RpcClient.java:95) : 连接远程服务端成功:Service{id='null', name='com.kangyonggan.rpc.service.UserService', impl='null', ref='userServiceImpl', ip='10.156.7.54', port=9203, version=null}
[INFO ] 2019-02-18 14:32:06,583 com.kangyonggan.rpc.core.RpcClient.send(RpcClient.java:149) : 异步调用直接返回:false
[INFO ] 2019-02-18 14:32:06,638 com.kangyonggan.rpc.core.RpcClient.remoteCall(RpcClient.java:178) : 服务端响应：RpcResponse(isSuccess=true, result=true, throwable=null)
```

