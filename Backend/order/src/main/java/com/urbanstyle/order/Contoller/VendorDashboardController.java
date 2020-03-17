package com.urbanstyle.order.Contoller;

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

import com.urbanstyle.order.Repository.AddressRepository;
import com.urbanstyle.order.Repository.ProductVarientRepository;
import com.urbanstyle.order.Repository.UserRepository;
import com.urbanstyle.order.Service.OrderService;
import com.urbanstyle.order.util.CommonResponseSender;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"*","http://localhost:4200"})
public class VendorDashboardController {

	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private AddressRepository addressRepo;
	@Autowired
	private ProductVarientRepository productVarRepo;
	
	
	/**
	 * 
	 * @param offset
	 * @param request
	 * @param response
	 * @return LAST 5 ORDERS
	 */
	@RequestMapping(value= {"/getLastOrdersForVendor"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> getLastOrdersForVendor(@RequestParam(value="offset")int offset,@RequestParam(value="vendorId")int vendorId,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultMap = new HashMap<String,Object>();
		resultMap.put("lastOrders",orderService.getLastOrdersForVendor(offset,vendorId));
		return CommonResponseSender.getRecordSuccessResponse(resultMap, response);
	}
}
