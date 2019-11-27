package com.urbanstyle.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anaadihsoft.common.DTO.FilterDTO;
import com.anaadihsoft.common.DTO.ProductVariantDTO;
import com.anaadihsoft.common.master.ProductVariant;

@Service
public interface ProductVarientService {

	List<ProductVariant> getAllFeaturedProducts();

	boolean setFeaturedProduct(long prodId);

	ProductVariant getDealOftheDay();

	boolean setDealOftheDay(long prodId);

	List<ProductVariant> getAllVarients(int Status);

	ProductVariant addVarientToProduct(ProductVariantDTO productVarientDTO);

	boolean deleteVarientToProduct(long prodId, long prodVarId);

	List<ProductVariant> searchProducts(FilterDTO filterDTO);
}
