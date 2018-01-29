package com.born.insurance.biz.job;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.born.insurance.biz.job.inter.ProcessJobService;
import com.born.insurance.biz.service.common.AsynchronousService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

@Service("asynchronousTaskJob")
public class AsynchronousTaskJob implements ProcessJobService {
	static List<AsynchronousService> serviceList = new ArrayList<AsynchronousService>();
	static List<Object[]> paramList = new ArrayList<Object[]>();
	protected final Logger logger = LoggerFactory.getLogger(AsynchronousTaskJob.class);
	
	@Override
	@Scheduled(cron = "0 0/1 * * * ? ")
	public void doJob() throws Exception {
		int count = serviceList.size();
		if (count > 0) {
			logger.info("asynchronousTaskJob " + new Date().toString());
		}
		for (int i = count - 1; i >= 0; i--) {
			AsynchronousService service = serviceList.get(i);
			Object[] objects = paramList.get(i);
			try {
				logger.info("异步任务开始：开始执行类={},参数={}", service.getClass(), Arrays.toString(objects));
				service.execute(objects);
				logger.info("异步任务结束：完成执行类={},参数={}", service.getClass(), Arrays.toString(objects));
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			synchronized (serviceList) {
				serviceList.remove(i);
				paramList.remove(i);
			}
		}
		
	}
	
	@Override
	public void changeIsRun(boolean isRun) {
	}
	
	public void addAsynchronousService(AsynchronousService asynchronousService, Object[] objects) {
		if (asynchronousService != null) {
			synchronized (serviceList) {
				serviceList.add(asynchronousService);
				paramList.add(objects);
			}
		}
	}
}
