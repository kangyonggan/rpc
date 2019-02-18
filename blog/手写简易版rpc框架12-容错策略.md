## 容错策略
由于网络抖动、连接超时等情况，在调用时可能会出现调用异常，次数可以配置失败重试、失败安全等策略。

## 实现源码
github: [https://github.com/kangyonggan/rpc.git](https://github.com/kangyonggan/rpc.git)
分支：12-fault

## 使用说明
### 第一步
下载源码
```
git clone https://github.com/kangyonggan/rpc.git
```

### 第二步
切换分支
```
git checkout 12-fault
```

### 第三步
编译源码
```
mvn clean install
```

### 第四步
不要运行服务提供者，模拟连接失败的场景。

### 第五步
运行测试用例，`ConsumerTest.testRemoveCall()`
```
[INFO ] 2019-02-18 15:20:12,548 com.kangyonggan.rpc.pojo.Refrence.getRefrences(Refrence.java:105) : 正在获取引用服务:[/rpc/com.kangyonggan.rpc.service.UserService/provider]
[INFO ] 2019-02-18 15:20:12,552 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:34) : 正在连接zookeeper[122.112.204.190:2181]
[INFO ] 2019-02-18 15:20:12,772 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:36) : 连接zookeeper[122.112.204.190:2181]成功
[INFO ] 2019-02-18 15:20:12,805 com.kangyonggan.rpc.pojo.Refrence.getRefrences(Refrence.java:124) : 引用服务获取完成[/rpc/com.kangyonggan.rpc.service.UserService/provider]:[]
[INFO ] 2019-02-18 15:20:12,818 com.kangyonggan.rpc.core.RpcClient.connectRemoteService(RpcClient.java:53) : 正在连接远程服务端:Refrence{id='null', name='com.kangyonggan.rpc.service.UserService', directServerIp='null', directServerPort=0, version=null, timeout=10000, useCache=false, cacheTime=60, async=false, fault=fail_safe, services=[]}
[INFO ] 2019-02-18 15:20:12,950 com.kangyonggan.rpc.core.RpcClient.connectRemoteService(RpcClient.java:88) : 客户端负载均衡策略:poll
[ERROR] 2019-02-18 15:20:12,951 com.kangyonggan.rpc.handler.ServiceHandler.remoteCall(ServiceHandler.java:98) : java.lang.RuntimeException: java.lang.RuntimeException: 没有可用的服务
[INFO ] 2019-02-18 15:20:12,952 com.kangyonggan.rpc.handler.ServiceHandler.remoteCall(ServiceHandler.java:109) : 远程调用失败，使用失败安全策略
```

