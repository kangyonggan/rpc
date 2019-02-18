## 结果缓存
可以设置是否缓存结果，以及缓存有效时间。

## 实现源码
github: [https://github.com/kangyonggan/rpc.git](https://github.com/kangyonggan/rpc.git)
分支：10-cache

## 使用说明
### 第一步
下载源码
```
git clone https://github.com/kangyonggan/rpc.git
```

### 第二步
切换分支
```
git checkout 10-cache
```

### 第三步
编译源码
```
mvn clean install
```

### 第四步
运行测试用例，`ProviderTest.testPublish()`
```
[INFO ] 2019-02-18 12:04:58,355 com.kangyonggan.rpc.core.RpcServer.run(RpcServer.java:35) : RPC服务端正在启动...
[INFO ] 2019-02-18 12:04:58,376 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:34) : 正在连接zookeeper[122.112.204.190:2181]
[INFO ] 2019-02-18 12:04:58,606 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:36) : 连接zookeeper[122.112.204.190:2181]成功
[INFO ] 2019-02-18 12:04:58,650 com.kangyonggan.rpc.pojo.Service.registerService(Service.java:102) : 服务发布成功:[/rpc/com.kangyonggan.rpc.service.UserService/provider/10.156.7.54_9203]
[INFO ] 2019-02-18 12:04:58,681 com.kangyonggan.rpc.core.RpcServer.run(RpcServer.java:62) : RPC服务端启动完成，监听【9203】端口
```

### 第五步
运行测试用例，`ConsumerTest.testRemoveCall()`
```
[INFO ] 2019-02-18 12:05:07,937 com.kangyonggan.rpc.pojo.Refrence.getRefrences(Refrence.java:101) : 正在获取引用服务:[/rpc/com.kangyonggan.rpc.service.UserService/provider]
[INFO ] 2019-02-18 12:05:07,941 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:34) : 正在连接zookeeper[122.112.204.190:2181]
[INFO ] 2019-02-18 12:05:08,155 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:36) : 连接zookeeper[122.112.204.190:2181]成功
[INFO ] 2019-02-18 12:05:08,214 com.kangyonggan.rpc.pojo.Refrence.getRefrences(Refrence.java:120) : 引用服务获取完成[/rpc/com.kangyonggan.rpc.service.UserService/provider]:[Service{id='null', name='com.kangyonggan.rpc.service.UserService', impl='null', ref='userServiceImpl', ip='10.156.7.54', port=9203, version=1.0.0}]
[INFO ] 2019-02-18 12:05:08,224 com.kangyonggan.rpc.core.RpcClient.connectRemoteService(RpcClient.java:56) : 正在连接远程服务端:Refrence{id='null', name='com.kangyonggan.rpc.service.UserService', directServerIp='null', directServerPort=0, version=1.0.0, timeout=10000, useCache=true, cacheTime=60, services=[Service{id='null', name='com.kangyonggan.rpc.service.UserService', impl='null', ref='userServiceImpl', ip='10.156.7.54', port=9203, version=1.0.0}]}
[INFO ] 2019-02-18 12:05:08,378 com.kangyonggan.rpc.core.RpcClient.connectRemoteService(RpcClient.java:91) : 客户端负载均衡策略:poll
[INFO ] 2019-02-18 12:05:08,482 com.kangyonggan.rpc.core.RpcClient.connectRemoteService(RpcClient.java:95) : 连接远程服务端成功:Service{id='null', name='com.kangyonggan.rpc.service.UserService', impl='null', ref='userServiceImpl', ip='10.156.7.54', port=9203, version=1.0.0}
[INFO ] 2019-02-18 12:05:08,608 com.kangyonggan.rpc.core.RpcClient.send(RpcClient.java:141) : 服务端响应：RpcResponse(isSuccess=true, result=true, throwable=null)
[INFO ] 2019-02-18 12:05:08,609 com.kangyonggan.rpc.core.RpcClient.send(RpcClient.java:147) : 结果已缓存
[INFO ] 2019-02-18 12:05:08,610 com.kangyonggan.rpc.core.RpcClient.connectRemoteService(RpcClient.java:56) : 正在连接远程服务端:Refrence{id='null', name='com.kangyonggan.rpc.service.UserService', directServerIp='null', directServerPort=0, version=1.0.0, timeout=10000, useCache=true, cacheTime=60, services=[Service{id='null', name='com.kangyonggan.rpc.service.UserService', impl='null', ref='userServiceImpl', ip='10.156.7.54', port=9203, version=1.0.0}]}
[INFO ] 2019-02-18 12:05:08,612 com.kangyonggan.rpc.core.RpcClient.connectRemoteService(RpcClient.java:91) : 客户端负载均衡策略:poll
[INFO ] 2019-02-18 12:05:08,615 com.kangyonggan.rpc.core.RpcClient.connectRemoteService(RpcClient.java:95) : 连接远程服务端成功:Service{id='null', name='com.kangyonggan.rpc.service.UserService', impl='null', ref='userServiceImpl', ip='10.156.7.54', port=9203, version=1.0.0}
[INFO ] 2019-02-18 12:05:08,615 com.kangyonggan.rpc.core.RpcClient.send(RpcClient.java:130) : 结果走缓存：RpcResponse(isSuccess=true, result=true, throwable=null)
```

