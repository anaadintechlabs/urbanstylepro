package com.urbanstyle.order.Repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.master.ReturnManagement;
import com.anaadihsoft.common.master.UserOrder;


@Repository
public interface ReturnOrder extends PagingAndSortingRepository<ReturnManagement, Long>{

	ReturnManagement findByOrderId(long l);

}
