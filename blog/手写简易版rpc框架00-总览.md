## 简介
RPC（Remote Procedure Call）远程过程调用。本例为简易版rpc框架，实现功能与dubbo类似。

## RPC主调用过程
![](https://kangyonggan.com/upload/blog/rpc.png)

> Provioder：服务提供者
> Register：注册中心
> Consumer：服务消费者

当然，这只是其中一个维度的主过程，其中还有很多花里胡哨的细节，诸如负载均衡、泛华调用、服务降级等功能。待我打通主线任务后再慢慢添加支线任务。

## 源码
https://github.com/kangyonggan/rpc.git

### 分支说明
- master： 完整代码。
- 01-xsd：自定义xml标签

## 环境
- JDK1.8
- Maven3
- IDEA
- Git

## 核心技术
- Java
- Spring
- Netty

---

> 参考资料：[http://www.recorddrip.com/dokuwiki/doku.php?id=%E5%88%86%E4%BA%AB:%E6%8A%80%E6%9C%AF:gxxrpc:gxxrpc%E4%BB%8B%E7%BB%8D%E6%96%87%E6%A1%A3](http://www.recorddrip.com/dokuwiki/doku.php?id=%E5%88%86%E4%BA%AB:%E6%8A%80%E6%9C%AF:gxxrpc:gxxrpc%E4%BB%8B%E7%BB%8D%E6%96%87%E6%A1%A3)
