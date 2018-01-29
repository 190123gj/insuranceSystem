/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.yjf;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 
 * @Filename SeviceTestBase.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author qichunhai
 * 
 * @Email qchunhai@yiji.com
 * 
 * @History <li>Author: qichunhai</li> <li>Date: 2013-7-5</li> <li>Version: 1.0</li>
 * <li>Content: create</li>
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/spring/insurance-common-dal-dao.xml", 
						"/spring/insurance-service.xml", "/spring/insurance-ws-server.xml",
						"/spring/insurance-common-dal-db.xml","/spring/insurance-integration.xml","/spring/integration-insurance-cxf.xml" })
public class SeviceTestBase {
	static {
		System.setProperty("spring.profiles.active", "test");
	}
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
}
