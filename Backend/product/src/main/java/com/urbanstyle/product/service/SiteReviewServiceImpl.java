package com.urbanstyle.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.SiteReview;
import com.urbanstyle.product.repository.SiteReviewRepository;

@Service
public class SiteReviewServiceImpl implements SiteReviewService{

	private static final int ACTIVE =	1;
	private static final int INACTIVE =2;
	
	@Autowired
	private SiteReviewRepository siteReviewRepository; 
	
	
	@Override
	public SiteReview siteReviewSave(SiteReview siteReview) {
		return siteReviewRepository.save(siteReview);
	}

	@Override
	public List<SiteReview> getAllReviewsOfUser(Filter filter, long userId) {
		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
				filter.getSortingDirection() != null
				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter.getSortingField());
		return siteReviewRepository.findByUserIdAndStatus(userId,ACTIVE,pagable);
	}

	@Override
	public List<SiteReview> getAllReviewsForDashboard(Filter filter) {
		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
				filter.getSortingDirection() != null
				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter.getSortingField());
		return siteReviewRepository.findByStatus(ACTIVE,pagable);
	}

	@Override
	public boolean softDeleteSiteReview(long userId) {
		siteReviewRepository.changeStatusOfSite(userId,INACTIVE);
		return true;
	}

}
