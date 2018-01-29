package com.born.insurance.integration.bpm.comparator;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.born.insurance.integration.bpm.info.menu.MenuInfo;
import com.google.common.collect.Lists;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

public class MenuComparator<T> implements Comparator<T> {
	protected static final Logger logger = LoggerFactory.getLogger(MenuComparator.class);
	
	@Override
	public int compare(T o1, T o2) {
		MenuInfo info1 = (MenuInfo) o1;
		MenuInfo info2 = (MenuInfo) o2;
		logger.info(" 比较" + (info1.getRank() - info2.getRank()));
		return (int) (info1.getRank() - info2.getRank());
	}
	
	public static void main(String[] args) {
		List<MenuInfo> menuInfos = Lists.newArrayList();
		MenuInfo menuInfo = new MenuInfo();
		menuInfo.setRank(2);
		menuInfos.add(menuInfo);
		MenuInfo menuInfo1 = new MenuInfo();
		menuInfo1.setRank(1);
		menuInfos.add(menuInfo1);
		System.out.println(menuInfos);
		Collections.sort(menuInfos, new MenuComparator<MenuInfo>());
		System.out.println(menuInfos);
	}
}
