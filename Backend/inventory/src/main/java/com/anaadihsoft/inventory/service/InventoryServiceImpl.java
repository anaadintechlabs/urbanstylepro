package com.anaadihsoft.inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.anaadihsoft.common.master.ProductInventory;
import com.anaadihsoft.inventory.repository.ProductInventoryRepo;

public class InventoryServiceImpl implements InventorySevice {

	@Autowired
	private ProductInventoryRepo productInventoryRepo;
	
	@Override
	public ProductInventory saveInventory(ProductInventory prodInventory) {
		return productInventoryRepo.save(prodInventory);
	}

	@Override
	public List<ProductInventory> getByVendor(long vendorId) {
		return productInventoryRepo.findByUserId(vendorId);
	}

	@Override
	public List<ProductInventory> getByProductVarient(long productVarId) {
		return productInventoryRepo.findByProductVariantProductVariantId(productVarId);
	}

}
