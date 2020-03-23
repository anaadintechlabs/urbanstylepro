package com.anaadihsoft.common.DTO;

import java.util.List;

public class ProductVarientPacketDTO {
	
	private ProductVariantUiDTO mainProduct;
	
	private List<String> allImages;



	public ProductVariantUiDTO getMainProduct() {
		return mainProduct;
	}

	public void setMainProduct(ProductVariantUiDTO mainProduct) {
		this.mainProduct = mainProduct;
	}

	public List<String> getAllImages() {
		return allImages;
	}

	public void setAllImages(List<String> allImages) {
		this.allImages = allImages;
	}
}
