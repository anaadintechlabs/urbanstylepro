package com.urbanstyle.product.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.master.Category;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category,Long>{

	

	//List<Category> findByStatusAndParentCategoryNULL(String active, Pageable pagable);

	List<Category> findByStatusAndParentCategoryCategoryId(String active, long categoryId);

	boolean existsByCategoryCode(String categoryCode);

}
