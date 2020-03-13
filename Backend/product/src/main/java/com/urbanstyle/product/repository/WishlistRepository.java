package com.urbanstyle.product.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.anaadihsoft.common.master.Wishlist;

public interface WishlistRepository extends PagingAndSortingRepository<Wishlist, Long>{

	List<Wishlist> findByUserIdAndStatus(long userId, int active, Pageable pagable);


	@Query(value="update Wishlist wl set wl.status =?1 where wl.id =?2 ")
	@Modifying
	@Transactional
	void changeStatusOfWishList(int inactive,long id);


	Wishlist findByUserIdAndProductVariantProductVariantId(Long id, long productVariantId);
	
	

}
