package com.urbanstyle.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anaadihsoft.common.DTO.FilterDTO;
import com.anaadihsoft.common.DTO.ProductVariantDTO;
import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.ProductVariant;

@Service
public interface ProductVarientService {

	List<ProductVariant> getAllFeaturedProducts();

	boolean setFeaturedProduct(long prodId);

	ProductVariant getDealOftheDay();

	boolean setDealOftheDay(long prodId);

	List<ProductVariant> getAllVarients(int Status, long prodId);

	ProductVariant addVarientToProduct(ProductVariantDTO productVarientDTO);

	boolean deleteVarientToProduct(long prodId, long prodVarId);
	
	List<ProductVariant> searchProducts(FilterDTO filterDTO);
	
	List<ProductVariant> getAllProducts();

	List<ProductVariant> getAllProductOfCategory(long catId, Filter filter);

	List<ProductVariant>  getAllProductVariantOfUser(long userId, Filter filter);

	List<ProductVariant> getAllActiveOrInactiveProductVariantOfUser(long userId, Filter filter, int status);

	void changeStatusOfProduct(long productId, int status);

}
