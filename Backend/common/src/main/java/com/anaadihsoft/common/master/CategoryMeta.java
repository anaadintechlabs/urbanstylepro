package com.anaadihsoft.common.master;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class CategoryMeta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	private Category category;
	
	private String metaKey;
	
	private String type;
	
	private boolean subKeyAvailable;
	
	private String subKeys;
	
	private boolean unitsAvailable;
	
	private String dropDownValues;
	
	private int status;
	
	private Date createdDate;
	
	private String createdBy;
	
	private Date modifiedDate;
	
	private String modifiedBy;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getMetaKey() {
		return metaKey;
	}

	public void setMetaKey(String metaKey) {
		this.metaKey = metaKey;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	

	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isSubKeyAvailable() {
		return subKeyAvailable;
	}

	public void setSubKeyAvailable(boolean subKeyAvailable) {
		this.subKeyAvailable = subKeyAvailable;
	}

	public String getSubKeys() {
		return subKeys;
	}

	public void setSubKeys(String subKeys) {
		this.subKeys = subKeys;
	}

	public boolean isUnitsAvailable() {
		return unitsAvailable;
	}

	public void setUnitsAvailable(boolean unitsAvailable) {
		this.unitsAvailable = unitsAvailable;
	}

	public String getDropDownValues() {
		return dropDownValues;
	}

	public void setDropDownValues(String dropDownValues) {
		this.dropDownValues = dropDownValues;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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
