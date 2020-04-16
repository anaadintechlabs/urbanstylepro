package com.urbanstyle.order.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.anaadihsoft.common.DTO.AffiliateComissionDTO;
import com.anaadihsoft.common.DTO.OrderUiListingDTO;
import com.anaadihsoft.common.DTO.ReturnUiListDTO;
import com.anaadihsoft.common.external.Filter;
import com.urbanstyle.order.Repository.AffiliateCommisionOrderRepo;
import com.urbanstyle.order.Service.AffiliateService;

@Service
public class AffiliateServiceImpl implements AffiliateService{

	
	@Autowired
	private AffiliateCommisionOrderRepo affiliateCommisionOrderRepo; 
	
	@Override
	public List<OrderUiListingDTO> getOrderProductByAffiliate(long affiliateId, Filter filter) {
		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
				filter.getSortingDirection() != null
				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter.getSortingField());
		
		return affiliateCommisionOrderRepo.getOrderForAffialite(affiliateId,pagable);
	}

	@Override
	public List<ReturnUiListDTO> getReturnByAffiliate(long affiliateId, Filter filter) {
		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
				filter.getSortingDirection() != null
				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter.getSortingField());
		return affiliateCommisionOrderRepo.getReturnByAffiliate(affiliateId,pagable);

	}

	@Override
	public List<OrderUiListingDTO> getOrderByAffiliateByStatus(long affiliateId,String status, Filter filter) {
		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
				filter.getSortingDirection() != null
				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter.getSortingField());
		
		return affiliateCommisionOrderRepo.getOrderForAffialiteAndStatus(affiliateId,status,pagable);
	}

	@Override
	public List<AffiliateComissionDTO> getTotalComissionGroupByProduct(long affiliateId, Filter filter) {
		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
				filter.getSortingDirection() != null
				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter.getSortingField());
		return affiliateCommisionOrderRepo.getTotalComissionGroupByProduct(affiliateId,pagable);

	}

	@Override
	public long getCountOrderProductByAffiliate(long parseLong, Filter filter) {
		// TODO Auto-generated method stub
		return 0;
	}

}
