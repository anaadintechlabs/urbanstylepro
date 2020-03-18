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
import com.urbanstyle.order.Service.OrderService;
import com.urbanstyle.order.Service.ReturnService;
import com.urbanstyle.order.util.CommonResponseSender;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"*","http://localhost:4200"})
public class ReturnController {

	@Autowired
	private ReturnService returnService;
	
	@Autowired
	private OrderService orderService;
	
	/**
	 * ALL RETURNS OF A CUSTOMER
	 * @param request
	 * @param filter
	 * @param response
	 * @param userId
	 * @return
	 */
	@RequestMapping(value= {"/getReturnByUser"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> getReturnByUser(HttpServletRequest request,
			@RequestBody Filter filter,HttpServletResponse response,@RequestParam(value="userId",required=true) String userId ){
		Map<String, Object> resultMap = new HashMap<String,Object>();
			resultMap.put("returnList",returnService.getReturnByUser(Long.parseLong(userId),filter));
			resultMap.put("RESPONSE", "SUCCESS");
		return CommonResponseSender.getRecordSuccessResponse(resultMap, response);
	}
	
	
	/**
	 * ALL RETURNS OF A CUSTOMER
	 * @param request
	 * @param filter
	 * @param response
	 * @param userId
	 * @return
	 */
	@RequestMapping(value= {"/getReturnForVendor"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> getReturnByVendor(HttpServletRequest request,
			@RequestBody Filter filter,HttpServletResponse response,@RequestParam(value="vendorId",required=true) String userId ){
		Map<String, Object> resultMap = new HashMap<String,Object>();
			resultMap.put("returnList",returnService.getReturnByVendor(Long.parseLong(userId),filter));
			resultMap.put("RESPONSE", "SUCCESS");
		return CommonResponseSender.getRecordSuccessResponse(resultMap, response);
	}
	
	
	/**
	 * 
	 * @param orderId
	 * @param userId
	 * @param reason
	 * @param request
	 * @param response
	 * @return ACTION BASED ON ORDER RETURN BBY USER
	 */
	@RequestMapping(value= {"/returnOrderByUser"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> returnOrderByUser(@RequestParam(value="orderId")long orderId,
			@RequestParam(value="userId")long userId,
			@RequestParam(value="reason")String reason,
			HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultMap = new HashMap<String,Object>();
			resultMap.put("orderList",orderService.returnOrderByUser(orderId,userId,reason));
		return CommonResponseSender.getRecordSuccessResponse(resultMap, response);
	}
	
	
	
	/**
	 *  ACTION PERFORMED BY ADMIN ON OERDER
	 * @param orderId
	 * @param status
	 * @param userId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value= {"/setReturnStatusbyAdmin"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> setStatusbyAdmin(@RequestParam(value="returnId")long returnId,
			@RequestParam(value="status")String status,
			HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultMap = new HashMap<String,Object>();
		returnService.setReturnStatusbyAdmin(returnId,status);
		return CommonResponseSender.getRecordSuccessResponse(resultMap, response);
	}
	
}
