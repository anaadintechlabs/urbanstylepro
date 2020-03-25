package com.urbanstyle.order.ServiceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.PaymentWalletDistribution;
import com.anaadihsoft.common.master.PaymentWalletTransaction;
import com.anaadihsoft.common.master.ProductVariant;
import com.anaadihsoft.common.master.ReturnManagement;
import com.anaadihsoft.common.master.User;
import com.anaadihsoft.common.master.UserOrder;
import com.anaadihsoft.common.master.UserOrderProducts;
import com.anaadihsoft.common.master.UserWallet;
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
	
	
	
	@Override
	public List<ReturnManagement> getReturnByUser(long userId, Filter filter) {
		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
				filter.getSortingDirection() != null
				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter.getSortingField());
		return returnOrderRepository.findByUserId(userId,pagable);
	}

	@Override
	public void setReturnStatusbyAdmin(long returnId, String status) {
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
				recieveOrder(returnObj); 
		
		 }
			returnOrderRepository.save(returnObj);
		}
		
	}

	private void recieveOrder(ReturnManagement returnObj) {
		
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
					ProductVariant varient = userOrdrProd.getProduct();
					varient.setTotalQuantity(varient.getTotalQuantity() + userOrdrProd.getQuantity());
					if(userBal.get(Long.valueOf(varient.getCreatedBy())) != null) {
						double oldAmount = userBal.get(Long.valueOf(varient.getCreatedBy()));
						oldAmount += userOrdrProd.getQuantity()*varient.getDisplayPrice();
						userBal.put(Long.valueOf(varient.getCreatedBy()), oldAmount);
					}else {
						double amount = userOrdrProd.getQuantity()*varient.getDisplayPrice();
						userBal.put(Long.valueOf(varient.getCreatedBy()), amount);
					}
					userOrderProdRepo.save(userOrdrProd);
					productVariantRepo.save(varient);
					System.out.println("userBal"+userBal);
				//}
					
					// maintain entry for return management with status
				    ReturnManagement returnManage = returnOrderRepository.findByOrderId(userOrder.get().getId());
				    if(returnManage!=null) {
						returnManage.setStatus("COMPLETE");
						returnManage.setUnitRecieveDate(new Date());
						returnManage.setCustomerRefundDate(new Date());
						returnOrderRepository.save(returnManage);
				    }
				 // above update inventory and status and update return status
				    returnBalanceToAllAuthority(userBal,usrOrdr,userOrdrProd);
				    
			}
			
			
			
	
		
	}

	private void returnBalanceToAllAuthority(HashMap<Long, Double> userBal, UserOrder userOrder, UserOrderProducts userOrdrProd) {
		
		
		
	    // now get data from all vendors
	    Set<Long> allVendors = userBal.keySet();
	    double totalAmount = 0;
	    User admin = userRepo.findByUserType("SUPERADMIN");
	    for(Long vendorId : allVendors) {
	    	PaymentWalletTransaction pwt = paymentwalletTransactionRepo.findByRecieverAndOrderId(String.valueOf(vendorId),userOrder.getId());
	    	
	    	totalAmount += pwt.getAmount();
	    	User vendorUser = userRepo.findById(vendorId).get();
	    	PaymentWalletTransaction pwtinner = new PaymentWalletTransaction();
			pwtinner.setAmount(pwt.getAmount());
			pwtinner.setCreatedDate(new Date());
			pwtinner.setOrder(userOrder);
			pwtinner.setOrderProds(userOrdrProd);
			pwtinner.setReciever(String.valueOf(userOrder.getUser().getId()));
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
				double amountFromAff=(userOrder.getOrderTotalPrice()*percGivenToAff)/100; 
				totalAmount += amountFromAff;
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
				pwtinnerAff.setOrder(userOrder);
				pwtinnerAff.setOrderProds(userOrdrProd);
				pwtinnerAff.setReciever(String.valueOf(userOrder.getUser().getId()));
				pwtinnerAff.setSender(affiliatiduser);
				pwtinnerAff.setStatus("1"); 
				pwtinnerAff.setType("RT");  //Return
				paymentwalletTransactionRepo.save(pwtinnerAff);
			}else {
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
		
	}

	@Override
	public List<ReturnManagement> getReturnByVendor(long vendorId, Filter filter) {
		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
				filter.getSortingDirection() != null
				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter.getSortingField());
		//Here We have to get return from User Order
		return returnOrderRepository.findByOrderProductVendorId(vendorId,pagable);
		//return returnOrderRepository
	}

	@Override
	public List<ReturnManagement> getReturnForSuperAdmin(Filter filter) {
		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
				filter.getSortingDirection() != null
				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter.getSortingField());
		//Here We have to get return from User Order
		Page<ReturnManagement> page= returnOrderRepository.findAll(pagable);
		return page.hasContent()?page.getContent():null;
	}

	@Override
	public ReturnManagement getAllDetailOfReturn(long returnId) {
		Optional<ReturnManagement> returnManage=returnOrderRepository.findById(returnId);
		return returnManage.isPresent()?returnManage.get():null;
	}

	@Override
	public long getCountForSuperAdmin(Filter filter) {
		return returnOrderRepository.count();
	}


}
