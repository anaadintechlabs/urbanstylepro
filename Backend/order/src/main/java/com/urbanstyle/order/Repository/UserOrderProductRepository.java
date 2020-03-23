package com.urbanstyle.order.Repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.master.User;
import com.anaadihsoft.common.master.UserOrder;
import com.anaadihsoft.common.master.UserOrderProducts;

@Repository
public interface UserOrderProductRepository extends PagingAndSortingRepository<UserOrderProducts, Long> , JpaRepository<UserOrderProducts, Long>{

	List<UserOrderProducts> findByUserOrderId(long id);

//	@Query("Select distinct uop.userOrder from UserOrderProducts uop where uop.vendor.id =?1 order by uop.userOrder.orderPlacedDate desc")
//	List<UserOrder> findByvendorvendor_Id(long vendorId);
	@Query("Select distinct uop from UserOrderProducts uop where uop.vendor.id =?1 order by uop.userOrder.orderPlacedDate desc")
	List<UserOrderProducts> findByvendorvendor_Id(long vendorId);

	
	UserOrderProducts findByUserOrderIdAndVendorId(long orderId, long vendorId);

//	@Query("Select distinct uop.userOrder from UserOrderProducts uop where uop.vendor.id =?1 and uop.userOrder.orderStatus=?2 order by uop.userOrder.orderPlacedDate desc")
//	List<UserOrder> findByvendorvendor_IdAndStatus(long vendorId, String status);

	@Query("Select distinct uop from UserOrderProducts uop where uop.vendor.id =?1 and uop.status=?2 order by uop.userOrder.orderPlacedDate desc")
	List<UserOrderProducts> findByvendorvendor_IdAndStatus(long vendorId, String status);

	
	List<UserOrderProducts> findByVendorIdAndStatus(long userId, String string, Pageable pagable);
	
	@Query(" from User Where userType =?2")
	List<User> getAllUsers(Pageable pagable, String userType);

	List<UserOrderProducts> findByUserOrderUserId(long userId, Pageable pagable);

	UserOrderProducts findByIdAndUserOrderUserId(long orderId, long userId);

	@Query(" From UserOrderProducts where status =?1")
	List<UserOrderProducts> getAllOrderByStatus(String status, Pageable pagable);

	UserOrderProducts findByIdAndVendorId(long orderProductId, long vendorId);

	@Query("Select  uop from UserOrderProducts uop where uop.vendor.id =?3 and uop.status=?2 order by uop.userOrder.orderPlacedDate desc")
	List<UserOrderProducts> getAllOrderByStatusAndUserId(Pageable pagable, String string, int vendorId);

}
