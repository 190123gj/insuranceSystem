--1.团体客户 联系人id
ALTER TABLE customer_company_detail ADD COLUMN `contact_man_id` BIGINT(20) NULL COMMENT '联系人id' AFTER `contact_man`;
ALTER TABLE customer_cert_info ADD COLUMN `long_time` VARCHAR(10) DEFAULT 'NO' NULL COMMENT '证件长期' AFTER `cert_exp_date`;
ALTER TABLE customer_base_info ADD COLUMN `referee_id` VARCHAR(20) NULL AFTER `remark`, ADD COLUMN `referee_name` VARCHAR(50) NULL AFTER `referee_id`;
ALTER TABLE customer_contact_way ADD COLUMN `remark` VARCHAR(2000) NULL AFTER `user_id`;
--2.客户增加备注
ALTER TABLE customer_base_info ADD COLUMN `remark` VARCHAR(2000) NULL COMMENT '备注' AFTER `out_user`;

--3 .银行增加备注
ALTER TABLE customer_bank_info  ADD COLUMN `remark` VARCHAR(2000) NULL COMMENT '备注' AFTER `bank_order`;

--4.历史保单
CREATE TABLE `customer_his_business_bill` (
  `his_bill_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '保单的id',
  `bill_no` varchar(200) DEFAULT NULL COMMENT '业务单号',
  `bill_customer_id` bigint(20) DEFAULT NULL COMMENT '投保人id',
  `bill_customer_name` varchar(200) DEFAULT NULL COMMENT '投保人名称',
  `catalog_id` bigint(20) DEFAULT NULL COMMENT '险种',
  `catalog_name` varchar(200) DEFAULT NULL COMMENT '险种名称',
  `premium_amount` bigint(20) DEFAULT NULL COMMENT '保费',
  `insuranceDate` varchar(20) NOT NULL COMMENT '投标日期',
  `beginDate` varchar(20) NOT NULL COMMENT '保险起期',
  `endDate` varchar(20) NOT NULL COMMENT '保险止期',
  `insurance_company_id` bigint(20) DEFAULT NULL COMMENT '保险公司',
  `insurance_company_name` varchar(200) DEFAULT NULL COMMENT '保险公司',
  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`his_bill_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='客户历史保单';

--5、佣金明细新增字段 保单id 和 保单编号
ALTER TABLE `person_commission_detail`
ADD COLUMN `business_bill_id`  bigint(20) NULL COMMENT '保单的id' AFTER `business_user_type`,
ADD COLUMN `insurance_no`  varchar(40) NULL COMMENT '保单的编号' AFTER `business_bill_id`;

--6、更改超权限审批项目权限人id的长度
ALTER TABLE `project_setup`
MODIFY COLUMN `setup_use_id`  varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目使用者id' AFTER `setup_use_name`;

--7、缴费计划数据新增时间、新增添加时间 和 更新时间
ALTER TABLE `business_bill_pay_plan`
ADD COLUMN `data_add_time`  timestamp NULL COMMENT '数据记录时间' AFTER `business_bill_id`,
ADD COLUMN `raw_update_time`  timestamp NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '数据更新时间' AFTER `data_add_time`,
ADD COLUMN `raw_add_time`  timestamp NULL COMMENT '新增时间' AFTER `raw_update_time`;


----20170428
ALTER TABLE price_contact_letter_demand ADD COLUMN `floor_num` VARCHAR(200) NULL ;
ALTER TABLE price_contact_letter_demand ADD COLUMN `floor_straight_num` VARCHAR(200) NULL ;
ALTER TABLE price_contact_letter_demand ADD COLUMN `floor_stairs_num` VARCHAR(200) NULL ;
ALTER TABLE price_contact_letter_demand ADD COLUMN `pool_num` VARCHAR(200) NULL ;
ALTER TABLE price_contact_letter_demand ADD COLUMN `pool_directly_num` VARCHAR(200) NULL ;
ALTER TABLE price_contact_letter_demand ADD COLUMN `pool_no_directly_num` VARCHAR(200) NULL ;
ALTER TABLE price_contact_letter_report_price ADD COLUMN `remark` VARCHAR(2000) NULL AFTER `contact_letter_id`;
 ALTER TABLE insurance_protocol_info ADD COLUMN `firstPeriod` VARCHAR(200) NULL AFTER `protocol_id`;

 -----------20170504
 ALTER TABLE `insurance_protocol` ADD COLUMN `is_main` VARCHAR(20) NULL AFTER `type`, ADD COLUMN `parent_id` VARCHAR(200) NULL AFTER `is_main`, ADD COLUMN `parent_name` VARCHAR(200) NULL AFTER `parent_id`;
 ALTER TABLE price_contact_letter_demand ADD COLUMN `construction_period_begin` VARCHAR(20) NULL COMMENT '建造期限' AFTER `construction_period`, ADD COLUMN `construction_period_end` VARCHAR(20) NULL AFTER `construction_period_begin`, ADD COLUMN `construction_period_num` VARCHAR(20) NULL AFTER `construction_period_end`, ADD COLUMN `commissioning_period_begin` VARCHAR(20) NULL COMMENT '试车期' AFTER `construction_period_num`, ADD COLUMN `commissioning_period_end` VARCHAR(20) NULL AFTER `commissioning_period_begin`, ADD COLUMN `commissioning_period_num` VARCHAR(20) NULL AFTER `commissioning_period_end`, ADD COLUMN `warranty_period_begin` VARCHAR(20) NULL AFTER `commissioning_period_num`, ADD COLUMN `warranty_period_end` VARCHAR(20) NULL AFTER `warranty_period_begin`, ADD COLUMN `warranty_period_num` VARCHAR(20) NULL AFTER `warranty_period_end`;
ALTER TABLE `price_contact_letter_demand` ADD COLUMN `project_period_begin` VARCHAR(20) NULL AFTER `warranty_period_num`, ADD COLUMN `project_period_end` VARCHAR(20) NULL AFTER `project_period_begin`, ADD COLUMN `project_period_num` VARCHAR(20) NULL AFTER `project_period_end`;



ALTER TABLE insurance_product` ADD COLUMN `unit_price` BIGINT(20) NULL COMMENT '单位' AFTER `creator_name`;
ALTER TABLE price_contact_letter_company_report_price` ADD COLUMN `expense_amount` BIGINT(20) NULL AFTER `create_date`;
ALTER TABLE `price_contact_letter_scheme_detail` CHANGE `product_name` `name` VARCHAR(200) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '品名';

--新增字段保单年度
ALTER TABLE `business_bill_pay_plan`
ADD COLUMN `year`  varchar(20) NULL COMMENT '保单年度' AFTER `premium_amount`;