package com.anaadihsoft.common.DTO;

import java.util.Date;

import com.anaadihsoft.common.master.PaymentWalletTransaction;



public class PaymentWalletTransactionDTO {

	private long id;
	
	private String sender;
	
	private long senderId;
	
	private String reciever;
	
	private String recieverId;
		
	private double amount;
	
	private String type;
	
	private String status; 
		
	private String orderCode;
	
	private long orderId;
	

	private Date createdDate;


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getSender() {
		return sender;
	}


	public void setSender(String sender) {
		this.sender = sender;
	}


	public long getSenderId() {
		return senderId;
	}


	public void setSenderId(long senderId) {
		this.senderId = senderId;
	}


	public String getReciever() {
		return reciever;
	}


	public void setReciever(String reciever) {
		this.reciever = reciever;
	}


	


	public String getRecieverId() {
		return recieverId;
	}


	public void setRecieverId(String recieverId) {
		this.recieverId = recieverId;
	}


	public double getAmount() {
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getOrderCode() {
		return orderCode;
	}


	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}


	public long getOrderId() {
		return orderId;
	}


	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}


	public Date getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	

	public PaymentWalletTransactionDTO()
	{
		
	}

	public PaymentWalletTransactionDTO(PaymentWalletTransaction pwt)
	{
		this.amount=pwt.getAmount();
		this.createdDate=pwt.getCreatedDate();
		this.id=pwt.getId();
		this.orderCode=pwt.getOrder().getOrderCode();
		this.orderId=pwt.getOrder().getId();
		this.reciever=pwt.getRecieverDetails().getName();
		this.recieverId=pwt.getReciever();
		this.sender=pwt.getSender().getName();
		this.senderId=pwt.getSender().getId();
		this.status=pwt.getStatus();
		this.type=pwt.getType();
	}
}
