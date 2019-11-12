package com.urbanstyle.product.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.anaadihsoft.common.master.Wishlist;

public interface WishlistRepository extends PagingAndSortingRepository<Wishlist, Long>{

	List<Wishlist> findByUserIdAndStatus(long userId, String active, Pageable pagable);


	@Query(value="update Wishlist set status =?2 where user.id = ?1 ")
	void changeStatusOfWishList(long userId, String inactive);

}
