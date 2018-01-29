package com.born.insurance.biz.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.born.insurance.biz.job.inter.ProcessJobService;
import com.born.insurance.ws.service.insuranceProduct.InsuranceProductService;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 
 * 产品状态的更改
 * 
 * @author guoj
 *
 */
@Service("productSaleStatusJob")
public class ProductSaleStatusJob extends JobBase implements ProcessJobService {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private InsuranceProductService insuranceProductService;
	
	@Scheduled(cron = "0 0 1 * * ?")
	@Override
	public void doJob() throws Exception {
		if (!isRun)
			return;
		try {
			insuranceProductService.runUpdateSaleStatus();
		} catch (Exception e) {
			logger.error("数据库备份出错:{}", e);
		}
	}
}
