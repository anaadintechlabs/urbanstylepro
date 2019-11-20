package com.anaadihsoft.order.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.UserOrder;
import com.anaadihsoft.order.Repository.OrderRepository;
import com.anaadihsoft.order.Service.OrderService;
import com.urbanstyle.user.Repository.UserRepository;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserOrder saveorUpdate(UserOrder userOrder) {
		userOrder.setUser(userRepository.getOne(userOrder.getUser().getId()));
		return orderRepository.save(userOrder);
	}

	@Override
	public List<UserOrder> getOrderByUser(long userId,Filter filter) {
		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
				filter.getSortingDirection() != null
				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter.getSortingField());
		
		return orderRepository.findByUserId(userId,pagable);
	}

	@Override
	public Optional<UserOrder> getOrderById(long orderId) {
		return orderRepository.findById(orderId);
	}

}
