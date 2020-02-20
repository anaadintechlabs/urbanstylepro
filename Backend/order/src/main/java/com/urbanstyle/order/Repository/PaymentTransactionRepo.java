package com.urbanstyle.order.Repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.master.PaymentTransaction;
import com.anaadihsoft.common.master.UserOrder;

@Repository
public interface PaymentTransactionRepo extends PagingAndSortingRepository<PaymentTransaction, Long> {

}
