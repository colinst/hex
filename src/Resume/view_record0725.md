## 阿里巴巴电话面试
### 问答
现在一台机器，后面访问量上来了可以扩展吗？
两台服务器客户端怎么知道怎样调取那一台？
不同服务器的增删改查如何进行感知？
先写缓存后写数据库，或者先写数据库后写缓存哪一种情况好一点。
数据脏写怎么办？
```
缓存里数据1被Server process A修改了,   那么缓存里的数据1已经与数据文件中的数据不一致了,那么数据1这时就成为了脏数据,  那么Server process B就不能读出那个脏的数据1了, 跟数据库里的不一致嘛, 否则就形成了脏读.
```
避免脏写脏读
```
缓存重新写一次，或者读的时候触发缓存
```

问服务器缓存多大（未超过20G）
GC压力比较大，怎么办？
GC可以自行更改吗？用什么工具？
GC用的什么算法（不了解）？
netty的reactor模型？
proactor和reactor的区别？
为社么用netty,既然说java的io有毛病，netty也是封装的java的io,为什么用netty?
websocket和普通socket的区别。
心跳的机制选型，推拉机制？
做im的话通信机制怎么做。
关于通信协议是否有相关的了解。
分包算法的解析，tcp怎么拆包分包的具体算法。tcp上一层拆包粘包怎么处理的。
tcp传输的可靠性是如何保证的。重传等等保证机制。重传怎么知道那个包在前那个包在后。
server端怎么知道那个包是新的，那个是旧的。
前期技术选型协议的轻重。
最近看什么书？
kotlin有什么特点。
还有什么其他jvm语言？
kotlin协程是怎么样的。
协程和线程的区别。
区块链的共识算法。
加密相关，ssl,tls
对称加密和不对称加密的区别
kotlin有什么好？良铮