package com.anaadihsoft.common.master;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
	
	@ManyToOne
	@JoinColumn(name="vendor_Id",nullable = false)
	private User vendor;
	
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