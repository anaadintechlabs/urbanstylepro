package com.urbanstyle.product.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.master.ShoppingCartItem;

@Repository
public interface ShoppingCartItemRepository extends PagingAndSortingRepository<ShoppingCartItem,Long> {


	@Query(value="update ShoppingCartItem sci set sci.status=?3 where sci.product.id in(?2) and  sci.shoppingCart.user.id=?1" )
	void changeStatusOfShoppingCart(String userId, List<String> productId2, String productId);

	List<ShoppingCartItem> findByShoppingCartUserIdAndStatus(String userId, String active, Pageable pagable);

//	@Query(value ="Select sum(sci.quantity) from ShoppingCartItem sci where sci.user.id=?1 and sci.status=?2")
//	Object getCartCountOfUser(long userId,String status);

}
