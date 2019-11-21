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
import com.anaadihsoft.common.master.ProductReview;
import com.anaadihsoft.common.master.SiteReview;
import com.urbanstyle.product.service.ProductReviewService;
import com.urbanstyle.product.service.SiteReviewService;
import com.urbanstyle.product.util.CommonResponseSender;

@RestController
@RequestMapping("/reviewsite")
@CrossOrigin(origins="*")
public class SiteReviewController {

	
	@Autowired
	private SiteReviewService siteReviewService; 
	/**
	 * 
	 * @param filter
	 * @param userId
	 * @param request
	 * @param response
	 * @return method for adding product to wishlist or cart
	 * 
	 */
		@RequestMapping(value="/siteReviewSave",method=RequestMethod.POST)
	public Map<String,Object> reviewProductSave(@RequestBody SiteReview siteReview,
			HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();
		map.put("siteReview", siteReviewService.siteReviewSave(siteReview));
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
	@RequestMapping(value="/getAllReviewsOfUser",method=RequestMethod.POST)
	public Map<String,Object> getAllReviewsOfUser(
			@RequestBody Filter filter,
			@RequestParam(value="userId") long userId,
			HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();
		map.put("reviewList", siteReviewService.getAllReviewsOfUser(filter,userId));
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
	@RequestMapping(value="/getAllReviewsForDashboard",method=RequestMethod.POST)
	public Map<String,Object> getAllReviewsOfProduct(
			@RequestBody Filter filter,
			HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();
		map.put("reviewList", siteReviewService.getAllReviewsForDashboard(filter));
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
	@RequestMapping(value="/softDeleteSiteReview",method=RequestMethod.DELETE)
	public Map<String,Object> softDeleteWishList(
			@RequestParam(value="userId") long userId,
			HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();
		map.put("deleted", siteReviewService.softDeleteSiteReview(userId));
		return CommonResponseSender.createdSuccessResponse(map, response);	
	}
	
	

	

	
}
