package com.urbanstyle.product.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.urbanstyle.product.service.CategoryVariationService;
import com.urbanstyle.product.util.CommonResponseSender;

@RestController
@RequestMapping("/variation")
@CrossOrigin(origins="*")
public class CategoryVariationController {

	@Autowired
	private CategoryVariationService categoryVariationService;
	
	
	@RequestMapping(value="/getAllVariationOfCategory",method=RequestMethod.GET)
	public Map<String,Object> getAllVariationOfCategory(
			@RequestParam(value="categoryId") int categoryId,
			HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();
		map.put("variationList", categoryVariationService.getAllVariationOfCategory(categoryId));
		return CommonResponseSender.createdSuccessResponse(map, response);
		
	}
	
	
	@RequestMapping(value="/getVariationDetail",method=RequestMethod.GET)
	public Map<String,Object> getVariationDetail(
			@RequestParam(value="variationId") long variationId,
			HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();
		map.put("variation", categoryVariationService.getVariationDetail(variationId));
		return CommonResponseSender.createdSuccessResponse(map, response);
		
	}
	
	
	
}
