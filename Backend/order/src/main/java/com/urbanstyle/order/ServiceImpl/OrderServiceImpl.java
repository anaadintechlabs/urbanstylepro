package com.urbanstyle.order.ServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.anaadihsoft.common.DTO.UserOrderFetchDTO;
import com.anaadihsoft.common.DTO.UserOrderQtyDTO;
import com.anaadihsoft.common.DTO.UserOrderSaveDTO;
import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.Address;
import com.anaadihsoft.common.master.BankDetails;
import com.anaadihsoft.common.master.PaymentDetails;
import com.anaadihsoft.common.master.PaymentTransaction;
import com.anaadihsoft.common.master.ProductVariant;
import com.anaadihsoft.common.master.ReturnManagement;
import com.anaadihsoft.common.master.User;
import com.anaadihsoft.common.master.UserOrder;
import com.anaadihsoft.common.master.UserOrderProducts;
import com.urbanstyle.order.Contoller.PaymentConn;
import com.urbanstyle.order.Repository.AddressRepository;
import com.urbanstyle.order.Repository.BankRepository;
import com.urbanstyle.order.Repository.OrderRepository;
import com.urbanstyle.order.Repository.PaymentDetailsRepo;
import com.urbanstyle.order.Repository.PaymentTransactionRepo;
import com.urbanstyle.order.Repository.ProductVarientRepository;
import com.urbanstyle.order.Repository.ReturnOrder;
import com.urbanstyle.order.Repository.UserOrderProductRepository;
import com.urbanstyle.order.Repository.UserRepository;
import com.urbanstyle.order.Service.OrderService;
import com.urbanstyle.order.Service.PaymentTransactionService;


@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;


	
	@Autowired
	private ProductVarientRepository productVariantRepo;
	
	@Autowired
	private AddressRepository addressRepo;
	
	@Autowired 
	private UserRepository userRepo;
	
	@Autowired 
	private OrderRepository orderRepo;
	
	@Autowired
	private UserOrderProductRepository userOrderProdRepo;
	
	@Autowired
	private BankRepository bankRepo;
	
	@Autowired
	private PaymentTransactionRepo paymantTransactionRepo;
	
	@Autowired
	private PaymentDetailsRepo paymentDettailsRepo;
	
	@Autowired
	private ReturnOrder returnManagement;
	
	@Override
	public UserOrder saveorUpdate(UserOrderSaveDTO userOrder) {
		long userId = userOrder.getUserId();
		System.out.println("userOrder"+userId);
		List<UserOrderQtyDTO> userOrderList = userOrder.getUserOrderList();
		String paymentType = userOrder.getPaymentType();
		Address address = userOrder.getAddress();
		String from = userOrder.getFrom();
		String to = userOrder.getTo();
		double totalPrice = 0;
		UserOrder userOrderSave = new UserOrder();
		
		
		//Payment information cam be added later
		// get Total Price
		for(UserOrderQtyDTO userDTO : userOrderList) {
			long prodVarId = userDTO.getProductVariantId();
			Optional<ProductVariant> optionalp = productVariantRepo.findById(prodVarId);
	
			if(optionalp.isPresent()) {
				ProductVariant product  = optionalp.get();
				totalPrice  += (product.getDisplayPrice() * userDTO.getQty()) ;
			}
		}
		System.out.println("total prices"+totalPrice);
		// save adress in case of new or old hibernate manage
//		Note full object is to be send	
		//addressRepo.save(address);
			
			Optional<User> user = userRepo.findById(userId);
			User loginUser  = null;
			if(user.isPresent()) {
				System.out.println("user present");
				 loginUser = user.get();
				 userOrderSave.setUser(loginUser);
				 userOrderSave.setCreatedBy(loginUser.getName());
			}
			
			
			userOrderSave.setAddress(address);
			userOrderSave.setOrderTotalPrice(totalPrice);
			userOrderSave.setOrderPlacedDate(new Date());
			userOrderSave.setOrderStatus("PLACED");
		if(totalPrice > 0) {
			orderRepo.save(userOrderSave);
		}
			// save user Product order
			
			double totalAmount = 0;
			
			List<UserOrderProducts> TotalProducts = new ArrayList<UserOrderProducts>();
			
			for(UserOrderQtyDTO userDTO : userOrderList) {
				long prodVarId = userDTO.getProductVariantId();
				int quantity = userDTO.getQty();
				Optional<ProductVariant> optionalp = productVariantRepo.findById(prodVarId);
				ProductVariant productVar = null;
				if(optionalp.isPresent()) {
					productVar = optionalp.get();
				}
				if(productVar != null) {
					// addd reserved quantity					
					double oldQty = productVar.getReservedQuantity();
					productVar.setReservedQuantity(oldQty+quantity);
					productVariantRepo.save(productVar);
				UserOrderProducts userOrderProduct = new UserOrderProducts();
				userOrderProduct.setProduct(productVar);
				// addd reserved quantity
				userOrderProduct.setStatus(userOrderSave.getOrderStatus());
				userOrderProduct.setQuantity(quantity);
				userOrderProduct.setUserOrder(userOrderSave);
				userOrderProduct.setComment("COMMENT...");
				
				//will be taken from token
				Optional<User> vendorUser = userRepo.findById(Long.valueOf(productVar.getCreatedBy()));
				User userVndor  = null;
				if(vendorUser.isPresent()) {
					System.out.println("user present");
					userVndor = vendorUser.get();
				}
				
				userOrderProduct.setVendor(userVndor);
				
				totalAmount += productVar.getDisplayPrice()*quantity;
				
				TotalProducts.add(userOrderProduct);
			  }
			}
			
			userOrderProdRepo.saveAll(TotalProducts);
			
			Optional<BankDetails> bankDetails = bankRepo.findById(userOrder.getBankInfo().getId());
			BankDetails reqDetails = new BankDetails();
			if(!bankDetails.isPresent()) {
				reqDetails =  bankRepo.save(userOrder.getBankInfo());
			}else {
				reqDetails = bankDetails.get();
			}
			
			// paymentWork
			PaymentTransaction pt = new PaymentTransaction();
			//paymentConn.chargePayment(CustomerName);
			pt.setAmount(totalAmount);
			pt.setCard(reqDetails);
			pt.setCreatedBy(String.valueOf(userId));
			pt.setCreatedDate(new Date());
			pt.setCustId("");
			pt.setPaymentDesc("");
			
			pt = paymantTransactionRepo.save(pt);
			
			List<PaymentDetails> TotalPaymentRef = new ArrayList<PaymentDetails>();
			
			for(UserOrderQtyDTO userDTO : userOrderList) {
				long prodVarId = userDTO.getProductVariantId();
				int quantity = userDTO.getQty();
				Optional<ProductVariant> optionalp = productVariantRepo.findById(prodVarId);
				ProductVariant productVar = null;
				if(optionalp.isPresent()) {
					productVar = optionalp.get();
				}
				
				Optional<User> vendorUser = userRepo.findById(Long.valueOf(productVar.getCreatedBy()));
				User userVndor  = null;
				if(vendorUser.isPresent()) {
					System.out.println("user present");
					userVndor = vendorUser.get();
				}
				
				PaymentDetails pd = new PaymentDetails();
				pd.setAmount(productVar.getDisplayPrice()*quantity);
				pd.setCreatedBy(String.valueOf(loginUser.getId()));
				pd.setCreatedDate(new Date());
				pd.setProdVar(productVar);
				pd.setPt(pt);
				pd.setUser(loginUser);
				pd.setUserVendor(userVndor);
				pd.setOrder(userOrderSave);
				TotalPaymentRef.add(pd);
			}
			paymentDettailsRepo.saveAll(TotalPaymentRef);
			
			
		return null;
	}

	@Override
	public List<UserOrderFetchDTO> getOrderByUser(long userId,Filter filter) {
		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
				filter.getSortingDirection() != null
				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter.getSortingField());
		
		 List<UserOrder> userorder = orderRepository.findByUserId(userId,pagable);
		 List<UserOrderFetchDTO> userOrderFetch = new ArrayList<>();
		 for(UserOrder order : userorder) {
			 UserOrderFetchDTO dto = new UserOrderFetchDTO();
			 dto.setUserOrder(order);
			List<UserOrderProducts> listOfProdts =  userOrderProdRepo.findByUserOrderId(order.getId());
			dto.setUserOrderProductList(listOfProdts);
			userOrderFetch.add(dto);
		 }
		 return userOrderFetch;
	}

	@Override
	public List<UserOrderFetchDTO> getOrderById(long orderId) {
		 Optional<UserOrder> userorderOpt = orderRepository.findById(orderId);
		 List<UserOrderFetchDTO> userOrderFetch = new ArrayList<>();
		 if(userorderOpt.get() != null)
		 {
			 UserOrder userOrder=userorderOpt.get();
			UserOrderFetchDTO dto = new UserOrderFetchDTO();
			dto.setUserOrder(userOrder);
			List<UserOrderProducts> listOfProdts =  userOrderProdRepo.findByUserOrderId(userOrder.getId());
			dto.setUserOrderProductList(listOfProdts);
			userOrderFetch.add(dto);
		 }
		
		 
		 return userOrderFetch;
	}

	/*
	 * Method To get Order for Vendor
	 * @see com.urbanstyle.order.Service.OrderService#getVendorOrder(long)
	 */
	
	@Override
	public List<UserOrder> getVendorOrder(long vendorId) {
		
		List<UserOrder> userOrderProducts = userOrderProdRepo.findByvendorvendor_Id(vendorId);

		return userOrderProducts;
	}

	@Override
	public UserOrder setStatusbyUser(long orderId,String status,String reason,long userId) {
	if("CANCEL".equalsIgnoreCase(status)) {
		Optional<UserOrder> userOrder  = orderRepo.findById(orderId);
		if(userOrder.isPresent()) {
			UserOrder usrOrdr = userOrder.get();
			usrOrdr.setOrderStatus(status);
			orderRepo.save(usrOrdr);
			List<UserOrderProducts> userOrderProducts = userOrderProdRepo.findByUserOrderId(orderId);
			for(UserOrderProducts userOrdrProd :userOrderProducts) {
				userOrdrProd.setStatus(status);
				ProductVariant varient = userOrdrProd.getProduct();
				double oldReserved = varient.getReservedQuantity();
				oldReserved = oldReserved - userOrdrProd.getQuantity();
				varient.setReservedQuantity(oldReserved);
				double oldQty = varient.getTotalQuantity();
				oldQty = oldQty - userOrdrProd.getQuantity();
				varient.setTotalQuantity(oldQty);
				productVariantRepo.save(varient);
				userOrderProdRepo.save(userOrdrProd);
			}
		}
	 }else if("RETURN".equalsIgnoreCase(status)) {
				// return management
				UserOrder usrOrdr = null;
				Optional<UserOrder> userOrder  = orderRepo.findById(orderId);
				if(userOrder.isPresent()) {
					 usrOrdr = userOrder.get();
					usrOrdr.setOrderStatus(status);
					orderRepo.save(usrOrdr);
					List<UserOrderProducts> userOrderProducts = userOrderProdRepo.findByUserOrderId(orderId);
					for(UserOrderProducts userOrdrProd :userOrderProducts) {
						userOrdrProd.setStatus(status);
						userOrderProdRepo.save(userOrdrProd);
					}
					
					// maintain entry for return management with status
					ReturnManagement returnManage = new ReturnManagement();
					returnManage.setOrder(usrOrdr);
					returnManage.setReason(reason);
					returnManage.setStatus("INP");
					Optional<User> loginUser = userRepo.findById(userId);
					if(loginUser.isPresent()) {
						returnManage.setUser(loginUser.get());					
						returnManage.setCreatedBy(String.valueOf(loginUser.get().getId()));
					}
					returnManagement.save(returnManage);
			}
	   }
		return null;
	}

	@Override
	public UserOrderProducts setStatusbyVendor(long orderProdId, String status) {
		
		Optional<UserOrderProducts> userordrProd = userOrderProdRepo.findById(orderProdId);
		if(userordrProd.isPresent()) {
			UserOrderProducts userOrderProd = userordrProd.get();
			UserOrder userOrder  =  userOrderProd.getUserOrder();
			boolean updateStatus = true;
			List<UserOrderProducts> userOrderProducts = userOrderProdRepo.findByUserOrderId(userOrder.getId());
			for(UserOrderProducts userOrdrProd :userOrderProducts) {
				if(!status.equals(userOrdrProd.getStatus())) {
					updateStatus = false;
				}
			}
			if(updateStatus) {
				userOrder.setOrderStatus(status);
				orderRepo.save(userOrder);
			}
		}
		return null;
	}

	@Override
	public void setStatusbyAdmin(long orderId, String status,String reason,long userId) {
		if("DISPATCHED".equalsIgnoreCase(status)) {
			// update inventory and status
			Optional<UserOrder> userOrder  = orderRepo.findById(orderId);
			if(userOrder.isPresent()) {
				UserOrder usrOrdr = userOrder.get();
				usrOrdr.setOrderStatus(status);
				orderRepo.save(usrOrdr);
				List<UserOrderProducts> userOrderProducts = userOrderProdRepo.findByUserOrderId(orderId);
				for(UserOrderProducts userOrdrProd :userOrderProducts) {
					userOrdrProd.setStatus(status);
					ProductVariant varient = userOrdrProd.getProduct();
					double oldReserved = varient.getReservedQuantity();
					oldReserved = oldReserved - userOrdrProd.getQuantity();
					varient.setReservedQuantity(oldReserved);
					double oldQty = varient.getTotalQuantity();
					oldQty = oldQty - userOrdrProd.getQuantity();
					varient.setTotalQuantity(oldQty);
					productVariantRepo.save(varient);
					userOrderProdRepo.save(userOrdrProd);
				}
			}
		}else if("RETURN".equalsIgnoreCase(status)) {
			// return management
			UserOrder usrOrdr = null;
			Optional<UserOrder> userOrder  = orderRepo.findById(orderId);
			if(userOrder.isPresent()) {
				 usrOrdr = userOrder.get();
				usrOrdr.setOrderStatus(status);
				orderRepo.save(usrOrdr);
				List<UserOrderProducts> userOrderProducts = userOrderProdRepo.findByUserOrderId(orderId);
				for(UserOrderProducts userOrdrProd :userOrderProducts) {
					userOrdrProd.setStatus(status);
					userOrderProdRepo.save(userOrdrProd);
				}
				
				// maintain entry for return management with status
				ReturnManagement returnManage = new ReturnManagement();
				returnManage.setOrder(usrOrdr);
				returnManage.setReason(reason);
				returnManage.setStatus("INP");
				Optional<User> loginUser = userRepo.findById(userId);
				if(loginUser.isPresent()) {
					returnManage.setUser(loginUser.get());					
					returnManage.setCreatedBy(String.valueOf(loginUser.get().getId()));
				}
				returnManagement.save(returnManage);
		}
	 }
	}

	@Override
	public List<UserOrderProducts> getOrderProductForVendor(long vendorId, long orderId) {
		return userOrderProdRepo.findByUserOrderIdAndVendorId(orderId,vendorId);
	}

	@Override
	public Object setStatusbyVendorForCompleteOrder(long orderId, String status) {
		Optional<UserOrder> userOrderOpt  =  orderRepo.findById(orderId);
		if(userOrderOpt.isPresent())
		{
			UserOrder userOrder=userOrderOpt.get();
		List<UserOrderProducts> userOrderProducts = userOrderProdRepo.findByUserOrderId(userOrder.getId());
		for(UserOrderProducts userOrdrProd :userOrderProducts) {
			userOrdrProd.setStatus(status);
			
		}
			userOrderProdRepo.saveAll(userOrderProducts);
			userOrder.setOrderStatus(status);
			orderRepo.save(userOrder);
		
		}
		return "updated";
	}

}