package com.anaadihsoft.common.DTO;

import java.util.List;

import com.anaadihsoft.common.master.Product;

public class ProductDTO {
	
	private Product product;
	
	private List<ProductVariantDTO> productVariantDTO;

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
	
	
	 
	
}
