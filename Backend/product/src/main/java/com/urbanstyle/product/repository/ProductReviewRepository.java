package com.urbanstyle.product.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.anaadihsoft.common.DTO.ProductReviewDTO;
import com.anaadihsoft.common.master.ProductReview;

public interface ProductReviewRepository extends PagingAndSortingRepository<ProductReview, Long>{

	  @Query(value="select new com.anaadihsoft.common.DTO.ProductReviewDTO(pr) from ProductReview pr where  pr.user.id=?1 and pr.status=?2")
	List<ProductReviewDTO> findByUserIdAndStatus(long userId, int active, Pageable pagable);

	List<ProductReview> findByProductProductVariantIdAndStatus(long productId, int active, Pageable pagable);

	@Query(value="update ProductReview pr set pr.status =?2 where pr.id = ?1  ")
	@Modifying
	@Transactional
	void changeStatusOfProduct(long reviewId,  int inactive);

	List<ProductReview> findByStatus(int active, Pageable pagable);

	@Query(value="select new com.anaadihsoft.common.DTO.ProductReviewDTO(pr) from ProductReview pr where pr.product.productVariantId=?1 ")
	List<ProductReviewDTO> getAllReviewsforSPV(long prodVarId, int active);

	long countByUserIdAndStatus(long userId, int active);



	
	  @Query(value="select avg(pr.rating) from ProductReview pr where  pr.product.productVariantId=?1 and pr.status=?2")
	  Long getAverageRatingOnProduct(long productId, int active);
	  
	  @Query(value="select new com.anaadihsoft.common.DTO.ProductReviewDTO(pr) from ProductReview pr where  pr.product.product.user.id=?1 and pr.status=?2")
	  List<ProductReviewDTO> getLast5ProductReviewsOfVendor(long vendorId, int
	  active, Pageable pageable);
	 
}
