## 追偿跟踪主表  20160727
DROP TABLE IF EXISTS `project_recovery`;
CREATE TABLE `project_recovery` (
   `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `project_code` VARCHAR(50) DEFAULT NULL COMMENT '项目编号',
   `recovery_amount` DECIMAL(17,0) NOT NULL DEFAULT '0' COMMENT  '代偿金额',
   `legal_manager_id` BIGINT(20) DEFAULT NULL COMMENT '法务经理ID',
   `legal_manager_name` VARCHAR(128) DEFAULT NULL COMMENT '法务经理名称',
   `recovery_status` VARCHAR(50) DEFAULT NULL COMMENT '项目状态',
   `status_update_time` TIMESTAMP NULL DEFAULT NULL COMMENT '状态更新时间',
   `recovery_principal_amount` DECIMAL(17,0) NOT NULL DEFAULT '0' COMMENT  '代偿本金金额',
   `recovery_interest_amount` DECIMAL(17,0) NOT NULL DEFAULT '0' COMMENT  '代偿利息金额',
   `recovery_interest_penalty_amount` DECIMAL(17,0) NOT NULL DEFAULT '0' COMMENT  '代偿罚息金额',
   `recovery_compensation_amount` DECIMAL(17,0) NOT NULL DEFAULT '0' COMMENT  '代偿违约金金额',
   `recovery_other_amount` DECIMAL(17,0) NOT NULL DEFAULT '0' COMMENT  '代偿其他金额',
   `litigation_on` VARCHAR(20) DEFAULT NULL COMMENT '是否执行诉讼',
   `debtor_reorganization_on` VARCHAR(20) DEFAULT NULL COMMENT '是否执行债务人重整或破产清算',
   `estimate_lose_amount` DECIMAL(17,0) NOT NULL DEFAULT '0' COMMENT  '预估损失金额',
   `apportion_lose_amount` DECIMAL(17,0) NOT NULL DEFAULT '0' COMMENT  '分摊损失金额',
   `lose_cognizance_amount` DECIMAL(17,0) NOT NULL DEFAULT '0' COMMENT  '损失认定金额',
   `close_form_id` BIGINT(20) DEFAULT  '0'  COMMENT '表单ID',
   `apply_user_id` BIGINT(20) DEFAULT NULL COMMENT '客户经理/经办人',
   `apply_user_account` VARCHAR(50) DEFAULT NULL COMMENT '客户经理/经办人账号',
   `apply_user_name` VARCHAR(50) DEFAULT NULL COMMENT '客户经理/经办人名字',
   `apply_dept_id` BIGINT(20) DEFAULT NULL COMMENT '客户经理/经办人部门ID',
   `apply_dept_code` VARCHAR(30) DEFAULT NULL COMMENT '客户经理/经办人部门编号',
   `apply_dept_name` VARCHAR(50) DEFAULT NULL COMMENT '客户经理/经办人部门名称',
   `raw_add_time` TIMESTAMP NULL DEFAULT NULL,
   `raw_update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`)
 ) ENGINE=INNODB  DEFAULT CHARSET=utf8  COMMENT='追偿跟踪表 - 主表';
 
 
 ##  追偿跟踪表 - 债务人重整或破产清算表  20160727
 CREATE TABLE `project_recovery_debtor_reorganization` (
   `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `project_recovery_id` BIGINT(20) NOT NULL COMMENT '追偿主表主键',
   `applicant` VARCHAR(128) DEFAULT NULL COMMENT '申请人',
   `accepting_court` VARCHAR(50) DEFAULT NULL COMMENT '受理法院',
   `debts_declare_end_time` TIMESTAMP NULL DEFAULT NULL COMMENT '债权申报截止日期',
   `division_we_declare_time` TIMESTAMP NULL DEFAULT NULL COMMENT '我司申报日期',
   `debts_confirm` VARCHAR(50) DEFAULT NULL COMMENT '债权确认',
   `council_circumstances` VARCHAR(2000) DEFAULT NULL COMMENT '会议情况',
   `we_suggestion` VARCHAR(2000) DEFAULT NULL COMMENT '我司意见',
   `re_execution_plan` VARCHAR(2000) DEFAULT NULL COMMENT '批准的重整方案及执行情况',
   `settlement_scheme_content` VARCHAR(2000) DEFAULT NULL COMMENT '和解方案',
   `liquidation_scheme` VARCHAR(2000) DEFAULT NULL COMMENT '清算方案',
   `liquidation_situation` VARCHAR(2000) DEFAULT NULL COMMENT '清偿情况',
   `recovery_total_amount` DECIMAL(17,0) NOT NULL DEFAULT '0' COMMENT  '回收总金额',
   `memo` VARCHAR(2000) DEFAULT NULL COMMENT '备注',
   `raw_add_time` TIMESTAMP NULL DEFAULT NULL,
   `raw_update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   KEY `project_recovery_id_index` (`project_recovery_id`) USING BTREE 
 ) ENGINE=INNODB  DEFAULT CHARSET=utf8  COMMENT='追偿跟踪表 - 债务人重整或破产清算表';
 
 ##  追偿跟踪表 - 债务人重整或破产清算表-债权人会议表  20160727
 
 CREATE TABLE `project_recovery_debtor_reorganization_debts_council` (
   `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `project_recovery_id` BIGINT(20) NOT NULL COMMENT '追偿主表主键',
   `project_recovery_debtor_reorganization_id` BIGINT(20) NOT NULL COMMENT '追偿跟踪表 - 债务人重整或破产清算表主键',
   `debts_council_describe`  VARCHAR(50) DEFAULT NULL COMMENT  '债权人会议描述',
   `debts_council_time` TIMESTAMP NULL DEFAULT NULL COMMENT '债权人会议日期',
   `raw_add_time` TIMESTAMP NULL DEFAULT NULL,
   `raw_update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   KEY `project_recovery_id_index` (`project_recovery_id`) USING BTREE ,
   KEY `project_recovery_debtor_reorganization_id_index` (`project_recovery_debtor_reorganization_id`) USING BTREE 
 ) ENGINE=INNODB  DEFAULT CHARSET=utf8  COMMENT='追偿跟踪表 - 债务人重整或破产清算表-债权人会议表';
 
 
 ##   追偿跟踪表 - 债务人重整或破产清算表-回收金额明细 20160727
 
DROP TABLE IF EXISTS `project_recovery_debtor_reorganization_amount_detail`;

CREATE TABLE `project_recovery_debtor_reorganization_amount_detail` (
   `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `project_recovery_id` BIGINT(20) NOT NULL COMMENT '追偿主表主键',
   `project_recovery_debtor_reorganization_id` BIGINT(20) NOT NULL COMMENT '追偿跟踪表 - 债务人重整或破产清算表主键',
   `project_recovery_litigation_execute_id` BIGINT(20) NOT NULL COMMENT '追偿跟踪表 -  诉讼 - 执行表主键',
   `project_recovery_type` VARCHAR(128) DEFAULT NULL COMMENT '追偿类型',
   `recovery_amount` DECIMAL(17,0) NOT NULL DEFAULT '0' COMMENT  '回收金额',
   `recovery_time` TIMESTAMP NULL DEFAULT NULL COMMENT '时间',
   `recovery_goods` VARCHAR(128) DEFAULT NULL COMMENT '标的物',
   `raw_add_time` TIMESTAMP NULL DEFAULT NULL,
   `raw_update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   KEY `project_recovery_id_index` (`project_recovery_id`) USING BTREE ,
   KEY `project_recovery_debtor_reorganization_id_index` (`project_recovery_debtor_reorganization_id`) USING BTREE  ,
   KEY `project_recovery_litigation_execute_id_index` (`project_recovery_litigation_execute_id`) USING BTREE  
 ) ENGINE=INNODB  DEFAULT CHARSET=utf8  COMMENT='追偿跟踪表 - 债务人重整或破产清算表-回收金额明细';
 
 ##  追偿跟踪表 - 债务人重整或破产清算表-抵质押资产抵债明细  20160727
 
 DROP TABLE IF EXISTS `project_recovery_debtor_reorganization_pledge`;


CREATE TABLE `project_recovery_debtor_reorganization_pledge` (
   `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `project_recovery_id` BIGINT(20) NOT NULL COMMENT '追偿主表主键',
   `project_recovery_debtor_reorganization_id` BIGINT(20) NOT NULL COMMENT '追偿跟踪表 - 债务人重整或破产清算表主键',
   `project_recovery_litigation_execute_id` BIGINT(20) NOT NULL COMMENT '追偿跟踪表 -  诉讼 - 执行表主键',
   `project_recovery_type` VARCHAR(128) DEFAULT NULL COMMENT '追偿类型',
   `pledge_name`  VARCHAR(50) DEFAULT NULL COMMENT  '名称',
   `pledge_amount` DECIMAL(17,0) NOT NULL DEFAULT '0' COMMENT  '抵债金额',
   `pledge_asset_management_mode`  VARCHAR(50) DEFAULT NULL COMMENT  '抵债资产管理方式',
   `raw_add_time` TIMESTAMP NULL DEFAULT NULL,
   `raw_update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   KEY `project_recovery_id_index` (`project_recovery_id`) USING BTREE ,
   KEY `project_recovery_debtor_reorganization_id_index` (`project_recovery_debtor_reorganization_id`) USING BTREE   ,
   KEY `project_recovery_litigation_execute_id_index` (`project_recovery_litigation_execute_id`) USING BTREE  
 ) ENGINE=INNODB  DEFAULT CHARSET=utf8  COMMENT='追偿跟踪表 - 债务人重整或破产清算表-抵质押资产抵债明细';
 
 
##  追偿跟踪表 - 诉讼-诉前保全  20160727

CREATE TABLE `project_recovery_litigation_before_preservation` (
   `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `project_recovery_id` BIGINT(20) NOT NULL COMMENT '追偿主表主键',
   `accepting_court` VARCHAR(50) DEFAULT NULL COMMENT '受理法院',
   `agent_law_firm` VARCHAR(50) DEFAULT NULL COMMENT '代理律所',
   `agent_attorney` VARCHAR(50) DEFAULT NULL COMMENT '承办律师',
   `agent_judge` VARCHAR(50) DEFAULT NULL COMMENT '承办法官',
   `apply_time` TIMESTAMP NULL DEFAULT NULL COMMENT '提交申请时间',
   `pay_time` TIMESTAMP NULL DEFAULT NULL COMMENT '缴费时间',
   `litigation_guarantee_type` VARCHAR(50) DEFAULT NULL COMMENT '诉讼担保方式',
   `preservation_time` TIMESTAMP NULL DEFAULT NULL COMMENT '保全时间',
   `raw_add_time` TIMESTAMP NULL DEFAULT NULL,
   `raw_update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   KEY `project_recovery_id_index` (`project_recovery_id`) USING BTREE 
 ) ENGINE=INNODB  DEFAULT CHARSET=utf8  COMMENT='追偿跟踪表 - 诉讼-诉前保全';
 
 ##   追偿跟踪表 - 诉讼-诉前保全-保全措施  20160727
 DROP TABLE IF EXISTS `project_recovery_litigation_before_preservation_precaution`;
CREATE TABLE `project_recovery_litigation_before_preservation_precaution` (
   `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `project_recovery_id` BIGINT(20) NOT NULL COMMENT '追偿主表主键',
   `project_recovery_litigation_before_preservation_id` BIGINT(20) NOT NULL COMMENT '诉前保全表主键',
   `project_recovery_litigation_preservation_id` BIGINT(20) NOT NULL COMMENT '诉讼保全表主键',
   `project_recovery_litigation_type` VARCHAR(50) DEFAULT NULL COMMENT '诉讼类型',
   `project_recovery_preservation_type` VARCHAR(50) DEFAULT NULL COMMENT '保全种类',
   `preservation_content` VARCHAR(1024) DEFAULT NULL COMMENT '保全内容',
   `preservation_time_start` TIMESTAMP NULL DEFAULT NULL COMMENT '保全期限（起时间）',
   `preservation_time_end` TIMESTAMP NULL DEFAULT NULL COMMENT '保全期限（止时间）',
   `memo` VARCHAR(1024) DEFAULT NULL COMMENT '备注',
   `end_notice` VARCHAR(50) DEFAULT NULL COMMENT '停止通知', 
   `raw_add_time` TIMESTAMP NULL DEFAULT NULL,
   `raw_update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   KEY `project_recovery_id_index` (`project_recovery_id`) USING BTREE ,
   KEY `project_recovery_litigation_before_preservation_id_index` (`project_recovery_litigation_before_preservation_id`) USING BTREE ,
   KEY `project_recovery_litigation_preservation_id_index` (`project_recovery_litigation_preservation_id`) USING BTREE 
 ) ENGINE=INNODB  DEFAULT CHARSET=utf8  COMMENT='追偿跟踪表 - 诉讼-诉前保全/诉讼保全-保全措施';
 
 
 ##   追偿跟踪表 - 诉讼-立案  20160727
 
 
 CREATE TABLE `project_recovery_litigation_place_on_file` (
   `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `project_recovery_id` BIGINT(20) NOT NULL COMMENT '追偿主表主键',
   `accepting_court` VARCHAR(50) DEFAULT NULL COMMENT '受理法院',
   `agent_law_firm` VARCHAR(50) DEFAULT NULL COMMENT '代理律所',
   `agent_attorney` VARCHAR(50) DEFAULT NULL COMMENT '承办律师',
   `agent_judge` VARCHAR(50) DEFAULT NULL COMMENT '承办法官',
   `place_on_file_time` TIMESTAMP NULL DEFAULT NULL COMMENT '立案时间',
   `pay_time` TIMESTAMP NULL DEFAULT NULL COMMENT '缴费时间',
   `memo` VARCHAR(1024) DEFAULT NULL COMMENT '备注',
   `raw_add_time` TIMESTAMP NULL DEFAULT NULL,
   `raw_update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   KEY `project_recovery_id_index` (`project_recovery_id`) USING BTREE 
 ) ENGINE=INNODB  DEFAULT CHARSET=utf8  COMMENT='追偿跟踪表 - 诉讼-立案';
 
 ##   追偿跟踪表 - 诉讼-诉讼保全 20160727
 
CREATE TABLE `project_recovery_litigation_preservation` (
   `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `project_recovery_id` BIGINT(20) NOT NULL COMMENT '追偿主表主键',
   `accepting_court` VARCHAR(50) DEFAULT NULL COMMENT '受理法院',
   `agent_law_firm` VARCHAR(50) DEFAULT NULL COMMENT '代理律所',
   `agent_attorney` VARCHAR(50) DEFAULT NULL COMMENT '承办律师',
   `agent_judge` VARCHAR(50) DEFAULT NULL COMMENT '承办法官',
   `apply_time` TIMESTAMP NULL DEFAULT NULL COMMENT '提交申请时间',
   `pay_time` TIMESTAMP NULL DEFAULT NULL COMMENT '缴费时间',
   `litigation_guarantee_type` VARCHAR(50) DEFAULT NULL COMMENT '诉讼担保方式',
   `preservation_time` TIMESTAMP NULL DEFAULT NULL COMMENT '保全时间',
   `raw_add_time` TIMESTAMP NULL DEFAULT NULL,
   `raw_update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   KEY `project_recovery_id_index` (`project_recovery_id`) USING BTREE 
 ) ENGINE=INNODB  DEFAULT CHARSET=utf8  COMMENT='追偿跟踪表 - 诉讼-诉讼保全';
 
 ###  追偿跟踪表 - 诉讼-庭前准备 20160728
 
 
 CREATE TABLE `project_recovery_litigation_before_trail` (
   `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `project_recovery_id` BIGINT(20) NOT NULL COMMENT '追偿主表主键',
   `opening_time` TIMESTAMP NULL DEFAULT NULL COMMENT '开庭时间',
   `notice_time` TIMESTAMP NULL DEFAULT NULL COMMENT '公告时间',
   `clerk_arrived_time` TIMESTAMP NULL DEFAULT NULL COMMENT '文书送达时间',
   `jurisdiction_objection` VARCHAR(50) DEFAULT NULL COMMENT '管辖异议',
   `jurisdiction_objection_judgment` VARCHAR(50) DEFAULT NULL COMMENT '管辖异议裁定',
   `jurisdiction_objection_appeal` VARCHAR(50) DEFAULT NULL COMMENT '管辖异议上诉',
   `jurisdiction_objection_judgment_second` VARCHAR(50) DEFAULT NULL COMMENT '管辖异议二审裁定',
   `evidence_exchange` VARCHAR(50) DEFAULT NULL COMMENT '证据交换',
   `appraisal_apply` VARCHAR(50) DEFAULT NULL COMMENT '鉴定申请',
   `appraisal_material` VARCHAR(50) DEFAULT NULL COMMENT '鉴定材料',
   `appraisal_amount` VARCHAR(50) DEFAULT NULL COMMENT '鉴定费用',
   `investigating_apply` VARCHAR(50) DEFAULT NULL COMMENT '申请调查取证',
   `witnesses_apply` VARCHAR(50) DEFAULT NULL COMMENT '申请证人出庭',
   `increase_litigation_apply` VARCHAR(50) DEFAULT NULL COMMENT '增加诉讼请求申请',
   `memo` VARCHAR(1024) DEFAULT NULL COMMENT '备注',
   `end_notice` VARCHAR(50) DEFAULT NULL COMMENT '停止通知',
   `raw_add_time` TIMESTAMP NULL DEFAULT NULL,
   `raw_update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   KEY `project_recovery_id_index` (`project_recovery_id`) USING BTREE 
 ) ENGINE=INNODB  DEFAULT CHARSET=utf8  COMMENT='追偿跟踪表 - 诉讼-庭前准备';
 
 
 ####   追偿跟踪表 - 诉讼-开庭  20160728
 
 CREATE TABLE `project_recovery_litigation_opening` (
   `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `project_recovery_id` BIGINT(20) NOT NULL COMMENT '追偿主表主键',
   `opening_time` TIMESTAMP NULL DEFAULT NULL COMMENT '开庭时间',
   `witness_attorney` VARCHAR(50) DEFAULT NULL COMMENT '出庭律师',
   `we_litigation_demand` VARCHAR(1024) DEFAULT NULL COMMENT '我方主要诉讼请求或答辩意见',
   `other_side_litigation_demand` VARCHAR(1024) DEFAULT NULL COMMENT '对方主要诉讼请求或答辩意见',
   `controversy_focus` VARCHAR(1024) DEFAULT NULL COMMENT '争议焦点',
   `additional_evidence` VARCHAR(1024) DEFAULT NULL COMMENT '补充证据',
   `memo` VARCHAR(1024) DEFAULT NULL COMMENT '备注',
   `raw_add_time` TIMESTAMP NULL DEFAULT NULL,
   `raw_update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   KEY `project_recovery_id_index` (`project_recovery_id`) USING BTREE 
 ) ENGINE=INNODB  DEFAULT CHARSET=utf8  COMMENT='追偿跟踪表 - 诉讼-开庭';
 
 ###   追偿跟踪表 - 诉讼-裁判   20160728
 
 DROP TABLE IF EXISTS `project_recovery_litigation_referee`;
 CREATE TABLE `project_recovery_litigation_referee` (
   `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `project_recovery_id` BIGINT(20) NOT NULL COMMENT '追偿主表主键',
   `project_recovery_referee_type` VARCHAR(50) NOT NULL COMMENT '裁判类型',
   `referee_clerk` VARCHAR(50) DEFAULT NULL COMMENT '裁判文书',
   `arrived_time` TIMESTAMP NULL DEFAULT NULL COMMENT '送达时间',
   `notice_time` TIMESTAMP NULL DEFAULT NULL COMMENT '公告时间',
   `effective_time` TIMESTAMP NULL DEFAULT NULL COMMENT '生效时间',
   `memo` VARCHAR(1024) DEFAULT NULL COMMENT '备注',
   `raw_add_time` TIMESTAMP NULL DEFAULT NULL,
   `raw_update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   KEY `project_recovery_id_index` (`project_recovery_id`) USING BTREE 
 ) ENGINE=INNODB  DEFAULT CHARSET=utf8  COMMENT='追偿跟踪表 - 诉讼-裁判';
 
 ###    追偿跟踪表 - 诉讼-二审上述  20160728
 
 
 CREATE TABLE `project_recovery_litigation_second_appeal` (
   `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `project_recovery_id` BIGINT(20) NOT NULL COMMENT '追偿主表主键',
   `appeal_center` VARCHAR(50) DEFAULT NULL COMMENT '上诉主体',
   `appeal_demand` VARCHAR(50) DEFAULT NULL COMMENT '上诉请求',
   `notice_time` TIMESTAMP NULL DEFAULT NULL COMMENT '公告时间',
   `opening_time` TIMESTAMP NULL DEFAULT NULL COMMENT '开庭时间',
   `new_evidence` VARCHAR(50) DEFAULT NULL COMMENT '新证据',
   `controversy_focus` VARCHAR(1024) DEFAULT NULL COMMENT '争议焦点',
   `memo` VARCHAR(1024) DEFAULT NULL COMMENT '备注',
   `raw_add_time` TIMESTAMP NULL DEFAULT NULL,
   `raw_update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   KEY `project_recovery_id_index` (`project_recovery_id`) USING BTREE 
 ) ENGINE=INNODB  DEFAULT CHARSET=utf8  COMMENT='追偿跟踪表 - 诉讼-二审上述';
 
 
 ###     追偿跟踪表 - 诉讼-实现担保物权特别程序    20160728 
 
 
 CREATE TABLE `project_recovery_litigation_special_procedure` (
   `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `project_recovery_id` BIGINT(20) NOT NULL COMMENT '追偿主表主键',
   `accepting_court` VARCHAR(50) DEFAULT NULL COMMENT '受理法院',
   `agent_law_firm` VARCHAR(50) DEFAULT NULL COMMENT '代理律所',
   `agent_attorney` VARCHAR(50) DEFAULT NULL COMMENT '承办律师',
   `agent_judge` VARCHAR(50) DEFAULT NULL COMMENT '承办法官',
   `place_on_file_time` TIMESTAMP NULL DEFAULT NULL COMMENT '立案时间',
   `pay_time` TIMESTAMP NULL DEFAULT NULL COMMENT '缴费时间',
   `referee_clerk` VARCHAR(50) DEFAULT NULL COMMENT '裁判文书',
   `referee_time` TIMESTAMP NULL DEFAULT NULL COMMENT '裁决时间',
   `memo` VARCHAR(1024) DEFAULT NULL COMMENT '备注',
   `raw_add_time` TIMESTAMP NULL DEFAULT NULL,
   `raw_update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   KEY `project_recovery_id_index` (`project_recovery_id`) USING BTREE 
 ) ENGINE=INNODB  DEFAULT CHARSET=utf8  COMMENT='追偿跟踪表 - 诉讼-实现担保物权特别程序';
 
 
 
 
 ####    追偿跟踪表 - 诉讼-强制执行公证执行证书   20160728
 
 
 CREATE TABLE `project_recovery_litigation_certificate` (
   `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `project_recovery_id` BIGINT(20) NOT NULL COMMENT '追偿主表主键',
   `notary_organ` VARCHAR(50) DEFAULT NULL COMMENT '公证机关',
   `agent_law_firm` VARCHAR(50) DEFAULT NULL COMMENT '代理律所',
   `agent_attorney` VARCHAR(50) DEFAULT NULL COMMENT '承办律师',
   `notarial` VARCHAR(50) DEFAULT NULL COMMENT '公证员',
   `apply_time` TIMESTAMP NULL DEFAULT NULL COMMENT '申请时间',
   `pay_time` TIMESTAMP NULL DEFAULT NULL COMMENT '缴费时间',
   `certificate` VARCHAR(50) DEFAULT NULL COMMENT '执行证书',
   `memo` VARCHAR(1024) DEFAULT NULL COMMENT '备注',
   `raw_add_time` TIMESTAMP NULL DEFAULT NULL,
   `raw_update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   KEY `project_recovery_id_index` (`project_recovery_id`) USING BTREE 
 ) ENGINE=INNODB  DEFAULT CHARSET=utf8  COMMENT='追偿跟踪表 - 诉讼-强制执行公证执行证书';
 
 
 ###     追偿跟踪表 - 诉讼-执行   20160728
 
 
DROP TABLE IF EXISTS `project_recovery_litigation_execute`;
CREATE TABLE `project_recovery_litigation_execute` (
   `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `project_recovery_id` BIGINT(20) NOT NULL COMMENT '追偿主表主键',
   `execute_apply` VARCHAR(50) DEFAULT NULL COMMENT '强制执行申请',
   `place_on_file` VARCHAR(50) DEFAULT NULL COMMENT '立案',
   `accepting_court` VARCHAR(50) DEFAULT NULL COMMENT '受理法院',
   `compromise` VARCHAR(50) DEFAULT NULL COMMENT '执行和解',
   `conciliation` VARCHAR(50) DEFAULT NULL COMMENT '调解',
   `estimate` VARCHAR(50) DEFAULT NULL COMMENT '评估',
   `recovery_total_amount` DECIMAL(17,0) NOT NULL DEFAULT '0' COMMENT  '回收总金额',
   `memo` VARCHAR(1024) DEFAULT NULL COMMENT '备注',
   `raw_add_time` TIMESTAMP NULL DEFAULT NULL,
   `raw_update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   KEY `project_recovery_id_index` (`project_recovery_id`) USING BTREE 
 ) ENGINE=INNODB  DEFAULT CHARSET=utf8  COMMENT='追偿跟踪表 - 诉讼-执行';
 
 
 
 ####   追偿跟踪表 - 诉讼-执行-执行内容    20160728
  
DROP TABLE IF EXISTS `project_recovery_litigation_execute_stuff`;
CREATE TABLE `project_recovery_litigation_execute_stuff` (
   `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `project_recovery_id` BIGINT(20) NOT NULL COMMENT '追偿主表主键',
   `project_recovery_litigation_execute_id` BIGINT(20) NOT NULL COMMENT '追偿执行表主键',
   `describe_type` VARCHAR(50) DEFAULT NULL COMMENT '描述',
   `value_stuff` VARCHAR(128) DEFAULT NULL COMMENT '内容值',
   `raw_add_time` TIMESTAMP NULL DEFAULT NULL,
   `raw_update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   KEY `project_recovery_id_index` (`project_recovery_id`) USING BTREE ,
   KEY `project_recovery_litigation_execute_id_index` (`project_recovery_litigation_execute_id`) USING BTREE 
 ) ENGINE=INNODB  DEFAULT CHARSET=utf8  COMMENT='追偿跟踪表 - 诉讼-执行-执行内容';
 
 
 ###     追偿跟踪表 - 诉讼-再审程序-一审  20160728
 
 
DROP TABLE IF EXISTS `project_recovery_litigation_adjourned_procedure`;
CREATE TABLE `project_recovery_litigation_adjourned_procedure` (
   `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `project_recovery_id` BIGINT(20) NOT NULL COMMENT '追偿主表主键',
   `procedure_type` VARCHAR(50) DEFAULT NULL COMMENT '一审/二审',
   `opening_time` TIMESTAMP NULL DEFAULT NULL COMMENT '开庭时间',
   `witness_attorney` VARCHAR(50) DEFAULT NULL COMMENT '出庭律师',
   `we_litigation_demand` VARCHAR(1024) DEFAULT NULL COMMENT '我方主要诉讼请求或答辩意见',
   `other_side_litigation_demand` VARCHAR(1024) DEFAULT NULL COMMENT '对方主要诉讼请求或答辩意见',
   `controversy_focus` VARCHAR(1024) DEFAULT NULL COMMENT '争议焦点',
   `additional_evidence` VARCHAR(1024) DEFAULT NULL COMMENT '补充证据',
   `memo` VARCHAR(1024) DEFAULT NULL COMMENT '备注',
   `raw_add_time` TIMESTAMP NULL DEFAULT NULL,
   `raw_update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   KEY `project_recovery_id_index` (`project_recovery_id`) USING BTREE 
 ) ENGINE=INNODB  DEFAULT CHARSET=utf8  COMMENT='追偿跟踪表 - 诉讼-再审程序';
 
 
 ###    追偿跟踪表 - 诉讼-再审程序-二审  20160728
 
 
 CREATE TABLE `project_recovery_litigation_adjourned_second` (
   `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `project_recovery_id` BIGINT(20) NOT NULL COMMENT '追偿主表主键',
   `appeal_center` VARCHAR(50) DEFAULT NULL COMMENT '上诉主体',
   `appeal_demand` VARCHAR(50) DEFAULT NULL COMMENT '上诉请求',
   `notice_time` TIMESTAMP NULL DEFAULT NULL COMMENT '公告时间',
   `opening_time` TIMESTAMP NULL DEFAULT NULL COMMENT '开庭时间',
   `new_evidence` VARCHAR(50) DEFAULT NULL COMMENT '新证据',
   `controversy_focus` VARCHAR(1024) DEFAULT NULL COMMENT '争议焦点',
   `memo` VARCHAR(1024) DEFAULT NULL COMMENT '备注',
   `raw_add_time` TIMESTAMP NULL DEFAULT NULL,
   `raw_update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   KEY `project_recovery_id_index` (`project_recovery_id`) USING BTREE 
 ) ENGINE=INNODB  DEFAULT CHARSET=utf8  COMMENT='追偿跟踪表 - 诉讼-再审程序-二审';
 
 ####    追偿跟踪表 - 诉讼-执行回转 20160728  
 
 
 CREATE TABLE `project_recovery_litigation_execute_gyration` (
   `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `project_recovery_id` BIGINT(20) NOT NULL COMMENT '追偿主表主键',
   `memo` VARCHAR(1024) DEFAULT NULL COMMENT '备注',
   `raw_add_time` TIMESTAMP NULL DEFAULT NULL,
   `raw_update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   KEY `project_recovery_id_index` (`project_recovery_id`) USING BTREE 
 ) ENGINE=INNODB  DEFAULT CHARSET=utf8  COMMENT='追偿跟踪表 - 诉讼-执行回转';
 
 
 ### 20160803  追偿跟踪表 - 通知函
 
  CREATE TABLE `project_recovery_notice_letter` (
   `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `project_recovery_id` BIGINT(20) NOT NULL COMMENT '追偿主表主键',
   `letter_type`  VARCHAR(50) DEFAULT NULL COMMENT  '函件类型',
   `content`  VARCHAR(50) DEFAULT NULL COMMENT  '函件内容',
   `content_message` VARCHAR(50)  DEFAULT NULL COMMENT '函件内容展示',
   `letter_status`  VARCHAR(50) DEFAULT NULL COMMENT  '函件状态',
   `raw_add_time` TIMESTAMP NULL DEFAULT NULL,
   `raw_update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   KEY `project_recovery_id_index` (`project_recovery_id`) USING BTREE 
 ) ENGINE=INNODB  DEFAULT CHARSET=utf8  COMMENT='追偿跟踪表 - 通知函';
 
 
 
 
 ### 20161028 追偿表 其他信息表
 
 CREATE TABLE `project_recovery_other` (
   `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `project_recovery_id` BIGINT(20) NOT NULL COMMENT '追偿主表主键',
   `memo` mediumtext COMMENT '备注',
   `raw_add_time` TIMESTAMP NULL DEFAULT NULL,
   `raw_update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   KEY `project_recovery_id_index` (`project_recovery_id`)
 ) ENGINE=INNODB  DEFAULT CHARSET=utf8 COMMENT='追偿跟踪表 - 其他信息表';
 
 
 
 