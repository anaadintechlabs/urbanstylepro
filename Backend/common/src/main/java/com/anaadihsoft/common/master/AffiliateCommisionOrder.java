package com.anaadihsoft.common.master;



import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Entity
public class AffiliateCommisionOrder {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	private User affiliateId;
	
	@ManyToOne
	private ProductVariant prodvarid;
	
	@ManyToOne
	private UserOrderProducts orderProd;
	
	@ManyToOne
	private User user; 
	
	private Date orderdate;
	
	private double commision;
	
	private String status;
	
	@ManyToOne
	private ReturnManagement returnId;
	
	private Date createdDate;
	
	private Date modifiedDate;
	
	
	@PrePersist()
	public void setDates()
	{
		this.createdDate= new Date();
	}
	
	@PreUpdate()
	public void setModifiedDate()
	{
		this.modifiedDate= new Date();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UserOrderProducts getOrderProd() {
		return orderProd;
	}

	public void setOrderProd(UserOrderProducts orderProd) {
		this.orderProd = orderProd;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public User getAffiliateId() {
		return affiliateId;
	}

	public void setAffiliateId(User affiliateId) {
		this.affiliateId = affiliateId;
	}

	public ProductVariant getProdvarid() {
		return prodvarid;
	}

	public void setProdvarid(ProductVariant prodvarid) {
		this.prodvarid = prodvarid;
	}

	public UserOrderProducts getOrderprodid() {
		return orderProd;
	}

	public void setOrderprodid(UserOrderProducts orderprodid) {
		this.orderProd = orderprodid;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ReturnManagement getReturnId() {
		return returnId;
	}

	public void setReturnId(ReturnManagement returnId) {
		this.returnId = returnId;
	}
	
}
