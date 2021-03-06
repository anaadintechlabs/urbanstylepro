package com.urbanstyle.order.Contoller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

import com.anaadihsoft.common.DTO.AffiliatetransactionDTO;
import com.anaadihsoft.common.DTO.OrderUiDTO;
import com.anaadihsoft.common.DTO.UserOrderQtyDTO;
import com.anaadihsoft.common.DTO.UserOrderSaveDTO;
import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.AffiliateCommisionOrder;
import com.anaadihsoft.common.master.UserOrder;
import com.urbanstyle.order.Repository.AddressRepository;
import com.urbanstyle.order.Repository.AffiliateCommisionOrderRepo;
import com.urbanstyle.order.Repository.PaymentWalletTransactionRepo;
import com.urbanstyle.order.Repository.ProductVarientRepository;
import com.urbanstyle.order.Repository.ReturnOrder;
import com.urbanstyle.order.Repository.ShoppingCartItemRepository;
import com.urbanstyle.order.Repository.UserRepository;
import com.urbanstyle.order.Repository.UserWalletRepo;
import com.urbanstyle.order.Repository.WishListRepository;
import com.urbanstyle.order.Service.OrderService;
import com.urbanstyle.order.Service.PaymentTransactionService;
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
	private AffiliateCommisionOrderRepo affiliateorderRepo;
	

	@Autowired
	private UserWalletRepo userWalletRep;

	@Autowired
	private PaymentWalletTransactionRepo paymentwalletTransactionRepo;

	@Autowired
	private ReturnOrder returnManagement;
	
	@Autowired
	private PaymentTransactionService paymentTransactionService; 

	/**
	 * 
	 * @param request
	 * @param response
	 * @param userDetailSave
	 * @return API TO SAVE ORDER
	 */
	@RequestMapping(value= {"/saveOrder"},method= {RequestMethod.POST})
	public Map<String,Object> saveOrder(HttpServletRequest request,HttpServletResponse response,@RequestBody UserOrderSaveDTO userDetailSave){
		Map<String, Object> resultMap = new HashMap<String,Object>();
			resultMap.put("order",orderService.saveorUpdate(userDetailSave));
		return CommonResponseSender.createdSuccessResponse(resultMap, response);
	}
	
	/**
	 * 
	 * @param request
	 * @param filter
	 * @param response
	 * @param userId
	 * @return GET ALL ORDER OF A USER based on filter and search string
	 */
	@RequestMapping(value= {"/getOrderByUser"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> getOrderByUser(HttpServletRequest request,
			@RequestBody Filter filter,HttpServletResponse response,@RequestParam(value="userId",required=true) String userId ){
		Map<String, Object> resultMap = new HashMap<String,Object>();
		
			resultMap.put("orderList",orderService.getOrderProductByUser(Long.parseLong(userId),filter));
			resultMap.put("count",orderService.getCountOrderProductByUser(Long.parseLong(userId),filter));
			
			return CommonResponseSender.getRecordSuccessResponse(resultMap, response);

	}
	
	

	/**
	 * NOT IN USE
	 * @param request
	 * @param response
	 * @param orderId
	 * @return GET SINGLE ORDER DETAILS
	 */
	@RequestMapping(value= {"/getOrderById"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> getOrderById(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="userOrderId",required=true) String userOrderId ){
		Map<String, Object> resultMap = new HashMap<String,Object>();
		try {
			resultMap.put("orderDetails",orderService.getOrderById(Long.parseLong(userOrderId)));
		}catch(Exception e) {
			resultMap.put("RESPONSE", "ERROR");	
		}
		return CommonResponseSender.getRecordSuccessResponse(resultMap, response);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return PAYMENT RELATED ACTIVITIRES
	 */
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
	}
	
	/**
	 * 
	 * @param vendorId
	 * @param request
	 * @param response
	 * @return ORDER OF VENDOR
	 */
//	@RequestBody Filter filter,
	@RequestMapping(value= {"/getOrderForVendor"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> getOrderForVendor(@RequestParam(value="vendorId")long vendorId,
			@RequestBody Filter filter,
			HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("orderList",orderService.getVendorOrder(vendorId,filter));
			resultMap.put("count",orderService.getVendorOrderCount(vendorId,filter));
		return CommonResponseSender.getRecordSuccessResponse(resultMap, response);
	}
	
	
	/**
	 * 
	 * @param vendorId
	 * @param status
	 * @param request
	 * @param response
	 * @return ORDER BASED ON STATUS
	 */
	@RequestMapping(value= {"/getOrderForVendorByStatus"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> getOrderForVendorByStatus(@RequestParam(value="vendorId")long vendorId,
			@RequestBody Filter filter,@RequestParam(value="status")String status,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultMap = new HashMap<String,Object>();
			resultMap.put("orderList",orderService.getOrderForVendorByStatus(vendorId,status,filter));
			resultMap.put("count",orderService.getVendorOrderCountByStatus(vendorId,status,filter));
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
	public Map<String,Object> getOrderProductForVendor(@RequestParam(value="vendorId")long vendorId,@RequestParam(value="orderId")long orderId,
			@RequestParam(value="orderProductId")long orderProductId,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultMap = new HashMap<String,Object>();
		OrderUiDTO orderDto=orderService.getOrderProductForVendor(vendorId,orderProductId);
		resultMap.put("orderDetails",orderDto);
		

		if(orderDto.getStatus().equals("COMPLETE")) {
			resultMap.put("transactionDetails", orderService.getTransactionofOrder(orderProductId));
		}
		
		
		//resultMap.put("orderDetails", orderService.getOrderDetails(orderId));
		return CommonResponseSender.getRecordSuccessResponse(resultMap, response);
	}
	
	/**
	 * NOT IN USER
	 * @param orderId
	 * @param status
	 * @param userId
	 * @param reason
	 * @param orderProdId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value= {"/setStatusbyUser"},method= {RequestMethod.POST,RequestMethod.GET})
	//DEPRICATED
	public Map<String,Object> setStatusbyUser(@RequestParam(value="orderId")long orderId,@RequestParam(value="status")String status,@RequestParam(value="userId")long userId,@RequestParam(value="reason")String reason,@RequestParam(value="orderProdId")long orderProdId,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultMap = new HashMap<String,Object>();
			resultMap.put("orderList",orderService.setStatusbyUser(orderId,status,reason,userId,orderProdId));
		return CommonResponseSender.getRecordSuccessResponse(resultMap, response);
	}
	
	/**
	 * 
	 * @param orderId
	 * @param userId
	 * @param request
	 * @param response
	 * @return ACTION BASED ON ORDER CANCELLED BY USER
	 */
	@RequestMapping(value= {"/cancelOrderByUser"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> cancelOrderByUser(@RequestParam(value="orderId")long orderId,
			@RequestParam(value="orderProductId")long orderProductId,
			@RequestParam(value="userId")long userId,
			HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("orderList",orderService.cancelOrderByUser(orderId,userId,orderProductId));
		return CommonResponseSender.getRecordSuccessResponse(resultMap, response);
	}
	

	/**
	 * 
	 * @param orderId
	 * @param status
	 * @param request
	 * @param response
	 * @return ORDER COMPLETION
	 */
	@RequestMapping(value= {"/setStatusbyVendorForCompleteOrder"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> setStatusbyVendorForCompleteOrder(@RequestParam(value="orderId")long orderId,@RequestParam(value="status")String status,@RequestParam(value="orderProdId")long orderProdId,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultMap = new HashMap<String,Object>();
			resultMap.put("orderList",orderService.setStatusbyVendorForCompleteOrder(orderId,status,orderProdId));
		return CommonResponseSender.getRecordSuccessResponse(resultMap, response);
	}
	
	/**
	 * 
	 * @param orderProdId
	 * @param status
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value= {"/setStatusbyVendor"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> setStatusbyVendor(@RequestParam(value="orderProdId")long orderProdId,@RequestParam(value="status")String status,
			@RequestParam(value="trackingId",required=false)String trackingId,
			@RequestParam(value="trackingLink",required = false)String link,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultMap = new HashMap<String,Object>();
			resultMap.put("orderList",orderService.setStatusbyVendor(orderProdId,status,trackingId,link));
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
	@RequestMapping(value= {"/setStatusbyAdmin"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> setStatusbyAdmin(@RequestParam(value="orderId")long orderId,
			@RequestParam(value="orderProdId")long orderProdId,@RequestParam(value="status")String status,@RequestParam(value="userId")long userId,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultMap = new HashMap<String,Object>();
			orderService.setStatusbyAdmin(orderId,orderProdId,status,userId);
		return CommonResponseSender.getRecordSuccessResponse(resultMap, response);
	}
	
	/**
	 * 
	 * @param orderId
	 * @param orderProdId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value= {"/getTransactionofOrder"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> getTransactionofOrder(@RequestParam(value="orderId")long orderId,
			@RequestParam(value="orderProdId")long orderProdId,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultMap = new HashMap<String,Object>();
		resultMap.put("allTransaction",orderService.getTransactionofOrder(orderProdId));
		return CommonResponseSender.getRecordSuccessResponse(resultMap, response);
	}

	/**
	 * 
	 * @param affiliateId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value= {"/getTransactionofAffiliate"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> getTransactionofAffiliate(@RequestParam(value="affiliateId")long affiliateId,HttpServletRequest request,HttpServletResponse response){
		
		List<AffiliatetransactionDTO> afDTo = new ArrayList<>();
		Map<String, Object> resultMap = new HashMap<String,Object>();
		List<AffiliateCommisionOrder> allaffiliateTrans = affiliateorderRepo.findByAffiliateIdId(affiliateId);
		for(AffiliateCommisionOrder affOrder : allaffiliateTrans) {
			AffiliatetransactionDTO dto = new AffiliatetransactionDTO();
			dto.setAffiliateId(affiliateId);
			dto.setAffUserDesc(affOrder.getAffiliateId().getName());
			dto.setAmount(affOrder.getOrderprodid().getOrderProductPrice());
			dto.setCommision(affOrder.getCommision());
			dto.setCustid(affOrder.getUser().getId());
			dto.setOrderdate(affOrder.getOrderdate());
			dto.setOrderprodId(affOrder.getOrderprodid().getId());
			dto.setProdDesc(affOrder.getProdvarid().getVariantName());
			dto.setQty(affOrder.getOrderprodid().getQuantity());
			dto.setStatus(affOrder.getStatus());
			dto.setVarid(affOrder.getProdvarid().getProductVariantId());
			afDTo.add(dto);
		}
		resultMap.put("allTransaction",afDTo);
		return CommonResponseSender.getRecordSuccessResponse(resultMap, response);
	}
	
	/**
	 * 
	 * @param userId
	 * @param request
	 * @param response
	 * @return wallet as well as incoming and outgoing transaction
	 */
	@RequestMapping(value= {"/getWalletByUser"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> getWalletByUser(@RequestParam(value="userId")long userId,
			@RequestBody Filter filter,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultMap = new HashMap<String,Object>();
		resultMap.put("walletDetails",userWalletRep.findByUserId(userId));
		
		resultMap.put("incomingTransactions", paymentTransactionService.getAllIncomingTransactions(userId,filter));
		resultMap.put("incomingCount", paymentTransactionService.getAllIncomingTransactionsCount(userId,filter));
		
		resultMap.put("outgoingTransactions", paymentTransactionService.getAllOutgoingTransactions(userId,filter));
		resultMap.put("outgoingCount", paymentTransactionService.getAllOutgoingTransactionsCount(userId,filter));

		return CommonResponseSender.getRecordSuccessResponse(resultMap, response);
	}

	
	/**
	 * 
	 * @param orderProdId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value= {"/getReturnTransOrder"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> getReturnTransOrder(@RequestParam(value="orderProdId")long orderProdId,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultMap = new HashMap<String,Object>();
		resultMap.put("ReturnTransaction",paymentwalletTransactionRepo.getTransactionofOrder(orderProdId, "RT"));
		return CommonResponseSender.getRecordSuccessResponse(resultMap, response);
	}
	
	
	
	// API To get all payment transaction Before Payment
	
	/**
	 * 
	 * @param orderProdId
	 * @param request
	 * @param response
	 * @return
	 */
		@RequestMapping(value= {"/getTransactionSummaryofOrder"},method= {RequestMethod.POST,RequestMethod.GET})
		public Map<String,Object> getTransactionSummaryofOrder(@RequestParam(value="orderProdId")long orderProdId,HttpServletRequest request,HttpServletResponse response){
			Map<String, Object> resultMap = new HashMap<String,Object>();
			resultMap.put("TransactionSummary",orderService.getTransactionSummaryofOrder(orderProdId));
			return CommonResponseSender.getRecordSuccessResponse(resultMap, response);
		}
		
	
		/**
		 * 
		 * @param request
		 * @param response
		 * @param userOrderList
		 * @return
		 */
	@RequestMapping(value="/canPlaceOrderOrNot",method=RequestMethod.POST)
	public Map<String,Object> canPlaceOrderOrNot(
			HttpServletRequest request,HttpServletResponse response,
			@RequestBody List<UserOrderQtyDTO> userOrderList){
		Map<String, Object> resultMap = new HashMap<String,Object>();
		resultMap.put("canPlace",orderService.canPlaceOrderOrNot(userOrderList));
		return CommonResponseSender.getRecordSuccessResponse(resultMap, response);
	}
	
	
	
		
}