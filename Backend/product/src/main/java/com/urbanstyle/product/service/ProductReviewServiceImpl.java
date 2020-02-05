package com.urbanstyle.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.anaadihsoft.common.DTO.ProductReviewDTO;
import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.ProductReview;
import com.urbanstyle.product.repository.ProductReviewRepository;

@Service
public class ProductReviewServiceImpl implements ProductReviewService{

	private static final int ACTIVE =	1;
	private static final int INACTIVE =2;
	
	@Autowired
	private ProductReviewRepository productReviewRepository; 
	@Override
	public ProductReview reviewProductSave(ProductReview productReview) {
		return productReviewRepository.save(productReview);
	}
	@Override
	public List<ProductReview> getAllReviewsOfUser(Filter filter, long userId) {
		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
				filter.getSortingDirection() != null
				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter.getSortingField());
		return productReviewRepository.findByUserIdAndStatus(userId,ACTIVE,pagable);
	}
	@Override
	public List<ProductReview> getAllReviewsOfProduct(Filter filter, long productId) {
		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
				filter.getSortingDirection() != null
				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter.getSortingField());
		return productReviewRepository.findByProductProductIdAndStatus(productId,ACTIVE,pagable);
	}
	@Override
	public boolean softDeleteProductReview(long userId, long productId) {
		productReviewRepository.changeStatusOfProduct(userId,productId,INACTIVE);
		return true;
	}
	@Override
	public List<ProductReview> getTop10ProductReviews(Filter filter) {
		filter.setLimit(10);
		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
				filter.getSortingDirection() != null
				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter.getSortingField());
		return productReviewRepository.findByStatus(ACTIVE,pagable);
	}
	@Override
	public double getAverageRatingOnProduct(long productId, String active) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public List<ProductReviewDTO> getAllReviewsforSPV(long prodVarId) {
		productReviewRepository.getAllReviewsforSPV(prodVarId,ACTIVE);
		return null;
	}

}
