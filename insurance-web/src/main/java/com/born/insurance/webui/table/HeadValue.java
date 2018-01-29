package com.born.insurance.webui.table;

import java.util.ArrayList;
import java.util.List;

public class HeadValue {
	
	private final List headDataList = new ArrayList(3);
	int hash = 0;
	
	public HeadValue() {
		
	}
	
	public void addValue(Object o) {
		headDataList.add(o);
	}
	
	@Override
	public boolean equals(Object obj) {
		try {
			HeadValue hValue = (HeadValue) obj;
			if (hValue.headDataList.size() == headDataList.size()) {
				for (int i = 0; i < headDataList.size(); i++) {
					if (!headDataList.get(i).equals(hValue.headDataList.get(i))) {
						return false;
					}
				}
				return true;
			}
			return false;
		} catch (Exception e) {
			
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		if (hash == 0) {
			for (int i = 0; i < headDataList.size(); i++) {
				Object o = headDataList.get(i);
				if (o == null) {
					o = "";
				}
				hash += o.hashCode();
			}
		}
		return hash;
	}
	
	@Override
	public String toString() {
		String value = "";
		value = "[";
		for (int i = 0; i < headDataList.size(); i++) {
			value += String.valueOf(headDataList.get(i)) + ",";
		}
		value += "]";
		return value;
	}
	
}
