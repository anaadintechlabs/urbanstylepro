package com.anaadihsoft.order.ServiceImpl;

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
import com.anaadihsoft.order.Repository.OrderRepository;
import com.anaadihsoft.order.Repository.UserOrderProductRepository;
import com.anaadihsoft.order.Service.OrderService;
import com.urbanstyle.product.service.ProductVarientRepository;
import com.urbanstyle.product.service.ProductVarientService;
import com.urbanstyle.user.Repository.AddressRepository;
import com.urbanstyle.user.Repository.UserRepository;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private UserRepository userRepository;
	
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
		List<UserOrderQtyDTO> userOrderList = userOrder.getUserOrderList();
		String paymentType = userOrder.getPaymentType();
		Address address = userOrder.getAddress();
		String from = userOrder.getFrom();
		String to = userOrder.getTo();
		double totalPrice = 0;
		UserOrder userOrderSave = new UserOrder();
		
		// get Total Price
		for(UserOrderQtyDTO userDTO : userOrderList) {
			long prodVarId = userDTO.getProductVariantId();
			Optional<ProductVariant> optionalp = productVariantRepo.findById(prodVarId);
			if(optionalp.isPresent()) {
				ProductVariant product  = optionalp.get();
				totalPrice  += product.getDisplayPrice();
			}
		}
		
		// save adress in case of new or old hibernate manage
			addressRepo.save(address);
			
			Optional<User> user = userRepo.findById(userId);
			User loginUser  = null;
			if(user.isPresent()) {
				 loginUser = user.get();
			}
			
			userOrderSave.setUser(loginUser);
			userOrderSave.setAddress(address);
			userOrderSave.setOrderTotalPrice(totalPrice);
			userOrderSave.setCreatedBy(loginUser.getName());
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
	public List<UserOrder> getOrderByUser(long userId,Filter filter) {
		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
				filter.getSortingDirection() != null
				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter.getSortingField());
		
		 List<UserOrder> userorder = orderRepository.findByUserId(userId,pagable);
		 List<UserOrderFetchDTO> userOrderFetch = new ArrayList<>();
		 for(UserOrder order : userorder) {
			 UserOrderFetchDTO dto = new UserOrderFetchDTO();
			 dto.setAddress(order.getAddress());
			 dto.setUserOrder(order);
			List<UserOrderProducts> listOfProdts =  userOrderProdRepo.findByUserOrderId(order.getId());
		 }
		 return null;
	}

	@Override
	public Optional<UserOrder> getOrderById(long orderId) {
		return orderRepository.findById(orderId);
	}


}
