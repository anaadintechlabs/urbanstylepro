package com.anaadihsoft.common.DTO;

import com.anaadihsoft.common.master.ProductVariant;

public class AffiliateComissionDTO {

	private double amount;
	
	private String uniqueprodvarId;
	
	private String sku;
 	
	 private String variantName;
	 
	 private String variantCode;
	 
	 private String mainImageUrl;
	 
	 public AffiliateComissionDTO(double amount,ProductVariant pv)
	 {
		 this.amount=amount;
		 this.uniqueprodvarId=pv.getUniqueprodvarId();
		 this.sku=pv.getSku();
		 this.variantCode=pv.getVariantCode();
		 this.variantName=pv.getVariantName();
		 this.mainImageUrl=pv.getMainImageUrl();
	 }

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getUniqueprodvarId() {
		return uniqueprodvarId;
	}

	public void setUniqueprodvarId(String uniqueprodvarId) {
		this.uniqueprodvarId = uniqueprodvarId;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
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
