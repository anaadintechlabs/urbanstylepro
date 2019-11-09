package com.urbanstyle.product.service;

import org.springframework.stereotype.Service;

import com.anaadihsoft.common.DTO.ShoppingCartDTO;

@Service
public interface ShoppingCartService  {


	Object addProductToShoppingCart(ShoppingCartDTO shoppingCartDTO);

}
