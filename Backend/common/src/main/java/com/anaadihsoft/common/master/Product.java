package com.anaadihsoft.common.master;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	//vital Information
	private String productCode;
	
	private String productName;
	
	private String brandName;
	
	private String manufacturer;
	
	
	//It will be shifted to user
	@ManyToOne
	private Vendor user;
	
	@ManyToOne
	private Category category;
	
	
	@ManyToOne
	private Product parentProduct;
	
	private String productIdType;//Enum GTIN,EAN,ASIN,
	
	private String productCondition; //new,used,colletible
	
	private String status;
	
	//If product has no variant
	private String defaultColor;
	
	private String defaultColorMap;
	
	private String defaultsize;
	
	private String defaultSizeMap;
	//
	
	//Media Information
	private String mainImage;
	
	private String mainVideoUrl;
	//
	
	//Price Information
	private String displayPrice;
	
	private String actualPrice;
	
	private String paidToVendor;
	
	//
	
	//Inventory Infornation
	private double totalQuantity;
	
	private double reservedQuantity;
	
	private String sku;
	

	
	private boolean variant;//Whether it is variant or parent product
	
	private boolean containVariants;
	
	private boolean shipByVendorOrSite;
	
	private boolean enableReview;

	
	
}
