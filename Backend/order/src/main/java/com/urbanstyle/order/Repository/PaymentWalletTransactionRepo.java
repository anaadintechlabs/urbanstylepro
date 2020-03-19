package com.urbanstyle.order.Repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.master.PaymentWalletTransaction;

@Repository
public interface PaymentWalletTransactionRepo extends PagingAndSortingRepository<PaymentWalletTransaction, Long> {

	PaymentWalletTransaction findByRecieverAndOrderId(String string, long id);

}
