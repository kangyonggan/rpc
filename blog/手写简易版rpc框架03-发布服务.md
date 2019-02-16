## 回顾
![](https://kangyonggan.com/upload/blog/rpc.png)
由rpc主调用过程，我们第一步应该要搭建一个注册中心，我使用的注册中心是zookeeper，搭建过程自行百度，我搭建的注册中心地址：`122.112.204.190:2181`。

有了注册中心，我们就可以开发一个服务提供者，然后在xml中配置注册中心的地址，才能把服务发布到注册中心。这其中有3点需要我们开发：
1. 自定义xml标签
2. 启动服务端
3. 发布服务

本篇实现第3点！

## 需求
在xml中配置一个User服务和一个注册中心。
```
<!-- 注册中心 -->
<rpc:register type="zookeeper" ip="122.112.204.190" port="2181" />

<!-- User服务 -->
<rpc:service id="userService" name="com.kangyonggan.rpc.service.UserService" ref="userServiceImpl" />
<bean id="userServiceImpl" class="com.kangyonggan.rpc.service.impl.UserServiceImpl" />
```
容器启动之后，rpc框架会读取xml并解析，然后把User服务发布到注册中心。

## 实现源码
github: [https://github.com/kangyonggan/rpc.git](https://github.com/kangyonggan/rpc.git)
分支：03-register-service

## 使用说明
### 第一步
下载源码
```
git clone https://github.com/kangyonggan/rpc.git
```

### 第二步
切换分支
```
git 03-register-service
```

### 第三步
编译源码
```
mvn clean install
```

### 第四步
运行测试用例，`Rpc03RegisterServiceTest.testPublish()`
```
[INFO ] 2019-02-16 12:48:00,957 com.kangyonggan.rpc.core.RpcServer.run(RpcServer.java:32) : RPC服务端正在启动...
[INFO ] 2019-02-16 12:48:00,968 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:34) : 正在连接zookeeper[122.112.204.190:2181]
[INFO ] 2019-02-16 12:48:01,089 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:36) : 连接zookeeper[122.112.204.190:2181]成功
[INFO ] 2019-02-16 12:48:01,129 com.kangyonggan.rpc.pojo.Service.registerService(Service.java:100) : 服务发布成功:[/rpc/com.kangyonggan.rpc.service.UserService/provider/192.168.2.188_9203]
[INFO ] 2019-02-16 12:48:01,193 com.kangyonggan.rpc.core.RpcServer.run(RpcServer.java:53) : RPC服务端启动完成，监听【9203】端口
```

### 第五步
使用zookeeper客户端命令查看一下是不是真的发布到zookeeper上了：
```
root@kyg:~/install/zookeeper-3.4.13# pwd
/root/install/zookeeper-3.4.13
sh bin/zkCli.sh -server 122.112.204.190:2181
# 一堆日志
[zk: 122.112.204.190:2181(CONNECTED) 0] ls /rpc
[com.kangyonggan.rpc.service.UserService]
```

可以看出UserService已经发布到zookeeper上了。

## 坑点
```
zkClient.writeData(path, data);
```

zkClient在把data写入path的时候，会将data序列化，data对应我们代码中是Service.java，因此要给Service.java实现序列化。

但是如果仅仅将Service.java实现序列化还是回报错，因此Service中有两个字段序列化会失败，即`logger`和`applicationContext`，所以我们序列化时要排除这两个字段，使用关键字`transient`。

