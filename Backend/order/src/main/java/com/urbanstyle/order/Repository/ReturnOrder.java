package com.urbanstyle.order.Repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.master.ReturnManagement;


@Repository
public interface ReturnOrder extends PagingAndSortingRepository<ReturnManagement, Long>{

}
