package com.born.insurance.web.util;

import com.born.insurance.page.Page;
import com.born.insurance.ws.result.base.QueryBaseBatchResult;

import java.util.ArrayList;
import java.util.List;


public class PageUtil {
	public static <T> Page<T> getCovertPage(QueryBaseBatchResult<T> batchResult) {
		long start = (batchResult.getPageNumber() - 1) * batchResult.getPageSize();
		Page<T> newPage = new Page<T>(batchResult.getSortCol(), batchResult.getSortOrder(), start,
			batchResult.getTotalCount(), (int) batchResult.getPageSize(), batchResult.getPageList());
		return newPage;
	}
	
	public static <T> Page<T> getCovertPageAndResult(QueryBaseBatchResult<T> batchResult) {
		List<T> listOld = batchResult.getPageList();
		long pageNum = batchResult.getPageNumber();
		long pageSize = batchResult.getPageSize();
		long totalCount = batchResult.getTotalCount();
		List<T> listNew = new ArrayList<T>();
		long start = (pageNum - 1) * pageSize;
		if (pageNum == 1) {
			if (pageSize >= totalCount) {
				listNew = listOld;
			} else {
				for (int i = 0; i < pageSize; i++) {
					listNew.add(listOld.get(i));
				}
			}
		} else {
			if (pageSize < totalCount) {
				if (start < totalCount) {
					int end = (int) ((start + pageSize) > totalCount ? totalCount
						: (start + pageSize));
					for (int i = (int) start; i < end; i++) {
						listNew.add(listOld.get(i));
					}
				}
			}
		}
		Page<T> newPage = new Page<T>(batchResult.getSortCol(), batchResult.getSortOrder(), start,
			totalCount, (int) pageSize, listNew);
		return newPage;
	}
}
