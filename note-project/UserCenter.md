# User Center

- 鱼皮项目

  1. 用户中心：学完框架新手入门、完整项目开发流程、管理系统CRUD
  2. 伙伴匹配系统：Redis、事务、并非编程、大数据推荐思想
  3. API开放平台：模板封装、架构设计、sdk开发、API签名认证、Dubbo RPC、Gateway微服务网关
  3. RPC框架：轮子开发、网络协议设计、序列化、Etcd注册中心、Vert.x服务器、动态代理、SPI机制、负载均衡、服务重试容错机制、注解驱动启动器
  4. 聚合搜索平台：实践爬虫、ElasticStack、设计模式、数据同步、JMeter压力测试
  5. 智能BI项目：异步化、线程池、RabbitMQ、AIGC prompt
  6. OJ在线判题系统：模板封装、多种设计模式、单体项目微服务改造、Linux远程开发、Docker代码沙箱、Java安全控制
  7. 代码生成器共享平台：命令行开发、模板引擎、Vert.x、设计模式、对象存储、性能优化、存储优化、系统设计、分布式任务调度系统
  8. AI答题应用平台：React跨端小程序、后端分库分表、分布式锁、缓存、幂等设计、设计模式、RxJava响应式编程、SSE实时推送、线程池隔离

- 企业项目流程

  需求分析 -> 概要设计详细设计 -> 技术选型 

  -> 初始化/引入需要的技术 -> 写demo -> 写代码实现业务逻辑 (优化代码：复用代码、提取公共逻辑、提取常量)

  -> 单元测试 -> 代码提交代码评审 -> 部署发布

- IDEA 插件

  `MyBatis-X`

  `generate all setter`, `auto filling java call arguments`





## 背景介绍

- Reference code

  [user-center-backend-public (github)](https://github.com/liyupi/user-center-backend-public), 

- Reference dev

  [nodejs org](https://nodejs.org/en), [nodejs LTS (cn)](https://nodejs.cn/#google_vignette); 

  [Ant Design Pro (docs)](https://pro.ant.design/zh-CN/docs/getting-started), [Ant Design Pro (dashboard)](https://preview.pro.ant.design/dashboard/analysis); 
  
  [umi docs](https://umijs.org/docs/guides/getting-started), 
  
  [mybatisplus docs](https://baomidou.com/pages/226c21/), 





### 需求分析

- 市场应用：微信、支付宝账号登录多个平台

- 用户体系：登录注册、用户管理(仅管理员可见)、用户校验(仅星球用户)

  **登录注册**：对用户的增加查询

  **用户管理**：对用户的查询修改 (管理员)

  **用户校验**：合法性 (爬虫、excel处理、定时任务)

  



### 项目规划

- 开发流程

  初始化项目：

  - 前端初始化：初始化、引入一些组件、框架介绍、项目瘦身
  - 后端初始化：准备环境(mysql)、引入框架整合框架

  **登陆注册**：前端、后端

  - 后端：注册和登录、service controller和接口测试
  - 前端：个性化、删代码；对接后端接口、获取用户的登录态
  - 注销功能

  **用户管理**：前端、后端

  - 后端：用户管理接口 (用户查询 状态更改)
  - 前端：开发；前端代码瘦身
  - 优化：注册失败返回-1不友好

  **校验用户**：星球用户





### 技术选型 ✔

- 前端

  HTML + CSS + JavaScript 

  Nodejs18.18 + React框架

  Ant Design Pro项目模板 + Ant Design组件库

  Umi框架 + Umi Request请求库

  正向和反向代理

- 后端

  JDK8

  Spring + SpringMVC + SpringBoot

  MyBatis + MyBatisPlus 数据库访问框架

  MySQL数据库

  jUnit单元测试库

- 部署

  服务器：Linux单机部署 + Nginx web服务器

  平台：Docker容器 + 容器托管平台

  



---

- Spring

  `Spring`：依赖注入框架、帮你管理java对象、集成一些内容

  `SpringMVC`：web框架、提供接口访问、restful接口等能力

  `MyBatisPlus`：操作数据库、持久层框架、再封装即不用写sql

  `SpringBoot`：不用自己管理spring配置、不用自己整合各种框架、快速启动快速集成项目 ✔

- Ant Design

  `Ant Design`: 组件库 (封装了react)

  [`Ant Design Procomponents`](https://procomponents.ant.design/components/form): 面向业务 定制化 (封装了ant design)

  `Ant Design Pro`: 后台管理系统 (由ant design、react、ant design procomponents及其他库组成)
  
  ![](res/UserCenter/AntDesignPreview.png)
  
  



### 业务流程

### 功能梳理

### 架构设计 ✔





## 页面设计 ✔

### 登陆注册

### 用户管理





## 库表设计 ✔

- 库表设计

  有哪些表(model)？表中有哪些字段？字段的类型？

  数据库字段添加索引？表与表之间的关联？

  



### 用户

- user

  id(主键)、userAccount、username、avatarUrl、gender、userPassword、phone、email、userStatus；

  createTime、updateTime、isDelete；

  ```sql
  # drop database user_center;
  # create database user_center;
  # use user_center;
  
  -- 用户表
  drop table if exists user;
  create table user
  (
      id           bigint auto_increment primary key comment '唯一ID',
      username     varchar(255) comment '用户名',
      userAccount  varchar(255) comment '登录账号',
      avatarUrl    varchar(1024) comment '用户头像URL',
      gender       tinyint comment '用户性别',
      userPassword varchar(512) not null comment '用户密码',
      phone        varchar(128) comment '用户电话号码',
      email        varchar(512) comment '用户电子邮件',
      userStatus   int       default 0 comment '用户状态0为正常',
      -- 三项
      createTime   timestamp default current_timestamp comment '创建时间',
      updateTime   timestamp default current_timestamp on update current_timestamp comment '更新时间',
      isDelete     tinyint   default 0 comment '逻辑删除标志',
      -- 补充
      userRole     int       default 0 comment '用户角色 0是普通用户 1是管理员'
  ) comment '用户';
  
  insert into user (username, useraccount, avatarurl, gender, userpassword, phone, email, userstatus,
                    createtime, updatetime, isdelete,
                    userrole)
  values ('oswin', 'oswin501',
          'https://miro.medium.com/v2/resize:fit:640/format:webp/1*4j2A9niz0eq-mRaCPUffpg.png', 1, 'oswin123456',
          '15534340089', 'oswin501@gmail.com', 0,
          current_timestamp, current_timestamp, 0,
          1),
         ('yupi', 'yupi501',
          'https://pic6.sucaisucai.com/01/88/01388996_2.jpg', 1, 'yupi123456',
          '15534340089', 'yupi501@gmail.com', 0,
          current_timestamp, current_timestamp, 0,
          0);
  
  ```
  
  



## 接口数据 ✔

### 用户模块

- 注册登陆

  ```bash
  # #################################################
  # /user/register (POST,body-json)
  # #################################################
  
  # request
  {
      "userAccount": "lisi501",
      "userPassword": "lisi123456",
      "checkPassword": "lisi123456"
  }
  
  # response
  3
  
  # #################################################
  # /user/login (POST,body-json)
  # #################################################
  
  # request
  {
    "userAccount": "yupi501",
    "userPassword": "12345678"
  }
  
  # response
  {
      "id": 2,
      "username": "yupi",
      "userAccount": "yupi501",
      "avatarUrl": "https://pic6.sucaisucai.com/01/88/01388996_2.jpg",
      "gender": 1,
      "userPassword": null,
      "phone": "15534340089",
      "email": "yupi501@gmail.com",
      "userStatus": 0,
      "createTime": "2024-08-25T11:56:41.000+00:00",
      "updateTime": null,
      "isDelete": null,
      "userRole": 0
  }
  
  
  # #################################################
  # /user/current (GET)
  # #################################################
  
  # request (cookies)
  JSESSIONID=438C760521E1CB7B427BE40E151C8365
  
  # response
  {
      "id": 1,
      "username": "oswin",
      "userAccount": "oswin501",
      "avatarUrl": "https://miro.medium.com/v2/resize:fit:640/format:webp/1*4j2A9niz0eq-mRaCPUffpg.png",
      "gender": 1,
      "userPassword": null,
      "phone": "15534340089",
      "email": "oswin501@gmail.com",
      "userStatus": 0,
      "createTime": "2024-08-25T11:56:41.000+00:00",
      "updateTime": null,
      "isDelete": null,
      "userRole": 1
  }
  
  ```

- 用户管理

  ```bash
  # #################################################
  # /user/search (GET,admin)
  # #################################################
  
  # request 
  username=yupi
  # request (cookies)
  JSESSIONID=438C760521E1CB7B427BE40E151C8365
  
  # response
  [
      {
          "id": 2,
          "username": "yupi",
          "userAccount": "yupi501",
          "avatarUrl": "https://pic6.sucaisucai.com/01/88/01388996_2.jpg",
          "gender": 1,
          "userPassword": null,
          "phone": "15534340089",
          "email": "yupi501@gmail.com",
          "userStatus": 0,
          "createTime": "2024-08-25T11:56:41.000+00:00",
          "updateTime": null,
          "isDelete": null,
          "userRole": 0
      }
  ]
  
  
  # #################################################
  # /user/delete (POST,body-json,admin)
  # #################################################
  
  # request 
  2
  # request (cookies)
  JSESSIONID=438C760521E1CB7B427BE40E151C8365
  
  # response
  true
  
  ```

  



## 前端初始化

- 前端流水线 (布局 + 页面 + 组件)

  src/main.ts -> src/router/index.ts -> src/App.vue

  -> src/views/IndexView.vue -> src/views/OverView.vue

  -> src/components/...

  



### 新建项目

- 环境要求

  [nodejs LTS (cn)](https://nodejs.cn/#google_vignette); [Ant Design Pro](https://pro.ant.design/zh-CN/docs/getting-started), [umi docs](https://umijs.org/docs/guides/getting-started)
  
- 新建项目

  ```bash
  # 拉取脚手架代码 创建项目
  npm i @ant-design/pro-cli -g
  pro create user-center-admin  # simple
  cd user-center-admin && npm install
  
  # 安装umi ui
  npm install @umijs/preset-ui --save-dev
  npm run start
  
  
  # yarn
  yarn global add @ant-design/pro-cli
  pro create web-ts --typescript
  cd web-ts && yarn install
  
  yarn add @umijs/preset-ui -D
  yarn start
  
  ```
  
  项目结构
  
  ```
  Ant Design Pro (umi框架)
  - app.tsx 项目全局入口文件，定义了整个项目中使用的公共数据 (如用户信息)
    首次访问页面或刷新页面，进入app.tsx执行getInitialState方法，该方法的返回值就是全局可用的状态值
  - access.ts 控制用户的访问权限
  
  
  # 目录结构
  ├── config                   # umi 配置，包含路由，构建等配置
  ├── mock                     # 本地模拟数据
  ├── public
  │   └── favicon.png          # Favicon
  ├── src
  │   ├── assets               # 本地静态资源
  │   ├── components           # 业务通用组件
  │   ├── e2e                  # 集成测试用例
  │   ├── layouts              # 通用布局
  │   ├── models               # 全局 dva model
  │   ├── pages                # 业务页面入口和常用模板
  │   ├── services             # 后台接口服务
  │   ├── utils                # 工具库
  │   ├── locales              # 国际化资源
  │   ├── global.less          # 全局样式
  │   └── global.ts            # 全局 JS
  ├── tests                    # 测试工具
  ├── README.md
  └── package.json			 # 项目依赖
  
  
  # 页面代码
  src
  ├── components
  └── pages
      ├── Welcome        // 路由组件下不应该再包含其他路由组件，基于这个约定就能清楚的区分路由组件和非路由组件了
      |   ├── components // 对于复杂的页面可以再自己做更深层次的组织，但建议不要超过三层
      |   ├── Form.tsx
      |   ├── index.tsx  // 页面组件的代码
      |   └── index.less // 页面样式
      ├── Order          // 路由组件下不应该再包含其他路由组件，基于这个约定就能清楚的区分路由组件和非路由组件了
      |   ├── index.tsx
      |   └── index.less
      ├── User
      |   ├── components // group 下公用的组件集合
      |   ├── Login      // group 下的页面 Login
      |   ├── Register   // group 下的页面 Register
      |   └── util.ts    // 这里可以有一些共用方法之类，不做推荐和约束，看业务场景自行做组织
      └── *              // 其它页面组件代码
  
  
  ```
  
- 项目瘦身

  ```bash
  cd 
  
  npm run i18n-remove  # 国际化
  rm -rf src/locales  # 国际化
  # src/components/index.ts  # SelectLang
  
  # 若前面有使用umi-ui添加页面，可把该页面删除
  # config/routes.ts删除对应的路由规则
  
  rm -rf src/services/swagger/  # 定义了一系列后台接口程序
  rm -rf config/oneapi.json  # 定义了整个项目用到的一些接口
  # config/config.ts  # 删除oneapi配置
  
  rm -rf src/e2e  # 一系列测试流程
  rm -rf tests  # 和测试相关的
  rm -rf jest.config.js  # 测试相关的配置文件
  rm -rf playwright.config.ts  # 自动化测试工具，帮你在火狐或谷歌自动测试，不用真实地打开浏览器就能测试
  
  ```
  
- 新建文件

  ```bash
  mkdir src/constants
  touch src/constants/index.ts
  
  ```

  



### 依赖配置

### 路由配置





## 后端初始化

- 项目规范

  `controller ` (接收请求 封装接口给前端调用), `service` (业务逻辑), `mapper` (数据库操作)

  `model.domain` (实体类对象), 全局变量常量, `contant` (枚举常量)

  `utils`(工具类), 配置信息,

- 速查流程

  `Springboot Application` 加注解 @MapperScan

  `application.yml` 配置信息

  `pom.xml` 导入依赖baomidou 整合mybatis-plus

  `MyBatisX` 操作：MyBatisX-Generator -> module path ->  annotation (MyBatis-Plus3) options (comment lombok actualColumn Model)  template (mybatis-plus3)

  



### 新建项目 vscode

- vscode插件

  `Extension Pack for Java`, `Spring Boot Extension Pack`
  
- 创建流程

  create java project 
  
  -> springboot -> maven project 
  
  -> maven -> 
  
- 补充设置

  settings -> proxy: http://1270.0.1:7890

  settings -> spring-init (java version, default language, service url)

  settings -> java.configuration.maven -> /opt/module/maven/conf/settings.xml (user settings, global settings)

  settings -> maven.executable.path -> /opt/module/maven/bin/mvn

  ```json
  {
    "files.autoSave": "onFocusChange",
    "editor.wordWrap": "on",
    "editor.formatOnSave": true,
    "editor.lineHeight": 20,
    "editor.defaultFormatter": "esbenp.prettier-vscode",
    "editor.codeActionsOnSave": {
      "source.fixAll.eslint": true,
      "source.organizeImports": true
    },
    "notebook.defaultFormatter": "esbenp.prettier-vscode",
    "workbench.colorTheme": "1984 - Cyberpunk",
    "workbench.iconTheme": "vscode-icons",
    "java.configuration.maven.userSettings": "/opt/module/maven/conf/settings.xml",
    "java.configuration.maven.globalSettings": "/opt/module/maven/conf/settings.xml",
    "maven.executable.path": "/opt/module/maven/bin/mvn",
    "maven.settingsFile": "/opt/module/maven/conf/settings.xml",
    "spring.initializr.defaultJavaVersion": "8",
    "spring.initializr.defaultLanguage": "Java",
    "spring.initializr.defaultPackaging": "JAR",
    "spring.initializr.defaultGroupId": "com.time1043",
    "spring.initializr.serviceUrl": [
      "https://start.aliyun.com/"
      // "https://start.spring.io"
    ]
  }
  
  ```

  



### 新建项目

- 集成开发工具：IDEA, webStorm (不需要其他)

- 三种方式初始化后端

  github上搜索springboot模板，拉现成的模板 (不推荐)

  SpringBoot 官方模板生成器：https://start.spring.io/

  集成开发工具IDEA (推荐✔)

- spring初始java8项目

  spring官方不再支持java8 https://start.spring.io

  用阿里云提供的脚手架 https://start.aliyun.com/

- 选择配置

  springboot2.6.4 

  springboot DevTools (热更新), spring configuration processor, spring web (web访问),

  mysql, mybatis framework (数据访问层),

  lombok (自动生成get set方法), junit (默认集成)

- 框架整合  [mybatisplus docs](https://baomidou.com/pages/226c21/)

  ```bash
  mv src/main/resources/application.properties src/main/resources/application.yml
  
  rm -rf src/main/resources/static/  # 前后端不分离
  cd src/main/java/com/time1043/usercenterbackend/ 
  mkdir controller service mapper model contant utils  # springboot项目结构
  
  # mybatis-x
  # localhost:8080/api/index.html
  
  ```
  
  



---

- mysql

  ```bash
  # docker mysql8
  mkdir -p /opt/data/mysql/data /opt/data/mysql/conf /opt/data/mysql/init  # rz -E
  
  docker run -d \
    --name mysql \
    -p 3306:3306 \
    -e TZ=Asia/Shanghai \
    -e MYSQL_ROOT_PASSWORD=123456 \
    -v /opt/data/mysql/data:/var/lib/mysql \
    -v /opt/data/mysql/conf:/etc/mysql/conf.d \
    -v /opt/data/mysql/init:/docker-entrypoint-initdb.d \
    mysql:8
  
  docker exec -it mysql bash
  mysql -uroot -p123456
  
  
  # docker mysql57
  docker create --name mysql57 \
    -e MYSQL_ROOT_PASSWORD=123456 \
    -p 3307:3306 \
    mysql:5.7
  
  docker start mysql57
  docker exec -it mysql57 bash
  mysql -uroot -p123456
  
  ```
  
  



### mybatis-plus demo

- 框架整合  [mybatisplus docs](https://baomidou.com/pages/226c21/)

  ```bash
  # #################################################
  # 
  # #################################################
  
  # 依赖 pom.xml
  # 配置文件 application.yml
  
  # 启动类 Application.java  @MapperScan
  # model mapper
  # 测试类
  
  ```
  
  pom.xml
  
  ```xml
          <!-- mybatis-plus baomidou -->
          <dependency>
              <groupId>com.baomidou</groupId>
              <artifactId>mybatis-plus-boot-starter</artifactId>
              <version>3.5.1</version>
          </dependency>
  
          <!-- https://mvnrepository.com/artifact/junit/junit -->
          <dependency>
              <groupId>junit</groupId>
              <artifactId>junit</artifactId>
              <version>4.13.2</version>
              <scope>test</scope>
          </dependency>
  
  ```
  
- 编码

  src/main/java/com/time1043/usercenterbackend/UserCenterBackendApplication.java
  
  ```java
  package com.time1043.usercenterbackend;
  
  import org.mybatis.spring.annotation.MapperScan;
  import org.springframework.boot.SpringApplication;
  import org.springframework.boot.autoconfigure.SpringBootApplication;
  
  @SpringBootApplication
  @MapperScan("com.time1043.usercenterbackend.mapper")
  public class UserCenterBackendApplication {
  
      public static void main(String[] args) {
          SpringApplication.run(UserCenterBackendApplication.class, args);
      }
  
  }
  
  ```

  src/main/java/com/time1043/usercenterbackend/model/User.java
  
  ```java
  package com.time1043.usercenterbackend.model;
  
  import lombok.Data;
  
  @Data
  public class User {
      private Long id;
      private String name;
      private Integer age;
      private String email;
  }
  ```

  src/main/java/com/time1043/usercenterbackend/mapper/UserMapper.java
  
  ```java
  package com.time1043.usercenterbackend.mapper;
  
  import com.baomidou.mybatisplus.core.mapper.BaseMapper;
  import com.time1043.usercenterbackend.model.User;
  
  public interface UserMapper extends BaseMapper<User> {
  
  }
  ```

- 测试
  
  src/test/java/com/time1043/usercenterbackend/UserCenterBackendApplicationTests.java (与启动类名一致)
  
  ```java
  package com.time1043.usercenterbackend;
  
  import com.time1043.usercenterbackend.mapper.UserMapper;
  import com.time1043.usercenterbackend.model.User;
  import org.junit.Assert;
  import org.junit.jupiter.api.Test;
  import org.springframework.boot.test.context.SpringBootTest;
  
  import javax.annotation.Resource;
  import java.util.List;
  
  @SpringBootTest  // @SpringBootTest({"UserCenterBackendApplication"})
  class UserCenterBackendApplicationTests {
      @Resource
      private UserMapper userMapper;
  
      @Test
      void contextLoads() {
          System.out.println(("----- selectAll method test ------"));
          List<User> userList = userMapper.selectList(null);
          Assert.assertEquals(5, userList.size());
          userList.forEach(System.out::println);
      }
  }
  
  ```

  src/test/java/com/time1043/usercenterbackend/SampleTest.java (与启动类名不一致)
  
  添加@RunWith 
  
  ```java
  package com.time1043.usercenterbackend;
  
  import com.time1043.usercenterbackend.mapper.UserMapper;
  import com.time1043.usercenterbackend.model.User;
  import org.junit.Assert;
  import org.junit.Test;
  import org.junit.runner.RunWith;
  import org.springframework.boot.test.context.SpringBootTest;
  import org.springframework.test.context.junit4.SpringRunner;
  
  import javax.annotation.Resource;
  import java.util.List;
  
  @SpringBootTest
  @RunWith(SpringRunner.class)  // junit与springboot联系
  public class SampleTest {
      @Resource
      private UserMapper userMapper;
  
      @Test  // org.junit.Test
      public void testSelect() {
          System.out.println(("----- selectAll method test ------"));
          List<User> userList = userMapper.selectList(null);
          Assert.assertEquals(5, userList.size());
          userList.forEach(System.out::println);
      }
  }
  
  ```
  
  不用@RunWith 
  
  ```java
  package com.time1043.usercenterbackend;
  
  import com.time1043.usercenterbackend.mapper.UserMapper;
  import com.time1043.usercenterbackend.model.User;
  import org.junit.Assert;
  import org.junit.jupiter.api.Test;
  import org.springframework.boot.test.context.SpringBootTest;
  
  import javax.annotation.Resource;
  import java.util.List;
  
  @SpringBootTest
  public class SampleTest {
      @Resource
      private UserMapper userMapper;
  
      @Test  // org.junit.jupiter.api.Test
      public void testSelect() {
          System.out.println(("----- selectAll method test ------"));
          List<User> userList = userMapper.selectList(null);
          Assert.assertEquals(5, userList.size());
          userList.forEach(System.out::println);
      }
  }
  
  ```
  
  




### 项目配置 ✔

- 逻辑删除配置 [MyBatis-Plus docs](https://baomidou.com/pages/6b03c5/#使用方法)

  src\main\resources\application.yml

  ```yml
  spring:
    application:
      name: user-center-backend
    # Database configuration
    datasource:
      driver-class-name: com.mysql.jdbc.Driver
      url: jdbc:mysql://localhost:3306/user_center
      username: root
      password: 123456
    session:
      timeout: 86400 # 1天的session过期时间
  server:
    port: 8080
    servlet:
      context-path: /api # 指定接口全局api前缀
  
  mybatis-plus:
    configuration:
      map-underscore-to-camel-case: false  # 字段转换
    global-config:
      db-config:
        logic-delete-field: isDelete # 全局逻辑删除的实体字段名(since 3.3.0,配置后可以忽略不配置步骤2)
        logic-delete-value: 1 # 逻辑已删除值(默认为 1)
        logic-not-delete-value: 0 # 逻辑未删除值(默认为 0)
  
  ```
  
  



### 依赖配置 ✔

- pom.xml

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
      <modelVersion>4.0.0</modelVersion>
      <groupId>com.time1043</groupId>
      <artifactId>user-center-backend</artifactId>
      <version>0.0.1-SNAPSHOT</version>
      <name>user-center-backend</name>
      <description>Demo project for Spring Boot</description>
      <properties>
          <java.version>8</java.version>
          <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
          <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
          <spring-boot.version>2.6.13</spring-boot.version>
      </properties>
      <dependencies>
  
          <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-actuator</artifactId>
          </dependency>
  
          <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-web</artifactId>
          </dependency>
          <dependency>
              <groupId>org.mybatis.spring.boot</groupId>
              <artifactId>mybatis-spring-boot-starter</artifactId>
              <version>2.2.2</version>
          </dependency>
  
          <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-devtools</artifactId>
              <scope>runtime</scope>
              <optional>true</optional>
          </dependency>
          <dependency>
              <groupId>com.mysql</groupId>
              <artifactId>mysql-connector-j</artifactId>
              <scope>runtime</scope>
          </dependency>
          <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-configuration-processor</artifactId>
              <optional>true</optional>
          </dependency>
          <dependency>
              <groupId>org.projectlombok</groupId>
              <artifactId>lombok</artifactId>
              <optional>true</optional>
          </dependency>
          <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-test</artifactId>
              <scope>test</scope>
          </dependency>
  
          <!-- mybatis-plus baomidou -->
          <dependency>
              <groupId>com.baomidou</groupId>
              <artifactId>mybatis-plus-boot-starter</artifactId>
              <version>3.5.1</version>
          </dependency>
  
          <!-- https://mvnrepository.com/artifact/junit/junit -->
          <dependency>
              <groupId>junit</groupId>
              <artifactId>junit</artifactId>
              <version>4.13.2</version>
              <scope>test</scope>
          </dependency>
  
      </dependencies>
      <dependencyManagement>
          <dependencies>
              <dependency>
                  <groupId>org.springframework.boot</groupId>
                  <artifactId>spring-boot-dependencies</artifactId>
                  <version>${spring-boot.version}</version>
                  <type>pom</type>
                  <scope>import</scope>
              </dependency>
          </dependencies>
      </dependencyManagement>
  
      <build>
          <plugins>
              <plugin>
                  <groupId>org.apache.maven.plugins</groupId>
                  <artifactId>maven-compiler-plugin</artifactId>
                  <version>3.8.1</version>
                  <configuration>
                      <source>8</source>
                      <target>8</target>
                      <encoding>UTF-8</encoding>
                  </configuration>
              </plugin>
              <plugin>
                  <groupId>org.springframework.boot</groupId>
                  <artifactId>spring-boot-maven-plugin</artifactId>
                  <version>${spring-boot.version}</version>
                  <configuration>
                      <mainClass>com.time1043.usercenterbackend.UserCenterBackendApplication</mainClass>
                      <skip>true</skip>
                  </configuration>
                  <executions>
                      <execution>
                          <id>repackage</id>
                          <goals>
                              <goal>repackage</goal>
                          </goals>
                      </execution>
                  </executions>
              </plugin>
          </plugins>
      </build>
  
  </project>
  
  ```
  
  



## 前端页面：用户模块

### 页面：用户登陆

- src/pages/User/Login/index.tsx

  ```tsx
  
  ```

  



### 组件：footer

- src/components/Footer/index.tsx

  ```tsx
  
  ```

  



### 组件 X





### 前后端交互

- 前端向后端发请求

  `ajax`前端请求后端

  `axios`封装了ajax

  `request`是ant design项目的再一次封装

  



### 前后端交互：用户登陆

- 登陆页面 src/pages/User/Login/index.tsx

  onFinish

  ```tsx
            onFinish={async (values) => {
              await handleSubmit(values as API.LoginParams);
            }}
  ```

  handleSubmit

  ```tsx
    const handleSubmit = async (values: API.LoginParams) => {
      try {
        // 登录
        const msg = await login({
  ```

  API.LoginParams (src/services/ant-design-pro/typings.d.ts)

  ```typescript
    type LoginParams = {
      userAccount?: string;
      userPassword?: string;
      autoLogin?: boolean;
      type?: string;
    };
  
    type LoginResult = {
      status?: string;
      type?: string;
      currentAuthority?: string;
    };
  ```

- 登陆请求

  login (src/services/ant-design-pro/api.ts)

  ```typescript
  /** 登录接口 POST /api/login/account */
  export async function login(body: API.LoginParams, options?: { [key: string]: any }) {
    return request<API.LoginResult>('/api/login/account', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      data: body,
      ...(options || {}),
    });
  }
  ```

  request (src/.umi/plugin-request/request.ts) https://umijs.org/docs/max/request#request
  
  ```typescript
  const request: IRequest = (url: string, opts: any = { method: 'GET' }) => {
  
  
  function useRequest(service: any, options: any = {}) {
    return useUmiRequest(service, {
      formatResult: result => result?.data,
      requestMethod: (requestOptions: any) => {
          
  
  // request 方法 opts 参数的接口
  interface IRequestOptions extends AxiosRequestConfig {
    skipErrorHandler?: boolean;
    requestInterceptors?: IRequestInterceptorTuple[];
    responseInterceptors?: IResponseInterceptorTuple[];
    [key: string]: any;
  }
  
  
  export interface RequestConfig<T = any> extends AxiosRequestConfig {
    errorConfig?: {
      errorHandler?: IErrorHandler;
      errorThrower?: ( res: T ) => void
    };
    requestInterceptors?: IRequestInterceptorTuple[];
    responseInterceptors?: IResponseInterceptorTuple[];
  }
  
  let requestInstance: AxiosInstance;
  let config: RequestConfig;
  const getConfig = (): RequestConfig => {
    if (config) return config;
    config = getPluginManager().applyPlugins({
      key: 'request',
      type: ApplyPluginsType.modify,
      initialValue: {},
    });
    return config;
  };
  
  const getRequestInstance = (): AxiosInstance => {
    if (requestInstance) return requestInstance;
    const config = getConfig();
    requestInstance = axios.create(config);
  
      
  ```
  
  
  
  ```
  
  ```
  
  





## 后端接口：用户模块

### 逻辑梳理：注册登陆

- 注册接口

  接收参数：用户账户、密码、校验密码、星球编号

  请求类型：POST

  请求体：JSON

  返回值：user.id (-1)

- 注册逻辑

  1. 用户在前端输入**账号和密码**、以及校验码 (todo)
  2. **校验**用户的账户、密码、检验密码是否符合要求  (非空检验、账户不小于4位、密码不小于8位、账户不能重复、账户不含特殊字符、密码和校验密码相同)
  3. 对密码进行**加密** (千万不要明文存储数据库)
  4. 向**数据库**插入用户数据

- 为什么前端校验了，后端还要校验？

  前端只能拦住正常用户，拦不住攻击，用户可以绕过前端向后端接口发请求

  



---

- 登录接口

  接收参数：用户账户、密码

  请求类型：POST

  请求体：JSON

  返回值：用户信息 (脱敏)

- 登录逻辑

  1. 检验用户账户和密码是否**合法** (非空、账户不小于4、密码不小于8、账户不含特殊字符)
  2. 校验**密码**是否输入正确，要和数据库密码密文对比
  3. 记录用户的**登录态** (session)，存到服务器上 (后端springBoot封装的服务器 tomcat)
  4. 返回用户信息 (**脱敏**)

- 如何知道是哪个用户登录？

  session, cookie

- 单机登录 (后续改造 redis分布式 第三方登录)

  



---

- 注销接口

- 注销逻辑

  删除登录态信息

  



### 逻辑梳理：用户管理

- 用户管理 CRUD

  查询用户：GET

  删除用户：POST





### 代码生成 MyBatisX

- MyBatisX

  domain：实体对象
  
  mapper：操作数据库的对象
  
  mapper.xml：定义了mapper对象和数据库的关联，可以自己写SQL
  
  service：包含常用CRUD
  
  serviceImpl：具体实现service
  
- MyBatisX 操作

  MyBatisX-Generator 

  -> module path 

  ->  annotation (MyBatis-Plus3) options (comment lombok actualColumn Model)  template (mybatis-plus3)

- 测试接口

  src/test/java/com/time1043/usercenterbackend/service/UserServiceTest.java (UserService 创建测试类 自动生成)

  ```java
  package com.time1043.usercenterbackend.service;
  
  import com.time1043.usercenterbackend.model.domain.User;
  import org.junit.jupiter.api.Assertions;
  import org.junit.jupiter.api.Test;
  import org.springframework.boot.test.context.SpringBootTest;
  
  import javax.annotation.Resource;
  
  /**
   * 用户服务测试类
   *
   * @author oswin
   */
  @SpringBootTest
  class UserServiceTest {
      @Resource
      private UserService userService;
  
      /**
       * 测试添加用户 (数据库操作层)
       */
      @Test
      void testAddUser() {
          // plugin: generateAllSetter
          User user = new User();
  
          //user.setId(0L);
          user.setUsername("zhangsan");
          user.setUserAccount("zhangsan501");
          user.setAvatarUrl("https://pic.616pic.com/ys_b_img/00/10/94/oGVCVUi1tc.jpg");
          user.setGender(0);
          user.setUserPassword("zhangsan123456");
          user.setPhone("13390905588");
          user.setEmail("zhangsan@qq.com");
          //user.setUserStatus(0);
          //user.setCreateTime(new Date());
          //user.setUpdateTime(new Date());
          //user.setIsDelete(0);
          //user.setUserRole(0);
  
          boolean result = userService.save(user);
          System.out.println(user.getId());
          Assertions.assertTrue(result);
      }
  }
  
  ```
  
  



### 数据库访问层 (mapper) ✔





### 业务逻辑层 (service) ✔

- src/main/java/com/time1043/usercenterbackend/service/UserService.java

  ```java
  package com.time1043.usercenterbackend.service;
  
  import com.time1043.usercenterbackend.model.domain.User;
  import com.baomidou.mybatisplus.extension.service.IService;
  
  import javax.servlet.http.HttpServletRequest;
  
  /**
   * @author oswin
   * @description 针对表【user(用户)】的数据库操作Service
   * @createDate 2024-08-25 13:56:11
   */
  public interface UserService extends IService<User> {
      /**
       * 用户注册
       *
       * @param userAccount   用户账号
       * @param userPassword  用户密码
       * @param checkPassword 确认密码
       * @return 注册成功返回用户id，失败返回-1
       */
      long userRegister(String userAccount, String userPassword, String checkPassword);
  
      /**
       * 用户登录
       *
       * @param userAccount  用户账号
       * @param userPassword 用户密码
       * @param request      http请求
       * @return 返回脱敏后的用户信息
       */
      User userLogin(String userAccount, String userPassword, HttpServletRequest request);
  
      /**
       * 用户注销
       *
       * @param request http请求
       * @return 返回用户id，返回-1表示失败
       */
      int userLogout(HttpServletRequest request);
  
      /**
       * 获取脱敏的用户信息
       *
       * @param originalUser 原始用户信息
       * @return 脱敏后的用户信息
       */
      User getSafetyUser(User originalUser);
  }
  
  ```

- src/main/java/com/time1043/usercenterbackend/service/impl/UserServiceImpl.java

  注册：密码加密加盐、用户信息脱敏

  登陆注销：用户登陆态信息 (cookie ~ HttpServletRequest)

  ```java
  package com.time1043.usercenterbackend.service.impl;
  
  import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
  import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
  import com.time1043.usercenterbackend.mapper.UserMapper;
  import com.time1043.usercenterbackend.model.domain.User;
  import com.time1043.usercenterbackend.service.UserService;
  import lombok.extern.slf4j.Slf4j;
  import org.apache.commons.lang3.StringUtils;
  import org.springframework.stereotype.Service;
  import org.springframework.util.DigestUtils;
  
  import javax.annotation.Resource;
  import javax.servlet.http.HttpServletRequest;
  import java.util.regex.Matcher;
  import java.util.regex.Pattern;
  
  import static com.time1043.usercenterbackend.contant.UserConstant.USER_LOGIN_STATE;
  
  /**
   * @author oswin
   * @description 针对表【user(用户)】的数据库操作Service实现
   * @createDate 2024-08-25 13:56:11
   */
  @Service
  @Slf4j
  public class UserServiceImpl extends ServiceImpl<UserMapper, User>
          implements UserService {
  
      /**
       * 注入UserMapper
       */
      @Resource
      private UserMapper userMapper;
  
      /**
       * 密码加盐
       */
      private static final String SALT = "oswin";
  
      /**
       * 用户注册
       *
       * @param userAccount   用户账号
       * @param userPassword  用户密码
       * @param checkPassword 确认密码
       * @return 用户id 注册失败返回-1
       */
      @Override
      public long userRegister(String userAccount, String userPassword, String checkPassword) {
          // #################################################
          // 校验
          // #################################################
          // 空字符串 位数过少
          if (StringUtils.isAllBlank(userAccount, userPassword, checkPassword)) {
              return -1;  // TODO 后续封装自定义异常类
          }
          if (userAccount.length() < 4) {
              return -1;
          }
          if (userPassword.length() < 8 || checkPassword.length() < 8) {
              return -1;
          }
          // 账户不能包含特殊字符 正则
          String validPattern = "^[a-zA-Z0-9]+$";
          Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
          if (!matcher.find()) {
              return -1;
          }
          // 密码和确认密码一致
          if (!userPassword.equals(checkPassword)) {
              return -1;
          }
          // 账户不能重复 数据库查询 (性能消耗的放后面!!)
          QueryWrapper<User> queryWrapper = new QueryWrapper<>();
          queryWrapper.eq("userAccount", userAccount);
          Long count = userMapper.selectCount(queryWrapper);  // this.count(queryWrapper);
          if (count > 0) {
              return -1;
          }
  
          // #################################################
          // 加密
          // #################################################
          String encryptPassword = DigestUtils.md5DigestAsHex((userPassword + SALT).getBytes());
  
          // #################################################
          // 插入数据库
          // #################################################
          User user = new User();
          user.setUserAccount(userAccount);
          user.setUserPassword(encryptPassword);
          boolean saveResult = this.save(user);  // this.  service.
          if (!saveResult) {
              return -1;
          }
  
          return user.getId();
      }
  
      /**
       * 用户登录
       *
       * @param userAccount  用户账号
       * @param userPassword 用户密码
       * @param request      http请求
       * @return 登录成功返回用户对象，登录失败返回null
       */
      @Override
      public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
          // #################################################
          // 校验
          // #################################################
          // 空字符串 位数过少
          if (StringUtils.isAllBlank(userAccount, userPassword)) {
              return null;
          }
          if (userAccount.length() < 4) {
              return null;
          }
          if (userPassword.length() < 8) {
              return null;
          }
          // 账户不能包含特殊字符 正则
          String validPattern = "^[a-zA-Z0-9]+$";
          Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
          if (!matcher.find()) {
              return null;
          }
  
          // #################################################
          // 加密
          // #################################################
          String encryptPassword = DigestUtils.md5DigestAsHex((userPassword + SALT).getBytes());
  
          // #################################################
          // 查询数据库
          // #################################################
          QueryWrapper<User> queryWrapper = new QueryWrapper<>();
          queryWrapper.eq("userAccount", userAccount);
          queryWrapper.eq("userPassword", encryptPassword);
          User user = userMapper.selectOne(queryWrapper);
          // 用户名或密码错误 or 用户不存在
          if (user == null) {
              log.info("user login failed, userAccount: {}, userPassword: {}", userAccount, userPassword);
              return null;
          }
          // TODO 限流策略
  
          // #################################################
          // 放行登陆
          // #################################################
          // 脱敏处理
          User safetyUser = getSafetyUser(user);
          // 记录用户登录态 session cookie
          request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);  // k v
          return safetyUser;
      }
  
      /**
       * 用户注销
       *
       * @param request http请求
       * @return 返回1表示注销成功
       */
      @Override
      public int userLogout(HttpServletRequest request) {
          // 移出登陆态 session cookie
          request.getSession().removeAttribute(USER_LOGIN_STATE);
          return 1;
      }
  
      /**
       * 获取脱敏用户信息
       *
       * @param originalUser 原始用户信息
       * @return 脱敏后的用户信息
       */
      @Override
      public User getSafetyUser(User originalUser) {
          // 空处理
          if (originalUser == null) {
              return null;
          }
  
          User safetyUser = new User();
          safetyUser.setId(originalUser.getId());
          safetyUser.setUsername(originalUser.getUsername());
          safetyUser.setUserAccount(originalUser.getUserAccount());
          safetyUser.setAvatarUrl(originalUser.getAvatarUrl());
          safetyUser.setGender(originalUser.getGender());
          safetyUser.setPhone(originalUser.getPhone());
          safetyUser.setEmail(originalUser.getEmail());
          safetyUser.setUserRole(originalUser.getUserRole());
          safetyUser.setUserStatus(originalUser.getUserStatus());
          safetyUser.setCreateTime(originalUser.getCreateTime());
          return safetyUser;
      }
  }
  
  ```

- 测试接口

  ```java
  package com.time1043.usercenterbackend.service;
  
  import com.time1043.usercenterbackend.model.domain.User;
  import org.junit.jupiter.api.Assertions;
  import org.junit.jupiter.api.Test;
  import org.springframework.boot.test.context.SpringBootTest;
  
  import javax.annotation.Resource;
  
  /**
   * 用户服务测试类
   *
   * @author oswin
   */
  @SpringBootTest
  class UserServiceTest {
      
      @Resource
      private UserService userService;
  
      /**
       * 测试加密密码
       */
      @Test
      void testDigestPassword() {
          // spring 加密的工具类
          String newPassword = DigestUtils.md5DigestAsHex(("123456" + "salt").getBytes());
          System.out.println(newPassword);  // 207acd61a3c1bd506d7e9a4535359f8a
      }
      
      /**
       * 测试登陆逻辑 (业务层)
       */
      @Test
      void testRegister() {
          String userAccount = "yingzhu2";
          String checkPassword = "123456";
  
          // 非空检验
          String userPassword = "";
          long result = userService.userRegister(userAccount, userPassword, checkPassword);
          Assertions.assertEquals(-1, result);
  
          // 账户长度不小于4
          userAccount = "yz";
          result = userService.userRegister(userAccount, userPassword, checkPassword);
          Assertions.assertEquals(-1, result);
  
          // 密码长度不小于8
          userAccount = "yingzhu2";
          userPassword = "123456";
          result = userService.userRegister(userAccount, userPassword, checkPassword);
          Assertions.assertEquals(-1, result);
  
          // 账户不包含特殊字符
          userAccount = "yingzhu2#";
          userPassword = "12345678";
          result = userService.userRegister(userAccount, userPassword, checkPassword);
          Assertions.assertEquals(-1, result);
  
          // 账户名不能重复
          userAccount = "yingzhu";
          userPassword = "12345678";
          result = userService.userRegister(userAccount, userPassword, checkPassword);
          Assertions.assertEquals(-1, result);
  
          // 密码和校验密码不一致
          userAccount = "yingzhu2";
          userPassword = "12345678";
          checkPassword = "123456789";
          result = userService.userRegister(userAccount, userPassword, checkPassword);
          Assertions.assertEquals(-1, result);
  
          // 成功的
          userAccount = "yingzhu2";
          userPassword = "12345678";
          checkPassword = "12345678";
          result = userService.userRegister(userAccount, userPassword, checkPassword);
          Assertions.assertTrue(result > 0);
      }
  }
  
  ```

  



### 接口访问层 (controller) ✔

- src/main/java/com/time1043/usercenterbackend/controller/UserController.java

  封装注册请求对象、封装登陆请求对象

  ```java
  package com.time1043.usercenterbackend.controller;
  
  import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
  import com.time1043.usercenterbackend.model.domain.User;
  import com.time1043.usercenterbackend.model.domain.request.UserLoginRequest;
  import com.time1043.usercenterbackend.model.domain.request.UserRegisterRequest;
  import com.time1043.usercenterbackend.service.UserService;
  import org.apache.commons.lang3.StringUtils;
  import org.springframework.web.bind.annotation.*;
  
  import javax.annotation.Resource;
  import javax.servlet.http.HttpServletRequest;
  import java.util.ArrayList;
  import java.util.List;
  import java.util.stream.Collectors;
  
  import static com.time1043.usercenterbackend.contant.UserConstant.ADMIN_ROLE;
  import static com.time1043.usercenterbackend.contant.UserConstant.USER_LOGIN_STATE;
  
  /**
   * 用户接口
   *
   * @author oswin
   */
  @RestController  // restful风格api  返回值默认json
  @RequestMapping("/user")
  public class UserController {
  
      @Resource
      private UserService userService;
  
      @PostMapping("/register")
      public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
          if (userRegisterRequest == null) {
              return null;
          }
  
          String userAccount = userRegisterRequest.getUserAccount();
          String userPassword = userRegisterRequest.getUserPassword();
          String checkPassword = userRegisterRequest.getCheckPassword();
  
          // 粗略校验 (controller层对请求参数的校验 没有涉及业务逻辑)
          if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
              return null;
          }
          return userService.userRegister(userAccount, userPassword, checkPassword);
      }
  
      @PostMapping("/login")
      public User userLogin(
              @RequestBody UserLoginRequest userLoginRequest,
              HttpServletRequest request
      ) {
          if (userLoginRequest == null) {
              return null;
          }
  
          String userAccount = userLoginRequest.getUserAccount();
          String userPassword = userLoginRequest.getUserPassword();
  
          if (StringUtils.isAnyBlank(userAccount, userPassword)) {
              return null;
          }
          return userService.userLogin(userAccount, userPassword, request);
      }
  
      @GetMapping("/current")
      public User getCurrentUser(HttpServletRequest request) {
          // 从请求中拿到登录态 cookie
          Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
          User currentUser = (User) userObj;
          if (currentUser == null) {
              return null;
          }
  
          // 数据库查询 (如果有积分信息)
          Long userId = currentUser.getId();
          User user = userService.getById(userId);
          // TODO 用户状态可能处于封号状态
          return userService.getSafetyUser(user);  // 脱敏
      }
  
      @GetMapping("/search")
      public List<User> searchUser(String username, HttpServletRequest request) {
          // 非管理员禁止搜索
          if (!isAdmin(request)) {
              return new ArrayList<>();
          }
  
          QueryWrapper<User> queryWrapper = new QueryWrapper<>();
          if (StringUtils.isNotBlank(username)) {
              queryWrapper.like("username", username);
          }
          List<User> userList = userService.list(queryWrapper);
          return userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
      }
  
      @PostMapping("/delete")
      public boolean deleteUser(@RequestBody long id, HttpServletRequest request) {
          // 非管理员禁止删除
          if (!isAdmin(request)) {
              return false;
          }
          if (id <= 0) {
              return false;
          }
  
          return userService.removeById(id);  // mybatis-plus BaseMapper
      }
  
      /**
       * 鉴权：是否是管理员
       *
       * @param request 请求
       * @return 是否是管理员
       */
      private boolean isAdmin(HttpServletRequest request) {
          Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
          User currentUser = (User) userObj;
          return currentUser != null && currentUser.getUserRole() == ADMIN_ROLE;
      }
  }
  
  ```

- 测试接口

  IDEA: tools -> http client -> create request

  ```bash
  ### 用户注册
  POST http://localhost:8080/api/user/register
  Content-Type: application/json
  
  {
    "userAccount": "lisi502",
    "userPassword": "lisi123456",
    "checkPassword": "lisi123456"
  }
  
  
  ### 用户登陆 (逻辑删除)
  POST http://localhost:8080/api/user/login
  Content-Type: application/json
  
  {
    "userAccount": "oswin501",
    "userPassword": "12345678"
  }
  
  ```

  



### 数据模型 (model)

- src/main/java/com/time1043/usercenterbackend/model/domain/User.java

  ```java
      /**
       * 逻辑删除标志
       */
      @TableLogic
      private Integer isDelete;
  
  ```

  src/main/java/com/time1043/usercenterbackend/model/domain/request/UserRegisterRequest.java
  
  ```java
  package com.time1043.usercenterbackend.model.domain.request;
  
  import lombok.Data;
  
  import java.io.Serializable;
  
  /**
   * 用户注册请求参数类
   *
   * @author oswin
   */
  @Data
  public class UserRegisterRequest implements Serializable {
      private static final long serialVersionUID = -5828182211292243190L;
  
      private String userAccount;
      private String userPassword;
      private String checkPassword;
  }
  
  ```
  
  src/main/java/com/time1043/usercenterbackend/model/domain/request/UserLoginRequest.java
  
  ```java
  package com.time1043.usercenterbackend.model.domain.request;
  
  import lombok.Data;
  
  import java.io.Serializable;
  
  /**
   * 用户登录请求参数类
   *
   * @author oswin
   */
  @Data
  public class UserLoginRequest implements Serializable {
      private static final long serialVersionUID = -6452848799120833655L;
  
      private String userAccount;
      private String userPassword;
  }
  
  ```
  
  



### 常量类 (constrant)

- src/main/java/com/time1043/usercenterbackend/contant/UserConstant.java

  ```java
  package com.time1043.usercenterbackend.contant;
  
  /**
   * 用户常量类
   *
   * @author oswin
   */
  public interface UserConstant {
      /**
       * 用户登录态键
       */
      String USER_LOGIN_STATE = "userLoginState";
  
      /**
       * 默认角色
       */
      int DEFAULT_ROLE = 0;
  
      /**
       * 管理员角色
       */
      int ADMIN_ROLE = 1;
  }
  
  ```

  



### 自定义异常













## 项目优化







## 项目部署








































































































































































