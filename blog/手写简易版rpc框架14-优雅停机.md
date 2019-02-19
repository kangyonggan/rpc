## 优雅停机
当某台服务关闭时，我们应该主动的从注册中心下掉，以防止客户端继续访问。

## 实现源码
github: [https://github.com/kangyonggan/rpc.git](https://github.com/kangyonggan/rpc.git)
分支：14-shutdown

## 使用说明
### 第一步
下载源码
```
git clone https://github.com/kangyonggan/rpc.git
```

### 第二步
切换分支
```
git checkout 14-shutdown
```

### 第三步
编译源码
```
mvn clean install
```

### 第四步
运行测试用例，`ProviderTest.testPublish()`
```
[INFO ] 2019-02-19 09:33:03,191 com.kangyonggan.rpc.core.RpcServer.run(RpcServer.java:35) : RPC服务端正在启动...
[INFO ] 2019-02-19 09:33:03,211 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:36) : 正在连接zookeeper[122.112.204.190:2181]
[INFO ] 2019-02-19 09:33:03,525 com.kangyonggan.rpc.core.RpcServer.run(RpcServer.java:62) : RPC服务端启动完成，监听【9203】端口
[INFO ] 2019-02-19 09:33:03,582 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:38) : 连接zookeeper[122.112.204.190:2181]成功
[INFO ] 2019-02-19 09:33:03,844 com.kangyonggan.rpc.pojo.Service.registerService(Service.java:102) : 服务发布成功:[/rpc/com.kangyonggan.rpc.service.UserService/provider/172.20.10.8_9203]
[INFO ] 2019-02-19 09:33:11,687 com.kangyonggan.rpc.handler.RpcServerHandler.channelRead(RpcServerHandler.java:27) : RPC服务端收到消息:RpcRequest(uuid=1fdb10b4-38ac-48d9-b2ad-b6251340a059, className=com.kangyonggan.rpc.service.UserService, methodName=existsMobileNo, types=[java.lang.String], args=[18516690317], clientApplicationName=RPC_CONSUMER, clientIp=172.20.10.8)
[INFO ] 2019-02-19 09:33:11,688 com.kangyonggan.rpc.handler.RpcServerHandler.channelRead(RpcServerHandler.java:64) : 服务端响应内容:RpcResponse(isSuccess=true, result=true, throwable=null)
```

### 第五步
运行测试用例，`ConsumerTest.testRemoveCall()`
```
[INFO ] 2019-02-19 09:33:10,723 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:36) : 正在连接zookeeper[122.112.204.190:2181]
[INFO ] 2019-02-19 09:33:10,994 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:38) : 连接zookeeper[122.112.204.190:2181]成功
[INFO ] 2019-02-19 09:33:11,256 com.kangyonggan.rpc.pojo.Refrence.registerRefrence(Refrence.java:126) : 客户端引用发布成功:[/rpc/com.kangyonggan.rpc.service.UserService/consumer/172.20.10.8]
[INFO ] 2019-02-19 09:33:11,257 com.kangyonggan.rpc.pojo.Refrence.getRefrences(Refrence.java:137) : 正在获取引用服务:[/rpc/com.kangyonggan.rpc.service.UserService/provider]
[INFO ] 2019-02-19 09:33:11,395 com.kangyonggan.rpc.pojo.Refrence.getRefrences(Refrence.java:155) : 引用服务获取完成[/rpc/com.kangyonggan.rpc.service.UserService/provider]:[Service{id='null', name='com.kangyonggan.rpc.service.UserService', impl='null', ref='userServiceImpl', ip='172.20.10.8', port=9203, version=null}]
[INFO ] 2019-02-19 09:33:11,409 com.kangyonggan.rpc.core.RpcClient.connectRemoteService(RpcClient.java:53) : 正在连接远程服务端:Refrence{id='null', name='com.kangyonggan.rpc.service.UserService', directServerIp='null', directServerPort=0, version=null, timeout=10000, useCache=false, cacheTime=60, async=false, fault=fail_fast, services=[Service{id='null', name='com.kangyonggan.rpc.service.UserService', impl='null', ref='userServiceImpl', ip='172.20.10.8', port=9203, version=null}], ip=172.20.10.8}
[INFO ] 2019-02-19 09:33:11,530 com.kangyonggan.rpc.core.RpcClient.connectRemoteService(RpcClient.java:88) : 客户端负载均衡策略:poll
[INFO ] 2019-02-19 09:33:11,626 com.kangyonggan.rpc.core.RpcClient.connectRemoteService(RpcClient.java:92) : 连接远程服务端成功:Service{id='null', name='com.kangyonggan.rpc.service.UserService', impl='null', ref='userServiceImpl', ip='172.20.10.8', port=9203, version=null}
[INFO ] 2019-02-19 09:33:11,702 com.kangyonggan.rpc.core.RpcClient.remoteCall(RpcClient.java:122) : 服务端响应：RpcResponse(isSuccess=true, result=true, throwable=null)
[INFO ] 2019-02-19 09:33:11,765 com.kangyonggan.rpc.util.ZookeeperClient.unregisterRefrence(ZookeeperClient.java:79) : 客户端引用已注销:/rpc/com.kangyonggan.rpc.service.UserService/consumer/172.20.10.8
[INFO ] 2019-02-19 09:33:11,765 com.kangyonggan.rpc.pojo.Application.unregisterClient(Application.java:79) : 客户端相关的全部注销
```

### 第六步
停止服务提供者
```
Disconnected from the target VM, address: '127.0.0.1:53621', transport: 'socket'
[INFO ] 2019-02-19 09:34:13,099 com.kangyonggan.rpc.util.ZookeeperClient.unregisterService(ZookeeperClient.java:65) : 服务已注销:/rpc/com.kangyonggan.rpc.service.UserService/provider/172.20.10.8_9203
[INFO ] 2019-02-19 09:34:13,100 com.kangyonggan.rpc.pojo.Application.unregisterServer(Application.java:96) : 服务端相关的全部注销

Process finished with exit code 0
```
