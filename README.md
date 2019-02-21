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
- 02-start-server：启动服务端
- 03-register-service：发布服务
- 04-subscribe-service：获取服务
- 05-remote-call：远程调用
- 06-load-balance：负载均衡
- 07-point2point：点对点服务
- 08-version：服务版本控制
- 09-timeout：超时时间
- 10-cache：结果缓存
- 11-async：异步调用
- 12-fault：容错策略
- 13-generics：泛化调用
- 14-shutdown：优雅停机
- 15-interceptor：拦截器
- 16-zookeeper-listener：服务订阅
- 17-degradation：服务降级
- 18-telnet：telnet治理
- 19-monitor：监控

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
