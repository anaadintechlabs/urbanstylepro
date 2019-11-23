package com.urbanstyle.product.controller;

import java.util.HashMap;
import java.util.List;
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
import com.urbanstyle.product.service.ShoppingCartService;
import com.urbanstyle.product.util.CommonResponseSender;

@RestController
@RequestMapping("/shopping")
@CrossOrigin(origins="*")
public class ShoppingCartController {

	@Autowired
	private ShoppingCartService shoppingCartService; 
	
	/**
	 * 
	 * @param filter
	 * @param userId
	 * @param request
	 * @param response
	 * @return method for adding product to wishlist or cart
	 */
	@RequestMapping(value="/addProductToShoppingCart",method=RequestMethod.POST)
	public Map<String,Object> addProductToShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO,
			HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();
		map.put("cartList", shoppingCartService.addProductToShoppingCart(shoppingCartDTO));
		return CommonResponseSender.createdSuccessResponse(map, response);
		
	}
	
	
	
	
	/**
	 * 
	 * @param filter
	 * @param request
	 * @param response
	 * @return method for getting all parent category
	 */
	@RequestMapping(value="/getShoppingCartListOfUser",method=RequestMethod.POST)
	public Map<String,Object> getShoppingCartListOfUser(@RequestBody Filter filter,
			@RequestParam(value="userId") String userId,
			HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();
		map.put("cartList", shoppingCartService.getShoppingCartListOfUser(filter,userId));
		return CommonResponseSender.createdSuccessResponse(map, response);
		
	}
	
	
	/**
	 * 
	 * @param filter
	 * @param request
	 * @param response
	 * @return method for getting all parent category
	 */
	@RequestMapping(value="/changeStatusOfShoppingCart",method=RequestMethod.GET)
	public Map<String,Object> changeStatusOfShoppingCart(
			@RequestParam(value="userId") String userId,
			@RequestParam(value="productId") List<String> productIds,
			@RequestParam(value="status") String status,
			HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();
		map.put("cartList", shoppingCartService.changeStatusOfShoppingCart(userId,productIds,status));
		return CommonResponseSender.createdSuccessResponse(map, response);
		
	}
	
	/**
	 * 
	 * @param filter
	 * @param request
	 * @param response
	 * @return method for getting all parent category
	 */
	@RequestMapping(value="/updateQuantityOfProduct",method=RequestMethod.GET)
	public Map<String,Object> updateQuantityOfProduct(
			@RequestParam(value="userId") String userId,
			@RequestParam(value="productId") List<String> productIds,
			@RequestParam(value="quantity") int quantity,
			HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();
		map.put("cartList", shoppingCartService.updateQuantityOfProduct(userId,productIds,status));
		return CommonResponseSender.createdSuccessResponse(map, response);
		
	}
	

	
	/**
	 * 
	 * @param filter
	 * @param request
	 * @param response
	 * @return method for getting all parent category
	 */
	@RequestMapping(value="/getCartCountOfUser",method=RequestMethod.GET)
	public Map<String,Object> getCartCountOfUser(
			@RequestParam(value="userId") long userId,
			HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();
		map.put("cartCount", shoppingCartService.getCartCountOfUser(userId));
		return CommonResponseSender.createdSuccessResponse(map, response);
		
	}
	
	
	
	
	
	
}
