# drop database if exists yupao;
# create database if not exists yupao;
# use yupao;

-- ---------------------------------------------------
-- table
-- ---------------------------------------------------
-- 用户表
create table user
(
    username     varchar(256)                       null comment '用户昵称',
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                       null comment '账号',
    avatarUrl    varchar(1024)                      null comment '用户头像',
    gender       tinyint                            null comment '性别',
    userPassword varchar(512)                       not null comment '密码',
    phone        varchar(128)                       null comment '电话',
    email        varchar(512)                       null comment '邮箱',
    userStatus   int      default 0                 not null comment '状态 0 - 正常',
    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    isDelete     tinyint  default 0                 not null comment '是否删除',
    userRole     int      default 0                 not null comment '用户角色 0 - 普通用户 1 - 管理员',
    planetCode   varchar(512)                       null comment '星球编号',
    tags         varchar(1024)                      null comment '标签 json 列表',
    profile      varchar(1024)                      null comment '个人简介'
) comment '用户';

-- 标签表（可以不创建，因为标签字段已经放到了用户表中）
create table tag
(
    id         bigint auto_increment comment 'id' primary key,
    tagName    varchar(256)                       null comment '标签名称',
    userId     bigint                             null comment '用户 id',
    parentId   bigint                             null comment '父标签 id',
    isParent   tinyint                            null comment '0 - 不是, 1 - 父标签',
    createTime datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    isDelete   tinyint  default 0                 not null comment '是否删除',
    constraint uniIdx_tagName unique (tagName)
) comment '标签';

create index idx_userId on tag (userId);

-- ---------------------------------------------------
-- data
-- ---------------------------------------------------
insert into user (username, useraccount, avatarurl, gender, userpassword, phone, email, userstatus,
                  createtime, updatetime, isdelete,
                  userrole, planetCode, tags)
values ('oswin', 'oswin501',
        'https://miro.medium.com/v2/resize:fit:640/format:webp/1*4j2A9niz0eq-mRaCPUffpg.png', 1,
        'c9d21e89dc04f9f2b446b4fbdafdf4b8',
        '15534340089', 'oswin501@gmail.com', 0,
        current_timestamp, current_timestamp, 0,
        1, 'nn00000001', '["java", "cpp"]'),
       ('yupi', 'yupi501',
        'https://miro.medium.com/v2/resize:fit:640/format:webp/0*1Og_hmJWdlMiDWuB.png', 1,
        'c9d21e89dc04f9f2b446b4fbdafdf4b8',
        '15534340089', 'yupi501@gmail.com', 0,
        current_timestamp, current_timestamp, 0,
        0, 'nn00000002', '["python", "javascript"]'),
       ('yupi2', 'yupi502',
        'https://miro.medium.com/v2/resize:fit:640/format:webp/0*1Og_hmJWdlMiDWuB.png', 1,
        'c9d21e89dc04f9f2b446b4fbdafdf4b8',
        '15534340089', 'yupi501@gmail.com', 0,
        current_timestamp, current_timestamp, 1,
        0, 'nn00000003', '["python", "javascript"]'),
       ('yupi3', 'yupi503',
        'https://miro.medium.com/v2/resize:fit:640/format:webp/0*1Og_hmJWdlMiDWuB.png', 1,
        'c9d21e89dc04f9f2b446b4fbdafdf4b8',
        '15534340089', 'yupi501@gmail.com', 1,
        current_timestamp, current_timestamp, 0,
        0, 'nn00000004', '["python", "javascript"]');
