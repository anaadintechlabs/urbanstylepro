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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.anaadihsoft.common.DTO.FilterDTO;
import com.anaadihsoft.common.DTO.ProductDTO;
import com.anaadihsoft.common.DTO.ProductVariantDTO;
import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.Product;
import com.urbanstyle.product.service.ProductService;
import com.urbanstyle.product.service.ProductVarientService;
import com.urbanstyle.product.util.CommonResponseSender;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins="*")
public class ProductController {

	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductVarientService productVarient;
	
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
	
	
	@RequestMapping(value="/getAllVarientsOfProducts",method=RequestMethod.GET)
	public Map<String,Object> getAllVarientsOfProducts(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="prodId",required = true)long prodId){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("product", productVarient.getAllVarients(1,prodId));
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
//dummy
	@RequestMapping(value="/getAllProducts",method=RequestMethod.GET)
	public Map<String,Object> getAllProducts(HttpServletRequest request,HttpServletResponse response){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("productList", productVarient.getAllProducts());
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	
	
	@RequestMapping(value = "/saveProduct",method = RequestMethod.POST, consumes = "multipart/form-data")
	@ResponseBody
	public Map<String,Object> createProduct(HttpServletRequest request,HttpServletResponse response,
			@RequestPart(value="file",required=false) MultipartFile[] files,@RequestPart("dto") ProductDTO productDTO) throws Exception{
		final HashMap<String, Object> map = new HashMap<>();
		map.put("product", productService.createProduct(productDTO,files));
		return CommonResponseSender.createdSuccessResponse(map, response);
	}

	@PutMapping(value="/updateProduct")
	public Map<String,Object> updateProduct(HttpServletRequest request,HttpServletResponse response,@RequestBody Product product){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("product", productService.updateProduct(product));
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	// FEATURED PRODUCTS
	
	@RequestMapping(value="/getAllFeaturedProducts",method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> getAllFeaturedProducts(HttpServletRequest request,HttpServletResponse response){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("featuredProducts", productVarient.getAllFeaturedProducts());
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	@RequestMapping(value="/setFeaturedProduct",method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> setFeaturedProduct(HttpServletRequest request,HttpServletResponse response,@RequestParam (value="prodId",required=true)long prodVarId){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("featuredProducts", productVarient.setFeaturedProduct(prodVarId));
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	// DEAL OF THE DAY
	
	@RequestMapping(value="/getDealOftheDay",method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> getDealOftheDay(HttpServletRequest request,HttpServletResponse response){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("dealOftheDay", productVarient.getDealOftheDay());
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	@RequestMapping(value="/setDealOftheDay",method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> setDealOftheDay(HttpServletRequest request,HttpServletResponse response,@RequestParam (value="prodId",required=true)long prodId){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("dealOftheDay", productVarient.setDealOftheDay(prodId));
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	@PostMapping(value="/getBestSellingProduct")
	public Map<String,Object> getBestSellingProducts(@RequestBody Filter filter,
			HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();
		map.put("productList", productService.getBestSellingProducts(filter));
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	@RequestMapping(value="/addVarientToProduct",method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> addVarientToProduct(HttpServletRequest request,HttpServletResponse response,@RequestParam ProductVariantDTO productVarientDTO){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("dealOftheDay", productVarient.addVarientToProduct(productVarientDTO));
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	@RequestMapping(value="/deleteVarientToProduct",method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> deleteVarientToProduct(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="prodId",required=true) long prodId,@RequestParam(value="prodVarId",required=true) long prodVarId){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("dealOftheDay", productVarient.deleteVarientToProduct(prodId,prodVarId));
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	@RequestMapping(value="/searchProducts",method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> searchProducts(HttpServletRequest request,HttpServletResponse response,@RequestParam FilterDTO filterDTO){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("dealOftheDay", productVarient.searchProducts(filterDTO));
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
}
