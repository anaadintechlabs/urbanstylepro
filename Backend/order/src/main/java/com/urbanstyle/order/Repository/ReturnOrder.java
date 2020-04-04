package com.urbanstyle.order.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.DTO.ReturnUiListDTO;
import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.ReturnManagement;
import com.anaadihsoft.common.master.UserOrder;


@Repository
public interface ReturnOrder extends PagingAndSortingRepository<ReturnManagement, Long>{

	ReturnManagement findByOrderId(long l);

	@Query(" FROM ReturnManagement order by createdDate")
	List<ReturnManagement> getLastReturns(Pageable pagable);

	@Query("Select new com.anaadihsoft.common.DTO.ReturnUiListDTO(uop) from ReturnManagement uop where uop.user.id =?1 order by uop.createdDate desc")
	List<ReturnUiListDTO> findByUserId(long userId, Pageable pagable);

	@Query("Select new com.anaadihsoft.common.DTO.ReturnUiListDTO(uop) from ReturnManagement uop where uop.orderProduct.vendor.id =?1 order by uop.createdDate desc")
	List<ReturnUiListDTO> findByOrderProductVendorId(long vendorId, Pageable pagable);

	@Query("Select new com.anaadihsoft.common.DTO.ReturnUiListDTO(uop) from ReturnManagement uop where uop.orderProduct.vendor.id =?1 and  uop.createdDate between ?2 and ?3")
	List<ReturnUiListDTO> findByOrderProductVendorIdAndDateRange(long vendorId, Date startDate, Date endDate,
			Pageable pagable);
	
	@Query("Select new com.anaadihsoft.common.DTO.ReturnUiListDTO(u) FROM ReturnManagement u where  u.orderProduct.vendor.id =?1  AND "+
			"LOWER(u.orderProduct.product.variantName) LIKE %?2% OR LOWER(u.orderProduct.product.variantCode) LIKE %?2%  " )
	List<ReturnUiListDTO> getAllReturnOfVendorBySearchString(long vendorId, String searchString, Pageable pagable);

	
	long countByUserId(long userId);

	@Query("Select count(uop) from ReturnManagement uop where uop.orderProduct.vendor.id =?1 ")
	long countByOrderProductVendorId(long vendorId);

	@Query("Select new com.anaadihsoft.common.DTO.ReturnUiListDTO(uop) from ReturnManagement uop where uop.user.id =?1 and  uop.createdDate between ?2 and ?3")
	List<ReturnUiListDTO> findByUserIdAndCreatedDateBetween(long userId, Date startDate, Date endDate,
			Pageable pagable);

	@Query("Select new com.anaadihsoft.common.DTO.ReturnUiListDTO(u) FROM ReturnManagement u where  u.user.id =?1  AND "+
			"LOWER(u.orderProduct.product.variantName) LIKE %?2% OR LOWER(u.orderProduct.product.variantCode) LIKE %?2%  " )
	List<ReturnUiListDTO> getAllReturnBySearchString(long userId, String searchString, Pageable pagable);

	@Query("Select count(uop) from ReturnManagement uop where uop.user.id =?1 and  uop.createdDate between ?2 and ?3")
	long getCountAllReturnByDateRange(long userId, Date startDate, Date endDate);

	@Query("Select count(u) FROM ReturnManagement u where  u.user.id =?1  AND "+
			"LOWER(u.orderProduct.product.variantName) LIKE %?2% OR LOWER(u.orderProduct.product.variantCode) LIKE %?2%  " )
	long getAllUsersBySearchString(long userId, String searchString);

	@Query("Select count(uop) from ReturnManagement uop where uop.orderProduct.vendor.id =?1 and  uop.createdDate between ?2 and ?3")
	long countAllReturnByDateRange(long vendorId, Date startDate, Date endDate);

	@Query("Select count(u) FROM ReturnManagement u where  u.orderProduct.vendor.id =?1  AND "+
			"LOWER(u.orderProduct.product.variantName) LIKE %?2% OR LOWER(u.orderProduct.product.variantCode) LIKE %?2%  " )
	long getAllReturnCountBySearchString(long vendorId, String searchString);

	@Query("Select new com.anaadihsoft.common.DTO.ReturnUiListDTO(uop) from ReturnManagement uop where  uop.createdDate between ?1 and ?2")
	List<ReturnUiListDTO> getAllReturnsByDateRange(Date startDate, Date endDate, Pageable pagable);

	@Query("Select new com.anaadihsoft.common.DTO.ReturnUiListDTO(u) FROM ReturnManagement u where   "+
			"LOWER(u.orderProduct.product.variantName) LIKE %?1% OR LOWER(u.orderProduct.product.variantCode) LIKE %?1%  " )
	List<ReturnUiListDTO> getAllReturnForSuperadmiBySearchString(String searchString, Pageable pagable);

	@Query("Select new com.anaadihsoft.common.DTO.ReturnUiListDTO(uop) from ReturnManagement uop ")
	List<ReturnUiListDTO> findAllForSuperAdmin(Pageable pagable);

	long countByCreatedDateBetween(Date startDate, Date endDate);

	@Query("Select count(u) FROM ReturnManagement u where   "+
			"LOWER(u.orderProduct.product.variantName) LIKE %?1% OR LOWER(u.orderProduct.product.variantCode) LIKE %?1%  " )
	long getAllReturnCountBySearchString(String searchString);


	
}
