package com.born.insurance.biz.job.inter;

/**
 * 
 * @Filename ProcessJobService.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author qichunhai
 * 
 * @Email qchunhai@yiji.com
 * 
 * @History <li>Author: qichunhai</li> <li>Date: 2014-4-14</li> <li>Version: 1.0
 * </li> <li>Content: create</li>
 * 
 */
public interface ProcessJobService {
	
	public void doJob() throws Exception;
	
	public void changeIsRun(boolean isRun);
}
