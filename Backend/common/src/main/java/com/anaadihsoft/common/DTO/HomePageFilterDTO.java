package com.anaadihsoft.common.DTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomePageFilterDTO {

	private List<ProductVariantDTO> allVarients;
	
	private HashMap<Long,Map<String,  List<String>>> attributeDataMap;

	public List<ProductVariantDTO> getAllVarients() {
		return allVarients;
	}

	public void setAllVarients(List<ProductVariantDTO> allVarients) {
		this.allVarients = allVarients;
	}

	public HashMap<Long, Map<String, List<String>>> getAttributeDataMap() {
		return attributeDataMap;
	}

	public void setAttributeDataMap(HashMap<Long, Map<String, List<String>>> attributeDataMap) {
		this.attributeDataMap = attributeDataMap;
	}
	
}
