package com.urbanstyle.order.ServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
import com.anaadihsoft.common.master.BankcardInfo;
import com.anaadihsoft.common.master.PaymentDetails;
import com.anaadihsoft.common.master.PaymentTransaction;
import com.anaadihsoft.common.master.PaymentWalletDistribution;
import com.anaadihsoft.common.master.PaymentWalletTransaction;
import com.anaadihsoft.common.master.ProductVariant;
import com.anaadihsoft.common.master.ReturnManagement;
import com.anaadihsoft.common.master.User;
import com.anaadihsoft.common.master.UserOrder;
import com.anaadihsoft.common.master.UserOrderProducts;
import com.anaadihsoft.common.master.UserWallet;
import com.urbanstyle.order.Contoller.PaymentConn;
import com.urbanstyle.order.Repository.AddressRepository;
import com.urbanstyle.order.Repository.BankRepository;
import com.urbanstyle.order.Repository.BankcardInfoRepo;
import com.urbanstyle.order.Repository.OrderRepository;
import com.urbanstyle.order.Repository.PaymentDetailsRepo;
import com.urbanstyle.order.Repository.PaymentTransactionRepo;
import com.urbanstyle.order.Repository.PaymentWalletDistributionRepo;
import com.urbanstyle.order.Repository.PaymentWalletTransactionRepo;
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
	
	@Autowired
	private PaymentWalletDistributionRepo paymentWalletDistRepo;
	
	@Autowired
	private PaymentWalletTransactionRepo paymentwalletTransactionRepo;
	
	@Autowired
	private com.urbanstyle.order.Repository.UserWalletRepo UserWalletRepo;
	@Autowired
	private BankcardInfoRepo bankCardInfoRepo;
	
	@Override
	public UserOrder saveorUpdate(UserOrderSaveDTO userOrder) {
		long userId = userOrder.getUserId();
		List<UserOrderQtyDTO> userOrderList = userOrder.getUserOrderList();
		Address address = userOrder.getAddress();
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
			
			BankcardInfo bankCardInfo = userOrder.getBankCardDetails();
			Optional<BankcardInfo> bankCardInfoold = bankCardInfoRepo.findByCardNumber(bankCardInfo.getCardNumber());
			if(!bankCardInfoold.isPresent()) {
				bankCardInfo=bankCardInfoRepo.save(bankCardInfo);	
				
			}
			userOrderSave.setBankCardInfo(bankCardInfo);
		if(totalPrice > 0) {
			orderRepo.save(userOrderSave);
		}
			// save user Product order
			
			double totalAmount = 0;
			
			List<UserOrderProducts> TotalProducts = new ArrayList<UserOrderProducts>();
			long vendorId;
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
				userOrderProduct.setStatus("PLACED");
				// addd reserved quantity
				userOrderProduct.setStatus(userOrderSave.getOrderStatus());
				userOrderProduct.setQuantity(quantity);
				userOrderProduct.setOrderProductPrice(productVar.getDisplayPrice() * quantity);
				userOrderProduct.setUserOrder(userOrderSave);
				userOrderProduct.setComment("COMMENT...");
				
				//will be taken from token
				vendorId=Long.valueOf(productVar.getCreatedBy());
				Optional<User> vendorUser = userRepo.findById(Long.valueOf(productVar.getCreatedBy()));
				User userVndor  = null;
				if(vendorUser.isPresent()) {
					
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
			// Save Card Info
			
						
						
						
			// paymentWork
			PaymentTransaction pt = new PaymentTransaction();
			//paymentConn.chargePayment(CustomerName);
			pt.setAmount(totalAmount);
			pt.setCard(bankCardInfo);
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
			
			// update wallet and add entry to paymwnt wallet entry from user to super admin
			
			User admin = userRepo.findByUserType("SUPERADMIN");
			if(admin!=null)
			{
			PaymentWalletTransaction pwt = new PaymentWalletTransaction();
			pwt.setAmount(totalAmount);
			pwt.setCreatedDate(new Date());
			pwt.setOrder(userOrderSave);
			pwt.setReciever(String.valueOf(admin.getId()));
			pwt.setSender(userOrderSave.getUser());
			pwt.setStatus("1"); 
			pwt.setType("OP");  //Order Placed
			paymentwalletTransactionRepo.save(pwt);			

			UserWallet userWalletAdmin = UserWalletRepo.findByUserId(admin.getId()); 
			if(userWalletAdmin != null) {
				double amount = userWalletAdmin.getAmount();
				userWalletAdmin.setAmount(amount + totalAmount);
				userWalletAdmin.setModifiedDate(new Date());
				userWalletAdmin.setStatus("1");
				userWalletAdmin.setUser(admin);
				UserWalletRepo.save(userWalletAdmin);
			}else {
				userWalletAdmin = new UserWallet();
				userWalletAdmin.setAmount(totalAmount);
				userWalletAdmin.setCreatedDate(new Date());
				userWalletAdmin.setModifiedDate(new Date());
				userWalletAdmin.setStatus("1");
				userWalletAdmin.setUser(admin);
				UserWalletRepo.save(userWalletAdmin);
			}
			}
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
		
		return userOrderProdRepo.findByvendorvendor_Id(vendorId);

	}

	@Override
	public UserOrder setStatusbyUser(long orderId,String status,String reason,long userId) {
	if("CANCEL".equalsIgnoreCase(status)) {
		UserOrder usrOrdr = null;
		Optional<UserOrder> userOrder  = orderRepo.findById(orderId);
		if(userOrder.isPresent()) {
			 usrOrdr = userOrder.get();
			usrOrdr.setOrderStatus(status);
			orderRepo.save(usrOrdr);
			List<UserOrderProducts> userOrderProducts = userOrderProdRepo.findByUserOrderId(orderId);
			for(UserOrderProducts userOrdrProd :userOrderProducts) {
				userOrdrProd.setStatus(status);
				ProductVariant varient = userOrdrProd.getProduct();
				double oldReserved = varient.getReservedQuantity();
				oldReserved = oldReserved - userOrdrProd.getQuantity();
				varient.setReservedQuantity(oldReserved);
//				double oldQty = varient.getTotalQuantity();
//				oldQty = oldQty - userOrdrProd.getQuantity();
//				varient.setTotalQuantity(oldQty);
				productVariantRepo.save(varient);
				userOrderProdRepo.save(userOrdrProd);
			}
			// update wallet and add entry to payment wallet entry from Super admin to user
			
						User admin = userRepo.findById((long) 0).get();
						PaymentWalletTransaction pwt = new PaymentWalletTransaction();
						pwt.setAmount(usrOrdr.getOrderTotalPrice());
						pwt.setCreatedDate(new Date());
						pwt.setOrder(usrOrdr);
						pwt.setReciever(String.valueOf(usrOrdr.getUser().getId()));
						pwt.setSender(admin);
						pwt.setStatus("1"); 
						pwt.setType("CANCEL");  //Order Placed
						paymentwalletTransactionRepo.save(pwt);			

						UserWallet userWalletAdmin = UserWalletRepo.findByUserId(admin.getId()); 
						if(userWalletAdmin != null) {
							double amount = userWalletAdmin.getAmount();
							userWalletAdmin.setAmount(amount - usrOrdr.getOrderTotalPrice());
							userWalletAdmin.setModifiedDate(new Date());
							userWalletAdmin.setStatus("1");
							userWalletAdmin.setUser(admin);
							UserWalletRepo.save(userWalletAdmin);
						}
						// update wallet for user
						UserWallet userWalletuser = UserWalletRepo.findByUserId(usrOrdr.getUser().getId()); 
						if(userWalletuser != null) {
							double amount = userWalletuser.getAmount();
							userWalletuser.setAmount(amount + usrOrdr.getOrderTotalPrice());
							userWalletuser.setModifiedDate(new Date());
							userWalletuser.setStatus("1");
							userWalletuser.setUser(usrOrdr.getUser());
							UserWalletRepo.save(userWalletuser);
						}else {
							userWalletuser = new UserWallet();
							userWalletuser.setAmount(usrOrdr.getOrderTotalPrice());
							userWalletuser.setModifiedDate(new Date());
							userWalletuser.setStatus("1");
							userWalletuser.setUser(usrOrdr.getUser());
							UserWalletRepo.save(userWalletuser);
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
			userOrderProd.setStatus(status);
			userOrderProd=userOrderProdRepo.save(userOrderProd);
			
			UserOrder userOrder  =  userOrderProd.getUserOrder();
			boolean updateStatus = true;
			List<UserOrderProducts> userOrderProducts = userOrderProdRepo.findByUserOrderId(userOrder.getId());
			for(UserOrderProducts userOrdrProd :userOrderProducts) {
				if(!status.equals(userOrdrProd.getStatus())) {
					updateStatus = false;
					break;
				}
			}
			if(updateStatus) {
				System.out.println("update status of parent order");
				userOrder.setOrderStatus(status);
				userOrder=orderRepo.save(userOrder);
				//If every status is DISPATCHED
				if("DISPATCHED".equalsIgnoreCase(status)) {
					updateBalanceInCaseOfDispatch(userOrder);
				}
			}
		}
		return null;
	}

	private void updateBalanceInCaseOfDispatch(UserOrder userOrder) {
		List<UserOrderProducts> userOrderProducts = userOrderProdRepo.findByUserOrderId(userOrder.getId());
		for(UserOrderProducts userOrdrProd :userOrderProducts) {
			
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

	@Override
	public void setStatusbyAdmin(long orderId, String status,long userId) {
		//admin can dispatch product
//		if("DISPATCHED".equalsIgnoreCase(status)) {
//			// update inventory and status
//			Optional<UserOrder> userOrder  = orderRepo.findById(orderId);
//			if(userOrder.isPresent()) {
//				UserOrder usrOrdr = userOrder.get();
//				usrOrdr.setOrderStatus(status);
//				orderRepo.save(usrOrdr);
//				List<UserOrderProducts> userOrderProducts = userOrderProdRepo.findByUserOrderId(orderId);
//				for(UserOrderProducts userOrdrProd :userOrderProducts) {
//					userOrdrProd.setStatus(status);
//					ProductVariant varient = userOrdrProd.getProduct();
//					double oldReserved = varient.getReservedQuantity();
//					oldReserved = oldReserved - userOrdrProd.getQuantity();
//					varient.setReservedQuantity(oldReserved);
//					double oldQty = varient.getTotalQuantity();
//					oldQty = oldQty - userOrdrProd.getQuantity();
//					varient.setTotalQuantity(oldQty);
//					productVariantRepo.save(varient);
//					userOrderProdRepo.save(userOrdrProd);
//				}
//			}
//		}else
		//admin can return product
//			if("RETURN".equalsIgnoreCase(status)) {
//			// return management
//			UserOrder usrOrdr = null;
//			Optional<UserOrder> userOrder  = orderRepo.findById(orderId);
//			if(userOrder.isPresent()) {
//				 usrOrdr = userOrder.get();
//				usrOrdr.setOrderStatus(status);
//				orderRepo.save(usrOrdr);
//				List<UserOrderProducts> userOrderProducts = userOrderProdRepo.findByUserOrderId(orderId);
//				for(UserOrderProducts userOrdrProd :userOrderProducts) {
//					userOrdrProd.setStatus(status);
//					userOrderProdRepo.save(userOrdrProd);
//				}
//				
//				// maintain entry for return management with status
//				ReturnManagement returnManage = new ReturnManagement();
//				returnManage.setOrder(usrOrdr);
//				returnManage.setReason(reason);
//				returnManage.setStatus("INP");
//				Optional<User> loginUser = userRepo.findById(userId);
//				if(loginUser.isPresent()) {
//					returnManage.setUser(loginUser.get());					
//					returnManage.setCreatedBy(String.valueOf(loginUser.get().getId()));
//				}
//				returnManagement.save(returnManage);
//		}
//	 }else
		 if("COMPLETE".equalsIgnoreCase(status)) {
		 HashMap<Long,Double> userBal = new HashMap<>();
		 Optional<UserOrder> userOrder  = orderRepo.findById(orderId);
			if(userOrder.isPresent()) {
				UserOrder usrOrdr = userOrder.get();
				usrOrdr.setOrderStatus(status);
				usrOrdr.setOrderPaidDate(new Date());
				orderRepo.save(usrOrdr);
				List<UserOrderProducts> userOrderProducts = userOrderProdRepo.findByUserOrderId(orderId);
				for(UserOrderProducts userOrdrProd :userOrderProducts) {
					userOrdrProd.setStatus(status);
					ProductVariant varient = userOrdrProd.getProduct();
					if(userBal.get(Long.valueOf(varient.getCreatedBy())) != null) {
						double oldAmount = userBal.get(Long.valueOf(varient.getCreatedBy()));
						oldAmount += userOrdrProd.getQuantity()*varient.getDisplayPrice();
						userBal.put(Long.valueOf(varient.getCreatedBy()), oldAmount);
					}else {
						double amount = userOrdrProd.getQuantity()*varient.getDisplayPrice();
						userBal.put(Long.valueOf(varient.getCreatedBy()), amount);
					}
					userOrderProdRepo.save(userOrdrProd);
				}
			}
			
			// Here Update Payment after calculate from each order product
			
			// add entry when user sends all payment to super admin
			
			User admin = userRepo.findByUserType("SUPERADMIN");
			UserWallet userWalletAdmin = UserWalletRepo.findByUserId(admin.getId()); 
			double orderTotalPrice = userOrder.get().getOrderTotalPrice();

			
			// Here super admin distributes to all sources
			
			long TotalPerc = 0;
			long percToVendor = 0; 
			Set<Long> allvendor = userBal.keySet();
				Iterable<PaymentWalletDistribution> allSources =  paymentWalletDistRepo.findAll();
				for(PaymentWalletDistribution source : allSources) {
					long perc = source.getPerc();
					if(source.getDistributionTo().equals("AFFILIATE")) {
						String affiliatid = source.getSource();
						User affiliatiduser = userRepo.findById(Long.parseLong(affiliatid)).get();
						UserWallet userWalletAff = UserWalletRepo.findByUserId(affiliatiduser.getId());
						double amountToVendor = (orderTotalPrice*source.getPerc())/100;
						if(userWalletAff != null) {
							double amount = userWalletAff.getAmount();
							userWalletAff.setAmount(amount + amountToVendor);
							userWalletAff.setModifiedDate(new Date());
							userWalletAff.setStatus("1");
							userWalletAff.setUser(affiliatiduser);
							UserWalletRepo.save(userWalletAff);
						}else {
							userWalletAff = new UserWallet();
							userWalletAff.setAmount(amountToVendor);
							userWalletAff.setCreatedDate(new Date());
							userWalletAff.setModifiedDate(new Date());
							userWalletAff.setStatus("1");
							userWalletAff.setUser(affiliatiduser);
							UserWalletRepo.save(userWalletAff);
						}
						// update wallet for admin
						if(userWalletAdmin != null) {
							double amount = userWalletAdmin.getAmount();
							userWalletAdmin.setAmount(amount - amountToVendor);
							userWalletAdmin.setModifiedDate(new Date());
							userWalletAdmin.setStatus("1");
							userWalletAdmin.setUser(admin);
							UserWalletRepo.save(userWalletAdmin);
						}
					}
						TotalPerc += perc;  
					double amount = (orderTotalPrice*perc)/100;
					PaymentWalletTransaction pwtinner = new PaymentWalletTransaction();
					pwtinner.setAmount(amount);
					pwtinner.setCreatedDate(new Date());
					pwtinner.setOrder(userOrder.get());
					pwtinner.setReciever(source.getSource());
					pwtinner.setSender(admin);
					pwtinner.setStatus("1"); 
					pwtinner.setType("OP");  //Order Placed
					paymentwalletTransactionRepo.save(pwtinner);
					if(source.getDistributionTo().equals("VENDOR")) {
						percToVendor += perc;
					}
				}
				
				// update wallet for vendor
				
				for(Long vendor : allvendor){
					//TotalPerc = TotalPerc-percToVendor;
					double percAmount = (userBal.get(vendor) * TotalPerc)/100;
					double amount = userBal.get(vendor) - percAmount;
					PaymentWalletTransaction pwtinner = new PaymentWalletTransaction();
					pwtinner.setAmount(amount);
					pwtinner.setCreatedDate(new Date());
					pwtinner.setOrder(userOrder.get(  ));
					pwtinner.setReciever(String.valueOf(vendor));
					pwtinner.setSender(admin);
					pwtinner.setStatus("1"); 
					pwtinner.setType("OP");  //Order Placed
					paymentwalletTransactionRepo.save(pwtinner);
					
					// update wallet for vendor
					User Uservendor = userRepo.findById(vendor).get();
					UserWallet userWalletVendor = UserWalletRepo.findByUserId(Uservendor.getId()); 
					if(userWalletVendor != null) {
						double amountWallet = userWalletVendor.getAmount();
						userWalletVendor.setAmount(amountWallet + amount);
						userWalletVendor.setModifiedDate(new Date());
						userWalletVendor.setStatus("1");
						userWalletVendor.setUser(admin);
						UserWalletRepo.save(userWalletVendor);
					}else {
						userWalletVendor = new UserWallet();
						userWalletVendor.setAmount(amount);
						userWalletVendor.setCreatedDate(new Date());
						userWalletVendor.setModifiedDate(new Date());
						userWalletVendor.setStatus("1");
						userWalletVendor.setUser(Uservendor);
						UserWalletRepo.save(userWalletVendor);
					}
					// update wallet for admin
					if(userWalletAdmin != null) {
						double amountFromAdmin = userWalletAdmin.getAmount();
						userWalletAdmin.setAmount(amountFromAdmin - amount);
						userWalletAdmin.setModifiedDate(new Date());
						userWalletAdmin.setStatus("1");
						userWalletAdmin.setUser(admin);
						UserWalletRepo.save(userWalletAdmin);
					}
				}
	 }
		 //update Inventory in case of return also
		 else if("RECIEVED".equalsIgnoreCase(status)) {
		 
		 HashMap<Long,Double> userBal = new HashMap<>();
		 Optional<UserOrder> userOrder  = orderRepo.findById(orderId);
			if(userOrder.isPresent()) {
				UserOrder usrOrdr = userOrder.get();
				usrOrdr.setOrderStatus(status);
				orderRepo.save(usrOrdr);
				List<UserOrderProducts> userOrderProducts = userOrderProdRepo.findByUserOrderId(orderId);
				for(UserOrderProducts userOrdrProd :userOrderProducts) {
					userOrdrProd.setStatus(status);
					ProductVariant varient = userOrdrProd.getProduct();
					varient.setTotalQuantity(varient.getTotalQuantity() + userOrdrProd.getQuantity());
					if(userBal.get(Long.valueOf(varient.getCreatedBy())) != null) {
						double oldAmount = userBal.get(Long.valueOf(varient.getCreatedBy()));
						oldAmount += userOrdrProd.getQuantity()*varient.getDisplayPrice();
						userBal.put(Long.valueOf(varient.getCreatedBy()), oldAmount);
					}else {
						double Amount = userOrdrProd.getQuantity()*varient.getDisplayPrice();
						userBal.put(Long.valueOf(varient.getCreatedBy()), Amount);
					}
					userOrderProdRepo.save(userOrdrProd);
					productVariantRepo.save(varient);
				}
			}
			
			// maintain entry for return management with status
		    ReturnManagement returnManage = returnManagement.findByOrderId(userOrder.get().getId());
		    if(returnManage!=null) {
				returnManage.setStatus("COMPLETE");
				returnManagement.save(returnManage);
		    }
			
			// above update inventory and status and update return status
			
		    // now get data from all vendors
		    Set<Long> allVendors = userBal.keySet();
		    double TotalAmount = 0;
		    User admin = userRepo.findById((long) 0).get();
		    for(Long vendorId : allVendors) {
		    	PaymentWalletTransaction pwt = paymentwalletTransactionRepo.findByRecieverAndOrderId(vendorId,userOrder.get().getId());
		    	TotalAmount += pwt.getAmount();
		    	User vendorUser = userRepo.findById(vendorId).get();
		    	PaymentWalletTransaction pwtinner = new PaymentWalletTransaction();
				pwtinner.setAmount(pwt.getAmount());
				pwtinner.setCreatedDate(new Date());
				pwtinner.setOrder(userOrder.get());
				pwtinner.setReciever(String.valueOf(userOrder.get().getUser().getId()));
				pwtinner.setSender(vendorUser);
				pwtinner.setStatus("1"); 
				pwtinner.setType("RT");  //Return
				paymentwalletTransactionRepo.save(pwtinner);
				
				// update wallet for vendor
				UserWallet userWalletVendor = UserWalletRepo.findByUserId(vendorUser.getId()); 
				if(userWalletVendor != null) {
					double amountWallet = userWalletVendor.getAmount();
					userWalletVendor.setAmount(amountWallet - pwt.getAmount());
					userWalletVendor.setModifiedDate(new Date());
					userWalletVendor.setStatus("1");
					userWalletVendor.setUser(vendorUser);
					UserWalletRepo.save(userWalletVendor);
				}
		    }
		    
		    Iterable<PaymentWalletDistribution> allSources =  paymentWalletDistRepo.findAll();
			for(PaymentWalletDistribution source : allSources) {
				long perc = source.getPerc();
				if(source.getDistributionTo().equals("VENDOR")) {
					continue;
				}
				if(source.getDistributionTo().equals("AFFILIATE")) {
					long percGivenToAff = source.getPerc();
					double amountFromAff=(userOrder.get().getOrderTotalPrice()*percGivenToAff)/100; 
					TotalAmount += amountFromAff;
					String affiliatid = source.getSource();
					User affiliatiduser = userRepo.findById(Long.parseLong(affiliatid)).get();
					UserWallet userWalletAff = UserWalletRepo.findByUserId(affiliatiduser.getId());
					if(userWalletAff != null) {
						double amount = userWalletAff.getAmount();
						userWalletAff.setAmount(amount - amountFromAff);
						userWalletAff.setModifiedDate(new Date());
						userWalletAff.setStatus("1");
						userWalletAff.setUser(affiliatiduser);
						UserWalletRepo.save(userWalletAff);
					}
					PaymentWalletTransaction pwtinnerAff = new PaymentWalletTransaction();
					pwtinnerAff.setAmount(amountFromAff);
					pwtinnerAff.setCreatedDate(new Date());
					pwtinnerAff.setOrder(userOrder.get());
					pwtinnerAff.setReciever(String.valueOf(userOrder.get().getUser().getId()));
					pwtinnerAff.setSender(affiliatiduser);
					pwtinnerAff.setStatus("1"); 
					pwtinnerAff.setType("RT");  //Return
					paymentwalletTransactionRepo.save(pwtinnerAff);
				}else {
					long percGivenToSA = source.getPerc();
					double amountFromSA=(userOrder.get().getOrderTotalPrice()*percGivenToSA)/100; 
					TotalAmount += amountFromSA;
					UserWallet userWalletSA = UserWalletRepo.findByUserId(admin.getId());
					if(userWalletSA != null) {
						double amount = userWalletSA.getAmount();
						userWalletSA.setAmount(amount - amountFromSA);
						userWalletSA.setModifiedDate(new Date());
						userWalletSA.setStatus("1");
						userWalletSA.setUser(admin);
						UserWalletRepo.save(userWalletSA);
					}
					PaymentWalletTransaction pwtinnerSA= new PaymentWalletTransaction();
					pwtinnerSA.setAmount(amountFromSA);
					pwtinnerSA.setCreatedDate(new Date());
					pwtinnerSA.setOrder(userOrder.get());
					pwtinnerSA.setReciever(String.valueOf(userOrder.get().getUser().getId()));
					pwtinnerSA.setSender(admin);
					pwtinnerSA.setStatus("1"); 
					pwtinnerSA.setType("RT");  //Return
					paymentwalletTransactionRepo.save(pwtinnerSA);
				}
			}
			
			// now all balance calculate and assign to user
			User actualUser = userOrder.get().getUser();
			UserWallet userWalletuser = UserWalletRepo.findByUserId(actualUser.getId()); 
			if(userWalletuser != null) {
				double amount = userWalletuser.getAmount();
				userWalletuser.setAmount(amount + TotalAmount);
				userWalletuser.setModifiedDate(new Date());
				userWalletuser.setStatus("1");
				userWalletuser.setUser(actualUser);
				UserWalletRepo.save(userWalletuser);
			}
	 }
	}

	@Override
	public List<UserOrderProducts> getOrderProductForVendor(long vendorId, long orderId) {
		return userOrderProdRepo.findByUserOrderIdAndVendorId(orderId,vendorId);
	}

	@Override
	public Object setStatusbyVendorForCompleteOrder(long orderId, String status) {
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
		}
		else
		{
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
		}
		return "updated";
	}

	@Override
	public List<UserOrder> getOrderForVendorByStatus(long vendorId, String status) {
		return userOrderProdRepo.findByvendorvendor_IdAndStatus(vendorId,status);
	}

	@Override
	public Object cancelOrderByUser(long orderId, long userId) {
		UserOrder usrOrdr = null;
		//just to make sure the order belongs to that user only
		Optional<UserOrder> userOrder  = orderRepo.findByIdAndUserId(orderId,userId);
		if(userOrder.isPresent()) {
			 usrOrdr = userOrder.get();
			usrOrdr.setOrderStatus("CANCELLED");
			orderRepo.save(usrOrdr);
			List<UserOrderProducts> userOrderProducts = userOrderProdRepo.findByUserOrderId(orderId);
			for(UserOrderProducts userOrdrProd :userOrderProducts) {
				userOrdrProd.setStatus("CANCELLED");
				ProductVariant varient = userOrdrProd.getProduct();
				double oldReserved = varient.getReservedQuantity();
				oldReserved = oldReserved - userOrdrProd.getQuantity();
				varient.setReservedQuantity(oldReserved);
//				double oldQty = varient.getTotalQuantity();
//				oldQty = oldQty - userOrdrProd.getQuantity();
//				varient.setTotalQuantity(oldQty);
				productVariantRepo.save(varient);
				userOrderProdRepo.save(userOrdrProd);
			}
			// update wallet and add entry to payment wallet entry from Super admin to user
			
						User admin = userRepo.findByUserType("SUPERADMIN");
						PaymentWalletTransaction pwt = new PaymentWalletTransaction();
						pwt.setAmount(usrOrdr.getOrderTotalPrice());
						pwt.setCreatedDate(new Date());
						pwt.setOrder(usrOrdr);
						pwt.setReciever(String.valueOf(usrOrdr.getUser().getId()));
						pwt.setSender(admin);
						pwt.setStatus("1"); 
						pwt.setType("CANCEL");  //Order Placed
						paymentwalletTransactionRepo.save(pwt);			

						UserWallet userWalletAdmin = UserWalletRepo.findByUserId(admin.getId()); 
						if(userWalletAdmin != null) {
							double amount = userWalletAdmin.getAmount();
							userWalletAdmin.setAmount(amount - usrOrdr.getOrderTotalPrice());
							userWalletAdmin.setModifiedDate(new Date());
							userWalletAdmin.setStatus("1");
							userWalletAdmin.setUser(admin);
							UserWalletRepo.save(userWalletAdmin);
						}
						// update wallet for user
						UserWallet userWalletuser = UserWalletRepo.findByUserId(usrOrdr.getUser().getId()); 
						if(userWalletuser != null) {
							double amount = userWalletuser.getAmount();
							userWalletuser.setAmount(amount + usrOrdr.getOrderTotalPrice());
							userWalletuser.setModifiedDate(new Date());
							userWalletuser.setStatus("1");
							userWalletuser.setUser(usrOrdr.getUser());
							UserWalletRepo.save(userWalletuser);
						}else {
							userWalletuser = new UserWallet();
							userWalletuser.setAmount(usrOrdr.getOrderTotalPrice());
							userWalletuser.setModifiedDate(new Date());
							userWalletuser.setStatus("1");
							userWalletuser.setUser(usrOrdr.getUser());
							UserWalletRepo.save(userWalletuser);
						}
		}
		return userOrder;
	}

	@Override
	public Object returnOrderByUser(long orderId, long userId, String reason) {
		UserOrder usrOrdr = null;
		Optional<UserOrder> userOrder  = orderRepo.findByIdAndUserId(orderId, userId);
		if(userOrder.isPresent()) {
			 usrOrdr = userOrder.get();
			 //RETURN CAN ONLY BE OR COMPLETED ORDERS
			 if(usrOrdr.getOrderStatus().equals("COMPLETE"))
			 {
			usrOrdr.setOrderStatus("RETURNED");
			orderRepo.save(usrOrdr);
			List<UserOrderProducts> userOrderProducts = userOrderProdRepo.findByUserOrderId(orderId);
			for(UserOrderProducts userOrdrProd :userOrderProducts) {
				userOrdrProd.setStatus("RETURNED");
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
		return userOrder;
	}

	
	
	@Override
	public List<UserOrder> getLastOrders(int offset) {
		final Pageable pagable = PageRequest.of(offset, 5, Sort.Direction.DESC,"createdDate");
		return orderRepo.getLastOrders(pagable);
	}

	@Override
	public List<ReturnManagement> getLastReturns(int offset) {
		final Pageable pagable = PageRequest.of(offset, 5, Sort.Direction.DESC,"createdDate");
		return returnManagement.getLastReturns(pagable);
	}

	@Override
	public List<UserOrder> getAllOrderByStatus(int offset,String status) {
		final Pageable pagable = PageRequest.of(offset, 5, Sort.Direction.DESC,"createdDate");
		return orderRepo.getAllOrderByStatus(pagable,status);
	}

	@Override
	public UserWallet getAllWalletDetails(long userId) {
		return UserWalletRepo.findByUserId(userId);
	}

	@Override
	public List<UserOrderProducts> getAllVendorSales(long userId, Filter filter) {
		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
				filter.getSortingDirection() != null
				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter.getSortingField());
		return userOrderProdRepo.findByVendorIdAndStatus(userId,"Complete",pagable);
	}

}