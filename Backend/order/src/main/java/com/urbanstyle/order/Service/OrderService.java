package com.urbanstyle.order.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.anaadihsoft.common.DTO.OrderTransactionSummaryDTO;
import com.anaadihsoft.common.DTO.OrderUiDTO;
import com.anaadihsoft.common.DTO.OrderUiListingDTO;
import com.anaadihsoft.common.DTO.ReturnUiListDTO;
import com.anaadihsoft.common.DTO.UserOrderFetchDTO;
import com.anaadihsoft.common.DTO.UserOrderQtyDTO;
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

	List<OrderUiListingDTO> getVendorOrder(long vendorId, Filter filter);

	UserOrder setStatusbyUser(long orderId,String status,String reason,long userId,long orderProdId);

	UserOrderProducts setStatusbyVendor(long orderProdId, String status, String trackingId, String link);

	void setStatusbyAdmin(long orderId, long orderProdId, String status,long userId);

	OrderUiDTO getOrderProductForVendor(long vendorId, long orderProductId);

	Object setStatusbyVendorForCompleteOrder(long orderId, String status,long orderProdId);

	List<OrderUiListingDTO> getOrderForVendorByStatus(long vendorId, String status, Filter filter);

	Object cancelOrderByUser(long orderId, long userId, long orderProductId);

	Object returnOrderByUser(long orderId, long userId, String reason, long orderProdId);

	
	List<OrderUiListingDTO> getLastOrders(int offset);

	List<ReturnUiListDTO> getLastReturns(int offset);

	List<OrderUiListingDTO> getAllOrderByStatus(int offset,String status);

	UserWallet getAllWalletDetails(long userId);

	List<OrderUiListingDTO> getAllVendorSales(long userId, Filter filter);

	List<UserWallet> getTop5Users(String userType);

	List<OrderUiListingDTO> getAllOrderForSuperAdmin(Filter filter);

	List<OrderUiListingDTO> getLastOrdersForVendor(int offset, long vendorId, String status);

	UserOrder getOrderDetails(long orderId);

	List<OrderUiListingDTO> getOrderProductByUser(long parseLong, Filter filter);

	UserOrderProducts getOrderById(long parseLong);

	List<PaymentWalletTransaction> getTransactionofOrder(long orderProdId);

	long countForSuperAdmin();

	long getCountOrderProductByUser(long parseLong, Filter filter);

	long getVendorOrderCount(long vendorId, Filter filter);

	List<OrderTransactionSummaryDTO>  getTransactionSummaryofOrder(long orderProdId);

	boolean canPlaceOrderOrNot(List<UserOrderQtyDTO> userOrderList);

	long getVendorOrderCountByStatus(long vendorId, String status, Filter filter);

	void getAllOrderCountForDashboard(String dateRange, Long vendorId, Map<String, Object> resultMap);
}
