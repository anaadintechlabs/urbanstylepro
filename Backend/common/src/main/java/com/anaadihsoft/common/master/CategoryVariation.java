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

public class CategoryVariation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
