[Jvm基本结构](https://lingsui.github.io/2018/03/08/JVM%E5%9F%BA%E6%9C%AC%E7%BB%93%E6%9E%84%E3%80%81%E5%86%85%E5%AD%98%E6%A8%A1%E5%9D%97/)
[触发JVM进行Full GC的情况及应对策略](https://blog.csdn.net/chenleixing/article/details/46706039/)

[Netty百万级推送服务设计要点](https://www.cnblogs.com/zhwl/p/4748507.html)


## 杂项
多个线程同时读写，读线程的数量远远⼤于写线程，你认为应该如何解决 并发的问题？你会选择加什么样的锁？
JAVA的AQS是否了解，它是⼲嘛的？
除了synchronized关键字之外，你是怎么来保障线程安全的？
什么时候需要加volatile关键字？它能保证线程安全吗？
线程池内的线程如果全部忙，提交⼀个新的任务，会发⽣什么？队列全部 塞满了之后，还是忙，再提交会发⽣什么？
Tomcat本身的参数你⼀般会怎么调整？
synchronized关键字锁住的是什么东⻄？在字节码中是怎么表示的？在内 存中的对象上表现为什么？
wait/notify/notifyAll⽅法需不需要被包含在synchronized块中？这是为什 么？
ExecutorService你⼀般是怎么⽤的？是每个service放⼀个还是⼀个项⽬ ⾥⾯放⼀个？有什么好处？

##Spring
你有没有⽤过Spring的AOP? 是⽤来⼲嘛的? ⼤概会怎么使⽤？
如果⼀个接⼝有2个不同的实现, 那么怎么来Autowire⼀个指定的实现？
Spring的声明式事务 @Transaction注解⼀般写在什么位置? 抛出了异常 会⾃动回滚吗？有没有办法控制不触发回滚?
如果想在某个Bean⽣成并装配完毕后执⾏⾃⼰的逻辑，可以什么⽅式实 现？
SpringBoot没有放到web容器⾥为什么能跑HTTP服务？
SpringBoot中如果你想使⽤⾃定义的配置⽂件⽽不仅仅是 application.properties，应该怎么弄？
SpringMVC中RequestMapping可以指定GET, POST⽅法么？怎么指定？
SpringMVC如果希望把输出的Object(例如XXResult或者XXResponse)这 种包装为JSON输出, 应该怎么处理?
怎样拦截SpringMVC的异常，然后做⾃定义的处理，⽐如打⽇志或者包装 成JSON
1.struts1和struts2的区别
.struts2和springMVC的区别
spring框架中需要引用哪些jar包，以及这些jar包的用途
springMVC的原理
springMVC注解的意思
spring中beanFactory和ApplicationContext的联系和区别
spring注入的几种方式
spring如何实现事物管理的
springIOC和AOP的原理
hibernate中的1级和2级缓存的使用方式以及区别原理
spring中循环注入的方式

## MySQL
如果有很多数据插⼊MYSQL 你会选择什么⽅式?
如果查询很慢，你会想到的第⼀个⽅式是什么？索引是⼲嘛的?
如果建了⼀个单列索引，查询的时候查出2列，会⽤到这个单列索引吗？
如果建了⼀个包含多个列的索引，查询的时候只⽤了第⼀列，能不能⽤上 这个索引？查三列呢？
接上题，如果where条件后⾯带有⼀个 i + 5 < 100 会使⽤到这个索引吗？
怎么看是否⽤到了某个索引？
like %aaa%会使⽤索引吗? like aaa%呢?
drop、truncate、delete的区别？
平时你们是怎么监控数据库的? 慢SQL是怎么排查的？
你们数据库是否⽀持emoji表情，如果不⽀持，如何操作?
你们的数据库单表数据量是多少？⼀般多⼤的时候开始出现查询性能急 剧下降？
查询死掉了，想要找出执⾏的查询进程⽤什么命令？找出来之后⼀般你 会⼲嘛？
读写分离是怎么做的？你认为中间件会怎么来操作？这样操作跟事务有 什么关系？ 14. 分库分表有没有做过？线上的迁移过程是怎么样的？如何确定数据是正 确的？
MySQL常用命令
数据库中事物的特征？
JDBC的使用？
InnodB与MyISAM的区别
MySQL为什么使用B+树作为索引？

## Jvm  [answer1](https://lingsui.github.io/2018/03/08/JVM%E5%9F%BA%E6%9C%AC%E7%BB%93%E6%9E%84%E3%80%81%E5%86%85%E5%AD%98%E6%A8%A1%E5%9D%97/)
你知道哪些或者你们线上使⽤什么GC策略? 它有什么优势，适⽤于什么 场景？
JAVA类加载器包括⼏种？它们之间的⽗⼦关系是怎么样的？双亲委派机 制是什么意思？有什么好处？
如何⾃定义⼀个类加载器？你使⽤过哪些或者你在什么场景下需要⼀个⾃ 定义的类加载器吗？
堆内存设置的参数是什么？ 5. Perm Space中保存什么数据? 会引起OutOfMemory吗？ 6. 做gc时，⼀个对象在内存各个Space中被移动的顺序是什么？
你有没有遇到过OutOfMemory问题？你是怎么来处理这个问题的？处理 过程中有哪些收获？
1.8之后Perm Space有哪些变动? MetaSpace⼤⼩默认是⽆限的么? 还是 你们会通过什么⽅式来指定⼤⼩?
Jstack是⼲什么的? Jstat呢? 如果线上程序周期性地出现卡顿，你怀疑可 能是gc导致的，你会怎么来排查这个问题？线程⽇志⼀般你会看其中的什么 部分？
StackOverFlow异常有没有遇到过？⼀般你猜测会在什么情况下被触 发？如何指定⼀个线程的堆栈⼤⼩？⼀般你们写多少？

## 多线程
1) 什么是线程？
2) 线程和进程有什么区别？
3) 如何在Java中实现线程？
4) 用Runnable还是Thread？
6) Thread 类中的start() 和 run() 方法有什么区别？
7) Java中CyclicBarrier 和 CountDownLatch有什么不同？
8) Java中的volatile 变量是什么？
9) Java中的同步集合与并发集合有什么区别？
10） 如何避免死锁？
11) Java中活锁和死锁有什么区别？
12） Java中synchronized 和 ReentrantLock 有什么不同？
13） Java中ConcurrentHashMap的并发度是什么？
14) 如何在Java中创建Immutable对象？
15） 单例模式的双检锁是什么？
16) 写出3条你遵循的多线程最佳实践
17） 如何避免死锁？


## Netty
1.BIO、NIO和AIO的区别？
2.NIO的组成？
3.Netty的特点？
4.Netty的线程模型？
5.TCP 粘包/拆包的原因及解决方法？
6.了解哪几种序列化协议？
7.如何选择序列化协议？
8.Netty的零拷贝实现？
9.Netty的高性能表现在哪些方面？
10.NIOEventLoopGroup源码？
##Redis
1.Redis与Memorycache的区别？
2.Redis的五种数据结构？
3.渐进式rehash过程？
4.rehash源码？
5.持久化机制
6.reaof源码？
7.事务与事件
8.主从复制
9.启动过程
10.集群
11.Redis的6种数据淘汰策略
12.redis的并发竞争问题？
## Hadoop
1.HDFS的特点？
2.客户端从HDFS中读写数据过程？
3.HDFS的文件目录结构？
4.NameNode的内存结构？
5.NameNode的重启优化？
6.Git的使用？

#==============================
## 体系结构

### 源码分析
####常用设计模式
Proxy代理模式
Factory工厂模式
Singleton单甾校式
Delegate委派模式
Strategy模式
Prototype原型模式
Template模扳模式
#### Spring 5
IOC各器设计原理及高级特性
AOP设计原理
FactoryBean与BeanFactory
Spring事务处悝机制
基于SpringJDBC 手写ORM框架
SpringMVC九大组件
手写实现SpringMVC框架
SpringMVC与Struts2对比分折
Spring5新特性
SpringBoot 2.0 新特性
#### Mybatis
代码自动生成器
关联查询，嵌套查询
缓存便用场景及选择策略
Spring集成下的SqlSession与Mapper
MyBatis的事务
分析MyBatis的动态代理的真止实现
手写Mini版的MyBatis


### 分布式架构
####分布式架构原理
演进过程
如何从单机扩展到分布式
CDN加速静态文件访问
系统监控，容灾，存储动态扩容
架构设计，业务驱动分化
CAP,BASE理论及其应用
####分布式架构策略
网络通信原理
通信协议系列化和反序列化
基于框架的RPC技术WebService/RMI/Hession
Zookeeper在disconf配置中心的应用
Zookeeper实现分布式服务器动态上下线感知
Zookeeper Zab协议及选举机制源码
Dubbo管理中心及监控品后台安装部署
Dubbo 分布式系统架构实战
Dubbo 容错机制及高扩展性分析
####分布式架构中间件
分布式消息通信ActiveMQ/kafka/RabbitMQ
Redis主从复制原理及五磁盘复制分析
图解Redis中AOF和RDB持久化策略原理
MongoDB企业集群解决方案
MongoDB数据分片，转存及恢复策略
OpenRestry部署应用层Nginx及Nginx+Lua
Nginx反向代理及负载均衡
Netty实现高性能通信
Netty实现Dubbo多协议通信支持
Netty无锁串行设计及高并发处理机制
####分布式架构实践
分布式全局ID生成方案
Session跨域共享，企业单点登陆解决方案
分布式事务解决方案
高并发下服务降级，限流实战
分布式下分布式锁的解决方案
分布式下实现分布式定时调度

### 微服务
#### 微框架
与微服务之间的关系
热部署
核心组件：Starter,Actuator,AutoConfigGuration,Cli
集成Mybatis多数据源路由，集成Dubbo,集成Redis
集成Swagger2构建Api管理测试体系
多环境配置动态解析
####SpringCloud
Eureka注册中心
Ribbon集成rest实现负载均衡
Fegion声明式服务调用
Hystrix服务熔断降级方式
Zuul实现微服务网关
Config分布式统一配置中心
Sleuth调用链路跟踪
BUS消息总线
基于Hystrix实现接口实现降级
集成SPringleCloud实现统一整合方案
#### Docker虚拟化
镜像，仓库，容器
Docker File 构建的LNMP环境部署个人博客
Docker Compose 构建的LNMP环境部署个人博客
网络组成，路由互联，Openswitch
基于Swarm构建，Docker集群实战
Kubernetes简介
#### 微服务架构
SOA架构-微服务架构联系和区别
如何设计微服务及其设计原理
Spring Boot流行因素解决什么问题
为何选择Spring Cloud
全局分析Spring Cloud各个组件所解决的问题

###性能优化
#### 理解
性能基准
优化到底是什么？？？
衡量维度
#### JVM调优
什么是运行时数据区
什么是内存模型 JMM
各垃圾回收器使用场景（Throughput/CMS）
理解GC日志
实战MAT分析dump文件
#### Tomcat 调优
运行机制及框架
线程模型分析
系统参数认识和调优
基准测试
#### MySQL 调优
底层B+TREE机制
SQL执行计划详解
索引优化详解
SQL语句优化

### Java工程化
#### Maven
生成JAR,理解Scope生成最精确的Jar
类冲突，包依赖，NoclassdefFoundERROR问题定位及解决
理解Maven的Lisfecycle，Phase,Goal
架构师必备 Maven生成Archetype
Nexus使用，上传，配置
对比Gradle
#### Jenkins 
搭建自动部署环境
集成Maven,git实现自动部署
test\pre\production 多环境发布
Jenkin 多环境配置，权限管理及插件使用
#### Sonar 
使用Sonar进行代码质量管理
代码检查工具 FindBugs/PMD运用
SonarQube代码质量管理平台安装及使用
Jenkins与Sonar集成对代码进行持续性检测
Idea与Sonar集合使用
#### Git
git及其工作原理
常用命令
冲突的引起，解决
git flow规范，团队git使用教程

