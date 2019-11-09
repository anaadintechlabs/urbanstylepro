package com.anaadihsoft.common.master;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ShoppingCartType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	
	@ManyToOne
	private ShoppingCart shoppingCart;
	
	private long cartCount;//no idea how it will be used
	
	private String cartType;//WishList,Shopping Cart

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public long getCartCount() {
		return cartCount;
	}

	public void setCartCount(long cartCount) {
		this.cartCount = cartCount;
	}

	public String getCartType() {
		return cartType;
	}

	public void setCartType(String cartType) {
		this.cartType = cartType;
	}
	
	
	
}
