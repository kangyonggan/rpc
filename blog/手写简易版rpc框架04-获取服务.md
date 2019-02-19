## 回顾
上一篇文章写道如何把服务端的服务发布到注册中心，那么这一篇写客户端如何从注册中心获取服务的引用。

![](https://kangyonggan.com/upload/blog/rpc.png)

## 需求
在一个新模块下，配置一个`rpc-consumer.xml`
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:rpc="https://www.kangyonggan.com/schema/rpc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       https://www.kangyonggan.com/schema/rpc https://www.kangyonggan.com/schema/rpc/rpc.xsd">

    <!-- 应用 -->
    <rpc:application name="RPC_CONSUMER"/>

    <!-- 客户端 -->
    <rpc:client/>

    <!-- 注册中心 -->
    <rpc:register type="zookeeper" ip="122.112.204.190" port="2181"/>

    <!-- 引用服务 -->
    <rpc:refrence id="mathService" name="com.kangyonggan.rpc.service.UserService"/>
</beans>
```
先启动上一篇文章中的测试用例把UserService发布到注册中心，再启动本例，rpc框架会读取xml并解析，然后把User服务从注册中心获取到本地。

## 实现源码
github: [https://github.com/kangyonggan/rpc.git](https://github.com/kangyonggan/rpc.git)
分支：04-subscribe-service

## 使用说明
### 第一步
下载源码
```
git clone https://github.com/kangyonggan/rpc.git
```

### 第二步
切换分支
```
git checkout 04-subscribe-service
```

### 第三步
编译源码
```
mvn clean install
```

### 第四步
运行测试用例，`Rpc04RegisterServiceTest.testPublish()`
```
[INFO ] 2019-02-16 13:16:36,614 com.kangyonggan.rpc.core.RpcServer.run(RpcServer.java:32) : RPC服务端正在启动...
[INFO ] 2019-02-16 13:16:36,623 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:34) : 正在连接zookeeper[122.112.204.190:2181]
[INFO ] 2019-02-16 13:16:36,756 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:36) : 连接zookeeper[122.112.204.190:2181]成功
[INFO ] 2019-02-16 13:16:36,870 com.kangyonggan.rpc.pojo.Service.registerService(Service.java:100) : 服务发布成功:[/rpc/com.kangyonggan.rpc.service.UserService/provider/192.168.2.188_9203]
[INFO ] 2019-02-16 13:16:36,898 com.kangyonggan.rpc.core.RpcServer.run(RpcServer.java:53) : RPC服务端启动完成，监听【9203】端口
```

### 第五步
运行测试用例，`Rpc04SubscribeServiceTest.testSubscribe()`
```
[INFO ] 2019-02-16 13:17:15,667 com.kangyonggan.rpc.pojo.Refrence.getRefrences(Refrence.java:78) : 正在获取引用服务:[/rpc/com.kangyonggan.rpc.service.UserService/provider]
[INFO ] 2019-02-16 13:17:15,671 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:34) : 正在连接zookeeper[122.112.204.190:2181]
[INFO ] 2019-02-16 13:17:15,792 com.kangyonggan.rpc.util.ZookeeperClient.<init>(ZookeeperClient.java:36) : 连接zookeeper[122.112.204.190:2181]成功
[INFO ] 2019-02-16 13:17:15,861 com.kangyonggan.rpc.pojo.Refrence.getRefrences(Refrence.java:91) : 引用服务获取完成[/rpc/com.kangyonggan.rpc.service.UserService/provider]:[Service{id='null', name='com.kangyonggan.rpc.service.UserService', impl='null', ref='userServiceImpl', ip='192.168.2.188', port=9203}]
```

可以看出已经从到zookeeper上把UserService获取到了。
