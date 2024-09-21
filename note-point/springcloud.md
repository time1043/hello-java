# springcloud

- Reference- dev

  [SpringCloud](https://spring.io/projects/spring-cloud), 

  [SpringCloudAlibaba(spring)](https://spring.io/projects/spring-cloud-alibaba), [SpringCloudAlibaba(alibaba)](https://sca.aliyun.com/), [SpringCloudAlibaba github](https://github.com/alibaba/spring-cloud-alibaba), 

- Reference - blog

  



## 背景介绍

### 基本概念

#### 微服务

- 单体架构

  分析 -> 编码 --> 测试 -> 部署 --> 优化

  优点：开发简单 (单个应用程序 单分代码工程 单个数据库)；测试直观、部署简单(单文件不涉及服务调用)；横向扩展(apache服务器负载均衡)

  缺点：多功能代码强耦合(难以维护)、开发速度慢(梳理复杂系统)、代码合并冲突；启动构建测试耗时、故障影响范围大；后期技术升级困难(团队长期一套版本)

- 微服务架构

  项目功能拆分成多个**微服务**，并通过网络通信实现**分布式部署**，输出完整的业务能力

  优点：代码自由轻量低耦合、独立开发独立部署、团队并行协作、故障可控

  缺点：技术复杂(跨进程通信需要分布式)、业务调用链路长、异步的数据不一致(分布式事务)；网络性能开销大、网络通信的延迟错误和数据泄露(高可用 高性能 高并发 高可靠)

  



#### SpringCloud

- SpringCloud

  定位：一系列框架的有序集合，屏蔽了复杂实现原理，简化分布式系统的开发(SpringBoot风格)  

  组件：服务发现和注册、配置中心、消息总线、负载均衡、断路器、数据监控

- SpringCloudAlibaba

  定位：二次开发、一站式解决方案

  扩展：无侵入服务治理(全链路灰度 无损上下线 离群实例删除等)、Nacos注册配置中心、云原生网关

  



### 版本选择

- 版本选择

  JDK17, IDEA2023, Spring6, SpringBoot3, SpringCloudAlibaba









































































