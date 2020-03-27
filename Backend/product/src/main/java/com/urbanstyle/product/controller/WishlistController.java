package com.urbanstyle.product.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anaadihsoft.common.DTO.ShoppingCartDTO;
import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.Wishlist;
import com.urbanstyle.product.service.ShoppingCartService;
import com.urbanstyle.product.service.WishlistService;
import com.urbanstyle.product.util.CommonResponseSender;

@RestController
@RequestMapping("/wishlist")
@CrossOrigin(origins="*")
public class WishlistController {

	
	@Autowired
	private WishlistService wishlistService; 
	
	/**
	 * 
	 * @param filter
	 * @param userId
	 * @param request
	 * @param response
	 * @return method for adding product to wishlist or cart
	 */
	@RequestMapping(value="/addProductToWishlist",method=RequestMethod.POST)
	public Map<String,Object> addProductToShoppingCart(@RequestBody Wishlist wishList,
			HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();
		map.put("alreadyAdded", wishlistService.addProductToWishlist(wishList));
		return CommonResponseSender.createdSuccessResponse(map, response);	
	}
	
	
	/**
	 * 
	 * @param filter
	 * @param userId
	 * @param request
	 * @param response
	 * @return method for adding product to wishlist or cart
	 */
	@RequestMapping(value="/getAllWishListOfUser",method=RequestMethod.POST)
	public Map<String,Object> getAllWishListOfUser(
			@RequestBody Filter filter,
			@RequestParam(value="userId") long userId,
			HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();
		map.put("wishList", wishlistService.getAllWishListOfUser(filter,userId));
		map.put("count", wishlistService.getAllWishListCountOfUser(userId));

		return CommonResponseSender.createdSuccessResponse(map, response);	
	}
	
	
	
	/**
	 * 
	 * @param filter
	 * @param userId
	 * @param request
	 * @param response
	 * @return method for adding product to wishlist or cart
	 */
	@RequestMapping(value="/softDeleteWishList",method=RequestMethod.DELETE)
	public Map<String,Object> softDeleteWishList(
			@RequestParam(value="userId") long userId,
			@RequestParam(value="id") long id,
			HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();
		//get other wishlist based on user id
		map.put("deleted", wishlistService.softDeleteWishList(userId,id));
		Filter filter = new Filter();
		filter.setLimit(15);
		filter.setOffset(0);
		filter.setSortingDirection("ASC");
		filter.setSortingField("modifiedDate");
		map.put("wishList", wishlistService.getAllWishListOfUser(filter,userId));
		
		return CommonResponseSender.createdSuccessResponse(map, response);	
	}
	
	
}
