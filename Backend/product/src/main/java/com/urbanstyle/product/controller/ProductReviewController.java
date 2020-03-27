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
import com.anaadihsoft.common.master.Wishlist;
import com.urbanstyle.product.service.ProductReviewService;
import com.urbanstyle.product.util.CommonResponseSender;

@RestController
@RequestMapping("/review")
@CrossOrigin(origins="*")
public class ProductReviewController {

		
	@Autowired
	private ProductReviewService productReviewService; 
	/**
	 * 
	 * @param filter
	 * @param userId
	 * @param request
	 * @param response
	 * @return method for adding product to wishlist or cart
	 * 
	 */
	
	private static final int ACTIVE=1;
	@RequestMapping(value="/productReviewSave",method=RequestMethod.POST)
	public Map<String,Object> reviewProductSave(@RequestBody ProductReview productReview,
			HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();
		map.put("productReview", productReviewService.reviewProductSave(productReview));
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
		map.put("reviewList", productReviewService.getAllReviewsOfUser(filter,userId));
		map.put("count", productReviewService.getAllReviewsCountOfUser(userId));
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
	@RequestMapping(value="/getAllReviewsOfProduct",method=RequestMethod.POST)
	public Map<String,Object> getAllReviewsOfProduct(
			@RequestBody Filter filter,
			@RequestParam(value="productId") long productId,
			HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();
		map.put("reviewList", productReviewService.getAllReviewsOfProduct(filter,productId));
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
	@RequestMapping(value="/softDeleteProductReview",method=RequestMethod.DELETE)
	public Map<String,Object> softDeleteWishList(
			@RequestParam(value="reviewId") long reviewId,
			HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();
		map.put("deleted", productReviewService.softDeleteProductReview(reviewId));
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
	@RequestMapping(value="/getTop10ProductReviews",method=RequestMethod.POST)
	public Map<String,Object> getTop10ProductReviews(
			@RequestBody Filter filter,
			HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();
		map.put("reviewList", productReviewService.getTop10ProductReviews(filter));
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
	@RequestMapping(value="/getLast5ProductReviewsOfVendor",method=RequestMethod.GET)
	public Map<String,Object> getLast5ProductReviewsOfVendor(
			@RequestParam(value="vendorId") long vendorId,
			@RequestParam(value="offset") int offset,
			HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();
		map.put("reviewList", productReviewService.getLast5ProductReviewsOfVendor(vendorId,offset));
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
	@RequestMapping(value="/getAverageRatingOnProduct",method=RequestMethod.GET)
	public Map<String,Object> getAverageRatingOnProduct(
			@RequestParam(value="productId") long productId,
			HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();
		map.put("averageRating", productReviewService.getAverageRatingOnProduct(productId,ACTIVE));
		return CommonResponseSender.createdSuccessResponse(map, response);	
	}
	
	
}
