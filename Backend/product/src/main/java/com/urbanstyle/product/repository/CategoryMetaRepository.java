package com.urbanstyle.product.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.anaadihsoft.common.master.CategoryMeta;

public interface CategoryMetaRepository extends PagingAndSortingRepository<CategoryMeta,Long>{

	List<CategoryMeta> findByCategoryCategoryId(long categoryId);

}
