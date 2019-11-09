package com.anaadihsoft.common.master;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Entity
public class ProductDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	private Product productId;
	
	@ManyToOne
	private CategoryVariation categoryVariation;
	
	private String variantValue;
	
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
}
