package com.urbanstyle.product.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.master.ProductInventory;

@Repository
public interface ProductInventoryRepo extends PagingAndSortingRepository<ProductInventory,Long> {

	List<ProductInventory> findByUserId(long vendorId);

	List<ProductInventory> findByProductVariantProductVariantId(long productVarId);

}
