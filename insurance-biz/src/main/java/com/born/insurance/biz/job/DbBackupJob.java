package com.born.insurance.biz.job;

import com.born.insurance.biz.job.inter.ProcessJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.born.insurance.ws.service.common.SysDbBackupService;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 
 * 数据库备份
 * 
 * @author wuzj
 *
 */
@Service("dbBackupJob")
public class DbBackupJob extends JobBase implements ProcessJobService {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SysDbBackupService sysDbBackupService;
	
	@Scheduled(cron = "0 0/1 * * * ? ")
	@Override
	public void doJob() throws Exception {
		if (!isRun)
			return;
		try {
			sysDbBackupService.runBackup();
		} catch (Exception e) {
			logger.error("数据库备份出错:{}", e);
		}
	}
}
