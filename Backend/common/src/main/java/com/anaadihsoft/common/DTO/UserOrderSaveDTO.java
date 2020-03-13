package com.anaadihsoft.common.DTO;

import java.util.List;

import com.anaadihsoft.common.master.Address;
import com.anaadihsoft.common.master.BankDetails;
import com.anaadihsoft.common.master.BankcardInfo;


public class UserOrderSaveDTO {

	private long userId;
	private List<UserOrderQtyDTO> userOrderList;
	private String paymentType;
	private Address address;
	private  String from;
	private  String to;
	
	private BankcardInfo bankCardDetails;
	
	private BankDetails bankInfo;
	
	public BankDetails getBankInfo() {
		return bankInfo;
	}
	public void setBankInfo(BankDetails bankInfo) {
		this.bankInfo = bankInfo;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public List<UserOrderQtyDTO> getUserOrderList() {
		return userOrderList;
	}
	public void setUserOrderList(List<UserOrderQtyDTO> userOrderList) {
		this.userOrderList = userOrderList;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public BankcardInfo getBankCardDetails() {
		return bankCardDetails;
	}
	public void setBankCardDetails(BankcardInfo bankCardDetails) {
		this.bankCardDetails = bankCardDetails;
	}
	
	
}
