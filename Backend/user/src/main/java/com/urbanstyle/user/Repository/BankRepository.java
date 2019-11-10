package com.urbanstyle.user.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.master.BankDetails;

@Repository
public interface BankRepository extends PagingAndSortingRepository<BankDetails, Long>{


	List<BankDetails> findByUserId(long userId);

}
