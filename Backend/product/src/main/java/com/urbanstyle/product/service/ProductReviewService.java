package com.urbanstyle.product.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.ProductReview;

@Service
public interface ProductReviewService {

	ProductReview reviewProductSave(ProductReview productReview);

	List<ProductReview> getAllReviewsOfUser(Filter filter, long userId);

	List<ProductReview> getAllReviewsOfProduct(Filter filter, long productId);

	boolean softDeleteProductReview(long userId, long productId);

	List<ProductReview> getTop10ProductReviews(Filter filter);

	@Query(value="select avg(pr.rating) from ProductReview pr where  pr.product.id=?1 and pr.status=?2")
	double getAverageRatingOnProduct(long productId, String active);

}