package com.urbanstyle.product.controller;

import java.io.IOException;
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
	
	@RequestMapping(value="/getAllParentCategories",method=RequestMethod.POST)
	public Map<String,Object> getAllParentCategories(@RequestBody Filter filter,
			HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();
		map.put("categoryList", categoryService.getAllParentCategories(filter));
		return CommonResponseSender.createdSuccessResponse(map, response);
		
	}
	
	
	

	@RequestMapping(value="/saveCategory",method=RequestMethod.POST)
	public Map<String,Object> saveCategory(@RequestParam(value="file",required=false) MultipartFile[] files,@RequestParam(value="categoryString",required=false) String categoryString,HttpServletRequest request,HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException
	{
		final HashMap<String, Object> map = new HashMap<>();	
		ObjectMapper objMapper= new ObjectMapper();
		TypeReference<Category> mapType= new TypeReference<Category>() {
		};
		Category category= objMapper.readValue(categoryString, mapType);
		//System.out.println("CATEGORY IS"+category);
		category=categoryService.saveCategory(category);
//		if(category!=null) {
//		fileUploadService.saveIconofCategory(files,category);
//		}
		map.put("category", category);
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	@RequestMapping(value="/getAllSubCategoriesOfCategory",method=RequestMethod.GET)
	public Map<String,Object> getAllSubCategoriesOfCategory(
			@RequestParam(value="categoryId") long categoryId,
			HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();
		map.put("categoryList", categoryService.getAllSubCategoriesOfCategory(categoryId));
		return CommonResponseSender.createdSuccessResponse(map, response);
		
	}
	
	
	
	
}
