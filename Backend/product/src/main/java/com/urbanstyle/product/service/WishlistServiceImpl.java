package com.urbanstyle.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.anaadih.aclassdeal.Model.WishlistModel;
import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.Wishlist;
import com.urbanstyle.product.repository.WishlistRepository;

@Service
public class WishlistServiceImpl implements WishlistService{

	private static final int ACTIVE =	1;
	private static final int INACTIVE =	2;
	@Autowired
	private WishlistRepository wishlistRepository;
	
	@Override
	public Wishlist addProductToWishlist(Wishlist wishList) {
//		WishlistModel previousAdded=wishlistRepository.findByUserIdAndProdIdProdId(userId,Integer.parseInt(prodId));
//		if(previousAdded!=null) {
//			map.put("msg", "Already Added to wishlist");
//			map.put("type", "Warning!");
//			return map;
//		}
//		
		return wishlistRepository.save(wishList);
	}
	
	@Override
	public List<Wishlist> getAllWishListOfUser(Filter filter, long userId) {
		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
				filter.getSortingDirection() != null
				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter.getSortingField());
		return wishlistRepository.findByUserIdAndStatus(userId,ACTIVE,pagable);
	}
	
	@Override
	public boolean softDeleteWishList(long userId) {
		wishlistRepository.changeStatusOfWishList(userId,INACTIVE);
		return true;
	}

}
