# javaweb

- java原生网站
  1. **Servlet**：Servlet是Java EE的一部分，用于处理客户端请求和生成动态内容。Servlet运行在Servlet容器中，如Apache Tomcat或Jetty。
  2. **JSP（JavaServer Pages）**：JSP是一种用于创建动态Web内容的Java技术，允许将Java代码嵌入HTML页面中。JSP页面最终会被编译成Servlet。
  3. **JSTL（JavaServer Pages Standard Tag Library）**：JSTL提供了一组标准标签库，用于简化JSP页面的开发，减少对脚本代码的依赖。
  4. **HTML/CSS/JavaScript**：虽然这些不是Java技术，但它们是Web开发的基础。HTML用于定义页面结构，CSS用于样式设计，JavaScript用于客户端交互。
  5. **JDBC（Java Database Connectivity）**：JDBC是Java用于与数据库交互的标准API。通过JDBC，Java程序可以连接到数据库，执行SQL语句并处理结果。
  6. **XML/JSON**：这些数据格式常用于在Web应用中传输和存储数据。Java提供了处理XML（如JAXB）和JSON（如Jackson）的库。
  7. **Web容器**：如前所述，Servlet和JSP需要运行在Web容器中，如Apache Tomcat或Jetty。这些容器提供了HTTP服务、会话管理、安全等功能。





## IDEA ✔





## vscode ✔





## tomcat





## maven ✔

- 依赖管理工具和项目构建工具

  maven：写配置文件

  gradle：写程序

  



### 背景介绍

- 依赖管理工具

  以前：手动依赖第三方jar、手动处理依赖关系

  现在：中央远程仓库、本地仓库

- 项目构建工具

  以前：原始构建、原始编译

  现在：批量编译、组织文件结构 (代码编译 测试 打包 安装 部署)

  



---

- 构建企业私服

  Nexus

  



### 环境配置

- 版本选择

  maven3.8.8, JDK17, IDEA2022

- 项目结构

  ```bash
  oswin@dt501:/opt/code/java-code/hello-java/code-show/HelloMaven$ tree -L 6
  .
  ├── pom.xml								# maven 依赖配置文件
  ├── src
  │   ├── main
  │   │   ├── java							# java业务代码
  │   │   │   └── com
  │   │   │       └── time1043
  │   │   │           └── Main.java
  │   │   └── resources						# 静态资源 配置文件
  │   └── test
  │       └── java							# java业务代码 测试类
  │
  └── target									# java业务代码 编译后
      ├── classes
      │   └── com
      │       └── time1043
      │           └── Main.class
      └── generated-sources
          └── annotations
  
  ```

  HelloMaven/pom.xml

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <project xmlns="http://maven.apache.org/POM/4.0.0"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
      <modelVersion>4.0.0</modelVersion>
  
      <groupId>com.time1043</groupId>
      <artifactId>HelloMaven</artifactId>
      <version>1.0-SNAPSHOT</version>
  
      <properties>
          <maven.compiler.source>8</maven.compiler.source>
          <maven.compiler.target>8</maven.compiler.target>
          <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
      </properties>
  
  </project>
  ```

  











## Gradle ✔

- build.gradle

  ```
  repositories {
  
      maven { url 'https://maven.aliyun.com/repository/public/' }
      maven { url 'https://maven.aliyun.com/repository/spring/' }
  
      mavenLocal()
      mavenCentral()
  
  }
  ```

  



## lombok







## jdbc (x)















## mybatis











## mybatis-plus





















































































































































































