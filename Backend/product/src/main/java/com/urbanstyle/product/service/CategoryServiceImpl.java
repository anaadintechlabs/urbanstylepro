package com.urbanstyle.product.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.AttributeMaster;
import com.anaadihsoft.common.master.Category;
import com.urbanstyle.product.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	final Logger log=LoggerFactory.getLogger(CategoryServiceImpl.class);
	
	private static final int ACTIVE =	1;
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
			return categoryRepository.save(category);
	}
	

	@Override
	public List<Category> getAllCategories() {
		return (List<Category>) categoryRepository.findAll();
	}




	@Override
	public List<AttributeMaster> fetchallAttributeDtail(long categoryId) {
		return categoryRepository.fetchallAttributeDtail(categoryId);
	}
}
