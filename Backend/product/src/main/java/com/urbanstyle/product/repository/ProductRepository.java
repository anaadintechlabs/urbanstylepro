package com.urbanstyle.product.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.Product;
import com.anaadihsoft.common.master.ProductVariant;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product,Long>{

	Product findByProductName(String name);
	
	Product findByProductId(long productId);

	Product findByProductCode(String productCode);

	List<Product> findByUserId(long userId, Pageable pagable);

	List<Product> findByUserIdAndStatus(long userId, int status, Pageable pagable);

	@Query(value="update Product wl set wl.status =?2 where wl.productId =?1 ")
	@Modifying
	@Transactional
	void changeStatusOfProduct(long productId, int status);

}
