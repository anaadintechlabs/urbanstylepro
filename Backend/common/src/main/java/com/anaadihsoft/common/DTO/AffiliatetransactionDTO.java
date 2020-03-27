package com.anaadihsoft.common.DTO;

import java.util.Date;

public class AffiliatetransactionDTO {

	private long affiliateId;
	
	private String affUserDesc;
	
	private long varid ;
	
	private String prodDesc;
	
	private long orderprodId;
	
	private String status;
	
	private double qty;
	
	private long custid;
	
	private Date orderdate;
	
	private double commision;
	
	private double amount;

	public long getAffiliateId() {
		return affiliateId;
	}

	public void setAffiliateId(long affiliateId) {
		this.affiliateId = affiliateId;
	}

	public String getAffUserDesc() {
		return affUserDesc;
	}

	public void setAffUserDesc(String affUserDesc) {
		this.affUserDesc = affUserDesc;
	}

	public long getVarid() {
		return varid;
	}

	public void setVarid(long varid) {
		this.varid = varid;
	}

	public String getProdDesc() {
		return prodDesc;
	}

	public void setProdDesc(String prodDesc) {
		this.prodDesc = prodDesc;
	}

	public long getOrderprodId() {
		return orderprodId;
	}

	public void setOrderprodId(long orderprodId) {
		this.orderprodId = orderprodId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getQty() {
		return qty;
	}

	public void setQty(double qty) {
		this.qty = qty;
	}

	public long getCustid() {
		return custid;
	}

	public void setCustid(long custid) {
		this.custid = custid;
	}

	public Date getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}

	public double getCommision() {
		return commision;
	}

	public void setCommision(double commision) {
		this.commision = commision;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
}
