package com.anaadihsoft.common.master;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.anaadihsoft.common.util.ProducIdType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"productCode"})})
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long productId;
	
	private String productCode;
	
	private ProducIdType productIdType;
		
	private String productName;
	
	private long categoryId;
	
	private Integer status;
	
	private String brandName;
	
	private String manufacturer;
	
	private String defaultSize;
	
	private String defaultColor;

	private boolean variantExist;
	
	private Date createdDate;
	
	private String createdBy;
	
	private Date modifiedDate;
	
	private String modifiedBy;
	
	private int totalVarients;
	
	public int getTotalVarients() {
		return totalVarients;
	}

	public void setTotalVarients(int totalVarients) {
		this.totalVarients = totalVarients;
	}

	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	@PrePersist
	public void setAudit()
	{
		this.createdDate=new Date();
		this.createdBy="Admin";
		this.setStatus(1);
	}
	
	@PreUpdate
	public void  update()
	{
		this.modifiedDate= new Date();
		this.modifiedBy="Admin";
	}

	
	public ProducIdType getProductIdType() {
		return productIdType;
	}

	public void setProductIdType(ProducIdType productIdType) {
		this.productIdType = productIdType;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}



	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	

	public boolean isVariantExist() {
		return variantExist;
	}

	public void setVariantExist(boolean variantExist) {
		this.variantExist = variantExist;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getDefaultSize() {
		return defaultSize;
	}

	public void setDefaultSize(String defaultSize) {
		this.defaultSize = defaultSize;
	}

	public String getDefaultColor() {
		return defaultColor;
	}

	public void setDefaultColor(String defaultColor) {
		this.defaultColor = defaultColor;
	}
	
	


	
	
}
