package com.urbanstyle.product.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.print.DocFlavor.STRING;
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
import org.springframework.web.multipart.MultipartFile;

import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.Category;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanstyle.product.service.CategoryService;
import com.urbanstyle.product.util.CommonResponseSender;

@RestController
@RequestMapping("/category")
@CrossOrigin(origins="*")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	/**
	 * 
	 * @param filter
	 * @param request
	 * @param response
	 * @return method for getting all parent category
	 */
	@RequestMapping(value="/getAllParentCategories",method=RequestMethod.GET)
	public Map<String,Object> getAllParentCategories(@RequestBody Filter filter,
			HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();
		map.put("categoryList", categoryService.getAllParentCategories(filter));
		return CommonResponseSender.getRecordSuccessResponse(map, response);
		
	}
	
	
	
	/**
	 * 
	 * @param categoryId
	 * @param request
	 * @param response
	 * @return method for getting all subcategory of a category
	 */
	@RequestMapping(value="/getAllSubCategoriesOfCategory",method=RequestMethod.GET)
	public Map<String,Object> getAllSubCategoriesOfCategory(
			@RequestParam(value="categoryId") long categoryId,
			HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();
		map.put("categoryList", categoryService.getAllSubCategoriesOfCategory(categoryId));
		return CommonResponseSender.getRecordSuccessResponse(map, response);
		
	}
	
	
	
	/**
	 * 
	 * @param categoryId
	 * @param request
	 * @param response
	 * @return method for getting all categories
	 */
	@RequestMapping(value="/getAllCategories",method=RequestMethod.GET)
	public Map<String,Object> getAllSubCategoriesOfCategory(HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();
		map.put("categoryList", categoryService.getAllCategories());
		return CommonResponseSender.getRecordSuccessResponse(map, response);
		
	}
	
	
	

	@PostMapping
	public Map<String,Object> saveCategory(HttpServletRequest request,HttpServletResponse response,@RequestBody(required=true)Category category) 
	{
		Map<String,Object> responseMap = new HashMap<>();
		if(category!=null) {
			category=categoryService.saveCategory(category);
			responseMap.put("category", category);
		}
		return CommonResponseSender.createdSuccessResponse(responseMap, response);
	}
	
	@PutMapping
	public Map<String,Object> updateCategory(HttpServletRequest request,HttpServletResponse response,@RequestBody(required=true)Category category) 
	{
		Map<String,Object> responseMap = new HashMap<>();
		category=categoryService.saveCategory(category);
		responseMap.put("category", category);
		return CommonResponseSender.createdSuccessResponse(responseMap, response);
	}
	
}
