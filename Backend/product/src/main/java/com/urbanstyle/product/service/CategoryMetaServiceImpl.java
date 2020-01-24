package com.urbanstyle.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anaadihsoft.common.master.CategoryMeta;
import com.urbanstyle.product.repository.CategoryMetaRepository;

@Service
public class CategoryMetaServiceImpl implements CategoryMetaService{

	@Autowired
	private CategoryMetaRepository categoryMetaRepository; 
	@Override
	public List<CategoryMeta> getAllMetaOfCategory(long categoryId) {
		return categoryMetaRepository.findByCategoryCategoryId(categoryId);
	}

}
