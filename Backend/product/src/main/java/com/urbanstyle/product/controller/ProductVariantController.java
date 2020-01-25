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

import com.anaadihsoft.common.DTO.ProductVariantDTO;
import com.anaadihsoft.common.external.Filter;
import com.urbanstyle.product.service.ProductVarientService;
import com.urbanstyle.product.util.CommonResponseSender;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins="*")
public class ProductVariantController {

//	@Autowired
//	private ProductVarientService productVarient;
//	
//	/**
//	 * @description : Gett All Product of a category
//	 * @param filter
//	 * @param catId
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping(value="/getAllProductOfCategory",method=RequestMethod.POST)
//	public Map<String,Object> getAllProductOfCategory(@RequestBody Filter filter,
//			@RequestParam(value="catId") long catId,
//			HttpServletRequest request,HttpServletResponse response){
//		final HashMap<String, Object> map = new HashMap<>();
//		map.put("productList", productVarient.getAllProductOfCategory(catId,filter));
//		return CommonResponseSender.createdSuccessResponse(map, response);
//	}
//	
//	
//	
//	/**
//	 * @desctiption get all variant of vendor
//	 * @param filter
//	 * @param userId
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping(value="/getAllProductVariantOfUser",method=RequestMethod.POST)
//	public Map<String,Object> getAllProductVariantOfUser(@RequestBody Filter filter,
//			@RequestParam(value="userId") long userId,
//			HttpServletRequest request,HttpServletResponse response){
//		final HashMap<String, Object> map = new HashMap<>();
//		map.put("productList", productVarient.getAllProductVariantOfUser(userId,filter));
//		return CommonResponseSender.createdSuccessResponse(map, response);
//	}
//	
//	/**
//	 * @description Marking a product as Inactive or Inactive
//	 * @param filter
//	 * @param productId
//	 * @param status
//	 * @param userId
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping(value="/changeStatusOfProductVariant",method=RequestMethod.POST)
//	public Map<String,Object> changeStatusOfProductVariant(
//			@RequestBody Filter filter,
//			@RequestParam(value="productId") long productId,
//			@RequestParam(value="status") int status,
//			@RequestParam(value="userId") long userId,
//			HttpServletRequest request,HttpServletResponse response){
//		final HashMap<String, Object> map = new HashMap<>();
//		productVarient.changeStatusOfProduct(productId,status);
//		map.put("productList",productVarient.getAllActiveOrInactiveProductVariantOfUser(userId,filter,status==1?0:1));
//		return CommonResponseSender.createdSuccessResponse(map, response);
//	}
//	
//	/**
//	 * @description getting all active and inactive variant
//	 * @param filter
//	 * @param userId
//	 * @param status
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping(value="/getAllActiveOrInactiveProductVariantOfUser",method=RequestMethod.POST)
//	public Map<String,Object> getAllActiveOrInactiveProductVariantOfUser(@RequestBody Filter filter,
//			@RequestParam(value="userId") long userId,
//			@RequestParam(value="status") int status,
//			HttpServletRequest request,HttpServletResponse response){
//		final HashMap<String, Object> map = new HashMap<>();
//		map.put("productList", productVarient.getAllActiveOrInactiveProductVariantOfUser(userId,filter,status));
//		return CommonResponseSender.createdSuccessResponse(map, response);
//	}
//	
//	/**
//	 * @description Get all variant of product
//	 * @param request
//	 * @param response
//	 * @param prodId
//	 * @return
//	 */
//	@RequestMapping(value="/getAllVarientsOfProducts",method=RequestMethod.GET)
//	public Map<String,Object> getAllVarientsOfProducts(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="prodId",required = true)long prodId){
//		final HashMap<String, Object> map = new HashMap<>();
//		map.put("product", productVarient.getAllVarients(1,prodId));
//		return CommonResponseSender.createdSuccessResponse(map, response);
//	}
//	
//	/**
//	 * @description adding variant to a product
//	 * @param request
//	 * @param response
//	 * @param productVarientDTO
//	 * @return
//	 */
//	@RequestMapping(value="/addVarientToProduct",method= {RequestMethod.POST})
//	public Map<String,Object> addVarientToProduct(HttpServletRequest request,HttpServletResponse response,@RequestBody ProductVariantDTO productVarientDTO){
//		final HashMap<String, Object> map = new HashMap<>();
//		map.put("product", productVarient.addVarientToProduct(productVarientDTO));
//		return CommonResponseSender.createdSuccessResponse(map, response);
//	}
//	
//	@RequestMapping(value="/deleteVarientToProduct",method= {RequestMethod.POST,RequestMethod.GET})
//	public Map<String,Object> deleteVarientToProduct(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="prodId",required=true) long prodId,@RequestParam(value="prodVarId",required=true) long prodVarId){
//		final HashMap<String, Object> map = new HashMap<>();
//		map.put("product", productVarient.deleteVarientToProduct(prodId,prodVarId));
//		return CommonResponseSender.createdSuccessResponse(map, response);
//	}
}
