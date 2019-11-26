package com.anaadihsoft.common.DTO;

import java.util.List;

import com.anaadihsoft.common.master.Product;
import com.anaadihsoft.common.master.ProductMeta;

public class ProductDTO {
	
	private Product product;
	
	private List<ProductVariantDTO> productVariantDTO;
	
	private List<ProductMeta> productMetaInfo;
	
	

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<ProductVariantDTO> getProductVariantDTO() {
		return productVariantDTO;
	}

	public void setProductVariantDTO(List<ProductVariantDTO> productVariantDTO) {
		this.productVariantDTO = productVariantDTO;
	}

	public List<ProductMeta> getProductMetaInfo() {
		return productMetaInfo;
	}

	public void setProductMetaInfo(List<ProductMeta> productMetaInfo) {
		this.productMetaInfo = productMetaInfo;
	}
	
	
	 
	
}
