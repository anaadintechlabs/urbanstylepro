package com.anaadihsoft.common.master;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ProductMeta {

	//information which are common for all product or that are not category dependent
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	private Product product;
	
	private String metaKey;
	
	private String metaValue;
	
	private Date createdDate;
	
	private String createdBy;
	
	private Date modifiedDate;
	
	private String modifiedBy;
}
