package com.urbanstyle.order.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.master.UserOrder;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<UserOrder, Long> {

	List<UserOrder> findByUserId(long userId, Pageable pagable);

	Optional<UserOrder> findByIdAndUserId(long orderId, long userId);


//	@Query("From UserOrder order by createdDate where orderStatus in (?2) ")
//	List<UserOrder> getLastOrders(Pageable pagable, List<String> statusList);

	@Query(" From UserOrder where orderStatus =?2")
	List<UserOrder> getAllOrderByStatus(Pageable pagable, String status);

//	@Query("Select distinct uop.userOrder from UserOrderProducts uop where uop.vendor.id =?3 and uop.status=?2 order by uop.userOrder.orderPlacedDate desc")
//	List<UserOrder> getAllOrderByStatusAndUserId(Pageable pagable, String status, int vendorId);
	
}
