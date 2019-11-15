package com.anaadihsoft.order.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public List<UserOrder> getOrderByUser(long userId) {
		return orderRepository.findByUserId(userId);
	}

	@Override
	public Optional<UserOrder> getOrderById(long orderId) {
		return orderRepository.findById(orderId);
	}

}
