package com.urbanstyle.order.ServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.anaadihsoft.common.DTO.OrderTransactionSummaryDTO;
import com.anaadihsoft.common.DTO.UserOrderFetchDTO;
import com.anaadihsoft.common.DTO.UserOrderQtyDTO;
import com.anaadihsoft.common.DTO.UserOrderSaveDTO;
import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.external.UrlShortner;
import com.anaadihsoft.common.master.Address;
import com.anaadihsoft.common.master.AffiliateCommisionOrder;
import com.anaadihsoft.common.master.BankDetails;
import com.anaadihsoft.common.master.BankcardInfo;
import com.anaadihsoft.common.master.Category;
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
import com.urbanstyle.order.Repository.AffiliateCommisionOrderRepo;
import com.urbanstyle.order.Repository.BankRepository;
import com.urbanstyle.order.Repository.BankcardInfoRepo;
import com.urbanstyle.order.Repository.CategoryRepo;
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

import io.micrometer.core.instrument.util.StringUtils;


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
	
	@Autowired
	private AffiliateCommisionOrderRepo affiliateCommOrderRepo;
	
	@Autowired
	private CategoryRepo catRepo;
	
	
	@Transactional
	@Override
	public UserOrder saveorUpdate(UserOrderSaveDTO userOrder) {
		long userId = userOrder.getUserId();
		Address address = userOrder.getAddress();
		double totalPrice = 0;
		UserOrder userOrderSave = new UserOrder();		
		List<UserOrderQtyDTO> userOrderList = userOrder.getUserOrderList();

		for(UserOrderQtyDTO userDTO : userOrderList) {
			long prodVarId = userDTO.getProductVariantId();
			Optional<ProductVariant> optionalp = productVariantRepo.findById(prodVarId);
	
			if(optionalp.isPresent()) {
				ProductVariant product  = optionalp.get();
				totalPrice  += (product.getDisplayPrice() * userDTO.getQty()) ;
			}
			else
			{
				System.out.println("any of the variant is not present");
				return null;
			}
		}

			Optional<User> user = userRepo.findById(userId);
			User loginUser  = null;
			if(user.isPresent()) {
				 loginUser = user.get();
				 userOrderSave.setUser(loginUser);
				 userOrderSave.setCreatedBy(loginUser.getName());
				 userOrderSave.setAddress(address);
				 userOrderSave.setOrderTotalPrice(totalPrice);
				 userOrderSave.setOrderPlacedDate(new Date());
				 userOrderSave.setOrderStatus("PLACED");
				 
				 BankcardInfo bankCardInfo = userOrder.getBankCardDetails();
					Optional<BankcardInfo> bankCardInfoold = bankCardInfoRepo.findByCardNumber(bankCardInfo.getCardNumber());
					if(!bankCardInfoold.isPresent()) {
						try {
						bankCardInfo=bankCardInfoRepo.save(bankCardInfo);	
						}
						catch(Exception e)
						{
							System.out.println("SOMETHING WENT WRONG IN SAVING BANK CARD INFOR");
							return null;
						}
						
					}
					else
					{
						bankCardInfo=bankCardInfoold.get();
					}
					userOrderSave.setBankCardInfo(bankCardInfo);
					if(totalPrice > 0) {
						userOrderSave=orderRepo.save(userOrderSave);
						saveOrderProductAndPaymentDetails(userOrder,userOrderSave,loginUser,bankCardInfo);
					}
					else
					{
						System.out.println("INVALID PRICE");
					}
					
			}
			else
			{
				System.out.println("ORDER CREATOR IS NOT REGISTER WITH USD");
			}
			


		return null;
	}

	private void saveOrderProductAndPaymentDetails(UserOrderSaveDTO userOrder, UserOrder userOrderSave, User loginUser, BankcardInfo bankCardInfo) {
		
		// save user Product ordere
		
		List<UserOrderQtyDTO> userOrderList = userOrder.getUserOrderList();
		double totalAmount = 0;
		List<UserOrderProducts> totalProducts = new ArrayList<>();
		long vendorId;
		UserOrderProducts userOrderProduct = new UserOrderProducts();
		for(UserOrderQtyDTO userDTO : userOrderList) {
			long prodVarId = userDTO.getProductVariantId();
			int quantity = userDTO.getQty();
			Optional<ProductVariant> optionalp = productVariantRepo.findById(prodVarId);
			ProductVariant productVar = null;
			if(optionalp.isPresent()) {
				productVar = optionalp.get();

				// addd reserved quantity					
				double oldQty = productVar.getReservedQuantity();
				productVar.setReservedQuantity(oldQty+quantity);
				productVariantRepo.save(productVar);
				
				
			 userOrderProduct = new UserOrderProducts();
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
			Optional<User> vendorUser = userRepo.findById(vendorId);
			User userVndor  = null;
			if(vendorUser.isPresent()) {
				
				userVndor = vendorUser.get();
			}
			userOrderProduct.setVendor(userVndor);
			
			totalAmount += productVar.getDisplayPrice()*quantity;
			
			totalProducts.add(userOrderProduct);
		  }
			
			// maintaining enteries for affiliate commisiomn
			long affiiateId = userOrder.getAffiliateId();
			if(affiiateId != 0) {
				AffiliateCommisionOrder afcommOrder = new AffiliateCommisionOrder();
				Optional<User> affiliateuser = userRepo.findById(affiiateId);
				if(affiliateuser.isPresent()) {
					afcommOrder.setAffiliateId(affiliateuser.get());
				}
				long catid = productVar.getCategoryId();
				Optional<Category> categoryProd =  catRepo.findById(catid);
				if(categoryProd.isPresent()) {
					afcommOrder.setCommision(categoryProd.get().getCommissionPercentage());
				}
				afcommOrder.setOrderdate( new Date());
				afcommOrder.setOrderprodid(userOrderProduct);
				afcommOrder.setProdvarid(productVar);
				afcommOrder.setReturnId(null);
				afcommOrder.setStatus("PLACED");
				Optional<User> customer = userRepo.findById(userOrder.getUserId());
				if(customer.isPresent()) {
					afcommOrder.setUser(customer.get());
				}
				affiliateCommOrderRepo.save(afcommOrder);
			}
			
			
		}
		
		userOrderProdRepo.saveAll(totalProducts);
		
		
		setPaymentTransactions(totalAmount,userOrder,userOrderSave,loginUser,bankCardInfo);
		
	}

	private void setPaymentTransactions(double totalAmount, UserOrderSaveDTO userOrder, UserOrder userOrderSave, User loginUser, BankcardInfo bankCardInfo) {
		
		List<UserOrderQtyDTO> userOrderList = userOrder.getUserOrderList();

		// paymentWork
				PaymentTransaction pt = new PaymentTransaction();
				//paymentConn.chargePayment(CustomerName);
				pt.setAmount(totalAmount);
				pt.setCard(bankCardInfo);
				pt.setCreatedBy(String.valueOf(loginUser.getId()));
				pt.setCreatedDate(new Date());
				pt.setCustId("");
				pt.setPaymentDesc("");
				
				pt = paymantTransactionRepo.save(pt);
				
				List<PaymentDetails> TotalPaymentRef = new ArrayList<>();
				
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
		
	}

	@Override
	public List<UserOrderProducts> getOrderProductByUser(long userId, Filter filter) {
		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
				filter.getSortingDirection() != null
				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter.getSortingField());
		return userOrderProdRepo.findByUserOrderUserId(userId,pagable);
	}
//	@Override
//	public List<UserOrderFetchDTO> getOrderByUser(long userId,Filter filter) {
//		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
//				filter.getSortingDirection() != null
//				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
//						: Sort.Direction.ASC,
//						filter.getSortingField());
//		
//		 List<UserOrder> userorder = orderRepository.findByUserId(userId,pagable);
//		 List<UserOrderFetchDTO> userOrderFetch = new ArrayList<>();
//		 for(UserOrder order : userorder) {
//			 UserOrderFetchDTO dto = new UserOrderFetchDTO();
//			 dto.setUserOrder(order);
//			List<UserOrderProducts> listOfProdts =  userOrderProdRepo.findByUserOrderId(order.getId());
//			dto.setUserOrderProductList(listOfProdts);
//			userOrderFetch.add(dto);
//		 }
//		 return userOrderFetch;
//	}

//	@Override
//	public List<UserOrderFetchDTO> getOrderById(long orderId) {
//		 Optional<UserOrder> userorderOpt = orderRepository.findById(orderId);
//		 List<UserOrderFetchDTO> userOrderFetch = new ArrayList<>();
//		 if(userorderOpt.get() != null)
//		 {
//			 UserOrder userOrder=userorderOpt.get();
//			UserOrderFetchDTO dto = new UserOrderFetchDTO();
//			dto.setUserOrder(userOrder);
//			List<UserOrderProducts> listOfProdts =  userOrderProdRepo.findByUserOrderId(userOrder.getId());
//			dto.setUserOrderProductList(listOfProdts);
//			userOrderFetch.add(dto);
//		 }
//		
//		 
//		 return userOrderFetch;
//	}

	/*
	 * Method To get Order for Vendor
	 * @see com.urbanstyle.order.Service.OrderService#getVendorOrder(long)
	 */
	
	@Override
	public List<UserOrderProducts> getVendorOrder(long vendorId,Filter filter) {
		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
				filter.getSortingDirection() != null
				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter.getSortingField());
		return userOrderProdRepo.findByvendorvendor_Id(vendorId,pagable);

	}

	@Override
	public UserOrder setStatusbyUser(long orderId,String status,String reason,long userId,long orderProdId) {
		UserOrderProducts userOrdrProdGlobal = null;
	if("CANCEL".equalsIgnoreCase(status)) {
		UserOrder usrOrdr = null;
		Optional<UserOrder> userOrder  = orderRepo.findById(orderId);
		if(userOrder.isPresent()) {
			 usrOrdr = userOrder.get();
			usrOrdr.setOrderStatus(status);
			orderRepo.save(usrOrdr);
			List<UserOrderProducts> userOrderProducts = userOrderProdRepo.findByUserOrderId(orderId);
			for(UserOrderProducts userOrdrProd :userOrderProducts) {
				if(orderProdId == userOrdrProd.getId()) {
					userOrdrProdGlobal = userOrdrProd;
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
			}
			// update wallet and add entry to payment wallet entry from Super admin to user
			
						User admin = userRepo.findById((long) 0).get();
						PaymentWalletTransaction pwt = new PaymentWalletTransaction();
						pwt.setAmount(userOrdrProdGlobal.getOrderProductPrice()*userOrdrProdGlobal.getQuantity());
						pwt.setCreatedDate(new Date());
						pwt.setOrder(usrOrdr);
						pwt.setReciever(String.valueOf(usrOrdr.getUser().getId()));
						pwt.setSender(admin);
						pwt.setStatus("1"); 
						pwt.setType("CANCEL");  //Order Placed
						pwt.setOrderProds(userOrdrProdGlobal);
						paymentwalletTransactionRepo.save(pwt);			

						UserWallet userWalletAdmin = UserWalletRepo.findByUserId(admin.getId()); 
						if(userWalletAdmin != null) {
							double amount = userWalletAdmin.getAmount();
							userWalletAdmin.setAmount(amount - (userOrdrProdGlobal.getOrderProductPrice()*userOrdrProdGlobal.getQuantity()));
							userWalletAdmin.setModifiedDate(new Date());
							userWalletAdmin.setStatus("1");
							userWalletAdmin.setUser(admin);
							UserWalletRepo.save(userWalletAdmin);
						}
						// update wallet for user
						UserWallet userWalletuser = UserWalletRepo.findByUserId(usrOrdr.getUser().getId()); 
						if(userWalletuser != null) {
							double amount = userWalletuser.getAmount();
							userWalletuser.setAmount(amount + (userOrdrProdGlobal.getOrderProductPrice()*userOrdrProdGlobal.getQuantity()));
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
		
		// now user only cancel one order product so update status for affiliate only
		AffiliateCommisionOrder afOrder = affiliateCommOrderRepo.findByOrderProdId(orderProdId);
		if(afOrder != null) {
			afOrder.setStatus("CANCEL");
			affiliateCommOrderRepo.save(afOrder);
		}
		
	 }else if("RETURN".equalsIgnoreCase(status)) {
			ReturnManagement returnManage = new ReturnManagement();
				// return management
				UserOrder usrOrdr = null;
				Optional<UserOrder> userOrder  = orderRepo.findById(orderId);
				if(userOrder.isPresent()) {
					 usrOrdr = userOrder.get();
					usrOrdr.setOrderStatus(status);
					orderRepo.save(usrOrdr);
					List<UserOrderProducts> userOrderProducts = userOrderProdRepo.findByUserOrderId(orderId);
					for(UserOrderProducts userOrdrProd :userOrderProducts) {
						if(orderProdId == userOrdrProd.getId()) {
							userOrdrProd.setStatus(status);
							userOrderProdRepo.save(userOrdrProd);
						}
					}
					
					// maintain entry for return management with status
					 returnManage = new ReturnManagement();
					//returnManage.setOrder(usrOrdr);
					returnManage.setReason(reason);
					returnManage.setStatus("INP");
					returnManage.setOrderProduct(userOrdrProdGlobal);
					Optional<User> loginUser = userRepo.findById(userId);
					if(loginUser.isPresent()) {
						returnManage.setUser(loginUser.get());					
						returnManage.setCreatedBy(String.valueOf(loginUser.get().getId()));
					}
					returnManagement.save(returnManage);
			}
				
				// now user only return one order product so update status for affiliate only
				AffiliateCommisionOrder afOrder = affiliateCommOrderRepo.findByOrderProdId(orderProdId);
				if(afOrder != null) {
					afOrder.setStatus("RETURN");
					afOrder.setReturnId(returnManage);
					affiliateCommOrderRepo.save(afOrder);
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
//				if("DISPATCHED".equalsIgnoreCase(status)) {
//					updateBalanceInCaseOfDispatch(userOrder);
//				}
				
				
				
			}
			if("DISPATCHED".equalsIgnoreCase(status)) {
				updateBalanceInCaseOfDispatchForSingleProduct(userOrderProd);
			}
		}
		return null;
	}

	private void updateBalanceInCaseOfDispatchForSingleProduct(UserOrderProducts userOrdrProd) {
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
	public void setStatusbyAdmin(long orderId,long orderProdId, String status,long userId) {
		//admin can dispatch product

		 if("COMPLETE".equalsIgnoreCase(status)) {
		 HashMap<Long,Double> userBal = new HashMap<>();
		 Optional<UserOrder> userOrder  = orderRepo.findById(orderId);
			if(userOrder.isPresent()) {
				UserOrder usrOrdr = userOrder.get();
				usrOrdr.setOrderStatus(status);
				usrOrdr.setOrderPaidDate(new Date());
				orderRepo.save(usrOrdr);
				//now all work will for one order product
			//	List<UserOrderProducts> userOrderProducts = userOrderProdRepo.findByUserOrderId(orderId);
				Optional<UserOrderProducts> userOrdrProdOpt=userOrderProdRepo.findById(orderProdId);
				if(userOrdrProdOpt.isPresent())
				{
					UserOrderProducts userOrdrProd=userOrdrProdOpt.get();
				//for(UserOrderProducts userOrdrProd :userOrderProducts) {
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
				//}
			}
			
			// Here Update Payment after calculate from each order product
			
			// add entry when user sends all payment to super admin
			
			User admin = userRepo.findByUserType("SUPERADMIN");
			UserWallet userWalletAdmin = UserWalletRepo.findByUserId(admin.getId());
			// now price we get for user order product
			
			Optional<UserOrderProducts> userOrdrProdOpt=userOrderProdRepo.findById(orderProdId);
			UserOrderProducts userOrdrProd = null;
			if(userOrdrProdOpt.isPresent())
			{
				 userOrdrProd=userOrdrProdOpt.get();
			}
			
			double orderTotalPrice = userOrdrProd.getQuantity()*userOrdrProd.getOrderProductPrice();

			
			// Here super admin distributes to all sources
			
			long TotalPerc = 0;
			long percToVendor = 0; 
			Set<Long> allvendor = userBal.keySet();
				Iterable<PaymentWalletDistribution> allSources =  paymentWalletDistRepo.findAll();
				for(PaymentWalletDistribution source : allSources) {
					long perc = source.getPerc();
					if(source.getDistributionTo().equals("AFFILIATE")) {
						long affiliatid;
						User affiliatiduser = null;
						double amountToVendor;
						AffiliateCommisionOrder afOrder = affiliateCommOrderRepo.findByOrderProdId(orderProdId);
						if(afOrder != null) {
							afOrder.setStatus("COMPLETE");
							affiliateCommOrderRepo.save(afOrder);
							affiliatid = afOrder.getAffiliateId().getId();
							 affiliatiduser =afOrder.getAffiliateId();
						}else {
							affiliatid = Long.parseLong(source.getSource());
							affiliatiduser = userRepo.findById(affiliatid).get();
						}
						UserWallet userWalletAff = UserWalletRepo.findByUserId(affiliatid);
						if(afOrder != null) {
							 amountToVendor = (orderTotalPrice*afOrder.getCommision())/100;							
						}else {
							 amountToVendor = (orderTotalPrice*source.getPerc())/100;
						}
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
					pwtinner.setOrderProds(userOrdrProd);
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
					pwtinner.setOrderProds(userOrdrProd);
					paymentwalletTransactionRepo.save(pwtinner);
					
					// update wallet for vendor
					User Uservendor = userRepo.findById(vendor).get();
					UserWallet userWalletVendor = UserWalletRepo.findByUserId(Uservendor.getId()); 
					if(userWalletVendor != null) {
						double amountWallet = userWalletVendor.getAmount();
						userWalletVendor.setAmount(amountWallet + amount);
						userWalletVendor.setModifiedDate(new Date());
						userWalletVendor.setStatus("1");
						userWalletVendor.setUser(Uservendor);
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
				//List<UserOrderProducts> userOrderProducts = userOrderProdRepo.findByUserOrderId(orderId);
				Optional<UserOrderProducts> userOrdrProdOpt = userOrderProdRepo.findById(orderProdId);
//				for(UserOrderProducts userOrdrProd :userOrderProducts) {
				if(!userOrdrProdOpt.isPresent())
				{
					UserOrderProducts userOrdrProd=userOrdrProdOpt.get();
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
			//	}
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
		    
		    Optional<UserOrderProducts> userOrdrProdOpt=userOrderProdRepo.findById(orderProdId);
			UserOrderProducts userOrdrProd = null;
			if(!userOrdrProdOpt.isPresent())
			{
				 userOrdrProd=userOrdrProdOpt.get();
			}
		    
		    Set<Long> allVendors = userBal.keySet();
		    double TotalAmount = 0;
		    User admin = userRepo.findById((long) 0).get();
		    for(Long vendorId : allVendors) {
		    	PaymentWalletTransaction pwt = paymentwalletTransactionRepo.findByRecieverAndOrderId(String.valueOf(vendorId),userOrder.get().getId());
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
				pwtinner.setOrderProds(userOrdrProd);
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
					double amountFromAff=(userOrdrProd.getOrderProductPrice()*userOrdrProd.getQuantity()*percGivenToAff)/100; 
					TotalAmount += amountFromAff;
					String affiliatid = source.getSource();
					AffiliateCommisionOrder afOrder = affiliateCommOrderRepo.findByOrderProdId(orderProdId);
					if(afOrder != null) {
						afOrder.setStatus("RECIEVED");
						affiliateCommOrderRepo.save(afOrder);
					}
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
					pwtinnerAff.setOrderProds(userOrdrProd);
					paymentwalletTransactionRepo.save(pwtinnerAff);
				}else {
					long percGivenToSA = source.getPerc();
					double amountFromSA=(userOrdrProd.getOrderProductPrice()*userOrdrProd.getQuantity()*percGivenToSA)/100; 
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
					pwtinnerSA.setOrderProds(userOrdrProd);
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
	public UserOrderProducts getOrderProductForVendor(long vendorId, long orderProductId) {
		return  userOrderProdRepo.findByIdAndVendorId(orderProductId,vendorId);
		//return userOrderProdRepo.findByUserOrderIdAndVendorId(orderId,vendorId);
	}

	@Override
	public Object setStatusbyVendorForCompleteOrder(long orderId, String status,long orderProdId) {
		if("DISPATCHED".equalsIgnoreCase(status)) {
			// update inventory and status
			Optional<UserOrder> userOrder  = orderRepo.findById(orderId);
			if(userOrder.isPresent()) {
				UserOrder usrOrdr = userOrder.get();
				usrOrdr.setOrderStatus(status);
				orderRepo.save(usrOrdr);
				List<UserOrderProducts> userOrderProducts = userOrderProdRepo.findByUserOrderId(orderId);
				for(UserOrderProducts userOrdrProd :userOrderProducts) {
					if(orderProdId == userOrdrProd.getId()) {
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
		}
		else
		{
			Optional<UserOrder> userOrderOpt  =  orderRepo.findById(orderId);
			if(userOrderOpt.isPresent())
			{
				UserOrder userOrder=userOrderOpt.get();
			List<UserOrderProducts> userOrderProducts = userOrderProdRepo.findByUserOrderId(userOrder.getId());
			for(UserOrderProducts userOrdrProd :userOrderProducts) {
				if(orderProdId == userOrdrProd.getId()) {
					userOrdrProd.setStatus(status);
				}
			}
				userOrderProdRepo.saveAll(userOrderProducts);
				userOrder.setOrderStatus(status);
				orderRepo.save(userOrder);
			
			}
		}
		return "updated";
	}

	@Override
	public List<UserOrderProducts> getOrderForVendorByStatus(long vendorId, String status) {
		return userOrderProdRepo.findByvendorvendor_IdAndStatus(vendorId,status);
	}

	@Override
	public Object cancelOrderByUser(long orderId, long userId,long orderProductId) {
		UserOrder usrOrdr = null;
		//just to make sure the order belongs to that user only
		Optional<UserOrder> userOrder  = orderRepo.findByIdAndUserId(orderId,userId);
		UserOrderProducts userOrderProducts = null;
		if(userOrder.isPresent()) {
			 usrOrdr = userOrder.get();
			usrOrdr.setOrderStatus("CANCELLED");
			orderRepo.save(usrOrdr);
			Optional<UserOrderProducts> userOrderProductOpt = userOrderProdRepo.findById(orderProductId);
			if(userOrderProductOpt.isPresent())
			{
				 userOrderProducts = userOrderProductOpt.get();
			
			//for(UserOrderProducts userOrdrProd :userOrderProducts) {
			userOrderProducts.setStatus("CANCELLED");
				ProductVariant varient = userOrderProducts.getProduct();
				double oldReserved = varient.getReservedQuantity();
				oldReserved = oldReserved - userOrderProducts.getQuantity();
				varient.setReservedQuantity(oldReserved);
//				double oldQty = varient.getTotalQuantity();
//				oldQty = oldQty - userOrdrProd.getQuantity();
//				varient.setTotalQuantity(oldQty);
				productVariantRepo.save(varient);
				userOrderProdRepo.save(userOrderProducts);
			//}
			// update wallet and add entry to payment wallet entry from Super admin to user
			
						User admin = userRepo.findByUserType("SUPERADMIN");
						PaymentWalletTransaction pwt = new PaymentWalletTransaction();
						pwt.setAmount(userOrderProducts.getOrderProductPrice());
						pwt.setCreatedDate(new Date());
						pwt.setOrder(usrOrdr);
						pwt.setReciever(String.valueOf(usrOrdr.getUser().getId()));
						pwt.setSender(admin);
						pwt.setOrderProds(userOrderProducts);
						pwt.setStatus("1"); 
						pwt.setType("CANCEL");  //Order Placed
						paymentwalletTransactionRepo.save(pwt);			

						UserWallet userWalletAdmin = UserWalletRepo.findByUserId(admin.getId()); 
						if(userWalletAdmin != null) {
							double amount = userWalletAdmin.getAmount();
//							userWalletAdmin.setAmount(amount - (userOrderProducts.getOrderProductPrice()*userOrderProducts.getQuantity()));
							userWalletAdmin.setAmount(amount - (userOrderProducts.getOrderProductPrice()));

							userWalletAdmin.setModifiedDate(new Date());
							userWalletAdmin.setStatus("1");
							userWalletAdmin.setUser(admin);
							UserWalletRepo.save(userWalletAdmin);
						}
						// update wallet for user
						UserWallet userWalletuser = UserWalletRepo.findByUserId(usrOrdr.getUser().getId()); 
						if(userWalletuser != null) {
							double amount = userWalletuser.getAmount();
							userWalletuser.setAmount(amount + (userOrderProducts.getOrderProductPrice()));
							userWalletuser.setModifiedDate(new Date());
							userWalletuser.setStatus("1");
							userWalletuser.setUser(usrOrdr.getUser());
							UserWalletRepo.save(userWalletuser);
						}else {
							userWalletuser = new UserWallet();
							userWalletuser.setAmount(userOrderProducts.getOrderProductPrice());
							userWalletuser.setModifiedDate(new Date());
							userWalletuser.setStatus("1");
							userWalletuser.setUser(usrOrdr.getUser());
							UserWalletRepo.save(userWalletuser);
						}

		}
		}
		return usrOrdr;
	}

	@Override
	public Object returnOrderByUser(long orderId, long userId, String reason, long orderProdId) {
		UserOrder usrOrdr = null;
		Optional<UserOrder> userOrder  = orderRepo.findByIdAndUserId(orderId, userId);
		if(userOrder.isPresent()) {
			 usrOrdr = userOrder.get();
			 //RETURN CAN ONLY BE OR COMPLETED ORDERS
//			 if(usrOrdr.getOrderStatus().equals("COMPLETE"))
//			 {
			//usrOrdr.setOrderStatus("RETURNED REQUESTED");
			//orderRepo.save(usrOrdr);
//			List<UserOrderProducts> userOrderProducts = userOrderProdRepo.findByUserOrderId(orderId);
//			for(UserOrderProducts userOrdrProd :userOrderProducts) {
			UserOrderProducts userOrdrProd = null;
			Optional<UserOrderProducts> userOrdrProdOpt=userOrderProdRepo.findById(orderProdId);
			if(userOrdrProdOpt.isPresent())
			{
				userOrdrProd=userOrdrProdOpt.get();
				if(userOrdrProd.getStatus().equals("COMPLETE"))
				{
				 userOrdrProd=userOrdrProdOpt.get();
				userOrdrProd.setStatus("RETURNED REQUESTED");
				userOrderProdRepo.save(userOrdrProd);
			
			// maintain entry for return management with status
			ReturnManagement returnManage = new ReturnManagement();
			returnManage.setOrder(usrOrdr);
			returnManage.setOrderProduct(userOrdrProd);
			returnManage.setReason(reason);
			returnManage.setStatus("REQUESTED");
			returnManage.setOrderProduct(userOrdrProd);
			Optional<User> loginUser = userRepo.findById(userId);
			if(loginUser.isPresent()) {
				returnManage.setUser(loginUser.get());					
				returnManage.setCreatedBy(String.valueOf(loginUser.get().getId()));
			}
			returnManagement.save(returnManage);
			}
			}
		}
	//}
		return userOrder;
	}

	
	
//	@Override
//	public List<UserOrder> getLastOrders(int offset) {
//		final Pageable pagable = PageRequest.of(offset, 5, Sort.Direction.DESC,"createdDate");
//		List<String> statusList = new ArrayList<>();
//		statusList.add("DISPATCHED");
//		statusList.add("INPROGRESS");
//		return orderRepo.getAllOrderByStatus(pagable,"DISPATCHED");
//	}
	
	@Override
	public List<UserOrderProducts> getLastOrders(int offset) {
		final Pageable pagable = PageRequest.of(offset, 5, Sort.Direction.DESC,"createdDate");
		List<String> statusList = new ArrayList<>();
		statusList.add("DISPATCHED");
		statusList.add("INPROGRESS");
		return userOrderProdRepo.getAllOrderByStatus("PLACED",pagable);
	}

	@Override
	public List<ReturnManagement> getLastReturns(int offset) {
		final Pageable pagable = PageRequest.of(offset, 5, Sort.Direction.DESC,"createdDate");
		return returnManagement.getLastReturns(pagable);
	}

//	@Override
//	public List<UserOrder> getAllOrderByStatus(int offset,String status) {
//		final Pageable pagable = PageRequest.of(offset, 5, Sort.Direction.DESC,"createdDate");
//		return orderRepo.getAllOrderByStatus(pagable,status);
//	}
	
	@Override
	public List<UserOrderProducts> getAllOrderByStatus(int offset,String status) {
		final Pageable pagable = PageRequest.of(offset, 5, Sort.Direction.DESC,"createdDate");
		return userOrderProdRepo.getAllOrderByStatus(status,pagable);
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
		return userOrderProdRepo.findByVendorIdAndStatus(userId,"COMPLETE",pagable);
	}

	@Override
	public List<UserWallet> getTop5Users(String userType) {
		return UserWalletRepo.findTop5ByUserUserType(userType);
	}

	@Override
	public List<UserOrderProducts> getAllOrderForSuperAdmin(Filter filter) {
		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
				filter.getSortingDirection() != null
				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter.getSortingField());
		Page<UserOrderProducts> pageDate= userOrderProdRepo.findAll(pagable);
		return pageDate.hasContent()? pageDate.getContent():null;
	}

	@Override
	public List<UserOrderProducts> getLastOrdersForVendor(int offset, long vendorId,String status) {
		final Pageable pagable = PageRequest.of(offset, 5, Sort.Direction.DESC,"createdDate");
		return userOrderProdRepo.getAllOrderByStatusAndUserId(status,vendorId,pagable);
	}

	@Override
	public UserOrder getOrderDetails(long orderId) {
		Optional<UserOrder> optOrder= orderRepo.findById(orderId);
		return optOrder.isPresent()?optOrder.get():null;
	}

	@Override
	public UserOrderProducts getOrderById(long parseLong) {
		Optional<UserOrderProducts> userOrderProdOpt= userOrderProdRepo.findById(parseLong);
		return userOrderProdOpt.isPresent()?userOrderProdOpt.get():null;
	}

	@Override
	public List<PaymentWalletTransaction> getTransactionofOrder(long orderProdId) {
		return paymentwalletTransactionRepo.getTransactionofOrder(orderProdId,"OP");
	}

	@Override
	public long countForSuperAdmin() {
		return userOrderProdRepo.count();
	}

	@Override
	public long getCountOrderProductByUser(long userId) {
		return userOrderProdRepo.countByUserOrderUserId(userId);

	}

	@Override
	public long getVendorOrderCount(long vendorId) {
		return userOrderProdRepo.getVendorOrderCount(vendorId);

	}
	

	
	@Override
	public List<OrderTransactionSummaryDTO> getTransactionSummaryofOrder(long orderProdId) {
		List<OrderTransactionSummaryDTO> allTransactionDetails = new ArrayList<>();
		Optional<UserOrderProducts> userOrdrProdOpt=userOrderProdRepo.findById(orderProdId);
		 HashMap<Long,Double> userBal = new HashMap<>();
		if(userOrdrProdOpt.isPresent())
		{
			UserOrderProducts userOrdrProd=userOrdrProdOpt.get();
			ProductVariant varient = userOrdrProd.getProduct();
			if(userBal.get(Long.valueOf(varient.getCreatedBy())) != null) {
				double oldAmount = userBal.get(Long.valueOf(varient.getCreatedBy()));
				oldAmount += userOrdrProd.getQuantity()*varient.getDisplayPrice();
				userBal.put(Long.valueOf(varient.getCreatedBy()), oldAmount);
			}else {
				double amount = userOrdrProd.getQuantity()*varient.getDisplayPrice();
				userBal.put(Long.valueOf(varient.getCreatedBy()), amount);
			}

			double orderTotalPrice = userOrdrProd.getQuantity()*userOrdrProd.getOrderProductPrice();

		Set<Long> allvendor = userBal.keySet();
		Iterable<PaymentWalletDistribution> allSources =  paymentWalletDistRepo.findAll();
		double percToVendor = 0;
		double TotalPerc = 0;
		for(PaymentWalletDistribution source : allSources) {
			long perc = source.getPerc();
			if(source.getDistributionTo().equals("AFFILIATE")) {
				long affiliatid;
				User affiliatiduser = null;
				double amountToVendor;
				AffiliateCommisionOrder afOrder = affiliateCommOrderRepo.findByOrderProdId(orderProdId);
				if(afOrder != null) {
					affiliatid = afOrder.getAffiliateId().getId();
					 affiliatiduser =afOrder.getAffiliateId();
				}else {
					affiliatid = Long.parseLong(source.getSource());
					affiliatiduser = userRepo.findById(affiliatid).get();
				}
				if(afOrder != null) {
					 amountToVendor = (orderTotalPrice*afOrder.getCommision())/100;							
				}else {
					 amountToVendor = (orderTotalPrice*source.getPerc())/100;
				}
				User admin = userRepo.findByUserType("SUPERADMIN");
				UserWallet userWalletAdmin = UserWalletRepo.findByUserId(admin.getId());
				OrderTransactionSummaryDTO afDto = new OrderTransactionSummaryDTO();
				afDto.setSenderuserCode(admin.getUserCode());
				afDto.setSenderuserName(admin.getName());
				afDto.setSenderuserId(admin.getId());
				afDto.setRecieveruserId(affiliatid);
				afDto.setRecieveruserName(affiliatiduser.getName());
				afDto.setRecieveruserCode(affiliatiduser.getUserCode());
				afDto.setAmount(amountToVendor);
				allTransactionDetails.add(afDto);
			}
			TotalPerc +=perc;
		}
		
		////
		
		for(Long vendor : allvendor){
			//TotalPerc = TotalPerc-percToVendor;
			double percAmount = (userBal.get(vendor) * TotalPerc)/100;
			double amount = userBal.get(vendor) - percAmount;
			// update wallet for vendor
			User Uservendor = userRepo.findById(vendor).get();
			
			User admin = userRepo.findByUserType("SUPERADMIN");
			UserWallet userWalletAdmin = UserWalletRepo.findByUserId(admin.getId());
			OrderTransactionSummaryDTO vendDto = new OrderTransactionSummaryDTO();
			vendDto.setSenderuserCode(admin.getUserCode());
			vendDto.setSenderuserName(admin.getName());
			vendDto.setSenderuserId(admin.getId());
			vendDto.setRecieveruserId(Uservendor.getId());
			vendDto.setRecieveruserName(Uservendor.getName());
			vendDto.setRecieveruserCode(Uservendor.getUserCode());
			vendDto.setAmount(amount);
			allTransactionDetails.add(vendDto);
			
		}
		
	  }
		return allTransactionDetails;
	}

	

}