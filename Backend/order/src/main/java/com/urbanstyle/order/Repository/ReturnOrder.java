package com.urbanstyle.order.Repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.ReturnManagement;
import com.anaadihsoft.common.master.UserOrder;


@Repository
public interface ReturnOrder extends PagingAndSortingRepository<ReturnManagement, Long>{

	ReturnManagement findByOrderId(long l);

	@Query(" FROM ReturnManagement order by createdDate")
	List<ReturnManagement> getLastReturns(Pageable pagable);

	List<ReturnManagement> findByUserId(long userId, Pageable pagable);

	List<ReturnManagement> findByOrderProductVendorId(long vendorId, Pageable pagable);

	long countByUserId(long userId);

	long countByOrderProductVendorId(long vendorId);
}
