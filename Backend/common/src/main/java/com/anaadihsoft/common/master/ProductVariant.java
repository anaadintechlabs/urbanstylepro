package com.anaadihsoft.common.master;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProductVariant {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long productVariantId;
	
	
	
	private long categoryId;
	
	 @ManyToOne
	 @JoinColumn(name="productId", nullable=false,updatable=false)
	 private Product product;
	 
	 private String sku;
	 
	private double displayPrice;
	
	private double actualPrice;
	
	private double discountPrice;
	
	private double totalQuantity;
	
	private double reservedQuantity;
		
	
	private int status;
	
	private Date createdDate;
	
	private String createdBy;
	
	private Date modifiedDate;
	
	private String modifiedBy;
	
	private boolean fetauredProduct;
	
	private boolean dealOfTheDay;
	
	

	public boolean isFetauredProduct() {
		return fetauredProduct;
	}

	public void setFetauredProduct(boolean fetauredProduct) {
		this.fetauredProduct = fetauredProduct;
	}

	public boolean isDealOfTheDay() {
		return dealOfTheDay;
	}

	public void setDealOfTheDay(boolean dealOfTheDay) {
		this.dealOfTheDay = dealOfTheDay;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public long getProductVariantId() {
		return productVariantId;
	}

	public void setProductVariantId(long productVariantId) {
		this.productVariantId = productVariantId;
	}


	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public double getDisplayPrice() {
		return displayPrice;
	}

	public void setDisplayPrice(double displayPrice) {
		this.displayPrice = displayPrice;
	}

	public double getActualPrice() {
		return actualPrice;
	}

	public void setActualPrice(double actualPrice) {
		this.actualPrice = actualPrice;
	}

	public double getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(double discountPrice) {
		this.discountPrice = discountPrice;
	}

	public double getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(double totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public double getReservedQuantity() {
		return reservedQuantity;
	}

	public void setReservedQuantity(double reservedQuantity) {
		this.reservedQuantity = reservedQuantity;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	
}