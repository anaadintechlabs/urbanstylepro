package com.anaadihsoft.common.master;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;

@Entity
public class Vendor {

	
	@Id
	private long id;

	private String createdBy;
	
    @Column(nullable = false)
	private String FirstName;
	
	private String LastName;

    @Email
    @Column(nullable = false)
	private String Email;
	
    @Column(nullable = false)
	private String PhoneNo;
	
    @Column(nullable = false)
	private String PanNumber;
	
    @Column(nullable = false)
	private String CompanyName;
	
    @Column(nullable = false)
	private String BrandName;
	
	private String Details;
	
	private Date createdDate;
	
	private Date modifiedDate;
	
	private String modifiedBy;
}
