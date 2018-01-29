/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.insurance.biz.service.base;

import java.util.Date;

import com.born.insurance.dal.daointerface.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.born.insurance.daointerface.BusiDAO;
import com.born.insurance.daointerface.ExtraDAO;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 
 * @Filename BaseAutowiredDAOService
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author qichunhai
 * 
 * @Email qchunhai@yiji.com
 * 
 * @History <li>Author: qichunhai</li> <li>Date: 2014-4-8</li> <li>Version: 1.0</li>
 * <li>Content: create</li>
 * 
 */
public class BaseAutowiredDAOService {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	protected ExtraDAO extraDAO;
	
	@Autowired
	protected BusiDAO busiDAO;
	
	@Autowired
	protected FormDAO formDAO;
	
	// 附件上传相关
	@Autowired
	protected CommonAttachmentDAO commonAttachmentDAO;
	
	@Autowired
	protected RelatedUserDAO relatedUserDAO;
	
	@Autowired
	protected FormRelatedUserDAO formRelatedUserDAO;
	
	@Autowired
	protected DemoDAO demoDAO;
	
	// 资金划付申请 end
	
	//站内信
	@Autowired
	protected MessageInfoDAO messageInfoDAO;
	@Autowired
	protected MessageReceivedDAO messageReceivedDAO;
	
	@Autowired
	protected FormMessageTempleteDAO formMessageTempleteDAO;
	
	@Autowired
	protected UserExtraMessageDAO userExtraMessageDAO;
	
	@Autowired
	protected SysDbBackupConfigDAO sysDbBackupConfigDAO;
	@Autowired
	protected SysDbBackupLogDAO sysDbBackupLogDAO;
	
	/**
	 * @return Date
	 */
	protected Date getSysdate() {
		try {
			Date sysDate = extraDAO.getSysdate();
			logger.info("系统时间：sysDate=" + sysDate);
			return sysDate;
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
		}
		return new Date();
	}
	
}
