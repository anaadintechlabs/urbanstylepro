package com.urbanstyle.product.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.anaadihsoft.common.master.ProductAttributeDetails;

public interface ProductAttributeDetailsRepository extends PagingAndSortingRepository<ProductAttributeDetails,Long>  {

	@Query(value="delete from ProductAttributeDetails where productVariant.product.productId = :productId")
	@Modifying
	@Transactional
	void deleteAllProductAttribute(long productId);

}
