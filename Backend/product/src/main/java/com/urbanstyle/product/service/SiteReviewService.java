package com.urbanstyle.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.SiteReview;


@Service
public interface SiteReviewService {

	SiteReview siteReviewSave(SiteReview productReview);

	List<SiteReview> getAllReviewsOfUser(Filter filter, long userId);

	List<SiteReview> getAllReviewsForDashboard(Filter filter);

	boolean softDeleteSiteReview(long userId);
}
