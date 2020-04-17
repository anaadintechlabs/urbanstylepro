package com.urbanstyle.order.ServiceImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.anaadihsoft.common.DTO.ReturnUiDTO;
import com.anaadihsoft.common.DTO.ReturnUiListDTO;
import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.AffiliateCommisionOrder;
import com.anaadihsoft.common.master.PaymentWalletDistribution;
import com.anaadihsoft.common.master.PaymentWalletTransaction;
import com.anaadihsoft.common.master.ProductVariant;
import com.anaadihsoft.common.master.ReturnManagement;
import com.anaadihsoft.common.master.User;
import com.anaadihsoft.common.master.UserOrder;
import com.anaadihsoft.common.master.UserOrderProducts;
import com.anaadihsoft.common.master.UserWallet;
import com.urbanstyle.order.Repository.AffiliateCommisionOrderRepo;
import com.urbanstyle.order.Repository.OrderRepository;
import com.urbanstyle.order.Repository.PaymentWalletDistributionRepo;
import com.urbanstyle.order.Repository.PaymentWalletTransactionRepo;
import com.urbanstyle.order.Repository.ProductVarientRepository;
import com.urbanstyle.order.Repository.ReturnOrder;
import com.urbanstyle.order.Repository.UserOrderProductRepository;
import com.urbanstyle.order.Repository.UserRepository;
import com.urbanstyle.order.Service.ReturnService;

@Service
public class ReturnServiceImpl implements ReturnService{

	@Autowired
	private ReturnOrder returnOrderRepository;
	@Autowired
	private PaymentWalletDistributionRepo paymentWalletDistRepo;
	
	@Autowired
	private PaymentWalletTransactionRepo paymentwalletTransactionRepo;
	
	@Autowired
	private com.urbanstyle.order.Repository.UserWalletRepo UserWalletRepo;
	
	@Autowired 
	private OrderRepository orderRepo;
	
	@Autowired
	private UserOrderProductRepository userOrderProdRepo;
	
	
	@Autowired
	private ProductVarientRepository productVariantRepo;
	
	@Autowired 
	private UserRepository userRepo;
	
	@Autowired
	private AffiliateCommisionOrderRepo affiliateCommOrderRepo;
	
	
	
	@Override
	public List<ReturnUiListDTO> getReturnByUser(long userId, Filter filter) {
		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
				filter.getSortingDirection() != null
				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter.getSortingField());
		
		if(filter.getSearchString()!=null && !filter.getSearchString().isEmpty())
		{
			return returnOrderRepository.getAllReturnBySearchString(userId,filter.getSearchString(),pagable);
		}
		else
		{
		if(filter.getDateRange()!=null && !filter.getDateRange().isEmpty())
		{
			String[] dates=filter.getDateRange().split(",");
			Date startDate= new Date(Long.parseLong(dates[0]));
			Date endDate = new Date(Long.parseLong(dates[1]));
			System.out.println("start date "+startDate+"  end Date"+endDate);
			return returnOrderRepository.findByUserIdAndCreatedDateBetween(userId,startDate,endDate,pagable);
		}
		}
		
		
		return returnOrderRepository.findByUserId(userId,pagable);
	}

	@Override
	public void setReturnStatusbyAdmin(long returnId, String status, String sellable, String fault) {
		Optional<ReturnManagement> optReturn = returnOrderRepository.findById(returnId);
		System.out.println("optReturn"+optReturn);
		if(optReturn.isPresent())
		{
			System.out.println("setting status"+optReturn.get());
			ReturnManagement returnObj=optReturn.get();
			returnObj.setStatus(status);
			
			if("ACCEPT".equals(status))
			{
				returnObj.setReturnAuthorizeDate(new Date());
			}
			 //update Inventory in case of return also
			 else if("RECIEVED".equalsIgnoreCase(status)) {
				returnObj.setReturnFault(fault);
				recieveOrder(returnObj,sellable); 
		
		 }
			returnOrderRepository.save(returnObj);
		}
		
	}

	private void recieveOrder(ReturnManagement returnObj, String sellable) {
		
		long orderId=returnObj.getOrder().getId();
		 HashMap<Long,Double> userBal = new HashMap<>();
		 Optional<UserOrder> userOrder  = orderRepo.findById(orderId);
			if(userOrder.isPresent()) {
				
				UserOrder usrOrdr = userOrder.get();
				usrOrdr.setOrderStatus("RETURNED");
				orderRepo.save(usrOrdr);
//				List<UserOrderProducts> userOrderProducts = userOrderProdRepo.findByUserOrderId(orderId);
				UserOrderProducts userOrdrProd=returnObj.getOrderProduct();
				System.out.println("quantity to return"+userOrdrProd.getQuantity());
				//for(UserOrderProducts userOrdrProd :userOrderProducts) {
					userOrdrProd.setStatus("RETURNED");
					if(userOrdrProd.isAffiliateCommisionExists())
					{
						updateAffiliateCommission(userOrdrProd);
					}
					
					//if(sellable!=null && sellable.equalsIgnoreCase("YES")))
				
					ProductVariant varient = userOrdrProd.getProduct();
					varient.setTotalQuantity(varient.getTotalQuantity() + userOrdrProd.getQuantity());
					productVariantRepo.save(varient);
					
					//Here we can take user from User order product
					if(userBal.get(Long.valueOf(varient.getCreatedBy())) != null) {
						double oldAmount = userBal.get(Long.valueOf(varient.getCreatedBy()));
						oldAmount += userOrdrProd.getQuantity()*varient.getDisplayPrice();
						userBal.put(Long.valueOf(varient.getCreatedBy()), oldAmount);
					}else {
						double amount = userOrdrProd.getQuantity()*varient.getDisplayPrice();
						userBal.put(Long.valueOf(varient.getCreatedBy()), amount);
					}
					userOrderProdRepo.save(userOrdrProd);
					
					System.out.println("userBal"+userBal);
				//}
					
					// maintain entry for return management with status
				    ReturnManagement returnManage = returnOrderRepository.findByOrderId(userOrder.get().getId());
				    if(returnManage!=null) {
						returnManage.setStatus("COMPLETE");
						returnManage.setUnitRecieveDate(new Date());
						returnManage.setCustomerRefundDate(new Date());
						returnManage=returnOrderRepository.save(returnManage);
				    }
				 // above update inventory and status and update return status
				    //AGAR TO CUSTOMER RETURN HAI TB TO INKE PASS PAISA GYA HOGA
				    //PR DUSRI TYPE KA RETURN HAI TO NI GYA
				    if(returnManage.getReturnType().equals("CUSTOMER_RETURN"))
				    {
				    	if(userOrdrProd.isAffiliateCommisionExists())
						{
				    returnBalanceToAllAuthority(userBal,usrOrdr,userOrdrProd,true);
						}
				    	else
				    	{
				    		returnBalanceToAllAuthority(userBal,usrOrdr,userOrdrProd,false);	
				    	}
				    }
				    else
				    {
				    	System.out.println("return case of courier");
				    	//jo code cancel me hota hai
				    	returnAsInCancel(usrOrdr,userOrdrProd);
				    }
			}
			
			
			
	
		
	}

	private void returnAsInCancel(UserOrder usrOrdr, UserOrderProducts userOrderProducts) {
		
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
//			userWalletAdmin.setAmount(amount - (userOrderProducts.getOrderProductPrice()*userOrderProducts.getQuantity()));
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

	private void updateAffiliateCommission(UserOrderProducts userOrdrProd) {
		AffiliateCommisionOrder afOrder = affiliateCommOrderRepo.findByOrderProdId(userOrdrProd.getId());
		if(afOrder!=null)
		{
			afOrder.setStatus(userOrdrProd.getStatus());
			affiliateCommOrderRepo.save(afOrder);
		}
		
	}

	private void returnBalanceToAllAuthority(HashMap<Long, Double> userBal, UserOrder userOrder, UserOrderProducts userOrdrProd, boolean affialitePresent) {
		
		
	    // now get data from all vendors
	    Set<Long> allVendors = userBal.keySet();
	    double totalAmount = 0;
	    User admin = userRepo.findByUserType("SUPERADMIN");
	    for(Long vendorId : allVendors) {
	    	//if return typr is after complete tb to inke pass paisa pachucha hoga other wise ni pahucha hga
	    	//there will be multiple entries f
	    	PaymentWalletTransaction pwt = paymentwalletTransactionRepo.findByRecieverAndOrderProdsId(String.valueOf(vendorId),userOrdrProd.getId());
	    	
	    	totalAmount += pwt.getAmount();
	    	User vendorUser = userRepo.findById(vendorId).get();
	    	PaymentWalletTransaction pwtinner = new PaymentWalletTransaction();
			pwtinner.setAmount(pwt.getAmount());
			pwtinner.setCreatedDate(new Date());
			pwtinner.setOrder(userOrder);
			pwtinner.setOrderProds(userOrdrProd);
			pwtinner.setReciever(String.valueOf(userOrder.getUser().getId()));
			pwtinner.setRecieverDetails(userOrder.getUser());
			pwtinner.setSender(vendorUser);
			pwtinner.setStatus("1"); 
			pwtinner.setType("RT");  //Return
			paymentwalletTransactionRepo.save(pwtinner);
			
			// update wallet for vendor
			UserWallet userWalletVendor = UserWalletRepo.findByUserId(vendorUser.getId()); 
			if(userWalletVendor != null) {
				double amountWallet = userWalletVendor.getAmount();
				System.out.println("previos amount"+amountWallet);
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
			if(source.getTypeOfuser().equals("VENDOR")) {
				continue;
			}
			if(source.getTypeOfuser().equals("AFFILIATE")) {
				AffiliateCommisionOrder afOrder = null;
				//Here also Affialite Order Commission comes into picture
				long percGivenToAff=0;
				long affiliatid ;
				if(affialitePresent)
				{
					afOrder = affiliateCommOrderRepo.findByOrderProdId(userOrdrProd.getId());
					if(afOrder!=null)
					{
					percGivenToAff=(long) afOrder.getCommision();
					affiliatid=afOrder.getAffiliateId().getId();
					double amountFromAff=(userOrder.getOrderTotalPrice()*percGivenToAff)/100; 
					totalAmount += amountFromAff;
					
					User affiliatiduser = userRepo.findById(affiliatid).get();
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
					pwtinnerAff.setOrder(userOrder);
					pwtinnerAff.setOrderProds(userOrdrProd);
					pwtinnerAff.setReciever(String.valueOf(userOrder.getUser().getId()));
					pwtinnerAff.setRecieverDetails(userOrder.getUser());
					pwtinnerAff.setSender(affiliatiduser);
					pwtinnerAff.setStatus("1"); 
					pwtinnerAff.setType("RT");  //Return
					paymentwalletTransactionRepo.save(pwtinnerAff);
					}
				}
	
			
			}else if(source.getTypeOfuser().equals("SUPERADMIN")){
				long percGivenToSA = source.getPerc();
				double amountFromSA=(userOrder.getOrderTotalPrice()*percGivenToSA)/100; 
				totalAmount += amountFromSA;
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
				pwtinnerSA.setOrder(userOrder);
				pwtinnerSA.setOrderProds(userOrdrProd);
				pwtinnerSA.setReciever(String.valueOf(userOrder.getUser().getId()));
				pwtinnerSA.setRecieverDetails(userOrder.getUser());
				pwtinnerSA.setSender(admin);
				pwtinnerSA.setStatus("1"); 
				pwtinnerSA.setType("RT");  //Return
				paymentwalletTransactionRepo.save(pwtinnerSA);
			}
		}
		
		// now all balance calculate and assign to user
		User actualUser = userOrder.getUser();
		UserWallet userWalletuser = UserWalletRepo.findByUserId(actualUser.getId()); 
		if(userWalletuser != null) {
			double amount = userWalletuser.getAmount();
			userWalletuser.setAmount(amount + totalAmount);
			userWalletuser.setModifiedDate(new Date());
			userWalletuser.setStatus("1");
			userWalletuser.setUser(actualUser);
			UserWalletRepo.save(userWalletuser);
		}
		else
		{
			userWalletuser= new UserWallet();
			userWalletuser.setAmount( totalAmount);
			userWalletuser.setModifiedDate(new Date());
			userWalletuser.setStatus("1");
			userWalletuser.setUser(actualUser);
			UserWalletRepo.save(userWalletuser);
		}
		
	}

	@Override
	public List<ReturnUiListDTO> getReturnByVendor(long vendorId, Filter filter,String type) {
		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
				filter.getSortingDirection() != null
				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter.getSortingField());
		//Here We have to get return from User Order
		if(filter.getSearchString()!=null && !filter.getSearchString().isEmpty())
		{
			return returnOrderRepository.getAllReturnOfVendorBySearchString(vendorId,filter.getSearchString(),type,pagable);
		}
		else
		{
			if(filter.getDateRange()!=null && !filter.getDateRange().isEmpty())
			{
				String[] dates=filter.getDateRange().split(",");
				Date startDate= new Date(Long.parseLong(dates[0]));
				Date endDate = new Date(Long.parseLong(dates[1]));
				System.out.println("start date "+startDate+"  end Date"+endDate);
				return returnOrderRepository.findByOrderProductVendorIdAndDateRange(vendorId,startDate,endDate,type,pagable);
			}
		}
		
		return returnOrderRepository.findByOrderProductVendorId(vendorId,type,pagable);
		//return returnOrderRepository
	}

	@Override
	public List<ReturnUiListDTO> getReturnForSuperAdmin(Filter filter) {
		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
				filter.getSortingDirection() != null
				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter.getSortingField());
		//Here We have to get return from User Order
		if(filter.getSearchString()!=null && !filter.getSearchString().isEmpty())
		{
			return returnOrderRepository.getAllReturnForSuperadmiBySearchString(filter.getSearchString(),pagable);
		}
		else
		{
		if(filter.getDateRange()!=null && !filter.getDateRange().isEmpty())
		{
			String[] dates=filter.getDateRange().split(",");
			Date startDate= new Date(Long.parseLong(dates[0]));
			Date endDate = new Date(Long.parseLong(dates[1]));
			System.out.println("start date "+startDate+"  end Date"+endDate);
			return returnOrderRepository.getAllReturnsByDateRange(startDate,endDate,pagable);
		}
		}
		return returnOrderRepository.findAllForSuperAdmin(pagable);
//		return page.hasContent()?page.getContent():null;
	}

	@Override
	public ReturnUiDTO getAllDetailOfReturn(long returnId) {
		Optional<ReturnManagement> returnManage=returnOrderRepository.findById(returnId);
		 if(returnManage.isPresent())
		 {
		return new ReturnUiDTO(returnManage.get());
		 }
		 return null;
	}

	@Override
	public long getCountForSuperAdmin(Filter filter) {
		if(filter.getSearchString()!=null && !filter.getSearchString().isEmpty())
		{
			return returnOrderRepository.getAllReturnCountBySearchString(filter.getSearchString());
		}
		else
		{
		if(filter.getDateRange()!=null && !filter.getDateRange().isEmpty())
		{
			String[] dates=filter.getDateRange().split(",");
			Date startDate= new Date(Long.parseLong(dates[0]));
			Date endDate = new Date(Long.parseLong(dates[1]));
			return returnOrderRepository.countByCreatedDateBetween(startDate,endDate);
		}
		}
		return returnOrderRepository.count();
	}

	@Override
	public long getReturnCountByUser(long userId,Filter filter) {
		
		if(filter.getSearchString()!=null && !filter.getSearchString().isEmpty())
		{
			return returnOrderRepository.getAllUsersBySearchString(userId,filter.getSearchString());
		}
		else
		{
		if(filter.getDateRange()!=null && !filter.getDateRange().isEmpty())
		{
			String[] dates=filter.getDateRange().split(",");
			Date startDate= new Date(Long.parseLong(dates[0]));
			Date endDate = new Date(Long.parseLong(dates[1]));
			System.out.println("start date "+startDate+"  end Date"+endDate);
			return returnOrderRepository.getCountAllReturnByDateRange(userId,startDate,endDate);
		}
		}
		
		return returnOrderRepository.countByUserId(userId);

	}

	@Override
	public long getReturnCountByVendor(long vendorId,Filter filter,String type) {
		if(filter.getSearchString()!=null && !filter.getSearchString().isEmpty())
		{
			return returnOrderRepository.getAllReturnCountBySearchString(vendorId,filter.getSearchString(),type);
		}
		else
		{
		if(filter.getDateRange()!=null && !filter.getDateRange().isEmpty())
		{
			String[] dates=filter.getDateRange().split(",");
			Date startDate= new Date(Long.parseLong(dates[0]));
			Date endDate = new Date(Long.parseLong(dates[1]));
			return returnOrderRepository.countAllReturnByDateRange(vendorId,startDate,endDate,type);
		}
		}
		return returnOrderRepository.countByOrderProductVendorId(vendorId,type);
	}

	@Override
	public void setTrackingCodeAndUrlForAdmin(long returnId,String trackingId, String trackingUrl) {
		Optional<ReturnManagement> rm =returnOrderRepository.findById(returnId);
		if(rm.isPresent())
		{
			ReturnManagement obj= rm.get();
			obj.setTrackingId(trackingId);
			obj.setTrackingUrl(trackingUrl);
			returnOrderRepository.save(obj);
		}
	}

	@Override
	public void getAllReturnCountForDashboard(String dateRange, Long vendorId, Map<String, Object> resultMap) {
		
		Date startDate;
		Date endDate;
		if(dateRange!=null && !dateRange.isEmpty())
		{
		String[] dates=dateRange.split(",");
		startDate = new Date(Long.parseLong(dates[0]));
		endDate = new Date(Long.parseLong(dates[1]));
		}
		else
		{
			startDate= new Date();
			endDate = new Date();
		}
		//settig start date to start and end date to end of day
		Calendar cal = new GregorianCalendar();
		cal.setTime(startDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		startDate=cal.getTime();
		
		Calendar cal2 = new GregorianCalendar();
		cal2.setTime(endDate);
		cal2.set(Calendar.HOUR_OF_DAY, 23);
		cal2.set(Calendar.MINUTE, 59);
		cal2.set(Calendar.SECOND, 59);
		endDate=cal2.getTime();
		
		//superadmin
		if(vendorId==null)
		{
			resultMap.put("totalCourierReturn", getCountOfReturByTypeAndDateRange("COURIER_RETURN",startDate,endDate));
			resultMap.put("totalCustomerReturn", getCountOfReturByTypeAndDateRange("CUSTOMER_RETURN",startDate,endDate));
			
			resultMap.put("requestedCourierReturn", getCountOfReturnByTypeAndStatusAndDateRange("COURIER_RETURN","REQUESTED",startDate,endDate));
			resultMap.put("requestedCustomerReturn", getCountOfReturnByTypeAndStatusAndDateRange("CUSTOMER_RETURN","REQUESTED",startDate,endDate));

			resultMap.put("completedCourierReturn",getCountOfReturnByTypeAndStatusAndDateRange("COURIER_RETURN","COMPLETE",startDate,endDate));
			resultMap.put("compltedCustomerReturn", getCountOfReturnByTypeAndStatusAndDateRange("CUSTOMER_RETURN","COMPLETE",startDate,endDate));

			
		}
		else
		{
			resultMap.put("totalCourierReturn", getCountOfReturByTypeAndDateRangeAndVendorId("COURIER_RETURN",startDate,endDate,vendorId.longValue()));
			resultMap.put("totalCustomerReturn", getCountOfReturByTypeAndDateRangeAndVendorId("CUSTOMER_RETURN",startDate,endDate,vendorId.longValue()));
			
			resultMap.put("requestedCourierReturn", getCountOfReturnByTypeAndStatusAndDateRangeAndVendorId("COURIER_RETURN","REQUESTED",startDate,endDate,vendorId.longValue()));
			resultMap.put("requestedCustomerReturn", getCountOfReturnByTypeAndStatusAndDateRangeAndVendorId("CUSTOMER_RETURN","REQUESTED",startDate,endDate,vendorId.longValue()));

			resultMap.put("completedCourierReturn",getCountOfReturnByTypeAndStatusAndDateRangeAndVendorId("COURIER_RETURN","COMPLETE",startDate,endDate,vendorId.longValue()));
			resultMap.put("compltedCustomerReturn", getCountOfReturnByTypeAndStatusAndDateRangeAndVendorId("CUSTOMER_RETURN","COMPLETE",startDate,endDate,vendorId.longValue()));

		}
	}

	private long getCountOfReturnByTypeAndStatusAndDateRangeAndVendorId(String type, String status, Date startDate,
			Date endDate, long vendorId) {
		return returnOrderRepository.countAllReturnByDateRangeAndStatus(vendorId,startDate,endDate,type,status);
	}

	private long getCountOfReturByTypeAndDateRangeAndVendorId(String type, Date startDate, Date endDate,
			long vendorId) {
		return returnOrderRepository.countAllReturnByDateRange(vendorId,startDate,endDate,type);
	}

	private long getCountOfReturnByTypeAndStatusAndDateRange(String type, String status, Date startDate,
			Date endDate) {
		return returnOrderRepository.countByReturnTypeAndStatusAndCreatedDateBetween(type,status,startDate,endDate);
	}

	private long getCountOfReturByTypeAndDateRange(String type, Date startDate, Date endDate) {
		return returnOrderRepository.countByReturnTypeAndCreatedDateBetween(type,startDate,endDate);
	}


}
