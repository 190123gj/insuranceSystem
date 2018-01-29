package com.born.insurance.ws.order.common;

public class CancelFormOrder extends SimpleFormOrder {
	
	private static final long serialVersionUID = 6993906452812932421L;
	String voteContent;
	
	public String getVoteContent() {
		return this.voteContent;
	}
	
	public void setVoteContent(String voteContent) {
		this.voteContent = voteContent;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CancelFormOrder [voteContent=");
		builder.append(voteContent);
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	
}
