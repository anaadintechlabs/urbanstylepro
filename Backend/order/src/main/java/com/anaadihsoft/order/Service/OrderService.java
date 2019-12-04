package com.anaadihsoft.order.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.anaadihsoft.common.DTO.UserOrderSaveDTO;
import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.UserOrder;

@Service
public interface OrderService {

	List<UserOrder> getOrderByUser(long userId, Filter filter);

	Optional<UserOrder> getOrderById(long orderId);

	Object saveorUpdate(UserOrderSaveDTO userDetailSave);

}
