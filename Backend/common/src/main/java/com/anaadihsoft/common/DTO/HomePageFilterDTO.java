package com.anaadihsoft.common.DTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.anaadihsoft.common.master.ProductVariant;

public class HomePageFilterDTO {

	private List<ProductVariant> allVarients;
	
	private HashMap<Long,Map<String,  List<String>>> attributeDataMap;

	
 
	public List<ProductVariant> getAllVarients() {
		return allVarients;
	}

	public void setAllVarients(List<ProductVariant> allVarients) {
		this.allVarients = allVarients;
	}

	public HashMap<Long, Map<String, List<String>>> getAttributeDataMap() {
		return attributeDataMap;
	}

	public void setAttributeDataMap(HashMap<Long, Map<String, List<String>>> attributeDataMap) {
		this.attributeDataMap = attributeDataMap;
	}
	
}
