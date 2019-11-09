package com.urbanstyle.product.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.master.ShoppingCart;
import com.anaadihsoft.common.master.ShoppingCartType;

@Repository
public interface ShoppingCartTypeRepository extends PagingAndSortingRepository<ShoppingCartType, Long>{

	boolean existsByShoppingCartUserIdAndCartType(Long id, String cartType);

	ShoppingCartType findByShoppingCartAndCartType(ShoppingCart previousUserCart, String cartType);

	
}
