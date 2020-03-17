package com.urbanstyle.product.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.master.ProductMeta;

@Repository
public interface ProductMetaRepository extends PagingAndSortingRepository<ProductMeta,Long>{

	List<ProductMeta> findByProductProductId(long prodId);

	@Query(value="delete from ProductMeta where product.productId = :productId")
	@Modifying
	@Transactional
	void deleteAllMeta(long productId);

	List<ProductMeta> findByProductVariantProductVariantId(long productVariantId);

	List<ProductMeta> findByProductProductIdAndProductVariantNull(long prodId);

}
