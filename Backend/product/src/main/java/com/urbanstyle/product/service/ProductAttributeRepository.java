package com.urbanstyle.product.service;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.master.ProductAttributeDetails;

@Repository
public interface ProductAttributeRepository extends PagingAndSortingRepository<ProductAttributeDetails,Long>{

	List<ProductAttributeDetails> findByProductVariantProductVariantId(long productVariantId);

}
