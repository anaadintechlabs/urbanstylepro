package com.anaadihsoft.common.DTO;

import java.util.List;
import java.util.Map;

import com.anaadihsoft.common.master.ProductMeta;
import com.anaadihsoft.common.master.ProductVariant;

public class ProductVariantDTO {

	
	public ProductVariant productVariant;
	
	private Map<Long,String> attributesMap;
	
	private List<ProductMeta> productMetaInfo;

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

	public List<ProductMeta> getProductMetaInfo() {
		return productMetaInfo;
	}

	public void setProductMetaInfo(List<ProductMeta> productMetaInfo) {
		this.productMetaInfo = productMetaInfo;
	}
	
	
	
}
