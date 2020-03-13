package com.urbanstyle.order.Repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.anaadihsoft.common.master.Wishlist;

public interface WishListRepository extends PagingAndSortingRepository<Wishlist, Long> {

	List<Wishlist> findByUserIdAndStatus(long userId, int i, Pageable pagable1);

}
