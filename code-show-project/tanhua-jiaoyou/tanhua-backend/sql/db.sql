-- ----------------------------
-- table structure for tb_user
-- ----------------------------
drop table if exists `tb_user`;
create table `tb_user`
(
    `id`       bigint(20) not null auto_increment,
    `mobile`   varchar(11) default null comment '手机号',
    `password` varchar(32) default null comment '密码，需要加密',
    `created`  datetime    default null,
    `updated`  datetime    default null,
    primary key (`id`),
    key `mobile` (`mobile`) using btree
) engine = innodb
  auto_increment = 5
  default charset = utf8 comment ='用户表';

-- ----------------------------
-- records of tb_user
-- ----------------------------
insert into `tb_user`
values ('3', '17602189189', 'e10adc3949ba59abbe56e057f20f883e', '2019-08-02 16:43:46', '2019-08-02 16:43:46');
insert into `tb_user`
values ('4', '15800807944', 'e10adc3949ba59abbe56e057f20f883e', '2019-08-02 16:50:32', '2019-08-02 16:50:32');

-- ----------------------------
-- table structure for tb_user_info
-- ----------------------------
drop table if exists `tb_user_info`;
create table `tb_user_info`
(
    `id`        bigint(20) not null auto_increment,
    `user_id`   bigint(20) not null comment '用户id',
    `nick_name` varchar(50)  default null comment '昵称',
    `logo`      varchar(100) default null comment '用户头像',
    `tags`      varchar(50)  default null comment '用户标签：多个用逗号分隔',
    `sex`       tinyint(1)   default '3' comment '性别，1-男，2-女，3-未知',
    `age`       int(11)      default null comment '用户年龄',
    `edu`       varchar(20)  default null comment '学历',
    `city`      varchar(20)  default null comment '居住城市',
    `birthday`  varchar(20)  default null comment '生日',
    `cover_pic` varchar(50)  default null comment '封面图片',
    `industry`  varchar(20)  default null comment '行业',
    `income`    varchar(20)  default null comment '收入',
    `marriage`  varchar(20)  default null comment '婚姻状态',
    `created`   datetime     default null,
    `updated`   datetime     default null,
    primary key (`id`),
    key `user_id` (`user_id`)
) engine = innodb
  auto_increment = 4
  default charset = utf8 comment ='用户信息表';

-- ----------------------------
-- records of tb_user_info
-- ----------------------------
insert into `tb_user_info`
values ('2', '3', 'heima', null, null, '2', null, null, '北京市-北京城区-东城区', '2019-08-01', null, null, null, null,
        '2019-08-02 16:44:23', '2019-08-02 16:44:23');
insert into `tb_user_info`
values ('3', '4', '波仔1', 'http://itcast-tanhua.oss-cn-shanghai.aliyuncs.com/images/2019/08/02/15647359300685826.jpg',
        null, '2', null, null, '北京市-北京城区-朝阳区', '2019-06-02', null, null, null, null, '2019-08-02 16:50:47',
        '2019-08-02 16:52:10');
