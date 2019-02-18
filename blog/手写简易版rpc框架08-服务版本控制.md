## 服务版本控制
开发中，经常对服务进行版本升级，或者不通环境使用不同版本等情况，因此需要对服务进行版本控制。

> 点对点会忽略版本。

## 实现源码
github: [https://github.com/kangyonggan/rpc.git](https://github.com/kangyonggan/rpc.git)
分支：08-version

## 使用说明
### 第一步
下载源码
```
git clone https://github.com/kangyonggan/rpc.git
```

### 第二步
切换分支
```
git checkout 08-version
```

### 第三步
编译源码
```
mvn clean install
```

### 第四步
运行测试用例，`ProviderTest.testPublish()`
```
[INFO ] 2019-02-18 10:46:39,173 com.kangyonggan.rpc.core.RpcServer.run(RpcServer.java:35) : RPC服务端正在启动...
[INFO ] 2019-02-18 10:46:39,191 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:34) : 正在连接zookeeper[122.112.204.190:2181]
[INFO ] 2019-02-18 10:46:39,415 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:36) : 连接zookeeper[122.112.204.190:2181]成功
[INFO ] 2019-02-18 10:46:39,495 com.kangyonggan.rpc.core.RpcServer.run(RpcServer.java:62) : RPC服务端启动完成，监听【9203】端口
[INFO ] 2019-02-18 10:46:39,499 com.kangyonggan.rpc.pojo.Service.registerService(Service.java:102) : 服务发布成功:[/rpc/com.kangyonggan.rpc.service.UserService/provider/10.156.7.54_9203]
[INFO ] 2019-02-18 10:46:46,441 com.kangyonggan.rpc.handler.RpcServerHandler.channelRead(RpcServerHandler.java:27) : RPC服务端收到消息:RpcRequest(uuid=0118be75-9506-44a1-b0d2-158e8e36bf14, className=com.kangyonggan.rpc.service.UserService, methodName=existsMobileNo, types=[java.lang.String], args=[18516690317], clientApplicationName=RPC_CONSUMER, clientIp=10.156.7.54)
[INFO ] 2019-02-18 10:46:46,442 com.kangyonggan.rpc.handler.RpcServerHandler.channelRead(RpcServerHandler.java:64) : 服务端响应内容:RpcResponse(isSuccess=true, result=true, throwable=null)
```

### 第五步
运行测试用例，`ConsumerTest.testRemoveCall()`
```
[INFO ] 2019-02-18 10:46:45,815 com.kangyonggan.rpc.pojo.Refrence.getRefrences(Refrence.java:95) : 正在获取引用服务:[/rpc/com.kangyonggan.rpc.service.UserService/provider]
[INFO ] 2019-02-18 10:46:45,820 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:34) : 正在连接zookeeper[122.112.204.190:2181]
[INFO ] 2019-02-18 10:46:46,034 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:36) : 连接zookeeper[122.112.204.190:2181]成功
[INFO ] 2019-02-18 10:46:46,093 com.kangyonggan.rpc.pojo.Refrence.getRefrences(Refrence.java:114) : 引用服务获取完成[/rpc/com.kangyonggan.rpc.service.UserService/provider]:[Service{id='null', name='com.kangyonggan.rpc.service.UserService', impl='null', ref='userServiceImpl', ip='10.156.7.54', port=9203, version=1.0.0}]
[INFO ] 2019-02-18 10:46:46,104 com.kangyonggan.rpc.core.RpcClient.connectRemoteService(RpcClient.java:51) : 正在连接远程服务端:Refrence{id='null', name='com.kangyonggan.rpc.service.UserService', directServerIp='null', directServerPort=0, version=1.0.0, services=[Service{id='null', name='com.kangyonggan.rpc.service.UserService', impl='null', ref='userServiceImpl', ip='10.156.7.54', port=9203, version=1.0.0}]}
[INFO ] 2019-02-18 10:46:46,265 com.kangyonggan.rpc.core.RpcClient.connectRemoteService(RpcClient.java:82) : 客户端负载均衡策略:poll
[INFO ] 2019-02-18 10:46:46,382 com.kangyonggan.rpc.core.RpcClient.connectRemoteService(RpcClient.java:86) : 连接远程服务端成功:Service{id='null', name='com.kangyonggan.rpc.service.UserService', impl='null', ref='userServiceImpl', ip='10.156.7.54', port=9203, version=1.0.0}
[INFO ] 2019-02-18 10:46:46,455 com.kangyonggan.rpc.core.RpcClient.send(RpcClient.java:123) : 服务端响应：RpcResponse(isSuccess=true, result=true, throwable=null)
```

