package com.urbanstyle.order.ServiceImpl;

import java.util.Date;
import java.util.List;

import javax.xml.ws.ServiceMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.anaadihsoft.common.DTO.PaymentWalletTransactionDTO;
import com.anaadihsoft.common.external.Filter;
import com.urbanstyle.order.Repository.PaymentWalletTransactionRepo;
import com.urbanstyle.order.Service.PaymentTransactionService;

@Service
public class PaymentTransactionServiceImpl implements PaymentTransactionService {

	@Autowired
	private PaymentWalletTransactionRepo paymentWalletTransactionRepo; 
	
	@Override
	public List<PaymentWalletTransactionDTO> getAllOutgoingTransactions(long userId, Filter filter) {
	
		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
				filter.getSortingDirection() != null
				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter.getSortingField());
		
		//String userIdString=userId+"";
		
		if(filter.getDateRange()!=null && !filter.getDateRange().isEmpty())
		{
			String[] dates=filter.getDateRange().split(",");
			Date startDate= new Date(Long.parseLong(dates[0]));
			Date endDate = new Date(Long.parseLong(dates[1]));
			return paymentWalletTransactionRepo.getAllOutgoingTransactionByDateRange(userId,startDate,endDate,pagable);
		}
		
		return paymentWalletTransactionRepo.getAllOutgoingTransaction(userId,pagable);
	}

	@Override
	public List<PaymentWalletTransactionDTO> getAllIncomingTransactions(long userId, Filter filter) {
		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
				filter.getSortingDirection() != null
				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter.getSortingField());
		
		String userIdString=userId+"";
		
		if(filter.getDateRange()!=null && !filter.getDateRange().isEmpty())
		{
			String[] dates=filter.getDateRange().split(",");
			Date startDate= new Date(Long.parseLong(dates[0]));
			Date endDate = new Date(Long.parseLong(dates[1]));
			return paymentWalletTransactionRepo.getAllIncomingTransactionByDateRange(userIdString,startDate,endDate,pagable);
		}
		
		return paymentWalletTransactionRepo.getAllIncomingTransaction(userIdString,pagable);
	}

	@Override
	public long getAllIncomingTransactionsCount(long userId, Filter filter) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getAllOutgoingTransactionsCount(long userId, Filter filter) {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
