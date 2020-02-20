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
import org.springframework.web.bind.annotation.ResponseBody; 
import org.springframework.web.bind.annotation.RestController;

import com.anaadihsoft.common.DTO.UserOrderSaveDTO;
import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.UserOrder;
import com.urbanstyle.order.Service.OrderService;
import com.urbanstyle.order.util.CommonResponseSender;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"*","http://localhost:4200"})
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private PaymentConn paymentConn;

	//@ResponseBody
	@RequestMapping(value= {"/saveOrder"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> saveOrder(HttpServletRequest request,HttpServletResponse response,@RequestBody UserOrderSaveDTO userDetailSave){
		Map<String, Object> resultMap = new HashMap<String,Object>();
		//try {
			resultMap.put("order",orderService.saveorUpdate(userDetailSave));
			resultMap.put("RESPONSE", "SUCCESS");
//		}catch(Exception e) {
//			resultMap.put("RESPONSE", "ERROR");	
//		}
		return CommonResponseSender.createdSuccessResponse(resultMap, response);
		//return resultMap;
	}
	
	//@ResponseBody
	@RequestMapping(value= {"/getOrderByUser"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> getOrderByUser(HttpServletRequest request,
			@RequestBody Filter filter,HttpServletResponse response,@RequestParam(value="userId",required=true) String userId ){
		Map<String, Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap.put("orderList",orderService.getOrderByUser(Long.parseLong(userId),filter));
			resultMap.put("RESPONSE", "SUCCESS");
		}catch(Exception e) {
			resultMap.put("RESPONSE", "ERROR");	
		}
		return CommonResponseSender.getRecordSuccessResponse(resultMap, response);

		//return resultMap;
	}
	
	//@ResponseBody
	@RequestMapping(value= {"/getOrderById"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> getOrderById(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="orderId",required=true) String orderId ){
		Map<String, Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap.put("orderDetails",orderService.getOrderById(Long.parseLong(orderId)));
			resultMap.put("RESPONSE", "SUCCESS");
		}catch(Exception e) {
			resultMap.put("RESPONSE", "ERROR");	
		}
		return CommonResponseSender.getRecordSuccessResponse(resultMap, response);
		//return resultMap;
	}
	
	@RequestMapping(value= {"/madePayment"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> madePayment(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultMap = new HashMap<String,Object>();
		try {
			//paymentConn.createCustomer();
			//paymentConn.getCustomer("cus_GibhGlFT9kXSxE");
			//paymentConn.updateCardToCustomer("cus_GjLGxkPFyFLaS7");
			paymentConn.chargePayment("cus_GjLGxkPFyFLaS7");
			resultMap.put("RESPONSE", "SUCCESS");
		}catch(Exception e) {
			resultMap.put("RESPONSE", "ERROR");	
		}
		return CommonResponseSender.getRecordSuccessResponse(resultMap, response);
		//return resultMap;
	}
	
}