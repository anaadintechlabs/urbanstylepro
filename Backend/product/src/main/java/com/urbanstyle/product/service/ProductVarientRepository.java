package com.urbanstyle.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.master.ProductVariant;

@Repository
public interface ProductVarientRepository extends PagingAndSortingRepository<ProductVariant,Long>{
	
	List<ProductVariant> findByFetauredProduct(boolean featured);

	List<ProductVariant> findProductProductId(long prodId);

	ProductVariant findByDealOfTheDay(boolean b);

	@Query("update ProductVariant set dealOfTheDay = ?1")
	boolean setDealOftheDay(boolean deal1);

//	@Query("update ProductVariant  pv set dealOfTheDay = ?1 where pv.product.productId = ?2")
//	void setDealofSelectedProd(boolean b,long prodId);

	List<ProductVariant> findByStatus(int i);

	Optional<ProductVariant> findByProductVariantId(long prodVarId);

}
