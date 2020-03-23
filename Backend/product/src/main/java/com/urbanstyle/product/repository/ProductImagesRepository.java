package com.urbanstyle.product.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.anaadihsoft.common.master.ProductImages;

public interface ProductImagesRepository extends PagingAndSortingRepository<ProductImages,Long>  {

	@Query(value="delete from ProductImages where product.productId = :productId")
	@Modifying
	@Transactional
	void deleteAllImage(long productId);

	@Query(value="select pi.productImageUrl from ProductImages pi where pi.product.productId = :prodId and pi.productVariant=null")
	List<String> findUrlByProduct(long prodId);
	
	@Query(value="select pi.productImageUrl from ProductImages pi where pi.productVariant.productVariantId = :productVariantId")
	List<String> findUrlByProductForVariant(long productVariantId);

}
