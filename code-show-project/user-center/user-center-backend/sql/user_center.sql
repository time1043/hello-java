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
        'https://miro.medium.com/v2/resize:fit:640/format:webp/1*4j2A9niz0eq-mRaCPUffpg.png', 1,
        'c9d21e89dc04f9f2b446b4fbdafdf4b8',
        '15534340089', 'oswin501@gmail.com', 0,
        current_timestamp, current_timestamp, 0,
        1),
       ('yupi', 'yupi501',
        'https://pic6.sucaisucai.com/01/88/01388996_2.jpg', 1,
        'c9d21e89dc04f9f2b446b4fbdafdf4b8',
        '15534340089', 'yupi501@gmail.com', 0,
        current_timestamp, current_timestamp, 0,
        0);
