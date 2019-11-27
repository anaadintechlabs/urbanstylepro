package com.urbanstyle.product.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.anaadihsoft.common.master.CategoryAttributeMapping;

public interface CategoryVariationRepository extends PagingAndSortingRepository<CategoryAttributeMapping,Long>{

	List<CategoryAttributeMapping> findByStatusAndCategoryCategoryId(int active, int categoryId);

}
