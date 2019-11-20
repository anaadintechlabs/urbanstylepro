package com.urbanstyle.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anaadihsoft.common.master.CategoryAttributeMapping;

@Service
public interface CategoryVariationService {

	List<CategoryAttributeMapping> getAllVariationOfCategory(int categoryId);

	CategoryAttributeMapping getVariationDetail(long variationId);

}
