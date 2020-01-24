package com.urbanstyle.product.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.anaadihsoft.common.master.ProductImages;

public interface ProductImagesRepository extends PagingAndSortingRepository<ProductImages,Long>  {

	@Query(value="delete from ProductImages where product.productId = :productId")
	@Modifying
	@Transactional
	void deleteAllImage(long productId);

}
