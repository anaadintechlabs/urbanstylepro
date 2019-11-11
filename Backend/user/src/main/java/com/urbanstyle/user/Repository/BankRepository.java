package com.urbanstyle.user.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.master.BankDetails;

@Repository
public interface BankRepository extends PagingAndSortingRepository<BankDetails, Long>{


	List<BankDetails> findByUserId(long userId);

	List<BankDetails> findByUserIdAndStatus(long userId,String status);
	
	boolean existsByUserIdAndStatus(long userId, String active);

	@Query(value="Update BankDetails bd set bd.status=?2 where bd.id=?1")
	void changeStatusOfBankDetails(long bankId, String status);

}
