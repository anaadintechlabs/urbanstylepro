package com.urbanstyle.order.Contoller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import com.urbanstyle.order.Repository.AddressRepository;
import com.urbanstyle.order.Repository.ProductVarientRepository;
import com.urbanstyle.order.Repository.ShoppingCartItemRepository;
import com.urbanstyle.order.Repository.UserRepository;
import com.urbanstyle.order.Repository.WishListRepository;
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
	
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private AddressRepository addressRepo;
	@Autowired
	private ProductVarientRepository productVarRepo;
	
	@Autowired
	private ShoppingCartItemRepository	shoppingCartItemRepository;
	
	@Autowired
	private WishListRepository wishlistRepository;
	//@ResponseBody
	@RequestMapping(value= {"/saveOrder"},method= {RequestMethod.POST})
	public Map<String,Object> saveOrder(HttpServletRequest request,HttpServletResponse response,@RequestBody UserOrderSaveDTO userDetailSave){
		Map<String, Object> resultMap = new HashMap<String,Object>();
		//try {
		System.out.println("userDetailSave"+userDetailSave);
			resultMap.put("order",orderService.saveorUpdate(userDetailSave));
			resultMap.put("RESPONSE", "SUCCESS");
//		}catch(Exception e) {
//			resultMap.put("RESPONSE", "ERROR");	
//		}
			System.out.println("resultMap"+resultMap);
		return CommonResponseSender.createdSuccessResponse(resultMap, response);
		//return resultMap;
	}
	
	//@ResponseBody
	@RequestMapping(value= {"/getOrderByUser"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> getOrderByUser(HttpServletRequest request,
			@RequestBody Filter filter,HttpServletResponse response,@RequestParam(value="userId",required=true) String userId ){
		Map<String, Object> resultMap = new HashMap<String,Object>();
		//try {
			resultMap.put("orderList",orderService.getOrderByUser(Long.parseLong(userId),filter));
			resultMap.put("RESPONSE", "SUCCESS");
		//}catch(Exception e) {
		//	resultMap.put("RESPONSE", "ERROR");	
		//}
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
	
	@RequestMapping(value= {"/getOrderForVendor"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> getOrderForVendor(@RequestParam(value="vendorId")long vendorId,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultMap = new HashMap<String,Object>();
			resultMap.put("orderList",orderService.getVendorOrder(vendorId));
		return CommonResponseSender.getRecordSuccessResponse(resultMap, response);
	}
	
	@RequestMapping(value= {"/getOrderForVendorByStatus"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> getOrderForVendorByStatus(@RequestParam(value="vendorId")long vendorId,@RequestParam(value="status")String status,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultMap = new HashMap<String,Object>();
			resultMap.put("orderList",orderService.getOrderForVendorByStatus(vendorId,status));
		return CommonResponseSender.getRecordSuccessResponse(resultMap, response);
	}
	
	/**
	 * All products associated with vendor , in case of multi vendor order
	 * @param vendorId
	 * @param orderId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value= {"/getOrderProductForVendor"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> getOrderProductForVendor(@RequestParam(value="vendorId")long vendorId,@RequestParam(value="orderId")long orderId,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultMap = new HashMap<String,Object>();
			resultMap.put("orderList",orderService.getOrderProductForVendor(vendorId,orderId));
		return CommonResponseSender.getRecordSuccessResponse(resultMap, response);
	}
	
	//DIVIDED INTO BELOW TWO METHOD
	@RequestMapping(value= {"/setStatusbyUser"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> setStatusbyUser(@RequestParam(value="orderId")long orderId,@RequestParam(value="status")String status,@RequestParam(value="userId")long userId,@RequestParam(value="reason")String reason,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultMap = new HashMap<String,Object>();
			resultMap.put("orderList",orderService.setStatusbyUser(orderId,status,reason,userId));
		return CommonResponseSender.getRecordSuccessResponse(resultMap, response);
	}
	
	
	@RequestMapping(value= {"/cancelOrderByUser"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> cancelOrderByUser(@RequestParam(value="orderId")long orderId,
			@RequestParam(value="userId")long userId,
			HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultMap = new HashMap<String,Object>();
			resultMap.put("orderList",orderService.cancelOrderByUser(orderId,userId));
		return CommonResponseSender.getRecordSuccessResponse(resultMap, response);
	}
	
	@RequestMapping(value= {"/returnOrderByUser"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> returnOrderByUser(@RequestParam(value="orderId")long orderId,
			@RequestParam(value="userId")long userId,
			@RequestParam(value="reason")String reason,
			HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultMap = new HashMap<String,Object>();
			resultMap.put("orderList",orderService.returnOrderByUser(orderId,userId,reason));
		return CommonResponseSender.getRecordSuccessResponse(resultMap, response);
	}
	
	@RequestMapping(value= {"/setStatusbyVendorForCompleteOrder"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> setStatusbyVendorForCompleteOrder(@RequestParam(value="orderId")long orderId,@RequestParam(value="status")String status,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultMap = new HashMap<String,Object>();
			resultMap.put("orderList",orderService.setStatusbyVendorForCompleteOrder(orderId,status));
		return CommonResponseSender.getRecordSuccessResponse(resultMap, response);
	}
	
	@RequestMapping(value= {"/setStatusbyVendor"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> setStatusbyVendor(@RequestParam(value="orderProdId")long orderProdId,@RequestParam(value="status")String status,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultMap = new HashMap<String,Object>();
			resultMap.put("orderList",orderService.setStatusbyVendor(orderProdId,status));
		return CommonResponseSender.getRecordSuccessResponse(resultMap, response);
	}
	
	@RequestMapping(value= {"/setStatusbyAdmin"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> setStatusbyAdmin(@RequestParam(value="orderId")long orderId,@RequestParam(value="status")String status,@RequestParam(value="userId")long userId,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultMap = new HashMap<String,Object>();
			orderService.setStatusbyAdmin(orderId,status,userId);
		return CommonResponseSender.getRecordSuccessResponse(resultMap, response);
	}
	
	
	
	
	
	
	
	//ADMIN APIS
	

	@RequestMapping(value= {"/getLastOrders"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> getLastOrders(@RequestParam(value="offset")int offset,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultMap = new HashMap<String,Object>();
		resultMap.put("lastOrders",orderService.getLastOrders(offset));
		return CommonResponseSender.getRecordSuccessResponse(resultMap, response);
	}
	
	@RequestMapping(value= {"/getLastReturns"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> getLastReturns(@RequestParam(value="offset")int offset,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultMap = new HashMap<String,Object>();
		resultMap.put("returns",orderService.getLastReturns(offset));
		return CommonResponseSender.getRecordSuccessResponse(resultMap, response);
	}
	
	@RequestMapping(value="/getDetailofVendor",method= {RequestMethod.GET,RequestMethod.POST})
	public Map<String,Object> getDetailofVendor(@RequestParam(value="vendorId") long vendorId, @RequestBody Filter filter,HttpServletRequest request,HttpServletResponse response){
		
		final HashMap<String, Object> map = new HashMap<>();
		map.put("vendorInfo", userRepo.findById(vendorId));
		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
				filter.getSortingDirection() != null
				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter.getSortingField());
		map.put("vendorProducts",productVarRepo.findByProductUserId(vendorId,pagable));
		map.put("vendorAddress",addressRepo.findByUserId(vendorId));
		return CommonResponseSender.getRecordSuccessResponse(map, response);
		
	}
	
	@RequestMapping(value= {"/getAllOrderByStatus"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> getAllOrderByStatus(@RequestParam(value="offset")int offset,@RequestParam(value="status")String status,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultMap = new HashMap<String,Object>();
		resultMap.put("allOrders",orderService.getAllOrderByStatus(offset,status));
		return CommonResponseSender.getRecordSuccessResponse(resultMap, response);
	}
	
	@RequestMapping(value= {"/getAllWalletDetails"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> getAllWalletDetails(@RequestParam(value="userId")long userId,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultMap = new HashMap<String,Object>();
		resultMap.put("walletInfo",orderService.getAllWalletDetails(userId));
		return CommonResponseSender.getRecordSuccessResponse(resultMap, response);
	}
	@RequestMapping(value= {"/getAllVendorSales"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> getAllVendorSales(@RequestBody Filter filter,@RequestParam(value="userId")long userId,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultMap = new HashMap<String,Object>();
		resultMap.put("vendorSales",orderService.getAllVendorSales(userId,filter));
		return CommonResponseSender.getRecordSuccessResponse(resultMap, response);
	}
	
	@RequestMapping(value= {"/getAllCartAndWishlist"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> getAllCartAndWishlist(@RequestBody Filter filter,@RequestBody Filter filter1,@RequestParam(value="userId")long userId,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultMap = new HashMap<String,Object>();
		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
				filter.getSortingDirection() != null
				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter.getSortingField());
		
		resultMap.put("cartList",shoppingCartItemRepository.findByShoppingCartUserIdAndStatus(userId,1,pagable));
		
		final Pageable pagable1 = PageRequest.of(filter1.getOffset(), filter1.getLimit(),
				filter1.getSortingDirection() != null
				&& filter1.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter1.getSortingField());
		resultMap.put("wishList", wishlistRepository.findByUserIdAndStatus(userId,1,pagable1));
		
		return CommonResponseSender.getRecordSuccessResponse(resultMap, response);
	}
	
	
}