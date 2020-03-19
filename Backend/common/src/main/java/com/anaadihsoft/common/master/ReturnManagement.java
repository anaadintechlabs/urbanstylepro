package com.anaadihsoft.common.master;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Entity
public class ReturnManagement {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long returnId;
	
	@OneToOne
	private UserOrder order; 
	
	@OneToOne
	private UserOrderProducts orderProduct;
	
	@ManyToOne
	private User user;
	
	private String reason;
	
	private String status;
	
	private Date returnAuthorizeDate;
	
	private Date unitRecieveDate;
	
	private Date customerRefundDate;
	
	
	private Date createdDate;
	private String createdBy;
	
	public long getReturnId() {
		return returnId;
	}

	public void setReturnId(long returnId) {
		this.returnId = returnId;
	}

	
	public Date getUnitRecieveDate() {
		return unitRecieveDate;
	}

	public void setUnitRecieveDate(Date unitRecieveDate) {
		this.unitRecieveDate = unitRecieveDate;
	}

	

	public UserOrder getOrder() {
		return order;
	}

	public void setOrder(UserOrder order) {
		this.order = order;
	}



	public UserOrderProducts getOrderProduct() {
		return orderProduct;
	}

	public void setOrderProduct(UserOrderProducts orderProduct) {
		this.orderProduct = orderProduct;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	
	public Date getReturnAuthorizeDate() {
		return returnAuthorizeDate;
	}

	public void setReturnAuthorizeDate(Date returnAuthorizeDate) {
		this.returnAuthorizeDate = returnAuthorizeDate;
	}

	public Date getCustomerRefundDate() {
		return customerRefundDate;
	}

	public void setCustomerRefundDate(Date customerRefundDate) {
		this.customerRefundDate = customerRefundDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	private Date modifiedDate;
	
	private String modifiedBy;
	
	@PrePersist
	private void setDate()
	{
		this.setCreatedDate(new Date());
	}
	@PreUpdate
	private void setModification()
	{
		this.setModifiedDate(new Date());
	}
}
