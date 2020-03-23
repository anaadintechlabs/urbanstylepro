package com.anaadihsoft.common.DTO;

import java.util.Map;

import com.anaadihsoft.common.master.ProductVariant;

public class ProductVariantUiDTO {
	
	private ProductVariant productVariant;
	
	private Map<String,String> attributesMap;

	public ProductVariant getProductVariant() {
		return productVariant;
	}

	public void setProductVariant(ProductVariant productVariant) {
		this.productVariant = productVariant;
	}

	public Map<String, String> getAttributesMap() {
		return attributesMap;
	}

	public void setAttributesMap(Map<String, String> attributesMap) {
		this.attributesMap = attributesMap;
	}
	
	
}
