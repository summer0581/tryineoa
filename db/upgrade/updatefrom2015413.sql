/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2015/4/27 11:45:03                           */
/*==============================================================*/


/*==============================================================*/
/* Table: oa_zhidu_content                                      */
/*==============================================================*/
create table oa_zhidu_content
(
   id                   national varchar(64) not null comment '编号',
   name                 national varchar(100) not null comment '名称',
   content              text comment '制度内容',
   ml_id                varchar(64) comment '所属目录',
   files                varchar(2000) comment '附件',
   create_by            national varchar(64) not null comment '创建者',
   create_date          datetime not null comment '创建时间',
   update_by            national varchar(64) not null comment '更新者',
   update_date          datetime not null comment '更新时间',
   remarks              national varchar(255) comment '备注信息',
   del_flag             national char(1) not null default '0' comment '删除标记',
   sort                 decimal(10,0) not null comment '排序'
);

alter table oa_zhidu_content comment '规章制度内容表';

/*==============================================================*/
/* Table: oa_zhidu_ml                                           */
/*==============================================================*/
create table tryineoa.oa_zhidu_ml
(
   id                   national varchar(64) not null comment '编号',
   parent_id            national varchar(64) not null comment '父级编号',
   parent_ids           national varchar(2000) not null comment '所有父级编号',
   name                 national varchar(100) not null comment '名称',
   sort                 decimal(10,0) not null comment '排序',
   create_by            national varchar(64) not null comment '创建者',
   create_date          datetime not null comment '创建时间',
   update_by            national varchar(64) not null comment '更新者',
   update_date          datetime not null comment '更新时间',
   remarks              national varchar(255) comment '备注信息',
   del_flag             national char(1) not null default '0' comment '删除标记',
   primary key (id)
);

alter table tryineoa.oa_zhidu_ml comment '规章制度目录表';

/*==============================================================*/
/* Index: test_data_parent_id                                   */
/*==============================================================*/
create index test_data_parent_id on tryineoa.oa_zhidu_ml
(
   parent_id
);

/*==============================================================*/
/* Index: test_tree_del_flag                                    */
/*==============================================================*/
create index test_tree_del_flag on tryineoa.oa_zhidu_ml
(
   del_flag
);


/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2015/4/28 13:41:25                           */
/*==============================================================*/


drop table if exists tryineoa.tmp_sys_user;

rename table tryineoa.sys_user to tmp_sys_user;

/*==============================================================*/
/* Table: sys_user                                              */
/*==============================================================*/
create table tryineoa.sys_user
(
   id                   national varchar(64) not null comment '编号',
   company_id           national varchar(64) not null comment '归属公司',
   office_id            national varchar(64) not null comment '归属部门',
   login_name           national varchar(100) not null comment '登录名',
   password             national varchar(100) not null comment '密码',
   no                   national varchar(100) comment '工号',
   name                 national varchar(100) not null comment '姓名',
   zhiwei               varchar(100) comment '职位',
   email                national varchar(200) comment '邮箱',
   phone                national varchar(200) comment '电话',
   mobile               national varchar(200) comment '手机',
   user_type            national char(1) comment '用户类型',
   photo                national varchar(1000) comment '用户头像',
   login_ip             national varchar(100) comment '最后登陆IP',
   login_date           datetime comment '最后登陆时间',
   login_flag           national varchar(64) comment '是否可登录',
   create_by            national varchar(64) not null comment '创建者',
   create_date          datetime not null comment '创建时间',
   update_by            national varchar(64) not null comment '更新者',
   update_date          datetime not null comment '更新时间',
   remarks              national varchar(255) comment '备注信息',
   del_flag             national char(1) not null default '0' comment '删除标记',
   primary key (id)
);

alter table tryineoa.sys_user comment '用户表';

insert into tryineoa.sys_user (id, company_id, office_id, login_name, password, no, name, email, phone, mobile, user_type, photo, login_ip, login_date, login_flag, create_by, create_date, update_by, update_date, remarks, del_flag)
select id, company_id, office_id, login_name, password, no, name, email, phone, mobile, user_type, photo, login_ip, login_date, login_flag, create_by, create_date, update_by, update_date, remarks, del_flag
from tryineoa.tmp_sys_user;

/*==============================================================*/
/* Index: sys_user_company_id                                   */
/*==============================================================*/
create index sys_user_company_id on tryineoa.sys_user
(
   company_id
);

/*==============================================================*/
/* Index: sys_user_del_flag                                     */
/*==============================================================*/
create index sys_user_del_flag on tryineoa.sys_user
(
   del_flag
);

/*==============================================================*/
/* Index: sys_user_login_name                                   */
/*==============================================================*/
create index sys_user_login_name on tryineoa.sys_user
(
   login_name
);

/*==============================================================*/
/* Index: sys_user_office_id                                    */
/*==============================================================*/
create index sys_user_office_id on tryineoa.sys_user
(
   office_id
);

/*==============================================================*/
/* Index: sys_user_update_date                                  */
/*==============================================================*/
create index sys_user_update_date on tryineoa.sys_user
(
   update_date
);

