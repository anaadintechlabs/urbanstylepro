package com.anaadihsoft.common.DTO;

import java.util.List;

import com.anaadihsoft.common.master.ProductReview;

public class SingleProductDTO {
	
	private ProductVarientPacketDTO mainProductPacket;
	
	private List<ProductVarientPacketDTO> relatedProductsPackets;
	
	private List<ProductReviewDTO> allReviews;

	public ProductVarientPacketDTO getMainProductPacket() {
		return mainProductPacket;
	}

	public void setMainProductPacket(ProductVarientPacketDTO mainProductPacket) {
		this.mainProductPacket = mainProductPacket;
	}

	public List<ProductVarientPacketDTO> getRelatedProductsPackets() {
		return relatedProductsPackets;
	}

	public void setRelatedProductsPackets(List<ProductVarientPacketDTO> relatedProductsPackets) {
		this.relatedProductsPackets = relatedProductsPackets;
	}

	public List<ProductReviewDTO> getAllReviews() {
		return allReviews;
	}

	public void setAllReviews(List<ProductReviewDTO> allReviews) {
		this.allReviews = allReviews;
	}

}
