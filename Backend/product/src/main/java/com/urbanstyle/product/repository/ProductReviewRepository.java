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

	List<ProductReview> findByUserIdAndStatus(long userId, int active, Pageable pagable);

	List<ProductReview> findByProductProductVariantIdAndStatus(long productId, int active, Pageable pagable);

	@Query(value="update ProductReview pr set pr.status =?3 where pr.user.id = ?1 and pr.product.productVariantId= ?2 ")
	@Modifying
	@Transactional
	void changeStatusOfProduct(long userId, long productId, int inactive);

	List<ProductReview> findByStatus(int active, Pageable pagable);

	@Query(value="select new com.anaadihsoft.common.DTO.ProductReviewDTO(pr) from ProductReview pr where pr.product.productVariantId=?1 ")
	List<ProductReviewDTO> getAllReviewsforSPV(long prodVarId, int active);



	@Query(value="select avg(pr.rating) from ProductReview pr where  pr.product.id=?1 and pr.status=?2")
	Long getAverageRatingOnProduct(long productId, int active);

	@Query(value="select avg(pr.rating) from ProductReview pr where  pr.product.user.id=?1 and pr.status=?2")
	List<ProductReviewDTO> getLast5ProductReviewsOfVendor(long vendorId, int active, Pageable pageable);
}
