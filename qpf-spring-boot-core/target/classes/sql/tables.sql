-- CREATE DATABASE IF NOT EXISTS ssm DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- use ssm;

CREATE TABLE IF NOT EXISTS dept (
  id int(11) auto_increment not null comment '部门id',
  name varchar(255) not null comment '部门名称',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment = '部门表';

CREATE TABLE IF NOT EXISTS empl (
  id int(11) auto_increment not null comment '员工id',
  name varchar(255) not null comment '员工姓名',
  gender char(1) not null comment '员工性别',
  email varchar(255) not null comment '员工邮箱',
  birth date not null comment '员工生日',
  dept_id int(11) not null comment '员工部门id',
  PRIMARY KEY (id),
--  KEY `dept_id` (`dept_id`),
  CONSTRAINT `empl_ibfk_1` FOREIGN KEY (`dept_id`) REFERENCES `dept` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment = '员工表';


-- ALTER TABLE empl ADD FOREIGN KEY (dept_id) REFERENCES dept(dept_id);

