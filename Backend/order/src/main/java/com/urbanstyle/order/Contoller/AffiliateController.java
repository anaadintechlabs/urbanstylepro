package com.urbanstyle.order.Contoller;

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
import com.urbanstyle.order.Service.AffiliateService;
import com.urbanstyle.order.util.CommonResponseSender;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"*","http://localhost:4200"})
public class AffiliateController {
	
	
	@Autowired
	private AffiliateService affiliateService;
	
	/**
	 * 
	 * @param request
	 * @param filter
	 * @param response
	 * @param userId
	 * @return GET ALL ORDER OF A USER based on filter and search string
	 */
	@RequestMapping(value= {"/getOrderByAffiliate"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> getOrderByUser(HttpServletRequest request,
			@RequestBody Filter filter,HttpServletResponse response,@RequestParam(value="affiliateId",required=true) String userId ){
		Map<String, Object> resultMap = new HashMap<String,Object>();
			resultMap.put("orderList",affiliateService.getOrderProductByAffiliate(Long.parseLong(userId),filter));
		//	resultMap.put("count",affiliateService.getCountOrderProductByAffiliate(Long.parseLong(userId),filter));
			return CommonResponseSender.getRecordSuccessResponse(resultMap, response);

	}
	
	
	/**
	 * 
	 * @param request
	 * @param filter
	 * @param response
	 * @param userId
	 * @return GET ALL ORDER OF A USER based on filter and search string
	 */
	@RequestMapping(value= {"/getOrderByAffiliateByStatus"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> getOrderByAffiliateByStatus(HttpServletRequest request,
			@RequestBody Filter filter,HttpServletResponse response,@RequestParam(value="affiliateId",required=true) String userId,@RequestParam(value="status",required=true) String status ){
		Map<String, Object> resultMap = new HashMap<String,Object>();
			resultMap.put("orderList",affiliateService.getOrderByAffiliateByStatus(Long.parseLong(userId),status,filter));
		//	resultMap.put("count",affiliateService.getCountOrderProductByAffiliate(Long.parseLong(userId),filter));
			return CommonResponseSender.getRecordSuccessResponse(resultMap, response);

	}
	
	
	/**
	 * 
	 * @param request
	 * @param filter
	 * @param response
	 * @param userId
	 * @return GET ALL ORDER OF A USER based on filter and search string
	 */
	@RequestMapping(value= {"/getReturnByAffiliate"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> getReturnByAffiliate(HttpServletRequest request,
			@RequestBody Filter filter,HttpServletResponse response,@RequestParam(value="affiliateId",required=true) String userId ){
		Map<String, Object> resultMap = new HashMap<String,Object>();
			resultMap.put("returnList",affiliateService.getReturnByAffiliate(Long.parseLong(userId),filter));
		//	resultMap.put("count",affiliateService.getCountOrderProductByAffiliate(Long.parseLong(userId),filter));
			return CommonResponseSender.getRecordSuccessResponse(resultMap, response);

	}

}
