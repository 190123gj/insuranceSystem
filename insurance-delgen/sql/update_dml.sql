SET NAMES 'utf8';

--
-- Definition for table afterwards_project_summary
--
DROP TABLE IF EXISTS afterwards_project_summary;
CREATE TABLE afterwards_project_summary (
  summary_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '保后项目汇总ID',
  dept_id bigint(20) DEFAULT NULL COMMENT '所属部门',
  dept_code varchar(30) DEFAULT NULL COMMENT '部门编号',
  dept_name varchar(50) DEFAULT NULL COMMENT '所属部门名称',
  report_period varchar(50) DEFAULT NULL COMMENT '所属报告期',
  submit_man_id bigint(20) DEFAULT NULL COMMENT '提交人id',
  submit_man_name varchar(30) DEFAULT NULL COMMENT '提交人名称',
  submit_man_account varchar(30) DEFAULT NULL COMMENT '提交人账号',
  guarantee_households int(11) DEFAULT NULL COMMENT '担保户数',
  guarantee_releasing_amount bigint(20) DEFAULT NULL COMMENT '担保在保余额',
  loan_households int(11) DEFAULT NULL COMMENT '委贷户数',
  loan_releasing_amount bigint(20) NOT NULL COMMENT '委贷在保余额',
  credit_risk text DEFAULT NULL COMMENT '本部授信项目风险分类情况',
  credit_after_check text DEFAULT NULL COMMENT '本部授信后现场检查情况',
  credit_after_check_prob text DEFAULT NULL COMMENT '授信后检查发现的问题及相应措施',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (summary_id),
  INDEX afterwards_project_summary_sheet_i (dept_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '保后项目汇总';

--
-- Definition for table assess_company
--
DROP TABLE IF EXISTS assess_company;
CREATE TABLE assess_company (
  company_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '公司ID',
  company_name varchar(128) DEFAULT NULL COMMENT '公司名称',
  region varchar(16) DEFAULT NULL COMMENT '地域属性',
  contact_name varchar(128) DEFAULT NULL COMMENT '联系人',
  contact_number varchar(128) DEFAULT NULL COMMENT '联系电话',
  city_code varchar(128) DEFAULT NULL COMMENT '所属区域 -城市代码',
  city varchar(128) DEFAULT NULL COMMENT '所属区域 -所在城市名称',
  country_code varchar(50) DEFAULT NULL COMMENT '所属区域 - 国家编码',
  country_name varchar(50) DEFAULT NULL COMMENT '所属区域 - 国家名称',
  province_code varchar(50) DEFAULT NULL COMMENT '所属区域 - 省编码',
  province_name varchar(50) DEFAULT NULL COMMENT '所属区域 - 省名称',
  county_name varchar(128) DEFAULT NULL COMMENT '所属区域 - 地区名称',
  county_code varchar(20) DEFAULT NULL COMMENT '所属区域 - 地区编码',
  delete_mark varchar(1) DEFAULT '0' COMMENT '删除标记(0:活动、1:已删除)',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (company_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '基础数据管理-评估公司';

--
-- Definition for table busi_type
--
DROP TABLE IF EXISTS busi_type;
CREATE TABLE busi_type (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  code varchar(10) DEFAULT NULL COMMENT '编码',
  name varchar(50) DEFAULT NULL COMMENT '名称',
  parent_id int(11) DEFAULT NULL COMMENT '父节点',
  customer_type varchar(10) DEFAULT NULL COMMENT '类型',
  has_children varchar(10) DEFAULT NULL COMMENT '是否有子节点',
  setup_form_code varchar(128) DEFAULT NULL COMMENT '对应立项的表单编码',
  sort_order int(11) DEFAULT NULL COMMENT '排序',
  is_del varchar(10) DEFAULT NULL COMMENT '是否可用',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '业务类型';

--
-- Definition for table channel
--
DROP TABLE IF EXISTS channel;
CREATE TABLE channel (
  channel_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  channel_code bigint(20) NOT NULL COMMENT '编码',
  channel_name varchar(128) DEFAULT NULL COMMENT '渠道名称',
  channel_type int(11) DEFAULT NULL COMMENT '渠道分类',
  channel_type_name varchar(20) DEFAULT NULL COMMENT '渠道分类名称',
  cash_deposit_rate decimal(12, 4) DEFAULT NULL COMMENT '保证金比例',
  refundable_deposit_rate decimal(12, 4) DEFAULT NULL COMMENT '存出保证金利率',
  loss_share_rate decimal(12, 4) DEFAULT NULL COMMENT '损失分摊比例',
  amount_limit bigint(20) DEFAULT NULL COMMENT '授信额度',
  final_credit_balance bigint(20) DEFAULT NULL COMMENT '期末授信余额',
  start_date date DEFAULT NULL COMMENT '授信起始日',
  end_date date DEFAULT NULL COMMENT '授信截止日',
  deadline_time_limit int(11) DEFAULT NULL COMMENT '代偿期限',
  deadline_time_unit varchar(10) DEFAULT NULL COMMENT '代偿期限单位',
  sort_order int(11) DEFAULT 0,
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (channel_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '渠道管理';

--
-- Definition for table channel_type
--
DROP TABLE IF EXISTS channel_type;
CREATE TABLE channel_type (
  channel_type int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  channel_type_name varchar(20) DEFAULT NULL COMMENT '渠道分类名称',
  channel_names text DEFAULT NULL COMMENT '渠道名（明细名字的拼接）',
  remark varchar(128) DEFAULT NULL COMMENT '备注',
  sort_order int(11) DEFAULT 0,
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (channel_type)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '渠道分类管理';

--
-- Definition for table common_attachment
--
DROP TABLE IF EXISTS common_attachment;
CREATE TABLE common_attachment (
  attachment_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '附件id',
  biz_no varchar(30) DEFAULT NULL COMMENT '交易(引用外部数据id)流水号',
  module_type varchar(40) DEFAULT NULL COMMENT '附件所属模块类型，暂时初审带的附件',
  check_status varchar(40) DEFAULT NULL COMMENT '是否已经被审核',
  file_name varchar(100) DEFAULT NULL COMMENT '文件名',
  isort bigint(20) DEFAULT NULL COMMENT '序号',
  file_physical_path varchar(500) DEFAULT NULL COMMENT '物理路径',
  request_path varchar(500) DEFAULT NULL COMMENT '请求路径',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (attachment_id),
  INDEX biz_no_module_type_i (biz_no, module_type)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Definition for table consent_issue_notice
--
DROP TABLE IF EXISTS consent_issue_notice;
CREATE TABLE consent_issue_notice (
  notice_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '通知书ID',
  project_code varchar(50) DEFAULT NULL COMMENT '项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  customer_type varchar(10) DEFAULT NULL COMMENT '客户类型',
  busi_type varchar(50) DEFAULT NULL COMMENT '业务类型',
  busi_type_name varchar(50) DEFAULT NULL COMMENT '业务类型名称',
  busi_manager_id bigint(20) DEFAULT NULL COMMENT '客户经理ID',
  busi_manager_account varchar(20) DEFAULT NULL COMMENT '客户经理账号',
  busi_manager_name varchar(50) DEFAULT NULL COMMENT '客户经理名称',
  is_upload_receipt varchar(10) DEFAULT NULL COMMENT '是否上传回执',
  receipt_attachment text DEFAULT NULL COMMENT '回执附件',
  your_cooperation_company varchar(128) DEFAULT NULL COMMENT '与贵司合作的公司',
  your_cooperation_attachment varchar(128) DEFAULT NULL COMMENT '与贵司合作公司签订的文件',
  my_cooperation_company varchar(128) DEFAULT NULL COMMENT '与我司合作的公司',
  my_cooperation_attachment varchar(128) DEFAULT NULL COMMENT '与我司合作公司签订的文件',
  my_cooperation_contract_no varchar(30) DEFAULT NULL COMMENT '与我司合作公司签订的合同编号',
  html text DEFAULT NULL COMMENT '打印详情html',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (notice_id),
  INDEX consent_issue_noticet_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '同意发行通知书(发债类项目)';

--
-- Definition for table contract_template
--
DROP TABLE IF EXISTS contract_template;
CREATE TABLE contract_template (
  template_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  name varchar(128) NOT NULL COMMENT '模板名',
  contract_type varchar(50) NOT NULL COMMENT '模板类别(制式/非制式)',
  busi_type varchar(50) DEFAULT NULL COMMENT '业务类型',
  busi_type_name varchar(50) DEFAULT NULL COMMENT '业务类型名称',
  is_main varchar(10) DEFAULT NULL COMMENT '是否为某业务类型主合同',
  credit_condition_type varchar(50) DEFAULT NULL COMMENT '反担保措施',
  pledge_type varchar(50) DEFAULT NULL COMMENT '抵押品类型',
  stamp_phase varchar(50) DEFAULT NULL COMMENT '用印阶段',
  template_file varchar(256) DEFAULT NULL COMMENT '模板附件（非制式）',
  status varchar(50) DEFAULT NULL COMMENT '模板状态(使用中/暂存)',
  template_content mediumtext DEFAULT NULL COMMENT '模板内容（制式）',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (template_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '合同模板';

--
-- Definition for table council
--
DROP TABLE IF EXISTS council;
CREATE TABLE council (
  council_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '会议ID',
  council_code varchar(20) DEFAULT NULL COMMENT '会议编号',
  council_subject varchar(512) DEFAULT NULL COMMENT '会议主题',
  start_time datetime DEFAULT NULL COMMENT '会议开始时间',
  end_time datetime DEFAULT NULL COMMENT '会议结束时间',
  council_type bigint(20) DEFAULT NULL COMMENT '会议类型',
  council_type_code varchar(20) DEFAULT NULL COMMENT '会议类型',
  council_type_name varchar(50) DEFAULT NULL COMMENT '会议名称',
  council_place varchar(128) DEFAULT NULL COMMENT '会议地点',
  status varchar(64) DEFAULT NULL COMMENT '状态',
  decision_institution_id bigint(20) DEFAULT NULL COMMENT '决策机构ID',
  major_num int(11) DEFAULT NULL COMMENT '最高评委人数',
  less_num int(11) DEFAULT NULL COMMENT '最低评委人数',
  if_vote varchar(10) DEFAULT NULL COMMENT '是否投票',
  vote_rule_type varchar(20) DEFAULT NULL COMMENT '投票规则类型（通过率/通过人数）',
  pass_num int(11) DEFAULT NULL COMMENT '通过人数（超过该值 - 项目通过）',
  indeterminate_num int(11) DEFAULT NULL COMMENT '本次不议人数（超过该值 - 项目重新上会）',
  pass_rate decimal(12, 4) DEFAULT NULL COMMENT '通过率',
  indeterminate_rate decimal(12, 4) DEFAULT NULL COMMENT '本次不议率',
  create_man_id bigint(20) DEFAULT NULL COMMENT '会议创建人ID',
  create_man_account varchar(20) DEFAULT NULL COMMENT '会议创建人账号',
  create_man_name varchar(50) DEFAULT NULL COMMENT '会议创建人名称',
  summary_url text DEFAULT NULL COMMENT '总经理办公会 - 上传的会议纪要地址（附件）',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (council_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '会议信息';

--
-- Definition for table council_apply
--
DROP TABLE IF EXISTS council_apply;
CREATE TABLE council_apply (
  apply_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '申请ID',
  project_code varchar(50) DEFAULT NULL COMMENT '对应项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '对应项目名称',
  form_id bigint(20) DEFAULT NULL COMMENT '对应表单ID',
  amount bigint(20) DEFAULT NULL COMMENT '授信金额',
  time_limit int(11) DEFAULT NULL COMMENT '期限',
  time_unit varchar(10) DEFAULT NULL COMMENT '期限单位',
  customer_id bigint(20) DEFAULT NULL COMMENT '客户ID',
  customer_name varchar(50) DEFAULT NULL COMMENT '客户名称',
  apply_man_id bigint(20) DEFAULT NULL COMMENT '申请人',
  apply_man_name varchar(50) DEFAULT NULL COMMENT '申请人名称',
  apply_dept_id bigint(20) DEFAULT NULL COMMENT '申请部门',
  apply_dept_name varchar(50) DEFAULT NULL COMMENT '申请部门名称',
  council_code varchar(20) DEFAULT NULL COMMENT '会议类型',
  council_type bigint(20) DEFAULT NULL COMMENT '会议类型ID',
  council_type_desc varchar(50) DEFAULT NULL COMMENT '会议类型描述',
  apply_time datetime DEFAULT NULL COMMENT '提交申请时间',
  status varchar(10) NOT NULL DEFAULT 'WAIT' COMMENT '状态',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (apply_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目上会申请（待上会项目列表）';

--
-- Definition for table council_judges
--
DROP TABLE IF EXISTS council_judges;
CREATE TABLE council_judges (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  council_id bigint(20) DEFAULT NULL COMMENT '会议ID',
  council_code varchar(20) DEFAULT NULL COMMENT '会议编号',
  judge_id bigint(20) DEFAULT NULL COMMENT '评委ID',
  judge_name varchar(20) DEFAULT NULL COMMENT '评委名称',
  judge_account varchar(20) DEFAULT NULL COMMENT '评委账号',
  sort_order int(11) DEFAULT 0,
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '会议信息 - 会议评委';

--
-- Definition for table council_participants
--
DROP TABLE IF EXISTS council_participants;
CREATE TABLE council_participants (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  council_id bigint(20) DEFAULT NULL COMMENT '会议ID',
  council_code varchar(20) DEFAULT NULL COMMENT '会议编号',
  participant_id bigint(20) DEFAULT NULL COMMENT '列席人员ID',
  participant_name varchar(50) DEFAULT NULL COMMENT '列席人员名称',
  sort_order int(11) DEFAULT 0,
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '会议信息 - 会议列席人员';

--
-- Definition for table council_project_vote
--
DROP TABLE IF EXISTS council_project_vote;
CREATE TABLE council_project_vote (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  council_project_id bigint(20) DEFAULT NULL COMMENT '对应某次会议的某项目',
  council_id bigint(20) DEFAULT NULL COMMENT '会议ID',
  council_code varchar(20) DEFAULT NULL COMMENT '会议编号',
  project_code varchar(50) DEFAULT NULL COMMENT '项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '项目编号',
  judge_id bigint(20) DEFAULT NULL COMMENT '评委ID',
  judge_name varchar(20) DEFAULT NULL COMMENT '评委名称',
  role_id bigint(20) DEFAULT NULL COMMENT '评委角色ID',
  role_name varchar(20) DEFAULT NULL COMMENT '评委角色名称',
  org_name varchar(50) DEFAULT NULL COMMENT '主部门',
  vote_status varchar(10) DEFAULT 'NO' COMMENT '投票状态(是否已投票)',
  vote_result varchar(20) DEFAULT 'UNKNOWN' COMMENT '投票结果',
  vote_result_desc varchar(20) DEFAULT NULL COMMENT '投票结果描述',
  vote_remark text DEFAULT NULL COMMENT '投票结果备注',
  sort_order int(11) DEFAULT 0,
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '会议信息 - 投票信息';

--
-- Definition for table council_projects
--
DROP TABLE IF EXISTS council_projects;
CREATE TABLE council_projects (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  council_id bigint(20) DEFAULT NULL COMMENT '会议ID',
  council_code varchar(20) DEFAULT NULL COMMENT '会议编号',
  apply_id bigint(20) DEFAULT NULL COMMENT '申请ID',
  project_code varchar(50) DEFAULT NULL COMMENT '项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '项目名称',
  sort_order int(11) DEFAULT 0,
  risk_secretary_quit varchar(20) DEFAULT NULL COMMENT '是否本次不议',
  risk_secretary_quit_mark text DEFAULT NULL COMMENT '被本次不议的理由',
  one_vote_down varchar(20) DEFAULT 'NO' COMMENT '是否被一票否决',
  one_vote_down_mark text DEFAULT NULL COMMENT '被一票否决的原因',
  project_vote_result varchar(50) DEFAULT NULL COMMENT '投票结果',
  judges_count int(11) DEFAULT 0 COMMENT '总投票人数',
  pass_count int(11) DEFAULT 0 COMMENT '投通赞成票累计人数',
  notpass_count int(11) DEFAULT 0 COMMENT '投通反对票累计人数',
  quit_count int(11) DEFAULT 0 COMMENT '投通弃权票累计人数',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '会议信息 - 会议讨论项目';

--
-- Definition for table council_type
--
DROP TABLE IF EXISTS council_type;
CREATE TABLE council_type (
  type_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  type_code varchar(20) DEFAULT NULL COMMENT '类型code',
  type_name varchar(50) DEFAULT NULL COMMENT '会议类型名称',
  decision_institution_id bigint(20) DEFAULT NULL COMMENT '决策机构ID',
  decision_institution_name varchar(30) DEFAULT NULL COMMENT '决策机构名称',
  apply_dept_id text DEFAULT NULL COMMENT '适用公司/部门id',
  apply_company text DEFAULT NULL COMMENT '适用公司/部门',
  major_num int(11) DEFAULT NULL COMMENT '最高评委人数',
  less_num int(11) DEFAULT NULL COMMENT '最低评委人数',
  if_vote varchar(10) DEFAULT NULL COMMENT '是否投票',
  vote_rule_type varchar(20) DEFAULT NULL COMMENT '投票规则类型（通过率/通过人数）',
  pass_num int(11) DEFAULT NULL COMMENT '通过人数（超过该值 - 项目通过）',
  indeterminate_num int(11) DEFAULT NULL COMMENT '本次不议人数（超过该值 - 项目重新上会）',
  pass_rate decimal(12, 4) DEFAULT NULL COMMENT '通过率',
  indeterminate_rate decimal(12, 4) DEFAULT NULL COMMENT '本次不议率',
  summary_code_prefix varchar(50) DEFAULT NULL COMMENT '会议纪要编号前缀',
  delete_mark varchar(10) DEFAULT NULL COMMENT '删除标记(0:活动,1:删除)',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (type_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '会议类型';

--
-- Definition for table db_field
--
DROP TABLE IF EXISTS db_field;
CREATE TABLE db_field (
  field_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  table_id bigint(20) DEFAULT NULL COMMENT '表ID',
  field_name varchar(50) DEFAULT NULL COMMENT '字段名称',
  field_for_short varchar(50) DEFAULT NULL COMMENT '字段简称',
  remark varchar(255) DEFAULT NULL COMMENT '说明',
  is_delete varchar(20) DEFAULT NULL COMMENT '删除标记',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (field_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '常用数据库字段维护';

--
-- Definition for table db_table
--
DROP TABLE IF EXISTS db_table;
CREATE TABLE db_table (
  table_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  table_name varchar(50) DEFAULT NULL COMMENT '表名',
  project_phase varchar(50) DEFAULT NULL COMMENT '项目阶段',
  table_for_short varchar(50) DEFAULT NULL COMMENT '表简称',
  class_name varchar(50) DEFAULT NULL COMMENT '类名',
  remark varchar(255) DEFAULT NULL COMMENT '说明',
  is_delete varchar(20) DEFAULT NULL COMMENT '删除标记',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (table_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '常用数据库表维护';

--
-- Definition for table decision_institution
--
DROP TABLE IF EXISTS decision_institution;
CREATE TABLE decision_institution (
  institution_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '机构ID',
  institution_name varchar(128) DEFAULT NULL COMMENT '机构名称',
  institution_members varchar(256) DEFAULT NULL COMMENT '机构人员列表(人员名称)',
  delete_mark varchar(1) DEFAULT '0' COMMENT '删除标记(0:活动,1:删除)',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (institution_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '基础数据管理-决策机构';

--
-- Definition for table decision_member
--
DROP TABLE IF EXISTS decision_member;
CREATE TABLE decision_member (
  member_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  institution_id bigint(20) NOT NULL COMMENT '所属决策机构ID',
  user_id bigint(20) NOT NULL COMMENT '成员ID',
  user_account varchar(50) DEFAULT NULL COMMENT '成员账号',
  user_name varchar(50) DEFAULT NULL COMMENT '成员真实名字',
  sort_order int(11) DEFAULT 0,
  delete_mark varchar(1) DEFAULT '0' COMMENT '删除标记(0:活动,1:删除)',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (member_id),
  INDEX institution_id_i (institution_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '基础数据管理-决策机构人员';

--
-- Definition for table enterprise_scale_rule
--
DROP TABLE IF EXISTS enterprise_scale_rule;
CREATE TABLE enterprise_scale_rule (
  rule_id int(11) NOT NULL AUTO_INCREMENT COMMENT '规则ID',
  industry_code varchar(128) DEFAULT NULL COMMENT '对应行业编码',
  industry_name varchar(50) DEFAULT NULL COMMENT '行业描述',
  kpi_type varchar(15) DEFAULT NULL COMMENT '指标类型',
  kpi_name varchar(50) DEFAULT NULL COMMENT '指标名称',
  kpi_unit varchar(10) DEFAULT NULL COMMENT '计量单位',
  kpi_variable varchar(10) DEFAULT NULL COMMENT '指标变量',
  scale_huge_script varchar(50) DEFAULT NULL COMMENT '特大企业表达式',
  scale_big_script varchar(50) DEFAULT NULL COMMENT '大型企业表达式',
  scale_medium_script varchar(50) DEFAULT NULL COMMENT '中型企业表达式',
  scale_small_script varchar(50) DEFAULT NULL COMMENT '小型企业表达式',
  scale_tiny_script varchar(50) DEFAULT NULL COMMENT '微型企业表达式',
  version varchar(10) DEFAULT 'NOW' COMMENT '版本',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (rule_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '企业规模计算规则';

--
-- Definition for table expire_project
--
DROP TABLE IF EXISTS expire_project;
CREATE TABLE expire_project (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  project_code varchar(50) DEFAULT NULL COMMENT '项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '项目名称',
  expire_date datetime DEFAULT NULL COMMENT '到期时间',
  status varchar(20) DEFAULT NULL COMMENT '到期状态：到期、逾期、已完成',
  receipt text DEFAULT NULL COMMENT '回执',
  repay_certificate text DEFAULT NULL COMMENT '还款凭证地址',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  INDEX f_financial_project_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '到期项目列表';

--
-- Definition for table expire_project_notice_template
--
DROP TABLE IF EXISTS expire_project_notice_template;
CREATE TABLE expire_project_notice_template (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '模板ID',
  expire_id bigint(20) DEFAULT NULL COMMENT 'expire_project表id',
  year varchar(10) DEFAULT NULL COMMENT '通字(年)',
  sequence varchar(10) DEFAULT 'NULL' COMMENT '序号',
  project_code varchar(50) DEFAULT NULL COMMENT '对应项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '对应项目名称',
  exhibition_period_protocol_no varchar(20) DEFAULT NULL COMMENT '展期协议编号',
  borrow_money bigint(20) DEFAULT 0 COMMENT '借款本金',
  operator_id bigint(20) DEFAULT 0 COMMENT '经办人id',
  operator varchar(20) DEFAULT NULL COMMENT '经办人',
  issue_date datetime DEFAULT NULL COMMENT '签发时间',
  expiration_date datetime DEFAULT NULL COMMENT '到期时间',
  interest bigint(20) DEFAULT 0 COMMENT '利息',
  full_in_numbers bigint(20) DEFAULT 0 COMMENT '填写份数',
  return_numbers bigint(20) DEFAULT 0 COMMENT '返回份数',
  status varchar(20) DEFAULT NULL COMMENT '到期状态：到期、逾期、已完成',
  remark varchar(512) DEFAULT NULL COMMENT '备注',
  html text DEFAULT NULL COMMENT '通知模板html',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '到期通知书模板';

--
-- Definition for table f_afterwards_check
--
DROP TABLE IF EXISTS f_afterwards_check;
CREATE TABLE f_afterwards_check (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '调查ID',
  form_id bigint(20) NOT NULL COMMENT '对应的表单ID',
  project_code varchar(50) DEFAULT NULL COMMENT '对应项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '对应项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  check_date date DEFAULT NULL COMMENT '现场监管日期',
  check_address varchar(256) DEFAULT NULL COMMENT '现场监管地址',
  edition varchar(32) DEFAULT NULL COMMENT '报告类型（版本）',
  round_year int(11) DEFAULT 0 COMMENT '期数:年',
  round_time int(11) DEFAULT 0 COMMENT '期次',
  amount bigint(20) DEFAULT NULL COMMENT '授信金额（方便计算(在保)余额）',
  used_amount bigint(20) DEFAULT NULL COMMENT '累计使用金额',
  repayed_amount bigint(20) DEFAULT NULL COMMENT '累计还款金额',
  use_way varchar(20) DEFAULT NULL COMMENT '用款方式',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  INDEX f_afterwards_check_form_id_i (form_id),
  INDEX f_afterwards_check_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '保后检查 - 项目基本信息';

--
-- Definition for table f_afterwards_check_asset_liability
--
DROP TABLE IF EXISTS f_afterwards_check_asset_liability;
CREATE TABLE f_afterwards_check_asset_liability (
  asset_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) NOT NULL COMMENT '对应的表单ID',
  project_code varchar(50) DEFAULT NULL COMMENT '对应项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '对应项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  item_type varchar(50) DEFAULT NULL COMMENT '资产/负债',
  item_sub_type varchar(50) DEFAULT NULL COMMENT '科目子类型',
  remark text DEFAULT NULL COMMENT '现场查看说明',
  sort_order int(11) DEFAULT 0,
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (asset_id),
  INDEX f_afterwards_check_asset_form_id_id (form_id),
  INDEX f_afterwards_check_asset_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '保后检查 - 企业资产/负债情况';

--
-- Definition for table f_afterwards_check_asset_liability_item
--
DROP TABLE IF EXISTS f_afterwards_check_asset_liability_item;
CREATE TABLE f_afterwards_check_asset_liability_item (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  asset_id bigint(20) NOT NULL COMMENT '资产类别ID',
  item varchar(50) NOT NULL COMMENT '科目明细/交易对手/对方单位',
  original_receipt_amount bigint(20) DEFAULT NULL COMMENT '原始单据核实金额',
  original_receipt_num int(11) DEFAULT NULL COMMENT '原始单据份数',
  original_receipt_age varchar(20) DEFAULT NULL COMMENT '原始单据账龄',
  estimated_bad_amount bigint(20) DEFAULT NULL COMMENT '估计坏账金额',
  sort_order int(11) DEFAULT 0,
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '保后检查 - 企业资产/负债明细';

--
-- Definition for table f_afterwards_check_base
--
DROP TABLE IF EXISTS f_afterwards_check_base;
CREATE TABLE f_afterwards_check_base (
  base_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) NOT NULL COMMENT '对应的表单ID',
  spend_way varchar(256) DEFAULT NULL COMMENT '用款方式',
  collect_year int(11) DEFAULT 0 COMMENT '本次回访记录搜集资料期间:年份',
  collect_month int(11) DEFAULT 0 COMMENT '本次回访记录搜集资料期间:月份',
  feedback_opinion text DEFAULT NULL COMMENT '向授信客户反馈意见',
  customer_opinion text DEFAULT NULL COMMENT '授信客户意见',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (base_id),
  INDEX f_afterwards_check_base_form_id_i (form_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '保后检查 - 基本情况';

--
-- Definition for table f_afterwards_check_collection
--
DROP TABLE IF EXISTS f_afterwards_check_collection;
CREATE TABLE f_afterwards_check_collection (
  collect_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '收集ID',
  form_id bigint(20) NOT NULL COMMENT '对应的表单ID',
  collect_type varchar(32) DEFAULT NULL COMMENT '类型:(反)担保方式|收集的资料',
  collect_item varchar(32) DEFAULT NULL COMMENT '内容名称',
  collect_code varchar(32) DEFAULT NULL COMMENT '标识',
  collected varchar(16) DEFAULT NULL COMMENT '是否收集:YES/NO',
  sort_order int(11) DEFAULT 0,
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (collect_id),
  INDEX f_afterwards_check_collection_form_id_id (form_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '保后检查 - 资料收集情况';

--
-- Definition for table f_afterwards_check_collection_doc
--
DROP TABLE IF EXISTS f_afterwards_check_collection_doc;
CREATE TABLE f_afterwards_check_collection_doc (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) NOT NULL COMMENT '对应表单ID',
  project_code varchar(50) DEFAULT NULL COMMENT '对应项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '对应项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  doc_name varchar(50) DEFAULT NULL COMMENT '附件名称',
  doc_url varchar(128) DEFAULT NULL COMMENT '附件位置',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  INDEX f_afterwards_check_collection_doc_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '保后检查 - 附件上传';

--
-- Definition for table f_afterwards_check_content
--
DROP TABLE IF EXISTS f_afterwards_check_content;
CREATE TABLE f_afterwards_check_content (
  content_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '监管内容ID',
  form_id bigint(20) NOT NULL COMMENT '对应的表单ID',
  project_code varchar(50) DEFAULT NULL COMMENT '对应项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '对应项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  analysis_conclusion text DEFAULT NULL COMMENT '监管内容分析与结论',
  other_explain text DEFAULT NULL COMMENT '其他需说明的事项',
  related_enterprise text DEFAULT NULL COMMENT '关联企业检查情况',
  management_matters text DEFAULT NULL COMMENT '重大经营管理事项检查',
  customer_suggestion text DEFAULT NULL COMMENT '授信客户意见',
  feedback text DEFAULT NULL COMMENT '向授信客户反馈意见',
  use_way_conditions text DEFAULT NULL COMMENT '授信的用途、还息及纳税检查',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (content_id),
  INDEX f_afterwards_check_content_form_id_i (form_id),
  INDEX f_afterwards_check_content_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '保后检查 - 监管内容';

--
-- Definition for table f_afterwards_check_content_concern
--
DROP TABLE IF EXISTS f_afterwards_check_content_concern;
CREATE TABLE f_afterwards_check_content_concern (
  cc_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '预警信号或关注事项',
  form_id bigint(20) NOT NULL COMMENT '对应的表单ID',
  project_code varchar(50) DEFAULT NULL COMMENT '对应项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '对应项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  type varchar(20) NOT NULL COMMENT '关注事项类型（担保措施检查|其他重要事项核查|预警信号）',
  other text DEFAULT NULL COMMENT '检查内容(其他重要事项检查)',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (cc_id),
  INDEX f_afterwards_check_content_counter_guarantee_form_id_i (form_id),
  INDEX f_afterwards_check_content_counter_guarantee_peoject_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '保后检查 - 监管内容 - 关注事项';

--
-- Definition for table f_afterwards_check_content_concern_item
--
DROP TABLE IF EXISTS f_afterwards_check_content_concern_item;
CREATE TABLE f_afterwards_check_content_concern_item (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '关注事项ID',
  cc_id bigint(20) NOT NULL COMMENT '反担保情况ID',
  type varchar(10) DEFAULT NULL COMMENT '类型(担保措施检查/其他重要事项检查)',
  item_type varchar(20) DEFAULT NULL COMMENT '关注事项类型标识',
  item_type_desc varchar(512) DEFAULT NULL COMMENT '关注事项描述',
  item_value varchar(512) DEFAULT NULL COMMENT '事项值',
  remark varchar(128) DEFAULT NULL COMMENT '说明',
  sort_order int(11) DEFAULT 0,
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '保后检查 - 监管内容 - 关注事项明细';

--
-- Definition for table f_afterwards_check_content_financal
--
DROP TABLE IF EXISTS f_afterwards_check_content_financal;
CREATE TABLE f_afterwards_check_content_financal (
  cf_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '财务检查ID',
  form_id bigint(20) NOT NULL COMMENT '对应的表单ID',
  project_code varchar(50) DEFAULT NULL COMMENT '对应项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '对应项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  check_content text DEFAULT NULL COMMENT '检查内容(授信的用途、还息及纳税等检查)',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (cf_id),
  INDEX f_afterwards_check_content_financal_form_id_i (form_id),
  INDEX f_afterwards_check_content_financal_peoject_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '保后检查 - 监管内容 - 财务状况检查';

--
-- Definition for table f_afterwards_check_content_financal_kpi
--
DROP TABLE IF EXISTS f_afterwards_check_content_financal_kpi;
CREATE TABLE f_afterwards_check_content_financal_kpi (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '财务检查ID',
  cf_id bigint(20) NOT NULL COMMENT '财务检查ID',
  kpi_code varchar(20) DEFAULT NULL COMMENT '指标标识',
  kpi_name varchar(50) DEFAULT NULL COMMENT '指标名',
  kpi_value varchar(20) DEFAULT NULL COMMENT '指标值（存金额或者百分比）',
  kpi_unit varchar(20) DEFAULT NULL COMMENT '指标单位',
  term_type varchar(20) DEFAULT NULL COMMENT '分类（账期yyyyMM）',
  remark varchar(128) DEFAULT NULL COMMENT '说明',
  sort_order int(11) DEFAULT 0,
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '保后检查 - 监管内容 - 财务状况检查';

--
-- Definition for table f_afterwards_check_content_income_cost
--
DROP TABLE IF EXISTS f_afterwards_check_content_income_cost;
CREATE TABLE f_afterwards_check_content_income_cost (
  ic_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '收入成本情况ID',
  form_id bigint(20) NOT NULL COMMENT '对应的表单ID',
  project_code varchar(50) DEFAULT NULL COMMENT '对应项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '对应项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  item varchar(128) DEFAULT NULL COMMENT '科目',
  item_code varchar(20) DEFAULT NULL COMMENT '科目代码',
  item_type varchar(20) DEFAULT NULL COMMENT '科目类别（企业收入/企业成本）',
  remark varchar(512) DEFAULT NULL COMMENT '说明',
  sort_order int(11) DEFAULT NULL COMMENT '排列顺序',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (ic_id),
  INDEX f_afterwards_check_content_income_cost_form_id_i (form_id),
  INDEX f_afterwards_check_content_income_cost_peoject_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '保后检查 - 监管内容 - 企业收入/成本核实情况';

--
-- Definition for table f_afterwards_check_content_income_cost_kpi
--
DROP TABLE IF EXISTS f_afterwards_check_content_income_cost_kpi;
CREATE TABLE f_afterwards_check_content_income_cost_kpi (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  ic_id bigint(20) NOT NULL COMMENT '财务检查ID',
  item varchar(128) DEFAULT NULL COMMENT '科目',
  item_code varchar(50) DEFAULT NULL COMMENT '科目代码',
  item_value varchar(20) DEFAULT NULL COMMENT '指标结果值',
  item_unit varchar(20) DEFAULT NULL COMMENT '指标单位',
  term varchar(20) DEFAULT NULL COMMENT '期次（账期yyyyMM）',
  remark varchar(128) DEFAULT NULL COMMENT '说明',
  sort_order int(11) DEFAULT 0,
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '保后检查 - 监管内容 - 收入核实情况（每期）';

--
-- Definition for table f_afterwards_check_liability
--
DROP TABLE IF EXISTS f_afterwards_check_liability;
CREATE TABLE f_afterwards_check_liability (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) NOT NULL COMMENT '对应的表单ID',
  project_code varchar(50) DEFAULT NULL COMMENT '对应项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '对应项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  item varchar(50) DEFAULT NULL COMMENT '科目',
  item_name varchar(50) DEFAULT NULL COMMENT '科目名称',
  trade_side varchar(128) DEFAULT NULL COMMENT '对方单位',
  original_receipt_amount bigint(20) DEFAULT NULL COMMENT '原始单据核实金额',
  original_receipt_num int(11) DEFAULT NULL COMMENT '原始单据份数',
  remark text DEFAULT NULL COMMENT '分析说明',
  sort_order int(11) DEFAULT 0,
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  INDEX f_afterwards_check_liability_form_id_id (form_id),
  INDEX f_afterwards_check_liability_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '保后检查 - 企业负债情况';

--
-- Definition for table f_afterwards_check_loans
--
DROP TABLE IF EXISTS f_afterwards_check_loans;
CREATE TABLE f_afterwards_check_loans (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) NOT NULL COMMENT '对应的表单ID',
  loan_institution varchar(128) DEFAULT NULL COMMENT '融资机构',
  loan_type varchar(50) DEFAULT NULL COMMENT '授信品种',
  loan_amount bigint(20) DEFAULT NULL COMMENT '授信金额',
  loan_balance bigint(20) DEFAULT NULL COMMENT '用信金额',
  loan_time_limit varchar(50) DEFAULT NULL COMMENT '额度期限',
  interest_rate decimal(12, 4) DEFAULT NULL COMMENT '利率',
  cash_deposit_rate decimal(12, 4) DEFAULT NULL COMMENT '保证金比例',
  loan_expire_date date DEFAULT NULL COMMENT '到期日',
  guarantee_way varchar(50) DEFAULT NULL COMMENT '担保方式',
  remark text DEFAULT NULL COMMENT '额度变化情况',
  del_able varchar(16) DEFAULT NULL COMMENT '是否可删除(页面上)',
  sort_order int(11) DEFAULT 0,
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  INDEX f_afterwards_check_loans_form_id_id (form_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '保后检查 - 银行贷款及其他融资';

--
-- Definition for table f_afterwards_check_report_capital
--
DROP TABLE IF EXISTS f_afterwards_check_report_capital;
CREATE TABLE f_afterwards_check_report_capital (
  capital_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) NOT NULL COMMENT '对应的表单ID',
  capital_type varchar(32) DEFAULT NULL COMMENT '类型',
  capital_item varchar(128) DEFAULT NULL COMMENT '科目名称',
  capital_value1 varchar(128) DEFAULT NULL COMMENT '科目1',
  capital_value2 varchar(128) DEFAULT NULL COMMENT '科目2',
  capital_value3 varchar(128) DEFAULT NULL COMMENT '科目3',
  capital_value4 varchar(128) DEFAULT NULL COMMENT '科目4',
  capital_value5 varchar(128) DEFAULT NULL COMMENT '科目5',
  capital_value6 varchar(128) DEFAULT NULL COMMENT '科目6',
  capital_value7 varchar(128) DEFAULT NULL COMMENT '科目7',
  capital_value8 varchar(128) DEFAULT NULL COMMENT '科目8',
  capital_value9 varchar(128) DEFAULT NULL COMMENT '科目9',
  capital_value10 varchar(128) DEFAULT NULL COMMENT '科目10',
  sort_order int(11) DEFAULT 0,
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (capital_id),
  INDEX f_afterwards_check_report_capital_form_id_i (form_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '保后检查 - 在建项目-前十大客户-按期收回情况等';

--
-- Definition for table f_afterwards_check_report_content
--
DROP TABLE IF EXISTS f_afterwards_check_report_content;
CREATE TABLE f_afterwards_check_report_content (
  content_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  form_id bigint(20) NOT NULL COMMENT '对应的表单ID',
  use_way_conditions text DEFAULT NULL COMMENT '授信的用途、还息及纳税检查(一)',
  project_finish_desc text DEFAULT NULL COMMENT '开发项目完成情况检查|银行贷款及其他融资(二)',
  income_check_desc text DEFAULT NULL COMMENT '企业收入调查工作底稿|企业成本核实情况工作底稿(二)',
  management_matters text DEFAULT NULL COMMENT '重大经营管理事项检查|业务流程(三)',
  decision_way text DEFAULT NULL COMMENT '审贷会的组成及决议方式(三)',
  counter_check text DEFAULT NULL COMMENT '担保措施检查(四)',
  related_enterprise text DEFAULT NULL COMMENT '关联企业检查情况(五)',
  other_explain text DEFAULT NULL COMMENT '其他需说明的事项(八)',
  analysis_conclusion text DEFAULT NULL COMMENT '监管内容分析与结论(九)',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (content_id),
  INDEX form_id (form_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '保后检查-监管内容';

--
-- Definition for table f_afterwards_check_report_debt
--
DROP TABLE IF EXISTS f_afterwards_check_report_debt;
CREATE TABLE f_afterwards_check_report_debt (
  debt_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) NOT NULL COMMENT '对应的表单ID',
  debt_amount bigint(20) DEFAULT 0 COMMENT '划转负债金额（纳入财政预算偿还）',
  debt_institution varchar(128) DEFAULT NULL COMMENT '融资机构',
  debt_type varchar(128) DEFAULT NULL COMMENT '贷款品种',
  debt_use varchar(128) DEFAULT NULL COMMENT '贷款用途（项目贷款的需填写项目名称）',
  due_date date DEFAULT NULL COMMENT '贷款到期日',
  debt_loan_way varchar(256) DEFAULT NULL COMMENT '贷款担保方式（简要描述担保方及担保物情况）',
  trans_amount bigint(20) DEFAULT 0 COMMENT '对应划转的资产',
  trans_gist varchar(256) DEFAULT NULL COMMENT '划转依据',
  sort_order int(11) DEFAULT 0,
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (debt_id),
  INDEX f_afterwards_check_report_debt_form_id_i (form_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '保后检查 - 企业资产负债划转明细';

--
-- Definition for table f_afterwards_check_report_financial
--
DROP TABLE IF EXISTS f_afterwards_check_report_financial;
CREATE TABLE f_afterwards_check_report_financial (
  financial_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  form_id bigint(20) NOT NULL COMMENT '关联的表单ID',
  financial_type varchar(20) NOT NULL COMMENT '类型（资产|负债|其它）',
  financial_item varchar(256) DEFAULT NULL COMMENT '科目名称',
  financial_name varchar(256) DEFAULT NULL COMMENT '科目明细/交易对手/对方单位',
  origial_amount bigint(20) DEFAULT 0 COMMENT '原始单据核实金额（元）',
  origial_count int(11) DEFAULT 0 COMMENT '原始单据核实份数（份）',
  origial_age varchar(64) DEFAULT NULL COMMENT '原始单据帐龄',
  bad_debt_amount bigint(20) DEFAULT 0 COMMENT '估计坏帐金额',
  remark varchar(256) DEFAULT NULL COMMENT '现场查看说明/分析',
  del_able varchar(16) DEFAULT NULL COMMENT '是否可删除(页面上)',
  sort_order int(11) DEFAULT 0,
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (financial_id),
  INDEX form_id_financial_type (form_id, financial_type)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '保后检查-企业资产/负债工作底稿';

--
-- Definition for table f_afterwards_check_report_income
--
DROP TABLE IF EXISTS f_afterwards_check_report_income;
CREATE TABLE f_afterwards_check_report_income (
  income_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) NOT NULL COMMENT '对应的表单ID',
  project_code varchar(50) DEFAULT NULL COMMENT '项目编号',
  income_name varchar(64) DEFAULT NULL COMMENT '收入项目名称',
  last_year int(11) DEFAULT 0 COMMENT '上一年',
  last_total_amount bigint(20) DEFAULT NULL COMMENT '(上一年)核实原始单据总金额（元）',
  last_total_day int(11) DEFAULT 0 COMMENT '(上一年)核实原始单据总天数',
  last_average_day bigint(20) DEFAULT NULL COMMENT '(上一年)（日）月均',
  last_accumulation bigint(20) DEFAULT NULL COMMENT '(上一年)全年',
  current_year int(11) DEFAULT 0 COMMENT '本年',
  current_total_amount bigint(20) DEFAULT NULL COMMENT '(本年)核实原始单据总金额（元）',
  current_total_day int(11) DEFAULT 0 COMMENT '(本年)核实原始单据总天数',
  current_average_day bigint(20) DEFAULT NULL COMMENT '(本年)（日）月均',
  current_accumulation bigint(20) DEFAULT NULL COMMENT '(本年)累计',
  sort_order int(11) DEFAULT 0,
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (income_id),
  INDEX f_afterwards_check_report_income_form_id_i (form_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '保后检查 - 反应收入项目';

--
-- Definition for table f_afterwards_check_report_item
--
DROP TABLE IF EXISTS f_afterwards_check_report_item;
CREATE TABLE f_afterwards_check_report_item (
  item_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  form_id bigint(20) NOT NULL COMMENT '关联的表单ID',
  item_type varchar(20) NOT NULL COMMENT '类型（企业收入/成本核实|反担保措施检查|其他重要事项核查|预警信号）',
  item_name varchar(256) DEFAULT NULL COMMENT '科目名称',
  item_value1 varchar(256) DEFAULT NULL COMMENT '科目数据1',
  item_value2 varchar(256) DEFAULT NULL COMMENT '科目数据2',
  item_value3 varchar(256) DEFAULT NULL COMMENT '科目数据3',
  item_desc varchar(1024) DEFAULT NULL COMMENT '科目说明',
  sort_order int(11) DEFAULT 0,
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (item_id),
  INDEX form_id_item_type (form_id, item_type)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '保后检查-核实内容-关注事项';

--
-- Definition for table f_afterwards_check_report_project
--
DROP TABLE IF EXISTS f_afterwards_check_report_project;
CREATE TABLE f_afterwards_check_report_project (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) NOT NULL COMMENT '对应的表单ID',
  project_name varchar(512) DEFAULT NULL COMMENT '项目名称',
  project_type varchar(32) DEFAULT NULL COMMENT '项目类型',
  opening_date date DEFAULT NULL COMMENT '开盘时间',
  closing_date date DEFAULT NULL COMMENT '预计交房时间',
  opening_area bigint(20) DEFAULT 0 COMMENT '开盘面积',
  received_amount bigint(20) DEFAULT 0 COMMENT '已收到的销售金额',
  receiving_amount bigint(20) DEFAULT 0 COMMENT '未收到的销售金额',
  saling_area bigint(20) DEFAULT 0 COMMENT '预计贷款期限内的销售面积',
  saling_amount bigint(20) DEFAULT 0 COMMENT '预计贷款期限内的销售金额',
  project_plan text DEFAULT NULL COMMENT '项目推进计划及时间节点安排',
  paid_land_amount bigint(20) DEFAULT 0 COMMENT '已支付土地款',
  paid_project_amount bigint(20) DEFAULT 0 COMMENT '已支付工程款',
  plan_area bigint(20) DEFAULT 0 COMMENT '预计贷款期限内完工的面积',
  plan_project_amount bigint(20) DEFAULT 0 COMMENT '贷款期限内预计将支付的工程款',
  plan_invest_amount bigint(20) DEFAULT 0 COMMENT '贷款期限内预计将支付投资款',
  project_desc text DEFAULT NULL COMMENT '已完工部分详述',
  sort_order int(11) DEFAULT 0,
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  INDEX f_afterwards_check_report_project_form_id_i (form_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '保后检查 - 开发项目完成情况检查';

--
-- Definition for table f_afterwards_check_report_projecting
--
DROP TABLE IF EXISTS f_afterwards_check_report_projecting;
CREATE TABLE f_afterwards_check_report_projecting (
  projecting_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) NOT NULL COMMENT '对应的表单ID',
  projecting_name varchar(128) DEFAULT NULL COMMENT '建设项目名称',
  contract_amount bigint(20) DEFAULT 0 COMMENT '合同金额（元）',
  projecting_progress varchar(128) DEFAULT NULL COMMENT '施工进度',
  projected_progress varchar(128) DEFAULT NULL COMMENT '累计完成工作量',
  paid_amount bigint(20) DEFAULT 0 COMMENT '已付工程款（元）',
  pay_type varchar(128) DEFAULT NULL COMMENT '结算方式',
  finish_date date DEFAULT NULL COMMENT '计划完工日期',
  sort_order int(11) DEFAULT 0,
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (projecting_id),
  INDEX f_afterwards_check_report_projecting_form_id_i (form_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '保后检查 - 在建项目进度及投入情况调查工作底稿';


--
-- Definition for table f_capital_appropriation_apply
--
DROP TABLE IF EXISTS f_capital_appropriation_apply;
CREATE TABLE f_capital_appropriation_apply (
  apply_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) DEFAULT NULL COMMENT '表单ID',
  project_code varchar(30) DEFAULT NULL COMMENT '项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '项目名称',
  project_type varchar(100) DEFAULT NULL COMMENT '项目类型：理财项目/非理财项目',
  attach text DEFAULT NULL COMMENT '附件',
  remark text DEFAULT NULL COMMENT '备注',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (apply_id),
  INDEX f_capital_appropriation_apply_form_id_i (form_id),
  INDEX f_capital_appropriation_apply_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '资金划付申请';

--
-- Definition for table f_capital_appropriation_apply_fee
--
DROP TABLE IF EXISTS f_capital_appropriation_apply_fee;
CREATE TABLE f_capital_appropriation_apply_fee (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  apply_id bigint(20) NOT NULL COMMENT '申请单ID',
  appropriate_reason varchar(50) DEFAULT NULL COMMENT '划付事由',
  appropriate_amount bigint(20) DEFAULT NULL COMMENT '划付金额',
  remark text DEFAULT NULL COMMENT '备注',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  INDEX f_capital_appropriation_apply_fee_apply_id_i (apply_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '资金划付申请-资金划付信息';

--
-- Definition for table f_charge_notification
--
DROP TABLE IF EXISTS f_charge_notification;
CREATE TABLE f_charge_notification (
  notification_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '通知ID',
  form_id bigint(20) NOT NULL COMMENT '对应表单ID',
  project_code varchar(50) DEFAULT NULL COMMENT '对应项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '对应项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '客户ID',
  customer_name varchar(50) DEFAULT NULL COMMENT '客户名称',
  contract_code varchar(255) DEFAULT NULL COMMENT '合同编号',
  is_agent_pay varchar(50) DEFAULT NULL COMMENT '是否代付',
  pay_amount bigint(20) DEFAULT NULL COMMENT '付款金额',
  pay_name varchar(50) DEFAULT NULL COMMENT '付款方户名',
  pay_account varchar(30) DEFAULT NULL COMMENT '付款账号',
  pay_bank varchar(100) DEFAULT NULL COMMENT '付款银行',
  pay_time datetime DEFAULT NULL COMMENT '付款时间',
  submit_man_id bigint(20) DEFAULT 0 COMMENT '提交人id',
  charge_no varchar(20) DEFAULT NULL COMMENT '收费单号',
  charge_time datetime DEFAULT NULL COMMENT '收费时间',
  submit_man_name varchar(50) DEFAULT NULL COMMENT '提交人名称',
  status varchar(20) DEFAULT NULL COMMENT '收费状态',
  remark varchar(2000) DEFAULT NULL COMMENT '备注',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (notification_id),
  INDEX f_charge_notification_form_id_i (form_id),
  INDEX f_charge_notification_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '收费通知';

--
-- Definition for table f_charge_notification_fee
--
DROP TABLE IF EXISTS f_charge_notification_fee;
CREATE TABLE f_charge_notification_fee (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  notification_id bigint(20) NOT NULL COMMENT '收费通知ID',
  fee_type varchar(50) DEFAULT NULL COMMENT '收费类型',
  fee_type_desc varchar(50) DEFAULT NULL COMMENT '收费类型描述',
  charge_base bigint(20) DEFAULT NULL COMMENT '收费基数',
  charge_amount bigint(20) DEFAULT NULL COMMENT '收取金额',
  charge_rate decimal(12, 4) DEFAULT NULL COMMENT '收取费率',
  start_time datetime DEFAULT NULL COMMENT '计费开始时间',
  end_time datetime DEFAULT NULL COMMENT '计费结束时间',
  remark varchar(128) DEFAULT NULL COMMENT '收费备注',
  sort_order int(11) DEFAULT NULL,
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '收费通知 - 收费明细';

--
-- Definition for table f_council_apply
--
DROP TABLE IF EXISTS f_council_apply;
CREATE TABLE f_council_apply (
  apply_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '申请ID',
  form_id bigint(20) DEFAULT NULL COMMENT '对应表单ID',
  project_code varchar(50) DEFAULT NULL COMMENT '对应项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '对应项目名称',
  amount bigint(20) DEFAULT NULL COMMENT '授信金额',
  customer_id bigint(20) DEFAULT NULL COMMENT '客户ID',
  customer_name varchar(50) DEFAULT NULL COMMENT '客户名称',
  apply_man_id bigint(20) DEFAULT NULL COMMENT '申请人',
  apply_man_name varchar(50) DEFAULT NULL COMMENT '申请人名称',
  apply_dept_id bigint(20) DEFAULT NULL COMMENT '申请部门',
  apply_dept_name varchar(50) DEFAULT NULL COMMENT '申请部门名称',
  council_type bigint(20) DEFAULT NULL COMMENT '会议类型ID',
  council_type_desc varchar(50) DEFAULT NULL COMMENT '会议类型描述',
  apply_time datetime DEFAULT NULL COMMENT '提交申请时间',
  status varchar(10) NOT NULL DEFAULT 'WAIT' COMMENT '状态',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (apply_id),
  INDEX f_council_apply_form_id_i (form_id),
  INDEX f_council_apply_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目上会申请（待上会项目列表）';

--
-- Definition for table f_council_apply_credit
--
DROP TABLE IF EXISTS f_council_apply_credit;
CREATE TABLE f_council_apply_credit (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  handle_id bigint(20) NOT NULL COMMENT 'f_council_apply_risk_handle.id',
  issue_date datetime DEFAULT NULL COMMENT '授信开始日期',
  expire_date datetime DEFAULT NULL COMMENT '授信到期日期',
  loan_amount bigint(20) NOT NULL COMMENT '应还款额',
  debit_interest bigint(20) NOT NULL COMMENT '欠息/费金额',
  sort_order int(11) NOT NULL DEFAULT 0,
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  INDEX f_council_apply_credit_handle_id_i (handle_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目上会申报 -- 授信业务基本情况';

--
-- Definition for table f_council_apply_new_product
--
DROP TABLE IF EXISTS f_council_apply_new_product;
CREATE TABLE f_council_apply_new_product (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  apply_id bigint(20) NOT NULL COMMENT '对应上会申请ID',
  product_name varchar(128) DEFAULT NULL COMMENT '新产品名称',
  busi_intro text DEFAULT NULL COMMENT '创新业务描述',
  status varchar(10) DEFAULT NULL COMMENT '状态',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '上会申请 - 新产品上会申请表';

--
-- Definition for table f_council_apply_risk_handle
--
DROP TABLE IF EXISTS f_council_apply_risk_handle;
CREATE TABLE f_council_apply_risk_handle (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  apply_id bigint(20) NOT NULL COMMENT '对应上会申请ID',
  form_id bigint(20) DEFAULT NULL COMMENT '对应表单ID',
  project_code varchar(50) DEFAULT NULL COMMENT '对应项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '对应项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '客户ID',
  customer_name varchar(50) DEFAULT NULL COMMENT '客户名称',
  is_repay varchar(10) DEFAULT NULL COMMENT '是否代偿:Y/N',
  company_name varchar(128) DEFAULT NULL COMMENT '企业名称',
  enterprise_type varchar(20) DEFAULT NULL COMMENT '企业性质',
  guarantee_amount bigint(20) DEFAULT NULL COMMENT '担保金额',
  guarantee_rate decimal(12, 4) DEFAULT NULL COMMENT '担保费率',
  loan_bank varchar(128) DEFAULT NULL COMMENT '放款银行',
  loan_time_limit varchar(128) DEFAULT NULL COMMENT '放款期限',
  busi_manager_name varchar(50) DEFAULT NULL COMMENT '客户经理名称',
  risk_manager_name varchar(50) DEFAULT NULL COMMENT '风险经理名称',
  repay_amount bigint(20) DEFAULT NULL COMMENT '代偿金额',
  repay_date datetime DEFAULT NULL COMMENT '代偿日期',
  loan_type varchar(50) DEFAULT NULL COMMENT '授信品种',
  credit_time_limit varchar(50) DEFAULT NULL COMMENT '授信期限',
  credit_amount bigint(20) DEFAULT NULL COMMENT '授信金额',
  basic_info text DEFAULT NULL COMMENT '基本情况',
  risk_info text DEFAULT NULL COMMENT '风险事项',
  last_council_decision text DEFAULT NULL COMMENT '前次风险处置会决议事项',
  last_council_check text DEFAULT NULL COMMENT '前次风险处置会决议落实情况',
  this_council_scheme text DEFAULT NULL COMMENT '本次上会提交的代偿方案及追偿方案/本次上会提交的处置方案',
  status varchar(10) DEFAULT NULL COMMENT '状态',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  INDEX f_council_apply_risk_handle_apply_id_i (apply_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目上会申请 - 风险处置会/代偿项目上会';

--
-- Definition for table f_council_summary
--
DROP TABLE IF EXISTS f_council_summary;
CREATE TABLE f_council_summary (
  summary_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '会议纪要ID',
  summary_code varchar(50) DEFAULT NULL COMMENT '会议纪要编号',
  form_id bigint(20) NOT NULL COMMENT '对应的表单ID',
  council_id bigint(20) DEFAULT NULL COMMENT '会议ID',
  council_code varchar(20) DEFAULT NULL COMMENT '会议编号',
  council_type varchar(20) DEFAULT NULL COMMENT '会议类型code',
  initiator_id bigint(20) DEFAULT NULL COMMENT '会议召开人ID',
  initiator_account varchar(20) DEFAULT NULL COMMENT '会议召开人账号',
  initiator_name varchar(50) DEFAULT NULL COMMENT '会议召开人名称',
  overview text DEFAULT NULL COMMENT '简述',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (summary_id),
  INDEX f_council_summary_form_id_i (form_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '会议纪要';

--
-- Definition for table f_council_summary_project
--
DROP TABLE IF EXISTS f_council_summary_project;
CREATE TABLE f_council_summary_project (
  sp_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  sp_code varchar(50) DEFAULT NULL COMMENT '项目批复编号',
  summary_id bigint(20) NOT NULL COMMENT '会议纪要ID',
  project_code varchar(50) DEFAULT NULL COMMENT '项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  customer_type varchar(20) DEFAULT NULL COMMENT '客户类型',
  busi_type varchar(10) DEFAULT NULL COMMENT '业务类型',
  busi_type_name varchar(50) DEFAULT NULL COMMENT '业务类型名称',
  loan_purpose varchar(512) DEFAULT NULL COMMENT '授信用途',
  amount bigint(20) DEFAULT NULL COMMENT '拟发行金额/保全金额/授信金额',
  is_maximum_amount varchar(10) DEFAULT 'NO' COMMENT '是否为最高额授信',
  time_limit int(11) DEFAULT NULL COMMENT '担保期限',
  time_unit varchar(10) DEFAULT NULL COMMENT '担保期限单位',
  repay_way varchar(20) DEFAULT NULL COMMENT '还款方式 多次/一次',
  is_repay_equal varchar(10) DEFAULT 'NO' COMMENT '是否连续年度等额偿还',
  charge_way varchar(20) DEFAULT NULL COMMENT '收费方式 多次/一次',
  charge_phase varchar(20) DEFAULT NULL COMMENT '先收/后扣',
  is_charge_every_beginning varchar(10) DEFAULT 'NO' COMMENT '首次收费外,是否以后为每年度期初',
  vote_result varchar(20) DEFAULT NULL COMMENT '投票结果',
  vote_result_desc varchar(512) DEFAULT NULL COMMENT '投票详情',
  one_vote_down varchar(10) DEFAULT 'NO' COMMENT '是否一票否决',
  one_vote_down_mark text DEFAULT NULL COMMENT '一票否决的原因',
  one_vote_down_time datetime DEFAULT NULL COMMENT '一票否决时间',
  is_del varchar(10) DEFAULT 'NO' COMMENT '是否已作废',
  remark text DEFAULT NULL COMMENT '备注',
  approval_time datetime DEFAULT NULL COMMENT '项目批复时间',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (sp_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '会议纪要 - 项目信息';

--
-- Definition for table f_council_summary_project_bond
--
DROP TABLE IF EXISTS f_council_summary_project_bond;
CREATE TABLE f_council_summary_project_bond (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  sp_id bigint(20) NOT NULL COMMENT '会议纪要项目ID',
  capital_channel_id bigint(20) DEFAULT NULL COMMENT '资金渠道ID',
  capital_channel_name varchar(128) DEFAULT NULL COMMENT '资金渠道名称',
  capital_sub_channel_id bigint(20) DEFAULT NULL COMMENT '二级资金渠道ID',
  capital_sub_channel_name varchar(128) DEFAULT NULL COMMENT '二级资金渠道名称',
  interest_rate decimal(12, 4) DEFAULT NULL COMMENT '利率',
  interest_rate_float varchar(20) DEFAULT NULL COMMENT '利率浮动(大于/小于等)',
  guarantee_fee decimal(12, 4) DEFAULT NULL COMMENT '担保费',
  guarantee_fee_type varchar(10) DEFAULT NULL COMMENT '担保费类型',
  total_cost decimal(12, 4) DEFAULT NULL COMMENT '总成本',
  total_cost_type varchar(10) DEFAULT NULL COMMENT '总成本类型 元/%',
  other_fee decimal(12, 4) DEFAULT NULL COMMENT '其他费用',
  other_fee_type varchar(10) DEFAULT NULL COMMENT '其他费用类型',
  process_flag varchar(32) DEFAULT NULL COMMENT '过程控制标识',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '会议纪要 - 发债项目详情';

--
-- Definition for table f_council_summary_project_charge_way
--
DROP TABLE IF EXISTS f_council_summary_project_charge_way;
CREATE TABLE f_council_summary_project_charge_way (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  sp_id bigint(20) NOT NULL COMMENT '对应项目批复ID',
  charge_order int(11) DEFAULT NULL COMMENT '收费顺序，1表示首次收费',
  phase varchar(30) DEFAULT NULL COMMENT '收费阶段',
  before_day int(11) DEFAULT NULL COMMENT 'phase 前 before_day 天内',
  amount decimal(12, 4) DEFAULT NULL COMMENT '收费',
  amount_type varchar(10) DEFAULT NULL COMMENT '元/%每年',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '会议纪要 - 项目评审会 - 收费方式';

--
-- Definition for table f_council_summary_project_entrusted
--
DROP TABLE IF EXISTS f_council_summary_project_entrusted;
CREATE TABLE f_council_summary_project_entrusted (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  sp_id bigint(20) NOT NULL COMMENT '会议纪要项目ID',
  capital_channel_id bigint(20) DEFAULT NULL COMMENT '资金渠道ID',
  capital_channel_name varchar(128) DEFAULT NULL COMMENT '资金渠道名称',
  capital_sub_channel_id bigint(20) DEFAULT NULL COMMENT '二级资金渠道',
  capital_sub_channel_name varchar(128) DEFAULT NULL COMMENT '二级资金渠道名称',
  interest_rate decimal(12, 4) DEFAULT NULL COMMENT '利率',
  other_fee decimal(12, 4) DEFAULT NULL COMMENT '其他费用',
  other_fee_type varchar(10) DEFAULT NULL COMMENT '其他费用类型',
  process_flag varchar(30) DEFAULT NULL COMMENT '过程控制标识',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '会议纪要 - 委贷项目详情';

--
-- Definition for table f_council_summary_project_guarantee
--
DROP TABLE IF EXISTS f_council_summary_project_guarantee;
CREATE TABLE f_council_summary_project_guarantee (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  sp_id bigint(20) NOT NULL COMMENT '会议纪要项目ID',
  capital_channel_id bigint(20) DEFAULT NULL COMMENT '资金渠道ID',
  capital_channel_name varchar(128) DEFAULT NULL COMMENT '资金渠道名称',
  capital_sub_channel_id bigint(20) DEFAULT NULL COMMENT '二级资金渠道',
  capital_sub_channel_name varchar(128) DEFAULT NULL COMMENT '二级资金渠道名称',
  interest_rate decimal(12, 4) DEFAULT NULL COMMENT '利率',
  interest_rate_float varchar(20) DEFAULT NULL COMMENT '利率浮动(大于/小于等)',
  guarantee_fee decimal(12, 4) DEFAULT NULL COMMENT '担保费',
  guarantee_fee_type varchar(10) DEFAULT NULL COMMENT '担保费类型',
  total_cost decimal(12, 4) DEFAULT NULL COMMENT '总成本',
  total_cost_type varchar(10) DEFAULT NULL COMMENT '总成本类型 元/%',
  other_fee decimal(12, 4) DEFAULT NULL COMMENT '其他费用',
  other_fee_type varchar(10) DEFAULT NULL COMMENT '其他费用类型',
  process_flag varchar(32) DEFAULT NULL COMMENT '过程控制标识',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '会议纪要 - 担保项目详情';

--
-- Definition for table f_council_summary_project_guarantor
--
DROP TABLE IF EXISTS f_council_summary_project_guarantor;
CREATE TABLE f_council_summary_project_guarantor (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  sp_id bigint(20) NOT NULL COMMENT '对应项目批复ID',
  guarantor varchar(50) DEFAULT NULL COMMENT '保证人',
  guarantee_amount bigint(20) DEFAULT NULL COMMENT '保证额度',
  guarantee_way varchar(50) DEFAULT NULL COMMENT '保证方式',
  sort_order int(11) DEFAULT NULL,
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '会议纪要 - 项目 -  保证人';

--
-- Definition for table f_council_summary_project_lg_litigation
--
DROP TABLE IF EXISTS f_council_summary_project_lg_litigation;
CREATE TABLE f_council_summary_project_lg_litigation (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  sp_id bigint(20) NOT NULL COMMENT '会议纪要项目ID',
  co_institution_id bigint(20) DEFAULT NULL COMMENT '合作机构ID',
  co_institution_name varchar(128) DEFAULT NULL COMMENT '合作机构名称',
  co_institution_charge decimal(12, 4) DEFAULT NULL COMMENT '合作机构服务费 元/%',
  co_institution_charge_type varchar(10) DEFAULT NULL COMMENT '合作机构服务费类型 元/%',
  guarantee_fee decimal(12, 4) DEFAULT NULL COMMENT '担保费',
  guarantee_fee_type varchar(10) DEFAULT NULL COMMENT '担保费类型',
  deposit decimal(12, 4) DEFAULT NULL COMMENT '保证金',
  deposit_type varchar(10) DEFAULT NULL COMMENT '保证金类型 元/%',
  other_fee decimal(12, 4) DEFAULT NULL COMMENT '其他费用',
  other_fee_type varchar(10) DEFAULT NULL COMMENT '其他费用类型 元/%',
  court varchar(128) DEFAULT NULL COMMENT '法院',
  assure_object text DEFAULT NULL COMMENT '本次申请保全标的',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '会议纪要 - 诉讼保函项目详情';

--
-- Definition for table f_council_summary_project_pledge
--
DROP TABLE IF EXISTS f_council_summary_project_pledge;
CREATE TABLE f_council_summary_project_pledge (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  sp_id bigint(20) NOT NULL COMMENT '对应项目批复ID',
  type varchar(10) DEFAULT NULL COMMENT '抵/质押',
  type_desc varchar(50) DEFAULT NULL COMMENT '抵押 / 质押',
  pledge_property varchar(50) DEFAULT NULL COMMENT '押品性质',
  pledge_property_desc varchar(50) DEFAULT NULL COMMENT '押品性质描述',
  pledge_type varchar(20) DEFAULT NULL COMMENT '押品类型',
  pledge_type_desc varchar(50) DEFAULT NULL COMMENT '押品类型描述',
  ownership varchar(50) DEFAULT NULL COMMENT '权利人',
  address varchar(256) DEFAULT NULL COMMENT '住所',
  warrant_no varchar(50) DEFAULT NULL COMMENT '权证号',
  num varchar(20) DEFAULT NULL COMMENT '数量',
  unit varchar(20) DEFAULT NULL COMMENT '单位',
  amount bigint(20) DEFAULT NULL COMMENT '评估价格',
  ratio decimal(12, 4) DEFAULT NULL COMMENT '抵押率',
  sort_order int(11) DEFAULT 0,
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '会议纪要 - 项目 - 授信条件（抵/质押物）';

--
-- Definition for table f_council_summary_project_process_control
--
DROP TABLE IF EXISTS f_council_summary_project_process_control;
CREATE TABLE f_council_summary_project_process_control (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  sp_id bigint(20) NOT NULL COMMENT '项目批复ID f_council_summary_project.sp_id',
  type varchar(20) DEFAULT NULL COMMENT '客户主体评价/资产负债率/其它',
  credit_level varchar(32) DEFAULT NULL COMMENT '主体评级',
  level_desc varchar(32) DEFAULT NULL COMMENT '评级描述',
  adjust_type varchar(32) DEFAULT NULL COMMENT '评级方式(每下调一级)',
  adjust_desc varchar(32) DEFAULT NULL COMMENT '评级方式描述',
  asset_liability_ratio varchar(32) DEFAULT NULL COMMENT '资产负责率',
  threshold_ratio varchar(32) DEFAULT NULL COMMENT '资产负责率警戒值',
  adjust_ratio varchar(32) DEFAULT NULL COMMENT '每超过(%)',
  up_down varchar(50) DEFAULT NULL COMMENT '上调/下调',
  up_down_rate decimal(12, 4) DEFAULT NULL COMMENT '上调/下调百分点',
  content varchar(128) DEFAULT NULL COMMENT '过程控制内容',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  INDEX sp_id_i (sp_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '会议纪要 - 项目批复 - 过程控制';

--
-- Definition for table f_council_summary_project_repay_way
--
DROP TABLE IF EXISTS f_council_summary_project_repay_way;
CREATE TABLE f_council_summary_project_repay_way (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  sp_id bigint(20) NOT NULL COMMENT '对应项目批复ID',
  phase varchar(30) DEFAULT NULL COMMENT '还款阶段',
  after_day int(11) DEFAULT NULL COMMENT '到达授信期限截止日后after_day内还款',
  after_year int(11) DEFAULT NULL COMMENT '自 phase 起 第after_year年',
  after_year_end int(11) DEFAULT NULL COMMENT '自 phase 起 第after_year年 到 第after_year_end年',
  month_period int(11) DEFAULT NULL COMMENT '每 month_period 月',
  repay_rate decimal(12, 4) DEFAULT NULL COMMENT '偿还率',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '会议纪要 - 项目评审会 -还款方式';

--
-- Definition for table f_council_summary_project_underwriting
--
DROP TABLE IF EXISTS f_council_summary_project_underwriting;
CREATE TABLE f_council_summary_project_underwriting (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  sp_id bigint(20) NOT NULL COMMENT '会议纪要项目ID',
  bourse_id bigint(20) DEFAULT NULL COMMENT '交易ID',
  bourse_name varchar(128) DEFAULT NULL COMMENT '交易所名称',
  release_rate decimal(12, 4) DEFAULT NULL COMMENT '发行利率',
  bourse_fee decimal(12, 4) DEFAULT NULL COMMENT '交易所费',
  bourse_fee_type varchar(10) DEFAULT NULL COMMENT '交易所费用类型',
  law_firm_fee decimal(12, 4) DEFAULT NULL COMMENT '律所费',
  law_firm_fee_type varchar(10) DEFAULT NULL COMMENT '律所费用类型',
  club_fee decimal(12, 4) DEFAULT NULL COMMENT '会所费',
  club_fee_type varchar(10) DEFAULT NULL COMMENT '会所费用类型',
  underwriting_fee decimal(12, 4) DEFAULT NULL COMMENT '承销费',
  underwriting_fee_type varchar(10) DEFAULT NULL COMMENT '承销费用类型',
  other_fee decimal(12, 4) DEFAULT NULL COMMENT '其他费用',
  other_fee_type varchar(10) DEFAULT NULL COMMENT '其他费用类型',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '会议纪要 - 承销项目详情';

--
-- Definition for table f_council_summary_risk_handle
--
DROP TABLE IF EXISTS f_council_summary_risk_handle;
CREATE TABLE f_council_summary_risk_handle (
  handle_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  summary_id bigint(20) NOT NULL COMMENT '会议纪要ID',
  project_code varchar(50) DEFAULT NULL COMMENT '项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  customer_type varchar(20) DEFAULT NULL COMMENT '客户类型',
  scheme varchar(20) DEFAULT NULL COMMENT '处置方案',
  principal_amount bigint(20) DEFAULT NULL COMMENT '代偿本金/展期本金',
  interest bigint(20) DEFAULT NULL COMMENT '代偿利息',
  interest_penalty bigint(20) DEFAULT NULL COMMENT '代偿罚息',
  time_limit int(11) DEFAULT NULL COMMENT '展期期限',
  time_unit varchar(10) DEFAULT NULL COMMENT '展期期限单位',
  end_time datetime DEFAULT NULL COMMENT '代偿截止时间',
  remark text DEFAULT NULL COMMENT '其他事项说明',
  sort_order int(11) DEFAULT 0 COMMENT '排序号',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (handle_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '会议纪要 - 风险处置会';

--
-- Definition for table f_counter_guarantee_release
--
DROP TABLE IF EXISTS f_counter_guarantee_release;
CREATE TABLE f_counter_guarantee_release (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) NOT NULL COMMENT '对应的表单ID',
  project_code varchar(50) DEFAULT NULL COMMENT '对应项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '对应项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  contract_number varchar(128) DEFAULT NULL COMMENT '合同编号',
  credit_amount bigint(20) DEFAULT NULL COMMENT '授信金额(已放款总额)',
  time_limit int(11) DEFAULT NULL COMMENT '授信期限',
  time_unit varchar(10) DEFAULT NULL COMMENT '期限单位',
  released_amount bigint(20) DEFAULT NULL COMMENT '已解保金额',
  releasing_amount bigint(20) DEFAULT NULL COMMENT '解保中金额',
  apply_amount bigint(20) DEFAULT NULL COMMENT '申请解保金额',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  INDEX f_counter_guarantee_release_form_id_i (form_id),
  INDEX f_counter_guarantee_release_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '解除（反）担保-解保申请';

--
-- Definition for table f_credit_condition_confirm
--
DROP TABLE IF EXISTS f_credit_condition_confirm;
CREATE TABLE f_credit_condition_confirm (
  confirm_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) NOT NULL COMMENT '对应表单ID',
  project_code varchar(50) DEFAULT NULL COMMENT '项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '项目名称',
  contract_no varchar(20) DEFAULT NULL COMMENT '合同编号',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  customer_type varchar(10) DEFAULT NULL COMMENT '客户类型',
  busi_type varchar(50) DEFAULT NULL COMMENT '业务类型',
  busi_type_name varchar(50) DEFAULT NULL COMMENT '业务类型名称',
  time_limit int(11) DEFAULT NULL COMMENT '期限',
  time_unit varchar(10) DEFAULT NULL COMMENT '期限单位',
  amount bigint(20) DEFAULT NULL COMMENT '授信金额',
  institution_id bigint(20) DEFAULT NULL COMMENT '融资机构ID',
  institution_name varchar(128) DEFAULT NULL COMMENT '融资机构名称',
  remark text DEFAULT NULL COMMENT '备注',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (confirm_id),
  INDEX f_credit_condition_confirm_form_id_i (form_id),
  INDEX f_credit_condition_confirm_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '授信条件落实情况';

--
-- Definition for table f_credit_condition_confirm_item
--
DROP TABLE IF EXISTS f_credit_condition_confirm_item;
CREATE TABLE f_credit_condition_confirm_item (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  confirm_id bigint(20) NOT NULL COMMENT '主表ID',
  project_code varchar(50) NOT NULL COMMENT '项目编号',
  item_id bigint(20) NOT NULL COMMENT 'f_council_summary_project_pledge.id,f_council_summary_project_guarantor.id',
  item_desc varchar(512) DEFAULT NULL COMMENT '授信条件文字描述（根据对应抵（质）押、保证生成）',
  type varchar(20) DEFAULT NULL COMMENT '类型：抵押、质押、保证',
  is_confirm varchar(10) DEFAULT NULL COMMENT '是否落实',
  confirm_man_id text DEFAULT NULL COMMENT '落实人ID',
  confirm_man_account varchar(50) DEFAULT NULL COMMENT '落实人账号',
  confirm_man_name varchar(50) DEFAULT NULL COMMENT '落实人名称',
  confirm_date datetime DEFAULT NULL COMMENT '落实日期',
  contract_no text DEFAULT NULL COMMENT '合同编号',
  contract_agreement_url text DEFAULT NULL COMMENT '合同/协议url',
  right_vouche text DEFAULT NULL COMMENT '权利凭证(有多个用,分开)',
  remark text DEFAULT NULL COMMENT '备注',
  status varchar(10) DEFAULT NULL COMMENT '状态',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  INDEX f_credit_condition_confirm_item_confirm_id_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目授信条件表单';

--
-- Definition for table f_credit_refrerence_apply
--
DROP TABLE IF EXISTS f_credit_refrerence_apply;
CREATE TABLE f_credit_refrerence_apply (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) NOT NULL COMMENT '对应表单ID',
  company_name varchar(128) DEFAULT NULL COMMENT '企业名称',
  project_code varchar(50) DEFAULT NULL COMMENT '对应项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '对应项目名称',
  address varchar(512) DEFAULT NULL COMMENT '地址',
  busi_license_no varchar(50) DEFAULT NULL COMMENT '营业执照号',
  legal_persion varchar(20) DEFAULT NULL COMMENT '法定代表人',
  established_time datetime DEFAULT NULL COMMENT '成立时间',
  register_capital bigint(20) DEFAULT NULL COMMENT '注册资本',
  busi_scope varchar(512) DEFAULT NULL COMMENT '经营范围',
  busi_license_url varchar(128) DEFAULT NULL COMMENT '营业执照附件',
  afmg_approval_url varchar(128) DEFAULT NULL COMMENT '保后管理征信查询审批表',
  auth_url varchar(128) DEFAULT NULL COMMENT '信查询授权书——企业/法人',
  legal_persion_cert_front varchar(128) DEFAULT NULL COMMENT '法定代表人身份证-正面',
  legal_persion_cert_back varchar(128) DEFAULT NULL COMMENT '法定代表人身份证-反面',
  loan_card_front varchar(128) DEFAULT NULL COMMENT '贷款卡-正面',
  loan_card_back varchar(128) DEFAULT NULL COMMENT '贷款卡-反面',
  apply_man_id bigint(20) DEFAULT NULL COMMENT '申请人ID',
  apply_man_name varchar(20) DEFAULT NULL COMMENT '申请人名称',
  status varchar(10) DEFAULT NULL COMMENT '状态',
  apply_status varchar(1) DEFAULT '1' COMMENT '申请状态',
  credit_report varchar(128) DEFAULT NULL COMMENT '征信报告',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  INDEX f_credit_refrerence_apply_form_id_i (form_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '征信查询申请表';

--
-- Definition for table f_file
--
DROP TABLE IF EXISTS f_file;
CREATE TABLE f_file (
  file_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '档案ID',
  form_id bigint(20) NOT NULL COMMENT '对应表单ID',
  file_code varchar(20) DEFAULT NULL COMMENT '档案编号',
  type varchar(100) DEFAULT NULL COMMENT '档案类型（授信业务-基础卷/授信后管理卷/权力凭证卷）',
  project_code varchar(50) DEFAULT NULL COMMENT '对应项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '对应项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  busi_manager_id bigint(20) DEFAULT NULL COMMENT '客户经理',
  busi_manager_name bigint(20) DEFAULT NULL COMMENT '客户经理名称',
  first_loan_time timestamp NULL DEFAULT NULL COMMENT '首次放款时间/债券募集完成时间',
  filing_time datetime DEFAULT NULL COMMENT '建档时间',
  hand_over_dept varchar(20) DEFAULT NULL COMMENT '档案移交部门',
  hand_over_man varchar(20) DEFAULT NULL COMMENT '档案移交人员',
  hand_over_time datetime DEFAULT NULL COMMENT '档案移交时间',
  principal_man varchar(20) DEFAULT NULL COMMENT '部门负责人',
  vice_manager varchar(20) DEFAULT NULL COMMENT '分管副总经理',
  receive_dept varchar(20) DEFAULT NULL COMMENT '接收部门',
  receive_man varchar(20) DEFAULT NULL COMMENT '档案接收人员',
  receive_time datetime DEFAULT NULL COMMENT '档案接收时间',
  status varchar(10) DEFAULT NULL,
  file_check_status varchar(10) DEFAULT NULL COMMENT '入库页面完整性标志',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (file_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '档案管理 --入库申请单';

--
-- Definition for table f_file_borrow
--
DROP TABLE IF EXISTS f_file_borrow;
CREATE TABLE f_file_borrow (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  file_id bigint(20) NOT NULL COMMENT '对应入库的档案ID',
  form_id bigint(20) NOT NULL COMMENT '表单ID',
  file_code varchar(20) DEFAULT NULL COMMENT '档案编号',
  project_code varchar(50) DEFAULT NULL COMMENT '对应项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '对应项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  apply_man_id bigint(20) DEFAULT NULL COMMENT '申请人id',
  apply_man varchar(20) DEFAULT NULL COMMENT '申请人',
  apply_time datetime DEFAULT NULL COMMENT '申请时间',
  apply_dept varchar(20) DEFAULT NULL COMMENT '申请部门',
  expect_return_time datetime DEFAULT NULL COMMENT '预计时间',
  borrow_reason varchar(512) DEFAULT NULL COMMENT '借阅原因',
  borrow_detail_id varchar(255) DEFAULT NULL COMMENT '借阅文档id',
  borrow_detail text DEFAULT NULL COMMENT '借阅明细',
  hand_over_man_id bigint(20) DEFAULT NULL COMMENT '移交人id',
  hand_over_man varchar(20) DEFAULT NULL COMMENT '档案移交人员',
  hand_over_time datetime DEFAULT NULL COMMENT '档案移交时间',
  receive_dept varchar(20) DEFAULT NULL COMMENT '接收部门',
  receive_man_id bigint(20) DEFAULT NULL COMMENT '接收人id',
  receive_man varchar(20) DEFAULT NULL COMMENT '档案接收人员',
  hand_over_dept varchar(20) DEFAULT NULL COMMENT '档案移交部门',
  receive_time datetime DEFAULT NULL COMMENT '档案接收时间',
  is_return varchar(10) DEFAULT NULL COMMENT '是否已归还',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  INDEX f_file_output_form_id_i (form_id),
  INDEX f_file_output_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '档案清单 - 借阅申请单';

--
-- Definition for table f_file_input
--
DROP TABLE IF EXISTS f_file_input;
CREATE TABLE f_file_input (
  input_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  file_id bigint(20) NOT NULL COMMENT '对应入库的档案ID',
  file_code varchar(20) DEFAULT NULL COMMENT '档案编号',
  form_id bigint(20) NOT NULL COMMENT '表单ID',
  project_code varchar(50) DEFAULT NULL COMMENT '对应项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '对应项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  type varchar(20) DEFAULT NULL COMMENT '档案类型（授信业务-基础卷/授信后管理卷/权力凭证卷）',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  first_loan_time timestamp NULL DEFAULT NULL COMMENT '首次放款时间/债券募集完成时间',
  filing_time datetime DEFAULT NULL COMMENT '建档时间',
  hand_over_dept varchar(20) DEFAULT NULL COMMENT '档案移交部门',
  hand_over_man varchar(20) DEFAULT NULL COMMENT '档案移交人员',
  hand_over_time datetime DEFAULT NULL COMMENT '档案移交时间',
  principal_man varchar(20) DEFAULT NULL COMMENT '部门负责人',
  vice_manager varchar(20) DEFAULT NULL COMMENT '分管副总经理',
  receive_dept varchar(20) DEFAULT NULL COMMENT '接收部门',
  receive_man varchar(20) DEFAULT NULL COMMENT '档案接收人员',
  receive_time datetime DEFAULT NULL COMMENT '档案接收时间',
  status varchar(10) DEFAULT NULL,
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (input_id),
  INDEX f_file_input_form_id_i (form_id),
  INDEX f_file_input_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '档案清单 - 入库申请单(暂时没用到这张表，现在用的f_file这张表)';

--
-- Definition for table f_file_input_list
--
DROP TABLE IF EXISTS f_file_input_list;
CREATE TABLE f_file_input_list (
  input_list_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  file_id bigint(20) DEFAULT 0 COMMENT '入库单ID',
  id bigint(20) DEFAULT 0 COMMENT '档案明细ID',
  file_type varchar(128) DEFAULT NULL COMMENT '档案类别',
  file_name varchar(128) DEFAULT NULL COMMENT '档案名称',
  archive_file_name varchar(128) DEFAULT NULL COMMENT '归档文件名称',
  file_page int(11) DEFAULT NULL COMMENT '档案页数',
  file_path text DEFAULT NULL COMMENT '档案地址（附件）',
  input_remark varchar(128) DEFAULT NULL COMMENT '备注',
  sort_order int(11) DEFAULT 0 COMMENT '排序',
  status varchar(255) DEFAULT NULL COMMENT '文件状态',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (input_list_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '入库申请单-明细列表';

--
-- Definition for table f_file_list
--
DROP TABLE IF EXISTS f_file_list;
CREATE TABLE f_file_list (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  file_id bigint(20) NOT NULL COMMENT '档案ID',
  file_type varchar(128) DEFAULT NULL COMMENT '档案类别',
  file_name varchar(128) DEFAULT NULL COMMENT '档案名称',
  archive_file_name varchar(128) DEFAULT NULL COMMENT '归档文件名称',
  file_page int(11) DEFAULT NULL COMMENT '档案页数',
  file_path varchar(128) DEFAULT NULL COMMENT '档案地址（附件）',
  remark varchar(128) DEFAULT NULL COMMENT '备注',
  input_remark varchar(128) DEFAULT NULL COMMENT '入库备注',
  receive_time datetime DEFAULT NULL COMMENT '档案接收时间',
  type varchar(30) DEFAULT NULL COMMENT '档案类型（授信业务-基础卷/授信后管理卷/权力凭证卷）',
  is_archive varchar(20) DEFAULT NULL COMMENT '是否已归档',
  parent_id bigint(20) DEFAULT 0 COMMENT '父文档id,对应本表id字段',
  sort_order int(11) DEFAULT 0 COMMENT '排序',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '档案目录';

--
-- Definition for table f_file_list_status
--
DROP TABLE IF EXISTS f_file_list_status;
CREATE TABLE f_file_list_status (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  type varchar(20) DEFAULT NULL COMMENT '类型',
  check_status varchar(10) DEFAULT NULL COMMENT '页面完整性标志',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  UNIQUE INDEX UK_f_file_list_status_type (type)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '保存归档文件目录页面的完整性';

--
-- Definition for table f_file_output
--
DROP TABLE IF EXISTS f_file_output;
CREATE TABLE f_file_output (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  file_id bigint(20) NOT NULL COMMENT '对应入库的档案ID',
  form_id bigint(20) NOT NULL COMMENT '表单ID',
  file_code varchar(20) DEFAULT NULL COMMENT '档案编号',
  project_code varchar(50) DEFAULT NULL COMMENT '对应项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '对应项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  apply_man_id bigint(20) DEFAULT NULL COMMENT '申请人id',
  apply_man varchar(20) DEFAULT NULL COMMENT '申请人',
  apply_time datetime DEFAULT NULL COMMENT '申请时间',
  apply_dept varchar(20) DEFAULT NULL COMMENT '申请部门',
  output_reason varchar(512) DEFAULT NULL COMMENT '出库原因',
  output_detail_ids varchar(255) DEFAULT NULL COMMENT '出库文件的id集',
  output_detail text DEFAULT NULL COMMENT '出库明细',
  hand_over_man_id bigint(20) DEFAULT NULL COMMENT '移交人id',
  hand_over_man varchar(20) DEFAULT NULL COMMENT '档案移交人员',
  hand_over_time datetime DEFAULT NULL COMMENT '档案移交时间',
  receive_dept varchar(20) DEFAULT NULL COMMENT '接收部门',
  receive_man_id bigint(20) DEFAULT NULL COMMENT '接收人id',
  receive_man varchar(20) DEFAULT NULL COMMENT '档案接收人员',
  hand_over_dept varchar(20) DEFAULT NULL COMMENT '档案移交部门',
  receive_time datetime DEFAULT NULL COMMENT '档案接收时间',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  INDEX f_file_output_form_id_i (form_id),
  INDEX f_file_output_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '档案清单 - 出库申请单';

--
-- Definition for table f_financial_kpi
--
DROP TABLE IF EXISTS f_financial_kpi;
CREATE TABLE f_financial_kpi (
  kpi_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) NOT NULL COMMENT '对应的表单ID',
  parent_id bigint(20) DEFAULT 0 COMMENT '父指标ID',
  kpi_type varchar(50) DEFAULT NULL COMMENT '指标分类（财务数据/偿债能力/运营能力/盈利能力/现金流）',
  kpi_code varchar(128) DEFAULT NULL COMMENT '指标标识',
  kpi_name varchar(128) DEFAULT NULL COMMENT '指标名',
  kpi_value1 varchar(64) DEFAULT NULL COMMENT '指标值1',
  kpi_value2 varchar(64) DEFAULT NULL COMMENT '指标值2',
  kpi_value3 varchar(64) DEFAULT NULL COMMENT '指标值3',
  kpi_value4 varchar(64) DEFAULT NULL COMMENT '指标值4',
  kpi_value5 varchar(64) DEFAULT NULL COMMENT '指标值5',
  kpi_value6 varchar(64) DEFAULT NULL COMMENT '指标值6',
  kpi_value7 varchar(64) DEFAULT NULL COMMENT '指标值7',
  remark varchar(1024) DEFAULT NULL COMMENT '说明',
  sort_order int(11) DEFAULT 0,
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (kpi_id),
  INDEX f_financial_kpi_form_id_type (form_id, kpi_type),
  INDEX f_financial_kpi_parent_id (parent_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '财务报表指标表';

--
-- Definition for table f_fund_pay
--
DROP TABLE IF EXISTS f_fund_pay;
CREATE TABLE f_fund_pay (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) NOT NULL COMMENT '表单ID',
  project_code varchar(50) DEFAULT NULL COMMENT '项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  customer_type varchar(10) DEFAULT NULL COMMENT '客户类型',
  busi_type varchar(50) DEFAULT NULL COMMENT '业务类型',
  busi_type_name varchar(50) DEFAULT NULL COMMENT '业务类型名称',
  contract_no varchar(50) DEFAULT NULL COMMENT '合同编号',
  amount bigint(20) DEFAULT NULL COMMENT '担保金额',
  institution_id bigint(20) DEFAULT NULL COMMENT '融资机构ID',
  institution_name varchar(128) DEFAULT NULL COMMENT '融资机构名称',
  start_time datetime DEFAULT NULL COMMENT '担保期间',
  end_time datetime DEFAULT NULL COMMENT '担保期间',
  pay_amount bigint(20) DEFAULT NULL COMMENT '本次划付金额',
  pay_reason varchar(128) DEFAULT NULL COMMENT '本次申请资金划付事由',
  remark text DEFAULT NULL COMMENT '备注',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  INDEX project_peoject_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '资金划付';

--
-- Definition for table f_investigation
--
DROP TABLE IF EXISTS f_investigation;
CREATE TABLE f_investigation (
  investigate_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '项目调查',
  form_id bigint(20) NOT NULL COMMENT '对应的表单ID',
  review varchar(16) DEFAULT 'NO' COMMENT '复议标识：YES',
  project_code varchar(50) DEFAULT NULL COMMENT '对应项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '对应项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  busi_type varchar(10) DEFAULT NULL COMMENT '业务类型',
  busi_type_name varchar(50) DEFAULT NULL COMMENT '业务类型名称',
  amount bigint(20) DEFAULT NULL COMMENT '授信金额',
  declares varchar(32) DEFAULT NULL COMMENT '声明不存在以下情形',
  investigate_place varchar(128) DEFAULT NULL COMMENT '调查地点',
  investigate_date datetime DEFAULT NULL COMMENT '调查日期',
  investigate_persion varchar(128) DEFAULT NULL COMMENT '调查人员',
  investigate_persion_id varchar(128) DEFAULT NULL COMMENT '调查人员ID(多个以逗号分隔)',
  reception_persion varchar(512) DEFAULT NULL COMMENT '客户接待的人员（职务）',
  reception_duty varchar(512) DEFAULT NULL COMMENT '客户接待的人员职务',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (investigate_id),
  INDEX f_investigation_form_id_i (form_id),
  INDEX f_investigation_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目调查 - 授信调查';

--
-- Definition for table f_investigation_credit_scheme
--
DROP TABLE IF EXISTS f_investigation_credit_scheme;
CREATE TABLE f_investigation_credit_scheme (
  scheme_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) NOT NULL COMMENT '对应表单ID',
  project_code varchar(50) DEFAULT NULL COMMENT '对应项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '对应项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  industry_code varchar(50) DEFAULT NULL COMMENT '行业编码',
  industry_name varchar(128) DEFAULT NULL COMMENT '行业名称',
  credit_amount bigint(20) DEFAULT NULL COMMENT '授信金额',
  time_limit int(11) DEFAULT NULL COMMENT '授信期限',
  time_unit varchar(10) DEFAULT NULL COMMENT '期限单位',
  busi_type varchar(50) DEFAULT NULL COMMENT '业务品种',
  project_channel_id bigint(20) DEFAULT NULL COMMENT '项目渠道ID',
  project_channel_name varchar(128) DEFAULT NULL COMMENT '项目渠道名称',
  project_sub_channel_id bigint(20) DEFAULT NULL COMMENT '二级项目渠道',
  project_sub_channel_name varchar(128) DEFAULT NULL COMMENT '二级项目渠道名称',
  capital_channel_id bigint(20) DEFAULT NULL COMMENT '资金渠道ID',
  capital_channel_name varchar(128) DEFAULT NULL COMMENT '资金渠道名称',
  capital_sub_channel_id bigint(20) DEFAULT NULL COMMENT '二级资金渠道',
  capital_sub_channel_name varchar(128) DEFAULT NULL COMMENT '二级资金渠道名称',
  loan_purpose varchar(128) DEFAULT NULL COMMENT '用途',
  repay_way varchar(50) DEFAULT NULL COMMENT '还款方式',
  charge_phase varchar(10) DEFAULT NULL COMMENT '先收/后扣',
  charge_way varchar(10) DEFAULT NULL COMMENT '一次性/分次',
  charge_rate decimal(12, 4) DEFAULT NULL COMMENT '费率',
  statement text DEFAULT NULL COMMENT '需重点说明的授信事项',
  process_flag varchar(32) DEFAULT NULL COMMENT '过程标志',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (scheme_id),
  UNIQUE INDEX f_investigation_credit_scheme_form_id_u (form_id),
  INDEX f_investigation_credit_scheme_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目调查 - 授信方案 ';

--
-- Definition for table f_investigation_credit_scheme_guarantor
--
DROP TABLE IF EXISTS f_investigation_credit_scheme_guarantor;
CREATE TABLE f_investigation_credit_scheme_guarantor (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  scheme_id bigint(20) NOT NULL COMMENT '授信方案ID f_investigation_credit_scheme.scheme_id',
  guarantor varchar(50) DEFAULT NULL COMMENT '保证人',
  guarantee_amount bigint(20) DEFAULT NULL COMMENT '保证额度',
  guarantee_way varchar(50) DEFAULT NULL COMMENT '保证方式',
  sort_order int(11) DEFAULT NULL,
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '授信方案 -（反）担保措施 -  保证人';

--
-- Definition for table f_investigation_credit_scheme_pledge
--
DROP TABLE IF EXISTS f_investigation_credit_scheme_pledge;
CREATE TABLE f_investigation_credit_scheme_pledge (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  scheme_id bigint(20) NOT NULL COMMENT '授信方案ID f_investigation_credit_scheme.scheme_id',
  type varchar(10) DEFAULT NULL COMMENT '抵/质押',
  type_desc varchar(50) DEFAULT NULL COMMENT '抵押 / 质押',
  pledge_type varchar(20) DEFAULT NULL COMMENT '押品类型',
  pledge_type_desc varchar(50) DEFAULT NULL COMMENT '押品类型描述',
  pledge_property varchar(50) DEFAULT NULL COMMENT '押品性质',
  pledge_property_desc varchar(50) DEFAULT NULL COMMENT '押品性质描述',
  ownership varchar(50) DEFAULT NULL COMMENT '权利人',
  address varchar(256) DEFAULT NULL COMMENT '住所',
  warrant_no varchar(50) DEFAULT NULL COMMENT '权证号',
  num varchar(20) DEFAULT NULL COMMENT '数量',
  unit varchar(20) DEFAULT NULL COMMENT '单位',
  amount bigint(20) DEFAULT NULL COMMENT '评估价格',
  ratio decimal(12, 4) DEFAULT NULL COMMENT '抵押率',
  sort_order int(11) DEFAULT NULL,
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '授信方案 -（反）担保措施 -  抵（质）押';

--
-- Definition for table f_investigation_credit_scheme_process_control
--
DROP TABLE IF EXISTS f_investigation_credit_scheme_process_control;
CREATE TABLE f_investigation_credit_scheme_process_control (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  scheme_id bigint(20) NOT NULL COMMENT '授信方案ID f_investigation_credit_scheme.scheme_id',
  type varchar(20) DEFAULT NULL COMMENT '客户主体评价/资产负债率/其它',
  credit_level varchar(32) DEFAULT NULL COMMENT '主体评级',
  level_desc varchar(32) DEFAULT NULL COMMENT '评级描述',
  adjust_type varchar(32) DEFAULT NULL COMMENT '评级方式(每下调一级)',
  adjust_desc varchar(32) DEFAULT NULL COMMENT '评级方式描述',
  asset_liability_ratio varchar(32) DEFAULT NULL COMMENT '资产负责率',
  threshold_ratio varchar(32) DEFAULT NULL COMMENT '资产负责率警戒值',
  adjust_ratio varchar(32) DEFAULT NULL COMMENT '每超过(%)',
  up_down varchar(50) DEFAULT NULL COMMENT '上调/下调',
  up_down_rate decimal(12, 4) DEFAULT NULL COMMENT '上调/下调百分点',
  content varchar(512) DEFAULT NULL COMMENT '过程控制内容',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  INDEX scheme_id_i (scheme_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目调查 - 授信方案 - 过程控制(客户主体评级/资产负债率/其他)';

--
-- Definition for table f_investigation_cs_rationality_review
--
DROP TABLE IF EXISTS f_investigation_cs_rationality_review;
CREATE TABLE f_investigation_cs_rationality_review (
  csr_review_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '授信方案主要事项合理性评价ID',
  form_id bigint(20) NOT NULL COMMENT '对应表单ID',
  project_code varchar(50) DEFAULT NULL COMMENT '对应项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '对应项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  amount_limit_review text DEFAULT NULL COMMENT '本次授信额度合理性评价',
  time_limit_review text DEFAULT NULL COMMENT '本次授信期限合理性评价',
  loan_purpose_review text DEFAULT NULL COMMENT '授信用途合理性评价',
  repay_source_review text DEFAULT NULL COMMENT '第一还款来源分析',
  guarantor_review text DEFAULT NULL COMMENT '保证人合法性评价（此处不含担保公司评价）',
  guarantee_company_info text DEFAULT NULL COMMENT '担保公司基本情况',
  guarantee_company_ability text DEFAULT NULL COMMENT '担保公司担保能力总体评价（担保公司股东背景及股份结构、履保情况、合作情况等）',
  counter_guarantee_info text DEFAULT NULL COMMENT '客户提供反担保情况（担保方式、资产种类、数量及价值情况）',
  guarantor_info text DEFAULT NULL COMMENT '担保人基本情况及合法性评价',
  pledge_value text DEFAULT NULL COMMENT '担保物基本情况及评估价值评价',
  review_summary text DEFAULT NULL COMMENT '评估机构名称、评估时间、评估方法、初评价值评价',
  other_repay_source text DEFAULT NULL COMMENT '其它还款来源',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (csr_review_id),
  INDEX f_investigation_cs_rationality_review_form_id_i (form_id),
  INDEX f_investigation_cs_rationality_review_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目调查 - 授信方案主要事项合理性评价';

--
-- Definition for table f_investigation_cs_rationality_review_financial_kpi
--
DROP TABLE IF EXISTS f_investigation_cs_rationality_review_financial_kpi;
CREATE TABLE f_investigation_cs_rationality_review_financial_kpi (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  csr_review_id bigint(20) NOT NULL COMMENT '授信方案主要事项合理性评价ID',
  kpi_code varchar(20) DEFAULT NULL COMMENT '指标标识',
  kpi_name varchar(50) DEFAULT NULL COMMENT '指标名',
  kpi_value varchar(20) DEFAULT NULL COMMENT '指标值（存金额或者百分比）',
  kpi_unit varchar(20) DEFAULT NULL COMMENT '指标单位',
  term_type varchar(20) DEFAULT NULL COMMENT '分类（账期）',
  remark varchar(128) DEFAULT NULL COMMENT '说明',
  sort_order int(11) DEFAULT 0,
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目调查 - 授信方案主要事项合理性评价 - 保证人主要财务指标';

--
-- Definition for table f_investigation_cs_rationality_review_guarantor_ability
--
DROP TABLE IF EXISTS f_investigation_cs_rationality_review_guarantor_ability;
CREATE TABLE f_investigation_cs_rationality_review_guarantor_ability (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  csr_review_id bigint(20) NOT NULL COMMENT '授信方案主要事项合理性评价ID',
  guarantor varchar(20) DEFAULT NULL COMMENT '保证人姓名',
  ability_level varchar(20) DEFAULT NULL COMMENT '担保能力评级',
  total_capital bigint(20) DEFAULT NULL COMMENT '上年净资产总额',
  intangible_assets bigint(20) DEFAULT NULL COMMENT '除土地使用权以外的无形资产',
  contingent_liability bigint(20) DEFAULT NULL COMMENT '或有负债',
  guarantee_amount bigint(20) DEFAULT NULL COMMENT '对外可提供担保额度',
  sort_order int(11) DEFAULT 0,
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目调查 - 授信方案主要事项合理性评价 - 保证人保证能力总体评价';

--
-- Definition for table f_investigation_declare
--
DROP TABLE IF EXISTS f_investigation_declare;
CREATE TABLE f_investigation_declare (
  declare_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  form_id bigint(20) NOT NULL COMMENT '对应的表单ID',
  project_code varchar(50) DEFAULT NULL COMMENT '对应项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '对应项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  investigate_place varchar(128) DEFAULT NULL COMMENT '调查地点',
  investigate_date datetime DEFAULT NULL COMMENT '调查日期',
  investigate_persion varchar(128) DEFAULT NULL COMMENT '调查人员',
  reception_persion varchar(512) DEFAULT NULL COMMENT '客户接待的人员（职务）',
  reception_duty varchar(512) DEFAULT NULL COMMENT '客户接待的人员职务',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (declare_id),
  INDEX f_investigation_declare_form_id_i (form_id),
  INDEX f_investigation_declare_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '尽职调查报告 - 担保/委贷 - 申明';

--
-- Definition for table f_investigation_financial_review
--
DROP TABLE IF EXISTS f_investigation_financial_review;
CREATE TABLE f_investigation_financial_review (
  f_review_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '财务评价ID',
  form_id bigint(20) NOT NULL COMMENT '对应表单ID',
  project_code varchar(50) DEFAULT NULL COMMENT '对应项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '对应项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  import_excel varchar(16) DEFAULT 'NO' COMMENT '导入过数据:YES/NO',
  is_audited varchar(10) DEFAULT NULL COMMENT '是否审计',
  audit_unit varchar(50) DEFAULT NULL COMMENT '审计单位',
  audit_suggest varchar(50) DEFAULT NULL COMMENT '审计意见类型',
  audit_suggest_explain text DEFAULT NULL COMMENT '审计意见解释与说明',
  debt_paying_ability text DEFAULT NULL COMMENT '偿债能力解释与说明',
  operating_ability text DEFAULT NULL COMMENT '运营能力分析解释与说明',
  profit_ability text DEFAULT NULL COMMENT '盈利能力分析解释与说明',
  cash_flow_explain text DEFAULT NULL COMMENT '现金流分析解释与说明',
  asset_quality_review text DEFAULT NULL COMMENT '资产质量总体评价',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (f_review_id),
  INDEX f_investigation_financial_review_form_id_i (form_id),
  INDEX f_investigation_financial_review_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目调查 - 客户财务评价';

--
-- Definition for table f_investigation_financial_review_kpi
--
DROP TABLE IF EXISTS f_investigation_financial_review_kpi;
CREATE TABLE f_investigation_financial_review_kpi (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  f_review_id bigint(20) NOT NULL COMMENT '财务评价ID',
  kpi_code varchar(128) DEFAULT NULL COMMENT '指标标识',
  kpi_type varchar(50) DEFAULT NULL COMMENT '指标分类（财务数据/偿债能力/运营能力/盈利能力/现金流）',
  kpi_name varchar(50) DEFAULT NULL COMMENT '指标名',
  kpi_level int(11) DEFAULT 1 COMMENT '指标层级',
  kpi_value varchar(64) DEFAULT NULL COMMENT '指标值',
  kpi_unit varchar(20) DEFAULT NULL COMMENT '指标单位',
  kpi_ratio decimal(12, 4) DEFAULT NULL COMMENT '占比(指标解释)',
  parent_id bigint(20) DEFAULT 0 COMMENT '父指标ID',
  term_type varchar(20) DEFAULT NULL COMMENT '分类（账期）',
  remark varchar(1024) DEFAULT NULL COMMENT '说明',
  sort_order int(11) DEFAULT 0,
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目调查 - 客户财务评价 - 财务报表指标表';

--
-- Definition for table f_investigation_investigate_persion
--
DROP TABLE IF EXISTS f_investigation_investigate_persion;
CREATE TABLE f_investigation_investigate_persion (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  investigate_id varchar(20) DEFAULT NULL COMMENT '对应调查ID',
  investigate_persion_id bigint(20) DEFAULT NULL COMMENT '现场调查人ID',
  investigate_persion_account varchar(20) DEFAULT NULL COMMENT '调查人账号',
  investigate_persion_name varchar(50) DEFAULT NULL COMMENT '现场调查人名称',
  sort_order int(11) DEFAULT 0,
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目调查报告 - 现场调查人员';

--
-- Definition for table f_investigation_litigation
--
DROP TABLE IF EXISTS f_investigation_litigation;
CREATE TABLE f_investigation_litigation (
  litigation_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  form_id bigint(20) NOT NULL COMMENT '对应的表单ID',
  project_code varchar(50) DEFAULT NULL COMMENT '对应项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '对应项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '申请人ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '申请人名称',
  guarantee_rate decimal(12, 4) DEFAULT NULL COMMENT '担保费率',
  guarantee_amount bigint(20) DEFAULT NULL COMMENT '担保费用',
  co_institution_id bigint(20) DEFAULT NULL COMMENT '合作机构ID',
  co_institution_name varchar(128) DEFAULT NULL COMMENT '合作机构名称',
  information_fee decimal(12, 4) DEFAULT NULL COMMENT '法律咨询费率',
  court varchar(128) DEFAULT NULL COMMENT '法院',
  deposit decimal(12, 4) DEFAULT NULL COMMENT '保证金 元/%',
  deposit_type varchar(10) DEFAULT NULL COMMENT '保证金类型 元/%',
  contact_person varchar(128) DEFAULT NULL COMMENT '项目联系人',
  contact_no varchar(128) DEFAULT NULL COMMENT '联系电话',
  investigate_persion varchar(128) DEFAULT NULL COMMENT '调查人员',
  accept_date datetime DEFAULT NULL COMMENT '受理时间',
  case_introduce text DEFAULT NULL COMMENT '案情介绍',
  content text DEFAULT NULL COMMENT '拟保全标的或内容',
  audit_opinion text DEFAULT NULL COMMENT '风险审查意见',
  synthesize_opinion text DEFAULT NULL COMMENT '项目综合意见',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (litigation_id),
  INDEX f_investigation_form_id_i (form_id),
  INDEX f_investigation_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目调查 - 诉讼担保项目调查报告';

--
-- Definition for table f_investigation_mability_review
--
DROP TABLE IF EXISTS f_investigation_mability_review;
CREATE TABLE f_investigation_mability_review (
  ma_review_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) NOT NULL COMMENT '对应表单ID',
  project_code varchar(50) DEFAULT NULL COMMENT '对应项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '对应项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  leader_review text DEFAULT NULL COMMENT '领导人整体评价（包括主要领导人简历、管理层的稳定性）',
  staff_review text DEFAULT NULL COMMENT '员工基本情况及整体素质评价',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (ma_review_id),
  INDEX f_investigation_mability_review_form_id_i (form_id),
  INDEX f_investigation_mability_review_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目调查 - 客户管理能力评价';

--
-- Definition for table f_investigation_mability_review_leading_team
--
DROP TABLE IF EXISTS f_investigation_mability_review_leading_team;
CREATE TABLE f_investigation_mability_review_leading_team (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  ma_review_id bigint(20) NOT NULL COMMENT '对应管理能力评价ID',
  owner varchar(128) DEFAULT NULL COMMENT '所属',
  name varchar(50) DEFAULT NULL COMMENT '姓名',
  sex varchar(10) DEFAULT NULL COMMENT '性别',
  age int(11) DEFAULT 0 COMMENT '年龄',
  degree varchar(10) DEFAULT NULL COMMENT '学历',
  title varchar(20) DEFAULT NULL COMMENT '职务',
  resume varchar(512) DEFAULT NULL COMMENT '履历',
  sort_order int(11) DEFAULT 0,
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目调查 - 客户管理能力评价 - 客户主要高管人员表
';

--
-- Definition for table f_investigation_mainly_review
--
DROP TABLE IF EXISTS f_investigation_mainly_review;
CREATE TABLE f_investigation_mainly_review (
  m_review_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '评价ID',
  owner varchar(128) DEFAULT NULL COMMENT '所属',
  form_id bigint(20) NOT NULL COMMENT '对应表单ID',
  project_code varchar(50) DEFAULT NULL COMMENT '对应项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '对应项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  established_time datetime DEFAULT NULL COMMENT '成立时间',
  operating_term varchar(20) DEFAULT NULL COMMENT '经营期限',
  legal_persion varchar(20) DEFAULT NULL COMMENT '法定代表人',
  org_code varchar(50) DEFAULT NULL COMMENT '组织机构代码证',
  living_address varchar(128) DEFAULT NULL COMMENT '住址',
  actual_control_person varchar(20) DEFAULT NULL COMMENT '实际控制人',
  enterprise_type varchar(20) DEFAULT NULL COMMENT '企业类型',
  work_address varchar(128) DEFAULT NULL COMMENT '办公地址',
  is_one_cert varchar(10) DEFAULT 'NO' COMMENT '是否三证合一',
  busi_license_no varchar(50) DEFAULT NULL COMMENT '营业执照号',
  tax_certificate_no varchar(50) DEFAULT NULL COMMENT '税务登记证号',
  loan_card_no varchar(30) DEFAULT NULL COMMENT '贷款卡号',
  last_check_year varchar(10) DEFAULT NULL COMMENT '最后年检年度',
  busi_scope varchar(512) DEFAULT NULL COMMENT '业务范围',
  customer_dev_evolution text DEFAULT NULL COMMENT '客户发展沿革（背景）及重大机构变革',
  related_trade text DEFAULT NULL COMMENT '关联交易情况',
  related_guarantee text DEFAULT NULL COMMENT '关联担保情况',
  related_capital_tieup text DEFAULT NULL COMMENT '关联资金占用',
  busi_qualification text DEFAULT NULL COMMENT '主营业务范围及资质情况',
  busi_place text DEFAULT NULL COMMENT '经营场所调查情况',
  query_time datetime DEFAULT NULL COMMENT '客户信用状况查询时间',
  loan_repay_situation_customer text DEFAULT NULL COMMENT '(客户)前两年度银行借贷变动及异常情况解放前明，并分析公司授信到期时客户需偿还的金额及偿付能力。',
  loan_repay_situation_persional text DEFAULT NULL COMMENT '(个人)前两年度银行借贷变动及异常情况解放前明，并分析公司授信到期时客户需偿还的金额及偿付能力。',
  other text DEFAULT NULL COMMENT '其他情况',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (m_review_id),
  INDEX f_investigation_mainly_review_form_id_i (form_id),
  INDEX f_investigation_mainly_review_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目调查 - 客户主体评价';

--
-- Definition for table f_investigation_mainly_review_asset_and_liability
--
DROP TABLE IF EXISTS f_investigation_mainly_review_asset_and_liability;
CREATE TABLE f_investigation_mainly_review_asset_and_liability (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  m_review_id bigint(20) NOT NULL COMMENT '对应客户主体评价ID',
  name varchar(50) DEFAULT NULL COMMENT '际控制人或自然人股东姓名',
  sex varchar(10) DEFAULT NULL COMMENT '性别',
  cert_no varchar(30) DEFAULT NULL COMMENT '证件号码',
  house_num int(20) DEFAULT NULL COMMENT '房屋数量',
  car_num int(20) DEFAULT NULL COMMENT '车辆数量',
  invest_amount bigint(20) DEFAULT NULL COMMENT '投资金额',
  deposit_amount bigint(20) DEFAULT NULL COMMENT '个人存款金额',
  marital_status varchar(128) DEFAULT NULL COMMENT '婚姻情况',
  spouse_name varchar(50) DEFAULT NULL COMMENT '配有姓名',
  spouse_cert_type varchar(50) DEFAULT NULL COMMENT '配偶证件类型',
  spouse_cert_no varchar(20) DEFAULT NULL COMMENT '配偶证件号码',
  spouse_contact_no varchar(11) DEFAULT NULL COMMENT '配偶联系电话(手机)',
  spouse_address varchar(50) DEFAULT NULL COMMENT '配偶家庭住址',
  spouse_immovable_property varchar(128) DEFAULT NULL COMMENT '配偶不动产信息',
  spouse_movable_property varchar(50) DEFAULT NULL COMMENT '配偶动产信息',
  has_bank_loan varchar(10) DEFAULT NULL COMMENT '是否有银行贷款',
  has_folk_loan varchar(10) DEFAULT NULL COMMENT '是否有民间贷款',
  bank_loan_amount bigint(20) DEFAULT NULL COMMENT '银行贷款总额',
  folk_loan_amount bigint(20) DEFAULT NULL COMMENT '民间贷款总额',
  consumer_loan_bank varchar(50) DEFAULT NULL COMMENT '消费类贷款银行',
  consumer_loan_amount bigint(20) DEFAULT NULL COMMENT '消费类贷款金额',
  consumer_loan_start_date datetime DEFAULT NULL COMMENT '消费类贷款开始时间',
  consumer_loan_end_date datetime DEFAULT NULL COMMENT '消费类贷款结束时间',
  busines_loan_bank varchar(50) DEFAULT NULL COMMENT '个人经营性贷款银行',
  busines_loan_amount bigint(20) DEFAULT NULL COMMENT '个人经营性贷款金额',
  busines_loan_start_date datetime DEFAULT NULL COMMENT '个人经营性贷款开始时间',
  busines_loan_end_date datetime DEFAULT NULL COMMENT '个人经营性贷款结束时间',
  mortgage_loan_bank varchar(50) DEFAULT NULL COMMENT '按揭贷款银行',
  mortgage_loan_amount bigint(20) DEFAULT NULL COMMENT '按揭贷款金额',
  mortgage_loan_start_date datetime DEFAULT NULL COMMENT '按揭贷款开始时间',
  mortgage_loan_end_date datetime DEFAULT NULL COMMENT '按揭贷款结束时间',
  sort_order int(11) DEFAULT 0,
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目调查 - 客户主体评价 - 实际控制人或自然人股东个人资产、负债状况（非国有必填）';

--
-- Definition for table f_investigation_mainly_review_bank_info
--
DROP TABLE IF EXISTS f_investigation_mainly_review_bank_info;
CREATE TABLE f_investigation_mainly_review_bank_info (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  m_review_id bigint(20) NOT NULL COMMENT '对应客户主体评价ID',
  bank_name varchar(50) DEFAULT NULL COMMENT '开户行',
  bank_desc varchar(128) DEFAULT NULL COMMENT '开户描述',
  account_no varchar(30) DEFAULT NULL COMMENT '账号',
  basic_flag varchar(10) DEFAULT NULL COMMENT '是否基本账号',
  sort_order int(11) DEFAULT 0,
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目调查 - 客户主体评价 - 客户开户情况';

--
-- Definition for table f_investigation_mainly_review_certificate
--
DROP TABLE IF EXISTS f_investigation_mainly_review_certificate;
CREATE TABLE f_investigation_mainly_review_certificate (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  m_review_id bigint(20) NOT NULL COMMENT '对应客户主体评价ID',
  owner varchar(128) DEFAULT NULL COMMENT '证书所属',
  certificate_name varchar(50) DEFAULT NULL COMMENT '资格证书名称',
  certificate_code varchar(128) DEFAULT NULL COMMENT '编码',
  valid_date date DEFAULT NULL COMMENT '有效期yyyyMMdd',
  certificate_url varchar(128) DEFAULT NULL COMMENT '拍照地址',
  sort_order int(11) DEFAULT 0,
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目调查 - 客户主体评价 - 资格证书';

--
-- Definition for table f_investigation_mainly_review_credit_info
--
DROP TABLE IF EXISTS f_investigation_mainly_review_credit_info;
CREATE TABLE f_investigation_mainly_review_credit_info (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  m_review_id bigint(20) NOT NULL COMMENT '对应客户主体评价ID',
  site_name varchar(50) DEFAULT NULL COMMENT '网站名称',
  site varchar(128) DEFAULT NULL COMMENT '网址',
  status varchar(128) DEFAULT NULL COMMENT '查询状态',
  remark varchar(512) DEFAULT NULL COMMENT '异常备注',
  sort_order int(11) DEFAULT 0,
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目调查 - 客户主体评价 - 其他信用信息（工商、税务、诉讼、环保等信息）';

--
-- Definition for table f_investigation_mainly_review_credit_status
--
DROP TABLE IF EXISTS f_investigation_mainly_review_credit_status;
CREATE TABLE f_investigation_mainly_review_credit_status (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  m_review_id bigint(20) NOT NULL COMMENT '对应客户主体评价ID',
  type varchar(10) NOT NULL COMMENT '类型（客户/个人-企业实际控制人、主要股东、管理人员、担保人等）',
  persional_name varchar(50) DEFAULT NULL COMMENT '企业实际控制人、主要股东、管理人员、担保人等（类型为个人时填写）',
  persional_desc varchar(128) DEFAULT NULL COMMENT '个人描述（类型为个人时填写）',
  loan_institution varchar(50) DEFAULT NULL COMMENT '融资机构',
  loan_balance bigint(20) DEFAULT NULL COMMENT '融资余额',
  loan_start_date datetime DEFAULT NULL COMMENT '融资期限起',
  loan_end_date datetime DEFAULT NULL COMMENT '融资期限止',
  loan_cost bigint(20) DEFAULT NULL COMMENT '融资成本',
  guarantee_pledge varchar(512) DEFAULT NULL COMMENT '担保方式及扣保物',
  consideration varchar(128) DEFAULT NULL COMMENT '提供的对价',
  sort_order int(11) DEFAULT 0,
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目调查 - 客户主体评价 - 信用状况';

--
-- Definition for table f_investigation_mainly_review_external_guarantee
--
DROP TABLE IF EXISTS f_investigation_mainly_review_external_guarantee;
CREATE TABLE f_investigation_mainly_review_external_guarantee (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  m_review_id bigint(20) DEFAULT NULL COMMENT '对应客户主体评价ID',
  type varchar(10) DEFAULT NULL COMMENT '类型（客户/个人-企业实际控制人、主要股东、管理人员、担保人等）',
  persional_name varchar(50) DEFAULT NULL COMMENT '企业实际控制人、主要股东、管理人员、担保人等（类型为个人时填写）',
  persional_desc varchar(128) DEFAULT NULL COMMENT '个人描述（类型为个人时填写）',
  warrantee varchar(50) DEFAULT NULL COMMENT '被担保人',
  amount bigint(20) DEFAULT NULL COMMENT '担保金额',
  guarrantee_way varchar(50) DEFAULT NULL COMMENT '担保方式',
  time_limit varchar(30) DEFAULT NULL COMMENT '担保期限',
  consideration varchar(128) DEFAULT NULL COMMENT '提供的对价',
  sort_order int(11) DEFAULT 0,
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目调查 - 客户主体评价 - 对外担保情况';

--
-- Definition for table f_investigation_mainly_review_related_company
--
DROP TABLE IF EXISTS f_investigation_mainly_review_related_company;
CREATE TABLE f_investigation_mainly_review_related_company (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  m_review_id bigint(20) NOT NULL COMMENT '对应客户主体评价ID',
  relation varchar(50) NOT NULL COMMENT '关系 (客户下属公司、全资和控股子公司/客户主要参股公司/客户其它关联公司)',
  relation_desc varchar(128) DEFAULT NULL COMMENT '从属关系/关联关系',
  company_name varchar(50) DEFAULT NULL COMMENT '公司名称',
  register_capital bigint(20) DEFAULT NULL COMMENT '注册资本',
  capital_ratio decimal(12, 4) DEFAULT NULL COMMENT '持股比例',
  major_busi varchar(128) DEFAULT NULL COMMENT '主营业务',
  asset_scale varchar(50) DEFAULT NULL COMMENT '资产规模',
  asset_liability_ratio decimal(12, 4) DEFAULT NULL COMMENT '资产负债率',
  net_profit_this_year bigint(20) DEFAULT NULL COMMENT '本年净利润',
  net_profit_last_year bigint(20) DEFAULT NULL COMMENT '去年净利润',
  sort_order int(11) DEFAULT 0,
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目调查 - 客户主体评价 - 相关联公司';

--
-- Definition for table f_investigation_mainly_review_stockholder
--
DROP TABLE IF EXISTS f_investigation_mainly_review_stockholder;
CREATE TABLE f_investigation_mainly_review_stockholder (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  m_review_id bigint(20) NOT NULL COMMENT '对应客户评价ID',
  holder_name varchar(50) DEFAULT NULL COMMENT '股东名称',
  holder_type varchar(50) DEFAULT NULL COMMENT '股东性质',
  holder_cert_type varchar(50) DEFAULT NULL COMMENT '股东证件类型',
  holder_cert_no varchar(50) DEFAULT NULL COMMENT '股东证件号码(注册号)',
  actual_investment bigint(20) DEFAULT NULL COMMENT '实际投资',
  paidin_capital_ratio decimal(12, 4) DEFAULT NULL COMMENT '占实收资本比例',
  holder_major_busi varchar(128) DEFAULT NULL COMMENT '股东主营业务',
  capital_scale bigint(20) DEFAULT NULL COMMENT '股东规模（资产）',
  produce_scale bigint(20) DEFAULT NULL COMMENT '股东规模（生产能力）',
  income_scale bigint(20) DEFAULT NULL COMMENT '股东规模（收入）',
  holder_contact_no varchar(16) DEFAULT NULL COMMENT '股东手机号',
  marital_status varchar(128) DEFAULT NULL COMMENT '婚姻情况',
  spouse_name varchar(50) DEFAULT NULL COMMENT '配偶姓名',
  spouse_cert_type varchar(50) DEFAULT NULL COMMENT '配偶证件类型',
  spouse_cert_no varchar(50) DEFAULT NULL COMMENT '配偶证件号码',
  spouse_contact_no varchar(20) DEFAULT NULL COMMENT '配偶联系电话(手机)',
  sort_order int(11) DEFAULT 0,
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目调查 - 客户主体评价 - 主要股东情况表';

--
-- Definition for table f_investigation_major_events
--
DROP TABLE IF EXISTS f_investigation_major_events;
CREATE TABLE f_investigation_major_events (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) NOT NULL COMMENT '对应表单ID',
  project_code varchar(50) DEFAULT NULL COMMENT '对应项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '对应项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  financial_condition text DEFAULT NULL COMMENT '其他重点财务情况调查（民间借贷、异常科目等）',
  investment text DEFAULT NULL COMMENT '多元化投资调查',
  other_events text DEFAULT NULL COMMENT '其他重大事项调查（城市开发类项目对当地经济、财政、支持程度的分析填写本项内容）',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  INDEX f_investigation_major_events_form_id_i (form_id),
  INDEX f_investigation_major_events_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目调查 - 客户重大事项调查';

--
-- Definition for table f_investigation_opability_review
--
DROP TABLE IF EXISTS f_investigation_opability_review;
CREATE TABLE f_investigation_opability_review (
  op_review_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) NOT NULL COMMENT '对应表单ID',
  project_code varchar(50) DEFAULT NULL COMMENT '对应项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '对应项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  strategy_marketpos text DEFAULT NULL COMMENT '客户发展战略及市场定位',
  industry_env text DEFAULT NULL COMMENT '客户所在行业的宏观环境分析',
  competitiveness_rival text DEFAULT NULL COMMENT '客户核心竞争力评价、主要竞争对手基本情况',
  explaination text DEFAULT NULL COMMENT '解释与说明',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (op_review_id),
  INDEX f_investigation_opability_review_form_id_i (form_id),
  INDEX f_investigation_opability_review_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目调查 - 客户运营能力评价';

--
-- Definition for table f_investigation_opability_review_product_structure
--
DROP TABLE IF EXISTS f_investigation_opability_review_product_structure;
CREATE TABLE f_investigation_opability_review_product_structure (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  op_review_id bigint(20) NOT NULL COMMENT '对应客户运营能力评价ID',
  name varchar(50) DEFAULT NULL COMMENT '主要产品',
  report_period1 varchar(20) DEFAULT NULL COMMENT '报告期1',
  report_period2 varchar(20) DEFAULT NULL COMMENT '报告期2',
  report_period3 varchar(20) DEFAULT NULL COMMENT '报告期3',
  income1 bigint(20) DEFAULT NULL COMMENT '收入1',
  income2 bigint(20) DEFAULT NULL COMMENT '收入2',
  income3 bigint(20) DEFAULT NULL COMMENT '收入3',
  income_ratio1 decimal(12, 4) DEFAULT NULL COMMENT '占比1',
  income_ratio2 decimal(12, 4) DEFAULT NULL COMMENT '占比2',
  income_ratio3 decimal(12, 4) DEFAULT NULL COMMENT '占比3',
  sort_order int(11) DEFAULT 0,
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目调查 - 客户运营能力评价 - 客户主要产品结构、下游主要客户情况（城投类客户根据实际情况可不填写）';

--
-- Definition for table f_investigation_opability_review_updown_stream
--
DROP TABLE IF EXISTS f_investigation_opability_review_updown_stream;
CREATE TABLE f_investigation_opability_review_updown_stream (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  op_review_id bigint(20) NOT NULL COMMENT '对应客户运营能力评价ID',
  name varchar(50) DEFAULT NULL COMMENT '对方名称',
  clearing_form varchar(128) DEFAULT NULL COMMENT '结算方式',
  payment_terms varchar(30) DEFAULT NULL COMMENT '帐期',
  ending_balance bigint(20) DEFAULT NULL COMMENT '期末余额',
  remark varchar(512) DEFAULT NULL COMMENT '备注（产品种类、合作年限等）',
  sort_order int(11) DEFAULT 0,
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目调查 - 客户运营能力评价 - 客户主要上下游情况（余额前五大）城投类客户根据实际情况可不填写';

--
-- Definition for table f_investigation_project_status
--
DROP TABLE IF EXISTS f_investigation_project_status;
CREATE TABLE f_investigation_project_status (
  status_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '项目情况调查ID',
  form_id bigint(20) NOT NULL COMMENT '对应表单ID',
  project_code varchar(50) DEFAULT NULL COMMENT '对应项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '对应项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  overview text DEFAULT NULL COMMENT '项目概述（简要说明客户/项目发起人的项目计划，生产规模，地理位置等）',
  background text DEFAULT NULL COMMENT '项目建设背景/必要性',
  approval text DEFAULT NULL COMMENT '项目审批依据或手续',
  progress text DEFAULT NULL COMMENT '项目建设进度（如项目已开工，请简述工程形象进度和资金投入进度）',
  market_outlook text DEFAULT NULL COMMENT '市场前景分析',
  total_cost bigint(20) DEFAULT NULL COMMENT '项目总成本',
  cost_fundraising text DEFAULT NULL COMMENT '项目总成本及资金筹措',
  benefit_review text DEFAULT NULL COMMENT '项目财务效益评估',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (status_id),
  INDEX f_investigation_project_status_form_id_i (form_id),
  INDEX f_investigation_project_status_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目调查 - 项目情况调查（固定资产贷款授信业务必须填写，其它业务不涉及项目情况的不填）';

--
-- Definition for table f_investigation_project_status_fund
--
DROP TABLE IF EXISTS f_investigation_project_status_fund;
CREATE TABLE f_investigation_project_status_fund (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  status_id bigint(20) NOT NULL COMMENT '项目情况调查ID',
  item varchar(32) DEFAULT NULL COMMENT '项目',
  item_code varchar(32) DEFAULT NULL COMMENT '项目标识',
  item_amount bigint(20) DEFAULT NULL COMMENT '项目金额',
  item_percent decimal(12, 4) DEFAULT NULL COMMENT '项目百分比',
  fund_source varchar(32) DEFAULT NULL COMMENT '资金来源',
  fund_code varchar(32) DEFAULT NULL COMMENT '资金来源标识',
  fund_amount bigint(20) DEFAULT NULL COMMENT '资金金额',
  fund_percent decimal(12, 4) DEFAULT NULL COMMENT '资金百分比',
  sort_order int(11) DEFAULT 0,
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目调查 - 项目情况调查 - 项目投资与资金筹措表';

--
-- Definition for table f_investigation_risk
--
DROP TABLE IF EXISTS f_investigation_risk;
CREATE TABLE f_investigation_risk (
  risk_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '风险点分析ID',
  form_id bigint(20) NOT NULL COMMENT '对应表单ID',
  project_code varchar(50) DEFAULT NULL COMMENT '对应项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '对应项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  risk_analysis text DEFAULT NULL COMMENT '风险点分析',
  conclusion text DEFAULT NULL COMMENT '结论意见',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (risk_id),
  INDEX f_investigation_risk_form_id_i (form_id),
  INDEX f_investigation_risk_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目调查 - 风险点分析';

--
-- Definition for table f_investigation_underwriting
--
DROP TABLE IF EXISTS f_investigation_underwriting;
CREATE TABLE f_investigation_underwriting (
  underwriting_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  form_id bigint(20) NOT NULL COMMENT '对应表单ID',
  project_code varchar(50) DEFAULT NULL COMMENT '对应项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '对应项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称(发行主体)',
  project_source varchar(20) DEFAULT NULL COMMENT '项目来源',
  project_gist varchar(20) DEFAULT NULL COMMENT '项目依据',
  financing_amount bigint(20) DEFAULT NULL COMMENT '本次申请融资金额',
  demand_date datetime DEFAULT NULL COMMENT '发行人资金需求日期',
  setup_date datetime DEFAULT NULL COMMENT '立项日期',
  issue_date datetime DEFAULT NULL COMMENT '预计发行日期',
  collect_scale bigint(20) DEFAULT NULL COMMENT '募集规模',
  time_limit int(11) DEFAULT NULL COMMENT '融资期限',
  time_unit varchar(10) DEFAULT NULL COMMENT '融资单位(年,月,日)',
  total_cost decimal(12, 4) DEFAULT NULL COMMENT '总成本(百分比)',
  issue_rate decimal(12, 4) DEFAULT NULL COMMENT '发行利率',
  has_credit varchar(10) DEFAULT NULL COMMENT '是否增信',
  exchange_id bigint(20) DEFAULT NULL COMMENT '交易所ID',
  exchange_name varchar(128) DEFAULT NULL COMMENT '交易所名称',
  charge_rate decimal(12, 4) DEFAULT NULL COMMENT '收费费率',
  charge_unit varchar(10) DEFAULT NULL COMMENT '收费费率单位(%/年,单)',
  law_office_rate decimal(12, 4) DEFAULT NULL COMMENT '律所费率',
  law_office_unit varchar(10) DEFAULT NULL COMMENT '律所费率单位(%/年,单)',
  club_rate decimal(12, 4) DEFAULT NULL COMMENT '会所费率',
  club_unit varchar(10) DEFAULT NULL COMMENT '会所费率单位(%/年,单)',
  other_rate decimal(12, 4) DEFAULT NULL COMMENT '其他费用',
  other_unit varchar(10) DEFAULT NULL COMMENT '其他费率单位(%/年,单)',
  underwriting_rate decimal(12, 4) DEFAULT NULL COMMENT '承销费率',
  underwriting_unit varchar(10) DEFAULT NULL COMMENT '承销费率单位(%/年,单)',
  charge_way varchar(10) DEFAULT NULL COMMENT '收费方式',
  balance bigint(20) DEFAULT NULL COMMENT '发行人扣除费用后实际到账金额',
  intro text DEFAULT NULL COMMENT '发行主体简要介绍',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (underwriting_id),
  INDEX f_investigation_form_id_i (form_id),
  INDEX f_investigation_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目调查 - 承销项目情况表';

--
-- Definition for table f_loan_use_apply
--
DROP TABLE IF EXISTS f_loan_use_apply;
CREATE TABLE f_loan_use_apply (
  apply_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '申请ID',
  form_id bigint(20) NOT NULL COMMENT '对应表单ID',
  project_code varchar(50) DEFAULT NULL COMMENT '对应项目编号',
  notification_id bigint(20) DEFAULT NULL COMMENT '对应收费通知单ID',
  apply_type varchar(10) DEFAULT NULL COMMENT '放用款类型',
  amount bigint(20) DEFAULT NULL COMMENT '本次申请金额',
  original_amount bigint(20) DEFAULT NULL COMMENT '授信金额',
  loaned_amount bigint(20) DEFAULT NULL COMMENT '已放款金额',
  used_amount bigint(20) DEFAULT NULL COMMENT '已用款金额',
  released_amount bigint(20) DEFAULT NULL COMMENT '已解保金额',
  applying_loan_amount bigint(20) DEFAULT NULL COMMENT '申请中放款金额',
  applying_use_amount bigint(20) DEFAULT NULL COMMENT '申请中用款金额',
  is_maximum_amount varchar(10) DEFAULT NULL COMMENT '是否最高授信额',
  expect_loan_date datetime DEFAULT NULL COMMENT '计划放款时间',
  receipt_name varchar(50) DEFAULT NULL COMMENT '收款帐户名',
  receipt_bank varchar(128) DEFAULT NULL COMMENT '收款银行',
  receipt_account varchar(30) DEFAULT NULL COMMENT '收款账号',
  remark text DEFAULT NULL COMMENT '备注',
  cash_deposit_amount bigint(20) DEFAULT NULL COMMENT '本次申请划付保证金金额',
  cash_deposit_bank varchar(50) DEFAULT NULL COMMENT '划出银行',
  cash_deposit_time_limit int(11) DEFAULT NULL COMMENT '存入期限',
  cash_deposit_time_unit varchar(10) DEFAULT NULL COMMENT '存入期限单位 D/M/Y',
  cash_deposit_ratio decimal(12, 4) DEFAULT NULL COMMENT '本次申请划付保证金比例',
  cash_deposit_end_time datetime DEFAULT NULL COMMENT '保证金划付截止时间',
  busi_manager_statement text DEFAULT NULL COMMENT '客户经理意见',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (apply_id),
  INDEX f_loan_pay_apply_form_id_i (form_id),
  INDEX f_loan_pay_apply_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '放用款申请';

--
-- Definition for table f_loan_use_apply_fee
--
DROP TABLE IF EXISTS f_loan_use_apply_fee;
CREATE TABLE f_loan_use_apply_fee (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  apply_id bigint(20) NOT NULL COMMENT '放用款申请单',
  fee_type varchar(50) DEFAULT NULL COMMENT '收费类型',
  fee_type_desc varchar(50) DEFAULT NULL COMMENT '收费类型描述',
  charge_base bigint(20) DEFAULT NULL COMMENT '收费基数',
  charge_rate decimal(12, 4) DEFAULT NULL COMMENT '收取费率',
  start_time datetime DEFAULT NULL COMMENT '计费开始时间',
  end_time datetime DEFAULT NULL COMMENT '计费结束时间',
  charge_amount bigint(20) DEFAULT NULL COMMENT '收取金额',
  remark varchar(128) DEFAULT NULL COMMENT '收费备注',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '放用款 - 费用收取情况';

--
-- Definition for table f_loan_use_apply_receipt
--
DROP TABLE IF EXISTS f_loan_use_apply_receipt;
CREATE TABLE f_loan_use_apply_receipt (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  apply_id bigint(20) DEFAULT NULL COMMENT '申请ID',
  project_code varchar(50) DEFAULT NULL,
  actual_amount bigint(20) DEFAULT NULL COMMENT '实际金额',
  actual_loan_time datetime DEFAULT NULL COMMENT '实际放款时间',
  actual_deposit_amount bigint(20) DEFAULT NULL COMMENT '实际保证金',
  busi_sub_type varchar(50) DEFAULT NULL COMMENT '业务品种细类',
  busi_sub_type_name varchar(50) DEFAULT NULL COMMENT '业务品种细类名称',
  voucher_url text DEFAULT NULL COMMENT '付款凭证',
  other_url text DEFAULT NULL COMMENT '其他资料url',
  remark text DEFAULT NULL COMMENT '备注',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '放用款申请-回执';

--
-- Definition for table f_pay_notification
--
DROP TABLE IF EXISTS f_pay_notification;
CREATE TABLE f_pay_notification (
  notification_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '通知ID',
  form_id bigint(20) NOT NULL COMMENT '对应表单ID',
  project_code varchar(50) DEFAULT NULL COMMENT '对应项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '对应项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '客户ID',
  customer_name varchar(50) DEFAULT NULL COMMENT '客户名称',
  is_agent_pay varchar(50) DEFAULT NULL COMMENT '是否代付',
  pay_amount bigint(20) DEFAULT NULL COMMENT '付款金额',
  pay_name varchar(50) DEFAULT NULL COMMENT '付款方户名',
  pay_account varchar(30) DEFAULT NULL COMMENT '付款账号',
  pay_bank varchar(30) DEFAULT NULL COMMENT '付款银行',
  pay_time datetime DEFAULT NULL COMMENT '付款时间',
  remark varchar(512) DEFAULT NULL COMMENT '备注',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (notification_id),
  INDEX f_pay_notification_form_id_i (form_id),
  INDEX f_pay_notification_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '收费通知';

--
-- Definition for table f_pay_notification_fee
--
DROP TABLE IF EXISTS f_pay_notification_fee;
CREATE TABLE f_pay_notification_fee (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  notification_id bigint(20) NOT NULL COMMENT '收费通知ID',
  type varchar(20) DEFAULT NULL COMMENT '费用/保证金',
  charge_type varchar(20) DEFAULT NULL COMMENT '收费类型',
  charge_type_desc varchar(20) DEFAULT NULL COMMENT '收费类型描述',
  charge_way varchar(50) DEFAULT NULL COMMENT '收费方式（一次性收取/按月等）',
  charge_unit varchar(50) DEFAULT NULL COMMENT '按金额收取/按费率收取',
  charge_amount bigint(20) DEFAULT NULL COMMENT '收取金额',
  charge_rate decimal(12, 4) DEFAULT NULL COMMENT '收取费率',
  actual_charge_amount bigint(20) DEFAULT NULL COMMENT '实际收取金额',
  actual_charge_amount_check bigint(20) DEFAULT NULL COMMENT '财务确认的收取金额',
  start_time datetime DEFAULT NULL COMMENT '计费开始时间',
  end_time datetime DEFAULT NULL COMMENT '计费结束时间',
  remark varchar(128) DEFAULT NULL COMMENT '收费备注',
  sort_order int(11) DEFAULT NULL,
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '收费通知 - 收费明细';

--
-- Definition for table f_project
--
DROP TABLE IF EXISTS f_project;
CREATE TABLE f_project (
  project_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  project_code varchar(50) DEFAULT NULL COMMENT '项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '项目名称',
  form_id bigint(20) NOT NULL COMMENT '对应表单ID',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  customer_type varchar(10) DEFAULT NULL COMMENT '客户类型',
  busi_type varchar(10) DEFAULT NULL COMMENT '业务类型',
  busi_type_name varchar(50) DEFAULT NULL COMMENT '业务类型名称',
  industry_code varchar(128) DEFAULT NULL COMMENT '所属行业',
  industry_name varchar(128) DEFAULT NULL COMMENT '行业名称',
  time_limit int(11) DEFAULT NULL COMMENT '期限',
  time_unit varchar(10) DEFAULT NULL COMMENT '期限单位',
  amount bigint(20) DEFAULT NULL COMMENT '金额',
  busi_manager_id bigint(20) DEFAULT NULL COMMENT '业务经理ID（默认同创建人）',
  busi_manager_account varchar(20) DEFAULT NULL COMMENT '业务经理账号',
  busi_manager_name varchar(50) DEFAULT NULL COMMENT '业务经理名称',
  busi_managerb_id bigint(20) DEFAULT NULL COMMENT '客户经理B',
  busi_managerb_account varchar(20) DEFAULT NULL COMMENT '业务经理B角账号',
  busi_managerb_name varchar(50) DEFAULT NULL COMMENT '客户经理B',
  dept_id bigint(20) DEFAULT NULL COMMENT '所属部门',
  dept_code varchar(30) DEFAULT NULL COMMENT '部门编号',
  dept_name varchar(50) DEFAULT NULL COMMENT '所属部门名称',
  dept_path varchar(256) DEFAULT NULL COMMENT '部门ID的路径',
  dept_path_name varchar(500) DEFAULT NULL COMMENT '部门路径中文名称',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (project_id),
  INDEX f_project_form_id_i (form_id),
  INDEX f_project_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '表单-立项-项目信息（通用）';

--
-- Definition for table f_project_bank_loan
--
DROP TABLE IF EXISTS f_project_bank_loan;
CREATE TABLE f_project_bank_loan (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) NOT NULL COMMENT '对应表单ID',
  project_code varchar(50) DEFAULT NULL COMMENT '项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  funding_side varchar(50) DEFAULT NULL COMMENT '资金方',
  channel_side varchar(50) DEFAULT NULL COMMENT '通道方',
  loan_amount bigint(20) DEFAULT NULL COMMENT '授信金额',
  loan_balance bigint(20) DEFAULT NULL COMMENT '贷款金额',
  loan_start_time datetime DEFAULT NULL COMMENT '贷款开始时间',
  loan_end_time datetime DEFAULT NULL COMMENT '贷款结束时间',
  guarantee_way varchar(50) DEFAULT NULL COMMENT '担保方式',
  sort_order int(11) DEFAULT NULL,
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  INDEX f_project_bank_loan_i (form_id),
  INDEX f_project_bank_loan_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '表单-立项-银行贷款情况';

--
-- Definition for table f_project_contract
--
DROP TABLE IF EXISTS f_project_contract;
CREATE TABLE f_project_contract (
  contract_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '合同集ID',
  form_id bigint(20) NOT NULL COMMENT '对应的表单ID',
  project_code varchar(50) DEFAULT NULL COMMENT '对应项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '对应项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (contract_id),
  INDEX f_project_contract_form_id_i (form_id),
  INDEX f_project_contract_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '授信前管理 - 项目合同集';

--
-- Definition for table f_project_contract_check
--
DROP TABLE IF EXISTS f_project_contract_check;
CREATE TABLE f_project_contract_check (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  contract_id bigint(20) NOT NULL COMMENT '合同集ID',
  contract_item_id bigint(20) NOT NULL COMMENT '合同ID',
  user_id bigint(20) DEFAULT NULL COMMENT '审核人id',
  user_name varchar(50) DEFAULT NULL COMMENT '审核人名',
  file_url text DEFAULT NULL COMMENT '附件',
  remark varchar(2000) DEFAULT NULL COMMENT '备注',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '授信前管理 - 项目合同集- 合同-合同审核记录';

--
-- Definition for table f_project_contract_extra_value
--
DROP TABLE IF EXISTS f_project_contract_extra_value;
CREATE TABLE f_project_contract_extra_value (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  contract_id bigint(20) NOT NULL COMMENT '合同集ID',
  contract_item_id bigint(20) NOT NULL COMMENT '合同ID',
  contract_code varchar(255) DEFAULT NULL COMMENT '合同编号',
  template_id bigint(20) DEFAULT NULL COMMENT '合同模板',
  document_name varchar(50) DEFAULT NULL COMMENT '字段name属性',
  document_describe varchar(128) DEFAULT NULL COMMENT '字段name描述',
  document_value varchar(50) DEFAULT NULL COMMENT '字段value属性',
  document_type varchar(11) DEFAULT 'input' COMMENT '字段类别-现在只有input',
  document_modify_num int(11) DEFAULT 0 COMMENT '字段修改次数',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '授信前管理 - 项目合同集- 合同-制式合同属性';

--
-- Definition for table f_project_contract_extra_value_modify
--
DROP TABLE IF EXISTS f_project_contract_extra_value_modify;
CREATE TABLE f_project_contract_extra_value_modify (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  contract_id bigint(20) NOT NULL COMMENT '合同集ID',
  contract_item_id bigint(20) NOT NULL COMMENT '合同ID',
  contract_code varchar(255) DEFAULT NULL COMMENT '合同编号',
  template_id bigint(20) DEFAULT NULL COMMENT '合同模板',
  user_id bigint(20) DEFAULT NULL COMMENT '修改人id',
  user_name varchar(50) DEFAULT NULL COMMENT '修改人名',
  value_id bigint(20) NOT NULL COMMENT '对应value表id',
  document_name varchar(50) NOT NULL COMMENT '字段name属性',
  document_describe varchar(128) DEFAULT NULL COMMENT '字段name描述',
  document_value_old varchar(50) DEFAULT NULL COMMENT '字段旧value属性',
  document_value_new varchar(50) DEFAULT NULL COMMENT '字段新value属性',
  document_type varchar(11) DEFAULT 'input' COMMENT '字段类别-现在只有input',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '授信前管理 - 项目合同集- 合同-制式合同属性修改记录';

--
-- Definition for table f_project_contract_item
--
DROP TABLE IF EXISTS f_project_contract_item;
CREATE TABLE f_project_contract_item (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  contract_id bigint(20) NOT NULL COMMENT '合同集ID',
  contract_code varchar(255) DEFAULT NULL COMMENT '合同编号',
  contract_name varchar(20) DEFAULT NULL COMMENT '合同名',
  contract_status varchar(50) DEFAULT NULL COMMENT '合同状态',
  pledge_id bigint(20) DEFAULT NULL COMMENT '授信条件ID',
  template_id bigint(20) DEFAULT NULL COMMENT '合同模板',
  contract_type varchar(512) DEFAULT NULL COMMENT '合同类型',
  is_main varchar(10) DEFAULT NULL COMMENT '是否主合同',
  stamp_phase varchar(128) DEFAULT NULL COMMENT '用印阶段',
  cnt bigint(10) DEFAULT NULL COMMENT '一式几（份）',
  credit_measure varchar(255) DEFAULT NULL COMMENT '关联授信措施',
  file_url text DEFAULT NULL COMMENT '合同附件',
  content mediumtext DEFAULT NULL COMMENT '合同内容',
  content_message mediumtext DEFAULT NULL COMMENT '合同内容展示',
  remark varchar(2000) DEFAULT NULL COMMENT '备注',
  audit_info varchar(2000) DEFAULT NULL COMMENT '审核意见',
  signed_time timestamp NULL DEFAULT NULL COMMENT '签订时间',
  sign_person_a_id bigint(20) DEFAULT 0 COMMENT '签订人A id',
  sign_person_a varchar(20) DEFAULT NULL COMMENT '签订人A',
  sign_person_b_id bigint(20) DEFAULT 0 COMMENT '签订人B id',
  sign_person_b varchar(20) DEFAULT NULL COMMENT '签订人B',
  contract_scan_url text DEFAULT NULL COMMENT '合同扫描URL',
  invalid_reason varchar(255) DEFAULT NULL COMMENT '作废原因',
  sort_order int(11) DEFAULT 0,
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '授信前管理 - 项目合同集- 合同';

--
-- Definition for table f_project_counter_guarantee_guarantor
--
DROP TABLE IF EXISTS f_project_counter_guarantee_guarantor;
CREATE TABLE f_project_counter_guarantee_guarantor (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) NOT NULL COMMENT '对应表单ID',
  project_code varchar(50) DEFAULT NULL COMMENT '项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  guarantor varchar(20) DEFAULT NULL COMMENT '保证人名称',
  legal_persion varchar(20) DEFAULT NULL COMMENT '法定代表人',
  register_capital bigint(20) DEFAULT NULL COMMENT '注册资本',
  total_asset bigint(20) DEFAULT NULL COMMENT '资产总额',
  external_guarantee_amount bigint(20) DEFAULT NULL COMMENT '对外担保金额',
  sort_order int(11) DEFAULT 0 COMMENT '排序号',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  INDEX f_project_counter_guarantee_guarantor_form_id_i (form_id),
  INDEX f_project_counter_guarantee_guarantor_project_code (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '表单-立项-反担保/担保情况(保证人)';

--
-- Definition for table f_project_counter_guarantee_pledge
--
DROP TABLE IF EXISTS f_project_counter_guarantee_pledge;
CREATE TABLE f_project_counter_guarantee_pledge (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) NOT NULL COMMENT '对应表单ID',
  project_code varchar(50) DEFAULT NULL COMMENT '项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  type varchar(20) DEFAULT NULL COMMENT '抵押/质押',
  pledger varchar(50) DEFAULT NULL COMMENT '抵（质）押人全称',
  pledge_type varchar(50) DEFAULT NULL COMMENT '抵押物类型',
  pledge_name varchar(20) DEFAULT NULL COMMENT '主要抵（质）押物全称',
  net_value bigint(20) DEFAULT NULL COMMENT '净值',
  sort_order int(11) DEFAULT 0 COMMENT '排序号',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  INDEX f_project_counter_guarantee_pledge_form_id_i (form_id),
  INDEX f_project_counter_guarantee_pledge_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '表单-立项-反担保/担保情况(抵（质）押物)';

--
-- Definition for table f_project_customer_base_info
--
DROP TABLE IF EXISTS f_project_customer_base_info;
CREATE TABLE f_project_customer_base_info (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) NOT NULL COMMENT '对应表单ID',
  project_code varchar(50) DEFAULT NULL COMMENT '项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  customer_type varchar(10) DEFAULT NULL,
  cert_no varchar(50) DEFAULT NULL COMMENT '证件号码（个人的时候）',
  industry_code varchar(50) DEFAULT NULL COMMENT '行业编码',
  industry_name varchar(128) DEFAULT NULL COMMENT '行业名称',
  is_local_gov_platform varchar(10) DEFAULT NULL COMMENT '是否地方政府融资平台',
  is_export_oriented_economy varchar(10) DEFAULT NULL COMMENT '是否外向型经济客户',
  enterprise_type varchar(20) DEFAULT NULL COMMENT '企业性质',
  country_code varchar(50) DEFAULT 'China' COMMENT '所属区域 - 国家编码',
  country_name varchar(50) DEFAULT '中国' COMMENT '所属区域 - 国家名称',
  province_code varchar(50) DEFAULT NULL COMMENT '所属区域 - 省编码',
  province_name varchar(50) DEFAULT NULL COMMENT '所属区域 - 省名称',
  city_code varchar(20) DEFAULT NULL COMMENT '所属区域 - 市编码',
  city_name varchar(50) DEFAULT NULL COMMENT '所属区域 - 市名称',
  county_code varchar(20) DEFAULT NULL COMMENT '所属区域 - 地区编码',
  county_name varchar(128) DEFAULT NULL COMMENT '所属区域 - 地区名称',
  address varchar(255) DEFAULT NULL COMMENT '地址',
  contact_man varchar(20) DEFAULT NULL COMMENT '联系人',
  contact_no varchar(20) DEFAULT NULL COMMENT '联系人电话',
  established_time datetime DEFAULT NULL COMMENT '成立时间',
  scale varchar(50) DEFAULT NULL COMMENT '企业规模',
  staff_num varchar(50) DEFAULT NULL COMMENT '员工人数',
  busi_scope varchar(512) DEFAULT NULL COMMENT '经营范围',
  major_product varchar(512) DEFAULT NULL COMMENT '主导产品',
  legal_persion varchar(20) DEFAULT NULL COMMENT '法定代表人',
  legal_persion_cert_no varchar(20) DEFAULT NULL COMMENT '法定代表人身份证号',
  legal_persion_address varchar(256) DEFAULT NULL COMMENT '法定代表人地址',
  actual_control_man varchar(20) DEFAULT NULL COMMENT '实际控制人',
  actual_control_man_cert_no varchar(20) DEFAULT NULL COMMENT '实际控制人身份证',
  actual_control_man_address varchar(256) DEFAULT NULL COMMENT '实际控制人地址',
  register_capital bigint(20) DEFAULT NULL COMMENT '注册资本',
  total_asset bigint(20) DEFAULT NULL COMMENT '总资产',
  net_asset bigint(20) DEFAULT NULL COMMENT '净资产',
  asset_liability_ratio decimal(12, 4) DEFAULT NULL COMMENT '资产负债率',
  liquidity_ratio decimal(12, 4) DEFAULT NULL COMMENT '流动比率',
  quick_ratio decimal(12, 4) DEFAULT NULL COMMENT '速动比率',
  sales_proceeds_last_year bigint(20) DEFAULT NULL COMMENT '去年销售收入',
  sales_proceeds_this_year bigint(20) DEFAULT NULL COMMENT '今年销售收入',
  total_profit_last_year bigint(20) DEFAULT NULL COMMENT '去年利润总额',
  total_profit_this_year bigint(20) DEFAULT NULL COMMENT '今年利润总额',
  is_one_cert varchar(10) DEFAULT 'NO' COMMENT '是否三证合一',
  busi_license_no varchar(50) DEFAULT NULL COMMENT '营业执照号',
  busi_license_url varchar(128) DEFAULT NULL COMMENT '营业执照拍照',
  org_code varchar(50) DEFAULT NULL COMMENT '组织机构号',
  org_code_url varchar(128) DEFAULT NULL COMMENT '组织机构证件拍照',
  tax_certificate_no varchar(50) DEFAULT NULL COMMENT '税务登记证',
  tax_certificate_url varchar(128) DEFAULT NULL COMMENT '税务登记证拍照',
  apply_scanning_url varchar(128) DEFAULT NULL COMMENT '客户申请书扫描件',
  local_finance text DEFAULT NULL COMMENT '地方财政情况',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  INDEX f_project_customer_base_info_form_id_i (form_id),
  INDEX f_project_customer_base_info_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '表单-立项-企业基本信息';

--
-- Definition for table f_project_equity_structure
--
DROP TABLE IF EXISTS f_project_equity_structure;
CREATE TABLE f_project_equity_structure (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) NOT NULL COMMENT '对应表单ID',
  project_code varchar(50) DEFAULT NULL COMMENT '项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  stockholder varchar(20) DEFAULT NULL COMMENT '主要股东名称',
  capital_contributions bigint(20) DEFAULT NULL COMMENT '出资金额',
  contribution_way varchar(128) DEFAULT NULL COMMENT '出资方式',
  equity_ratio decimal(12, 4) DEFAULT NULL COMMENT '股权比例',
  sort_order int(11) DEFAULT 0,
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  INDEX f_project_equity_structure_form_id (form_id),
  INDEX f_project_equity_structure_project_code (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '表单-立项-股权结构';

--
-- Definition for table f_project_external_guarantee
--
DROP TABLE IF EXISTS f_project_external_guarantee;
CREATE TABLE f_project_external_guarantee (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) NOT NULL COMMENT '对应表单ID',
  project_code varchar(50) DEFAULT NULL COMMENT '项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  warrantee varchar(20) DEFAULT NULL COMMENT '被担保人',
  loan_bank varchar(50) DEFAULT NULL COMMENT '贷款银行',
  amount bigint(20) DEFAULT NULL COMMENT '担保金额',
  start_time datetime DEFAULT NULL COMMENT '担保期限-起',
  end_time datetime DEFAULT NULL COMMENT '担保期限-止',
  sort_order int(11) DEFAULT 0,
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '表单-立项-对外担保情况';

--
-- Definition for table f_project_financial
--
DROP TABLE IF EXISTS f_project_financial;
CREATE TABLE f_project_financial (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) DEFAULT NULL COMMENT '表单ID',
  project_code varchar(30) DEFAULT NULL COMMENT '项目编号',
  product_id bigint(20) DEFAULT NULL COMMENT '产品ID',
  product_type varchar(20) DEFAULT NULL COMMENT '产品类型',
  product_name varchar(20) DEFAULT NULL COMMENT '产品名称',
  interest_type varchar(20) DEFAULT NULL COMMENT '收益类型',
  term_type varchar(20) DEFAULT NULL COMMENT '时间类型（短期/中长期）',
  time_limit int(11) DEFAULT NULL COMMENT '期限',
  time_unit varchar(10) DEFAULT NULL COMMENT '期限单位',
  issue_institution varchar(128) DEFAULT NULL COMMENT '发行机构',
  interest_rate decimal(12, 4) DEFAULT NULL COMMENT '年化收益率',
  interest_settlement_way varchar(128) DEFAULT NULL COMMENT '结息方式',
  expect_buy_date datetime DEFAULT NULL COMMENT '预计申购日',
  expect_expire_date datetime DEFAULT NULL COMMENT '预计到期日',
  price bigint(20) DEFAULT NULL COMMENT '票面单价',
  buy_num bigint(20) DEFAULT NULL COMMENT '拟申购份数',
  risk_level varchar(10) DEFAULT NULL COMMENT '风险等级',
  risk_measure text DEFAULT NULL COMMENT '风险措施',
  attach_name text DEFAULT NULL COMMENT '附件名称多个用,隔开',
  attach_url text DEFAULT NULL COMMENT '附件多个用,隔开',
  create_user_id bigint(20) DEFAULT NULL COMMENT '创建人',
  create_user_account varchar(20) DEFAULT NULL COMMENT '创建人',
  create_user_name varchar(50) DEFAULT NULL COMMENT '创建人',
  dept_id bigint(20) DEFAULT NULL COMMENT '所属部门',
  dept_code varchar(30) DEFAULT NULL COMMENT '部门编号',
  dept_name varchar(50) DEFAULT NULL COMMENT '所属部门名称',
  dept_path varchar(256) DEFAULT NULL COMMENT '部门ID的路径',
  dept_path_name varchar(500) DEFAULT NULL COMMENT '部门路径中文名称',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  INDEX f_project_financial_form_id_i (form_id),
  INDEX f_project_financial_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '表单-理财项目立项';

--
-- Definition for table f_project_financial_redeem_apply
--
DROP TABLE IF EXISTS f_project_financial_redeem_apply;
CREATE TABLE f_project_financial_redeem_apply (
  apply_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) DEFAULT NULL COMMENT '表单ID',
  project_code varchar(30) DEFAULT NULL COMMENT '理财项目编号',
  hold_num bigint(20) DEFAULT NULL COMMENT '持有份额',
  transfering_num bigint(20) DEFAULT NULL COMMENT '转让中份额',
  redeeming_num bigint(20) DEFAULT NULL COMMENT '赎回中份额',
  redeem_price bigint(20) DEFAULT NULL COMMENT '赎回单价',
  redeem_num bigint(20) DEFAULT NULL COMMENT '赎回份额',
  redeem_principal bigint(20) DEFAULT NULL COMMENT '应收本金',
  redeem_interest bigint(20) DEFAULT NULL COMMENT '预估利息',
  redeem_reason text DEFAULT NULL COMMENT '赎回事由',
  attach text DEFAULT NULL COMMENT '附件',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (apply_id),
  INDEX f_project_financial_redeem_apply_form_id_i (form_id),
  INDEX f_project_financial_redeem_apply_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '理财项目赎回申请';

--
-- Definition for table f_project_financial_tansfer_apply
--
DROP TABLE IF EXISTS f_project_financial_tansfer_apply;
CREATE TABLE f_project_financial_tansfer_apply (
  apply_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) DEFAULT NULL COMMENT '表单ID',
  project_code varchar(30) DEFAULT NULL COMMENT '理财项目编号',
  hold_num bigint(20) DEFAULT NULL COMMENT '当前持有份数',
  transfering_num bigint(20) DEFAULT NULL COMMENT '转让中份额',
  redeeming_num bigint(20) DEFAULT NULL COMMENT '赎回中份额',
  transfer_price bigint(20) DEFAULT NULL COMMENT '转让单价',
  transfer_num bigint(20) DEFAULT NULL COMMENT '转让份额',
  transfer_time_start datetime DEFAULT NULL COMMENT '转让日期',
  transfer_time_end datetime DEFAULT NULL COMMENT '转让日期',
  transfer_reason text DEFAULT NULL COMMENT '转让事由',
  attach text DEFAULT NULL COMMENT '附件',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (apply_id),
  INDEX f_project_financial_tansfer_apply_form_id_i (form_id),
  INDEX f_project_financial_tansfer_apply_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '理财项目转让申请';

--
-- Definition for table f_project_guarantee_entrusted
--
DROP TABLE IF EXISTS f_project_guarantee_entrusted;
CREATE TABLE f_project_guarantee_entrusted (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  project_id bigint(20) NOT NULL COMMENT 'f_project.project_id',
  project_channel_id bigint(20) DEFAULT NULL COMMENT '项目渠道ID',
  project_channel_name varchar(128) DEFAULT NULL COMMENT '项目渠道名称',
  project_sub_channel_id bigint(20) DEFAULT NULL COMMENT '二级项目渠道',
  project_sub_channel_name varchar(128) DEFAULT NULL COMMENT '二级项目渠道名称',
  capital_channel_id bigint(20) DEFAULT NULL COMMENT '资金渠道ID',
  capital_channel_name varchar(128) DEFAULT NULL COMMENT '资金渠道名称',
  capital_sub_channel_id bigint(20) DEFAULT NULL COMMENT '二级资金渠道',
  capital_sub_channel_name varchar(128) DEFAULT NULL COMMENT '二级资金渠道名称',
  loan_purpose varchar(512) DEFAULT NULL COMMENT '授信用途',
  repay_source varchar(512) DEFAULT NULL COMMENT '还款资金来源',
  repay_plan text DEFAULT NULL COMMENT '还款计划',
  has_pledge varchar(10) DEFAULT NULL COMMENT '是否有抵押物',
  has_evaluate_company varchar(10) DEFAULT NULL COMMENT '是否需要指派评估公司',
  evaluate_company_id bigint(20) DEFAULT NULL COMMENT '评估公司ID',
  evaluate_company_name varchar(50) DEFAULT NULL COMMENT '评估公司名称',
  evaluate_company_region varchar(50) DEFAULT NULL COMMENT '评估公司属于市内OR市外',
  risk_manager_id bigint(20) DEFAULT NULL COMMENT '风险经理ID',
  risk_manager_account varchar(20) DEFAULT NULL COMMENT '风险经理账号',
  risk_manager_name varchar(50) DEFAULT NULL COMMENT '风险经理名称',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  INDEX f_project_guarantee_entrusted_project_id_i (project_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '表单-立项-项目信息（担保/委贷项目详情）';

--
-- Definition for table f_project_lg_litigation
--
DROP TABLE IF EXISTS f_project_lg_litigation;
CREATE TABLE f_project_lg_litigation (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  project_id bigint(20) NOT NULL COMMENT 'f_project.project_id',
  guarantee_fee_rate decimal(12, 2) DEFAULT NULL COMMENT '担保费率',
  co_institution_id bigint(20) DEFAULT NULL COMMENT '合作机构ID',
  co_institution_name varchar(128) DEFAULT NULL COMMENT '合作机构名称',
  co_institution_charge decimal(14, 4) DEFAULT NULL COMMENT '合作机构服务费 元/%',
  co_institution_charge_type varchar(10) DEFAULT NULL COMMENT '合作机构服务费类型 元/%',
  court varchar(128) DEFAULT NULL COMMENT '法院',
  deposit decimal(20, 4) DEFAULT NULL COMMENT '保证金 元/%',
  deposit_type varchar(10) DEFAULT NULL COMMENT '保证金类型 元/%',
  assure_object text DEFAULT NULL COMMENT '本次申请保全标的',
  legal_manager_id bigint(20) DEFAULT NULL COMMENT '法务经理ID',
  legal_manager_account varchar(30) DEFAULT NULL COMMENT '法务经理账号',
  legal_manager_name varchar(50) DEFAULT NULL COMMENT '法务经理名称',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  INDEX f_project_lg_litigation_project_id_i (project_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '表单-立项-项目信息（诉讼保函业务详情 lg : letter of guarantee）';

--
-- Definition for table f_project_underwriting
--
DROP TABLE IF EXISTS f_project_underwriting;
CREATE TABLE f_project_underwriting (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  project_id bigint(20) NOT NULL COMMENT 'f_project.project_id',
  co_institution_id bigint(20) DEFAULT NULL COMMENT '合作机构ID',
  co_institution_name varchar(128) DEFAULT NULL COMMENT '合作机构名称',
  letter_institution_id bigint(20) DEFAULT NULL COMMENT '担保函出具机构ID',
  letter_institution_name varchar(128) DEFAULT NULL COMMENT '担保函出具机构名称',
  repay_source varchar(512) DEFAULT NULL COMMENT '还款资金来源',
  has_financial_support varchar(10) DEFAULT NULL COMMENT '是否财政支持',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  INDEX f_project_underwriting_project_id_i (project_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '表单-立项-项目信息（承销项目详情）';

--
-- Definition for table f_re_council_apply
--
DROP TABLE IF EXISTS f_re_council_apply;
CREATE TABLE f_re_council_apply (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) NOT NULL COMMENT '对应表单ID',
  project_code varchar(50) DEFAULT NULL COMMENT '项目编号',
  old_sp_id bigint(20) DEFAULT NULL COMMENT '原风控会会议纪要ID',
  old_sp_code varchar(30) DEFAULT NULL COMMENT '元风控会议纪要编号',
  content_reason text DEFAULT NULL COMMENT '复议内容及理由',
  overview text DEFAULT NULL COMMENT '综合意见',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  INDEX f_re_council_apply_form_id_i (form_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目复议申请表';

--
-- Definition for table f_refund_application
--
DROP TABLE IF EXISTS f_refund_application;
CREATE TABLE f_refund_application (
  refund_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) DEFAULT NULL COMMENT '表单ID',
  project_code varchar(30) DEFAULT NULL COMMENT '项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '项目名称',
  attach text DEFAULT NULL COMMENT '附件',
  remark text DEFAULT NULL COMMENT '备注',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (refund_id),
  INDEX f_refund_application_form_id_i (form_id),
  INDEX f_refund_application_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '退费申请单';

--
-- Definition for table f_refund_application_fee
--
DROP TABLE IF EXISTS f_refund_application_fee;
CREATE TABLE f_refund_application_fee (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  refund_id bigint(20) NOT NULL COMMENT '申请单ID',
  refund_reason varchar(50) DEFAULT NULL COMMENT '退费事由',
  refund_amount bigint(20) DEFAULT NULL COMMENT '申请退费金额',
  remark text DEFAULT NULL COMMENT '退费说明',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  INDEX f_refund_application_fee_refund_id_i (refund_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '退费申请单-退费信息';

--
-- Definition for table f_risk_level
--
DROP TABLE IF EXISTS f_risk_level;
CREATE TABLE f_risk_level (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) NOT NULL COMMENT '对应的表单ID',
  project_code varchar(50) DEFAULT NULL COMMENT '项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  busi_type varchar(50) DEFAULT NULL COMMENT '业务类型',
  busi_type_name varchar(50) DEFAULT NULL COMMENT '业务类型名称',
  credit_amount bigint(20) DEFAULT NULL COMMENT '授信金额',
  evaluation decimal(12, 4) DEFAULT NULL COMMENT '初评得分',
  evaluation_level varchar(50) DEFAULT NULL COMMENT '初评等级',
  reevaluation_id bigint(20) DEFAULT NULL COMMENT '复评人ID',
  reevaluation_account varchar(50) DEFAULT NULL COMMENT '复评人账号',
  reevaluation_name varchar(50) DEFAULT NULL COMMENT '复评人名字',
  reevaluation decimal(12, 4) DEFAULT NULL COMMENT '复评得分',
  reevaluation_level varchar(50) DEFAULT NULL COMMENT '复评等级',
  enterprise_type varchar(50) DEFAULT NULL COMMENT '企业类型',
  project_type varchar(50) DEFAULT NULL COMMENT '项目类型',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  INDEX f_risk_level_peoject_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目风险评估-初评-复评';

--
-- Definition for table f_risk_level_detail
--
DROP TABLE IF EXISTS f_risk_level_detail;
CREATE TABLE f_risk_level_detail (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  risk_level_id bigint(20) NOT NULL COMMENT 'f_risk_level.id',
  score_template_id bigint(20) NOT NULL COMMENT 'f_risk_level_score_template.id',
  evaluation decimal(12, 4) DEFAULT NULL COMMENT '初评得分',
  reevaluation decimal(12, 4) DEFAULT NULL COMMENT '复评得分',
  evaluation_reason varchar(128) DEFAULT NULL COMMENT '分配完成率M/N变动30%以上具体说明原因',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  UNIQUE INDEX risk_level_id_score_template_id_u (risk_level_id, score_template_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目风险评估-初评-复评-评分情况';

--
-- Definition for table f_risk_level_score_template
--
DROP TABLE IF EXISTS f_risk_level_score_template;
CREATE TABLE f_risk_level_score_template (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  evaluation_type varchar(20) DEFAULT NULL COMMENT '评分分类(企业/项目/反担保)',
  index_no varchar(20) DEFAULT NULL COMMENT '序号',
  index1 varchar(128) DEFAULT NULL COMMENT '评分项目/指标名称/指标1',
  score1 decimal(12, 4) DEFAULT NULL COMMENT '指标1得分',
  index2 varchar(128) DEFAULT NULL COMMENT '评分项目/指标名称/指标2',
  score2 decimal(12, 4) DEFAULT NULL COMMENT '指标2得分',
  sort_order int(11) DEFAULT 0 COMMENT '排序号',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  INDEX f_risk_level_score_template_evaluation_type_i (evaluation_type)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目风险评估-初评-复评-评分指标模板';

--
-- Definition for table f_risk_review_report
--
DROP TABLE IF EXISTS f_risk_review_report;
CREATE TABLE f_risk_review_report (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) NOT NULL COMMENT '对应的表单ID',
  project_code varchar(50) DEFAULT NULL COMMENT '对应项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '对应项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  report_content text DEFAULT NULL COMMENT '风险审查报告内容',
  case_info text DEFAULT NULL COMMENT '案情介绍',
  preserve_content text DEFAULT NULL COMMENT '拟保全标的或内容',
  audit_opinion text DEFAULT NULL COMMENT '风险审查意见',
  synthesize_opinion text DEFAULT NULL COMMENT '项目综合意见',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  INDEX f_irisk_review_report_form_id_i (form_id),
  INDEX f_risk_review_report_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '授信前 - 风险审查报告';

--
-- Definition for table f_risk_warning
--
DROP TABLE IF EXISTS f_risk_warning;
CREATE TABLE f_risk_warning (
  warning_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) NOT NULL COMMENT '表单ID',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  signal_level varchar(32) DEFAULT NULL COMMENT '信号等级: 特别预警(special)，一般预警(nomal)',
  special_signal varchar(256) DEFAULT NULL COMMENT '特别预警(special)ID加逗号分隔 risk_warning_signal.id',
  special_signal_desc text DEFAULT NULL COMMENT '特别预警(special)详细信息',
  nomal_signal varchar(256) DEFAULT NULL COMMENT '一般预警(nomal)ID加逗号分隔 risk_warning_signal.id',
  nomal_signal_desc text DEFAULT NULL COMMENT '一般预警(nomal)详细信息',
  risk_signal_detail text DEFAULT NULL COMMENT '风险预警信号详细描述',
  risk_measure text DEFAULT NULL COMMENT '风险防范化解措施',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (warning_id),
  INDEX f_risk_warning_form_id_i (form_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '风险预警处理表';

--
-- Definition for table f_risk_warning_credit
--
DROP TABLE IF EXISTS f_risk_warning_credit;
CREATE TABLE f_risk_warning_credit (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  warning_id bigint(20) NOT NULL COMMENT 'f_risk_warning.warning_id',
  issue_date datetime DEFAULT NULL COMMENT '发放日期',
  expire_date datetime DEFAULT NULL COMMENT '到期日期',
  loan_amount bigint(20) NOT NULL COMMENT '借款/担保余额',
  debit_interest bigint(20) NOT NULL COMMENT '欠息/费金额',
  sort_order int(11) NOT NULL DEFAULT 0,
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  INDEX f_risk_warning_credit_warning_id_i (warning_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '风险预警处理表 -- 授信业务基本情况';

--
-- Definition for table f_send_audit_apply
--
DROP TABLE IF EXISTS f_send_audit_apply;
CREATE TABLE f_send_audit_apply (
  apply_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '送审申请表',
  form_id bigint(20) NOT NULL COMMENT '对应表单ID',
  project_code varchar(50) DEFAULT NULL COMMENT '对应项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '对应项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  is_investigation_rule varchar(10) DEFAULT NULL COMMENT '尽职调查报告是否按《尽职调查操作方法》规定执行',
  is_data_correct varchar(10) DEFAULT NULL COMMENT '提供的核心资料是否齐全，资料和数据勾稽关系一致',
  is_legal varchar(10) DEFAULT NULL COMMENT '是否符合法律法规规定',
  is_company_rule varchar(10) DEFAULT NULL COMMENT '是否符合公司制度准入规定',
  is_purpose_real varchar(10) DEFAULT NULL COMMENT '用途是否真实',
  is_amount_correct varchar(20) DEFAULT NULL COMMENT '金额是否匹配',
  is_timelimit_repayway_rational text DEFAULT NULL COMMENT '期限及还款方式是否合理',
  other text DEFAULT NULL COMMENT '其他',
  operation_state_review text DEFAULT NULL COMMENT '经营状况分析',
  financial_kpi_review text DEFAULT NULL COMMENT '财务指标分析',
  repay_source_review text DEFAULT NULL COMMENT '还款来源审查',
  counter_guarantee_review text DEFAULT NULL COMMENT '（反）担保措施审查',
  risk_precaution text DEFAULT NULL COMMENT '授信风险分析及防范建议',
  conclusion_suggestion text DEFAULT NULL COMMENT '审查结论及建议',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (apply_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '（担保）项目送审申请表';

--
-- Definition for table f_stamp_apply
--
DROP TABLE IF EXISTS f_stamp_apply;
CREATE TABLE f_stamp_apply (
  apply_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  apply_code varchar(20) DEFAULT NULL COMMENT '申请单编号',
  form_id bigint(20) NOT NULL COMMENT '表单ID',
  file_code varchar(20) DEFAULT NULL COMMENT '档案编号',
  project_code varchar(50) DEFAULT NULL COMMENT '对应项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '对应项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  status varchar(10) DEFAULT NULL,
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  replace_form_id bigint(20) DEFAULT NULL COMMENT '替换申请formid',
  PRIMARY KEY (apply_id),
  INDEX f_stamp_apply_form_id_i (form_id),
  INDEX f_stamp_apply_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '用印申请';

--
-- Definition for table f_stamp_apply_file
--
DROP TABLE IF EXISTS f_stamp_apply_file;
CREATE TABLE f_stamp_apply_file (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  apply_id bigint(20) NOT NULL COMMENT '对应申请ID',
  contract_code varchar(255) DEFAULT NULL COMMENT '合同编号',
  replace_apply_id bigint(20) DEFAULT NULL COMMENT '替换申请id',
  file_name varchar(128) DEFAULT NULL COMMENT '文件名称',
  file_conent varchar(512) DEFAULT NULL COMMENT '文件内容简述',
  legal_chapter_num int(11) DEFAULT 0 COMMENT '法人章份数',
  cachet_num int(11) DEFAULT 0 COMMENT '公章份数',
  is_replace_old varchar(10) DEFAULT NULL COMMENT '是否要替换旧文件',
  old_file_num int(11) DEFAULT 0 COMMENT '返回旧文件份数',
  sort_order int(11) NOT NULL DEFAULT 0,
  remark varchar(512) DEFAULT NULL,
  old_file_content varchar(512) DEFAULT NULL COMMENT '替换前文件内容',
  old_legal_chapter_num int(11) DEFAULT 0 COMMENT '申请替换前法人章份数',
  old_cachet_num int(11) DEFAULT 0 COMMENT '申请替换前公章分数',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '用印申请 - 文件列表';

--
-- Definition for table financial_product
--
DROP TABLE IF EXISTS financial_product;
CREATE TABLE financial_product (
  product_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  product_type varchar(20) DEFAULT NULL COMMENT '产品类型',
  product_name varchar(20) DEFAULT NULL COMMENT '产品名称',
  interest_type varchar(20) DEFAULT NULL COMMENT '收益类型',
  term_type varchar(20) DEFAULT NULL COMMENT '期限类型（短期/中长期）',
  time_limit int(11) DEFAULT NULL COMMENT '产品期限',
  time_unit varchar(10) DEFAULT NULL COMMENT '产品期限单位',
  issue_institution varchar(128) DEFAULT NULL COMMENT '发行机构',
  interest_rate decimal(12, 4) DEFAULT NULL COMMENT '年化收益率%',
  interest_settlement_way varchar(128) DEFAULT NULL COMMENT '结息方式',
  price bigint(20) DEFAULT NULL COMMENT '单价',
  sell_status varchar(10) DEFAULT NULL COMMENT '状态 - 正在销售/停售',
  risk_level varchar(10) DEFAULT NULL COMMENT '风险等级',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (product_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '理财产品信息';

--
-- Definition for table form
--
DROP TABLE IF EXISTS form;
CREATE TABLE form (
  form_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '表单ID',
  form_code varchar(32) DEFAULT NULL COMMENT '表单编码',
  form_name varchar(50) DEFAULT NULL COMMENT '表单名称',
  form_url varchar(255) DEFAULT NULL COMMENT '表单地址',
  act_inst_id bigint(20) DEFAULT NULL COMMENT 'ACT流程实例ID',
  act_def_id varchar(128) DEFAULT NULL COMMENT 'ACT流程定义ID',
  def_id bigint(20) DEFAULT NULL COMMENT '流程定义ID',
  run_id bigint(20) DEFAULT NULL COMMENT '流程runId(流程实例)',
  task_id bigint(20) DEFAULT NULL COMMENT '任务id',
  status varchar(10) DEFAULT NULL COMMENT '状态',
  detail_status varchar(128) DEFAULT NULL COMMENT 'bpm详细状态',
  user_id bigint(20) DEFAULT NULL COMMENT '创建人ID',
  user_account varchar(50) DEFAULT NULL COMMENT '创建人账号',
  user_name varchar(50) DEFAULT NULL COMMENT '创建人名字',
  user_mobile varchar(20) DEFAULT NULL COMMENT '用户电话',
  user_email varchar(50) DEFAULT NULL COMMENT '创建人邮箱',
  dept_id bigint(20) DEFAULT NULL COMMENT '创建部门ID',
  dept_code varchar(30) DEFAULT NULL COMMENT '部门编号',
  dept_name varchar(50) DEFAULT NULL COMMENT '部门名称',
  dept_path varchar(256) DEFAULT NULL COMMENT '部门路径',
  dept_path_name varchar(500) DEFAULT NULL COMMENT '部门路径名称',
  check_status varchar(30) DEFAULT NULL COMMENT '校验状态 1表示通过 0表示不通过  多个表的校验如1111100000011',
  submit_time datetime DEFAULT NULL COMMENT '表单提交时间',
  finish_time datetime DEFAULT NULL COMMENT '表单审核完成时间',
  related_project_code varchar(512) DEFAULT NULL COMMENT '相关项目编号,多条用逗号隔开XXXX,xxxx1',
  task_user_data text DEFAULT NULL COMMENT '当前代办任务人员',
  trace text DEFAULT NULL COMMENT '执行轨迹',
  remark text DEFAULT NULL COMMENT '备注',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (form_id),
  INDEX form_act_def_inst_id_i (act_def_id, act_inst_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '表单';

--
-- Definition for table form_change_apply
--
DROP TABLE IF EXISTS form_change_apply;
CREATE TABLE form_change_apply (
  apply_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) NOT NULL DEFAULT 0 COMMENT '表单ID',
  apply_type varchar(30) DEFAULT NULL COMMENT '签报类型',
  project_code varchar(30) DEFAULT NULL COMMENT '项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '客户ID',
  customer_name varchar(250) DEFAULT NULL COMMENT '客户名称',
  customer_type varchar(10) DEFAULT NULL COMMENT '客户类型',
  busi_type varchar(20) DEFAULT NULL COMMENT '业务品种',
  busi_type_name varchar(128) DEFAULT NULL COMMENT '业务品种名称',
  change_form varchar(50) DEFAULT NULL COMMENT '签报表单',
  change_form_id bigint(20) DEFAULT NULL COMMENT '签报表单ID',
  change_remark text DEFAULT NULL COMMENT '签报事项说明',
  attach text DEFAULT NULL COMMENT '附件',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (apply_id),
  INDEX from_change_apply_form_id_i (form_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '表单签报申请';

--
-- Definition for table form_change_record
--
DROP TABLE IF EXISTS form_change_record;
CREATE TABLE form_change_record (
  record_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  apply_form_id bigint(20) DEFAULT 0 COMMENT '签报申请的表单ID',
  tab_index int(11) DEFAULT 0 COMMENT '表单tab页索引',
  tab_desc varchar(255) DEFAULT NULL COMMENT '页签描述',
  exe_url varchar(512) DEFAULT NULL COMMENT '执行地址',
  form_desc varchar(255) DEFAULT NULL COMMENT '表单描述',
  form_data text DEFAULT NULL COMMENT '表单数据（序列化后的表单数据）',
  original_form_data text DEFAULT NULL COMMENT '原始表单数据（序列化后的表单数据）',
  page_content text DEFAULT NULL COMMENT '页面内容',
  original_page_content text DEFAULT NULL COMMENT '原页面内容',
  user_id bigint(20) DEFAULT NULL COMMENT '修改人ID',
  user_account varchar(50) DEFAULT NULL COMMENT '修改人账号',
  user_name varchar(50) DEFAULT NULL COMMENT '修改人名称',
  user_ip varchar(50) DEFAULT NULL COMMENT '修改人用户IP',
  dept_id bigint(20) DEFAULT NULL COMMENT '修改人部门ID',
  dept_name varchar(128) DEFAULT NULL COMMENT '修改人部门名称',
  session_id varchar(50) DEFAULT NULL COMMENT '修改当时的sessionId(同一次会话只记录一次)',
  access_token varchar(50) DEFAULT NULL COMMENT '访问密钥',
  status varchar(20) DEFAULT NULL COMMENT '执行状态',
  exe_result text DEFAULT NULL,
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (record_id),
  INDEX from_change_record_change_apply_form_id_i (apply_form_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '表单修改记录';

--
-- Definition for table form_change_record_detail
--
DROP TABLE IF EXISTS form_change_record_detail;
CREATE TABLE form_change_record_detail (
  detail_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  record_id bigint(20) NOT NULL COMMENT '修改记录ID',
  label varchar(128) DEFAULT NULL COMMENT '标签',
  name varchar(128) DEFAULT NULL COMMENT '属性名',
  old_text text DEFAULT NULL COMMENT '旧值描述',
  old_value text DEFAULT NULL COMMENT '原值',
  new_value text DEFAULT NULL COMMENT '新值',
  new_text text DEFAULT NULL COMMENT '新值描述',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (detail_id),
  INDEX from_change_record_detail_record_id_i (record_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '表单修改记录明细';

--
-- Definition for table form_message_templete
--
DROP TABLE IF EXISTS form_message_templete;
CREATE TABLE form_message_templete (
  templete_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '模板ID',
  form_code varchar(128) DEFAULT NULL COMMENT '对应表单',
  type varchar(30) DEFAULT NULL COMMENT '模板类型（步骤通知 ，结果通知）',
  subject varchar(255) DEFAULT NULL COMMENT '消息主题（主要用于邮件）',
  content_html text DEFAULT NULL COMMENT 'html消息内容',
  content_txt text DEFAULT NULL COMMENT '文字消息内容',
  is_send_site_message varchar(10) DEFAULT 'IS' COMMENT '是否站内信通知',
  is_send_mail varchar(10) DEFAULT 'IS' COMMENT '是否发送邮件',
  is_send_sms varchar(10) DEFAULT 'NO' COMMENT '是否发送短信',
  base_on varchar(10) DEFAULT 'PM' COMMENT '发送信息基于bpm配置或者当前PM配置',
  remark text DEFAULT NULL COMMENT '备注',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (templete_id),
  UNIQUE INDEX UK_form_message_templete_form_code (form_code, type)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '流程处理消息模板';

--
-- Definition for table from_change_record
--
DROP TABLE IF EXISTS from_change_record;
CREATE TABLE from_change_record (
  record_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  change_form_id bigint(20) NOT NULL DEFAULT 0 COMMENT '修改的表单ID',
  change_apply_form_id bigint(20) DEFAULT 0 COMMENT '签报申请的表单ID',
  tab_index int(11) DEFAULT NULL COMMENT '表单tab页索引',
  req_url varchar(512) DEFAULT NULL COMMENT '表单请求地址',
  form_data text DEFAULT NULL COMMENT '表单数据（序列化后的表单数据）',
  original_form_data text DEFAULT NULL COMMENT '原始表单数据（序列化后的表单数据）',
  user_id bigint(20) DEFAULT NULL COMMENT '修改人ID',
  user_account varchar(50) DEFAULT NULL COMMENT '修改人账号',
  user_name varchar(50) DEFAULT NULL COMMENT '修改人名称',
  user_ip varchar(50) DEFAULT NULL COMMENT '修改人用户IP',
  dept_id bigint(20) DEFAULT NULL COMMENT '修改人部门ID',
  dept_name varchar(128) DEFAULT NULL COMMENT '修改人部门名称',
  session_id varchar(50) DEFAULT NULL COMMENT '修改当时的sessionId(同一次会话只记录一次)',
  access_token varchar(50) DEFAULT NULL COMMENT '访问密钥',
  status varchar(20) DEFAULT NULL COMMENT '执行状态',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (record_id),
  INDEX from_change_record_change_apply_form_id_i (change_apply_form_id),
  INDEX from_change_record_change_form_id_i (change_form_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '表单修改记录';

--
-- Definition for table from_change_record_detail
--
DROP TABLE IF EXISTS from_change_record_detail;
CREATE TABLE from_change_record_detail (
  detail_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  record_id bigint(20) NOT NULL COMMENT '修改记录ID',
  unique_key varchar(20) DEFAULT NULL COMMENT '唯一标识',
  change_type varchar(20) DEFAULT NULL COMMENT '类型(新增/删除/修改)',
  property varchar(128) DEFAULT NULL COMMENT '属性名称',
  property_desc varchar(128) DEFAULT NULL COMMENT '属性描述',
  property_value text DEFAULT NULL COMMENT '修改后的值',
  orignal_value text DEFAULT NULL COMMENT '原始值',
  property_html text DEFAULT NULL COMMENT '修改后的html（主要用于新增和删除）',
  orignal_html text DEFAULT NULL COMMENT '原始html值',
  container varchar(20) DEFAULT NULL COMMENT '页面容器（jquery的selector 主要用于新增和修改数据的容器位置）',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (detail_id),
  INDEX from_change_record_detail_record_id_i (record_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '表单修改记录明细';

--
-- Definition for table industry
--
DROP TABLE IF EXISTS industry;
CREATE TABLE industry (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  type varchar(10) DEFAULT NULL COMMENT '门类',
  type_big varchar(10) DEFAULT NULL COMMENT '大类',
  type_medium varchar(10) DEFAULT NULL COMMENT '中类',
  type_small varchar(10) DEFAULT NULL COMMENT '小类',
  level smallint(6) DEFAULT NULL COMMENT '层级',
  parent_code varchar(40) DEFAULT NULL COMMENT '上级code',
  code varchar(40) DEFAULT NULL COMMENT '编码=门类-大类-中类-小类',
  name varchar(128) DEFAULT NULL COMMENT '名称',
  remark varchar(250) DEFAULT NULL COMMENT '说明',
  review_template varchar(255) DEFAULT NULL COMMENT '保后检查报告模板',
  sort_order int(11) DEFAULT 0 COMMENT '排序号',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '国民经济行业分类';

--
-- Definition for table message_info
--
DROP TABLE IF EXISTS message_info;
CREATE TABLE message_info (
  message_id bigint(18) NOT NULL AUTO_INCREMENT,
  message_title varchar(128) DEFAULT NULL COMMENT '消息标题',
  message_type varchar(32) DEFAULT NULL COMMENT '消息类型',
  message_status varchar(32) DEFAULT NULL COMMENT '消息状态',
  message_subject varchar(128) DEFAULT NULL COMMENT '消息主题',
  message_content longtext DEFAULT NULL COMMENT '消息内容',
  notification_object varchar(2000) DEFAULT NULL COMMENT '通知对象',
  link_url varchar(200) DEFAULT NULL COMMENT '消息链接',
  view_type varchar(32) DEFAULT NULL COMMENT '显示方式',
  notification_type varchar(32) DEFAULT NULL COMMENT '通知对象类型',
  message_send_date timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '推送有效期',
  message_plan_send_date timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '开始推送时间',
  message_sender_id bigint(18) DEFAULT NULL COMMENT '消息发送人id',
  message_sender_name varchar(255) DEFAULT NULL COMMENT '发送人名称',
  message_sender_account varchar(50) DEFAULT NULL COMMENT '消息发送人账号',
  raw_add_time timestamp NULL DEFAULT '0000-00-00 00:00:00',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (message_id),
  INDEX message_sender_id_index (message_sender_id),
  INDEX message_status_index (message_status),
  INDEX message_type_index (message_type)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Definition for table message_received
--
DROP TABLE IF EXISTS message_received;
CREATE TABLE message_received (
  received_id bigint(18) NOT NULL AUTO_INCREMENT,
  message_id bigint(18) DEFAULT NULL,
  message_title varchar(128) DEFAULT NULL COMMENT '消息标题',
  message_type varchar(32) DEFAULT NULL COMMENT '消息类型',
  message_subject varchar(128) DEFAULT NULL COMMENT '消息主题',
  message_content longtext DEFAULT NULL COMMENT '消息内容',
  notification_object varchar(2000) DEFAULT NULL COMMENT '通知对象',
  view_type varchar(32) DEFAULT NULL COMMENT '显示方式',
  link_url varchar(200) DEFAULT NULL COMMENT '消息链接',
  message_sender_id bigint(18) DEFAULT NULL COMMENT '消息发送人id',
  message_sender_name varchar(50) DEFAULT NULL COMMENT '消息发送人名',
  message_received_id bigint(18) DEFAULT NULL COMMENT '接收人id',
  message_received_date datetime DEFAULT NULL,
  message_received_name varchar(255) DEFAULT NULL COMMENT '接收人名称',
  message_received_account varchar(50) DEFAULT NULL COMMENT '接收人账号',
  message_received_status varchar(32) DEFAULT NULL COMMENT '接收状态',
  raw_add_time timestamp NULL DEFAULT '0000-00-00 00:00:00',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (received_id),
  INDEX message_received_status_index (message_received_status),
  INDEX message_sender_id_index (message_sender_id),
  INDEX received_message_id_index (message_id),
  INDEX received_message_type_index (message_type)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Definition for table operation_journal
--
DROP TABLE IF EXISTS operation_journal;
CREATE TABLE operation_journal (
  identity bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增字段',
  base_module_name varchar(30) NOT NULL COMMENT '父权限组织名称',
  permission_name varchar(100) NOT NULL COMMENT '子模块名称(对应操作员进行操作的模块)',
  operation_content varchar(30) NOT NULL COMMENT '操作内容',
  memo varchar(2000) DEFAULT NULL COMMENT '操作详情',
  operator_id bigint(20) NOT NULL COMMENT '操作员id',
  operator_name varchar(30) NOT NULL COMMENT '操作员名称',
  operator_ip varchar(30) NOT NULL COMMENT '客户端ip地址 request.getRemoteAddr',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '创建时间(操作时间)',
  raw_update_time timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (identity),
  INDEX base_module_name_index (base_module_name),
  INDEX operation_content_index (operation_content),
  INDEX operator_id_index (operator_id),
  INDEX operator_ip_index (operator_ip),
  INDEX operator_name_index (operator_name),
  INDEX permission_name_index (permission_name),
  INDEX raw_add_time_index (raw_add_time)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '操作日志表';





--
-- Definition for table project
--
DROP TABLE IF EXISTS project;
CREATE TABLE project (
  project_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  project_code varchar(50) DEFAULT NULL COMMENT '项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  customer_type varchar(10) DEFAULT NULL COMMENT '客户类型',
  busi_type varchar(50) DEFAULT NULL COMMENT '业务类型',
  busi_type_name varchar(120) DEFAULT NULL COMMENT '业务类型名称',
  busi_sub_type varchar(50) DEFAULT NULL COMMENT '业务类型',
  busi_sub_type_name varchar(120) DEFAULT NULL COMMENT '业务类型名称',
  industry_code varchar(128) DEFAULT NULL COMMENT '所属行业',
  industry_name varchar(128) DEFAULT NULL COMMENT '行业名称',
  loan_purpose varchar(512) DEFAULT NULL COMMENT '授信用途',
  time_limit int(11) DEFAULT NULL COMMENT '期限',
  time_unit varchar(10) DEFAULT NULL COMMENT '期限单位',
  start_time datetime DEFAULT NULL COMMENT '开始时间',
  end_time datetime DEFAULT NULL COMMENT '结束时间',
  amount bigint(20) DEFAULT NULL COMMENT '金额',
  accumulated_issue_amount bigint(20) DEFAULT NULL COMMENT '累计已发行金额(承销/发债信息维护)',
  loaned_amount bigint(20) DEFAULT NULL COMMENT '已放款金额',
  used_amount bigint(20) DEFAULT NULL COMMENT '已用款金额',
  repayed_amount bigint(20) DEFAULT NULL COMMENT '已还款金额',
  charged_amount bigint(20) DEFAULT NULL COMMENT '已收费金额',
  refund_amount bigint(20) DEFAULT NULL COMMENT '已退费金额',
  is_maximum_amount varchar(10) DEFAULT 'NO' COMMENT '是否最高额授信',
  released_amount bigint(20) DEFAULT NULL COMMENT '已解保金额',
  releasable_amount bigint(20) DEFAULT NULL COMMENT '总的可解保金额',
  releasing_amount bigint(20) DEFAULT NULL COMMENT '解保中的金额',
  customer_deposit_amount bigint(20) DEFAULT NULL COMMENT '客户保证金',
  self_deposit_amount bigint(20) DEFAULT NULL COMMENT '自家保证金',
  comp_principal_amount bigint(20) DEFAULT NULL COMMENT '已代偿本金',
  comp_interest_amount bigint(20) DEFAULT NULL COMMENT '已代偿利息',
  contract_no varchar(20) DEFAULT NULL COMMENT '合同编号',
  contract_time datetime DEFAULT NULL COMMENT '合同签订时间',
  sp_id bigint(20) DEFAULT 0 COMMENT '会议纪要ID',
  sp_code varchar(20) DEFAULT NULL COMMENT '会议纪要编号（项目批复编号）',
  is_approval varchar(10) DEFAULT 'NO' COMMENT '项目是否已经批复',
  is_approval_del varchar(10) DEFAULT 'NO' COMMENT '项目批复是否作废',
  approval_time datetime DEFAULT NULL COMMENT '会议纪要通过时间',
  busi_manager_id bigint(20) DEFAULT NULL COMMENT '客户经理ID',
  busi_manager_account varchar(20) DEFAULT NULL COMMENT '客户经理账号',
  busi_manager_name varchar(50) DEFAULT NULL COMMENT '客户经理名称',
  busi_managerb_id bigint(20) DEFAULT NULL COMMENT '客户经理B',
  busi_managerb_account varchar(20) DEFAULT NULL COMMENT '客户经理B账号',
  busi_managerb_name varchar(50) DEFAULT NULL COMMENT '客户经理B名称',
  dept_id bigint(20) DEFAULT NULL COMMENT '所属部门ID',
  dept_code varchar(30) DEFAULT NULL COMMENT '部门编号',
  dept_name varchar(50) DEFAULT NULL COMMENT '所属部门名称',
  dept_path varchar(256) DEFAULT NULL COMMENT '部门路径',
  dept_path_name varchar(500) DEFAULT NULL COMMENT '部门路径名称',
  phases varchar(20) DEFAULT NULL COMMENT '项目阶段',
  phases_status varchar(10) DEFAULT NULL COMMENT '项目阶段状态',
  status varchar(20) DEFAULT 'NORMAL' COMMENT '项目状态(正常、暂缓、未成立、完成)',
  is_continue varchar(20) DEFAULT 'NO' COMMENT '是否手动停止继续发售(承销/发债发售信息维护)',
  is_recouncil varchar(10) DEFAULT 'NO' COMMENT '是否可复议',
  last_recouncil_time datetime DEFAULT NULL COMMENT '上次复议时间',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (project_id),
  INDEX project_peoject_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '真实项目信息';

--
-- Definition for table project_credit_condition
--
DROP TABLE IF EXISTS project_credit_condition;
CREATE TABLE project_credit_condition (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  project_code varchar(50) NOT NULL COMMENT '项目编号',
  item_id bigint(20) NOT NULL COMMENT 'f_council_summary_project_pledge.id,f_council_summary_project_guarantor.id',
  item_desc varchar(512) DEFAULT NULL COMMENT '授信条件文字描述（根据对应抵（质）押、保证生成）',
  type varchar(20) DEFAULT NULL COMMENT '类型：抵押、质押、保证',
  is_confirm varchar(10) DEFAULT NULL COMMENT '是否落实',
  confirm_man_id text DEFAULT NULL COMMENT '落实人ID',
  confirm_man_account text DEFAULT NULL COMMENT '落实人账号',
  confirm_man_name text DEFAULT NULL COMMENT '落实人名称',
  confirm_date datetime DEFAULT NULL COMMENT '落实日期',
  contract_no text DEFAULT NULL COMMENT '合同编号',
  contract_agreement_url text DEFAULT NULL COMMENT '合同/协议url',
  right_vouche text DEFAULT NULL COMMENT '权利凭证(有多个用,分开)',
  remark text DEFAULT NULL COMMENT '备注',
  status varchar(10) DEFAULT NULL COMMENT '状态',
  release_status varchar(10) DEFAULT 'WAITING' COMMENT '解保申请状态(默认待解保)',
  release_id bigint(20) DEFAULT 0 COMMENT '解保申请的id:f_counter_guarantee_release.id',
  release_reason varchar(512) DEFAULT NULL COMMENT '解除的原因',
  release_gist varchar(512) DEFAULT NULL COMMENT '解除的依据',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  INDEX project_credit_condition_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目授信条件';

--
-- Definition for table project_financial
--
DROP TABLE IF EXISTS project_financial;
CREATE TABLE project_financial (
  project_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  project_code varchar(30) DEFAULT NULL COMMENT '项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '项目名称',
  product_id bigint(20) DEFAULT NULL COMMENT '产品ID',
  product_type varchar(20) DEFAULT NULL COMMENT '产品类型',
  product_name varchar(20) DEFAULT NULL COMMENT '产品名称',
  term_type varchar(20) DEFAULT NULL COMMENT '时间类型（短期/中长期）',
  interest_type varchar(20) DEFAULT NULL COMMENT '收益类型',
  time_limit int(11) DEFAULT NULL COMMENT '期限',
  time_unit varchar(10) DEFAULT NULL COMMENT '期限单位',
  issue_institution varchar(128) DEFAULT NULL COMMENT '发行机构',
  interest_rate decimal(12, 4) DEFAULT NULL COMMENT '年化收益率',
  interest_settlement_way varchar(128) DEFAULT NULL COMMENT '结息方式',
  expect_buy_date datetime DEFAULT NULL COMMENT '预计申购日',
  expect_expire_date datetime DEFAULT NULL COMMENT '预计到期日',
  price bigint(20) DEFAULT NULL COMMENT '票面单价',
  buy_num bigint(20) DEFAULT NULL COMMENT '拟申购份数',
  risk_level varchar(10) DEFAULT NULL COMMENT '风险等级',
  risk_measure text DEFAULT NULL COMMENT '风险措施',
  attach_name varchar(50) DEFAULT NULL COMMENT '附件名称',
  attach_url text DEFAULT NULL COMMENT '附件',
  create_user_id bigint(20) DEFAULT NULL COMMENT '创建人',
  create_user_account varchar(20) DEFAULT NULL COMMENT '创建人',
  create_user_name varchar(50) DEFAULT NULL COMMENT '创建人',
  dept_id bigint(20) DEFAULT NULL COMMENT '所属部门',
  dept_code varchar(30) DEFAULT NULL COMMENT '部门编号',
  dept_name varchar(50) DEFAULT NULL COMMENT '所属部门名称',
  dept_path varchar(256) DEFAULT NULL COMMENT '部门ID的路径',
  dept_path_name varchar(500) DEFAULT NULL COMMENT '部门路径中文名称',
  actual_buy_date datetime DEFAULT NULL COMMENT '实际申购日',
  actual_expire_date datetime DEFAULT NULL COMMENT '产品到期日',
  actual_price bigint(20) DEFAULT NULL COMMENT '实际单价',
  actual_buy_num bigint(20) DEFAULT NULL COMMENT '实际申购份数',
  original_hold_num bigint(20) DEFAULT NULL COMMENT '原持有份额（不含转让部分）',
  actual_hold_num bigint(20) DEFAULT NULL COMMENT '实际持有份数(最新)',
  actual_principal bigint(20) DEFAULT NULL COMMENT '实收本金',
  actual_interest bigint(20) DEFAULT NULL COMMENT '实收利息',
  actual_interest_rate decimal(12, 4) DEFAULT NULL COMMENT '实际年化收益率(计算得出)',
  transfered_num bigint(20) DEFAULT 0 COMMENT '已转让份额',
  buy_back_num bigint(20) DEFAULT 0 COMMENT '已回购份额',
  redeemed_num bigint(20) DEFAULT 0 COMMENT '已赎回份额',
  status varchar(30) DEFAULT NULL COMMENT '状态',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (project_id),
  INDEX f_project_financial_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '理财项目信息';

--
-- Definition for table project_financial_trade_redeem
--
DROP TABLE IF EXISTS project_financial_trade_redeem;
CREATE TABLE project_financial_trade_redeem (
  trade_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  flow_no varchar(30) DEFAULT NULL COMMENT '流水号',
  project_code varchar(30) DEFAULT NULL COMMENT '项目编号',
  apply_id bigint(20) DEFAULT NULL COMMENT '赎回申请单ID',
  redeem_price bigint(20) DEFAULT NULL COMMENT '赎回单价',
  redeem_num bigint(20) DEFAULT NULL COMMENT '赎回份额',
  redeem_principal bigint(20) DEFAULT NULL COMMENT '实收本金',
  redeem_interest bigint(20) DEFAULT NULL COMMENT '实收利息',
  redeem_interest_rate decimal(12, 4) DEFAULT NULL COMMENT '实际收益率',
  redeem_time datetime DEFAULT NULL COMMENT '赎回日期',
  redeem_reason text DEFAULT NULL COMMENT '赎回事由',
  attach text DEFAULT NULL COMMENT '附件',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (trade_id),
  INDEX project_financial_redeem_trade_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '理财项目赎回交易信息';

--
-- Definition for table project_financial_trade_tansfer
--
DROP TABLE IF EXISTS project_financial_trade_tansfer;
CREATE TABLE project_financial_trade_tansfer (
  trade_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  flow_no varchar(30) DEFAULT NULL COMMENT '流水号',
  project_code varchar(30) DEFAULT NULL COMMENT '项目编号',
  apply_id bigint(20) DEFAULT NULL COMMENT '转让申请单ID',
  transfer_price bigint(20) DEFAULT NULL COMMENT '转让单价',
  transfer_num bigint(20) DEFAULT NULL COMMENT '转让份额',
  transfer_interest bigint(20) DEFAULT NULL COMMENT '实收利息',
  transfer_time datetime DEFAULT NULL COMMENT '转让日期',
  is_transfer_ownership varchar(10) DEFAULT 'NO' COMMENT '是否过户',
  is_buy_back varchar(10) DEFAULT 'NO' COMMENT '是否回购',
  buy_back_price bigint(20) DEFAULT NULL COMMENT '回购单价',
  buy_back_time datetime DEFAULT NULL COMMENT '回购时间',
  is_confirm varchar(10) DEFAULT NULL COMMENT '是否确认回购',
  confirm_time datetime DEFAULT NULL COMMENT '回购确认时间',
  transfer_reason text DEFAULT NULL COMMENT '转让事由',
  attach text DEFAULT NULL COMMENT '附件',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (trade_id),
  INDEX project_financial_tansfer_trade_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '理财项目转让交易信息';

--
-- Definition for table project_financial_withdrawing
--
DROP TABLE IF EXISTS project_financial_withdrawing;
CREATE TABLE project_financial_withdrawing (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  project_code varchar(30) DEFAULT NULL COMMENT '项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '项目名称',
  product_id bigint(20) DEFAULT NULL COMMENT '产品ID',
  product_name varchar(20) DEFAULT NULL COMMENT '产品名称',
  interest_rate decimal(12, 4) DEFAULT NULL COMMENT '预期收益率',
  buy_date datetime DEFAULT NULL COMMENT '购买时间',
  total_interest bigint(20) DEFAULT NULL COMMENT '累计结息金额',
  withdraw_date varchar(10) DEFAULT NULL COMMENT '计提时间',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  INDEX project_financial_withdrawing_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '理财项目 - 计提台帐';

--
-- Definition for table project_issue_information
--
DROP TABLE IF EXISTS project_issue_information;
CREATE TABLE project_issue_information (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  project_code varchar(50) DEFAULT NULL COMMENT '项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  customer_type varchar(10) DEFAULT NULL COMMENT '客户类型',
  busi_type varchar(50) DEFAULT NULL COMMENT '业务类型',
  busi_type_name varchar(50) DEFAULT NULL COMMENT '业务类型名称',
  time_limit int(11) DEFAULT NULL COMMENT '期限',
  time_unit varchar(10) DEFAULT NULL COMMENT '期限单位',
  amount bigint(20) DEFAULT NULL COMMENT '拟发行金额',
  institution_id bigint(20) DEFAULT NULL COMMENT '交易所/发行机构ID',
  institution_name varchar(256) DEFAULT NULL COMMENT '交易所/发行机构名称',
  issue_rate decimal(12, 4) DEFAULT NULL COMMENT '发行利率',
  project_gist varchar(512) DEFAULT NULL COMMENT '项目依据',
  bond_code varchar(50) DEFAULT NULL COMMENT '债权代码',
  letter_url varchar(512) DEFAULT NULL COMMENT '担保函地址',
  voucher_url varchar(512) DEFAULT NULL COMMENT '付款凭证地址',
  actual_amount bigint(20) DEFAULT NULL COMMENT '实际发行金额',
  issue_date datetime DEFAULT NULL COMMENT '发行日期',
  expire_time datetime DEFAULT NULL COMMENT '到期日期',
  is_continue varchar(10) DEFAULT NULL COMMENT '是否继续发售',
  status varchar(100) DEFAULT NULL COMMENT '发售状态',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  INDEX project_peoject_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '承销/发债类项目发行信息';

--
-- Definition for table recover_project
--
DROP TABLE IF EXISTS recover_project;
CREATE TABLE recover_project (
  recover_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  project_code varchar(50) DEFAULT NULL COMMENT '对应项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '对应项目名称',
  customer_id bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  customer_name varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  busi_manager_id bigint(20) DEFAULT NULL COMMENT '客户经理ID',
  busi_manager_name varchar(50) DEFAULT NULL COMMENT '客户经理名称',
  status varchar(10) DEFAULT NULL COMMENT '状态',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (recover_id),
  INDEX recover_project_peoject_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目追偿列表';

--
-- Definition for table recover_project_receipt
--
DROP TABLE IF EXISTS recover_project_receipt;
CREATE TABLE recover_project_receipt (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  recover_id bigint(20) NOT NULL COMMENT '追偿ID',
  receipt_name varchar(128) DEFAULT NULL COMMENT '回执单名称',
  receipt_url varchar(20) DEFAULT NULL COMMENT '回执单地址',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目追偿列表 - 回执单';

--
-- Definition for table recover_project_track_record
--
DROP TABLE IF EXISTS recover_project_track_record;
CREATE TABLE recover_project_track_record (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  recover_id bigint(20) NOT NULL COMMENT '追偿ID',
  record_type varchar(20) NOT NULL COMMENT '节点类型',
  record_type_name varchar(20) NOT NULL COMMENT '节点类型描述',
  propose_time datetime DEFAULT NULL COMMENT '提出时间',
  pay_time datetime DEFAULT NULL COMMENT '缴费时间',
  judge_time datetime DEFAULT NULL COMMENT '裁定时间',
  seal_up_date date DEFAULT NULL COMMENT '查封日期',
  seal_up_object date DEFAULT NULL COMMENT '查封标的',
  seal_up_start_time datetime DEFAULT NULL COMMENT '查封期限-起',
  seal_up_end_time datetime DEFAULT NULL COMMENT '查封期限-止',
  seal_up_remark varchar(128) DEFAULT NULL COMMENT '查封备注',
  detain_date date DEFAULT NULL COMMENT '扣押日期',
  detain_object date DEFAULT NULL COMMENT '扣押标的',
  detain_start_time datetime DEFAULT NULL COMMENT '扣押期限-起',
  detain_end_time datetime DEFAULT NULL COMMENT '扣押期限-止',
  detain_remark varchar(128) DEFAULT NULL COMMENT '扣押备注',
  freeze_date date DEFAULT NULL COMMENT '冻结日期',
  freeze_object date DEFAULT NULL COMMENT '冻结标的',
  freeze_start_time datetime DEFAULT NULL COMMENT '冻结期限-起',
  freeze_end_time datetime DEFAULT NULL COMMENT '冻结期限-止',
  freeze_remark varchar(128) DEFAULT NULL COMMENT '冻结备注',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目追偿 - 追偿跟踪表';

--
-- Definition for table region
--
DROP TABLE IF EXISTS region;
CREATE TABLE region (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  code varchar(255) DEFAULT NULL COMMENT '编码',
  name varchar(50) DEFAULT NULL COMMENT '名称',
  parent_code varchar(20) DEFAULT NULL COMMENT '父节点',
  level smallint(6) DEFAULT NULL COMMENT '层级',
  sort_order int(11) DEFAULT NULL COMMENT '排序号',
  has_children varchar(10) DEFAULT NULL COMMENT '是否还有下级',
  remark varchar(50) DEFAULT NULL COMMENT '备注',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '新增区域划分';

--
-- Definition for table related_user
--
DROP TABLE IF EXISTS related_user;
CREATE TABLE born_fcs_fbpm.related_user (
  related_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) DEFAULT NULL COMMENT '表单ID（项目不相关时候保存）',
  form_code varchar(50) DEFAULT NULL COMMENT '表单code',
  task_id bigint(20) DEFAULT 0 COMMENT '任务ID',
  exe_status varchar(20) DEFAULT NULL COMMENT '任务执行状态',
  project_code varchar(50) DEFAULT NULL COMMENT '项目编号',
  user_type varchar(20) DEFAULT 'OTHER' COMMENT '人员类型 (业务经理、法务经理、其他...等)',
  user_id bigint(20) DEFAULT NULL COMMENT '当前人员ID',
  user_account varchar(50) DEFAULT NULL COMMENT '当前人员账号',
  user_name varchar(50) DEFAULT NULL COMMENT '当前人员名称',
  user_mobile varchar(20) DEFAULT NULL COMMENT '当前人员手机',
  user_email varchar(128) DEFAULT NULL COMMENT '当前人员email',
  dept_id bigint(20) DEFAULT NULL COMMENT '当前部门ID',
  dept_code varchar(50) DEFAULT NULL COMMENT '部门编号',
  dept_name varchar(128) DEFAULT NULL COMMENT '部门名称',
  transfer_time datetime DEFAULT NULL COMMENT '转交时间',
  remark text DEFAULT NULL COMMENT '备注',
  is_current varchar(10) DEFAULT 'IS' COMMENT '是否是当前人员(方便保存历史记录)',
  is_del varchar(10) DEFAULT 'NO' COMMENT '是否删除(删除后不再有权限)',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (related_id),
  INDEX related_persion_form_id_i (form_id),
  INDEX related_persion_project_code_i (project_code),
  INDEX related_user_task_id_i (task_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '项目/表单相关人员';

--
-- Definition for table release_project
--
DROP TABLE IF EXISTS release_project;
CREATE TABLE release_project (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  project_code varchar(50) DEFAULT NULL COMMENT '项目编号',
  project_name varchar(512) DEFAULT NULL COMMENT '项目名称',
  status varchar(20) DEFAULT NULL COMMENT '解保状态：可解保，不可解保，解保完成',
  raw_add_time timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  raw_update_time timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (id),
  INDEX release_project_project_code_i (project_code)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '解保项目列表';

--
-- Definition for table risk_warning_signal
--
DROP TABLE IF EXISTS risk_warning_signal;
CREATE TABLE risk_warning_signal (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  signal_type varchar(32) DEFAULT NULL COMMENT '客户类型(公司类，金融类，小微企业等)',
  signal_level varchar(32) NOT NULL COMMENT '信号种类',
  signal_type_name varchar(128) DEFAULT NULL COMMENT '信号种类名称',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '风险预警处理 - 风险预警信号数据表';

--
-- Definition for table special_paper_invalid
--
DROP TABLE IF EXISTS special_paper_invalid;
CREATE TABLE special_paper_invalid (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  receive_man_id bigint(20) DEFAULT 0 COMMENT '领取人id',
  receive_man_name varchar(20) DEFAULT NULL COMMENT '领取人名字',
  keeping_man_id bigint(20) DEFAULT 0 COMMENT '保管人id',
  keeping_man_name varchar(50) DEFAULT NULL COMMENT '保管人名字',
  pieces bigint(20) DEFAULT NULL COMMENT '张数',
  receive_date datetime DEFAULT NULL COMMENT '领用时间',
  remark varchar(512) DEFAULT NULL COMMENT '备注',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '特殊纸管理 - 作废';

--
-- Definition for table special_paper_no
--
DROP TABLE IF EXISTS special_paper_no;
CREATE TABLE special_paper_no (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  start_no bigint(20) DEFAULT 0 COMMENT '起号',
  end_no bigint(20) DEFAULT 0 COMMENT '止号',
  source_id bigint(20) DEFAULT NULL COMMENT '来源id(登记、部门领取、人领取、作废)',
  keeping_man_name varchar(50) DEFAULT NULL COMMENT '保管人名字',
  keeping_man_id bigint(20) DEFAULT 0 COMMENT '保管人id',
  left_paper bigint(20) DEFAULT 0 COMMENT '剩余纸张',
  source varchar(20) DEFAULT NULL COMMENT '来源(登记、部门领取、人领取、作废)',
  parent_id bigint(20) DEFAULT 0 COMMENT '父亲编号id',
  pieces bigint(20) DEFAULT NULL COMMENT '张数',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '特殊纸管理 - 编号表';

--
-- Definition for table special_paper_provide_dept
--
DROP TABLE IF EXISTS special_paper_provide_dept;
CREATE TABLE special_paper_provide_dept (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  dept_id bigint(20) DEFAULT NULL COMMENT '部门id',
  dept_name varchar(20) DEFAULT NULL COMMENT '部门名称',
  receive_man_id bigint(20) DEFAULT NULL COMMENT '领取人id',
  receive_man_name varchar(20) DEFAULT NULL COMMENT '领取人',
  provide_man_id bigint(20) DEFAULT 0 COMMENT '发放人id',
  provide_man_name varchar(50) DEFAULT NULL COMMENT '发放人名字',
  pieces bigint(20) DEFAULT NULL COMMENT '张数',
  remark text DEFAULT NULL COMMENT '备注',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '特殊纸管理 - 部门发放';

--
-- Definition for table special_paper_provide_project
--
DROP TABLE IF EXISTS special_paper_provide_project;
CREATE TABLE special_paper_provide_project (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  receive_man_id bigint(20) DEFAULT NULL COMMENT '领取人id',
  receive_man_name varchar(20) DEFAULT NULL COMMENT '领取人',
  provide_man_id bigint(20) DEFAULT 0 COMMENT '发放人id',
  provide_man_name varchar(50) DEFAULT NULL COMMENT '发放人名字',
  project_code varchar(50) DEFAULT NULL COMMENT '关联项目编码',
  project_name varchar(512) DEFAULT NULL COMMENT '关联项目名称',
  receipt_place varchar(128) DEFAULT NULL COMMENT '收函单位',
  receipt_man varchar(20) DEFAULT NULL COMMENT '收函人',
  pieces bigint(20) DEFAULT NULL COMMENT '张数',
  profiles text DEFAULT NULL COMMENT '保函内容简介',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '特殊纸管理 - 项目发放';

--
-- Definition for table sys_date_seq
--
DROP TABLE IF EXISTS sys_date_seq;
CREATE TABLE sys_date_seq (
  name varchar(50) NOT NULL COMMENT 'seq名字',
  seq_date varchar(10) NOT NULL COMMENT '日期',
  max_number bigint(20) NOT NULL COMMENT '当前最大序列号',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '数据添加时间',
  raw_update_time timestamp DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (name, seq_date)
)
ENGINE = INNODB
AVG_ROW_LENGTH = 128
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '系统表，用于生成按日期序列号';

--
-- Definition for table sys_param
--
DROP TABLE IF EXISTS sys_param;
CREATE TABLE sys_param (
  param_name varchar(50) NOT NULL DEFAULT '' COMMENT '系统参数名称',
  param_value varchar(5000) DEFAULT NULL COMMENT '系统参数值',
  extend_attribute_one varchar(500) DEFAULT NULL COMMENT '扩展属性1(开始金额)',
  extend_attribute_two varchar(500) DEFAULT NULL COMMENT '扩展属性2（结束金额）',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  raw_update_time timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  description text DEFAULT NULL,
  PRIMARY KEY (param_name)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '系统参数表';

--
-- Definition for table sys_web_access_token
--
DROP TABLE IF EXISTS sys_web_access_token;
CREATE TABLE sys_web_access_token (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  user_id bigint(20) DEFAULT NULL COMMENT '用户ID',
  user_name varchar(50) DEFAULT NULL COMMENT '用户名',
  user_account varchar(50) DEFAULT NULL COMMENT '用户账号',
  access_token varchar(50) DEFAULT NULL COMMENT '访问密钥',
  is_valid varchar(10) DEFAULT NULL COMMENT '是否有效(使用过后就失效)',
  remark varchar(512) DEFAULT NULL COMMENT '备注',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  INDEX sys_web_access_token_access_token_i (access_token)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '访问web端的密钥';

--
-- Definition for table user_extra_message
--
DROP TABLE IF EXISTS user_extra_message;
CREATE TABLE user_extra_message (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  user_id bigint(20) NOT NULL COMMENT '用户ID',
  user_name varchar(50) NOT NULL COMMENT '用户名，用作查看',
  user_account varchar(50) DEFAULT NULL COMMENT '用户登录名',
  user_judge_key varchar(50) DEFAULT NULL COMMENT '用户投票的证书key',
  raw_add_time timestamp NULL DEFAULT NULL,
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  INDEX user_account_key (user_account),
  INDEX user_id_key (user_id)
)
ENGINE = INNODB
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '用户信息- 用户额外信息';

DROP TABLE IF EXISTS sys_db_backup_config;
CREATE TABLE born_fcs_fbpm.sys_db_backup_config (
  config_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '配置ID',
  db_user varchar(50) DEFAULT NULL COMMENT '数据库用户',
  db_psw varchar(1024) DEFAULT NULL COMMENT '数据库密码',
  db_host varchar(50) DEFAULT NULL COMMENT '数据库地址',
  db_port int(11) DEFAULT NULL COMMENT '数据库端口',
  db_home varchar(128) DEFAULT NULL COMMENT 'mysql数据库目录',
  scheme_name varchar(128) DEFAULT NULL COMMENT '数据库名称',
  backup_folder varchar(512) DEFAULT NULL COMMENT '备份目录',
  backup_inverval_minute int(11) DEFAULT NULL COMMENT '备份频率分钟数',
  backup_time_begin varchar(20) DEFAULT NULL COMMENT '备份开始时间',
  backup_time_end varchar(20) DEFAULT NULL COMMENT '备份结束时间',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (config_id),
  UNIQUE INDEX UK_sys_db_backup_config_db_host (db_host)
)
ENGINE = INNODB
AUTO_INCREMENT = 1
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '数据库备份配置';


DROP TABLE IF EXISTS sys_db_backup_log;
CREATE TABLE born_fcs_fbpm.sys_db_backup_log (
  log_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  scheme_name varchar(128) DEFAULT NULL COMMENT '备份数据库',
  backup_file varchar(512) DEFAULT NULL COMMENT '备份文件',
  backup_time datetime DEFAULT NULL COMMENT '备份时间',
  is_success varchar(10) DEFAULT NULL COMMENT '是否成功',
  is_del varchar(10) DEFAULT NULL COMMENT '备份是否已删除',
  del_time datetime DEFAULT NULL COMMENT '删除时间',
  fail_reason text DEFAULT NULL COMMENT '失败原因',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '新增时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (log_id)
)
ENGINE = INNODB
AUTO_INCREMENT = 1
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '系统数据库备份日志';
--jiliang 2016-9-22
DROP TABLE IF EXISTS f_capital_appropriation_apply_receipt;
CREATE TABLE born_fcs_fbpm.f_capital_appropriation_apply_receipt (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  form_id bigint(20) DEFAULT NULL COMMENT '主表form_id',
  stroke_time datetime DEFAULT NULL COMMENT '划款时间',
  payee_account_name varchar(30) DEFAULT NULL COMMENT '收款方账户名',
  bank_account varchar(128) DEFAULT NULL COMMENT '开户行',
  payee_account varchar(100) DEFAULT NULL COMMENT '收款账号',
  payment_amount bigint(20) DEFAULT NULL COMMENT '付款金额',
  attach text DEFAULT NULL COMMENT '附件',
  raw_add_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
  raw_update_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id),
  INDEX f_capital_appropriation_receipt_id_i (id)
)
ENGINE = INNODB
AUTO_INCREMENT = 70
AVG_ROW_LENGTH = 733
CHARACTER SET utf8
COLLATE utf8_general_ci
COMMENT = '资金划付申请-回执信息';



CREATE TABLE `project_data_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `project_id` bigint(20) NOT NULL COMMENT '主键',
  `project_code` varchar(50) NOT NULL COMMENT '项目编号',
  `project_name` varchar(128) DEFAULT NULL COMMENT '项目名称',
  `customer_id` bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  `customer_name` varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  `customer_type` varchar(32) DEFAULT NULL COMMENT '客户类型',
  `enterprise_type` varchar(32) DEFAULT NULL COMMENT '客户性质、企业性质',
  `customer_level` varchar(64) DEFAULT NULL COMMENT '客户等级',
  `scale` varchar(64) DEFAULT NULL COMMENT '客户规模',
  `province_code` varchar(64) DEFAULT NULL COMMENT '客户地区省',
  `province_name` varchar(64) DEFAULT NULL COMMENT '客户地区省',
  `city_code` varchar(64) DEFAULT NULL COMMENT '客户地区市',
  `city_name` varchar(64) DEFAULT NULL COMMENT '客户地区市',
  `county_code` varchar(64) DEFAULT NULL COMMENT '客户地区编码',
  `county_name` varchar(64) DEFAULT NULL COMMENT '客户地区',
  `busi_type` varchar(50) DEFAULT NULL COMMENT '业务类型',
  `busi_type_name` varchar(120) DEFAULT NULL COMMENT '业务类型名称',
  `industry_code` varchar(128) DEFAULT NULL COMMENT '所属行业',
  `industry_name` varchar(128) DEFAULT NULL COMMENT '行业名称',
  `capital_channel_id` bigint(20) DEFAULT NULL COMMENT '资金渠道一级',
  `capital_channel_name` varchar(128) DEFAULT NULL COMMENT '资金渠道一级',
  `channal_type` varchar(128) DEFAULT NULL COMMENT '渠道类型',
  `capital_sub_channel_name` varchar(256) DEFAULT NULL COMMENT '资金渠道二级',
  `time_limit` int(11) DEFAULT NULL COMMENT '期限',
  `time_unit` varchar(10) DEFAULT NULL COMMENT '期限单位',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `amount` bigint(20) DEFAULT NULL COMMENT '金额',
  `accumulated_issue_amount` bigint(20) DEFAULT NULL COMMENT '累计已发行金额(承销/发债信息维护)',
  `loaned_amount` bigint(20) DEFAULT NULL COMMENT '已放款金额',
  `used_amount` bigint(20) DEFAULT NULL COMMENT '已用款金额',
  `repayed_amount` bigint(20) DEFAULT NULL COMMENT '已还款金额',
  `charged_amount` bigint(20) DEFAULT NULL COMMENT '已收费金额',
  `refund_amount` bigint(20) DEFAULT NULL COMMENT '已退费金额',
  `is_maximum_amount` varchar(10) DEFAULT 'NO' COMMENT '是否最高额授信',
  `released_amount` bigint(20) DEFAULT NULL COMMENT '已解保金额',
  `releasable_amount` bigint(20) DEFAULT NULL COMMENT '总的可解保金额',
  `releasing_amount` bigint(20) DEFAULT NULL COMMENT '解保中的金额',
  `customer_deposit_amount` bigint(20) DEFAULT NULL COMMENT '客户保证金',
  `self_deposit_amount` bigint(20) DEFAULT NULL COMMENT '自家保证金',
  `comp_principal_amount` bigint(20) DEFAULT NULL COMMENT '已代偿本金',
  `comp_interest_amount` bigint(20) DEFAULT NULL COMMENT '已代偿利息',
  `guarantee_amount` bigint(20) DEFAULT NULL COMMENT '担保费',
  `guarantee_fee_rate` float DEFAULT NULL COMMENT '担保费率',
  `blance` bigint(20) DEFAULT NULL COMMENT '再保余额',
  `contract_no` varchar(20) DEFAULT NULL COMMENT '合同编号',
  `contract_time` datetime DEFAULT NULL COMMENT '合同签订时间',
  `is_approval` varchar(10) DEFAULT 'NO' COMMENT '项目是否已经批复',
  `is_approval_del` varchar(10) DEFAULT 'NO' COMMENT '项目批复是否作废',
  `approval_time` datetime DEFAULT NULL COMMENT '会议纪要通过时间',
  `busi_manager_id` bigint(20) DEFAULT NULL COMMENT '客户经理ID',
  `busi_manager_account` varchar(20) DEFAULT NULL COMMENT '客户经理账号',
  `busi_manager_name` varchar(50) DEFAULT NULL COMMENT '客户经理名称',
  `busi_managerb_id` bigint(20) DEFAULT NULL COMMENT '客户经理B',
  `busi_managerb_account` varchar(20) DEFAULT NULL COMMENT '客户经理B账号',
  `busi_managerb_name` varchar(50) DEFAULT NULL COMMENT '客户经理B名称',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '所属部门ID',
  `dept_code` varchar(30) DEFAULT NULL COMMENT '部门编号',
  `dept_name` varchar(50) DEFAULT NULL COMMENT '所属部门名称',
  `dept_path` varchar(256) DEFAULT NULL COMMENT '部门路径',
  `dept_path_name` varchar(500) DEFAULT NULL COMMENT '部门路径名称',
  `phases` varchar(20) DEFAULT NULL COMMENT '项目阶段',
  `phases_status` varchar(10) DEFAULT NULL COMMENT '项目阶段状态',
  `status` varchar(20) DEFAULT 'NORMAL' COMMENT '项目状态(正常、暂缓、未成立、完成)',
  `is_continue` varchar(20) DEFAULT 'NO' COMMENT '是否手动停止继续发售(承销/发债发售信息维护)',
  `is_recouncil` varchar(10) DEFAULT 'NO' COMMENT '是否可复议',
  `last_recouncil_time` datetime DEFAULT NULL COMMENT '上次复议时间',
  `setup_date` date DEFAULT NULL COMMENT '立项时间',
  `set_up_year` bigint(18) DEFAULT NULL COMMENT '立项时间-年',
  `set_up_month` bigint(18) DEFAULT NULL COMMENT '立项时间-月',
  `set_up_day` bigint(18) DEFAULT NULL COMMENT '立项时间-日',
  `apply_date` date DEFAULT NULL COMMENT '立项申请时间',
  `apply_date_year` bigint(18) DEFAULT NULL COMMENT '立项申请时间-年',
  `apply_date_month` bigint(18) DEFAULT NULL COMMENT '立项申请时间-月',
  `apply_date_day` bigint(18) DEFAULT NULL COMMENT '立项申请时间-日',
  `survey_date` date DEFAULT NULL COMMENT '调查时间',
  `survey_date_year` bigint(18) DEFAULT NULL COMMENT '调查时间-年',
  `survey_date_month` bigint(18) DEFAULT NULL COMMENT '调查时间-月',
  `survey_date_day` bigint(18) DEFAULT NULL COMMENT '调查时间-日',
  `on_will_date` date DEFAULT NULL COMMENT '上会时间',
  `on_will_date_year` bigint(18) DEFAULT NULL COMMENT '上会时间-年',
  `on_will_date_month` bigint(18) DEFAULT NULL COMMENT '上会时间-月',
  `on_will_date_day` bigint(18) DEFAULT NULL COMMENT '上会时间-日',
  `is_on_will_pass` varchar(32) DEFAULT NULL COMMENT '上会通过标记',
  `first_lending_date` date DEFAULT NULL COMMENT '首次放款时间',
  `raw_add_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `custom_amount1` bigint(20) DEFAULT NULL COMMENT '自定义金额1',
  `custom_amount2` bigint(20) DEFAULT NULL COMMENT '自定义金额2',
  `custom_amount3` bigint(20) DEFAULT NULL COMMENT '自定义金额3',
  `custom_name1` varchar(256) DEFAULT NULL COMMENT '自定义字符1',
  `custom_name2` varchar(256) DEFAULT NULL COMMENT '自定义字符2',
  `custom_name3` varchar(256) DEFAULT NULL COMMENT '自定义字符3',
  `raw_update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `project_data_info_project_id` (`project_id`) USING BTREE,
  UNIQUE KEY `project_data_info_project_code` (`project_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='项目数据信息';


CREATE TABLE `project_data_info_his_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `project_date` date NOT NULL,
  `project_id` bigint(20) NOT NULL COMMENT '主键',
  `project_code` varchar(50) NOT NULL COMMENT '项目编号',
  `project_name` varchar(128) DEFAULT NULL COMMENT '项目名称',
  `customer_id` bigint(20) DEFAULT NULL COMMENT '对应客户ID',
  `customer_name` varchar(128) DEFAULT NULL COMMENT '对应客户名称',
  `customer_type` varchar(32) DEFAULT NULL COMMENT '客户类型',
  `enterprise_type` varchar(32) DEFAULT NULL COMMENT '客户性质、企业性质',
  `customer_level` varchar(64) DEFAULT NULL,
  `scale` varchar(64) DEFAULT NULL COMMENT '客户规模',
  `province_code` varchar(64) DEFAULT NULL COMMENT '客户地区省',
  `province_name` varchar(64) DEFAULT NULL COMMENT '客户地区省',
  `city_code` varchar(64) DEFAULT NULL COMMENT '客户地区市',
  `city_name` varchar(64) DEFAULT NULL COMMENT '客户地区市',
  `county_code` varchar(64) DEFAULT NULL COMMENT '客户地区编码',
  `county_name` varchar(64) DEFAULT NULL COMMENT '客户地区',
  `busi_type` varchar(50) DEFAULT NULL COMMENT '业务类型',
  `busi_type_name` varchar(120) DEFAULT NULL COMMENT '业务类型名称',
  `industry_code` varchar(128) DEFAULT NULL COMMENT '所属行业',
  `industry_name` varchar(128) DEFAULT NULL COMMENT '行业名称',
  `capital_channel_id` bigint(20) DEFAULT NULL COMMENT '资金渠道一级',
  `capital_channel_name` varchar(128) DEFAULT NULL COMMENT '资金渠道一级',
  `channal_type` varchar(128) DEFAULT NULL COMMENT '渠道类型',
  `capital_sub_channel_name` varchar(256) DEFAULT NULL COMMENT '资金渠道二级',
  `time_limit` int(11) DEFAULT NULL COMMENT '期限',
  `time_unit` varchar(10) DEFAULT NULL COMMENT '期限单位',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `amount` bigint(20) DEFAULT NULL COMMENT '金额',
  `accumulated_issue_amount` bigint(20) DEFAULT NULL COMMENT '累计已发行金额(承销/发债信息维护)',
  `loaned_amount` bigint(20) DEFAULT NULL COMMENT '已放款金额',
  `used_amount` bigint(20) DEFAULT NULL COMMENT '已用款金额',
  `repayed_amount` bigint(20) DEFAULT NULL COMMENT '已还款金额',
  `charged_amount` bigint(20) DEFAULT NULL COMMENT '已收费金额',
  `refund_amount` bigint(20) DEFAULT NULL COMMENT '已退费金额',
  `is_maximum_amount` varchar(10) DEFAULT 'NO' COMMENT '是否最高额授信',
  `released_amount` bigint(20) DEFAULT NULL COMMENT '已解保金额',
  `releasable_amount` bigint(20) DEFAULT NULL COMMENT '总的可解保金额',
  `releasing_amount` bigint(20) DEFAULT NULL COMMENT '解保中的金额',
  `customer_deposit_amount` bigint(20) DEFAULT NULL COMMENT '客户保证金',
  `self_deposit_amount` bigint(20) DEFAULT NULL COMMENT '自家保证金',
  `comp_principal_amount` bigint(20) DEFAULT NULL COMMENT '已代偿本金',
  `comp_interest_amount` bigint(20) DEFAULT NULL COMMENT '已代偿利息',
  `guarantee_amount` bigint(20) DEFAULT NULL COMMENT '担保费',
  `guarantee_fee_rate` float DEFAULT NULL COMMENT '担保费率',
  `blance` bigint(20) DEFAULT NULL COMMENT '再保余额',
  `contract_no` varchar(20) DEFAULT NULL COMMENT '合同编号',
  `contract_time` datetime DEFAULT NULL COMMENT '合同签订时间',
  `is_approval` varchar(10) DEFAULT 'NO' COMMENT '项目是否已经批复',
  `is_approval_del` varchar(10) DEFAULT 'NO' COMMENT '项目批复是否作废',
  `approval_time` datetime DEFAULT NULL COMMENT '会议纪要通过时间',
  `busi_manager_id` bigint(20) DEFAULT NULL COMMENT '客户经理ID',
  `busi_manager_account` varchar(20) DEFAULT NULL COMMENT '客户经理账号',
  `busi_manager_name` varchar(50) DEFAULT NULL COMMENT '客户经理名称',
  `busi_managerb_id` bigint(20) DEFAULT NULL COMMENT '客户经理B',
  `busi_managerb_account` varchar(20) DEFAULT NULL COMMENT '客户经理B账号',
  `busi_managerb_name` varchar(50) DEFAULT NULL COMMENT '客户经理B名称',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '所属部门ID',
  `dept_code` varchar(30) DEFAULT NULL COMMENT '部门编号',
  `dept_name` varchar(50) DEFAULT NULL COMMENT '所属部门名称',
  `dept_path` varchar(256) DEFAULT NULL COMMENT '部门路径',
  `dept_path_name` varchar(500) DEFAULT NULL COMMENT '部门路径名称',
  `phases` varchar(20) DEFAULT NULL COMMENT '项目阶段',
  `phases_status` varchar(10) DEFAULT NULL COMMENT '项目阶段状态',
  `status` varchar(20) DEFAULT 'NORMAL' COMMENT '项目状态(正常、暂缓、未成立、完成)',
  `is_continue` varchar(20) DEFAULT 'NO' COMMENT '是否手动停止继续发售(承销/发债发售信息维护)',
  `is_recouncil` varchar(10) DEFAULT 'NO' COMMENT '是否可复议',
  `last_recouncil_time` datetime DEFAULT NULL COMMENT '上次复议时间',
  `setup_date` date DEFAULT NULL COMMENT '立项时间',
  `set_up_year` bigint(18) DEFAULT NULL COMMENT '立项时间-年',
  `set_up_month` bigint(18) DEFAULT NULL COMMENT '立项时间-月',
  `set_up_day` bigint(18) DEFAULT NULL COMMENT '立项时间-日',
  `apply_date` date DEFAULT NULL COMMENT '立项申请时间',
  `apply_date_year` bigint(18) DEFAULT NULL COMMENT '立项申请时间-年',
  `apply_date_month` bigint(18) DEFAULT NULL COMMENT '立项申请时间-月',
  `apply_date_day` bigint(18) DEFAULT NULL COMMENT '立项申请时间-日',
  `survey_date` date DEFAULT NULL COMMENT '调查时间',
  `survey_date_year` bigint(18) DEFAULT NULL COMMENT '调查时间-年',
  `survey_date_month` bigint(18) DEFAULT NULL COMMENT '调查时间-月',
  `survey_date_day` bigint(18) DEFAULT NULL COMMENT '调查时间-日',
  `on_will_date` date DEFAULT NULL COMMENT '上会时间',
  `on_will_date_year` bigint(18) DEFAULT NULL COMMENT '上会时间-年',
  `on_will_date_month` bigint(18) DEFAULT NULL,
  `on_will_date_day` bigint(18) DEFAULT NULL,
  `is_on_will_pass` varchar(32) DEFAULT NULL COMMENT '上会通过标记',
  `first_lending_date` date DEFAULT NULL COMMENT '首次放款时间',
  `raw_add_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `year` bigint(20) DEFAULT NULL COMMENT '年',
  `month` bigint(20) DEFAULT NULL COMMENT '月',
  `day` bigint(20) DEFAULT NULL COMMENT '日',
  `custom_amount1` bigint(20) DEFAULT NULL COMMENT '自定义金额1',
  `custom_amount2` bigint(20) DEFAULT NULL COMMENT '自定义金额2',
  `custom_amount3` bigint(20) DEFAULT NULL COMMENT '自定义金额3',
  `custom_name1` varchar(256) DEFAULT NULL COMMENT '自定义字符1',
  `custom_name2` varchar(256) DEFAULT NULL COMMENT '自定义字符2',
  `custom_name3` varchar(256) DEFAULT NULL COMMENT '自定义字符3',
  `raw_update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `project_data_info_his_date_code` (`project_date`,`project_code`),
  UNIQUE KEY `project_data_info_his_date_id` (`project_date`,`project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='项目数据信息';

