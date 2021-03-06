
package com.anaadihsoft.common.master;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;

@Entity
public class UserOrderProducts {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	private UserOrder userOrder;
	
	@ManyToOne
	private ProductVariant product;
	
	private double quantity;
	
	private double orderProductPrice;
	
	private String comment;
	
	private String status;
	
	private String trackingId;
	
	private String trackingLink;
	
	@ManyToOne
	@JoinColumn(name="vendor_Id",nullable = false)
	private User vendor;
	
	
	@ManyToOne
	@JoinColumn(name="affiliate_id")
	private User affiliate;
	
	private Date createdDate;
	
	private String createdBy;
	
	private Date modifiedDate;
	
	private String modifiedBy;
	
	private String orderCode;
	
	private boolean affiliateCommisionExists;
	
	
	
	public User getAffiliate() {
		return affiliate;
	}
	public void setAffiliate(User affiliate) {
		this.affiliate = affiliate;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	
	@PrePersist
	public void setDate()
	{
		this.createdDate= new Date();
	}
	@PostPersist
	public void updateDate()
	{
		this.modifiedDate= new Date();
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	
	public boolean isAffiliateCommisionExists() {
		return affiliateCommisionExists;
	}
	public void setAffiliateCommisionExists(boolean affiliateCommisionExists) {
		this.affiliateCommisionExists = affiliateCommisionExists;
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

	
	public String getTrackingId() {
		return trackingId;
	}
	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}
	public String getTrackingLink() {
		return trackingLink;
	}
	public void setTrackingLink(String trackingLink) {
		this.trackingLink = trackingLink;
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public User getVendor() {
		return vendor;
	}

	public void setVendor(User vendor) {
		this.vendor = vendor;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	

	public double getOrderProductPrice() {
		return orderProductPrice;
	}

	public void setOrderProductPrice(double orderProductPrice) {
		this.orderProductPrice = orderProductPrice;
	}

	public UserOrder getUserOrder() {
		return userOrder;
	}

	public void setUserOrder(UserOrder userOrder) {
		this.userOrder = userOrder;
	}

	public ProductVariant getProduct() {
		return product;
	}

	public void setProduct(ProductVariant product) {
		this.product = product;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
}