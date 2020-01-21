package com.anaadihsoft.inventory.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anaadihsoft.common.master.ProductInventory;

@Service
public interface InventorySevice {

	ProductInventory saveInventory(ProductInventory prodInventory);

	List<ProductInventory> getByVendor(long vendorId);

	List<ProductInventory> getByProductVarient(long productVarId);

}
