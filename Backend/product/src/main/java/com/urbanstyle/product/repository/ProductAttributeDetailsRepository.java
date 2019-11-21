package com.urbanstyle.product.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.master.ProductAttributeDetails;

@Repository
public interface ProductAttributeDetailsRepository extends PagingAndSortingRepository<ProductAttributeDetails, Long> {

}
