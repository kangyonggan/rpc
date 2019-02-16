## 回顾
上一篇文章写到客户端如何从注册中心订阅服务，那么这一篇写客户端如何远程调用服务端的服务。

![](https://kangyonggan.com/upload/blog/rpc.png)

## 需求
在消费者测试用例中调用UserService的方法，并返回结果。
先启动上一篇文章中的测试用例把UserService发布到注册中心，再启动本例，rpc框架会读取xml并解析，然后把User服务从注册中心获取到本地。

## 实现源码
github: [https://github.com/kangyonggan/rpc.git](https://github.com/kangyonggan/rpc.git)
分支：05-remote-call

## 使用说明
### 第一步
下载源码
```
git clone https://github.com/kangyonggan/rpc.git
```

### 第二步
切换分支
```
git checkout 05-remote-call
```

### 第三步
编译源码
```
mvn clean install
```

### 第四步
运行测试用例，`Rpc05RegisterServiceTest.testPublish()`
```
[INFO ] 2019-02-16 13:34:23,458 com.kangyonggan.rpc.core.RpcServer.run(RpcServer.java:35) : RPC服务端正在启动...
[INFO ] 2019-02-16 13:34:23,469 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:34) : 正在连接zookeeper[122.112.204.190:2181]
[INFO ] 2019-02-16 13:34:23,630 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:36) : 连接zookeeper[122.112.204.190:2181]成功
[INFO ] 2019-02-16 13:34:23,733 com.kangyonggan.rpc.pojo.Service.registerService(Service.java:100) : 服务发布成功:[/rpc/com.kangyonggan.rpc.service.UserService/provider/192.168.2.188_9203]
[INFO ] 2019-02-16 13:34:23,781 com.kangyonggan.rpc.core.RpcServer.run(RpcServer.java:62) : RPC服务端启动完成，监听【9203】端口
[INFO ] 2019-02-16 13:34:38,085 com.kangyonggan.rpc.handler.RpcServerHandler.channelRead(RpcServerHandler.java:27) : RPC服务端收到消息:RpcRequest(uuid=95bb3bb7-b833-4f8e-951d-35b26371172b, className=com.kangyonggan.rpc.service.UserService, methodName=existsMobileNo, types=[java.lang.String], args=[18516690317], clientApplicationName=RPC_CONSUMER, clientIp=192.168.2.188)
[INFO ] 2019-02-16 13:34:38,086 com.kangyonggan.rpc.handler.RpcServerHandler.channelRead(RpcServerHandler.java:64) : 服务端响应内容:RpcResponse(isSuccess=true, result=true, throwable=null)
```

### 第五步
运行测试用例，`Rpc05RemoteCallTest.testRemoveCall()`
```
[INFO ] 2019-02-16 13:34:37,664 com.kangyonggan.rpc.pojo.Refrence.getRefrences(Refrence.java:81) : 正在获取引用服务:[/rpc/com.kangyonggan.rpc.service.UserService/provider]
[INFO ] 2019-02-16 13:34:37,668 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:34) : 正在连接zookeeper[122.112.204.190:2181]
[INFO ] 2019-02-16 13:34:37,802 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:36) : 连接zookeeper[122.112.204.190:2181]成功
[INFO ] 2019-02-16 13:34:37,850 com.kangyonggan.rpc.pojo.Refrence.getRefrences(Refrence.java:94) : 引用服务获取完成[/rpc/com.kangyonggan.rpc.service.UserService/provider]:[Service{id='null', name='com.kangyonggan.rpc.service.UserService', impl='null', ref='userServiceImpl', ip='192.168.2.188', port=9203}]
[INFO ] 2019-02-16 13:34:37,856 com.kangyonggan.rpc.core.RpcClient.connectRemoteService(RpcClient.java:51) : 正在连接远程服务端:Refrence{id='null', name='com.kangyonggan.rpc.service.UserService', services=[Service{id='null', name='com.kangyonggan.rpc.service.UserService', impl='null', ref='userServiceImpl', ip='192.168.2.188', port=9203}]}
[INFO ] 2019-02-16 13:34:38,047 com.kangyonggan.rpc.core.RpcClient.connectRemoteService(RpcClient.java:78) : 连接远程服务端成功:Service{id='null', name='com.kangyonggan.rpc.service.UserService', impl='null', ref='userServiceImpl', ip='192.168.2.188', port=9203}
[INFO ] 2019-02-16 13:34:38,094 com.kangyonggan.rpc.core.RpcClient.send(RpcClient.java:114) : 服务端响应：RpcResponse(isSuccess=true, result=true, throwable=null)
```

