package com.urbanstyle.product.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anaadihsoft.common.DTO.ProductDTO;
import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.Product;
import com.urbanstyle.product.service.ProductService;
import com.urbanstyle.product.util.CommonResponseSender;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins="*")
public class ProductController {

	
	@Autowired
	private ProductService productService;
	
//	/**
//	 * 
//	 * @param filter
//	 * @param userId
//	 * @param request
//	 * @param response
//	 * @return method for gettung all product for display
//	 */
//	@RequestMapping(value="/getAllMainProductsOfUser",method=RequestMethod.POST)
//	public Map<String,Object> getAllMainProductsOfUser(@RequestBody Filter filter,
//			@RequestParam(value="userId") long userId,
//			HttpServletRequest request,HttpServletResponse response)
//	{
//		final HashMap<String, Object> map = new HashMap<>();
//		map.put("productList", productService.getAllMainProductsOfUser(userId,filter));
//		return CommonResponseSender.createdSuccessResponse(map, response);
//		
//	}
//	
//	
//	/**
//	 * 
//	 * @param filter
//	 * @param userId
//	 * @param productId
//	 * @param request
//	 * @param response
//	 * @return method for getting all variant products of user
//	 */
//	@RequestMapping(value="/getAllVariantProductsOfProductOfUser",method=RequestMethod.POST)
//	public Map<String,Object> getAllVariantProductsOfProductOfUser(@RequestBody Filter filter,
//			@RequestParam(value="userId") long userId,
//			@RequestParam(value="productId") long productId,
//			HttpServletRequest request,HttpServletResponse response)
//	{
//		final HashMap<String, Object> map = new HashMap<>();
//		map.put("productVariantsList", productService.getAllVariantProductsOfProductOfUser(userId,productId,filter));
//		return CommonResponseSender.createdSuccessResponse(map, response);
//		
//	}
//	
//	
//	
//	/**
//	 * 
//	 * @param filter
//	 * @param categoryId
//	 * @param request
//	 * @param response
//	 * @return method for getting all product of a category
//	 */
//	@RequestMapping(value="/getProductOfCategory",method=RequestMethod.POST)
//	public Map<String,Object> getAllProductOfCategory(@RequestBody Filter filter,
//			@RequestParam(value="categoryId") long categoryId,
//			HttpServletRequest request,HttpServletResponse response)
//	{
//		final HashMap<String, Object> map = new HashMap<>();
//		map.put("productList", productService.getAllProductOfCategory(categoryId,filter));
//		return CommonResponseSender.createdSuccessResponse(map, response);
//		
//	}
//	
//	
//	
//	
//	
//	/**
//	 * method to get product By Id
//	 * @param prodId
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	
//	@RequestMapping(value="/getProductById",method=RequestMethod.GET)
//	public Map<String,Object> getProductById(@RequestParam(value="prodId")String prodId,HttpServletRequest request,HttpServletResponse response){
//		final HashMap<String, Object> map = new HashMap<>();
//		map.put("product", productService.getProductById(Long.parseLong(prodId)));
//		return CommonResponseSender.createdSuccessResponse(map, response);
//	}
	
	

	@RequestMapping(value="/getAllProducts",method=RequestMethod.GET)
	public Map<String,Object> getAllProducts(HttpServletRequest request,HttpServletResponse response){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("product", productService.getAllProducts());
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	
	
	@PostMapping
	public Map<String,Object> createProduct(HttpServletRequest request,HttpServletResponse response,@RequestBody ProductDTO productDTO){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("product", productService.createProduct(productDTO));
		return CommonResponseSender.createdSuccessResponse(map, response);
	}

	@PutMapping
	public Map<String,Object> updateProduct(HttpServletRequest request,HttpServletResponse response,@RequestBody Product product){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("product", productService.updateProduct(product));
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
}
