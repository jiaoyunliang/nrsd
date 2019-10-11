create table bnz_project_info
(
  id           number primary key,
  org_id       number,
  project_name varchar2(256) not null,
  project_time date          not null,
  project_type varchar2(1)   not null,
  is_del       int           not null,
  update_user  varchar2(30),
  update_time  date,
  create_user  varchar2(30)  not null,
  create_time  date          not null
);

comment on table bnz_project_info is '项目信息表';
comment on column bnz_project_info.org_id is '医院编码';
comment on column bnz_project_info.project_name is '项目名称';
comment on column bnz_project_info.project_time is '项目时间';
comment on column bnz_project_info.project_type is '项目类型（1.本院项目 2.全网公开项目）';
comment on column bnz_project_info.is_del is '是否删除（0.否；1.是）';
comment on column bnz_project_info.update_user is '最后一次修改人';
comment on column bnz_project_info.update_time is '最后一次修改时间';
comment on column bnz_project_info.create_user is '创建人';
comment on column bnz_project_info.create_time is '创建时间';

--项目信息表主键序列
drop sequence BNZ_PROJECT_INFO_SEQUENCE;
create sequence BNZ_PROJECT_INFO_SEQUENCE
  minvalue 1
  maxvalue 999999999999999999999999999999
  start with 1
  increment by 1
  cache 20;


--文件和业务关系表
drop table bnz_file_relation;
create table bnz_file_relation
(
  id          number primary key,
  file_url    varchar2(100) not null,
  file_name   varchar2(100) not null,
  type        int,
  category    int           not null,
  category_id number        not null,
  is_del      number        not null,
  update_user varchar2(30),
  update_time date,
  create_user varchar2(30)  not null,
  create_time date          not null
);
comment on table bnz_file_relation is '文件和业务关系表';
comment on column bnz_file_relation.id is '主键';
comment on column bnz_file_relation.file_url is '后台生成文件名';
comment on column bnz_file_relation.file_name is '文件原名';
comment on column bnz_file_relation.type is '类型 1 图片;2.文件';
comment on column bnz_file_relation.category is '文件所属业务类别 1.企业资质，2.产品资质，3.企业介绍图片，4.企业介绍品牌，5.产品图片，6.产品视频';
comment on column bnz_file_relation.category_id is '企业编号或者标书编号等';
comment on column bnz_file_relation.is_del is '是否删除（0.否；1.是）';
comment on column bnz_file_relation.update_user is '最后一次修改人';
comment on column bnz_file_relation.update_time is '最后一次修改时间';
comment on column bnz_file_relation.create_user is '创建人';
comment on column bnz_file_relation.create_time is '创建时间';
--文件和业务关系表主键序列
drop sequence BNZ_FILE_RELATION_SEQUENCE;
create sequence BNZ_FILE_RELATION_SEQUENCE
  minvalue 1
  maxvalue 999999999999999999999999999999
  start with 1
  increment by 1
  cache 20;


--机构资质信息表
drop table bnz_institution_qua;
create table bnz_institution_qua
(
  id            number primary key,
  org_info_id   number        not null,
  qua_name      VARCHAR2(200) not null,
  org_name      varchar2(200),
  validity_date date          not null,
  qua_type      int           not null,
  is_show       int           not null,
  type_flag     int           not null,
  is_del        int           not null,
  update_user   varchar2(30),
  update_time   date,
  create_user   varchar2(30)  not null,
  create_time   date          not null
);
comment on table bnz_institution_qua is '机构资质信息表';
comment on column bnz_institution_qua.id is '主键';
comment on column bnz_institution_qua.org_info_id is '机构编码(企业维护的时候为选择企业资质，医院维护时为维护的医院机构编号)';
comment on column bnz_institution_qua.org_name is '医院端维护资质输入的企业名称';
comment on column bnz_institution_qua.qua_name is '资质名称';
comment on column bnz_institution_qua.validity_date is '截至有效期';
comment on column bnz_institution_qua.qua_type is '资质类型（见字典表）';
comment on column bnz_institution_qua.is_show is '是否可见（0，否 1.是）';
comment on column bnz_institution_qua.type_flag is '维护资质的机构1，企业，2.医院';
comment on column bnz_institution_qua.is_del is '是否删除（0.否；1.是）';
comment on column bnz_institution_qua.update_user is '最后一次修改人';
comment on column bnz_institution_qua.update_time is '最后一次修改时间';
comment on column bnz_institution_qua.create_user is '创建人';
comment on column bnz_institution_qua.create_time is '创建时间';
--资质信息表主键序列
drop sequence BNZ_INSTITUTION_QUA_SEQUENCE;
create sequence BNZ_INSTITUTION_QUA_SEQUENCE
  minvalue 1
  maxvalue 999999999999999999999999999999
  start with 1
  increment by 1
  cache 20;


--资质属性扩展表
drop table BNZ_QUA_EXT_ITEM;
create table BNZ_QUA_EXT_ITEM
(
  ID          NUMBER primary key,
  QUA_CODE    number        not null,
  EXT_CODE    VARCHAR2(24)  not null,
  EXT_NAME    VARCHAR2(100) not null,
  IS_MUST     CHAR          not null,
  ORDER_ID    int,
  QUA_TYPE    int           not null,
  EXT_TYPE    int           not null,
  is_del      int           not null,
  update_user varchar2(30),
  update_time date,
  create_user varchar2(30)  not null,
  create_time date          not null
);
comment on table BNZ_QUA_EXT_ITEM is '资质扩展属性表';
comment on column BNZ_QUA_EXT_ITEM.ID is 'ID';
comment on column BNZ_QUA_EXT_ITEM.QUA_CODE is '资质编号(外键字典表)';
comment on column BNZ_QUA_EXT_ITEM.EXT_CODE is '扩展编号';
comment on column BNZ_QUA_EXT_ITEM.EXT_NAME is '扩展名称';
comment on column BNZ_QUA_EXT_ITEM.IS_MUST is '是否必须(0,否；1.是)';
comment on column BNZ_QUA_EXT_ITEM.ORDER_ID is '排序';
comment on column BNZ_QUA_EXT_ITEM.is_del is '是否删除';
comment on column BNZ_QUA_EXT_ITEM.QUA_TYPE is '资质类别（1.企业资质；2.产品资质）';
comment on column BNZ_QUA_EXT_ITEM.EXT_TYPE is '字段类型';
--资质属性扩展表主键序列
drop sequence BNZ_QUA_EXT_ITEM_SEQUENCE;
create sequence BNZ_QUA_EXT_ITEM_SEQUENCE
  minvalue 1
  maxvalue 999999999999999999999999999999
  start with 1
  increment by 1
  cache 20;

drop table bnz_product_qua;

create table bnz_product_qua
(
  id          number primary key,
  org_info_id number        not null,
  org_name    varchar2(200),
  qua_code    int           not null,
  qua_value   varchar2(200) not null,
  qua_type    int           not null,
  valid_date  date,
  is_show     int           not null,
  type_flag   int           not null,
  is_del      int           not null,
  update_user varchar2(30),
  update_time date,
  create_user varchar2(30)  not null,
  create_time date          not null
);
comment on table bnz_product_qua is '产品资质表';
comment on column bnz_product_qua.id is '主键';
comment on column bnz_product_qua.org_info_id is '机构编码(企业维护的时候为选择企业资质，医院维护时为维护的医院机构编号)';
comment on column bnz_product_qua.org_name is '医院维护资质输入的企业名称';
comment on column bnz_product_qua.qua_code is '资质类型编码';
comment on column bnz_product_qua.qua_value is '资质详细信息';
comment on column bnz_product_qua.qua_type is '资质类别（1.企业资质；2.产品资质）';
comment on column bnz_product_qua.valid_date is '有效期至（如果详情里有此字段，冗余这里一份）';
comment on column bnz_product_qua.is_show is '是否医院可见（0，否；1.是）';
comment on column bnz_product_qua.type_flag is '维护资质的机构1，企业，2.医院';
comment on column bnz_product_qua.is_del is '是否删除（0，否；1,是）';
comment on column bnz_product_qua.update_user is '最后一次修改人';
comment on column bnz_product_qua.update_time is '最后一次修改时间';
comment on column bnz_product_qua.create_user is '创建人';
comment on column bnz_product_qua.create_time is '创建时间';

drop sequence BNZ_PRODUCT_QUA_SEQUENCE;
create sequence BNZ_PRODUCT_QUA_SEQUENCE
  minvalue 1
  maxvalue 999999999999999999999999999999
  start with 1
  increment by 1
  cache 20;

drop table bnz_qua_product;
create table bnz_qua_product
(
  id              number primary key,
  org_qua_info_id number       not null,
  pro_qua_type_id number       not null,
  org_pro_id      number       not null,
  is_del          int          not null,
  update_user     varchar2(30),
  update_time     date,
  create_user     varchar2(30) not null,
  create_time     date         not null
);

comment on table bnz_qua_product is '产品资质关系表';
comment on column bnz_qua_product.org_qua_info_id is '资质ID';
comment on column bnz_qua_product.pro_qua_type_id is '产品资质类型ID';
comment on column bnz_qua_product.org_pro_id is '产品ID';
comment on column bnz_qua_product.is_del is '是否删除（0，否；1,是）';
comment on column bnz_qua_product.update_user is '最后一次修改人';
comment on column bnz_qua_product.update_time is '最后一次修改时间';
comment on column bnz_qua_product.create_user is '创建人';
comment on column bnz_qua_product.create_time is '创建时间';

drop sequence BNZ_QUA_PRODUCT_SEQUENCE;
create sequence BNZ_QUA_PRODUCT_SEQUENCE
  minvalue 1
  maxvalue 999999999999999999999999999999
  start with 1
  increment by 1
  cache 20;

drop table bnz_package_record;
create table bnz_package_record
(
  id          number primary key,
  package_url varchar2(500) not null,
  type        int           not null,
  org_info_id number        not null,
  create_user varchar2(30)  not null,
  create_time date          not null
);

drop sequence BNZ_PACKAGE_RECORD_SEQUENCE;
create sequence BNZ_PACKAGE_RECORD_SEQUENCE
  minvalue 1
  maxvalue 999999999999999999999999999999
  start with 1
  increment by 1
  cache 20;


--中标详细信息表
drop table bnz_bid_detail;
create table bnz_bid_detail
(
  id                       number primary key,
  project_id               number       not null,
  bid_number               varchar2(4000),
  bid_org_name             varchar2(4000),
  bid_user_name            varchar2(4000),
  zt_item_id               varchar2(4000),
  maintain_org_name        varchar2(4000),
  produce_org_name         varchar2(4000),
  zt_item_name             varchar2(4000),
  project_category_id      varchar2(4000),
  project_category_name    varchar2(4000),
  project_catalogue_id     varchar2(4000),
  project_catalogue_name   varchar2(4000),
  zt_item_spec             varchar2(4000),
  zt_item_model            varchar2(4000),
  zt_register_id           varchar2(4000),
  zt_register_spec         varchar2(4000),
  zt_register_model        varchar2(4000),
  zt_item_brand            varchar2(4000),
  zt_item_min_number_unit  varchar2(4000),
  zt_item_pack_spec        varchar2(4000),
  zt_item_pack_material    varchar2(4000),
  zt_item_performace       varchar2(4000),
  zt_item_scope            varchar2(4000),
  zt_data_check_state      varchar2(4000),
  zt_catalogue_check_state varchar2(4000),
  item_id                  varchar2(4000),
  item_name                varchar2(4000),
  item_spec                varchar2(4000),
  item_model               varchar2(4000),
  item_register_id         varchar2(4000),
  item_register_spec       varchar2(4000),
  item_register_model      varchar2(4000),
  item_register_valid_date varchar2(4000),
  item_brand               varchar2(4000),
  item_min_number_unit     varchar2(4000),
  item_min_pack_unit       varchar2(4000),
  item_conversion_ratio    varchar2(4000),
  item_pack_spec           varchar2(4000),
  item_pack_material       varchar2(4000),
  item_performance         varchar2(4000),
  item_scope               varchar2(4000),
  compose_number           varchar2(4000),
  item_check_state         varchar2(4000),
  item_check_person        varchar2(4000),
  item_base_price          varchar2(4000),
  item_quote               varchar2(4000),
  is_del                   int          not null,
  update_user              varchar2(30),
  update_time              date,
  create_user              varchar2(30) not null,
  create_time              date         not null
);
comment on table bnz_bid_detail is '中标详细信息表';
comment on column bnz_bid_detail.id is '主键';
comment on column bnz_bid_detail.project_id is '项目基本信息表主键';
comment on column bnz_bid_detail.bid_org_name is '投标企业';
comment on column bnz_bid_detail.bid_user_name is '投标用户';
comment on column bnz_bid_detail.zt_item_id is '组套商品编号';
comment on column bnz_bid_detail.maintain_org_name is '维护企业';
comment on column bnz_bid_detail.produce_org_name is '生产企业';
comment on column bnz_bid_detail.zt_item_name is '组套商品名称';
comment on column bnz_bid_detail.project_category_id is '项目分类编号';
comment on column bnz_bid_detail.project_category_name is '项目分类名称';
comment on column bnz_bid_detail.project_catalogue_id is '项目目录编号';
comment on column bnz_bid_detail.project_catalogue_name is '项目目录名称';
comment on column bnz_bid_detail.zt_item_spec is '组套商品规格';
comment on column bnz_bid_detail.zt_item_model is '组套商品型号';
comment on column bnz_bid_detail.zt_register_id is '组套注册证编号';
comment on column bnz_bid_detail.zt_register_spec is '组套注册证规格';
comment on column bnz_bid_detail.zt_register_model is '组套注册证型号';
comment on column bnz_bid_detail.zt_item_brand is '组套商品商标';
comment on column bnz_bid_detail.zt_item_min_number_unit is '组套商品最小计量单位';
comment on column bnz_bid_detail.zt_item_pack_spec is '组套商品包装规格';
comment on column bnz_bid_detail.zt_item_pack_material is '组套商品包装材质';
comment on column bnz_bid_detail.zt_item_performace is '组套商品性能组成';
comment on column bnz_bid_detail.zt_item_scope is '组套商品适用范围';
comment on column bnz_bid_detail.zt_data_check_state is '组套数据审核状态';
comment on column bnz_bid_detail.zt_catalogue_check_state is '组套目录审核状态';
comment on column bnz_bid_detail.item_id is '商品编号';
comment on column bnz_bid_detail.item_name is '商品名称';
comment on column bnz_bid_detail.item_spec is '商品规格';
comment on column bnz_bid_detail.item_model is '商品型号';
comment on column bnz_bid_detail.item_register_id is '商品注册证编号';
comment on column bnz_bid_detail.item_register_spec is '商品注册证规格';
comment on column bnz_bid_detail.item_register_model is '商品注册证型号';
comment on column bnz_bid_detail.item_register_valid_date is '商品注册证有效期至';
comment on column bnz_bid_detail.item_brand is '商品商标';
comment on column bnz_bid_detail.item_min_number_unit is '商品最小计量单位';
comment on column bnz_bid_detail.item_min_pack_unit is '商品最小包装单位';
comment on column bnz_bid_detail.item_conversion_ratio is '商品转算比';
comment on column bnz_bid_detail.item_pack_spec is '商品包装规格';
comment on column bnz_bid_detail.item_pack_material is '商品包装材质';
comment on column bnz_bid_detail.item_performance is '商品性能组成';
comment on column bnz_bid_detail.item_scope is '商品适用范围';
comment on column bnz_bid_detail.compose_number is '构成数量';
comment on column bnz_bid_detail.item_check_state is '商品审核状态';
comment on column bnz_bid_detail.item_check_person is '商品审核人';
comment on column bnz_bid_detail.item_base_price is '商品基准价';
comment on column bnz_bid_detail.item_quote is '商品报价';
comment on column bnz_bid_detail.is_del is '是否删除（0.否；1.是）';
comment on column bnz_bid_detail.update_user is '最后一次修改人';
comment on column bnz_bid_detail.update_time is '最后一次修改时间';
comment on column bnz_bid_detail.create_user is '创建人';
comment on column bnz_bid_detail.create_time is '创建时间';
--中标详细信息表序列
drop sequence BNZ_BID_DETAIL_SEQUENCE;
create sequence BNZ_BID_DETAIL_SEQUENCE
  minvalue 1
  maxvalue 999999999999999999999999999999
  start with 1
  increment by 1
  cache 20;
--存储项目中标数据公开列
drop table bnz_bid_show_column;
create table bnz_bid_show_column
(
  id           number       not null,
  project_id   number       not null,
  column_key   varchar2(50) not null,
  column_name  varchar2(50) not null,
  column_order number,
  is_del       int          not null,
  update_user  varchar2(30),
  update_time  date,
  create_user  varchar2(30) not null,
  create_time  date         not null
);
--存储项目中标数据公开列表序列
drop sequence BNZ_BID_SHOW_COLUMN_SEQUENCE;
create sequence BNZ_BID_SHOW_COLUMN_SEQUENCE
  minvalue 1
  maxvalue 999999999999999999999999999999
  start with 1
  increment by 1
  cache 20;
