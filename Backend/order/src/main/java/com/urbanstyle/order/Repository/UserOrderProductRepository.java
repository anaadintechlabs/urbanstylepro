package com.urbanstyle.order.Repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.master.User;
import com.anaadihsoft.common.master.UserOrder;
import com.anaadihsoft.common.master.UserOrderProducts;

@Repository
public interface UserOrderProductRepository extends PagingAndSortingRepository<UserOrderProducts, Long> {

	List<UserOrderProducts> findByUserOrderId(long id);

	@Query("Select distinct uop.userOrder from UserOrderProducts uop where uop.vendor.id =?1 order by uop.userOrder.orderPlacedDate desc")
	List<UserOrder> findByvendorvendor_Id(long vendorId);

	List<UserOrderProducts> findByUserOrderIdAndVendorId(long orderId, long vendorId);

	@Query("Select distinct uop.userOrder from UserOrderProducts uop where uop.vendor.id =?1 and uop.userOrder.orderStatus=?2 order by uop.userOrder.orderPlacedDate desc")
	List<UserOrder> findByvendorvendor_IdAndStatus(long vendorId, String status);

	List<UserOrderProducts> findByVendorIdAndStatus(long userId, String string, Pageable pagable);
	
	@Query(" from User Where userType =?2")
	List<User> getAllUsers(Pageable pagable, String userType);

}
