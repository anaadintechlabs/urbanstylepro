package com.urbanstyle.product.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.anaadihsoft.common.master.ProductAttributeDetails;
import com.anaadihsoft.common.master.ProductVariant;

public interface ProductAttributeDetailsRepository extends PagingAndSortingRepository<ProductAttributeDetails,Long>  {

	@Query(value="delete from ProductAttributeDetails where productVariant in (?1)")
	@Modifying
	@Transactional
	void deleteAllProductAttribute(List<ProductVariant> pvList);

}
