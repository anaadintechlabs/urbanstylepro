package com.urbanstyle.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.AttributeMaster;
import com.anaadihsoft.common.master.Category;

@Service
public interface CategoryService {

	List<Category> getAllParentCategories(Filter filter);

	List<Category> getAllSubCategoriesOfCategory(long categoryId);
	
	List<Category> getAllCategories(Filter filter);

	Category saveCategory(Category category, MultipartFile[] files);

	List<AttributeMaster> fetchallAttributeDtail(long categoryId);

	void changeStatusOfCategory(long categoryId, int status);

}
