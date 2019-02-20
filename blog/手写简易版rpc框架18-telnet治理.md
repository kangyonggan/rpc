## telnet治理
通过telnet命令查看应用名称、ip、引用、服务、管理缓存、管理降级服务等。

## 实现源码
github: [https://github.com/kangyonggan/rpc.git](https://github.com/kangyonggan/rpc.git)
分支：18-telnet

## 使用说明
### 第一步
下载源码
```
git clone https://github.com/kangyonggan/rpc.git
```

### 第二步
切换分支
```
git checkout 18-telnet
```

### 第三步
编译源码
```
mvn clean install
```

### 第四步
运行测试用例，`ProviderTest.testPublish()`
```
[INFO ] 2019-02-20 10:15:27,827 com.kangyonggan.rpc.core.RpcServer.run(RpcServer.java:35) : RPC服务端正在启动...
[INFO ] 2019-02-20 10:15:27,846 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:37) : 正在连接zookeeper[122.112.204.190:2181]
[INFO ] 2019-02-20 10:15:27,997 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:39) : 连接zookeeper[122.112.204.190:2181]成功
[INFO ] 2019-02-20 10:15:28,097 com.kangyonggan.rpc.pojo.Service.registerService(Service.java:102) : 服务发布成功:[/rpc/com.kangyonggan.rpc.service.UserService/provider/10.156.7.176_9203]
[INFO ] 2019-02-20 10:15:28,137 com.kangyonggan.rpc.core.RpcServer.run(RpcServer.java:62) : RPC服务端启动完成，监听【9203】端口
```

### 第五步
运行测试用例，`ConsumerTest.testRemoteCall()`
```
[INFO ] 2019-02-20 11:42:41,728 com.kangyonggan.rpc.util.ServiceDegradeUtil.getDegradeServices(ServiceDegradeUtil.java:52) : 正在从注册中心获取降级服务:[/rpc/degrade]
[INFO ] 2019-02-20 11:42:41,736 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:37) : 正在连接zookeeper[122.112.204.190:2181]
[INFO ] 2019-02-20 11:42:41,873 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:39) : 连接zookeeper[122.112.204.190:2181]成功
[INFO ] 2019-02-20 11:42:41,887 com.kangyonggan.rpc.util.ServiceDegradeUtil.getDegradeServices(ServiceDegradeUtil.java:63) : 从注册中心获取降级服务完成[/rpc/degrade]:[]
[INFO ] 2019-02-20 11:42:41,888 com.kangyonggan.rpc.util.ServiceDegradeUtil.listenerDegradeServices(ServiceDegradeUtil.java:72) : 订阅降级服务:[/rpc/degrade]
[INFO ] 2019-02-20 11:42:42,033 com.kangyonggan.rpc.pojo.Refrence.registerRefrence(Refrence.java:158) : 客户端引用发布成功:[/rpc/com.kangyonggan.rpc.service.UserService/consumer/10.156.7.176]
[INFO ] 2019-02-20 11:42:42,034 com.kangyonggan.rpc.pojo.Refrence.getRefrences(Refrence.java:169) : 正在获取引用服务:[/rpc/com.kangyonggan.rpc.service.UserService/provider]
[INFO ] 2019-02-20 11:42:42,080 com.kangyonggan.rpc.pojo.Refrence.getRefrences(Refrence.java:187) : 引用服务获取完成[/rpc/com.kangyonggan.rpc.service.UserService/provider]:[Service{id='null', name='com.kangyonggan.rpc.service.UserService', impl='null', ref='userServiceImpl', ip='10.156.7.176', port=9203, version=null}]
[INFO ] 2019-02-20 11:42:42,081 com.kangyonggan.rpc.pojo.Refrence.subscribeServiceChange(Refrence.java:132) : 订阅服务变化:[/rpc/com.kangyonggan.rpc.service.UserService/provider]
[INFO ] 2019-02-20 11:42:42,107 com.kangyonggan.rpc.core.RpcTelnet.run(RpcTelnet.java:34) : telnet服务治理正在启动...
[INFO ] 2019-02-20 11:42:42,112 com.kangyonggan.rpc.pojo.Service.afterPropertiesSet(Service.java:54) : 没有配置server，不发布到注册中心
[INFO ] 2019-02-20 11:42:42,381 com.kangyonggan.rpc.core.RpcTelnet.run(RpcTelnet.java:64) : telnet服务治理启动完成，监听【9217】端口
[INFO ] 2019-02-20 11:42:59,516 com.kangyonggan.rpc.util.ServiceDegradeUtil.getDegradeServices(ServiceDegradeUtil.java:52) : 正在从注册中心获取降级服务:[/rpc/degrade]
[INFO ] 2019-02-20 11:42:59,528 com.kangyonggan.rpc.util.ServiceDegradeUtil.getDegradeServices(ServiceDegradeUtil.java:63) : 从注册中心获取降级服务完成[/rpc/degrade]:[]
```

### 第六步
打开终端：
```
kangyonggans-MacBook-Pro:~ kyg$ telnet 127.0.0.1 9217
Trying 127.0.0.1...
Connected to localhost.
Escape character is '^]'.
10.156.7.176:9217>help
 1. help                                    帮助
 2. exit                                    退出
 3. client                                  客户端信息
 4. refs                                    引用列表
 5. ref [name]                              引用详情
 6. services                                服务列表
 7. service [name]                          服务详情
 8. cache -size                             缓存大小
 9. cache -clear                            清空缓存
10. degrades                                降级服务列表
11. degrade -pull                           拉取最新降级服务
12. degrade -add [name]                     添加降级服务
13. degrade -del [name]                     删除降级服务
10.156.7.176:9217>degrade -pull
Total 0
10.156.7.176:9217>client
应用名称：RPC_CONSUMER
IP地址：10.156.7.176
10.156.7.176:9217>reds
Unknown command 'reds'. See 'help'.
10.156.7.176:9217>refs
Total 1
0) com.kangyonggan.rpc.service.UserService
10.156.7.176:9217>ref com.kangyonggan.rpc.service.UserService
Refrence{id='null', name='com.kangyonggan.rpc.service.UserService', directServerIp='null', directServerPort=0, version=null, timeout=10000, useCache=false, cacheTime=60, async=false, fault=fail_fast, interceptor=com.kangyonggan.rpc.interceptor.UserServiceInterceptor, refCount=0, services=[Service{id='null', name='com.kangyonggan.rpc.service.UserService', impl='null', ref='userServiceImpl', ip='10.156.7.176', port=9203, version=null}], ip=10.156.7.176}
10.156.7.176:9217>
```