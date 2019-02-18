## 返回调用
在客户端pom.xml中可以不必依赖服务提供者的api，且xml中不必配置refrence，且xml中refrence标签支持的属性泛化调用都支持。

## 实现源码
github: [https://github.com/kangyonggan/rpc.git](https://github.com/kangyonggan/rpc.git)
分支：13-generics

## 使用说明
### 第一步
下载源码
```
git clone https://github.com/kangyonggan/rpc.git
```

### 第二步
切换分支
```
git checkout 13-generics
```

### 第三步
编译源码
```
mvn clean install
```

### 第四步
运行测试用例，`ProviderTest.testPublish()`
```
[INFO ] 2019-02-18 16:36:49,677 com.kangyonggan.rpc.core.RpcServer.run(RpcServer.java:35) : RPC服务端正在启动...
[INFO ] 2019-02-18 16:36:49,694 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:34) : 正在连接zookeeper[122.112.204.190:2181]
[INFO ] 2019-02-18 16:36:49,936 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:36) : 连接zookeeper[122.112.204.190:2181]成功
[INFO ] 2019-02-18 16:36:49,988 com.kangyonggan.rpc.pojo.Service.registerService(Service.java:102) : 服务发布成功:[/rpc/com.kangyonggan.rpc.service.UserService/provider/10.156.7.54_9203]
[INFO ] 2019-02-18 16:36:49,999 com.kangyonggan.rpc.core.RpcServer.run(RpcServer.java:62) : RPC服务端启动完成，监听【9203】端口
[INFO ] 2019-02-18 16:36:54,281 com.kangyonggan.rpc.handler.RpcServerHandler.channelRead(RpcServerHandler.java:27) : RPC服务端收到消息:RpcRequest(uuid=3029190c-f0a6-406a-af0a-9e39d492e81a, className=com.kangyonggan.rpc.service.UserService, methodName=existsMobileNo, types=[java.lang.String], args=[18516690317], clientApplicationName=RPC_CONSUMER, clientIp=10.156.7.54)
[INFO ] 2019-02-18 16:36:54,282 com.kangyonggan.rpc.handler.RpcServerHandler.channelRead(RpcServerHandler.java:64) : 服务端响应内容:RpcResponse(isSuccess=true, result=true, throwable=null)
```

### 第五步
运行测试用例，`ConsumerTest.testRemoveCall2()`
```
[INFO ] 2019-02-18 16:36:53,695 com.kangyonggan.rpc.pojo.Refrence.getRefrences(Refrence.java:105) : 正在获取引用服务:[/rpc/com.kangyonggan.rpc.service.UserService/provider]
[INFO ] 2019-02-18 16:36:53,699 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:34) : 正在连接zookeeper[122.112.204.190:2181]
[INFO ] 2019-02-18 16:36:53,898 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:36) : 连接zookeeper[122.112.204.190:2181]成功
[INFO ] 2019-02-18 16:36:53,960 com.kangyonggan.rpc.pojo.Refrence.getRefrences(Refrence.java:123) : 引用服务获取完成[/rpc/com.kangyonggan.rpc.service.UserService/provider]:[Service{id='null', name='com.kangyonggan.rpc.service.UserService', impl='null', ref='userServiceImpl', ip='10.156.7.54', port=9203, version=null}]
[INFO ] 2019-02-18 16:36:53,965 com.kangyonggan.rpc.core.RpcClient.connectRemoteService(RpcClient.java:53) : 正在连接远程服务端:Refrence{id='null', name='com.kangyonggan.rpc.service.UserService', directServerIp='null', directServerPort=0, version=null, timeout=0, useCache=false, cacheTime=0, async=false, fault=fail_fast, services=[Service{id='null', name='com.kangyonggan.rpc.service.UserService', impl='null', ref='userServiceImpl', ip='10.156.7.54', port=9203, version=null}]}
[INFO ] 2019-02-18 16:36:54,104 com.kangyonggan.rpc.core.RpcClient.connectRemoteService(RpcClient.java:88) : 客户端负载均衡策略:poll
[INFO ] 2019-02-18 16:36:54,217 com.kangyonggan.rpc.core.RpcClient.connectRemoteService(RpcClient.java:92) : 连接远程服务端成功:Service{id='null', name='com.kangyonggan.rpc.service.UserService', impl='null', ref='userServiceImpl', ip='10.156.7.54', port=9203, version=null}
[INFO ] 2019-02-18 16:36:54,297 com.kangyonggan.rpc.core.RpcClient.remoteCall(RpcClient.java:122) : 服务端响应：RpcResponse(isSuccess=true, result=true, throwable=null)
```

