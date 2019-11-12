package com.anaadihsoft.common.DTO;

import java.util.List;

import com.anaadihsoft.common.master.Product;
import com.anaadihsoft.common.master.User;

public class ShoppingCartDTO {

	private User user;
	
	private List<Double> cost;
	
	private List<Long> quantity;
	
	private List<Product> product;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
		

	public List<Double> getCost() {
		return cost;
	}

	public void setCost(List<Double> cost) {
		this.cost = cost;
	}

	public List<Long> getQuantity() {
		return quantity;
	}

	public void setQuantity(List<Long> quantity) {
		this.quantity = quantity;
	}

	public List<Product> getProduct() {
		return product;
	}

	public void setProduct(List<Product> product) {
		this.product = product;
	}


	
}
