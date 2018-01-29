package com.born.insurance.ws.service.common;

import javax.jws.WebService;

import com.born.insurance.ws.info.common.SysDbBackupConfigInfo;
import com.born.insurance.ws.info.common.SysDbBackupLogInfo;
import com.born.insurance.ws.order.common.SysDbBackupConfigOrder;
import com.born.insurance.ws.order.common.SysDbBackupConfigQueryOrder;
import com.born.insurance.ws.order.common.SysDbBackupLogQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;

@WebService
public interface SysDbBackupService {
	
	/**
	 * 保存备份配置信息
	 * @param order
	 * @return
	 */
	InsuranceBaseResult saveConfig(SysDbBackupConfigOrder order);
	
	/**
	 * 删除配置
	 * @param configId
	 * @return
	 */
	InsuranceBaseResult delConfig(long configId);
	
	/**
	 * 更新状态
	 * @param configId
	 * @param inUse
	 * @return
	 */
	InsuranceBaseResult changeInUse(long configId);
	
	/**
	 * 查询备份日志
	 * @return
	 */
	QueryBaseBatchResult<SysDbBackupConfigInfo> queryConfig(SysDbBackupConfigQueryOrder order);
	
	/**
	 * 根据备份日志删除备份文件
	 * @param logId
	 * @return
	 */
	InsuranceBaseResult delBackupFile(long logId);
	
	/**
	 * 查询备份日志
	 * @return
	 */
	QueryBaseBatchResult<SysDbBackupLogInfo> queryLog(SysDbBackupLogQueryOrder order);
	
	/**
	 * 执行备份
	 * @return
	 */
	InsuranceBaseResult runBackupByConfig(SysDbBackupConfigInfo config, boolean isManual);
	
	/**
	 * 执行备份
	 * @param configId
	 * @return
	 */
	InsuranceBaseResult runBackupByConfigId(long configId);
	
	/**
	 * 执行备份
	 * @return
	 */
	InsuranceBaseResult runBackup();
}
