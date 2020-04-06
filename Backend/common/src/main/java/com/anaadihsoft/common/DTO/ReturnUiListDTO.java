package com.anaadihsoft.common.DTO;

import java.util.Date;

import com.anaadihsoft.common.master.ReturnManagement;

public class ReturnUiListDTO {

	private long returnId;

	private String reason;

	private String status;

	private Date returnAuthorizeDate;

	private Date unitRecieveDate;

	private Date customerRefundDate;

	private long orderId;

	private Date orderPlacedDate;

	// USER ORDER PRODUCT
	private long orderProductId;

	private double quantity;

	private double orderProductPrice;

	private String orderCode;

	private long userId;

	private String userName;

	private long productVariantId;

	private String variantName;

	private String variantCode;

	private String mainImageUrl;
	
	

	public long getReturnId() {
		return returnId;
	}

	public void setReturnId(long returnId) {
		this.returnId = returnId;
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

	public Date getReturnAuthorizeDate() {
		return returnAuthorizeDate;
	}

	public void setReturnAuthorizeDate(Date returnAuthorizeDate) {
		this.returnAuthorizeDate = returnAuthorizeDate;
	}

	public Date getUnitRecieveDate() {
		return unitRecieveDate;
	}

	public void setUnitRecieveDate(Date unitRecieveDate) {
		this.unitRecieveDate = unitRecieveDate;
	}

	public Date getCustomerRefundDate() {
		return customerRefundDate;
	}

	public void setCustomerRefundDate(Date customerRefundDate) {
		this.customerRefundDate = customerRefundDate;
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

	public ReturnUiListDTO() {

	}

	public ReturnUiListDTO(ReturnManagement r) {
		this.orderId = r.getOrder().getId();
		this.orderCode = r.getOrderProduct().getOrderCode();
		this.orderProductId = r.getOrderProduct().getId();
		this.orderProductPrice = r.getOrderProduct().getOrderProductPrice();
		this.quantity = r.getOrderProduct().getQuantity();

		this.orderPlacedDate = r.getOrder().getOrderPlacedDate();
		this.productVariantId = r.getOrderProduct().getProduct().getProductVariantId();
		this.variantName = r.getOrderProduct().getProduct().getVariantName();
		this.variantCode = r.getOrderProduct().getProduct().getVariantCode();
		this.mainImageUrl = r.getOrderProduct().getProduct().getMainImageUrl();
		this.userId = r.getOrder().getUser().getId();
		this.userName = r.getOrder().getUser().getName();

		this.returnId=r.getReturnId();
		this.status = r.getStatus();
		this.reason = r.getReason();
		this.customerRefundDate = r.getCustomerRefundDate();
		this.unitRecieveDate = r.getUnitRecieveDate();
		this.returnAuthorizeDate = r.getReturnAuthorizeDate();
	}
}
