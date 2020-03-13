package com.urbanstyle.order.Repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.anaadihsoft.common.master.ShoppingCartItem;

public interface ShoppingCartItemRepository extends PagingAndSortingRepository<ShoppingCartItem,Long> {

	List<ShoppingCartItem> findByShoppingCartUserIdAndStatus(long userId, int i, Pageable pagable);

}
