package com.urbanstyle.order.Repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.master.ProductVariant;

@Repository
public interface ProductVarientRepository extends PagingAndSortingRepository<ProductVariant, Long>{

	List<ProductVariant> findByProductUserId(long vendorId, Pageable pagable);

	@Query("select pv.totalQuantity - pv.reservedQuantity as available_quanity from ProductVariant pv where productVariantId=?1;")
	long getAvailableQuantityOfVariant(long productVariantId);

}
