package com.urbanstyle.product.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.anaadihsoft.common.DTO.ShoppingCartDTO;
import com.anaadihsoft.common.DTO.ShoppingCartItemDTO;
import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.Product;
import com.anaadihsoft.common.master.ProductVariant;
import com.anaadihsoft.common.master.ShoppingCart;
import com.anaadihsoft.common.master.ShoppingCartItem;
import com.urbanstyle.product.repository.ShoppingCartItemRepository;
import com.urbanstyle.product.repository.ShoppingCartRepository;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService{

	private static final int ACTIVE =	1;
	
	@Autowired
	private ShoppingCartRepository  shoppingCartRepository;
	
	@Autowired
	private ShoppingCartItemRepository shoppingCartItemRepository;
	
	@Autowired
	private ProductVarientService prodVarService;
	
	//ALGO
	//Check whether Cart of that user exists or not
	// If No => Just create the cart and add the product
	// If yes => check again if any product is already added or not
	//if yes update the quantity
//	@Override
	public Object addProductToShoppingCart(ShoppingCartDTO shoppingCartDTO) {
		
		ShoppingCart previousUserCart = shoppingCartRepository.findByUserId(shoppingCartDTO.getUser().getId());
		if (previousUserCart!=null) {
				List<ShoppingCartItemDTO> itemDTO = shoppingCartDTO.getShoppingCartItemDTO();
			
				List<ShoppingCartItem> shoppingCartList = new ArrayList<>();
				double totalCost =0.0;
				int totalQuantity=0;
				for(int i =0;i<itemDTO.size();i++)
				{
				ShoppingCartItem  shoppingCartItem = shoppingCartItemRepository.findByShoppingCartAndProductVariant(previousUserCart,itemDTO.get(i).getProductVariant());
				ProductVariant prodVar  =  prodVarService.findByProdVarId(itemDTO.get(i).getProductVariant().getProductVariantId());

				if(shoppingCartItem==null)
				{
				 shoppingCartItem = new ShoppingCartItem();
					int quantity=itemDTO.get(i).getQuantity();
					shoppingCartItem.setQuantity(quantity);
				
				shoppingCartItem.setShoppingCart(previousUserCart);
				shoppingCartItem.setProductVariant(itemDTO.get(i).getProductVariant());
				//Cost I have to calculate again
				double cost =prodVar!=null ? prodVar.getDisplayPrice():itemDTO.get(i).getCost();
				shoppingCartItem.setCost(cost*quantity);
				totalCost += cost*quantity;
				}
				else
				{
					int quantity=itemDTO.get(i).getQuantity();
					double cost =prodVar!=null ? prodVar.getDisplayPrice():itemDTO.get(i).getCost();
					shoppingCartItem.setQuantity(shoppingCartItem.getQuantity() + itemDTO.get(i).getQuantity());	
					shoppingCartItem.setCost(shoppingCartItem.getCost() + (cost*quantity));	
					
					totalCost += cost*quantity;
				}
				
				//Set proper product

				
				totalQuantity += itemDTO.get(i).getQuantity();
				shoppingCartList.add(shoppingCartItem);
				}
				
				previousUserCart.setTotalCost(previousUserCart.getTotalCost()+totalCost);
				previousUserCart.setCartCount(previousUserCart.getCartCount()+totalQuantity);
				shoppingCartRepository.save(previousUserCart);
				shoppingCartItemRepository.saveAll(shoppingCartList);

		} else {

			// Now no need to check just save all three table record
			ShoppingCart shoppingCart = new ShoppingCart();
			shoppingCart.setUser(shoppingCartDTO.getUser());
			shoppingCart=shoppingCartRepository.save(shoppingCart);
			double totalCost =0.0;
			int totalQuantity=0;
			List<ShoppingCartItemDTO> itemDTO = shoppingCartDTO.getShoppingCartItemDTO();
			List<ShoppingCartItem> shoppingCartList = new ArrayList<>();
			for(int i =0;i<itemDTO.size();i++)
			{
			ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
			int quantity=itemDTO.get(i).getQuantity();
			shoppingCartItem.setQuantity(quantity);
			
			//Set proper product
			shoppingCartItem.setProductVariant(itemDTO.get(i).getProductVariant());
			ProductVariant prodVar  =  prodVarService.findByProdVarId(itemDTO.get(i).getProductVariant().getProductVariantId());
			double cost =prodVar!=null ? prodVar.getDisplayPrice():itemDTO.get(i).getCost();
			shoppingCartItem.setCost(cost*quantity);
			shoppingCartItem.setShoppingCart(shoppingCart);
			
			totalCost += (cost*quantity);
			totalQuantity += itemDTO.get(i).getQuantity();
			
			shoppingCartList.add(shoppingCartItem);
			}
			
			shoppingCart.setTotalCost(shoppingCart.getTotalCost()+totalCost);
			shoppingCart.setCartCount(shoppingCart.getCartCount()+totalQuantity);
			shoppingCartRepository.save(shoppingCart);
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
		
		return shoppingCartItemRepository.findByShoppingCartUserIdAndStatus(Long.parseLong(userId),ACTIVE,pagable);
	}


	@Override
	public Object changeStatusOfShoppingCart(String userId, List<Long> productIds, int status) {
		//shoppingCartItemRepository.changeStatusOfShoppingCart(userId,productId,status);
		List<ShoppingCartItem> shoppingCartItems =  shoppingCartItemRepository.findByShoppingCartUserIdAndProductVariantProductVariantIdInAndStatus(Long.parseLong(userId), productIds,ACTIVE);
		if(shoppingCartItems!=null)
		{
			double totalCostToRemove=0;
			int totalQuantityToRemove=0;
			
			for(ShoppingCartItem shoppingCartItem:shoppingCartItems)
			{
				totalCostToRemove+=shoppingCartItem.getCost();
				totalQuantityToRemove+=shoppingCartItem.getQuantity();
				shoppingCartItem.setStatus(0);
			}
			if(shoppingCartItems!=null && !shoppingCartItems.isEmpty())
			{
				ShoppingCart shoppingCart=shoppingCartItems.get(0).getShoppingCart();
				shoppingCart.setCartCount(shoppingCart.getCartCount() - totalQuantityToRemove);
				shoppingCart.setTotalCost(shoppingCart.getTotalCost() - totalCostToRemove);
				shoppingCartRepository.save(shoppingCart);

			}
			System.out.println("Update list"+shoppingCartItems);

			shoppingCartItemRepository.saveAll(shoppingCartItems);
			
		}
		return shoppingCartItems;
	}


	@Override
	public int getCartCountOfUser(long userId) {
	ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId);
	if(shoppingCart!=null)
	{
		return shoppingCart.getCartCount();
	}
	return 0;
		
	}


	@Override
	public Object updateQuantityOfProduct(String userId, Long productIds, int quantity) {
		
		ShoppingCartItem shoppingCartItem =  shoppingCartItemRepository.findByShoppingCartUserIdAndProductVariantProductVariantIdAndStatus(Long.parseLong(userId), productIds,ACTIVE);
		if(shoppingCartItem!=null && quantity>0)
		{
			//I will get the Previous Item, make the changes in parent as well
			//then set the new quantity and new cost in parent also
			ShoppingCart parentCart=shoppingCartItem.getShoppingCart();
			if(parentCart!=null)
			{
				//reset the quantity of 
				parentCart.setCartCount(parentCart.getCartCount() - (int)shoppingCartItem.getQuantity());
				parentCart.setTotalCost(parentCart.getTotalCost() - shoppingCartItem.getCost());
				
				//Now setting the quantity in item coming from UI
				shoppingCartItem.setQuantity(quantity);				
				ProductVariant prodVar  =  prodVarService.findByProdVarId(productIds);
				double cost =prodVar!=null ? prodVar.getDisplayPrice():110;
				shoppingCartItem.setCost(cost*quantity);
				
				
				parentCart.setTotalCost(parentCart.getTotalCost()+shoppingCartItem.getCost());
				parentCart.setCartCount(parentCart.getCartCount()+(int)shoppingCartItem.getQuantity());
				shoppingCartItemRepository.save(shoppingCartItem);
				shoppingCartRepository.save(parentCart);
				
				
			}
			
		}
		return null;
	}


	@Override
	public long getShoppingCartCountOfUser(String userId) {
		return shoppingCartItemRepository.countByShoppingCartUserIdAndStatus(Long.parseLong(userId),ACTIVE);

	}

	


	
}
