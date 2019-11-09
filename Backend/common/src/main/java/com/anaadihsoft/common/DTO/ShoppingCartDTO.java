package com.anaadihsoft.common.DTO;

import com.anaadihsoft.common.master.Product;
import com.anaadihsoft.common.master.User;

public class ShoppingCartDTO {

	private User user;
	
	private String cartType;
	
	private long quantity;
	
	private Product product;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCartType() {
		return cartType;
	}

	public void setCartType(String cartType) {
		this.cartType = cartType;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	
}
