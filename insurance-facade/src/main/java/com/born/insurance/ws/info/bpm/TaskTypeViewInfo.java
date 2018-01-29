package com.born.insurance.ws.info.bpm;

import java.io.Serializable;

public class TaskTypeViewInfo implements Serializable {
	private static final long serialVersionUID = -6322578796056866760L;
	String processName;
	String name;
	private int read = 0;
	
	private int total = 0;
	
	private int notRead = 0;
	
	public String getProcessName() {
		return this.processName;
	}
	
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getRead() {
		return this.read;
	}
	
	public void setRead(int read) {
		this.read = read;
	}
	
	public int getTotal() {
		return this.total;
	}
	
	public void setTotal(int total) {
		this.total = total;
	}
	
	public int getNotRead() {
		return this.notRead;
	}
	
	public void setNotRead(int notRead) {
		this.notRead = notRead;
	}
	
}
