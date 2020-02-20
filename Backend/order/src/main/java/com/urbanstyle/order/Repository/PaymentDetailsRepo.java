package com.urbanstyle.order.Repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.master.PaymentDetails;
import com.anaadihsoft.common.master.PaymentTransaction;

@Repository
public interface PaymentDetailsRepo  extends PagingAndSortingRepository<PaymentDetails, Long> {

}
