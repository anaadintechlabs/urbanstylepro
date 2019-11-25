package com.anaadihsoft.common.DTO;

import java.util.List;

import com.anaadihsoft.common.master.Product;
import com.anaadihsoft.common.master.User;

public class ShoppingCartDTO {

	private User user;
	
	private List<ShoppingCartItemDTO> shoppingCartItemDTO; 

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<ShoppingCartItemDTO> getShoppingCartProductDTO() {
		return shoppingCartItemDTO;
	}

	public void setShoppingCartProductDTO(List<ShoppingCartItemDTO> shoppingCartItemDTO) {
		this.shoppingCartItemDTO = shoppingCartItemDTO;
	}
	
		



	
}
