package com.urbanstyle.product.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.master.Product;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product,Long>{

	Product findByProductName(String name);
	
	Product findByProductId(long productId);
}
