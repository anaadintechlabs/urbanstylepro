package com.anaadihsoft.order.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.anaadihsoft.common.master.UserOrder;

@Service
public interface OrderService {

	UserOrder saveorUpdate(UserOrder userOrder);

	List<UserOrder> getOrderByUser(long userId);

	Optional<UserOrder> getOrderById(long orderId);

}
