package com.urbanstyle.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anaadihsoft.common.master.CategoryAttributeMapping;
import com.urbanstyle.product.repository.CategoryVariationRepository;

@Service
public class CategoryVariationServiceImpl implements CategoryVariationService{
	private static final String ACTIVE ="ACTIVE";
	
	@Autowired
	private CategoryVariationRepository categoryVariationRepository; 
	@Override
	public List<CategoryAttributeMapping> getAllVariationOfCategory(int categoryId) {
		return categoryVariationRepository.findByStatusAndCategoryCategoryId(ACTIVE,categoryId);
	}
	
	@Override
	public CategoryAttributeMapping getVariationDetail(long variationId) {
		Optional<CategoryAttributeMapping> optCategoryVariation= categoryVariationRepository.findById(variationId);
		return optCategoryVariation.isPresent()?optCategoryVariation.get():null;
	}

}
