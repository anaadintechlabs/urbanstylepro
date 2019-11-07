package com.urbanstyle.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anaadihsoft.common.master.CategoryVariation;

@Service
public interface CategoryVariationService {

	List<CategoryVariation> getAllVariationOfCategory(int categoryId);

	CategoryVariation getVariationDetail(long variationId);

}
