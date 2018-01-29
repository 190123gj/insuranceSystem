package com.born.insurance.web.controller.base;

import com.born.insurance.biz.service.bpm.WorkflowEngineClient;
import com.born.insurance.biz.service.common.SiteMessageService;
import com.born.insurance.biz.service.common.SysParameterService;
import com.born.insurance.integration.bpm.user.BpmUserQueryService;
import com.born.insurance.integration.bpm.user.MobileManager;
import com.born.insurance.integration.common.PropertyConfigService;
import com.born.insurance.ws.service.common.*;
import com.born.insurance.ws.service.demo.DemoService;
import com.born.insurance.ws.service.sms.SMSService;
import com.born.insurance.ws.service.user.UserExtraMessageService;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseAutowiredController {
	
	@Autowired
	protected WorkflowEngineClient workflowEngineClient;
	
	@Autowired
	protected SysParameterService sysParameterService;
	
	@Autowired
	protected FormService formService; //PM 表单客户端
	
	@Autowired
	protected BpmUserQueryService bpmUserQueryService;
	
	@Autowired
	protected SMSService smsService;
	
	@Autowired
	protected CommonAttachmentService commonAttachmentService;
	
	@Autowired
	protected FormRelatedUserService formRelatedUserService;
	
	@Autowired
	protected SiteMessageService siteMessageService;
	
	@Autowired
	protected FormMessageTempleteService formMessageTempleteService;
	
	@Autowired
	protected PropertyConfigService propertyConfigService;
	
	@Autowired
	protected MobileManager mobileManager;
	
	@Autowired
	protected UserExtraMessageService userExtraMessageService;
	
	@Autowired
	protected SysDbBackupService sysDbBackupService;

	@Autowired
	protected DemoService demoService;


	
}
