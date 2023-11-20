# tools
集众人之所长，成一家之大成。

能够对 Redis、Zookeeper、Kafka、Docker、Kubernetes 等组件提供可视化界面。支持 Linux SSH 客户端，等等很多有趣好玩的功能工具集。

不打算设计 tools web 端，因为只不过是将小程序的功能搬到 web 而已，并且对于 web 开发早已耳熟能详。而小程序是一直没能深入的领域，在这个项目中，我会投入一部分精力，将其完善，并将自己早就萌生的一些想法，添加到项目中。

因为这个项目的定位是工具集，所以开发出来的成品相对来说会"比较重"，功能繁多，但不管怎么说，开发的过程中，总会用到这些功能的。

因为一直没找到合适的实习工作，所以这个仓库什么时候更新完全随缘。

## 开发日志

- 2023-11-14

  - 立项：tools 服务端、tools 小程序端。
  
- 2023-11-20

    - 小程序端除 Redis 面板以外的 Redis 相关业务（增删查改）都已实现。服务端的业务难点在于 Netty 和 Redis Server 的通信，需要足够熟悉 Netty，并且还需要对其进行调优。
    
    - 服务端的 Redis 面板已实现字符串 get。但是测试时 100 个线程并发获取 key 时，报错 `[Finalizer] DEBUG io.netty.buffer.PoolThreadCache - Freed 1 thread-local buffer(s) from thread: Finalizer`。第一版先这样，先把功能实现了，然后再谈优化。
    
## 功能开发

- [ ] Redis 可视化
- [ ] Zookeeper 可视化
- [ ] Kafka 可视化
- [ ] Docker 可视化
- [ ] Kubernetes 可视化
- [ ] SSH 客户端
- [ ] 集成微信支付
- [ ] 集成聊天功能
- [ ] 服务器性能可视化
- [ ] 数据库性能可视化
- [ ] 集成抢票功能
- [ ] ......
