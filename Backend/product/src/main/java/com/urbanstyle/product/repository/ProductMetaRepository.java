package com.urbanstyle.product.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.master.ProductMeta;

@Repository
public interface ProductMetaRepository extends PagingAndSortingRepository<ProductMeta,Long>{

	List<ProductMeta> findByProductProductId(long prodId);

}
