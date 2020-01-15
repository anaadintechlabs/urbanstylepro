package com.urbanstyle.order.ServiceImpl;

import java.util.ArrayList;
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
import com.anaadihsoft.common.master.ProductVariant;
import com.anaadihsoft.common.master.User;
import com.anaadihsoft.common.master.UserOrder;
import com.anaadihsoft.common.master.UserOrderProducts;
import com.urbanstyle.order.Repository.AddressRepository;
import com.urbanstyle.order.Repository.OrderRepository;
import com.urbanstyle.order.Repository.ProductVarientRepository;
import com.urbanstyle.order.Repository.UserOrderProductRepository;
import com.urbanstyle.order.Repository.UserRepository;
import com.urbanstyle.order.Service.OrderService;


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
			
			userOrderSave.setOrderStatus("PLACED");
			orderRepo.save(userOrderSave);
			
			// save user Product order
			
			List<UserOrderProducts> TotalProducts = new ArrayList<UserOrderProducts>();
			
			for(UserOrderQtyDTO userDTO : userOrderList) {
				long prodVarId = userDTO.getProductVariantId();
				int quantity = userDTO.getQty();
				Optional<ProductVariant> optionalp = productVariantRepo.findById(prodVarId);
				ProductVariant productVar = null;
				if(optionalp.isPresent()) {
					productVar = optionalp.get();
				}
				UserOrderProducts userOrderProduct = new UserOrderProducts();
				userOrderProduct.setProduct(productVar);
				userOrderProduct.setQuantity(quantity);
				userOrderProduct.setUserOrder(userOrderSave);
				userOrderProduct.setComment("COMMENT...");
				TotalProducts.add(userOrderProduct);
			}
			
			userOrderProdRepo.saveAll(TotalProducts);
			
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


}