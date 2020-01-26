package com.anaadihsoft.common.DTO;

public class VariantPriceUpdateDTO {

	private long productVariantId;
	
	private long actualPrice;
	
	private long displayPrice;

	public long getProductVariantId() {
		return productVariantId;
	}

	public void setProductVariantId(long productVariantId) {
		this.productVariantId = productVariantId;
	}

	public long getActualPrice() {
		return actualPrice;
	}

	public void setActualPrice(long actualPrice) {
		this.actualPrice = actualPrice;
	}

	public long getDisplayPrice() {
		return displayPrice;
	}

	public void setDisplayPrice(long displayPrice) {
		this.displayPrice = displayPrice;
	}
	
	
}
