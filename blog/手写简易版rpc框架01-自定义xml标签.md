## 回顾
![](https://kangyonggan.com/upload/blog/rpc.png)
由rpc主调用过程，我们第一步应该要搭建一个注册中心，我使用的注册中心是zookeeper，搭建过程自行百度，我搭建的注册中心地址：`122.112.204.190:2181`。

有了注册中心，我们就可以开发一个服务提供者，然后在xml中配置注册中心的地址，才能把服务发布到注册中心。这其中有3点需要我们开发：
1. 自定义xml标签
2. 启动一个服务
3. 发布服务

本篇实现第1点！

## 需求
下面是我希望的自定义xml标签，为了篇幅，例子中只写一个标签。

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:rpc="https://www.kangyonggan.com/schema/rpc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       https://www.kangyonggan.com/schema/rpc https://www.kangyonggan.com/schema/rpc/rpc.xsd">

    <!-- 应用 -->
    <rpc:application name="TEST_XSD"/>

</beans>
```

## 项目结构
```
│  .gitignore
│  pom.xml
│  README.md
│  rpc.iml
│
├─.idea
│      compiler.xml
│      encodings.xml
│      misc.xml
│      workspace.xml
│
├─rpc-core
│  │  pom.xml
│  │
│  └─src
│      └─main
│          ├─java
│          │  └─com
│          │      └─kangyonggan
│          │          └─rpc
│          │              ├─constants
│          │              │      RpcPojo.java
│          │              │
│          │              ├─pojo
│          │              │      Application.java
│          │              │
│          │              └─xsd
│          │                      RpcNamespaceHandler.java
│          │                      SimpleBeanDefinitionParser.java
│          │
│          └─resources
│              └─META-INF
│                      rpc.xsd
│                      spring.handlers
│                      spring.schemas
│
└─rpc-test
    │  pom.xml
    │
    └─src
        └─test
            ├─java
            │  └─com
            │      └─kangyonggan
            │          └─rpc
            │                  Rpc01XsdTest.java
            │
            └─resources
                    log4j.properties
                    test.xml
```

> 技巧：在Windows命令窗口，进入项目目录下，执行`tree /f`可以查看树状目录。

### 结构说明
- rpc: 项目的父目录，用来声明依赖，所有的版本信息都在这里。
- rpc-core: 存放rpc实现的核心代码，也是其他项目的最小依赖。
- rpc-test: 是测试代码，之所有独立出一个模块，是为了自清。

## 实现源码
github: [https://github.com/kangyonggan/rpc.git](https://github.com/kangyonggan/rpc.git)
分支：01-xsd

## 使用说明
### 第一步
下载源码
```
git clone https://github.com/kangyonggan/rpc.git
```

### 第二步
编译源码
```
mvn clean install
```

### 第三步
查看测试报告，在`rpc-test/target/surefire-reports/com.kangyonggan.rpc.Rpc01XsdTest.txt`， 内容为:
```
-------------------------------------------------------------------------------
Test set: com.kangyonggan.rpc.Rpc01XsdTest
-------------------------------------------------------------------------------
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.292 sec
```

