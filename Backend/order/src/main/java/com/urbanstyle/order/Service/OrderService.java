package com.urbanstyle.order.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.anaadihsoft.common.DTO.UserOrderFetchDTO;
import com.anaadihsoft.common.DTO.UserOrderSaveDTO;
import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.ReturnManagement;
import com.anaadihsoft.common.master.UserOrder;
import com.anaadihsoft.common.master.UserOrderProducts;
import com.anaadihsoft.common.master.UserWallet;

@Service
public interface OrderService {

	List<UserOrderFetchDTO> getOrderByUser(long userId, Filter filter);

	List<UserOrderFetchDTO> getOrderById(long orderId);

	Object saveorUpdate(UserOrderSaveDTO userDetailSave);

	List<UserOrder> getVendorOrder(long vendorId);

	UserOrder setStatusbyUser(long orderId,String status,String reason,long userId);

	UserOrderProducts setStatusbyVendor(long orderProdId, String status);

	void setStatusbyAdmin(long orderId, String status,long userId);

	List<UserOrderProducts> getOrderProductForVendor(long vendorId, long orderId);

	Object setStatusbyVendorForCompleteOrder(long orderId, String status);

	List<UserOrder> getOrderForVendorByStatus(long vendorId, String status);

	Object cancelOrderByUser(long orderId, long userId);

	Object returnOrderByUser(long orderId, long userId, String reason);

	
	List<UserOrder> getLastOrders(int offset);

	List<ReturnManagement> getLastReturns(int offset);

	List<UserOrder> getAllOrderByStatus(int offset,String status);

	UserWallet getAllWalletDetails(long userId);

	List<UserOrderProducts> getAllVendorSales(long userId, Filter filter);

	List<UserWallet> getTop5Users(String userType);

	List<UserOrder> getAllOrderForSuperAdmin(Filter filter);

	List<UserOrder> getLastOrdersForVendor(int offset, int vendorId);

	UserOrder getOrderDetails(long orderId);
}
