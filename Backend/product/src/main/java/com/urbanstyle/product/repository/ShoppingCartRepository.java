package com.urbanstyle.product.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.master.ShoppingCart;

@Repository
public interface ShoppingCartRepository extends PagingAndSortingRepository<ShoppingCart, Long>{

	ShoppingCart findByUserId(Long id);

	boolean existsByUserId(Long id);

	
}
