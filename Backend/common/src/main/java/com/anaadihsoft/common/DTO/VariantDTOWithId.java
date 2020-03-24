package com.anaadihsoft.common.DTO;

import java.util.Set;

public class VariantDTOWithId {

	private String variationId;
	
	private Set<AttributeMiniDTO> variationData;

	

	public String getVariationId() {
		return variationId;
	}

	public void setVariationId(String variationId) {
		this.variationId = variationId;
	}

	public Set<AttributeMiniDTO> getVariationData() {
		return variationData;
	}

	public void setVariationData(Set<AttributeMiniDTO> variationData) {
		this.variationData = variationData;
	}

	
}

