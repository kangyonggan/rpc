## 点对点服务
点对点服务又叫做定向服务，是指客户端获取服务地址时可以不通过注册中心。

## 实现源码
github: [https://github.com/kangyonggan/rpc.git](https://github.com/kangyonggan/rpc.git)
分支：07-point2point

## 使用说明
### 第一步
下载源码
```
git clone https://github.com/kangyonggan/rpc.git
```

### 第二步
切换分支
```
git checkout 07-point2point
```

### 第三步
编译源码
```
mvn clean install
```

### 第四步
运行测试用例，`Rpc07RegisterServiceTest.testPublish()`
```
[INFO ] 2019-02-18 10:17:11,016 com.kangyonggan.rpc.core.RpcServer.run(RpcServer.java:35) : RPC服务端正在启动...
[INFO ] 2019-02-18 10:17:11,035 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:34) : 正在连接zookeeper[122.112.204.190:2181]
[INFO ] 2019-02-18 10:17:11,261 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:36) : 连接zookeeper[122.112.204.190:2181]成功
[INFO ] 2019-02-18 10:17:11,308 com.kangyonggan.rpc.pojo.Service.registerService(Service.java:100) : 服务发布成功:[/rpc/com.kangyonggan.rpc.service.UserService/provider/10.156.7.54_9203]
[INFO ] 2019-02-18 10:17:11,312 com.kangyonggan.rpc.core.RpcServer.run(RpcServer.java:62) : RPC服务端启动完成，监听【9203】端口
[INFO ] 2019-02-18 10:17:15,926 com.kangyonggan.rpc.handler.RpcServerHandler.channelRead(RpcServerHandler.java:27) : RPC服务端收到消息:RpcRequest(uuid=89642b84-062e-4638-9567-08356a1aa80e, className=com.kangyonggan.rpc.service.UserService, methodName=existsMobileNo, types=[java.lang.String], args=[18516690317], clientApplicationName=RPC_CONSUMER, clientIp=10.156.7.54)
[INFO ] 2019-02-18 10:17:15,927 com.kangyonggan.rpc.handler.RpcServerHandler.channelRead(RpcServerHandler.java:64) : 服务端响应内容:RpcResponse(isSuccess=true, result=true, throwable=null)
```

### 第五步
运行测试用例，`Rpc07RemoteCallTest.testRemoveCall()`
```
[INFO ] 2019-02-18 10:17:15,458 com.kangyonggan.rpc.pojo.Refrence.afterPropertiesSet(Refrence.java:73) : 点对点服务，127.0.0.1:9203
[INFO ] 2019-02-18 10:17:15,471 com.kangyonggan.rpc.core.RpcClient.connectRemoteService(RpcClient.java:51) : 正在连接远程服务端:Refrence{id='null', name='com.kangyonggan.rpc.service.UserService', directServerIp='127.0.0.1', directServerPort=9203, services=null}
[INFO ] 2019-02-18 10:17:15,859 com.kangyonggan.rpc.core.RpcClient.connectRemoteService(RpcClient.java:78) : 点对点服务连接成功
[INFO ] 2019-02-18 10:17:15,942 com.kangyonggan.rpc.core.RpcClient.send(RpcClient.java:123) : 服务端响应：RpcResponse(isSuccess=true, result=true, throwable=null)
```

