package com.urbanstyle.product.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.master.ProductVariant;

@Repository
public interface ProductVariantRepository extends PagingAndSortingRepository<ProductVariant, Long> {

}
