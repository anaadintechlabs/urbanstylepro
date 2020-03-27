package com.urbanstyle.order.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.anaadihsoft.common.DTO.UserOrderFetchDTO;
import com.anaadihsoft.common.DTO.UserOrderSaveDTO;
import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.PaymentWalletTransaction;
import com.anaadihsoft.common.master.ReturnManagement;
import com.anaadihsoft.common.master.UserOrder;
import com.anaadihsoft.common.master.UserOrderProducts;
import com.anaadihsoft.common.master.UserWallet;

@Service
public interface OrderService {

	//List<UserOrderFetchDTO> getOrderByUser(long userId, Filter filter);

	//List<UserOrderFetchDTO> getOrderById(long orderId);

	Object saveorUpdate(UserOrderSaveDTO userDetailSave);

	List<UserOrderProducts> getVendorOrder(long vendorId);

	UserOrder setStatusbyUser(long orderId,String status,String reason,long userId,long orderProdId);

	UserOrderProducts setStatusbyVendor(long orderProdId, String status);

	void setStatusbyAdmin(long orderId, long orderProdId, String status,long userId);

	UserOrderProducts getOrderProductForVendor(long vendorId, long orderProductId);

	Object setStatusbyVendorForCompleteOrder(long orderId, String status,long orderProdId);

	List<UserOrderProducts> getOrderForVendorByStatus(long vendorId, String status);

	Object cancelOrderByUser(long orderId, long userId, long orderProductId);

	Object returnOrderByUser(long orderId, long userId, String reason, long orderProdId);

	
	List<UserOrderProducts> getLastOrders(int offset);

	List<ReturnManagement> getLastReturns(int offset);

	List<UserOrderProducts> getAllOrderByStatus(int offset,String status);

	UserWallet getAllWalletDetails(long userId);

	List<UserOrderProducts> getAllVendorSales(long userId, Filter filter);

	List<UserWallet> getTop5Users(String userType);

	List<UserOrderProducts> getAllOrderForSuperAdmin(Filter filter);

	List<UserOrderProducts> getLastOrdersForVendor(int offset, long vendorId, String status);

	UserOrder getOrderDetails(long orderId);

	List<UserOrderProducts> getOrderProductByUser(long parseLong, Filter filter);

	UserOrderProducts getOrderById(long parseLong);

	List<PaymentWalletTransaction> getTransactionofOrder(long orderProdId);

	long countForSuperAdmin();

	long getCountOrderProductByUser(long parseLong);

	long getVendorOrderCount(long vendorId);
}
