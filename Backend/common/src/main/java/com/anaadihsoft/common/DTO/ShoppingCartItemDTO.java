package com.anaadihsoft.common.DTO;

import com.anaadihsoft.common.master.Product;
import com.anaadihsoft.common.master.ProductVariant;

public class ShoppingCartItemDTO {
	
	private long id;

	private double cost;
	
	private int quantity;
	
	private ProductVariant productVariant;

	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public ProductVariant getProductVariant() {
		return productVariant;
	}

	public void setProductVariant(ProductVariant productVariant) {
		this.productVariant = productVariant;
	}

	
	
}
