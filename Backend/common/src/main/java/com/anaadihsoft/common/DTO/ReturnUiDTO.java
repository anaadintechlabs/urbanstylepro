package com.anaadihsoft.common.DTO;

import java.util.Date;

import com.anaadihsoft.common.master.ReturnManagement;

public class ReturnUiDTO {

	//ORDER DETAILS
		private long orderId;
		
		private Date orderPlacedDate;
		
		private Date orderPaidDate;
		

		//USER ORDER PRODUCT
		private long orderProductId;
		
		private double quantity;
		
		private double orderProductPrice;
		
		private String comment;
		
		private String status;
		
		private String trackingId;
		
		private String trackingLink;
		
		private String orderCode;

		//PRODUCT VARIANT 
		private long productVariantId;
		
		private String uniqueprodvarId;
		
		 private String variantName;
		 
		 private String variantCode;
		 
		 private String mainImageUrl;
		
		 //VENDOR
		 
		 private long vendorId;
		 
		 private String vendorName;
		 
		 //User
		 
		 private long userId;
		 
		 private String userName;
		 
		 //ADDRESS DETAILS
		 
			
			private String addressOne;
			
			private String addressTwo;
					
			private String country;


			private String state;
			
			private String city;
			
			private String zip;
			
			private boolean primaryAddress;
			
			
			//return 
			private long returnId;

			private String reason;


			private Date returnAuthorizeDate;

			private Date unitRecieveDate;

			private Date customerRefundDate;
			
			

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

			public Date getOrderPaidDate() {
				return orderPaidDate;
			}

			public void setOrderPaidDate(Date orderPaidDate) {
				this.orderPaidDate = orderPaidDate;
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

			public String getComment() {
				return comment;
			}

			public void setComment(String comment) {
				this.comment = comment;
			}

			public String getStatus() {
				return status;
			}

			public void setStatus(String status) {
				this.status = status;
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

			public String getOrderCode() {
				return orderCode;
			}

			public void setOrderCode(String orderCode) {
				this.orderCode = orderCode;
			}

			public long getProductVariantId() {
				return productVariantId;
			}

			public void setProductVariantId(long productVariantId) {
				this.productVariantId = productVariantId;
			}

			public String getUniqueprodvarId() {
				return uniqueprodvarId;
			}

			public void setUniqueprodvarId(String uniqueprodvarId) {
				this.uniqueprodvarId = uniqueprodvarId;
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

			public long getVendorId() {
				return vendorId;
			}

			public void setVendorId(long vendorId) {
				this.vendorId = vendorId;
			}

			public String getVendorName() {
				return vendorName;
			}

			public void setVendorName(String vendorName) {
				this.vendorName = vendorName;
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

			public String getAddressOne() {
				return addressOne;
			}

			public void setAddressOne(String addressOne) {
				this.addressOne = addressOne;
			}

			public String getAddressTwo() {
				return addressTwo;
			}

			public void setAddressTwo(String addressTwo) {
				this.addressTwo = addressTwo;
			}

			public String getCountry() {
				return country;
			}

			public void setCountry(String country) {
				this.country = country;
			}

			public String getState() {
				return state;
			}

			public void setState(String state) {
				this.state = state;
			}

		
			public String getCity() {
				return city;
			}

			public void setCity(String city) {
				this.city = city;
			}

			public String getZip() {
				return zip;
			}

			public void setZip(String zip) {
				this.zip = zip;
			}

			public boolean isPrimaryAddress() {
				return primaryAddress;
			}

			public void setPrimaryAddress(boolean primaryAddress) {
				this.primaryAddress = primaryAddress;
			}
			
			public ReturnUiDTO()
			{
				
			}
			
			public ReturnUiDTO(ReturnManagement r)
			{
				
				 
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
					
				 this.trackingId=r.getOrderProduct().getTrackingId();
				 this.trackingLink=r.getOrderProduct().getTrackingLink();
				 this.addressOne=r.getOrder().getAddress().getAddressOne();
				 this.addressTwo=r.getOrder().getAddress().getAddressTwo();
				 this.city=r.getOrder().getAddress().getCite().getCityName();
				 this.state=r.getOrder().getAddress().getState().getStateName();
				 this.country=r.getOrder().getAddress().getCountry().getCountryName();
				 this.vendorId=r.getOrderProduct().getVendor().getId();
				 this.vendorName=r.getOrderProduct().getVendor().getName();
				 this.orderPaidDate=r.getOrder().getOrderPaidDate();
				 this.uniqueprodvarId=r.getOrderProduct().getProduct().getUniqueprodvarId();
				 this.comment=r.getOrderProduct().getComment();
				 
				 

					this.returnId=r.getReturnId();
					this.status = r.getStatus();
					this.reason = r.getReason();
					this.customerRefundDate = r.getCustomerRefundDate();
					this.unitRecieveDate = r.getUnitRecieveDate();
					this.returnAuthorizeDate = r.getReturnAuthorizeDate();
			}
}
