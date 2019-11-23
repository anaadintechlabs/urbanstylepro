package com.anaadihsoft.common.DTO;

import java.util.List;
import java.util.Map;

import com.anaadihsoft.common.master.ProductMeta;
import com.anaadihsoft.common.master.ProductVariant;

public class ProductVariantDTO {

	
	public ProductVariant productVariant;
	
	private Map<Long,String> attributesMap;
	
	

	public ProductVariant getProductVariant() {
		return productVariant;
	}

	public void setProductVariant(ProductVariant productVariant) {
		this.productVariant = productVariant;
	}

 

	public Map<Long, String> getAttributesMap() {
		return attributesMap;
	}

	public void setAttributesMap(Map<Long, String> attributesMap) {
		this.attributesMap = attributesMap;
	}

	
	
	
}
