package com.urbanstyle.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anaadihsoft.common.DTO.ShoppingCartDTO;
import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.ShoppingCartItem;

@Service
public interface ShoppingCartService  {


	Object addProductToShoppingCart(ShoppingCartDTO shoppingCartDTO);

	List<ShoppingCartItem> getShoppingCartListOfUser(Filter filter, String userId);

	Object changeStatusOfShoppingCart(String userId, List<Long> productIds, int status);

	int getCartCountOfUser(long userId);

	Object updateQuantityOfProduct(String userId, Long productId, int quantity);

	long getShoppingCartCountOfUser(String userId);

}
