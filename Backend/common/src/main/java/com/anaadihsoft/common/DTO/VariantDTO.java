package com.anaadihsoft.common.DTO;

import java.util.List;
import java.util.Set;

public class VariantDTO {

	private String variationName;
	
	private Set<AttributeMiniDTO> variationData;

	

	public String getVariationName() {
		return variationName;
	}

	public void setVariationName(String variationName) {
		this.variationName = variationName;
	}

	public Set<AttributeMiniDTO> getVariationData() {
		return variationData;
	}

	public void setVariationData(Set<AttributeMiniDTO> variationData) {
		this.variationData = variationData;
	}

	


	

	
	
}
