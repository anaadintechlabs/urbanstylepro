package com.urbanstyle.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anaadihsoft.common.DTO.ShoppingCartDTO;
import com.anaadihsoft.common.master.ShoppingCart;
import com.anaadihsoft.common.master.ShoppingCartItem;
import com.anaadihsoft.common.master.ShoppingCartType;
import com.urbanstyle.product.repository.ShoppingCartItemRepository;
import com.urbanstyle.product.repository.ShoppingCartRepository;
import com.urbanstyle.product.repository.ShoppingCartTypeRepository;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService{

	
	@Autowired
	private ShoppingCartRepository  shoppingCartRepository;
	@Autowired
	private ShoppingCartTypeRepository  shoppingCartTypeRepository;
	
	@Autowired
	private ShoppingCartItemRepository shoppingCartItemRepository;
	
	
//	@Override
	public Object addProductToShoppingCart(ShoppingCartDTO shoppingCartDTO) {
//	
//		shoppingCartRepository.existsByUserId(shoppingCartDTO.getUser().getId())
		ShoppingCart previousUserCart = shoppingCartRepository.findByUserId(shoppingCartDTO.getUser().getId());
//		//If user previous details exists
		if (previousUserCart!=null) {
			
//			shoppingCartTypeRepository.existsByShoppingCartUserIdAndCartType(shoppingCartDTO.getUser().getId(),shoppingCartDTO.getCartType())
			ShoppingCartType shoppingCartType=shoppingCartTypeRepository.findByShoppingCartAndCartType(previousUserCart,shoppingCartDTO.getCartType());
			if (shoppingCartType!=null) {
				shoppingCartType.setCartCount(shoppingCartType.getCartCount()+shoppingCartDTO.getQuantity());
				shoppingCartTypeRepository.save(shoppingCartType);
				
				
				ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
				shoppingCartItem.setShoppingCartType(shoppingCartType);
				shoppingCartItem.setQuantity(shoppingCartDTO.getQuantity());
				//Set proper product
				shoppingCartItem.setProduct(shoppingCartDTO.getProduct());
				shoppingCartItemRepository.save(shoppingCartItem);

				
			} else {
				System.out.println("RECORD WILL BE CREATED IN TWO TABLE");
				// JUST CREATE RECORD FOR CWSD and CWSLT
				ShoppingCartType shoppingCartTypeNew = new ShoppingCartType();
				shoppingCartTypeNew.setShoppingCart(previousUserCart);
				shoppingCartTypeNew.setCartType(shoppingCartDTO.getCartType());
				shoppingCartTypeRepository.save(shoppingCartTypeNew);

				

				ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
				shoppingCartItem.setShoppingCartType(shoppingCartTypeNew);
				shoppingCartItem.setQuantity(shoppingCartDTO.getQuantity());
				//Set proper product
				shoppingCartItem.setProduct(shoppingCartDTO.getProduct());
				shoppingCartItemRepository.save(shoppingCartItem);

				
			}

		} else {
		//	System.out.println("RECORD WILL BE CREATED IN ALL THREE TABLE");
			
			// Now no need to check just save all three table record
			ShoppingCart shoppingCart = new ShoppingCart();
			shoppingCart.setUser(shoppingCartDTO.getUser());
			shoppingCart=shoppingCartRepository.save(shoppingCart);
			
			//It can ve saved In seperate method in future
			ShoppingCartType shoppingCartType = new ShoppingCartType();
			shoppingCartType.setShoppingCart(shoppingCart);
			shoppingCartType.setCartType(shoppingCartDTO.getCartType());
			shoppingCartTypeRepository.save(shoppingCartType);
			
			
			ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
			shoppingCartItem.setShoppingCartType(shoppingCartType);
			shoppingCartItem.setQuantity(shoppingCartDTO.getQuantity());
			//Set proper product
			shoppingCartItem.setProduct(shoppingCartDTO.getProduct());
			shoppingCartItemRepository.save(shoppingCartItem);
			
		}

		return null;
	}

	
}
