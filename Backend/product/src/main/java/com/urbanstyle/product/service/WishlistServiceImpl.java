package com.urbanstyle.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.Wishlist;
import com.urbanstyle.product.repository.WishlistRepository;

@Service
public class WishlistServiceImpl implements WishlistService{

	private static final int ACTIVE =	1;
	private static final int INACTIVE =	0;
	@Autowired
	private WishlistRepository wishlistRepository;
	
	@Override
	public boolean addProductToWishlist(Wishlist wishList) {
		Wishlist previousAdded=wishlistRepository.findByUserIdAndProductVariantProductVariantId(wishList.getUser().getId(),wishList.getProductVariant().getProductVariantId());
		if(previousAdded!=null) {		
			return true;
		}
		else
		{
		 wishlistRepository.save(wishList);
		 return false;
		}
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
	public boolean softDeleteWishList(long userId,long id) {
		wishlistRepository.changeStatusOfWishList(INACTIVE,id);
		
		return true;
	}

	@Override
	public long getAllWishListCountOfUser(long userId) {
		return wishlistRepository.countByUserIdAndStatus(userId,ACTIVE);

	}

}
