package com.urbanstyle.product.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.anaadihsoft.common.DTO.FilterDTO;
import com.anaadihsoft.common.DTO.HomePageFilterDTO;
import com.anaadihsoft.common.DTO.InventorySearchDTO;
import com.anaadihsoft.common.DTO.ProductDTO;
import com.anaadihsoft.common.DTO.ProductVariantDTO;
import com.anaadihsoft.common.DTO.SingleProductDTO;
import com.anaadihsoft.common.DTO.VariantPriceUpdateDTO;
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

	List<ProductVariantDTO> getALLProductVarientDTO(int i, long prodId);
	
	List<VariantPriceUpdateDTO>  updateVarientDTO(List<VariantPriceUpdateDTO> allVarientDTO);

	
	List<ProductVariant> searchInventory(InventorySearchDTO inventorySearchDTO);

	SingleProductDTO getSingleProductDetail(long prodVarId);


	List<ProductVariantDTO> getSingleProductVarientDTOList(int i, long prodId, long productVariantId);

	HomePageFilterDTO applyHomePageFilter(String searchString);


		List<ProductVariantDTO> applySideBarFilter(String searchString, HashMap<Long, List<String>> filterData);

		ProductVariant findByProdVarId(long l);

 
}
