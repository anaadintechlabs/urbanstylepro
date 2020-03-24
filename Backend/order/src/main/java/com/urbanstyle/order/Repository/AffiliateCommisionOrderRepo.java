package com.urbanstyle.order.Repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.master.AffiliateCommisionOrder;

@Repository
public interface AffiliateCommisionOrderRepo extends PagingAndSortingRepository<AffiliateCommisionOrder, Long> {

	AffiliateCommisionOrder findByOrderProdId(long orderProdId);

	List<AffiliateCommisionOrder> findByAffiliateIdId(long affiliateId);

}
