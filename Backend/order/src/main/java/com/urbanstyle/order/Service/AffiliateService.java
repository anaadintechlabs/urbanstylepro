package com.urbanstyle.order.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anaadihsoft.common.DTO.AffiliateComissionDTO;
import com.anaadihsoft.common.DTO.OrderUiListingDTO;
import com.anaadihsoft.common.DTO.ReturnUiListDTO;
import com.anaadihsoft.common.external.Filter;

@Service
public interface AffiliateService {

	List<OrderUiListingDTO> getOrderProductByAffiliate(long parseLong, Filter filter);

	List<ReturnUiListDTO> getReturnByAffiliate(long parseLong, Filter filter);

	List<OrderUiListingDTO> getOrderByAffiliateByStatus(long parseLong, String status, Filter filter);

	List<AffiliateComissionDTO> getTotalComissionGroupByProduct(long parseLong, Filter filter);

}
