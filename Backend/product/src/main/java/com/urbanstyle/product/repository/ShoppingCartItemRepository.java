package com.urbanstyle.product.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.master.Product;
import com.anaadihsoft.common.master.ProductVariant;
import com.anaadihsoft.common.master.ShoppingCart;
import com.anaadihsoft.common.master.ShoppingCartItem;

@Repository
public interface ShoppingCartItemRepository extends PagingAndSortingRepository<ShoppingCartItem,Long> {


	@Query(value="update ShoppingCartItem sci set sci.status=?3 where sci.productVariant.productVariantId in(?2) and  sci.shoppingCart.user.id=?1" )
	@Modifying
	@Transactional
	void changeStatusOfShoppingCart(String userId, List<String> productId2, String productId);

	List<ShoppingCartItem> findByShoppingCartUserIdAndStatus(String userId, int active, Pageable pagable);

	ShoppingCartItem findByShoppingCartAndProductVariant(ShoppingCart previousUserCart, ProductVariant product);

	List<ShoppingCartItem> findByShoppingCartUserIdAndProductVariantProductVariantIdIn(String userId, List<Long> productId);

	ShoppingCartItem findByShoppingCartUserIdAndProductVariantProductVariantId(String userId, Long productIds);



}
