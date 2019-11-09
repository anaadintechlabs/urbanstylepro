package com.anaadihsoft.common.master;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class CategoryVariation {

	@Id
	private long id;
	
	private String variationName;
	
	private String variationType;// Like Input,Checkbox,Select Etc
	
	private String variationValue;
	
	private String typeOfInput; //If variationType is input then text,number or what
	
	private String variantDummy; //Showing Size dummy like XL,XXL label show
	
	@ManyToOne
	private Category category;
	
	private String status;
	
	private Date createdDate;
	
	private String createdBy;
	
	private Date modifiedDate;
	
	private String modifiedBy;
	
}
