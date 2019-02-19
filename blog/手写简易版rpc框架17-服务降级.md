## 服务降级
订阅注册中心的降级服务，客户端发起请求时判断服务是否被降级，如果被降级直接返回null。

## 实现源码
github: [https://github.com/kangyonggan/rpc.git](https://github.com/kangyonggan/rpc.git)
分支：17-degradation

## 使用说明
### 第一步
下载源码
```
git clone https://github.com/kangyonggan/rpc.git
```

### 第二步
切换分支
```
git checkout 17-degradation
```

### 第三步
编译源码
```
mvn clean install
```

### 第四步
运行测试用例，`ProviderTest.testPublish()`
```
[INFO ] 2019-02-19 15:03:40,992 com.kangyonggan.rpc.core.RpcServer.run(RpcServer.java:35) : RPC服务端正在启动...
[INFO ] 2019-02-19 15:03:41,019 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:37) : 正在连接zookeeper[122.112.204.190:2181]
[INFO ] 2019-02-19 15:03:41,284 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:39) : 连接zookeeper[122.112.204.190:2181]成功
[INFO ] 2019-02-19 15:03:41,395 com.kangyonggan.rpc.core.RpcServer.run(RpcServer.java:62) : RPC服务端启动完成，监听【9203】端口
[INFO ] 2019-02-19 15:03:41,499 com.kangyonggan.rpc.pojo.Service.registerService(Service.java:102) : 服务发布成功:[/rpc/com.kangyonggan.rpc.service.UserService/provider/10.156.7.155_9203]
[INFO ] 2019-02-19 15:03:51,139 com.kangyonggan.rpc.handler.RpcServerHandler.channelRead(RpcServerHandler.java:27) : RPC服务端收到消息:RpcRequest(uuid=1674a3b5-03b5-4e63-a5e6-483b2a9955e0, className=com.kangyonggan.rpc.service.UserService, methodName=existsMobileNo, types=[java.lang.String], args=[18516690317], clientApplicationName=RPC_CONSUMER, clientIp=10.156.7.155)
[INFO ] 2019-02-19 15:03:51,140 com.kangyonggan.rpc.handler.RpcServerHandler.channelRead(RpcServerHandler.java:64) : 服务端响应内容:RpcResponse(isSuccess=true, result=true, throwable=null)
```

### 第五步
运行测试用例，`ConsumerTest.testRemoveCall()`
```
[INFO ] 2019-02-19 15:03:50,261 com.kangyonggan.rpc.util.ServiceDegradeUtil.getDegradeServices(ServiceDegradeUtil.java:43) : 正在从注册中心获取降级服务:[/rpc/degrade]
[INFO ] 2019-02-19 15:03:50,271 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:37) : 正在连接zookeeper[122.112.204.190:2181]
[INFO ] 2019-02-19 15:03:50,533 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:39) : 连接zookeeper[122.112.204.190:2181]成功
[INFO ] 2019-02-19 15:03:50,552 com.kangyonggan.rpc.util.ServiceDegradeUtil.getDegradeServices(ServiceDegradeUtil.java:54) : 从注册中心获取降级服务完成[/rpc/degrade]:[]
[INFO ] 2019-02-19 15:03:50,552 com.kangyonggan.rpc.util.ServiceDegradeUtil.listenerDegradeServices(ServiceDegradeUtil.java:63) : 订阅降级服务:[/rpc/degrade]
[INFO ] 2019-02-19 15:03:50,696 com.kangyonggan.rpc.pojo.Refrence.registerRefrence(Refrence.java:156) : 客户端引用发布成功:[/rpc/com.kangyonggan.rpc.service.UserService/consumer/10.156.7.155]
[INFO ] 2019-02-19 15:03:50,697 com.kangyonggan.rpc.pojo.Refrence.getRefrences(Refrence.java:167) : 正在获取引用服务:[/rpc/com.kangyonggan.rpc.service.UserService/provider]
[INFO ] 2019-02-19 15:03:50,746 com.kangyonggan.rpc.pojo.Refrence.getRefrences(Refrence.java:185) : 引用服务获取完成[/rpc/com.kangyonggan.rpc.service.UserService/provider]:[Service{id='null', name='com.kangyonggan.rpc.service.UserService', impl='null', ref='userServiceImpl', ip='10.156.7.155', port=9203, version=null}]
[INFO ] 2019-02-19 15:03:50,749 com.kangyonggan.rpc.pojo.Refrence.subscribeServiceChange(Refrence.java:130) : 订阅服务变化:[/rpc/com.kangyonggan.rpc.service.UserService/provider]
[INFO ] 2019-02-19 15:03:50,786 com.kangyonggan.rpc.core.RpcClient.connectRemoteService(RpcClient.java:54) : 正在连接远程服务端:Refrence{id='null', name='com.kangyonggan.rpc.service.UserService', directServerIp='null', directServerPort=0, version=null, timeout=10000, useCache=false, cacheTime=60, async=false, fault=fail_fast, interceptor=com.kangyonggan.rpc.interceptor.UserServiceInterceptor, services=[Service{id='null', name='com.kangyonggan.rpc.service.UserService', impl='null', ref='userServiceImpl', ip='10.156.7.155', port=9203, version=null}], ip=10.156.7.155}
[INFO ] 2019-02-19 15:03:50,949 com.kangyonggan.rpc.core.RpcClient.connectRemoteService(RpcClient.java:89) : 客户端负载均衡策略:poll
[INFO ] 2019-02-19 15:03:51,077 com.kangyonggan.rpc.core.RpcClient.connectRemoteService(RpcClient.java:93) : 连接远程服务端成功:Service{id='null', name='com.kangyonggan.rpc.service.UserService', impl='null', ref='userServiceImpl', ip='10.156.7.155', port=9203, version=null}
[INFO ] 2019-02-19 15:03:51,079 com.kangyonggan.rpc.interceptor.UserServiceInterceptor.preHandle(UserServiceInterceptor.java:18) : ===================== 拦截器-方法执行之前 =====================
[INFO ] 2019-02-19 15:03:51,156 com.kangyonggan.rpc.core.RpcClient.remoteCall(RpcClient.java:136) : 服务端响应：RpcResponse(isSuccess=true, result=true, throwable=null)
[INFO ] 2019-02-19 15:03:51,156 com.kangyonggan.rpc.interceptor.UserServiceInterceptor.postHandle(UserServiceInterceptor.java:23) : ===================== 拦截器-方法执行之后 =====================
```
