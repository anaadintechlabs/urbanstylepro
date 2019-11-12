package com.urbanstyle.product.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.anaadihsoft.common.DTO.ShoppingCartDTO;
import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.Product;
import com.anaadihsoft.common.master.ShoppingCart;
import com.anaadihsoft.common.master.ShoppingCartItem;
import com.urbanstyle.product.repository.ShoppingCartItemRepository;
import com.urbanstyle.product.repository.ShoppingCartRepository;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService{

	private static final String ACTIVE="ACTIVE";
	
	@Autowired
	private ShoppingCartRepository  shoppingCartRepository;
	
	@Autowired
	private ShoppingCartItemRepository shoppingCartItemRepository;
	
	
//	@Override
	public Object addProductToShoppingCart(ShoppingCartDTO shoppingCartDTO) {
//	
//		shoppingCartRepository.existsByUserId(shoppingCartDTO.getUser().getId())
		ShoppingCart previousUserCart = shoppingCartRepository.findByUserId(shoppingCartDTO.getUser().getId());
//		//If user previous details exists
		if (previousUserCart!=null) {
				List<Product> productList = shoppingCartDTO.getProduct();
				List<ShoppingCartItem> shoppingCartList = new ArrayList<>();
				for(int i =0;i<productList.size();i++)
				{
				ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
				shoppingCartItem.setQuantity(shoppingCartDTO.getQuantity().get(i));
				//Set proper product
				shoppingCartItem.setProduct(shoppingCartDTO.getProduct().get(i));
				shoppingCartItem.setCost(shoppingCartDTO.getCost().get(i));
				
				shoppingCartList.add(shoppingCartItem);
				}
				shoppingCartItemRepository.saveAll(shoppingCartList);

		} else {

			// Now no need to check just save all three table record
			ShoppingCart shoppingCart = new ShoppingCart();
			shoppingCart.setUser(shoppingCartDTO.getUser());
			shoppingCart=shoppingCartRepository.save(shoppingCart);
			
			List<Product> productList = shoppingCartDTO.getProduct();
			List<ShoppingCartItem> shoppingCartList = new ArrayList<>();
			for(int i =0;i<productList.size();i++)
			{
			ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
			shoppingCartItem.setQuantity(shoppingCartDTO.getQuantity().get(i));
			//Set proper product
			shoppingCartItem.setProduct(shoppingCartDTO.getProduct().get(i));
			shoppingCartItem.setCost(shoppingCartDTO.getCost().get(i));
			
			shoppingCartList.add(shoppingCartItem);
			}
			shoppingCartItemRepository.saveAll(shoppingCartList);
			
		}

		return null;
	}


	@Override
	public List<ShoppingCartItem> getShoppingCartListOfUser(Filter filter,String userId) {
		
		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
				filter.getSortingDirection() != null
				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter.getSortingField());
		
		return shoppingCartItemRepository.findByShoppingCartUserIdAndStatus(userId,ACTIVE,pagable);
	}


	@Override
	public Object changeStatusOfShoppingCart(String userId, List<String> productId, String status) {
		shoppingCartItemRepository.changeStatusOfShoppingCart(userId,productId,status);
		return null;
	}


	@Override
	public Object getCartCountOfUser(long userId) {
	//return	shoppingCartItemRepository.getCartCountOfUser(userId,ACTIVE);
		return null;
	}

	
}
