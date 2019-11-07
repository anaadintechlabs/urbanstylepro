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

import com.anaadihsoft.common.external.Filter;
import com.urbanstyle.product.service.ProductService;
import com.urbanstyle.product.util.CommonResponseSender;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins="*")
public class ProductController {

	
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value="/getAllMainProductsOfUser",method=RequestMethod.POST)
	public Map<String,Object> getAllMainProductsOfUser(@RequestBody Filter filter,
			@RequestParam(value="userId") long userId,
			HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();
		map.put("productList", productService.getAllMainProductsOfUser(userId,filter));
		return CommonResponseSender.createdSuccessResponse(map, response);
		
	}
	
	
	@RequestMapping(value="/getAllVariantProductsOfProductOfUser",method=RequestMethod.POST)
	public Map<String,Object> getAllVariantProductsOfProductOfUser(@RequestBody Filter filter,
			@RequestParam(value="userId") long userId,
			@RequestParam(value="productId") long productId,
			HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();
		map.put("productVariantsList", productService.getAllVariantProductsOfProductOfUser(userId,productId,filter));
		return CommonResponseSender.createdSuccessResponse(map, response);
		
	}
	
	
	
	
}
