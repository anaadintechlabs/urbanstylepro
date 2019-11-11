package com.urbanstyle.product.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.master.ShoppingCartItem;

@Repository
public interface ShoppingCartItemRepository extends PagingAndSortingRepository<ShoppingCartItem,Long> {

	

	List<ShoppingCartItem> findByShoppingCartTypeCartTypeAndShoppingCartTypeShoppingCartUserIdAndStatus(String cartType,
			String userId, Pageable pagable);

	@Query(value="update ShoppingCartItem sci set sci.status=?4 where sci.product.id=?3 and sci.shoppingCartType.cartType=?2 and sci.shoppingCartType.shoppingCart.user.id=?1" )
	void changeStatusOfShoppingCart(String userId, String cartType, String productId, String status);

}
