package com.urbanstyle.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.Category;
import com.urbanstyle.product.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	
	private static final String ACTIVE ="ACTIVE";
	@Autowired
	private CategoryRepository categoryRepository;
	
	


	/**
	 * 
	 */
	@Override
	public List<Category> getAllParentCategories(Filter filter) {
		
		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
				filter.getSortingDirection() != null
				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter.getSortingField());
		//return null;
		return categoryRepository.findByStatusAndParentCategory(ACTIVE,null,pagable);
	}




	/**
	 * 
	 */
	@Override
	public List<Category> getAllSubCategoriesOfCategory(long categoryId) {
		return categoryRepository.findByStatusAndParentCategoryCategoryId(ACTIVE,categoryId);
	}




	@Override
	public Category saveCategory(Category category) {
		if(categoryRepository.existsByCategoryCode(category.getCategoryCode())) {
			System.out.println("Duplicate Record");
		}
		else {
			return categoryRepository.save(category);
		}
		
		return null;
	}
}
