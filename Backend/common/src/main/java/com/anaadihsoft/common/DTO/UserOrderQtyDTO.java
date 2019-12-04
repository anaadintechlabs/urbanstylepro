package com.anaadihsoft.common.DTO;

public class UserOrderQtyDTO {

	private long productVariantId;
	
	public long getProductVariantId() {
		return productVariantId;
	}

	public void setProductVariantId(long productVariantId) {
		this.productVariantId = productVariantId;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	private int qty;
}
