package com.born.insurance.util;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.springframework.util.Assert;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yjf.common.lang.exception.Exceptions;

public class ConstraintUtil {
	private final ConcurrentMap<String, ConstraintUtil.Info> smsMap = Maps.newConcurrentMap();
	
	private final List<Checker> checkers = Lists.newArrayList();
	
	/**
	 * 上次清空缓存的时间 默认时间为对象初始化时当天的0时0分0秒
	 */
	private long lastClearTime = getCurrentDay();
	
	private final boolean isErrorCheck = false;
	/**
	 * 一天的毫秒数
	 */
	private static long MSID = 24 * 60 * 60 * 1000;
	/**
	 * 15分钟的毫秒数
	 */
	private static long ERROR_ID = 15 * 60 * 1000;
	
	public static Builder newBuilder() {
		
		return new Builder();
	}
	
	private void addChecker(Checker checker) {
		checkers.add(checker);
	}
	
	/**
	 * 
	 * @param key
	 */
	public void check(String key) {
		Assert.notNull(key, "key不能为空");
		
		long now = getNow();
		//检查是否已经过了一天，如果距离上次清空缓存过了一天，就清空缓存
		checkNeedClear(now);
		
		//因为仅仅是限制检查，我觉得这种锁的开销得不偿失，所以没有用锁，不要求很强的并发语义,即便是并发，影响也就让他多执行几次
		if (smsMap.containsKey(key)) {
			Info smsInfo = smsMap.get(key);
			smsInfo.now = Long.valueOf(now);
			for (Checker checker : checkers) {
				checker.check(smsInfo);
			}
			smsInfo.count++;
			smsInfo.lastSendTime = now;
			smsInfo.now = null;
		} else {
			smsMap.put(key, new Info(key, 1, now, null));
		}
	}
	
	public void clearKey(String key) {
		Assert.notNull(key, "key不能为空");
		smsMap.remove(key);
	}
	
	public void removeCount(String key) {
		Assert.notNull(key, "key不能为空");
		ConstraintUtil.Info info = smsMap.get(key);
		if(info!=null)
		{
			info.count--;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("ConstraintUtil [smsMap=");
		builder2.append(smsMap);
		builder2.append(", checkers=");
		builder2.append(checkers);
		builder2.append(", lastClearTime=");
		builder2.append(lastClearTime);
		builder2.append(", isErrorCheck=");
		builder2.append(isErrorCheck);
		builder2.append("]");
		return builder2.toString();
	}
	
	private void checkNeedClear(long now) {
		if (now - lastClearTime >= MSID) {
			smsMap.clear();
			lastClearTime = getCurrentDay();
		}
	}
	
	private static long getNow() {
		long now = System.currentTimeMillis();
		return now;
	}
	
	private static long getCurrentDay() {
		GregorianCalendar now = new GregorianCalendar();
		now.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE), 0, 0, 0);
		return now.getTimeInMillis();
	}
	
	public static class Builder {
		private final ConstraintUtil smsSendCheck = new ConstraintUtil();
		
		/**
		 * 限制次数
		 * @param count
		 * @return
		 */
		public Builder count(int count) {
			Assert.isTrue(count > 0, "限制次数必须大于0");
			smsSendCheck.addChecker(new CountChecker(count));
			return this;
		}
		
		public Builder count(int count, boolean isError) {
			Assert.isTrue(count > 0, "限制次数必须大于0");
			smsSendCheck.addChecker(new CountChecker(count, isError));
			return this;
		}
		
		/**
		 * 限制间隔，单位:秒
		 * @param intervalSecond
		 * @return
		 */
		public Builder interval(long intervalSecond) {
			Assert.isTrue(intervalSecond > 0, "限制间隔时间必须大于0");
			smsSendCheck.addChecker(new IntervalChecker(intervalSecond));
			return this;
		}
		
		public ConstraintUtil build() {
			return smsSendCheck;
		}
	}
	
	private static interface Checker {
		public void check(Info smsInfo);
	}
	
	private static class CountChecker implements Checker {
		
		private final int count;
		private final boolean isError;
		
		public CountChecker(int count, boolean isError) {
			this.count = count;
			this.isError = isError;
		}
		
		public CountChecker(int count) {
			this.count = count;
			this.isError = false;
		}
		
		@Override
		public void check(Info smsInfo) {
			if (this.isError) {
				long now = getNow();
				if (now - smsInfo.lastSendTime >= ERROR_ID) {
					smsInfo.count = 0;
				}
			}
			if (smsInfo.count + 1 > this.count) {
				throw Exceptions.newRuntimeException("超过次数限制");
			}
		}
	}
	
	private static class IntervalChecker implements Checker {
		
		private final long interval;
		
		public IntervalChecker(long interval) {
			this.interval = interval * 1000;
		}
		
		@Override
		public void check(Info smsInfo) {
			if (smsInfo.now.longValue() - smsInfo.lastSendTime < interval) {
				throw Exceptions.newRuntimeException("超过间隔限制");
			}
		}
	}
	
	private static class Info {
		@SuppressWarnings("unused")
		private final String key;
		private int count;
		private long lastSendTime;
		//为了减少调用getNow方法
		private Long now;
		
		public Info(String mobile, int count, long lastSendTime, Long now) {
			super();
			this.key = mobile;
			this.count = count;
			this.lastSendTime = lastSendTime;
			this.now = now;
		}
	}
	
}
