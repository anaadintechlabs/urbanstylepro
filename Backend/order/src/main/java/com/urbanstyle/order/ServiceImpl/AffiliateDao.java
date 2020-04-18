package com.urbanstyle.order.ServiceImpl;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.anaadihsoft.common.DTO.AffiliateComissionDTO;
import com.anaadihsoft.common.DTO.OrderUiListingDTO;
import com.anaadihsoft.common.DTO.ReturnUiListDTO;
import com.anaadihsoft.common.external.Filter;

public interface AffiliateDao {

	List<OrderUiListingDTO> getOrderForAffialite(long affiliateId, Filter filter);

	List<ReturnUiListDTO> getReturnByAffiliate(long affiliateId, Filter filter);

	List<AffiliateComissionDTO> getTotalComissionGroupByProduct(long affiliateId, Filter filter);

	List<OrderUiListingDTO> getOrderForAffialiteAndStatus(long affiliateId, String status, Filter filter);

}
