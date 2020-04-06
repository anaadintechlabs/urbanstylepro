package com.urbanstyle.order.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.DTO.PaymentWalletTransactionDTO;
import com.anaadihsoft.common.master.PaymentWalletTransaction;

@Repository
public interface PaymentWalletTransactionRepo extends PagingAndSortingRepository<PaymentWalletTransaction, Long> {

	PaymentWalletTransaction findByRecieverAndOrderId(String string, long id);

	@Query("FROM PaymentWalletTransaction where orderProds.id =?1 and status =?2 order by amount")
	List<PaymentWalletTransaction> getTransactionofOrder(long orderProdId,String status);

	@Query("Select new com.anaadihsoft.common.DTO.PaymentWalletTransactionDTO(uop) from PaymentWalletTransaction uop where orderProds.id =?1  order by amount")
	List<PaymentWalletTransactionDTO> getTransactionofOrderWithDTO(long orderProdId,String status);
	
	@Query("Select new com.anaadihsoft.common.DTO.PaymentWalletTransactionDTO(uop) from PaymentWalletTransaction uop where uop.sender.id =?1 and uop.createdDate between ?2 and ?3")
	List<PaymentWalletTransactionDTO> getAllOutgoingTransactionByDateRange(String userIdString, Date startDate,
			Date endDate, Pageable pagable);
	
	@Query("Select new com.anaadihsoft.common.DTO.PaymentWalletTransactionDTO(uop) from PaymentWalletTransaction uop where uop.sender.id =?1 ")
	List<PaymentWalletTransactionDTO> getAllOutgoingTransaction(String userIdString, Pageable pagable);

	@Query("Select new com.anaadihsoft.common.DTO.PaymentWalletTransactionDTO(uop) from PaymentWalletTransaction uop where uop.reciever =?1 and uop.createdDate between ?2 and ?3")
	List<PaymentWalletTransactionDTO> getAllIncomingTransactionByDateRange(String userIdString, Date startDate,
			Date endDate, Pageable pagable);

	@Query("Select new com.anaadihsoft.common.DTO.PaymentWalletTransactionDTO(uop) from PaymentWalletTransaction uop where uop.reciever =?1 ")
	List<PaymentWalletTransactionDTO> getAllIncomingTransaction(String userIdString, Pageable pagable);

}
