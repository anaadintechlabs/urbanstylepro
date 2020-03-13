package com.urbanstyle.order.Repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.master.ProductVariant;

@Repository
public interface ProductVarientRepository extends PagingAndSortingRepository<ProductVariant, Long>{

	List<ProductVariant> findByProductUserId(long vendorId, Pageable pagable);

}
