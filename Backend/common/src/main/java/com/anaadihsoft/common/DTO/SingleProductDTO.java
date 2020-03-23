package com.anaadihsoft.common.DTO;

import java.util.List;

import com.anaadihsoft.common.master.ProductReview;

public class SingleProductDTO {
	
	private ProductVarientPacketDTO mainProductPacket;
	
	private long variantTotal;
	
	private List<VariantDTO> variants;
	
	private List<VariantDTOWithId> variantCombinations;
	
	private List<ProductReviewDTO> allReviews;
	
	
	



	public List<VariantDTOWithId> getVariantCombinations() {
		return variantCombinations;
	}

	public void setVariantCombinations(List<VariantDTOWithId> variantCombinations) {
		this.variantCombinations = variantCombinations;
	}

	public ProductVarientPacketDTO getMainProductPacket() {
		return mainProductPacket;
	}

	public void setMainProductPacket(ProductVarientPacketDTO mainProductPacket) {
		this.mainProductPacket = mainProductPacket;
	}



	public long getVariantTotal() {
		return variantTotal;
	}

	public void setVariantTotal(long variantTotal) {
		this.variantTotal = variantTotal;
	}

	public List<VariantDTO> getVariants() {
		return variants;
	}

	public void setVariants(List<VariantDTO> variants) {
		this.variants = variants;
	}

	public List<ProductReviewDTO> getAllReviews() {
		return allReviews;
	}

	public void setAllReviews(List<ProductReviewDTO> allReviews) {
		this.allReviews = allReviews;
	}

	
	
	
	//private List<ProductVariantDTO> relatedProductsPackets;

//	public List<ProductVariantDTO> getRelatedProductsPackets() {
//		return relatedProductsPackets;
//	}
//
//	public void setRelatedProductsPackets(List<ProductVariantDTO> relatedProductsPackets) {
//		this.relatedProductsPackets = relatedProductsPackets;
//	}
}
