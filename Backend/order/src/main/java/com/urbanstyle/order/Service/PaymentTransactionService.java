package com.urbanstyle.order.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anaadihsoft.common.DTO.PaymentWalletTransactionDTO;
import com.anaadihsoft.common.external.Filter;

@Service
public interface PaymentTransactionService {

	List<PaymentWalletTransactionDTO> getAllOutgoingTransactions(long userId, Filter filter);

	List<PaymentWalletTransactionDTO> getAllIncomingTransactions(long userId, Filter filter);

	long getAllIncomingTransactionsCount(long userId, Filter filter);

	long getAllOutgoingTransactionsCount(long userId, Filter filter);

}
