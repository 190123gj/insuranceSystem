/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.insurance.biz.service.common;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.born.insurance.daointerface.ExtraDAO;
import com.born.insurance.util.DateUtil;
import com.born.insurance.ws.enums.SysDateSeqNameEnum;
import com.yjf.common.lang.util.StringUtil;

/**
 * 
 * @Filename DateSeqServicesImpl.java
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
@Service("dateSeqService")
public class DateSeqServiceImpl implements DateSeqService {
	@Autowired
	protected ExtraDAO extraDAO;
	static Map<String, SeqItem> exsitSeqMap = new HashMap<String, SeqItem>();
	
	/** 事务模板 */
	@Autowired
	protected TransactionTemplate transactionTemplateRequiresNew;
	
	@Override
	public String getNextDateSeq(String seqName, String prefix, int length) {
		if (length > 100)
			length = 100;
		if (prefix == null)
			prefix = "";
		Date date = new Date();
		String dateString = DateUtil.getFormat(com.yjf.common.lang.util.DateUtil.dtShort).format(
			date);
		StringBuffer sb = new StringBuffer();
		SeqItem seqItem = exsitSeqMap.get(seqName);
		if (seqItem != null) {
			if (StringUtil.equals(dateString, seqItem.dateString)) {
				return getNextSeqString(seqName, prefix, length, date, dateString, sb);
			}
		}
		synchronized (exsitSeqMap) {
			exsitSeqMap.remove(seqName);
			SeqItem newSeqItem = new SeqItem();
			newSeqItem.seqName = seqName;
			newSeqItem.dateString = dateString;
			boolean isExsit = extraDAO.selectDateSeq(newSeqItem.seqName, date);
			if (isExsit) {
				exsitSeqMap.put(seqName, newSeqItem);
				return getNextSeqString(seqName, prefix, length, date, dateString, sb);
			} else {
				long value = extraDAO.insertDateSeq(seqName, date);
				exsitSeqMap.put(seqName, newSeqItem);
				return makeSeqString(prefix, length, dateString, sb, value);
			}
		}
	}
	
	@Override
	public String getNextYearSeq(String seqName, String prefix, int length) {
		if (length > 100)
			length = 100;
		if (prefix == null)
			prefix = "";
		Date date = new Date();
		String dateString = DateUtil.getFormat("yyyy").format(date);
		StringBuffer sb = new StringBuffer();
		SeqItem seqItem = exsitSeqMap.get(seqName);
		if (seqItem != null) {
			if (StringUtil.equals(dateString, seqItem.dateString)) {
				return getNextYearSeqString(seqName, prefix, length, date, dateString, sb);
			}
		}
		synchronized (exsitSeqMap) {
			exsitSeqMap.remove(seqName);
			SeqItem newSeqItem = new SeqItem();
			newSeqItem.seqName = seqName;
			newSeqItem.dateString = dateString;
			boolean isExsit = extraDAO.selectYearSeq(newSeqItem.seqName, date);
			if (isExsit) {
				exsitSeqMap.put(seqName, newSeqItem);
				return getNextYearSeqString(seqName, prefix, length, date, dateString, sb);
			} else {
				long value = extraDAO.insertYearSeq(seqName, date);
				exsitSeqMap.put(seqName, newSeqItem);
				return makeSeqString(prefix, length, dateString, sb, value);
			}
		}
	}
	
	@Override
	public String getNextYearMonthSeq(String seqName, String prefix, int length) {
		if (length > 100)
			length = 100;
		if (prefix == null)
			prefix = "";
		Date date = new Date();
		String dateString = DateUtil.getFormat("yyyy.MM").format(date);
		StringBuffer sb = new StringBuffer();
		SeqItem seqItem = exsitSeqMap.get(seqName);
		if (seqItem != null) {
			if (StringUtil.equals(dateString, seqItem.dateString)) {
				return getNextYearMonthSeqString(seqName, prefix, length, date, dateString, sb);
			}
		}
		synchronized (exsitSeqMap) {
			exsitSeqMap.remove(seqName);
			SeqItem newSeqItem = new SeqItem();
			newSeqItem.seqName = seqName;
			newSeqItem.dateString = dateString;
			boolean isExsit = extraDAO.selectYearMonthSeq(newSeqItem.seqName, date);
			if (isExsit) {
				exsitSeqMap.put(seqName, newSeqItem);
				return getNextYearMonthSeqString(seqName, prefix, length, date, dateString, sb);
			} else {
				long value = extraDAO.insertYearMonthSeq(seqName, date);
				exsitSeqMap.put(seqName, newSeqItem);
				return makeSeqString(prefix, length, dateString, sb, value);
			}
		}
	}
	
	@Override
	public long getNextSeqNumber(String seqName) {
		return getNextSeqNumberWithCache(seqName, 50);
	}
	
	@Override
	public long getNextSeqNumberWithCache(String seqName, long cacheNumber) {
		Calendar cal = Calendar.getInstance();
		cal.set(2012, 1, 1, 0, 0, 0);
		Date date = cal.getTime();
		
		SeqItem seqItem = exsitSeqMap.get(seqName);
		if (seqItem != null) {
			long nextIndex = seqItem.getNextIndex();
			if (nextIndex > 0)
				return nextIndex;
			synchronized (seqItem) {
				nextIndex = seqItem.getNextIndex();
				if (nextIndex > 0)
					return nextIndex;
				return getNextSeqLong(seqName, seqItem, date, cacheNumber);
			}
			
		}
		synchronized (exsitSeqMap) {
			exsitSeqMap.remove(seqName);
			SeqItem newSeqItem = new SeqItem();
			newSeqItem.seqName = seqName;
			boolean isExsit = extraDAO.selectDateSeq(seqName, date);
			if (isExsit) {
				exsitSeqMap.put(seqName, newSeqItem);
				return getNextSeqLong(seqName, newSeqItem, date, cacheNumber);
			} else {
				return insertSeq(seqName, newSeqItem, date);
			}
		}
	}
	
	@Override
	public long getNextSeqNumberWithoutCache(String seqName, boolean transactionRequireNew) {
		Calendar cal = Calendar.getInstance();
		cal.set(2012, 1, 1, 0, 0, 0);
		Date date = cal.getTime();
		boolean isExsit = extraDAO.selectDateSeq(seqName, date);
		if (isExsit) {
			return getNextSeqLong(seqName, date, 1, transactionRequireNew);
		} else {
			return insertSeq(seqName, date, transactionRequireNew);
		}
	}
	
	/**
	 * @param seqName
	 * @param prefix
	 * @param length
	 * @param date
	 * @param dateString
	 * @param sb
	 * @param transactionRequiresNew
	 * @return
	 */
	protected String getNextSeqString(final String seqName, final String prefix, final int length,
										final Date date, final String dateString,
										final StringBuffer sb) {
		return transactionTemplateRequiresNew.execute(new TransactionCallback<String>() {
			@Override
			public String doInTransaction(TransactionStatus status) {
				long value = extraDAO.getNextDateSeq(seqName, date, 1);
				return makeSeqString(prefix, length, dateString, sb, value);
			}
		});
	}
	
	/**
	 * @param seqName
	 * @param prefix
	 * @param length
	 * @param date
	 * @param dateString
	 * @param sb
	 * @param transactionRequiresNew
	 * @return
	 */
	protected String getNextYearSeqString(final String seqName, final String prefix,
											final int length, final Date date,
											final String dateString, final StringBuffer sb) {
		return transactionTemplateRequiresNew.execute(new TransactionCallback<String>() {
			@Override
			public String doInTransaction(TransactionStatus status) {
				long value = extraDAO.getNextYearSeq(seqName, date, 1);
				return makeSeqString(prefix, length, dateString, sb, value);
			}
		});
	}
	
	/**
	 * @param seqName
	 * @param prefix
	 * @param length
	 * @param date
	 * @param dateString
	 * @param sb
	 * @param transactionRequiresNew
	 * @return
	 */
	protected String getNextYearMonthSeqString(final String seqName, final String prefix,
												final int length, final Date date,
												final String dateString, final StringBuffer sb) {
		return transactionTemplateRequiresNew.execute(new TransactionCallback<String>() {
			@Override
			public String doInTransaction(TransactionStatus status) {
				long value = extraDAO.getNextYearMonthSeq(seqName, date, 1);
				return makeSeqString(prefix, length, dateString, sb, value);
			}
		});
	}
	
	protected long getNextSeqLong(final String seqName, final SeqItem seqItem, final Date date,
									final long cacheNumber) {
		return transactionTemplateRequiresNew.execute(new TransactionCallback<Long>() {
			@Override
			public Long doInTransaction(TransactionStatus status) {
				
				long value = extraDAO.getNextDateSeq(seqName, date, cacheNumber);
				seqItem.currentIndex = value - cacheNumber + 1;
				seqItem.maxIndex = value;
				return seqItem.getNextIndex();
			}
		});
	}
	
	protected long getNextSeqLong(final String seqName, final Date date, final long cacheNumber,
									boolean transactionRequiresNew) {
		
		if (!transactionRequiresNew) {
			long value = extraDAO.getNextDateSeq(seqName, date, cacheNumber);
			return value;
		}
		
		return transactionTemplateRequiresNew.execute(new TransactionCallback<Long>() {
			@Override
			public Long doInTransaction(TransactionStatus status) {
				long value = extraDAO.getNextDateSeq(seqName, date, cacheNumber);
				return value;
			}
		});
	}
	
	protected long insertSeq(final String seqName, final SeqItem newSeqItem, final Date date) {
		return transactionTemplateRequiresNew.execute(new TransactionCallback<Long>() {
			@Override
			public Long doInTransaction(TransactionStatus status) {
				long value = extraDAO.insertDateSeq(seqName, date);
				newSeqItem.currentIndex = value;
				newSeqItem.maxIndex = value;
				exsitSeqMap.put(seqName, newSeqItem);
				return newSeqItem.getNextIndex();
			}
		});
	}
	
	protected long insertSeq(final String seqName, final Date date,
								final boolean transactionRequiresNew) {
		if (!transactionRequiresNew) {
			long value = extraDAO.insertDateSeq(seqName, date);
			return value;
		}
		return transactionTemplateRequiresNew.execute(new TransactionCallback<Long>() {
			@Override
			public Long doInTransaction(TransactionStatus status) {
				long value = extraDAO.insertDateSeq(seqName, date);
				return value;
			}
		});
	}
	
	/**
	 * @param prefix
	 * @param length
	 * @param dateString
	 * @param sb
	 * @param value
	 * @return
	 */
	protected String makeSeqString(String prefix, int length, String dateString, StringBuffer sb,
									long value) {
		int numberOfDigits = length - dateString.length() - prefix.length();
		String seq = String.valueOf(value);
		sb.append(prefix);
		sb.append(dateString);
		while (seq.length() < numberOfDigits) {
			seq = "0" + seq;
		}
		sb.append(seq);
		return sb.toString();
	}
	
	private static class SeqItem {
		public String seqName = "";
		public String dateString = "";
		public long currentIndex = 0;
		public long maxIndex = 10;
		
		public long getNextIndex() {
			long index = currentIndex++;
			if (index <= maxIndex)
				return index;
			else
				return -1;
		}
	}
	

	


	
}
