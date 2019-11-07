package com.urbanstyle.product.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.anaadihsoft.common.master.CategoryVariation;

public interface CategoryVariationRepository extends PagingAndSortingRepository<CategoryVariation,Long>{

	List<CategoryVariation> findByStatusAndCategoryCategoryId(String active, int categoryId);

}
