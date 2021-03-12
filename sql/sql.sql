CREATE TABLE `account` (
    `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `user_id` bigint(11) DEFAULT NULL COMMENT '用户id',
    `total` decimal(10,0) DEFAULT NULL COMMENT '总额度',
    `used` decimal(10,0) DEFAULT NULL COMMENT '已用余额',
    `residue` decimal(10,0) DEFAULT '0' COMMENT '剩余可用额度',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

INSERT INTO `account` (`id`, `user_id`, `total`, `used`, `residue`) VALUES ('1', '1', '1000', '0', '100');

CREATE TABLE `storage`
(
    `id`         bigint(11) NOT NULL AUTO_INCREMENT,
    `product_id` bigint(11) DEFAULT NULL COMMENT '产品id',
    `total`      int(11)    DEFAULT NULL COMMENT '总库存',
    `used`       int(11)    DEFAULT NULL COMMENT '已用库存',
    `residue`    int(11)    DEFAULT NULL COMMENT '剩余库存',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 2  DEFAULT CHARSET = utf8;

INSERT INTO `storage` (`id`, `product_id`, `total`, `used`, `residue`)VALUES ('1', '1', '100', '0', '100');
CREATE TABLE `order`
(
    `id`         bigint(11) NOT NULL AUTO_INCREMENT,
    `user_id`    bigint(11)     DEFAULT NULL COMMENT '用户id',
    `product_id` bigint(11)     DEFAULT NULL COMMENT '产品id',
    `count`      int(11)        DEFAULT NULL COMMENT '数量',
    `money`      decimal(11, 0) DEFAULT NULL COMMENT '金额',
    `status`     int(1)         DEFAULT NULL COMMENT '订单状态：0：创建中；1：已完结',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 7 DEFAULT CHARSET = utf8;

CREATE TABLE `undo_log`
(
    `id`            bigint(20)   NOT NULL AUTO_INCREMENT,
    `branch_id`     bigint(20)   NOT NULL,
    `xid`           varchar(100) NOT NULL,
    `context`       varchar(128) NOT NULL,
    `rollback_info` longblob     NOT NULL,
    `log_status`    int(11)      NOT NULL,
    `log_created`   datetime     NOT NULL,
    `log_modified`  datetime     NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8;
