package com.urbanstyle.product.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.DTO.ProductVariantDTO;
import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.ProductVariant;

@Repository
public interface ProductVarientRepository extends PagingAndSortingRepository<ProductVariant,Long>{
	
	List<ProductVariant> findByFetauredProduct(boolean featured);

	List<ProductVariant> findByProductProductId(long prodId);

	ProductVariant findByDealOfTheDay(boolean b);

	@Query("update ProductVariant set dealOfTheDay = ?1")
	@Modifying
	@Transactional
	boolean setDealOftheDay(boolean deal1);

//	@Query("update ProductVariant  pv set dealOfTheDay = ?1 where pv.product.productId = ?2")
//	void setDealofSelectedProd(boolean b,long prodId);

	List<ProductVariant> findByStatus(int i);

	Optional<ProductVariant> findByProductVariantId(long prodVarId);

	List<ProductVariant> findByStatusAndProductProductId(int status, long prodId);

	List<ProductVariant> findByProductCategoryIdAndStatus(long catId, int i, Pageable pagable);

	List<ProductVariant> findByProductUserId( long userId, Pageable pagable);

	List<ProductVariant> findByStatusAndProductUserId(int status, long userId, Pageable pagable);

	@Query(value="update ProductVariant wl set wl.status =?2 where wl.productVariantId =?1 ")
	@Modifying
	@Transactional
	void changeStatusOfProduct(long productId, int status);

	@Query(value="delete from ProductVariant where product.productId = :productId")
	@Modifying
	@Transactional
	void deleteAllProductVarient(long productId);
	
	@Query(value="update ProductVariant wl set wl.displayPrice =?3 , wl.actualPrice =?2 where wl.productVariantId =?1 ")
	@Transactional
	void updateVarientDTO(long productVariantId, double d, double e);

	@Query(value="select * from  ProductVariant wl where  wl.categoryId =?2 , wl.productVariantId <>?1")
	List<ProductVariant> getRelatedProducts(long prodVarId, long categoryId);

}
