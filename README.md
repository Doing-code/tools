# tools
集众人之所长，成一家之大成。

能够对 Redis、Zookeeper、Kafka、Docker、Kubernetes 等组件提供可视化界面。支持 Linux SSH 客户端，等等很多有趣好玩的功能工具集。

不打算设计 tools web 端，因为只不过是将小程序的功能搬到 web 而已，并且对于 web 开发早已耳熟能详。而小程序是一直没能深入的领域，在这个项目中，我会投入一部分精力，将其完善，并将自己早就萌生的一些想法，添加到项目中。

因为这个项目的定位是工具集，所以开发出来的成品相对来说会"比较重"，功能繁多，但不管怎么说，开发的过程中，总会用到这些功能的。

因为一直没找到合适的实习工作，所以这个仓库什么时候更新完全随缘。

但是小程序的功能相对比较局限，且需要企业认证，可能最终方向还是 web 端。

## 开发日志

- 2023-11-14

  - 立项：tools 服务端、tools 小程序端。
  
- 2023-11-20

    - 小程序端除 Redis 面板以外的 Redis 相关业务（增删查改）都已实现。服务端的业务难点在于 Netty 和 Redis Server 的通信，需要足够熟悉 Netty，并且还需要对其进行调优。
    
    - 服务端的 Redis 面板已实现字符串 get。但是测试时 100 个线程并发获取 key 时，报错 `[Finalizer] DEBUG io.netty.buffer.PoolThreadCache - Freed 1 thread-local buffer(s) from thread: Finalizer`。第一版先这样，先把功能实现了，然后再谈优化。
    
- 2023-11-22

    - 解决同步获取 Redis 响应的数据，基于 Netty 提供的 Promise 实现。
    
    - 解决连接池问题，基于 Netty 提供的 FixedChannelPool 实现。
    
    - 实现 get、set、scan 三个命令的请求。
    
    - 发送命令的流程已经全部打通，**但是接收到的 buffer 没有手动释放，这是一个优化点**。
    
    - redis 流程全部打通，目前仅支持 String 类型的操作。更多的命令会阶段性开发。
    
    - 小程序端仅支持 key 的列表展示，阶段性开发 tree 列表。
    
- 2023-11-25

    - Redis 可视化功能开发：get、set、scan均已开发完毕。暂不支持 del ，且仅限 string 类型。
    
    - [ ] 待开发功能：查看更多 key、搜索 key、删除 key。支持更多的数据类型
    
    - [ ] 迭代更新时，需要将接口优化为策略模式，需要支持不同数据类型的增删查改。
    
    - [x] 小程序端不会有线上版本，如果有需要，会提供真机体验版本。
    
    - [ ] 服务器信息传输需要加密处理.
    
    - [x] 开发 Zookeeper 可视化接口 API. 暂时仅支持查询.
    
- 2023-11-27

    - Zookeeper 可视化任务已开发完成。（仅支持查询：子节点查询、节点数据查询、节点信息查询）
    
    - [ ] 有时间重写一个 Zookeeper 客户端，其难点在于搞清楚 Zookeeper 通信协议，以及 Jute 的序列化和反序列化。
    
    - [ ] 前端需要优化。
    
    - 对于 Kafka 的可视化，分两种情况，KRaft、Zookeeper。（2.8版本以后用 KRaft）
    
- 2023-12-10

    - [x] 实现通过 socket 和 mysql 通信，并能够发送命令.
    
    - 主从同步也完善了部分，但事件解析有点繁琐且枯燥. 
    
    - mysql 可视化业务往后延，优先完善 Elasticsearch.
    
## 功能开发

- [x] Redis 可视化
- [x] Zookeeper 可视化
- [ ] Kafka 可视化
- [ ] Docker 可视化
- [ ] Kubernetes 可视化
- [ ] SSH 客户端
- [ ] 集成微信支付
- [ ] 集成聊天功能
- [ ] 服务器性能可视化
- [ ] 数据库性能可视化
- [ ] 集成抢票功能
- [ ] 音视频转换
- [ ] 图片去水印
- [ ] ......


开源项目的支持

在此基础上，会继续完善功能.

https://github.com/cmmsky/mysql-x-client/tree/master