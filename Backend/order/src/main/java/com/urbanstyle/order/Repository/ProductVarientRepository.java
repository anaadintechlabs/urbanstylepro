package com.urbanstyle.order.Repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.master.ProductVariant;

@Repository
public interface ProductVarientRepository extends PagingAndSortingRepository<ProductVariant, Long>{

}
