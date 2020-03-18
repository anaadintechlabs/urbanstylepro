package com.urbanstyle.product.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import com.anaadihsoft.common.DTO.InventorySearchDTO;
import com.anaadihsoft.common.DTO.ProductDTO;
import com.anaadihsoft.common.DTO.ProductVariantDTO;
import com.anaadihsoft.common.DTO.VariantPriceUpdateDTO;
import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.urbanstyle.product.service.ProductService;
import com.urbanstyle.product.service.ProductVarientService;
import com.urbanstyle.product.util.CommonResponseSender;

import io.micrometer.core.instrument.util.StringUtils;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins="*")
public class ProductController {

	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductVarientService productVarient;
	

	
	
	
	@RequestMapping(value="/getAllProductOfCategory",method=RequestMethod.POST)
	public Map<String,Object> getAllProductOfCategory(@RequestBody Filter filter,
			@RequestParam(value="catId") long catId,
			HttpServletRequest request,HttpServletResponse response){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("productList", productVarient.getAllProductOfCategory(catId,filter));
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	
	@RequestMapping(value="/changeStatusOfProduct",method=RequestMethod.POST)
	public Map<String,Object> changeStatusOfProduct(
			@RequestBody Filter filter,
			@RequestParam(value="productId") long productId,
			@RequestParam(value="status") int status,
			@RequestParam(value="userId") long userId,
			HttpServletRequest request,HttpServletResponse response){
		final HashMap<String, Object> map = new HashMap<>();
		 productService.changeStatusOfProduct(productId,status);
		map.put("productList", productService.getAllActiveOrInactiveProductOfUser(userId,filter,status==1?0:1));
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	@RequestMapping(value="/getAllProductOfUser",method=RequestMethod.POST)
	public Map<String,Object> getAllProductOfUser(@RequestBody Filter filter,
			@RequestParam(value="userId") long userId,
			HttpServletRequest request,HttpServletResponse response){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("productList", productService.getAllProductOfUser(userId,filter));
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	
	@RequestMapping(value="/getAllProductVariantOfUser",method=RequestMethod.POST)
	public Map<String,Object> getAllProductVariantOfUser(@RequestBody Filter filter,
			@RequestParam(value="userId") long userId,
			HttpServletRequest request,HttpServletResponse response){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("productList", productVarient.getAllProductVariantOfUser(userId,filter));
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	@RequestMapping(value="/getAllActiveOrInactiveProductOfUser",method=RequestMethod.POST)
	public Map<String,Object> getAllActiveOrInactiveProductOfUser(@RequestBody Filter filter,
			@RequestParam(value="userId") long userId,
			@RequestParam(value="status") int status,
			HttpServletRequest request,HttpServletResponse response){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("productList", productService.getAllActiveOrInactiveProductOfUser(userId,filter,status));
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	@RequestMapping(value="/changeStatusOfProductVariant",method=RequestMethod.POST)
	public Map<String,Object> changeStatusOfProductVariant(
			@RequestBody Filter filter,
			@RequestParam(value="productId") long productId,
			@RequestParam(value="status") int status,
			@RequestParam(value="userId") long userId,
			HttpServletRequest request,HttpServletResponse response){
		final HashMap<String, Object> map = new HashMap<>();
		productVarient.changeStatusOfProduct(productId,status);
		map.put("productList",productVarient.getAllActiveOrInactiveProductVariantOfUser(userId,filter,status==1?0:1));
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	@RequestMapping(value="/getAllActiveOrInactiveProductVariantOfUser",method=RequestMethod.POST)
	public Map<String,Object> getAllActiveOrInactiveProductVariantOfUser(@RequestBody Filter filter,
			@RequestParam(value="userId") long userId,
			@RequestParam(value="status") int status,
			HttpServletRequest request,HttpServletResponse response){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("productList", productVarient.getAllActiveOrInactiveProductVariantOfUser(userId,filter,status));
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	
	@RequestMapping(value="/getAllVarientsOfProducts",method=RequestMethod.GET)
	public Map<String,Object> getAllVarientsOfProducts(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="prodId",required = true)long prodId){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("product", productVarient.getAllVarients(1,prodId));
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	@RequestMapping(value="/getVariantById",method=RequestMethod.GET)
	public Map<String,Object> getVariantById(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="prodVarId",required = true) long prodVarId){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("productVariant", productVarient.getVariantById(prodVarId));
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
//dummy
	@RequestMapping(value="/getAllProducts",method=RequestMethod.GET)
	public Map<String,Object> getAllProducts(HttpServletRequest request,HttpServletResponse response){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("productList", productVarient.getAllProducts());
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	
//	
//	/
	@RequestMapping(value = "/saveProduct",method = RequestMethod.POST, consumes = "multipart/form-data")
	@ResponseBody
	public Map<String,Object> createProduct(HttpServletRequest request,HttpServletResponse response,
			@RequestPart(value="file",required=false) MultipartFile[] files,@RequestParam(value="productDTOString") String productDTOString) throws Exception{

		final HashMap<String, Object> map = new HashMap<>();

		ObjectMapper objMapper= new ObjectMapper();
		objMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		TypeReference<ProductDTO> typeRefernce = new TypeReference<ProductDTO>() {
		};
		System.out.println("productDTOString"+productDTOString);
		ProductDTO productDTO=objMapper.readValue(productDTOString, typeRefernce);
		map.put("product", productService.createProduct(productDTO,files,false));
		return CommonResponseSender.createdSuccessResponse(map, response);
	}

//	@PutMapping(value="/updateProduct")
	@RequestMapping(value="/updateProduct",method=RequestMethod.POST)
	public Map<String,Object> updateProduct(HttpServletRequest request,HttpServletResponse response,@RequestPart(value="file",required=false) MultipartFile[] files,@RequestParam(value="productDTOString") String productDTOString) throws JsonMappingException, JsonProcessingException{
		final HashMap<String, Object> map = new HashMap<>();
		ObjectMapper objMapper= new ObjectMapper();
		objMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		TypeReference<ProductDTO> typeRefernce = new TypeReference<ProductDTO>() {
		};
		ProductDTO productDTO=objMapper.readValue(productDTOString, typeRefernce);
		map.put("product", productService.updateProduct(productDTO,files));
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	@RequestMapping(value="/updateSingleProductVariant",method=RequestMethod.POST)
	public Map<String,Object> updateSingleProductVariant(HttpServletRequest request,HttpServletResponse response,@RequestPart(value="file",required=false) MultipartFile[] files,@RequestParam(value="productDTOString") String productDTOString,
			@RequestParam(value="productVariantId") long productVariantId) throws JsonMappingException, JsonProcessingException{
		final HashMap<String, Object> map = new HashMap<>();
		ObjectMapper objMapper= new ObjectMapper();
		objMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		TypeReference<ProductDTO> typeRefernce = new TypeReference<ProductDTO>() {
		};
		ProductDTO productDTO=objMapper.readValue(productDTOString, typeRefernce);
		map.put("product", productService.updateSingleProductVariant(productDTO,files,productVariantId));
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
	
	@RequestMapping(value="/addVarientToProduct",method= {RequestMethod.POST})
	public Map<String,Object> addVarientToProduct(HttpServletRequest request,HttpServletResponse response,@RequestBody ProductVariantDTO productVarientDTO){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("product", productVarient.addVarientToProduct(productVarientDTO));
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	@RequestMapping(value="/deleteVarientToProduct",method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> deleteVarientToProduct(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="prodId",required=true) long prodId,@RequestParam(value="prodVarId",required=true) long prodVarId){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("product", productVarient.deleteVarientToProduct(prodId,prodVarId));
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	@RequestMapping(value="/searchProducts",method= {RequestMethod.POST})
	public Map<String,Object> searchProducts(HttpServletRequest request,HttpServletResponse response,@RequestBody FilterDTO filterDTO){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("productList", productVarient.searchProducts(filterDTO));
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	@RequestMapping(value="/getCompleteProduct",method= {RequestMethod.POST})
	public Map<String,Object> getCompleteProduct(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="prodId",required=true) long prodId){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("productList", productService.getCompleteProduct(prodId));
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	@RequestMapping(value="/getCompleteVariant",method= {RequestMethod.POST})
	public Map<String,Object> getCompleteVariant(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="productVariantId",required=true) long productVariantId,
			@RequestParam(value="prodId",required=true) long prodId){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("completeVariant", productService.getCompleteVariant(productVariantId,prodId));
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	
	
	@RequestMapping(value="/updateVarientDTO",method= {RequestMethod.POST})
	public Map<String,Object> updateVarientDTO(HttpServletRequest request,HttpServletResponse response,@RequestBody List<VariantPriceUpdateDTO> allVarientDTO){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("allVarientDTO", productVarient.updateVarientDTO(allVarientDTO));
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	@RequestMapping(value="/searchInventory",method= {RequestMethod.GET,RequestMethod.POST})
	public Map<String,Object> searchInventory(@RequestBody InventorySearchDTO inventorySearchDTO, HttpServletRequest request,HttpServletResponse response){
		
		final HashMap<String, Object> map = new HashMap<>();
		map.put("inventoryList", productVarient.searchInventory(inventorySearchDTO));
		return CommonResponseSender.getRecordSuccessResponse(map, response);
		
	}
	
	@RequestMapping(value="/getSingleProductDetail",method= {RequestMethod.GET,RequestMethod.POST})
	public Map<String,Object> getSingleProductDetail(@RequestParam(value="prodVarId") long prodVarId, HttpServletRequest request,HttpServletResponse response){
		
		final HashMap<String, Object> map = new HashMap<>();
		map.put("SingleProductDetail", productVarient.getSingleProductDetail(prodVarId));
		return CommonResponseSender.getRecordSuccessResponse(map, response);
		
	}
	
	@RequestMapping(value="/applyHomePageFilter",method= {RequestMethod.GET,RequestMethod.POST})
	public Map<String,Object> applyHomePageFilter(@RequestParam(value="searchString") String searchString,@RequestBody(required=false) HashMap<Long,List<String>> filterData,@RequestParam(value="catId") long catId, HttpServletRequest request,HttpServletResponse response){
		final HashMap<String, Object> map = new HashMap<>();
		if(filterData.isEmpty() && StringUtils.isNotBlank(searchString)) {
			map.put("HomePageFilterSearch", productVarient.applyHomePageFilter(searchString));
		}else if(!filterData.isEmpty() && StringUtils.isNotBlank(searchString)) {
			map.put("HomePageFilter", productVarient.applySideBarFilter(searchString,filterData));
		}else if("null".equalsIgnoreCase((String.valueOf(catId)))) {
			map.put("productVariants", productVarient.getAllVariantOfCategoryWithFilter(catId));
		}
		if(productVarient.applyHomePageFilter(searchString) == null &&  productVarient.applySideBarFilter(searchString,filterData) == null && productVarient.getAllVariantOfCategoryWithFilter(catId) == null) {
			map.put("productList",getAllProducts(request,response));
		}
		return CommonResponseSender.getRecordSuccessResponse(map, response);
		
	}
	
	/*
	 * @RequestMapping(value="/applyHomePageFilter",method=
	 * {RequestMethod.GET,RequestMethod.POST}) public Map<String,Object>
	 * applyHomePageFilter(@RequestParam(value="searchString") String searchString,
	 * HttpServletRequest request,HttpServletResponse response){
	 * 
	 * final HashMap<String, Object> map = new HashMap<>();
	 * map.put("HomePageFilter", productVarient.applyHomePageFilter(searchString));
	 * return CommonResponseSender.getRecordSuccessResponse(map, response);
	 * 
	 * }
	 * 
	 * @RequestMapping(value="/applySideBarFilter",method=
	 * {RequestMethod.GET,RequestMethod.POST}) public Map<String,Object>
	 * applySideBarFilter(@RequestParam(value="searchString") String
	 * searchString,@RequestBody(required=true) HashMap<Long,List<String>>
	 * filterData, HttpServletRequest request,HttpServletResponse response){
	 * 
	 * final HashMap<String, Object> map = new HashMap<>();
	 * map.put("HomePageFilter",
	 * productVarient.applySideBarFilter(searchString,filterData)); return
	 * CommonResponseSender.getRecordSuccessResponse(map, response);
	 * 
	 * }
	 * 
	 * 
	 * @RequestMapping(value="/getAllVariantOfCategoryWithFilter",method=
	 * {RequestMethod.GET,RequestMethod.POST}) public Map<String,Object>
	 * getAllVariantOfCategoryWithFilter(@RequestParam(value="catId") long catId,
	 * HttpServletRequest request,HttpServletResponse response){
	 * 
	 * final HashMap<String, Object> map = new HashMap<>();
	 * map.put("productVariants",
	 * productVarient.getAllVariantOfCategoryWithFilter(catId)); return
	 * CommonResponseSender.getRecordSuccessResponse(map, response);
	 * 
	 * }
	 */
	
	@RequestMapping(value="/genAffiliatelink",method= {RequestMethod.GET,RequestMethod.POST})
	public Map<String,Object> genAffiliatelink(@RequestParam(value="prodVarId") long prodVarId,@RequestParam(value="userId") long userId, HttpServletRequest request,HttpServletResponse response){
		
		final HashMap<String, Object> map = new HashMap<>();
		map.put("product", productService.genAffiliatelink(prodVarId,userId));
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
}
