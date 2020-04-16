package com.anaadihsoft.common.DTO;

public class OrderTransactionSummaryDTO {

	private String senderuserCode;
	private long senderuserId;
	private String senderuserName;

	private String recieveruserCode;
	private long recieveruserId;
	private String recieveruserName;
	
	
	private String  reason;

	
	
	
	public String getReason() {
		return reason;
	}


	public void setReason(String reason) {
		this.reason = reason;
	}


	public String getSenderuserCode() {
		return senderuserCode;
	}


	public void setSenderuserCode(String senderuserCode) {
		this.senderuserCode = senderuserCode;
	}


	public long getSenderuserId() {
		return senderuserId;
	}


	public void setSenderuserId(long senderuserId) {
		this.senderuserId = senderuserId;
	}


	public String getSenderuserName() {
		return senderuserName;
	}


	public void setSenderuserName(String senderuserName) {
		this.senderuserName = senderuserName;
	}


	public String getRecieveruserCode() {
		return recieveruserCode;
	}


	public void setRecieveruserCode(String recieveruserCode) {
		this.recieveruserCode = recieveruserCode;
	}


	public long getRecieveruserId() {
		return recieveruserId;
	}


	public void setRecieveruserId(long recieveruserId) {
		this.recieveruserId = recieveruserId;
	}


	public String getRecieveruserName() {
		return recieveruserName;
	}


	public void setRecieveruserName(String recieveruserName) {
		this.recieveruserName = recieveruserName;
	}


	public double getAmount() {
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}


	private double amount;
}