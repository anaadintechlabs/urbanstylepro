package com.anaadihsoft.common.DTO;

import com.anaadihsoft.common.master.Product;

public class ShoppingCartItemDTO {
	
	private long id;

	private double cost;
	
	private int quantity;
	
	private Product product;

	
	
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

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	
}
