package com.urbanstyle.product.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.master.SiteReview;

@Repository
public interface SiteReviewRepository extends PagingAndSortingRepository<SiteReview,Long> {

	List<SiteReview> findByUserIdAndStatus(long userId, int active, Pageable pagable);

	List<SiteReview> findByStatus(int active, Pageable pagable);

	@Query(value="update SiteReview set status =?2 where user.id = ?1  ")
	void changeStatusOfSite(long userId, int inactive);

}
