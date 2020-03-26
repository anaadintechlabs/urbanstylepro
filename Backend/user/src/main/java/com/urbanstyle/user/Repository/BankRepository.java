package com.urbanstyle.user.Repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.master.BankDetails;

@Repository
public interface BankRepository extends PagingAndSortingRepository<BankDetails, Long>{


	List<BankDetails> findByUserId(long userId);

	List<BankDetails> findByUserIdAndStatus(long userId,int active);
	
	boolean existsByUserIdAndStatus(long userId, int i);

	@Query(value="Update BankDetails bd set bd.status=?2 where bd.id=?1")
	@Modifying
	@Transactional
	void changeStatusOfBankDetails(long bankId, int status);

	boolean existsByIfscCode(String ifscCode);

	long countByUserIdAndStatus(long userId, int active);

}
