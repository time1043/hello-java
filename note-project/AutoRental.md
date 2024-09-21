# AutoRental

- 简介

  项目名称：汽车租赁管理系统

  项目简介：主要完成汽车租赁公司*租赁业务*、*财务统计*、*日常保养*、*违章处理*、*权限管理*、*数据初始业务*等功能

  



## 背景介绍

### 需求分析

- 管理员

  数据录入：汽车厂商、汽车型号、车辆信息、车辆颜色、出租类型 (汽车入库)

  日常业务：车辆出租、车辆归还、车辆保养、违章处理(违章通知 信誉等级 黑名单)、客户管理

  财务报表：日租报表、月租报表、数据查询、报表导出、定期邮件

  权限管理：角色管理、权限资源管理、部门管理、用户管理

- 操作人员

  数据录入：...

  日常业务：...

- 财务人员

  财务报表：...

- 用户租客

  租车：登陆注册、个人信息；检索车辆、下单支付押金

  还车：支付账单、违章验查、车刮验查、押金返还

  



### 项目规划

- 项目

  单体架构、微服务架构

  



### 技术选型 ✔

- 前端

  Vue + Element-UI + Vue-Element-Admin 

  Echarts

- 后端

  JDK17 + Springboot3 + MyBatisPlus + SpringSecurity + JWT

  Hutool + FastJson + EasyExcel + Swagger

  OSS + JavaMail

  MySQL8 + Redis

- 工具

  IDEA + vscode

  Git + Maven + Nodejs

  Postman / Apifox
  
  



### 业务流程

> 数据录入：汽车厂商、汽车型号、车辆信息、车辆颜色、出租类型 
>
> 日常业务：车辆出租、车辆归还、车辆保养、违章处理(违章通知 信誉等级 黑名单)、客户管理
>
> 财务报表：日租报表、月租报表、数据查询、报表导出、定期邮件 ~ *不需建表*
>
> 权限管理：角色管理、权限资源管理、部门管理、用户管理



- 数据录入

  汽车厂商：id、厂商名称、厂商拼音

  汽车型号：id、厂商id、型号名称；厂商名称

  车辆信息：id、厂商id、型号id(二级联动)、车辆颜色、车辆排量、行驶里程、上牌日期、车辆照片 (oss)、租金、押金、状态 (未租 已租 维保)

  车辆颜色：配置文件、只读
  
  出租类型：id、出租类型、享受折扣
  
  



---

- 日常业务

- 车辆出租

  -> 车辆列表检索(状态未租) + 高级检索(厂商 型号 上牌年限 颜色等 +模糊匹配 +按钮抽屉) 

  -> 车辆详情页(车牌 厂商 型号) 信息回显 固定信息不可更改 

  -> 客户信息回显确认(姓名 电话 手机号) + 订单信息(可议价租金押金 折扣信息 出租类型 行驶里程 出租时间)

  // 新用户要求填写基本信息 重定向 (身份证 -> 出生日期年龄)

  -> 提交订单(修改车辆状态)

- 车辆归还

  -> 用户已租车辆列表(订单分类 日期排序 车牌检索)

  -> 修改状态为未租 + 记录当前车辆信息(归还时间 行驶里程 发生租金 实付租金) + 跟踪车辆保养信息(5kkm保养一次 应保次数 实保次数)

  -> 计算费用 用户支付 押金返回

- 车辆保养

  -> 所有保养记录列表(记录分类 时间排序 应保>实保则提醒用户)

  -> 线下保养 跟踪车辆保养信息(车辆信息 保养内容 保养时间 费用合计 保养商家 更新保养次数 +异常处理)

- 违章处理
  
  -> 交警队接口 信息同步(违章时间 违章车牌 -> 所关联的订单和客户) 

  -> 通知客户处理(1w不处理 不返押金 拉黑)
  
 - 客户管理
   
    -> 所有用户列表及状态(客户分类 条件查询 用户过往订单)
    
    



---

- 财务报表

- 图表

  日租报表：出租数量、归还数量、订单数量、订单金额、收取押金、收取租金、客户新增 (数据大屏 折线柱状饼状)

  月租报表：...

  数据查询：自定义日期、...

  统计信息：全部车辆、已租车辆、未租车辆、违章车辆、需保养车辆、空置车辆、空置率、客户数量 (1d更新 运营状况)

- 延展

  报表导出：excel

  定期邮件：定时任务、邮箱发送、短信发送



---

- 权限管理 SpringSecurity

  角色管理：管理员、操作人员、财务人员

  权限资源管理：定义菜单访问资源，处理父级权限，与角色进行绑定

  部门管理：业务部、维保部

  用户管理：登陆账号、密码等，并且和角色绑定

  



### 功能梳理





### 架构设计 ✔





## 页面设计 ✔

### 页面 X

### 页面 X





## 库表设计 ✔

- 汇总

  ```sql
  -- ---------------------------------------------------
  -- db: auto_rental  # create drop use  # utf8 ...
  -- ---------------------------------------------------
  drop database if exists auto_rental;
  create database if not exists auto_rental character set utf8;
  use auto_rental;
  
  
  -- ---------------------------------------------------
  -- 汽车入库
  -- ---------------------------------------------------
  # auto_maker '汽车厂商'
  drop table if exists auto_maker;
  create table auto_maker (
      id int auto_increment primary key not null,
      name varchar(255) comment '厂商名称',
      order_letter varchar(255) comment '排序字母',
      deleted tinyint(1) default 0 comment '逻辑删除 0未删除 1已删除'
  ) comment='汽车厂商';
  
  # auto_brand '汽车型号 汽车品牌'
  drop table if exists auto_brand;
  create table auto_brand (
      id int auto_increment primary key not null,
      mid int comment '厂商id',
      brand_name varchar(255) comment '品牌名称',
      deleted tinyint(1) default 0 comment '逻辑删除 0未删除 1已删除'
  ) comment='汽车型号 汽车品牌';
  
  # auto_info '车辆信息 车辆枚举'
  drop table if exists auto_info;
  create table auto_info (
      id int auto_increment primary key not null,
      auto_num varchar(255) comment '车牌号码',
      maker_id int comment '厂商id',
      brand_id int comment '品牌id',
      info_type tinyint(1) comment '车辆类型 0燃油车 1电动车 2混动车',
      color varchar(255) comment '车辆颜色',
      displacement double comment '汽车排量',
      unit varchar(255) comment '排量计量单位',
      registration_date date comment '上牌日期',
      pic varchar(255) comment '车辆图片',
      mileage int comment '行驶里程',
      rent int comment '日租金额',
      deposit int comment '押金',
      status tinyint(1) comment '状态 0未租 1已租 2维保 3自用',
      expected_num int comment '应保次数',
      actual_num int comment '实保次数',
      create_time datetime comment '创建时间',
      update_time datetime comment '修改时间',
      deleted tinyint(1) default 0 comment '逻辑删除 0未删除 1已删除'
  ) comment='车辆信息 车辆枚举';
  
  
  -- ---------------------------------------------------
  -- 权限
  -- ---------------------------------------------------
  # sys_dept '权限 - 部门管理'
  drop table if exists sys_dept;
  create table sys_dept (
      id int auto_increment primary key not null,
      dept_name varchar(255) comment '部门名称',
      phone varchar(255) comment '联系电话',
      address varchar(255) comment '部门地址',
      pid int comment '上级部门id',
      parent_name varchar(255) comment '上级部门名称',
      order_num int comment '排序号',
      deleted tinyint(1) default 0 comment '逻辑删除 0未删除 1已删除'
  ) comment='权限 - 部门管理';
  
  insert into sys_dept (id, dept_name, phone, address, pid, parent_name, order_num, deleted) values
  (1, '沉石汽车租赁上海部', '021-88991111', '上海市徐家汇', 0, '', 1, 0),
  (2, '业务部', '021-88991111', '上海市徐家汇', 1, '沉石汽车租赁上海部', 2, 0),
  (3, '维保部', '021-88991111', '上海市徐家汇', 1, '沉石汽车租赁上海部', 3, 0),
  (4, '沉石汽车租赁北京部', '010-88992222', '北京市海淀区', 0, '', 4, 0),
  (5, '业务部', '021-88991111', '上海市徐家汇', 4, '沉石汽车租赁北京部', 5, 0),
  (6, '维保部', '021-88991111', '上海市徐家汇', 4, '沉石汽车租赁北京部', 6, 0);
  
  
  # sys_role '权限 - 角色管理'
  drop table if exists sys_role;
  create table sys_role (
      id int auto_increment primary key not null,
      role_code varchar(255) comment '角色编码',
      role_name varchar(255) comment '角色名称',
      create_id int comment '创建人id',
      remark varchar(255) comment '备注',
      create_time datetime comment '创建时间',
      update_time datetime comment '修改时间',
      deleted tinyint(1) default 0 comment '逻辑删除 0未删除 1已删除'
  ) comment='权限 - 角色管理';
  
  insert into sys_role (id, role_code, role_name, create_id, remark, create_time, update_time, deleted) values
  (1, '', '超级管理员', NULL, '', NULL, NULL, 0),
  (2, '', '普通管理员', NULL, '', NULL, NULL, 0);
  
  
  # sys_permission '权限 - 权限资源管理 路由'
  drop table if exists sys_permission;
  create table sys_permission (
      id int auto_increment primary key not null,
      permission_label varchar(255) comment '权限名称',
      pid int comment '父权限id',
      parent_label varchar(255) comment '父权限名称',
      permission_code varchar(255) comment '权限标识',
      route_path varchar(255) comment '权限路由地址',
      route_url varchar(255) comment '权限路由路径',
      permission_type tinyint(1) comment '权限类型 0根目录 1子目录 2按钮级别',
      icon varchar(255) comment '图标地址',
      order_num int comment '排序',
      remark varchar(255) comment '备注',
      create_time datetime comment '创建时间',
      update_time datetime comment '修改时间',
      deleted tinyint(1) default 0 comment '逻辑删除 0未删除 1已删除'
  ) comment='权限 - 权限资源管理 路由';
  
  insert into sys_permission (id, permission_label, pid, parent_label, permission_code, route_path, route_url, permission_type, icon, order_num, remark, create_time, update_time, deleted) values
  (1, '权限管理', 0, '菜单', 'sys:manager', '/system', 'system', 0, 'component', NULL, NULL, NULL, NULL, 0),
  (2, '用户管理', 1, '权限管理', 'sys:user', '/userList', 'userList', 1, 'guide', NULL, NULL, NULL, NULL, 0),
  (3, '部门管理', 1, '权限管理', 'sys:dept', '/depatList', 'deptList', 1, 'guide', NULL, NULL, NULL, NULL, 0),
  (4, '角色管理', 1, '权限管理', 'sys:role', '/roleList', 'roleList', 1, 'guide', NULL, NULL, NULL, NULL, 0),
  (5, '新增', 2, '用户管理', 'sys:user:add', '', '', 2, 'add', NULL, NULL, NULL, NULL, 0),
  (6, '删除', 2, '用户管理', 'sys:user:delete', '', '', 2, 'delete', NULL, NULL, NULL, NULL, 0),
  (7, '修改', 2, '用户管理', 'sys:user:edit', '', '', 2, 'edit', NULL, NULL, NULL, NULL, 0),
  (8, '查询', 2, '用户管理', 'sys:user:select', '', '', 2, 'select', NULL, NULL, NULL, NULL, 0),
  (9, '新增', 3, '部门管理', 'sys:dept:add', '', '', 2, 'add', NULL, NULL, NULL, NULL, 0),
  (10, '删除', 3, '部门管理', 'sys:dept:delete', '', '', 2, 'delete', NULL, NULL, NULL, NULL, 0),
  (11, '修改', 3, '部门管理', 'sys:dept:edit', '', '', 2, 'edit', NULL, NULL, NULL, NULL, 0),
  (12, '查询', 3, '部门管理', 'sys:dept:select', '', '', 2, 'select', NULL, NULL, NULL, NULL, 0);
  
  
  # sys_role_permission '权限 - 角色和权限资源绑定管理'
  drop table if exists sys_role_permission;
  create table sys_role_permission (
      role_id int not null,
      permission_id int not null,
      primary key (role_id, permission_id)
  ) comment='权限 - 角色和权限资源绑定管理';
  
  insert into sys_role_permission (role_id, permission_id) values
  (1, 1),
  (1, 2),
  (1, 3),
  (1, 4),
  (1, 5),
  (1, 6),
  (1, 7),
  (1, 8),
  (1, 9),
  (1, 10),
  (1, 11),
  (1, 12),
  (2, 1),
  (2, 2),
  (2, 5),
  (2, 6),
  (2, 7),
  (2, 8);
  
  
  # sys_user '权限 - 用户管理 员工'
  drop table if exists sys_user;
  create table sys_user (
      id int auto_increment primary key not null,
      user_account varchar(255) comment '用户账户',
      nickname varchar(255) comment '用户昵称',
      realname varchar(255) comment '用户正式姓名',
      password varchar(255) comment '用户密码',
      is_account_non_expired tinyint(1) comment '账户是否过期',
      is_account_non_locked tinyint(1) comment '账户是否被锁定',
      is_credentials_non_expired tinyint(1) comment '密码是否过期',
      is_enabled tinyint(1) comment '账户是否可用',
      dept_id int comment '所属部门id',
      dept_name varchar(255) comment '所属部门名称',
      gender tinyint(1) comment '性别 1男 0女',
      phone varchar(255) comment '联系电话',
      email varchar(255) comment '邮箱',
      avatar varchar(255) comment '头像',
      is_admin tinyint(1) comment '是否管理员',
      create_time datetime comment '创建时间',
      update_time datetime comment '修改时间',
      deleted tinyint(1) default 0 comment '逻辑删除 0未删除 1已删除'
  ) comment='权限 - 用户管理 员工';
  
  insert into sys_user (id, user_account, nickname, realname, password, is_account_non_expired, is_account_non_locked, is_credentials_non_expired, is_enabled, dept_id, dept_name, gender, phone, email, avatar, is_admin, create_time, update_time, deleted) values
  (1, 'admin', 'admin', 'admin', 'adminpasswd', 1, 1, 1, 1, NULL, NULL, 1, NULL, NULL, NULL, 1, NULL, NULL, 0),
  (2, 'luren', '路人甲', '张三', 'zhangsanpasswd', 1, 1, 1, 1, NULL, NULL, 1, '13380809797', 'zhangsan@gmail.com', 'https://miro.medium.com/v2/resize:fit:640/format:webp/1*4j2A9niz0eq-mRaCPUffpg.png', 1, NULL, NULL, 0);
  
  
  # sys_user_role '权限 - 用户和角色绑定管理'
  drop table if exists sys_user_role;
  create table sys_user_role (
      user_id int not null,
      role_id int not null,
      primary key (user_id, role_id)
  ) comment='权限 - 用户和角色绑定管理';
  
  insert into sys_user_role (user_id, role_id) values
  (1, 1),
  (2, 2);
  
  
  -- ---------------------------------------------------
  -- 租赁业务
  -- ---------------------------------------------------
  
  # busi_customer '客户信息'
  drop table if exists busi_customer;
  create table busi_customer (
      id int auto_increment primary key not null,
      name varchar(255) comment '客户姓名',
      age int comment '客户年龄',
      phone varchar(255) comment '手机号码',
      birthday date comment '出生日期',
      idcard varchar(255) comment '身份证号码',
      status tinyint(1) comment '客户状态 0黑名单 1白名单'
      create_time datetime comment '创建时间',
      update_time datetime comment '修改时间',
      deleted tinyint(1) default 0 comment '逻辑删除 0未删除 1已删除'
  ) comment='客户信息';
  
  
  # busi_maintain '车辆保养'
  drop table if exists busi_maintain;
  create table busi_maintain (
      id int auto_increment primary key not null,
      auto_id int comment '车辆id',
      maintain_time date comment '维保时间',
      location varchar(255) comment '维保地点',
      item varchar(255) comment '维保项目',
      cost int comment '维保费用',
      create_time datetime comment '创建时间',
      update_time datetime comment '修改时间',
      deleted tinyint(1) default 0 comment '逻辑删除 0未删除 1已删除'
  ) comment='车辆保养';
  
  
  # busi_violation '违章管理'
  drop table if exists busi_violation;
  create table busi_violation (
      id int auto_increment primary key not null,
      auto_id int comment '车辆id',
      violation_time datetime comment '违章时间',
      reason varchar(255) comment '违章事由',
      location varchar(255) comment '违章地点',
      deduct_points int comment '扣分',
      fine int comment '罚款',
      status tinyint(1) comment '是否处理 0未处理 1已处理',
      create_time datetime comment '创建时间',
      update_time datetime comment '修改时间',
      deleted tinyint(1) default 0 comment '逻辑删除 0未删除 1已删除'
  ) comment='违章管理';
  
  
  # busi_rental_type '出租类型'
  drop table if exists busi_rental_type;
  create table busi_rental_type (
      id int auto_increment primary key not null,
      type_name varchar(255) comment '出租类型名称',
      type_discout double comment '享受折扣',
      remark varchar(255) comment '备注',
      create_time datetime comment '创建时间',
      update_time datetime comment '修改时间',
      deleted tinyint(1) default 0 comment '逻辑删除 0未删除 1已删除'
  ) comment='出租类型';
  
  
  # busi_order '业务订单 出租 + 归还'
  drop table if exists busi_order;
  create table busi_order (
      id int auto_increment primary key not null,
      order_num varchar(255) comment '订单编号',
      auto_id int comment '车辆id',
      customer_id int comment '客户id',
      rental_time datetime comment '出租时间',
      type_id int comment '出租类型',
      rent int comment '日租金额',
      deposit int comment '押金',
      mileage int comment '起租里程',
      return_time datetime comment '归还时间',
      return_mileage int comment '归还里程',
      rent_payable int comment '应付租金',
      rent_actual int comment '实付租金',
      deposit_return tinyint(1) comment '押金返回状态 0未返还 1已返还',
      create_time datetime comment '创建时间',
      update_time datetime comment '修改时间',
      deleted tinyint(1) default 0 comment '逻辑删除 0未删除 1已删除'
  ) comment='业务订单 出租 + 归还';
  
  ```
  
  



### model 汽车入库

- 汽车入库

  ```sql
  # db: reserve_vehicle  # create drop use  # utf8 ...
  
  -- ---------------------------------------------------
  -- 汽车入库
  -- ---------------------------------------------------
  # auto_maker '汽车厂商'
  id int auto_ pk not null '厂商id'
  name varchar(255) '厂商名称'
  order_letter varchar(255) '排序字母'
  deleted tinyint(1) default 0 '逻辑删除 0未删除 1已删除'
  
  
  # auto_brand '汽车型号 汽车品牌'
  id int auto_ pk not null '型号id 品牌id'
  mid int '厂商id'
  brand_name varchar(255) '品牌名称'
  deleted tinyint(1) default 0 '逻辑删除 0未删除 1已删除'
  
  
  # auto_info '车辆信息 车辆枚举'
  id int auto_ pk not null '车辆信息id'
  auto_num varchar(255) '车牌号码'
  maker_id int '厂商id'
  brand_id int '品牌id'
  info_type tinyint(1) '车辆类型 0燃油车 1电动车 2混动车'
  color varchar(255) '车辆颜色'
  displacement double '汽车排量'untitled
  unit varchar(255) '排量计量单位'
  registration_date '上牌日期'
  pic varchar(255) '车辆图片'
  mileage int '行驶里程'
  rent int '日租金额'
  deposit int '押金'
  status tinyint(1) '状态 0未租 1已租 2维保 3自用'
  expected_num int '应保次数'
  actual_num int '实保次数'
  create_time datetime '创建时间'
  update_time datetime '修改时间'
  deleted tinyint(1) default 0 '逻辑删除 0未删除 1已删除'
  
  ```

  



### model 权限管理

- 权限管理

  ```sql
  -- ---------------------------------------------------
  -- 权限
  -- ---------------------------------------------------
  # sys_dept '权限 - 部门管理'
  id int auto_ pk not null '部门id'
  dept_name varchar(255) '部门名称'
  phone varchar(255) '联系电话'
  address varchar(255) '部门地址'
  pid int '上级部门id'
  parent_name varchar(255) '上级部门名称'
  order_num int '排序号'
  deleted tinyint(1) default 0 '逻辑删除 0未删除 1已删除'
  
  1, 沉石汽车租赁上海部, 021-88991111, 上海市徐家汇, 0, , 1, 0
  2, 业务部, 021-88991111, 上海市徐家汇, 1, 沉石汽车租赁上海部, 2, 0
  3, 维保部, 021-88991111, 上海市徐家汇, 1, 沉石汽车租赁上海部, 3, 0
  4, 沉石汽车租赁北京部, 010-88992222, 北京市海淀区, 0, , 4, 0
  5, 业务部, 021-88991111, 上海市徐家汇, 4, 沉石汽车租赁北京部, 5, 0
  6, 维保部, 021-88991111, 上海市徐家汇, 4, 沉石汽车租赁北京部, 6, 0
  
  
  # sys_role '权限 - 角色管理'
  id int auto_ pk not null '角色id'
  role_code varchar(255) '角色编码'
  role_name varchar(255) '角色名称'
  create_id int '创建人id'
  remark varchar(255) '备注'
  create_time datetime '创建时间'
  update_time datetime '修改时间'
  deleted tinyint(1) default 0 '逻辑删除 0未删除 1已删除'
  
  1, , 超级管理员, , , , , 0
  2, , 普通管理员, , , , , 0
  
  
  # sys_permission '权限 - 权限资源管理 路由'
  id int auto_ pk not null '权限资源id'
  permission_label varchar(255) '权限名称'
  pid int '父权限id'
  parent_label varchar(255) '父权限名称'
  permission_code varchar(255) '权限标识'
  route_path varchar(255) '权限路由地址'
  route_url varchar(255) '权限路由路径'
  permission_type tinyint(1) '权限类型 0根目录 1子目录 2按钮级别'
  icon varchar(255) '图标地址'
  order_num int '排序'
  remark varchar(255) '备注'
  create_time datetime '创建时间'
  update_time datetime '修改时间'
  deleted tinyint(1) default 0 '逻辑删除 0未删除 1已删除'
  
  1, 权限管理, 0, 菜单, sys:manager, /system, system, /system/system, 0, component, , , , , 0
  2, 用户管理, 1, 权限管理, sys:user, /userList, userList, /system/user/userList, 1, guide, , , , , 0
  3, 部门管理, 1, 权限管理, sys:dept, /depatList, deptList, /system/dept/depatList, 1, guide, , , , , 0
  4, 角色管理, 1, 权限管理, sys:role, /roleList, roleList, /system/role/roleList, 1, guide, , , , , 0
  5, 新增, 2, 用户管理, sys:user:add, , , , 2, add, , , , , 0
  6, 删除, 2, 用户管理, sys:user:delete, , , , 2, delete, , , , , 0
  7, 修改, 2, 用户管理, sys:user:edit, , , , 2, edit, , , , , 0
  8, 查询, 2, 用户管理, sys:user:select, , , , 2, select, , , , , 0
  9, 新增, 3, 部门管理, sys:dept:add, , , , 2, add, , , , , 0
  10, 删除, 3, 部门管理, sys:dept:delete, , , , 2, delete, , , , , 0
  11, 修改, 3, 部门管理, sys:dept:edit, , , , 2, edit, , , , , 0
  12, 查询, 3, 部门管理, sys:dept:select, , , , 2, select, , , , , 0
  
  
  # sys_role_permission '权限 - 角色和权限资源绑定管理'
  role_id int not null pk '角色id'
  permission_id int null pk '权限资源id'
  
  1, 1
  1, 2
  1, 3
  1, 4
  1, 5
  1, 6
  1, 7
  1, 8
  1, 9
  1, 10
  1, 11
  1, 12
  2, 1
  2, 2
  2, 5
  2, 6
  2, 7
  2, 8
  
  
  # sys_user '权限 - 用户管理'
  id int auto_ pk not null '用户id'
  user_account varchar(255) '用户账户'
  nickname varchar(255) '用户昵称'
  realname varchar(255) '用户正式姓名'
  password varchar(255) '用户密码'
  is_account_non_expired tinyint(1) '账户是否过期'
  is_account_non_locked tinyint(1) '账户是否被锁定'
  is_credentials_non_expired tinyint(1) '密码是否过期'
  is_enabled tinyint(1) '账户是否可用'
  dept_id int '所属部门id'
  dept_name varchar(255) '所属部门名称'
  gender tinyint(1) '性别 1男 0女'
  phone varchar(255) '联系电话'
  email varchar(255) '邮箱'
  avatar varchar(255) '头像'
  is_admin tinyint(1) '是否管理员'
  create_time datetime '创建时间'
  update_time datetime '修改时间'
  deleted tinyint(1) default 0 '逻辑删除 0未删除 1已删除'
  
  1, admin, admin, admin, adminpasswd, 1, 1, 1, 1, , , 1, , , , 1, , , 0
  2, luren, 路人甲, 张三, zhangsanpasswd, 1, 1, 1, 1, , , 1, 13380809797, zhangsan@gmail.com, https://miro.medium.com/v2/resize:fit:640/format:webp/1*4j2A9niz0eq-mRaCPUffpg.png, 1, , , 0
  
  
  # sys_user_role '权限 - 用户和角色绑定管理'
  user_id int not null '用户id'
  role_id int not_null '角色id'
  
  1, 1
  2, 2
  
  ```
  
  



### model 租赁业务

- 租赁业务

  ```sql
  -- ---------------------------------------------------
  -- 租赁业务
  -- ---------------------------------------------------
  
  # busi_customer '客户信息'
  id int auto_ pk not null '客户id'
  name varchar(255) '客户姓名'
  age int '客户年龄'
  phone varchar(255) '手机号码'
  birthday date '出生日期'
  idcard varchar(255) '身份证号码'
  status tinyint(1) '客户状态 0黑名单 1白名单'
  create_time datetime '创建时间'
  update_time datetime '修改时间'
  deleted tinyint(1) default 0 '逻辑删除 0未删除 1已删除'
  
  
  # busi_maintain '车辆保养'
  id int auto_ pk not null '保养记录id'
  auto_id int '车辆id'
  maintain_time date '维保时间'
  location varchar(255) '维保地点'
  item varchar(255) '维保项目'
  cost int '维保费用'
  create_time datetime '创建时间'
  update_time datetime '修改时间'
  deleted tinyint(1) default 0 '逻辑删除 0未删除 1已删除'
  
  
  # busi_violation '违章管理'
  id int auto_ pk not null '违章记录id'
  auto_id int '车辆id'
  violation_time datetime '违章时间'
  reason varchar(255) '违章事由'
  location varchar(255) '违章地点'
  deduct_points int '扣分'
  fine int '罚款'
  status tinyint(1) '是否处理 0未处理 1已处理'
  create_time datetime '创建时间'
  update_time datetime '修改时间'
  deleted tinyint(1) default 0 '逻辑删除 0未删除 1已删除'
  
  
  # busi_rental_type '出租类型'
  id int auto_ pk not null '出租类型id'
  type_name varchar(255) '出租类型名称'
  type_discout double '享受折扣'
  remark varchar(255) '备注'
  create_time datetime '创建时间'
  update_time datetime '修改时间'
  deleted tinyint(1) default 0 '逻辑删除 0未删除 1已删除'
  
  
  # busi_order '业务订单 出租 + 归还'
  id int auto_ pk not null 'id'
  order_num varchar(255) '订单编号'
  auto_id int '车辆id'
  customer_id int '客户id'
  rental_time datetime '出租时间'
  type_id int '出租类型'
  rent int '日租金额'
  deposit int '押金'
  mileage int '起租里程'
  return_time datetime '归还时间'
  return_mileage int '归还里程'
  rent_payable int '应付租金'
  rent_actual int '实付租金'
  deposit_return tingint(1) '押金返回状态 0未返还 1已返还'
  create_time datetime '创建时间'
  update_time datetime '修改时间'
  deleted tinyint(1) default 0 '逻辑删除 0未删除 1已删除'
  
  ```
  
  





## 接口数据 ✔

### Interface X

### Interface X





## 前端初始化

### 新建项目

### 依赖配置

### 路由配置





## 后端初始化

### 新建项目

- 新建Maven项目

  ```
  
  ```

  



### 依赖文件

- 基础信息

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <project xmlns="http://maven.apache.org/POM/4.0.0"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
      <modelVersion>4.0.0</modelVersion>
  
      <groupId>com.time1043</groupId>
      <artifactId>auto-rental-backend</artifactId>
      <version>1.0-SNAPSHOT</version>
  
      <!-- springboot3 单体架构 -->
      <parent>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-parent</artifactId>
          <version>3.0.10</version>
      </parent>
  
      <!-- 依赖版本：jdk17 ... -->
      <properties>
          <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
          <maven.compiler.source>17</maven.compiler.source>
          <maven.compiler.target>17</maven.compiler.target>
          <mybaits-plus.version>3.5.5</mybaits-plus.version>
          <mybatis-plus-generator.version>3.5.5</mybatis-plus-generator.version>
          <fastjson.version>2.0.47</fastjson.version>
          <mysql.version>8.0.30</mysql.version>
          <swagger.version>3.0.0</swagger.version>
          <hutool.version>5.8.26</hutool.version>
          <jwt.version>0.12.5</jwt.version>
          <aliyun-oss.version>3.17.4</aliyun-oss.version>
          <jaxb-api.version>2.3.1</jaxb-api.version>
          <activation.version>1.1.1</activation.version>
          <easyexcel.version>3.3.3</easyexcel.version>
      </properties>
      
      <dependencies>
          
          
          
      </dependencies>    
      
  </project>
  ```

- 具体依赖

  web框架

  ```xml
          <!-- spring -->
          <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-web</artifactId>
          </dependency>
          <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-test</artifactId>
          </dependency>
          <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-devtools</artifactId>
          </dependency>
          <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-freemarker</artifactId>
          </dependency>
          <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-security</artifactId>
          </dependency>
  
          <dependency>
              <groupId>io.jsonwebtoken</groupId>
              <artifactId>jjwt</artifactId>
              <version>${jwt.version}</version>
          </dependency>
          <dependency>
              <groupId>org.projectlombok</groupId>
              <artifactId>lombok</artifactId>
          </dependency>
  ```
  
  中间件
  
  ```xml
          <!-- middleware: mysql + mybatis-plus -->
          <dependency>
              <groupId>mysql</groupId>
              <artifactId>mysql-connector-java</artifactId>
              <version>${mysql.version}</version>
          </dependency>
          <dependency>
              <groupId>com.baomidou</groupId>
              <artifactId>mybatis-plus-boot-starter</artifactId>
              <version>${mybaits-plus.version}</version>
          </dependency>
          <dependency>
              <groupId>com.baomidou</groupId>
              <artifactId>mybatis-plus-generator</artifactId>
              <version>${mybatis-plus-generator.version}</version>
          </dependency>
  
          <!-- middleware: redis -->
          <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-data-redis</artifactId>
          </dependency>
  ```
  
  工具
  
  ```xml
          <!-- tool -->
          <dependency>
              <groupId>com.alibaba</groupId>
              <artifactId>fastjson</artifactId>
              <version>${fastjson.version}</version>
          </dependency>
          <dependency>
              <groupId>cn.hutool</groupId>
              <artifactId>hutool-all</artifactId>
              <version>${hutool.version}</version>
          </dependency>
          <dependency>
              <groupId>com.alibaba</groupId>
              <artifactId>easyexcel</artifactId>
              <version>${easyexcel.version}</version>
          </dependency>
  
          <dependency>
              <groupId>io.springfox</groupId>
              <artifactId>springfox-boot-starter</artifactId>
              <version>${swagger.version}</version>
          </dependency>
  ```
  
  三方服务
  
  ```xml
          <!-- service: aliyun oss -->
          <dependency>
              <groupId>com.aliyun.oss</groupId>
              <artifactId>aliyun-sdk-oss</artifactId>
              <version>${aliyun-oss.version}</version>
          </dependency>
          <dependency>
              <groupId>javax.xml.bind</groupId>
              <artifactId>jaxb-api</artifactId>
              <version>${jaxb-api.version}</version>
          </dependency>
          <dependency>
              <groupId>javax.xml.bind</groupId>
              <artifactId>jaxb-api</artifactId>
              <version>${jaxb-api.version}</version>
          </dependency>
          <dependency>
              <groupId>javax.activation</groupId>
              <artifactId>activation</artifactId>
              <version>${activation.version}</version>
          </dependency>
  
  ```
  
  



### 配置文件

- 





## 前端页面 XXX

### 页面和布局

### 组件 X

### 组件 X

### 前端请求





## 后端接口 XXX

### 逻辑梳理

### 代码生成 MyBatisX



### 数据库访问层 (mapper) ✔

### 业务逻辑层 (service) ✔

### 接口访问层 (controller) ✔



### 数据模型 (model)

### 自定义异常

### 测试接口





## 项目部署











































