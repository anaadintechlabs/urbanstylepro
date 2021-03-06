package com.urbanstyle.order.ServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.anaadihsoft.common.DTO.OrderTransactionSummaryDTO;
import com.anaadihsoft.common.DTO.OrderUiDTO;
import com.anaadihsoft.common.DTO.OrderUiListingDTO;
import com.anaadihsoft.common.DTO.ReturnUiListDTO;
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
				 userOrderSave.setOrderStatus("PENDING");
				 
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
		long affiiateId = userOrder.getAffiliateId();
		User affialiateUser =null;
		if(affiiateId!=0)
		{
		Optional<User> affiliateuser = userRepo.findById(affiiateId);
		if(affiliateuser.isPresent()) {
			affialiateUser=affiliateuser.get();
		}
		}
		User admin = userRepo.findByUserType("SUPERADMIN");
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
			userOrderProduct.setStatus("PENDING");
			
			UrlShortner urlSShort= new UrlShortner();
			String UID = urlSShort.generateUid("OD-", 9);
			System.out.println("uid"+UID);

			userOrderProduct.setOrderCode("OD-"+UID);
			
			// addd reserved quantity
			//userOrderProduct.setStatus(userOrderSave.getOrderStatus());
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
			
			// if 3 products are ordered then 3 entries are maintained user to admin against order prod id
			
			if(admin != null) {
				PaymentWalletTransaction pwt = new PaymentWalletTransaction();
				pwt.setAmount(productVar.getDisplayPrice()*quantity);
				pwt.setCreatedDate(new Date());
				pwt.setOrder(userOrderSave);
				pwt.setReciever(String.valueOf(admin.getId()));
				pwt.setRecieverDetails(admin);
				pwt.setSender(userOrderSave.getUser());
				pwt.setStatus("1"); 
				pwt.setType("OP");  //Order Placed
				pwt.setOrderProds(userOrderProduct);
				paymentwalletTransactionRepo.save(pwt);		
			}
		  }
			
			// maintaining enteries for affiliate commisiomn
			
			if(affiiateId != 0) {
				AffiliateCommisionOrder afcommOrder = new AffiliateCommisionOrder();
				afcommOrder.setAffiliateId(affialiateUser);
				long catid = productVar.getCategoryId();
				Optional<Category> categoryProd =  catRepo.findById(catid);
				//Here commission will be taken from some other point
				if(categoryProd.isPresent()) {
					afcommOrder.setCommision(categoryProd.get().getCommissionPercentage());
				}
				afcommOrder.setOrderdate( new Date());
				afcommOrder.setOrderprodid(userOrderProduct);
				afcommOrder.setProdvarid(productVar);
				afcommOrder.setReturnId(null);
				//Update the Status of This also 
				afcommOrder.setStatus("PENDING");
				Optional<User> customer = userRepo.findById(userOrder.getUserId());
				if(customer.isPresent()) {
					afcommOrder.setUser(customer.get());
				}
				affiliateCommOrderRepo.save(afcommOrder);
				
				userOrderProduct.setAffiliateCommisionExists(true);
				//userOrderProduct.setAffiliate(affialiateUser);
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
			/*
			 * PaymentWalletTransaction pwt = new PaymentWalletTransaction();
			 * pwt.setAmount(totalAmount); pwt.setCreatedDate(new Date());
			 * pwt.setOrder(userOrderSave); pwt.setReciever(String.valueOf(admin.getId()));
			 * pwt.setSender(userOrderSave.getUser()); pwt.setStatus("1");
			 * pwt.setType("OP"); //Order Placed paymentwalletTransactionRepo.save(pwt);
			 */	
	
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
	public List<OrderUiListingDTO> getOrderProductByUser(long userId, Filter filter) {
		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
				filter.getSortingDirection() != null
				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter.getSortingField());
		//CHANGE IMPLEMENTING DATE FILTER AND SEARCH STRING
		if(filter.getSearchString()!=null && !filter.getSearchString().isEmpty())
		{
			return userOrderProdRepo.getAllUsersOrderBySearchString(userId,filter.getSearchString(),pagable);
		}
		else
		{
		if(filter.getDateRange()!=null && !filter.getDateRange().isEmpty())
		{
			String[] dates=filter.getDateRange().split(",");
			Date startDate= new Date(Long.parseLong(dates[0]));
			Date endDate = new Date(Long.parseLong(dates[1]));
			System.out.println("start date "+startDate+"  end Date"+endDate);
			return userOrderProdRepo.findByUserOrderUserIdAndCreatedDateBetween(userId,startDate,endDate,pagable);
		}
		}
		
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
	public List<OrderUiListingDTO> getVendorOrder(long vendorId,Filter filter) {
		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
				filter.getSortingDirection() != null
				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter.getSortingField());
		if(filter.getSearchString()!=null && !filter.getSearchString().isEmpty())
		{
			return userOrderProdRepo.getAllOrderForVendorBySearchString(vendorId,filter.getSearchString(),pagable);
		}
		else
		{
		if(filter.getDateRange()!=null && !filter.getDateRange().isEmpty())
		{
			String[] dates=filter.getDateRange().split(",");
			Date startDate= new Date(Long.parseLong(dates[0]));
			Date endDate = new Date(Long.parseLong(dates[1]));
			System.out.println("start date "+startDate+"  end Date"+endDate);
			return userOrderProdRepo.findByvendorvendor_IdByDateRange(vendorId,startDate,endDate,pagable);
		}
		}
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
			 //Here also check if previous status was Discpatchec then creaate a new return 
			 //of type courier return
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
						pwt.setRecieverDetails(usrOrdr.getUser());
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
	public UserOrderProducts setStatusbyVendor(long orderProdId, String status, String trackingId, String link) {
		
		Optional<UserOrderProducts> userordrProd = userOrderProdRepo.findById(orderProdId);
		if(userordrProd.isPresent()) {
			UserOrderProducts userOrderProd = userordrProd.get();
			userOrderProd.setStatus(status);
			userOrderProd.setTrackingId(trackingId);
			userOrderProd.setTrackingLink(link);
			userOrderProd=userOrderProdRepo.save(userOrderProd);
			
			if(userOrderProd.isAffiliateCommisionExists())
			{
				updateAffiliateCommission(userOrderProd,null);
			}
			
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
			
			double orderTotalPrice = userOrdrProd.getOrderProductPrice();

			
			// Here super admin distributes to all sources
			long percToAdmin = 0; 
			Set<Long> allvendor = userBal.keySet();
				Iterable<PaymentWalletDistribution> allSources =  paymentWalletDistRepo.findAll();
				for(PaymentWalletDistribution source : allSources) {
					AffiliateCommisionOrder afOrder = null;
					double perc = source.getPerc();
					if(source.getTypeOfuser().equals("AFFILIATE")) {
						long affiliatid;
						User affiliatiduser = null;
						double amountToVendor;
						 afOrder = affiliateCommOrderRepo.findByOrderProdId(orderProdId);
						if(afOrder != null) {
							afOrder.setStatus("COMPLETE");
							affiliateCommOrderRepo.save(afOrder);
							affiliatid = afOrder.getAffiliateId().getId();
							 affiliatiduser =afOrder.getAffiliateId();
							 perc = afOrder.getCommision();
								UserWallet userWalletAff = UserWalletRepo.findByUserId(affiliatid);
								 amountToVendor = (orderTotalPrice*afOrder.getCommision())/100;	
								 
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
								 
								 if(userWalletAdmin != null) {
										double amount = userWalletAdmin.getAmount();
										userWalletAdmin.setAmount(amount - amountToVendor);
										userWalletAdmin.setModifiedDate(new Date());
										userWalletAdmin.setStatus("1");
										userWalletAdmin.setUser(admin);
										UserWalletRepo.save(userWalletAdmin);
									}
									percToAdmin += perc; 

									double amount = (orderTotalPrice*perc)/100;
									PaymentWalletTransaction pwtinner = new PaymentWalletTransaction();
									pwtinner.setAmount(amount);
									pwtinner.setCreatedDate(new Date());
									pwtinner.setOrder(userOrder.get());
									//here source will come from affiliate transaction table
									pwtinner.setReciever(String.valueOf(afOrder.getAffiliateId().getId()));
									pwtinner.setRecieverDetails(afOrder.getAffiliateId());
							
									pwtinner.setSender(admin);
									pwtinner.setStatus("1"); 
									pwtinner.setType("OC");  //Order complete
									pwtinner.setOrderProds(userOrdrProd);
									paymentwalletTransactionRepo.save(pwtinner);
									
						}

						
						// update wallet for admin
						
					}
					else if(source.getTypeOfuser().equals("SUPERADMIN"))
					{
						double amount = (orderTotalPrice*perc)/100;
						PaymentWalletTransaction pwtinner = new PaymentWalletTransaction();
						pwtinner.setAmount(amount);
						pwtinner.setCreatedDate(new Date());
						pwtinner.setOrder(userOrder.get());
						//here source will come from affiliate transaction table
				
						pwtinner.setReciever(String.valueOf(admin.getId()));
						pwtinner.setRecieverDetails(admin);
					
						pwtinner.setSender(admin);
						pwtinner.setStatus("1"); 
						pwtinner.setType("OC");  //Order complete
						pwtinner.setOrderProds(userOrdrProd);
						paymentwalletTransactionRepo.save(pwtinner);
						
						percToAdmin += perc;
						
					}
					
					
				}
				
				// update wallet for vendor
				
				for(Long vendor : allvendor){
					//TotalPerc = TotalPerc-percToVendor;
					double percAmount = (userBal.get(vendor)*percToAdmin)/100;
					double amount = userBal.get(vendor) - percAmount;
					User Uservendor = userRepo.findById(vendor).get();
					PaymentWalletTransaction pwtinner = new PaymentWalletTransaction();
					pwtinner.setAmount(amount);
					pwtinner.setCreatedDate(new Date());
					pwtinner.setOrder(userOrder.get());
					pwtinner.setReciever(String.valueOf(vendor));
					pwtinner.setRecieverDetails(Uservendor);
					pwtinner.setSender(admin);
					pwtinner.setStatus("1"); 
					pwtinner.setType("OC");  //Order Placed
					pwtinner.setOrderProds(userOrdrProd);
					paymentwalletTransactionRepo.save(pwtinner);
					
					// update wallet for vendor

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
	
	}

	@Override
	public OrderUiDTO getOrderProductForVendor(long vendorId, long orderProductId) {
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
	public List<OrderUiListingDTO> getOrderForVendorByStatus(long vendorId, String status, Filter filter) {
		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
				filter.getSortingDirection() != null
				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter.getSortingField());
		//CHANGE IMPLEMENTING DATE FILTER AND SEARCH STRING
		if(filter.getSearchString()!=null && !filter.getSearchString().isEmpty())
		{
			return userOrderProdRepo.getAllVendorOrderAndStatusAndBySearchString(vendorId,status,filter.getSearchString(),pagable);
		}
		else
		{
		if(filter.getDateRange()!=null && !filter.getDateRange().isEmpty())
		{
			String[] dates=filter.getDateRange().split(",");
			Date startDate= new Date(Long.parseLong(dates[0]));
			Date endDate = new Date(Long.parseLong(dates[1]));
			System.out.println("start date "+startDate+"  end Date"+endDate);
			return userOrderProdRepo.findByvendorvendor_IdAndStatusCreatedDateBetween(vendorId,status,startDate,endDate,pagable);
		}
		}
		
		return userOrderProdRepo.findByvendorvendor_IdAndStatus(vendorId,status,pagable);
	}

	@Override
	public Object cancelOrderByUser(long orderId, long userId,long orderProductId) {
		UserOrder usrOrdr = null;
		//just to make sure the order belongs to that user only
		Optional<UserOrder> userOrder  = orderRepo.findByIdAndUserId(orderId,userId);
		UserOrderProducts userOrderProducts = null;
		if(userOrder.isPresent()) {
			
			 usrOrdr = userOrder.get();
			String previousStatus=usrOrdr.getOrderStatus();
			usrOrdr.setOrderStatus("CANCELLED");
			usrOrdr=orderRepo.save(usrOrdr);
			
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
				userOrderProducts=userOrderProdRepo.save(userOrderProducts);
				ReturnManagement rm=null;
				if(previousStatus.equals("DISPATCHED"))
				{
					System.out.println("CANCELED AFTER DISPATCHED ENTRY WILL GO TO COURIER RETURN");
					 rm =generateCourierReturnRecord(userOrderProducts,userId);
				}
				
//				//Update AFFILIATE COMMISSION TABLE ALSO IF ORDER IS CANCELLED
				if(userOrderProducts.isAffiliateCommisionExists())
				{
					if(previousStatus.equals("DISPATCHED"))
					{
						updateAffiliateCommission(userOrderProducts,rm);
					}
					else
					{
					updateAffiliateCommission(userOrderProducts,null);
					}
				}
			// update wallet and add entry to payment wallet entry from Super admin to user
			
					//IN CASE OF NORMAL CANCEL PAISE SATH KE SATH WASPI
				// INC ASE OF COURIER RETURN RETURN ACCEPT KE BAD ENTRY HOGI
				if(!previousStatus.equals("DISPATCHED"))
				{
						User admin = userRepo.findByUserType("SUPERADMIN");
						PaymentWalletTransaction pwt = new PaymentWalletTransaction();
						pwt.setAmount(userOrderProducts.getOrderProductPrice());
						pwt.setCreatedDate(new Date());
						pwt.setOrder(usrOrdr);
						pwt.setReciever(String.valueOf(usrOrdr.getUser().getId()));
						pwt.setRecieverDetails(usrOrdr.getUser());
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
		}
		return usrOrdr;
	}

	private ReturnManagement generateCourierReturnRecord(UserOrderProducts userOrdrProd, long userId) {
		ReturnManagement returnManage = new ReturnManagement();
		returnManage.setOrder(userOrdrProd.getUserOrder());
		returnManage.setOrderProduct(userOrdrProd);
		returnManage.setReason("Courier Return ");
		returnManage.setStatus("REQUESTED");
		returnManage.setOrderProduct(userOrdrProd);
		UrlShortner urlShort = new UrlShortner();
		String code=urlShort.generateUid("RT",9);
		returnManage.setReturnCode("RT-"+code);
		returnManage.setReturnType("COURIER_RETURN");
		Optional<User> loginUser = userRepo.findById(userId);
		if(loginUser.isPresent()) {
			returnManage.setUser(loginUser.get());					
			returnManage.setCreatedBy(String.valueOf(loginUser.get().getId()));
		}
		return returnManagement.save(returnManage);
	}

	public void updateAffiliateCommission(UserOrderProducts userOrderProducts, ReturnManagement rm) {
		// TODO Auto-generated method stub
		AffiliateCommisionOrder afOrder = affiliateCommOrderRepo.findByOrderProdId(userOrderProducts.getId());
		if(afOrder!=null)
		{
			afOrder.setStatus(userOrderProducts.getStatus());
			afOrder.setReturnId(rm);
			affiliateCommOrderRepo.save(afOrder);
		}
		
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
				
				if(userOrdrProd.isAffiliateCommisionExists())
				{
					updateAffiliateCommission(userOrdrProd,null);
				}
			
				
			// maintain entry for return management with status
			ReturnManagement returnManage = new ReturnManagement();
			returnManage.setOrder(usrOrdr);
			returnManage.setOrderProduct(userOrdrProd);
			returnManage.setReason(reason);
			returnManage.setStatus("REQUESTED");
			returnManage.setOrderProduct(userOrdrProd);
			UrlShortner urlShort = new UrlShortner();
			String code=urlShort.generateUid("RT", 9);
			returnManage.setReturnCode("RT-"+code);
			returnManage.setReturnType("CUSTOMER_RETURN");
			Optional<User> loginUser = userRepo.findById(userId);
			if(loginUser.isPresent()) {
				returnManage.setUser(loginUser.get());					
				returnManage.setCreatedBy(String.valueOf(loginUser.get().getId()));
			}
			returnManagement.save(returnManage);
			
			// now user only return one order product so update status for affiliate only
			AffiliateCommisionOrder afOrder = affiliateCommOrderRepo.findByOrderProdId(orderProdId);
			if(afOrder != null) {
				afOrder.setStatus("RETURNED");
				afOrder.setReturnId(returnManage);
				affiliateCommOrderRepo.save(afOrder);
			}
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
	public List<OrderUiListingDTO> getLastOrders(int offset) {
		final Pageable pagable = PageRequest.of(offset, 5, Sort.Direction.DESC,"createdDate");
		List<String> statusList = new ArrayList<>();
		statusList.add("DISPATCHED");
		statusList.add("INPROGRESS");
		return userOrderProdRepo.getAllOrderByStatus("PLACED",pagable);
	}

	@Override
	public List<ReturnUiListDTO> getLastReturns(int offset) {
		final Pageable pagable = PageRequest.of(offset, 5, Sort.Direction.DESC,"createdDate");
		return returnManagement.getLastReturns(pagable);
	}

//	@Override
//	public List<UserOrder> getAllOrderByStatus(int offset,String status) {
//		final Pageable pagable = PageRequest.of(offset, 5, Sort.Direction.DESC,"createdDate");
//		return orderRepo.getAllOrderByStatus(pagable,status);
//	}
	
	@Override
	public List<OrderUiListingDTO> getAllOrderByStatus(int offset,String status) {
		final Pageable pagable = PageRequest.of(offset, 5, Sort.Direction.DESC,"createdDate");
		return userOrderProdRepo.getAllOrderByStatus(status,pagable);
	}

	@Override
	public UserWallet getAllWalletDetails(long userId) {
		return UserWalletRepo.findByUserId(userId);
	}

	@Override
	public List<OrderUiListingDTO> getAllVendorSales(long userId, Filter filter) {
		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
				filter.getSortingDirection() != null
				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter.getSortingField());
		return userOrderProdRepo.findByvendorvendor_IdAndStatus(userId,"COMPLETE",pagable);
	}

	@Override
	public List<UserWallet> getTop5Users(String userType) {
		return UserWalletRepo.findTop5ByUserUserType(userType);
	}

	@Override
	public List<OrderUiListingDTO> getAllOrderForSuperAdmin(Filter filter) {
		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
				filter.getSortingDirection() != null
				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter.getSortingField());
		return userOrderProdRepo.findAllOrderForSuperAdmin();
	//	Page<UserOrderProducts> pageDate= userOrderProdRepo.find
	//	return pageDate.hasContent()? pageDate.getContent():null;
	}

	@Override
	public List<OrderUiListingDTO> getLastOrdersForVendor(int offset, long vendorId,String status) {
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
	public long getCountOrderProductByUser(long userId, Filter filter) {
		//CHANGE IMPLEMENTING DATE FILTER AND SEARCH STRING
				if(filter.getSearchString()!=null && !filter.getSearchString().isEmpty())
				{
					return userOrderProdRepo.getAllUsersOrderCountBySearchString(userId,filter.getSearchString());
				}
				else
				{
				if(filter.getDateRange()!=null && !filter.getDateRange().isEmpty())
				{
					String[] dates=filter.getDateRange().split(",");
					Date startDate= new Date(Long.parseLong(dates[0]));
					Date endDate = new Date(Long.parseLong(dates[1]));
					System.out.println("start date "+startDate+"  end Date"+endDate);
					return userOrderProdRepo.countByUserOrderUserIdAndCreatedDateBetween(userId,startDate,endDate);
				}
				}
		return userOrderProdRepo.countByUserOrderUserId(userId);

	}

	@Override
	public long getVendorOrderCount(long vendorId,Filter filter) {
		if(filter.getSearchString()!=null && !filter.getSearchString().isEmpty())
		{
			return userOrderProdRepo.getVendorOrderCountBySearchString(vendorId,filter.getSearchString());
		}
		else
		{
		if(filter.getDateRange()!=null && !filter.getDateRange().isEmpty())
		{
			String[] dates=filter.getDateRange().split(",");
			Date startDate= new Date(Long.parseLong(dates[0]));
			Date endDate = new Date(Long.parseLong(dates[1]));
			System.out.println("start date "+startDate+"  end Date"+endDate);
			return userOrderProdRepo.getVendorOrderCountByDateRange(vendorId,startDate,endDate);
		}
		}
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
			if(userBal.get(userOrdrProd.getVendor().getId()) != null) {
				double oldAmount = userBal.get(Long.valueOf(varient.getCreatedBy()));
				oldAmount += userOrdrProd.getOrderProductPrice();
				userBal.put(userOrdrProd.getVendor().getId(), oldAmount);
			}else {
				double amount = userOrdrProd.getOrderProductPrice();
				userBal.put(userOrdrProd.getVendor().getId(), amount);
			}

			double orderTotalPrice = userOrdrProd.getOrderProductPrice();

		Set<Long> allvendor = userBal.keySet();
		Iterable<PaymentWalletDistribution> allSources =  paymentWalletDistRepo.findAll();
		double percToVendor = 0;
		double TotalPerc = 0;
		for(PaymentWalletDistribution source : allSources) {
			double perc = source.getPerc();
			if(source.getTypeOfuser().equals("AFFILIATE")) {
				long affiliatid;
				User affiliatiduser = null;
				double amountToVendor;
				AffiliateCommisionOrder afOrder = affiliateCommOrderRepo.findByOrderProdId(orderProdId);
				if(afOrder != null) {
					affiliatid = afOrder.getAffiliateId().getId();
					 affiliatiduser =afOrder.getAffiliateId();
					 perc = afOrder.getCommision();
					 amountToVendor = (orderTotalPrice*afOrder.getCommision())/100;							

					    User admin = userRepo.findByUserType("SUPERADMIN");
						OrderTransactionSummaryDTO afDto = new OrderTransactionSummaryDTO();
						afDto.setSenderuserCode(admin.getUserCode());
						afDto.setSenderuserName(admin.getName());
						afDto.setSenderuserId(admin.getId());
						afDto.setRecieveruserId(affiliatid);
						afDto.setRecieveruserName(affiliatiduser.getName());
						afDto.setRecieveruserCode(affiliatiduser.getUserCode());
						afDto.setAmount(amountToVendor);
						afDto.setReason(source.getDistributionTo());
						allTransactionDetails.add(afDto);
				}

				
			}else if(source.getTypeOfuser().equals("SUPERADMIN")) {
				double amoutToadmin = (orderTotalPrice*source.getPerc())/100;
				User admin = userRepo.findByUserType("SUPERADMIN");
				OrderTransactionSummaryDTO adminDto = new OrderTransactionSummaryDTO();
				adminDto.setSenderuserCode(admin.getUserCode());
				adminDto.setSenderuserName(admin.getName());
				adminDto.setSenderuserId(admin.getId());
				adminDto.setRecieveruserId(admin.getId());
				adminDto.setReason(source.getDistributionTo());
				adminDto.setRecieveruserCode(admin.getUserCode());
				adminDto.setRecieveruserName(admin.getName());
				adminDto.setAmount(amoutToadmin);
				allTransactionDetails.add(adminDto);
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

	@Override
	public boolean canPlaceOrderOrNot(List<UserOrderQtyDTO> userOrderList) {
		for(UserOrderQtyDTO userOrder:userOrderList)
		{
			long availableQuantity=productVariantRepo.getAvailableQuantityOfVariant(userOrder.getProductVariantId());
			if(availableQuantity<userOrder.getQty())
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public long getVendorOrderCountByStatus(long vendorId, String status, Filter filter) {
		if(filter.getSearchString()!=null && !filter.getSearchString().isEmpty())
		{
			return userOrderProdRepo.getVendorOrderCountBySearchStringAndStatus(vendorId,status,filter.getSearchString());
		}
		else
		{
		if(filter.getDateRange()!=null && !filter.getDateRange().isEmpty())
		{
			String[] dates=filter.getDateRange().split(",");
			Date startDate= new Date(Long.parseLong(dates[0]));
			Date endDate = new Date(Long.parseLong(dates[1]));
			System.out.println("start date "+startDate+"  end Date"+endDate);
			return userOrderProdRepo.getVendorOrderCountByDateRangeAndStatus(vendorId,status,startDate,endDate);
		}
		}
		return userOrderProdRepo.getVendorOrderCountAndStatus(vendorId,status);
	}

	

}