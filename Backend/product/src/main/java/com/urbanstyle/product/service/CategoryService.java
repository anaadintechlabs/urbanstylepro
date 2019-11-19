package com.urbanstyle.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.AttributeMaster;
import com.anaadihsoft.common.master.Category;

@Service
public interface CategoryService {

	List<Category> getAllParentCategories(Filter filter);

	List<Category> getAllSubCategoriesOfCategory(long categoryId);
	
	List<Category> getAllCategories();

	Category saveCategory(Category category);

	List<AttributeMaster> fetchallAttributeDtail(long categoryId);

}
