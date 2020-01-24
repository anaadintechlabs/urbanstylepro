package com.urbanstyle.product.DAO;

import java.util.List;

import com.anaadihsoft.common.DTO.FilterDTO;
import com.anaadihsoft.common.DTO.InventorySearchDTO;
import com.anaadihsoft.common.DTO.ProductVariantDTO;
import com.anaadihsoft.common.master.ProductVariant;

public interface ProductVarientDAO {

	List<ProductVariant> findInProductVarient(FilterDTO filterDTO);

	List<ProductVariant> searchInventory(InventorySearchDTO inventorySearchDTO);

}
