package com.anaadihsoft.common.DTO;

import java.util.List;

public class VariantDTO {

	private String variationName;
	
	private List<AttributeMiniDTO> variationData;

	

	public String getVariationName() {
		return variationName;
	}

	public void setVariationName(String variationName) {
		this.variationName = variationName;
	}

	public List<AttributeMiniDTO> getVariationData() {
		return variationData;
	}

	public void setVariationData(List<AttributeMiniDTO> variationData) {
		this.variationData = variationData;
	}

	

	
	
}
