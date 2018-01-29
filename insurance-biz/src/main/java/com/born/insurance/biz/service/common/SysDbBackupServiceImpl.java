package com.born.insurance.biz.service.common;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Maps;

import com.alibaba.druid.filter.config.ConfigTools;
import com.born.insurance.biz.exception.ExceptionFactory;
import com.born.insurance.biz.service.base.BaseAutowiredDomainService;
import com.born.insurance.dal.dataobject.SysDbBackupConfigDO;
import com.born.insurance.dal.dataobject.SysDbBackupLogDO;
import com.born.insurance.domain.context.InsuranceDomainHolder;
import com.born.insurance.ws.base.PageComponent;
import com.born.insurance.ws.enums.BooleanEnum;
import com.born.insurance.ws.enums.base.InsuranceResultEnum;
import com.born.insurance.ws.info.common.SysDbBackupConfigInfo;
import com.born.insurance.ws.info.common.SysDbBackupLogInfo;
import com.born.insurance.ws.order.common.SysDbBackupConfigOrder;
import com.born.insurance.ws.order.common.SysDbBackupConfigQueryOrder;
import com.born.insurance.ws.order.common.SysDbBackupLogQueryOrder;
import com.born.insurance.ws.result.base.InsuranceBaseResult;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;
import com.born.insurance.ws.service.common.SysDbBackupService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("sysDbBackupService")
public class SysDbBackupServiceImpl extends BaseAutowiredDomainService implements
																		SysDbBackupService {
	
	//所有配置信息
	private List<SysDbBackupConfigInfo> configs = null;
	
	//上次备份时间缓存
	private Map<String, Date> lastBackUpTimeMap = Maps.newHashMap();
	
	private static final String lineSeparator = System.getProperty("line.separator");
	
	@Override
	public InsuranceBaseResult saveConfig(final SysDbBackupConfigOrder order) {
		
		return commonProcess(order, "保存数据库备份配置", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				Date now = InsuranceDomainHolder.get().getSysDate();
				SysDbBackupConfigDO config = null;
				
				if (order.getConfigId() > 0) {
					config = sysDbBackupConfigDAO.findById(order.getConfigId());
				}
				
				boolean isUpdate = false;
				if (config == null) {
					SysDbBackupConfigDO queryDO = new SysDbBackupConfigDO();
					queryDO.setDbHost(order.getDbHome());
					queryDO.setSchemeName(order.getSchemeName());
					if (sysDbBackupConfigDAO.findByConditionCount(queryDO) > 0) {
						throw ExceptionFactory.newFcsException(
							InsuranceResultEnum.DO_ACTION_STATUS_ERROR, "数据库配置已经存在");
					}
					
					config = new SysDbBackupConfigDO();
					BeanCopier.staticCopy(order, config);
					config.setRawAddTime(now);
					//默认不启用，需要手动启用
					config.setInUse(BooleanEnum.NO.code());
				} else {
					long configId = config.getConfigId();
					BeanCopier.staticCopy(order, config);
					config.setConfigId(configId);
					isUpdate = true;
				}
				
				try {
					//加密一下数据
					config.setDbPsw(ConfigTools.encrypt(config.getDbPsw()));
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					throw ExceptionFactory.newFcsException(InsuranceResultEnum.EXECUTE_FAIL,
						"数据库密码加密出错");
				}
				
				if (isUpdate) {
					sysDbBackupConfigDAO.update(config);
				} else {
					sysDbBackupConfigDAO.insert(config);
				}
				
				configs = null;
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public InsuranceBaseResult delConfig(long configId) {
		InsuranceBaseResult result = createResult();
		try {
			sysDbBackupConfigDAO.deleteById(configId);
			this.configs = null;
			result.setSuccess(true);
			result.setMessage("删除配置成功");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("删除配置出错");
			logger.error("删除配置出错");
		}
		return result;
	}
	
	@Override
	public InsuranceBaseResult changeInUse(long configId) {
		InsuranceBaseResult result = createResult();
		String tip = "启用";
		try {
			SysDbBackupConfigDO config = sysDbBackupConfigDAO.findById(configId);
			if (config == null) {
				result.setSuccess(false);
				result.setMessage("配置不存在");
			}
			
			if (BooleanEnum.YES.code().equals(config.getInUse())) {
				tip = "禁用";
				config.setInUse(BooleanEnum.NO.code());
			} else {
				tip = "启用";
				config.setInUse(BooleanEnum.YES.code());
			}
			sysDbBackupConfigDAO.update(config);
			this.configs = null;
			result.setSuccess(true);
			result.setMessage(tip + "配置成功");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(tip + "配置出错");
			logger.error(tip + "配置出错");
		}
		return result;
	}
	
	@Override
	public QueryBaseBatchResult<SysDbBackupConfigInfo> queryConfig(SysDbBackupConfigQueryOrder order) {
		
		QueryBaseBatchResult<SysDbBackupConfigInfo> batchResult = new QueryBaseBatchResult<SysDbBackupConfigInfo>();
		
		try {
			
			SysDbBackupConfigDO configDO = new SysDbBackupConfigDO();
			BeanCopier.staticCopy(order, configDO);
			
			if (order.getInUse() != null) {
				configDO.setInUse(order.getInUse().code());
			}
			
			long totalCount = sysDbBackupConfigDAO.findByConditionCount(configDO);
			PageComponent component = new PageComponent(order, totalCount);
			
			List<SysDbBackupConfigDO> list = sysDbBackupConfigDAO.findByCondition(configDO,
				order.getSortCol(), order.getSortOrder(), component.getFirstRecord(),
				component.getPageSize());
			
			List<SysDbBackupConfigInfo> pageList = new ArrayList<SysDbBackupConfigInfo>(list.size());
			for (SysDbBackupConfigDO item : list) {
				SysDbBackupConfigInfo info = new SysDbBackupConfigInfo();
				BeanCopier.staticCopy(item, info);
				info.setInUse(BooleanEnum.getByCode(item.getInUse()));
				info.setDbPsw(ConfigTools.decrypt(item.getDbPsw()));
				pageList.add(info);
			}
			
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询数据库配置失败" + e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setInsuranceResultEnum(InsuranceResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
	
	@Override
	public InsuranceBaseResult delBackupFile(long logId) {
		InsuranceBaseResult result = createResult();
		try {
			SysDbBackupLogDO logDO = sysDbBackupLogDAO.findById(logId);
			if (logDO != null) {
				delBackupFile(logDO);
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("删除备份文件出错");
		}
		return result;
	}
	
	private boolean isWindows() {
		String osName = System.getProperty("os.name");
		if (osName.indexOf("Windows") != -1 || osName.indexOf("windows") != -1)
			return true;
		return false;
	}
	
	private void delBackupFile(SysDbBackupLogDO logDO) {
		
		if (BooleanEnum.NO.code().equals(logDO.getIsDel())
			&& BooleanEnum.IS.code().equals(logDO.getIsSuccess())) {
			
			Runtime run = Runtime.getRuntime();
			BufferedInputStream in = null;
			BufferedReader inBr = null;
			try {
				
				String[] cmd = null;
				if (isWindows()) {//windows
					String backFile = logDO.getBackupFile().replaceAll("/", "\\\\");
					cmd = new String[] { "cmd", "/c", "del " + backFile };
				} else { //linux
					String backFile = logDO.getBackupFile().replaceAll("\\\\", "/");
					cmd = new String[] { "sh", "-c", "rm -rf " + backFile };
				}
				
				Process p = run.exec(cmd);// 启动另一个进程来执行命令  
				
				in = new BufferedInputStream(p.getInputStream());
				inBr = new BufferedReader(new InputStreamReader(in));
				
				//获得命令执行后在控制台的输出信息  
				String lineStr;
				String exeResult = "";
				while ((lineStr = inBr.readLine()) != null) {
					exeResult += lineStr + lineSeparator;
				}
				//执行结果
				logger.info("删除备份文件 {}", "", exeResult);
				
				//检查命令是否执行失败。  
				if (p.waitFor() != 0) {
					//p.exitValue()==0表示正常结束，1：非正常结束  
					if (p.exitValue() == 1) {
						logger.info("{}", "", exeResult);
					}
				}
				
				logDO.setDelTime(getSysdate());
				logDO.setIsDel(BooleanEnum.IS.code());
				
				sysDbBackupLogDAO.update(logDO);
				
			} catch (Exception e) {
				logger.error("{}", e);
			} finally {
				try {
					if (inBr != null)
						inBr.close();
					if (in != null)
						in.close();
				} catch (Exception e2) {
				}
			}
		}
	}
	
	@Override
	public QueryBaseBatchResult<SysDbBackupLogInfo> queryLog(SysDbBackupLogQueryOrder order) {
		
		QueryBaseBatchResult<SysDbBackupLogInfo> batchResult = new QueryBaseBatchResult<SysDbBackupLogInfo>();
		
		try {
			
			SysDbBackupLogDO logDO = new SysDbBackupLogDO();
			BeanCopier.staticCopy(order, logDO);
			
			if (StringUtil.isBlank(order.getSortCol())) {
				order.setSortCol("log_id");
				order.setSortOrder("desc");
			}
			
			long totalCount = sysDbBackupLogDAO.findByConditionCount(logDO);
			PageComponent component = new PageComponent(order, totalCount);
			
			List<SysDbBackupLogDO> list = sysDbBackupLogDAO.findByCondition(logDO,
				order.getSortCol(), order.getSortOrder(), component.getFirstRecord(),
				component.getPageSize());
			
			List<SysDbBackupLogInfo> pageList = new ArrayList<SysDbBackupLogInfo>(list.size());
			for (SysDbBackupLogDO item : list) {
				SysDbBackupLogInfo info = new SysDbBackupLogInfo();
				BeanCopier.staticCopy(item, info);
				info.setIsDel(BooleanEnum.getByCode(item.getIsDel()));
				info.setIsSuccess(BooleanEnum.getByCode(item.getIsSuccess()));
				pageList.add(info);
			}
			
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询数据库配置失败" + e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setInsuranceResultEnum(InsuranceResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
	
	@Override
	public InsuranceBaseResult runBackupByConfig(SysDbBackupConfigInfo config, boolean isManual) {
		InsuranceBaseResult result = createResult();
		try {
			if (config != null) {
				//当前时间
				Date now = getSysdate();
				
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd_HHmm");
				
				//是否在备份时间范围内
				Time nowTime = new Time(sdf.parse(sdf.format(now)).getTime());
				Time beginTime = new Time(sdf.parse(config.getBackupTimeBegin()).getTime());
				Time endTime = new Time(sdf.parse(config.getBackupTimeEnd()).getTime());
				
				int compare2Begin = nowTime.compareTo(beginTime);
				int compare2End = nowTime.compareTo(endTime);
				
				//手动备份或者到达时间范围了备份
				if (isManual || (compare2Begin == 1 || compare2Begin == 0)
					&& (compare2End == -1 || compare2End == 0)) {
					
					String[] schemeNames = config.getSchemeName().split(",");
					
					for (String schemeName : schemeNames) {
						
						boolean canBackup = false;
						
						if (!isManual) { //非手动备份才去检查时间
						
							//上次备份时间
							Date lastBackUpTime = lastBackUpTimeMap.get(schemeName
																		+ config.getDbHost());
							
							if (lastBackUpTime == null) {
								//查询上次备份时间
								SysDbBackupLogDO queryDO = new SysDbBackupLogDO();
								queryDO.setSchemeName(schemeName);
								queryDO.setDbHost(config.getDbHost());
								List<SysDbBackupLogDO> logs = sysDbBackupLogDAO.findByCondition(
									queryDO, "log_id", "desc", 0, 1);
								if (ListUtil.isNotEmpty(logs)) {
									lastBackUpTime = logs.get(0).getBackupTime();
								}
							}
							
							if (lastBackUpTime == null) {
								canBackup = true;
							} else {
								Calendar backupTime = Calendar.getInstance();
								backupTime.setTime(lastBackUpTime);
								backupTime.add(Calendar.MINUTE, config.getBackupInvervalMinute());
								int compare2Now = backupTime.getTime().compareTo(now);
								if (compare2Now == -1 || compare2Now == 0) {
									canBackup = true;
								}
							}
						}
						
						//执行备份
						if (isManual || canBackup) {
							
							boolean isWindows = isWindows();
							
							//执行命令
							StringBuffer cmd = new StringBuffer();
							
							String backupFolder = config.getBackupFolder();
							if (backupFolder != null)
								backupFolder.replaceAll("\\\\", "/");
							if (backupFolder == null || !backupFolder.endsWith("/")) {
								backupFolder += "/";
							}
							
							File folder = new File(backupFolder);
							if (!folder.exists()) {
								folder.mkdirs();
							}
							
							//备份文件名称
							String fileName = backupFolder + config.getSchemeName() + "@"
												+ config.getDbHost() + "_" + sdf1.format(now)
												+ (isManual ? "_manual" : "") + ".sql";
							
							if (StringUtil.isNotBlank(config.getDbHome())) {
								if (config.getDbHome().endsWith("/")) {
									cmd.append(config.getDbHome()).append("bin/");
								} else {
									cmd.append(config.getDbHome()).append("/bin/");
								}
							}
							
							cmd.append("mysqldump -u").append(config.getDbUser()).append(" -p")
								.append(config.getDbPsw()).append(" -h").append(config.getDbHost())
								.append(" -P").append(config.getDbPort()).append(" -R ")
								.append(schemeName).append(" > ").append(fileName);
							
							logger.info("开始执行数据库备份 {},{}", new Date(), cmd);
							
							Runtime run = Runtime.getRuntime();
							BufferedInputStream in = null;
							BufferedReader inBr = null;
							try {
								
								String[] cmds = null;
								if (isWindows) {
									cmds = new String[] { "cmd", "/c", cmd.toString() };
								} else {
									cmds = new String[] { "sh", "-c", cmd.toString() };
								}
								
								Process p = run.exec(cmds);// 启动另一个进程来执行命令  
								in = new BufferedInputStream(p.getInputStream());
								inBr = new BufferedReader(new InputStreamReader(in));
								
								//获得命令执行后在控制台的输出信息  
								String lineStr;
								String exeResult = null;
								while ((lineStr = inBr.readLine()) != null) {
									exeResult += lineStr + lineSeparator;
								}
								
								//备份日志
								SysDbBackupLogDO logDO = new SysDbBackupLogDO();
								logDO.setBackupFile(fileName);
								logDO.setBackupTime(now);
								logDO.setIsDel(BooleanEnum.NO.code());
								logDO.setRawAddTime(now);
								logDO.setSchemeName(schemeName);
								logDO.setDbHost(config.getDbHost());
								if (StringUtil.isEmpty(exeResult)) {
									exeResult = "success";
								}
								logDO.setRemark(exeResult);
								
								//检查命令是否执行失败。  
								if (p.waitFor() != 0) {
									//p.exitValue()==0表示正常结束，1：非正常结束  
									if (p.exitValue() == 1) {
										logDO.setIsSuccess(BooleanEnum.NO.code());
									} else {
										logDO.setIsSuccess(BooleanEnum.IS.code());
									}
								} else {
									logDO.setIsSuccess(BooleanEnum.IS.code());
								}
								
								//保存日志
								sysDbBackupLogDAO.insert(logDO);
								
								//记录上次
								lastBackUpTimeMap.put(schemeName + config.getDbHost(),
									logDO.getBackupTime());
								
								//删掉历史备份数据
								SysDbBackupLogDO queryDO = new SysDbBackupLogDO();
								queryDO.setSchemeName(schemeName);
								queryDO.setDbHost(config.getDbHost());
								List<SysDbBackupLogDO> logs = sysDbBackupLogDAO.findByCondition(
									queryDO, "log_id", "desc", 0, 6);
								if (ListUtil.isNotEmpty(logs)) {
									int count = 0;
									for (SysDbBackupLogDO log : logs) {
										count++;
										if (count == 6) { //只保留最近5条记录
											delBackupFile(log);
										}
									}
								}
								
							} catch (Exception e) {
								logger.error("{}", e);
							} finally {
								try {
									if (inBr != null)
										inBr.close();
									if (in != null)
										in.close();
								} catch (Exception e2) {
								}
							}
							
							logger.info("数据库备份完成 {} {}", new Date(), fileName);
						}
					}
				}
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("执行数据库备份出错");
			logger.error("执行数据库备份出错");
		}
		return result;
	}
	
	@Override
	public InsuranceBaseResult runBackupByConfigId(long configId) {
		InsuranceBaseResult result = createResult();
		try {
			SysDbBackupConfigQueryOrder order = new SysDbBackupConfigQueryOrder();
			order.setConfigId(configId);
			QueryBaseBatchResult<SysDbBackupConfigInfo> configs = queryConfig(order);
			if (configs == null || configs.getTotalCount() == 0) {
				result.setSuccess(false);
				result.setMessage("配置不存在");
			} else {
				result = runBackupByConfig(configs.getPageList().get(0), true);
			}
		} catch (Exception e) {
			logger.error("手动备份出错{}", e);
		}
		return result;
	}
	
	@Override
	public InsuranceBaseResult runBackup() {
		InsuranceBaseResult result = createResult();
		try {
			
			if (this.configs == null) {
				SysDbBackupConfigQueryOrder order = new SysDbBackupConfigQueryOrder();
				order.setPageSize(999);
				order.setInUse(BooleanEnum.YES);
				QueryBaseBatchResult<SysDbBackupConfigInfo> configs = queryConfig(order);
				if (configs != null && configs.isSuccess()) {
					this.configs = configs.getPageList();
				}
			}
			
			if (ListUtil.isNotEmpty(configs)) {
				for (SysDbBackupConfigInfo config : configs) {
					result = runBackupByConfig(config, false);
				}
			}
			
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("执行数据库备份出错");
			logger.error("执行数据库备份出错");
		}
		
		return result;
	}
	
}
