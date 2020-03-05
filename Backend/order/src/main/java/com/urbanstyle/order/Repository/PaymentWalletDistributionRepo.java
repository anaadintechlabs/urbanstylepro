package com.urbanstyle.order.Repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.master.PaymentWalletDistribution;

@Repository
public interface PaymentWalletDistributionRepo extends PagingAndSortingRepository<PaymentWalletDistribution, Long>{

}
