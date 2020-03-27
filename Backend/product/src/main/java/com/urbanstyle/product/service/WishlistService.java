package com.urbanstyle.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.Wishlist;

@Service
public interface WishlistService {

	boolean addProductToWishlist(Wishlist wishList);

	List<Wishlist> getAllWishListOfUser(Filter filter, long userId);

	boolean softDeleteWishList(long userId, long id);

	long getAllWishListCountOfUser(long userId);

}
