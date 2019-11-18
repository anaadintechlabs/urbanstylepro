package com.anaadihsoft.order.Contoller;

import java.util.HashMap;

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

import com.anaadihsoft.common.master.UserOrder;
import com.anaadihsoft.order.Service.OrderService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"*","http://localhost:4200"})
public class OrderController {
	
	@Autowired
	private OrderService orderService;

	@ResponseBody
	@RequestMapping(value= {"/saveOrder"},method= {RequestMethod.POST,RequestMethod.GET})
	public HashMap<String,Object> saveOrder(HttpServletRequest request,HttpServletResponse response,@RequestBody UserOrder userOrder){
		HashMap<String, Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap.put("addressDetails",orderService.saveorUpdate(userOrder));
			resultMap.put("RESPONSE", "SUCCESS");
		}catch(Exception e) {
			resultMap.put("RESPONSE", "ERROR");	
		}
		return resultMap;
	}
	
	@ResponseBody
	@RequestMapping(value= {"/getOrderByUser"},method= {RequestMethod.POST,RequestMethod.GET})
	public HashMap<String,Object> getOrderByUser(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="userId",required=true) String userId ){
		HashMap<String, Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap.put("addressDetails",orderService.getOrderByUser(Long.parseLong(userId)));
			resultMap.put("RESPONSE", "SUCCESS");
		}catch(Exception e) {
			resultMap.put("RESPONSE", "ERROR");	
		}
		return resultMap;
	}
	
	@ResponseBody
	@RequestMapping(value= {"/getOrderById"},method= {RequestMethod.POST,RequestMethod.GET})
	public HashMap<String,Object> getOrderById(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="orderId",required=true) String orderId ){
		HashMap<String, Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap.put("addressDetails",orderService.getOrderById(Long.parseLong(orderId)));
			resultMap.put("RESPONSE", "SUCCESS");
		}catch(Exception e) {
			resultMap.put("RESPONSE", "ERROR");	
		}
		return resultMap;
	}
}
