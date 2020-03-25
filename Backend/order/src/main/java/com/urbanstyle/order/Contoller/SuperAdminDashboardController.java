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
import org.springframework.web.bind.annotation.RestController;

import com.anaadihsoft.common.external.Filter;
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
public class SuperAdminDashboardController {

	@Autowired
	private OrderService orderService;
	
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
	
	
	/**
	 * 
	 * @param userType
	 * @param request
	 * @param response
	 * @return TOP 5 USER
	 */
	@RequestMapping(value= {"/getTop5Users"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> getTop5Users(@RequestParam(value="userType")String userType,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultMap = new HashMap<String,Object>();
		resultMap.put("topUsers",orderService.getTop5Users(userType));
		return CommonResponseSender.getRecordSuccessResponse(resultMap, response);
	}

	/**
	 * 
	 * @param offset
	 * @param request
	 * @param response
	 * @return LAST 5 ORDERS
	 */
	@RequestMapping(value= {"/getLastOrders"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> getLastOrders(@RequestParam(value="offset")int offset,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultMap = new HashMap<String,Object>();
		resultMap.put("lastOrders",orderService.getLastOrders(offset));
		return CommonResponseSender.getRecordSuccessResponse(resultMap, response);
	}
	
	/**
	 * 
	 * @param offset
	 * @param request
	 * @param response
	 * @return LAST 5 RETURNS
	 */
	@RequestMapping(value= {"/getLastReturns"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> getLastReturns(@RequestParam(value="offset")int offset,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultMap = new HashMap<String,Object>();
		resultMap.put("returns",orderService.getLastReturns(offset));
		return CommonResponseSender.getRecordSuccessResponse(resultMap, response);
	}
	

	/**
	 * 
	 * @param vendorId
	 * @param filter
	 * @param request
	 * @param response
	 * @return VENDOR INFO LIKE HIS PRODUCT ORDER AND ADDRESS
	 */
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
		map.put("vendorOrders",orderService.getVendorOrder(vendorId));
		map.put("vendorAddress",addressRepo.findByUserId(vendorId));
		return CommonResponseSender.getRecordSuccessResponse(map, response);
		
	}
	
	/**
	 * 
	 * @param offset
	 * @param status
	 * @param request
	 * @param response
	 * @return ORDER BASED ON STATUS LIKE DISPATCHED, 
	 */
	@RequestMapping(value= {"/getAllOrderByStatus"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> getAllOrderByStatus(@RequestParam(value="offset")int offset,@RequestParam(value="status")String status,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultMap = new HashMap<String,Object>();
		resultMap.put("allOrders",orderService.getAllOrderByStatus(offset,status));
		return CommonResponseSender.getRecordSuccessResponse(resultMap, response);
	}
	
	/**
	 * ALLL WALLET DETAILS BASED ON USER ID
	 * @param userId
	 * @param request
	 * @param response
	 * @return 
	 */
	@RequestMapping(value= {"/getAllWalletDetails"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> getAllWalletDetails(@RequestParam(value="userId")long userId,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultMap = new HashMap<String,Object>();
		resultMap.put("walletInfo",orderService.getAllWalletDetails(userId));
		return CommonResponseSender.getRecordSuccessResponse(resultMap, response);
	}
	
	/**
	 * ALL SALES OF VENDOR
	 * @param filter
	 * @param userId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value= {"/getAllVendorSales"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> getAllVendorSales(@RequestBody Filter filter,@RequestParam(value="userId")long userId,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultMap = new HashMap<String,Object>();
		resultMap.put("vendorSales",orderService.getAllVendorSales(userId,filter));
		return CommonResponseSender.getRecordSuccessResponse(resultMap, response);
	}
	
	/**
	 * WISHLIST AND CART LIST OF USER
	 * @param filter
	 * @param filter1
	 * @param userId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value= {"/getAllCartAndWishlist"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> getAllCartAndWishlist(@RequestBody Filter filter,@RequestParam(value="userId")long userId,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> resultMap = new HashMap<String,Object>();
		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
				filter.getSortingDirection() != null
				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter.getSortingField());
		
		resultMap.put("cartList",shoppingCartItemRepository.findByShoppingCartUserIdAndStatus(userId,1,pagable));
		
		final Pageable pagable1 = PageRequest.of(filter.getOffset(), filter.getLimit(),
				filter.getSortingDirection() != null
				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter.getSortingField());
		resultMap.put("wishList", wishlistRepository.findByUserIdAndStatus(userId,1,pagable1));
		
		return CommonResponseSender.getRecordSuccessResponse(resultMap, response);
	}
	
	/**
	 * ALL ORDER IRRESPECTIVE OF STATUS FOR SUPER ADMIN
	 * @param request
	 * @param filter
	 * @param response
	 * @return
	 */
	@RequestMapping(value= {"/getAllOrderForSuperAdmin"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> getAllOrderForSuperAdmin(HttpServletRequest request,
			@RequestBody Filter filter,HttpServletResponse response ){
		Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("orderList",orderService.getAllOrderForSuperAdmin(filter));
			resultMap.put("count",orderService.countForSuperAdmin());
		return CommonResponseSender.getRecordSuccessResponse(resultMap, response);

	}
}
