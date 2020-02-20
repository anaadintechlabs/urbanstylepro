package com.anaadihsoft.common.DTO;

import java.util.List;

public class ProductVarientPacketDTO {
	
	private ProductVariantDTO mainProduct;
	
	private List<String> allImages;

	public ProductVariantDTO getMainProduct() {
		return mainProduct;
	}

	public void setMainProduct(ProductVariantDTO mainProduct) {
		this.mainProduct = mainProduct;
	}

	public List<String> getAllImages() {
		return allImages;
	}

	public void setAllImages(List<String> allImages) {
		this.allImages = allImages;
	}
}
