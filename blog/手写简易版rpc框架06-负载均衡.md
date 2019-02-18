## 问题
当有多个服务端提供UserService服务时，作为客户端应该连接哪个服务呢？这就设计到了负载均衡。本文将实现以下负载均衡：

-  轮询
-  随机
-  加权轮询
-  加权随机
-  源地址哈希

## 实现源码
github: [https://github.com/kangyonggan/rpc.git](https://github.com/kangyonggan/rpc.git)
分支：06-load-balance

## 使用说明
### 第一步
下载源码
```
git clone https://github.com/kangyonggan/rpc.git
```

### 第二步
切换分支
```
git checkout 06-load-balance
```

### 第三步
编译源码
```
mvn clean install
```

### 第四步
运行测试用例，`Rpc06RegisterServiceTest.testPublish()`
```
[INFO ] 2019-02-18 09:22:14,493 com.kangyonggan.rpc.core.RpcServer.run(RpcServer.java:35) : RPC服务端正在启动...
[INFO ] 2019-02-18 09:22:14,514 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:34) : 正在连接zookeeper[122.112.204.190:2181]
[INFO ] 2019-02-18 09:22:14,721 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:36) : 连接zookeeper[122.112.204.190:2181]成功
[INFO ] 2019-02-18 09:22:14,768 com.kangyonggan.rpc.core.RpcServer.run(RpcServer.java:62) : RPC服务端启动完成，监听【9203】端口
[INFO ] 2019-02-18 09:22:14,821 com.kangyonggan.rpc.pojo.Service.registerService(Service.java:100) : 服务发布成功:[/rpc/com.kangyonggan.rpc.service.UserService/provider/10.156.7.54_9203]
[INFO ] 2019-02-18 09:22:21,439 com.kangyonggan.rpc.handler.RpcServerHandler.channelRead(RpcServerHandler.java:27) : RPC服务端收到消息:RpcRequest(uuid=83c5f1fb-84eb-40c9-a35d-175268d304f3, className=com.kangyonggan.rpc.service.UserService, methodName=existsMobileNo, types=[java.lang.String], args=[18516690317], clientApplicationName=RPC_CONSUMER, clientIp=10.156.7.54)
[INFO ] 2019-02-18 09:22:21,440 com.kangyonggan.rpc.handler.RpcServerHandler.channelRead(RpcServerHandler.java:64) : 服务端响应内容:RpcResponse(isSuccess=true, result=true, throwable=null)
```

### 第五步
运行测试用例，`Rpc06RemoteCallTest.testRemoveCall()`
```
[INFO ] 2019-02-18 09:22:20,888 com.kangyonggan.rpc.pojo.Refrence.getRefrences(Refrence.java:81) : 正在获取引用服务:[/rpc/com.kangyonggan.rpc.service.UserService/provider]
[INFO ] 2019-02-18 09:22:20,893 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:34) : 正在连接zookeeper[122.112.204.190:2181]
[INFO ] 2019-02-18 09:22:21,102 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:36) : 连接zookeeper[122.112.204.190:2181]成功
[INFO ] 2019-02-18 09:22:21,157 com.kangyonggan.rpc.pojo.Refrence.getRefrences(Refrence.java:94) : 引用服务获取完成[/rpc/com.kangyonggan.rpc.service.UserService/provider]:[Service{id='null', name='com.kangyonggan.rpc.service.UserService', impl='null', ref='userServiceImpl', ip='10.156.7.54', port=9203}]
[INFO ] 2019-02-18 09:22:21,166 com.kangyonggan.rpc.core.RpcClient.connectRemoteService(RpcClient.java:50) : 正在连接远程服务端:Refrence{id='null', name='com.kangyonggan.rpc.service.UserService', services=[Service{id='null', name='com.kangyonggan.rpc.service.UserService', impl='null', ref='userServiceImpl', ip='10.156.7.54', port=9203}]}
[INFO ] 2019-02-18 09:22:21,289 com.kangyonggan.rpc.core.RpcClient.connectRemoteService(RpcClient.java:76) : 客户端负载均衡策略:poll
[INFO ] 2019-02-18 09:22:21,386 com.kangyonggan.rpc.core.RpcClient.connectRemoteService(RpcClient.java:81) : 连接远程服务端成功:Service{id='null', name='com.kangyonggan.rpc.service.UserService', impl='null', ref='userServiceImpl', ip='10.156.7.54', port=9203}
[INFO ] 2019-02-18 09:22:21,453 com.kangyonggan.rpc.core.RpcClient.send(RpcClient.java:117) : 服务端响应：RpcResponse(isSuccess=true, result=true, throwable=null)
```

