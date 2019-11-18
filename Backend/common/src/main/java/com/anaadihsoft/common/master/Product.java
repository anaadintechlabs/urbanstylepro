package com.anaadihsoft.common.master;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"productName"})})
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long productId;
		
	private String productName;
	
	private long categoryId;
	
	private String sku;

	private Integer status;
	
	private double displayPrice;
	
	private double actualPrice;
	
	private double discountPrice;
	
	private double totalQuantity;
	
	private double reservedQuantity;
	
	
	@OneToMany(mappedBy="product")
	private Set<ProductImages> productImages;
	
	@OneToMany(mappedBy="product")
	private Set<ProductVariant> productVariants;
	
	@OneToMany(mappedBy="product")
	private Set<ProductVideos> productVideos;
	
	private Date createdDate;
	
	private String createdBy;
	
	private Date modifiedDate;
	
	private String modifiedBy;

	@PrePersist
	public void setAudit()
	{
		this.createdDate=new Date();
		this.createdBy="Admin";
	}
	
	@PreUpdate
	public void  update()
	{
		this.modifiedDate= new Date();
		this.modifiedBy="Admin";
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public Set<ProductImages> getProductImages() {
		return productImages;
	}

	public void setProductImages(Set<ProductImages> productImages) {
		this.productImages = productImages;
	}

	public Set<ProductVariant> getProductVariants() {
		return productVariants;
	}

	public void setProductVariants(Set<ProductVariant> productVariants) {
		this.productVariants = productVariants;
	}

	public Set<ProductVideos> getProductVideos() {
		return productVideos;
	}

	public void setProductVideos(Set<ProductVideos> productVideos) {
		this.productVideos = productVideos;
	}
	
	
	
}
