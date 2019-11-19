package com.urbanstyle.product.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.anaadihsoft.common.master.ProductReview;

public interface ProductReviewRepository extends PagingAndSortingRepository<ProductReview, Long>{

	List<ProductReview> findByUserIdAndStatus(long userId, int active, Pageable pagable);

	List<ProductReview> findByProductIdAndStatus(long productId, int active, Pageable pagable);

	@Query(value="update ProductReview set status =?3 where user.id = ?1 and product.id= ?2 ")
	void changeStatusOfProduct(long userId, long productId, int inactive);

	List<ProductReview> findByStatus(int active, Pageable pagable);

}
