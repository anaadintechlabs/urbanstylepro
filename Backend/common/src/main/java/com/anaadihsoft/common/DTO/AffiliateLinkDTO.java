package com.anaadihsoft.common.DTO;

import com.anaadihsoft.common.master.ShortCodeGenerator;

public class AffiliateLinkDTO {

	private long userId;
	
	private String shortCode;
	
	private String generatedLink;
	
	private long id;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getShortCode() {
		return shortCode;
	}

	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}

	public String getGeneratedLink() {
		return generatedLink;
	}

	public void setGeneratedLink(String generatedLink) {
		this.generatedLink = generatedLink;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public AffiliateLinkDTO(ShortCodeGenerator scg)
	{
		this.userId=scg.getUser().getId();
		this.generatedLink=scg.getGeneratedUrl();
		this.shortCode=scg.getShortCode();
		this.id=scg.getProdVar().getProductVariantId();
		
	}
	
	public AffiliateLinkDTO()
	{
		
	}
		
}
