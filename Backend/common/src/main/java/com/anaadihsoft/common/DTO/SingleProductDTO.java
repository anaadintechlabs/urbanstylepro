package com.anaadihsoft.common.DTO;

import java.util.List;

import com.anaadihsoft.common.master.ProductReview;

public class SingleProductDTO {
	
	private ProductVarientPacketDTO mainProductPacket;
	
	private List<ProductVariantDTO> relatedProductsPackets;
	
	private List<ProductReviewDTO> allReviews;

	public ProductVarientPacketDTO getMainProductPacket() {
		return mainProductPacket;
	}

	public void setMainProductPacket(ProductVarientPacketDTO mainProductPacket) {
		this.mainProductPacket = mainProductPacket;
	}

	public List<ProductVariantDTO> getRelatedProductsPackets() {
		return relatedProductsPackets;
	}

	public void setRelatedProductsPackets(List<ProductVariantDTO> relatedProductsPackets) {
		this.relatedProductsPackets = relatedProductsPackets;
	}

	public List<ProductReviewDTO> getAllReviews() {
		return allReviews;
	}

	public void setAllReviews(List<ProductReviewDTO> allReviews) {
		this.allReviews = allReviews;
	}

}
