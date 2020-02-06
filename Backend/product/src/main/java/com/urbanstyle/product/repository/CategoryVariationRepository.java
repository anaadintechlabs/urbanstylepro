package com.urbanstyle.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.anaadihsoft.common.master.CategoryAttributeMapping;

public interface CategoryVariationRepository extends PagingAndSortingRepository<CategoryAttributeMapping,Long>{

	List<CategoryAttributeMapping> findByStatusAndCategoryCategoryId(int active, long categoryId);

	@Query("From CategoryAttributeMapping cam Where cam.category.categoryId in(?1)")
	@Modifying
	List<CategoryAttributeMapping> findAllAttribute(List<Long> listCat);

}
