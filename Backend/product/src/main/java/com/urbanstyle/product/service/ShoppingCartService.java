package com.urbanstyle.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anaadihsoft.common.DTO.ShoppingCartDTO;
import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.ShoppingCartItem;

@Service
public interface ShoppingCartService  {


	Object addProductToShoppingCart(ShoppingCartDTO shoppingCartDTO);

	List<ShoppingCartItem> getShoppingCartListOfUser(Filter filter, String cartType, String userId);

	Object changeStatusOfShoppingCart(String userId, String cartType, String productId, String status);

}
