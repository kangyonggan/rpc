## 回顾
![](https://kangyonggan.com/upload/blog/rpc.png)
由rpc主调用过程，我们第一步应该要搭建一个注册中心，我使用的注册中心是zookeeper，搭建过程自行百度，我搭建的注册中心地址：`122.112.204.190:2181`。

有了注册中心，我们就可以开发一个服务提供者，然后在xml中配置注册中心的地址，才能把服务发布到注册中心。这其中有3点需要我们开发：
1. 自定义xml标签
2. 启动服务端
3. 发布服务

本篇实现第2点！

## 需求
在xml中配置一个服务端
```
<rpc:server port="9203" />
```
容器启动之后服务端也会启动，并监听9203端口，以便后续提供服务。

## 实现源码
github: [https://github.com/kangyonggan/rpc.git](https://github.com/kangyonggan/rpc.git)
分支：02-start-server

## 使用说明
### 第一步
下载源码
```
git clone https://github.com/kangyonggan/rpc.git
```

### 第二步
切换分支
```
git checkout 02-start-server
```

### 第三步
编译源码
```
mvn clean install
```

### 第四步
运行测试用例，`Rpc02StartrServerTest.testStartServer()`
```
[INFO ] 2019-02-16 12:25:23,693 com.kangyonggan.rpc.core.RpcServer.run(RpcServer.java:32) : RPC服务端正在启动...
[INFO ] 2019-02-16 12:25:23,959 com.kangyonggan.rpc.core.RpcServer.run(RpcServer.java:53) : RPC服务端启动完成，监听【9203】端口
```


