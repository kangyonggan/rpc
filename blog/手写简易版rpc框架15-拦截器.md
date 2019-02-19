## 拦截器
在远程方法调用之前拦截，在远程方法调用之后拦截，在远程方法调用异常时拦截。

## 实现源码
github: [https://github.com/kangyonggan/rpc.git](https://github.com/kangyonggan/rpc.git)
分支：15-interceptor

## 使用说明
### 第一步
下载源码
```
git clone https://github.com/kangyonggan/rpc.git
```

### 第二步
切换分支
```
git checkout 15-interceptor
```

### 第三步
编译源码
```
mvn clean install
```

### 第四步
运行测试用例，`ProviderTest.testPublish()`
```
[INFO ] 2019-02-19 11:11:17,413 com.kangyonggan.rpc.core.RpcServer.run(RpcServer.java:35) : RPC服务端正在启动...
[INFO ] 2019-02-19 11:11:17,430 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:36) : 正在连接zookeeper[122.112.204.190:2181]
[INFO ] 2019-02-19 11:11:17,695 com.kangyonggan.rpc.core.RpcServer.run(RpcServer.java:62) : RPC服务端启动完成，监听【9203】端口
[INFO ] 2019-02-19 11:11:17,770 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:38) : 连接zookeeper[122.112.204.190:2181]成功
[INFO ] 2019-02-19 11:11:18,157 com.kangyonggan.rpc.pojo.Service.registerService(Service.java:102) : 服务发布成功:[/rpc/com.kangyonggan.rpc.service.UserService/provider/172.20.10.8_9203]
[INFO ] 2019-02-19 11:11:25,739 com.kangyonggan.rpc.handler.RpcServerHandler.channelRead(RpcServerHandler.java:27) : RPC服务端收到消息:RpcRequest(uuid=815ccada-c48d-4410-a71f-49a819bbda09, className=com.kangyonggan.rpc.service.UserService, methodName=existsMobileNo, types=[java.lang.String], args=[18516690317], clientApplicationName=RPC_CONSUMER, clientIp=172.20.10.8)
[INFO ] 2019-02-19 11:11:25,740 com.kangyonggan.rpc.handler.RpcServerHandler.channelRead(RpcServerHandler.java:64) : 服务端响应内容:RpcResponse(isSuccess=true, result=true, throwable=null)
```

### 第五步
运行测试用例，`ConsumerTest.testRemoveCall()`
```
[INFO ] 2019-02-19 11:11:23,694 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:36) : 正在连接zookeeper[122.112.204.190:2181]
[INFO ] 2019-02-19 11:11:24,169 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:38) : 连接zookeeper[122.112.204.190:2181]成功
[INFO ] 2019-02-19 11:11:25,245 com.kangyonggan.rpc.pojo.Refrence.registerRefrence(Refrence.java:128) : 客户端引用发布成功:[/rpc/com.kangyonggan.rpc.service.UserService/consumer/172.20.10.8]
[INFO ] 2019-02-19 11:11:25,246 com.kangyonggan.rpc.pojo.Refrence.getRefrences(Refrence.java:139) : 正在获取引用服务:[/rpc/com.kangyonggan.rpc.service.UserService/provider]
[INFO ] 2019-02-19 11:11:25,477 com.kangyonggan.rpc.pojo.Refrence.getRefrences(Refrence.java:157) : 引用服务获取完成[/rpc/com.kangyonggan.rpc.service.UserService/provider]:[Service{id='null', name='com.kangyonggan.rpc.service.UserService', impl='null', ref='userServiceImpl', ip='172.20.10.8', port=9203, version=null}]
[INFO ] 2019-02-19 11:11:25,487 com.kangyonggan.rpc.core.RpcClient.connectRemoteService(RpcClient.java:54) : 正在连接远程服务端:Refrence{id='null', name='com.kangyonggan.rpc.service.UserService', directServerIp='null', directServerPort=0, version=null, timeout=10000, useCache=false, cacheTime=60, async=false, fault=fail_fast, interceptor=com.kangyonggan.rpc.interceptor.UserServiceInterceptor, services=[Service{id='null', name='com.kangyonggan.rpc.service.UserService', impl='null', ref='userServiceImpl', ip='172.20.10.8', port=9203, version=null}], ip=172.20.10.8}
[INFO ] 2019-02-19 11:11:25,599 com.kangyonggan.rpc.core.RpcClient.connectRemoteService(RpcClient.java:89) : 客户端负载均衡策略:poll
[INFO ] 2019-02-19 11:11:25,688 com.kangyonggan.rpc.core.RpcClient.connectRemoteService(RpcClient.java:93) : 连接远程服务端成功:Service{id='null', name='com.kangyonggan.rpc.service.UserService', impl='null', ref='userServiceImpl', ip='172.20.10.8', port=9203, version=null}
[INFO ] 2019-02-19 11:11:25,689 com.kangyonggan.rpc.interceptor.UserServiceInterceptor.preHandle(UserServiceInterceptor.java:18) : ===================== 拦截器-方法执行之前 =====================
[INFO ] 2019-02-19 11:11:25,751 com.kangyonggan.rpc.core.RpcClient.remoteCall(RpcClient.java:136) : 服务端响应：RpcResponse(isSuccess=true, result=true, throwable=null)
[INFO ] 2019-02-19 11:11:25,751 com.kangyonggan.rpc.interceptor.UserServiceInterceptor.postHandle(UserServiceInterceptor.java:23) : ===================== 拦截器-方法执行之后 =====================
[INFO ] 2019-02-19 11:11:25,876 com.kangyonggan.rpc.util.ZookeeperClient.unregisterRefrence(ZookeeperClient.java:79) : 客户端引用已注销:/rpc/com.kangyonggan.rpc.service.UserService/consumer/172.20.10.8
[INFO ] 2019-02-19 11:11:25,876 com.kangyonggan.rpc.pojo.Application.unregisterClient(Application.java:79) : 客户端相关的全部注销
```
