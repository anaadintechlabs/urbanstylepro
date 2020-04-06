package com.anaadihsoft.common.DTO;

import java.util.Date;

import com.anaadihsoft.common.master.UserOrderProducts;

public class OrderUiListingDTO {

	private long orderId;
	
	private Date orderPlacedDate;


	//USER ORDER PRODUCT
	private long orderProductId;
	
	private String status;

	
	private double quantity;
	
	private double orderProductPrice;
	
	private String orderCode;

	 private long userId;
	 
	 private String userName;
	 
	 
	 private long productVariantId;
		
		
	 private String variantName;
		 
	 private String variantCode;
		 
	 private String mainImageUrl;
	 
	 
	 public OrderUiListingDTO()
	 {
		 
	 }
	 
	 
	 public OrderUiListingDTO(UserOrderProducts uop)
	 {
		 this.orderId=uop.getUserOrder().getId();
		 this.orderCode=uop.getOrderCode();
		 this.orderProductId=uop.getId();
		 this.orderProductPrice=uop.getOrderProductPrice();
		 this.quantity=uop.getQuantity();
		 this.status=uop.getStatus();
		 this.orderPlacedDate=uop.getUserOrder().getOrderPlacedDate();
		 this.productVariantId=uop.getProduct().getProductVariantId();
		 this.variantName=uop.getProduct().getVariantName();
		 this.variantCode=uop.getProduct().getVariantCode();
		 this.mainImageUrl=uop.getProduct().getMainImageUrl();
		 this.userId=uop.getUserOrder().getUser().getId();
		 this.userName=uop.getUserOrder().getUser().getName();
	 }


	public long getOrderId() {
		return orderId;
	}


	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}


	public Date getOrderPlacedDate() {
		return orderPlacedDate;
	}


	public void setOrderPlacedDate(Date orderPlacedDate) {
		this.orderPlacedDate = orderPlacedDate;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public long getOrderProductId() {
		return orderProductId;
	}


	public void setOrderProductId(long orderProductId) {
		this.orderProductId = orderProductId;
	}


	public double getQuantity() {
		return quantity;
	}


	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}


	public double getOrderProductPrice() {
		return orderProductPrice;
	}


	public void setOrderProductPrice(double orderProductPrice) {
		this.orderProductPrice = orderProductPrice;
	}


	public String getOrderCode() {
		return orderCode;
	}


	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}


	public long getUserId() {
		return userId;
	}


	public void setUserId(long userId) {
		this.userId = userId;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public long getProductVariantId() {
		return productVariantId;
	}


	public void setProductVariantId(long productVariantId) {
		this.productVariantId = productVariantId;
	}


	public String getVariantName() {
		return variantName;
	}


	public void setVariantName(String variantName) {
		this.variantName = variantName;
	}


	public String getVariantCode() {
		return variantCode;
	}


	public void setVariantCode(String variantCode) {
		this.variantCode = variantCode;
	}


	public String getMainImageUrl() {
		return mainImageUrl;
	}


	public void setMainImageUrl(String mainImageUrl) {
		this.mainImageUrl = mainImageUrl;
	}
	 
	
	
	 
	 
}
