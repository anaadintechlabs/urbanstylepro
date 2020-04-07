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
import com.anaadihsoft.common.master.ProductVariant;
import com.anaadihsoft.common.master.ShortCodeGenerator;
import com.urbanstyle.product.repository.ProductReviewRepository;
import com.urbanstyle.product.repository.ShortCodeGeneratorRepository;

@Service
public class ProductReviewServiceImpl implements ProductReviewService{

	private static final int ACTIVE =	1;
	private static final int INACTIVE =2;
	
	@Autowired
	private ProductReviewRepository productReviewRepository; 
	@Autowired
	private ProductVarientRepository  productVarRepo;
	@Autowired
	private ShortCodeGeneratorRepository shortCodeGeneratorRepository; 
	
	@Override
	public ProductReview reviewProductSave(ProductReview productReview) {
		return productReviewRepository.save(productReview);
	}
	@Override
	public List<ProductReviewDTO> getAllReviewsOfUser(Filter filter, long userId) {
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
		return productReviewRepository.findByProductProductVariantIdAndStatus(productId,ACTIVE,pagable);
	}
	@Override
	public boolean softDeleteProductReview(long reviewId) {
		productReviewRepository.changeStatusOfProduct(reviewId,INACTIVE);
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
	public List<ProductReviewDTO> getAllReviewsforSPV(long prodVarId) {
		return	productReviewRepository.getAllReviewsforSPV(prodVarId,ACTIVE);
	}
	
	@Override
		public Long getAverageRatingOnProduct(String productId, int active) {
		
		ProductVariant prodVarient =  productVarRepo.findByUniqueprodvarId(productId);
		System.out.println("prodVarient"+prodVarient);
		if(prodVarient==null)
		{
			//check in Short Code table
			ShortCodeGenerator scg=shortCodeGeneratorRepository.findByShortCode(productId);
			if(scg!=null)
			{
				prodVarient=scg.getProdVar();
			}
		}
		
		if(prodVarient!=null)
		{
			return	productReviewRepository.getAverageRatingOnProduct(prodVarient.getProductVariantId(),active);	
		}
		else
		{
			return null;
		}

	}
	@Override
	public List<ProductReviewDTO> getLast5ProductReviewsOfVendor(long vendorId, int offset) {
		final Pageable pageable = PageRequest.of(offset, 5);
		return productReviewRepository.getLast5ProductReviewsOfVendor(vendorId,ACTIVE,pageable);
	}
	@Override
	public long getAllReviewsCountOfUser(long userId) {
		return productReviewRepository.countByUserIdAndStatus(userId,ACTIVE);

	}	
	}
