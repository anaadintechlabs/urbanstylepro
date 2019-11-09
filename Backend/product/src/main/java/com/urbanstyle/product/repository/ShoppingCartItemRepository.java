package com.urbanstyle.product.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.anaadihsoft.common.master.ShoppingCartItem;

public interface ShoppingCartItemRepository extends PagingAndSortingRepository<ShoppingCartItem,Long> {

}
