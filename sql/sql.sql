drop table if exists sys_user;
CREATE TABLE sys_user
(
    id           bigint(20) NOT NULL COMMENT 'id',
    user_name    varchar(100) NOT NULL COMMENT 'user_name',
    nick_name    varchar(100)          DEFAULT NULL COMMENT 'nick_name',
    password     varchar(300)          DEFAULT NULL COMMENT 'password',
    email        varchar(100)          DEFAULT NULL COMMENT 'email',
    phone_number varchar(100)          DEFAULT NULL COMMENT 'phone_number',
    sex          char(1)               DEFAULT NULL COMMENT 'sex',
    birthday     datetime              DEFAULT NULL COMMENT 'birthday',
    avatar       varchar(100)          DEFAULT NULL COMMENT 'avatar',
    status       char(1)      NOT NULL DEFAULT 0 COMMENT 'status（0 normal, 1 disabled）',
    del_flag     char(1)      NOT NULL DEFAULT 0,
    created_by   bigint(20) DEFAULT NULL,
    created_date datetime              DEFAULT NULL,
    updated_by   bigint(20) DEFAULT NULL,
    updated_date datetime              DEFAULT NULL,
    remark       varchar(100)          DEFAULT NULL,
    PRIMARY KEY (id) USING BTREE,
    index        name_index (user_name) USING BTREE
) ENGINE=InnoDB  COMMENT='sys_user table';

drop table if exists sys_role;
create table sys_role
(
    id           bigint(20) not null auto_increment comment 'id',
    role_name    varchar(30)  not null comment 'role_name',
    role_key     varchar(100) not null comment 'role_key',
    role_sort    int(4) not null comment 'role_sort',
    status       char(1)      not null comment 'status（0 normal, 1 disabled）',
    del_flag     char(1)      NOT NULL DEFAULT 0,
    created_by   bigint(20) DEFAULT NULL,
    created_date datetime              DEFAULT NULL,
    updated_by   bigint(20) DEFAULT NULL,
    updated_date datetime              DEFAULT NULL,
    remark       varchar(100)          DEFAULT NULL,
    primary key (id)
) engine=innodb auto_increment=100 comment = 'sys_role table';


drop table if exists sys_user_role;
create table sys_user_role
(
    user_id bigint(20) not null comment 'user_id',
    role_id bigint(20) not null comment 'role_id',
    primary key (user_id, role_id)
) engine=innodb comment = 'sys_user_role table';

-- 注意此处0.3.0+ 增加唯一索引 ux_undo_log
CREATE TABLE `undo_log`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT,
    `branch_id`     bigint(20) NOT NULL,
    `xid`           varchar(100) NOT NULL,
    `context`       varchar(128) NOT NULL,
    `rollback_info` longblob     NOT NULL,
    `log_status`    int(11) NOT NULL,
    `log_created`   datetime     NOT NULL,
    `log_modified`  datetime     NOT NULL,
    `ext`           varchar(100) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

sourceSets
{
    main {
        resources {
            srcDirs += [
                    project(':jcloud-framework').sourceSets.main.resources
            ]
        }
    }
}
