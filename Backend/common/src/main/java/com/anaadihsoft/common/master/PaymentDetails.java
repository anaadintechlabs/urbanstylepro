package com.anaadihsoft.common.master;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class PaymentDetails {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name="userId", nullable=false,updatable=false)
	private User user;
	
	@ManyToOne
	@JoinColumn(name="vendorId", nullable=false,updatable=false)
	private User userVendor;

	@ManyToOne
	@JoinColumn(name="varId", nullable=false,updatable=false)
	private ProductVariant prodVar;

	@ManyToOne
	@JoinColumn(name="payId", nullable=false,updatable=false)
	private PaymentTransaction pt;
	
	private double amount;
	
	private Date createdDate;
	
	private String createdBy;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUserVendor() {
		return userVendor;
	}

	public void setUserVendor(User userVendor) {
		this.userVendor = userVendor;
	}

	public ProductVariant getProdVar() {
		return prodVar;
	}

	public void setProdVar(ProductVariant prodVar) {
		this.prodVar = prodVar;
	}

	public PaymentTransaction getPt() {
		return pt;
	}

	public void setPt(PaymentTransaction pt) {
		this.pt = pt;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
}
