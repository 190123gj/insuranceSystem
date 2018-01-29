/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.insurance.biz.service.common;

import javax.jws.WebService;

/**
 * 
 * @Filename DateSeqServices.java
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

@WebService
public interface DateSeqService {
	
	/**
	 * 按天累计生成序列号（每天重头开始）
	 * @param seqName
	 * @param prefix
	 * @param length
	 * @return
	 */
	String getNextDateSeq(String seqName, String prefix, int length);
	
	/**
	 * 按年累计生成序列号（每年重头开始）
	 * @param seqName
	 * @param prefix
	 * @param length
	 * @return
	 */
	String getNextYearSeq(String seqName, String prefix, int length);
	
	/**
	 * 按年月累计生成序列号（每年重头开始）
	 * @param seqName
	 * @param prefix
	 * @param length
	 * @return
	 */
	String getNextYearMonthSeq(String seqName, String prefix, int length);
	

	/**
	 * 获取下一个可用的保后检查期次
	 * @param projectCode
	 * @return
	 */

	/**
	 * 得到一般流水号(getNextSeqNumberWithCache(seqName,50))
	 * @param seqName
	 * @return
	 */
	long getNextSeqNumber(String seqName);
	
	/**
	 * 获得流水号
	 * @param seqName
	 * @param cacheNumber
	 * @return
	 */
	long getNextSeqNumberWithCache(String seqName, long cacheNumber);
	
	/**
	 * 获得流水号
	 * @param seqName
	 * @param transactionRequireNew 是否开启新的事务
	 * @return
	 */
	public long getNextSeqNumberWithoutCache(String seqName, boolean transactionRequireNew);
	
}
