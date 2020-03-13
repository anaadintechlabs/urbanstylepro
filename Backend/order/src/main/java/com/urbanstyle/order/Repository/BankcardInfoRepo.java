package com.urbanstyle.order.Repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.master.BankcardInfo;

@Repository
public interface BankcardInfoRepo  extends PagingAndSortingRepository<BankcardInfo, Long> {

	Optional<BankcardInfo> findByCardNumber(String cardNumber);

}
