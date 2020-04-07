package com.urbanstyle.order.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.DTO.OrderUiDTO;
import com.anaadihsoft.common.DTO.OrderUiListingDTO;
import com.anaadihsoft.common.master.User;
import com.anaadihsoft.common.master.UserOrder;
import com.anaadihsoft.common.master.UserOrderProducts;

@Repository
public interface UserOrderProductRepository extends PagingAndSortingRepository<UserOrderProducts, Long> , JpaRepository<UserOrderProducts, Long>{

	List<UserOrderProducts> findByUserOrderId(long id);

//	@Query("Select distinct uop.userOrder from UserOrderProducts uop where uop.vendor.id =?1 order by uop.userOrder.orderPlacedDate desc")
//	List<UserOrder> findByvendorvendor_Id(long vendorId);
	@Query("Select new com.anaadihsoft.common.DTO.OrderUiListingDTO(uop) from UserOrderProducts uop where uop.vendor.id =?1 ")
	List<OrderUiListingDTO> findByvendorvendor_Id(long vendorId, Pageable pagable);

	
	UserOrderProducts findByUserOrderIdAndVendorId(long orderId, long vendorId);

//	@Query("Select distinct uop.userOrder from UserOrderProducts uop where uop.vendor.id =?1 and uop.userOrder.orderStatus=?2 order by uop.userOrder.orderPlacedDate desc")
//	List<UserOrder> findByvendorvendor_IdAndStatus(long vendorId, String status);

	@Query("Select new com.anaadihsoft.common.DTO.OrderUiListingDTO(uop) from UserOrderProducts uop where uop.vendor.id =?1 and uop.status=?2 ")
	List<OrderUiListingDTO> findByvendorvendor_IdAndStatus(long vendorId, String status, Pageable pagable);

	
	List<UserOrderProducts> findByVendorIdAndStatus(long userId, String string, Pageable pagable);
	
	@Query(" from User Where userType =?2")
	List<User> getAllUsers(Pageable pagable, String userType);

	@Query("Select new com.anaadihsoft.common.DTO.OrderUiListingDTO(u) FROM UserOrderProducts u where  u.userOrder.user.id =?1 ")
	List<OrderUiListingDTO> findByUserOrderUserId(long userId, Pageable pagable);

	UserOrderProducts findByIdAndUserOrderUserId(long orderId, long userId);

	@Query(" Select new com.anaadihsoft.common.DTO.OrderUiListingDTO(u) From UserOrderProducts u where u.status =?1")
	List<OrderUiListingDTO> getAllOrderByStatus(String status, Pageable pagable);

	@Query("Select new com.anaadihsoft.common.DTO.OrderUiDTO(u) FROM UserOrderProducts u where  u.id =?1 and u.vendor.id=?2")
	OrderUiDTO findByIdAndVendorId(long orderProductId, long vendorId);

	@Query("Select  new com.anaadihsoft.common.DTO.OrderUiListingDTO(uop) from UserOrderProducts uop where uop.vendor.id =?2 and uop.status=?1 ")
	List<OrderUiListingDTO> getAllOrderByStatusAndUserId(String status, long vendorId, Pageable pagable);

	long countByUserOrderUserId(long userId);

	@Query("Select count(distinct uop) from UserOrderProducts uop where uop.vendor.id =?1 order by uop.userOrder.orderPlacedDate desc")
	long getVendorOrderCount(long vendorId);
	
	
	@Query("Select count(distinct uop) from UserOrderProducts uop where uop.vendor.id =?1 and uop.status=?2 order by uop.userOrder.orderPlacedDate desc")
	long getVendorOrderCountAndStatus(long vendorId,String status);

	
	@Query("Select new com.anaadihsoft.common.DTO.OrderUiListingDTO(u) FROM UserOrderProducts u where  u.userOrder.user.id =?1  AND "+
			"  u.createdDate between ?2 and ?3 ")
	List<OrderUiListingDTO> findByUserOrderUserIdAndCreatedDateBetween(long userId, Date startDate, Date endDate,
			Pageable pagable);

	@Query("Select new com.anaadihsoft.common.DTO.OrderUiListingDTO(u) FROM UserOrderProducts u where  u.userOrder.user.id =?1  AND "+
			"LOWER(u.product.variantName) LIKE %?2% OR LOWER(u.product.variantCode) LIKE %?2%  " +  
			"OR LOWER(u.product.product.productName) LIKE %?2%")
	List<OrderUiListingDTO> getAllUsersOrderBySearchString(long userId, String searchString, Pageable pagable);

	long countByUserOrderUserIdAndCreatedDateBetween(long userId, Date startDate, Date endDate);

	@Query("Select count(u) FROM UserOrderProducts u where  u.userOrder.user.id =?1  AND "+
			"LOWER(u.product.variantName) LIKE %?2% OR LOWER(u.product.variantCode) LIKE %?2%  " +  
			"OR LOWER(u.product.product.productName) LIKE %?2%")
	long getAllUsersOrderCountBySearchString(long userId, String searchString);

	@Query("Select new com.anaadihsoft.common.DTO.OrderUiListingDTO(uop) from UserOrderProducts uop where uop.vendor.id =?1 and uop.createdDate between ?2 and ?3")
	List<OrderUiListingDTO> findByvendorvendor_IdByDateRange(long vendorId, Date startDate, Date endDate,
			Pageable pagable);


	@Query("Select new com.anaadihsoft.common.DTO.OrderUiListingDTO(u) FROM UserOrderProducts u where  u.vendor.id =?1  AND "+
			"LOWER(u.product.variantName) LIKE %?2% OR LOWER(u.product.variantCode) LIKE %?2%  " +  
			"OR LOWER(u.product.product.productName) LIKE %?2%")
	List<OrderUiListingDTO> getAllOrderForVendorBySearchString(long vendorId, String searchString, Pageable pagable);


	@Query("Select count(u) FROM UserOrderProducts u where  u.vendor.id =?1  AND "+
			"LOWER(u.product.variantName) LIKE %?2% OR LOWER(u.product.variantCode) LIKE %?2%  " +  
			"OR LOWER(u.product.product.productName) LIKE %?2%")
	long getVendorOrderCountBySearchString(long vendorId, String searchString);
	

	@Query("Select count(u) FROM UserOrderProducts u where  u.vendor.id =?1  and u.status=?2 AND "+
			"LOWER(u.product.variantName) LIKE %?3% OR LOWER(u.product.variantCode) LIKE %?3%  " +  
			"OR LOWER(u.product.product.productName) LIKE %?3%")
	long getVendorOrderCountBySearchStringAndStatus(long vendorId,String status, String searchString);

	@Query("Select distinct uop from UserOrderProducts uop where uop.vendor.id =?1 and  uop.createdDate between ?2 and ?3 order by uop.userOrder.orderPlacedDate desc")
	long getVendorOrderCountByDateRange(long vendorId, Date startDate, Date endDate);
	
	@Query("Select  count(uop) from UserOrderProducts uop where uop.vendor.id =?1 and uop.status=?2  and  uop.createdDate between ?3 and ?4 order by uop.userOrder.orderPlacedDate desc")
	long getVendorOrderCountByDateRangeAndStatus(long vendorId, String status,Date startDate, Date endDate);

	
	@Query("Select new com.anaadihsoft.common.DTO.OrderUiListingDTO(uop) from UserOrderProducts uop where uop.vendor.id =?1 and uop.status=?2 and uop.createdDate between ?3 and ?4 ")
	List<OrderUiListingDTO> findByvendorvendor_IdAndStatusCreatedDateBetween(long vendorId, String status,
			Date startDate, Date endDate, Pageable pagable);

	@Query("Select new com.anaadihsoft.common.DTO.OrderUiListingDTO(u) FROM UserOrderProducts u where  u.vendor.id =?1  and u.status=?2 AND "+
			"LOWER(u.product.variantName) LIKE %?3% OR LOWER(u.product.variantCode) LIKE %?3%  " +  
			"OR LOWER(u.product.product.productName) LIKE %?3%")
	List<OrderUiListingDTO> getAllVendorOrderAndStatusAndBySearchString(long vendorId, String status,
			String searchString, Pageable pagable);

	@Query("Select new com.anaadihsoft.common.DTO.OrderUiListingDTO(uop) from UserOrderProducts uop  order by uop.userOrder.orderPlacedDate desc")
	List<OrderUiListingDTO> findAllOrderForSuperAdmin();

}
