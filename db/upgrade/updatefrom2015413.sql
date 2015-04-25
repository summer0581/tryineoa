/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2015/4/13 15:50:58                           */
/*==============================================================*/


/*==============================================================*/
/* Table: oa_leaveapply                                         */
/*==============================================================*/
create table oa_leaveapply
(
   id                   national varchar(64) not null comment '编号',
   type                 varchar(20) comment '类型',
   name                 varchar(64) comment '姓名',
   office_id            varchar(64) comment '部门',
   post                 varchar(64) comment '岗位',
   telephone            varchar(64) comment '联系电话',
   is_positive          char(1) comment '是否转正',
   starttime            date comment '开始时间',
   endtime              date comment '结束时间',
   post_agent           varchar(64) comment '岗位代理',
   reason               varchar(4000) comment '请假理由',
   direct_leader_idea   varchar(4000) comment '直接上级意见',
   human_resource_idea  varchar(4000) comment '人资部意见',
   general_manager_idea varchar(4000) comment '总经理意见',
   chair_man_idea       varchar(4000) comment '董事长意见',
   create_by            national varchar(64) not null comment '创建者',
   create_date          datetime not null comment '创建时间',
   update_by            national varchar(64) not null comment '更新者',
   update_date          datetime not null comment '更新时间',
   remarks              national varchar(255) comment '备注信息',
   del_flag             national char(1) not null default '0' comment '删除标记'
);

alter table oa_leaveapply comment '请假申请表';



/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2015/4/13 16:27:44                           */
/*==============================================================*/


drop table if exists tryineoa.tmp_oa_leaveapply;

rename table tryineoa.oa_leaveapply to tmp_oa_leaveapply;

/*==============================================================*/
/* Table: oa_leaveapply                                         */
/*==============================================================*/
create table oa_leaveapply
(
   id                   national varchar(64) not null comment '编号',
   type                 varchar(20) comment '类型',
   name                 varchar(64) comment '姓名',
   office_id            varchar(64) comment '部门',
   post                 varchar(64) comment '岗位',
   telephone            varchar(64) comment '联系电话',
   is_positive          char(1) comment '是否转正',
   starttime            date comment '开始时间',
   endtime              date comment '结束时间',
   post_agent           varchar(64) comment '岗位代理',
   reason               varchar(4000) comment '请假理由',
   direct_leader_idea   varchar(4000) comment '直接上级意见',
   human_resource_idea  varchar(4000) comment '人资部意见',
   general_manager_idea varchar(4000) comment '总经理意见',
   chair_man_idea       varchar(4000) comment '董事长意见',
   create_by            national varchar(64) not null comment '创建者',
   create_date          datetime not null comment '创建时间',
   update_by            national varchar(64) not null comment '更新者',
   update_date          datetime not null comment '更新时间',
   remarks              national varchar(255) comment '备注信息',
   del_flag             national char(1) not null default '0' comment '删除标记',
   primary key (id)
);

alter table oa_leaveapply comment '请假申请表';

insert into oa_leaveapply (id, type, name, office_id, post, telephone, is_positive, starttime, endtime, post_agent, reason, direct_leader_idea, human_resource_idea, general_manager_idea, chair_man_idea, create_by, create_date, update_by, update_date, remarks, del_flag)
select id, type, name, office_id, post, telephone, is_positive, starttime, endtime, post_agent, reason, direct_leader_idea, human_resource_idea, general_manager_idea, chair_man_idea, create_by, create_date, update_by, update_date, remarks, del_flag
from tryineoa.tmp_oa_leaveapply;

