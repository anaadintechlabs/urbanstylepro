package com.urbanstyle.order.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.master.PaymentWalletTransaction;

@Repository
public interface PaymentWalletTransactionRepo extends PagingAndSortingRepository<PaymentWalletTransaction, Long> {

	PaymentWalletTransaction findByRecieverAndOrderId(String string, long id);

	@Query("FROM PaymentWalletTransaction where orderProds.id =?1 and status =?2 order by amount")
	List<PaymentWalletTransaction> getTransactionofOrder(long orderProdId,String status);

}
