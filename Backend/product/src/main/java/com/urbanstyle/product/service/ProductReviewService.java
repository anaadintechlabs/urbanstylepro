package com.urbanstyle.product.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.anaadihsoft.common.DTO.ProductReviewDTO;
import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.ProductReview;

@Service
public interface ProductReviewService {

	ProductReview reviewProductSave(ProductReview productReview);

	List<ProductReviewDTO> getAllReviewsOfUser(Filter filter, long userId);

	List<ProductReview> getAllReviewsOfProduct(Filter filter, long productId);

	boolean softDeleteProductReview(long userId);

	List<ProductReview> getTop10ProductReviews(Filter filter);
	
	List<ProductReviewDTO> getAllReviewsforSPV(long prodVarId);
	
	Long getAverageRatingOnProduct(String productId, int active);

	List<ProductReviewDTO> getLast5ProductReviewsOfVendor(long vendorId, int offset);

	long getAllReviewsCountOfUser(long userId);

}
