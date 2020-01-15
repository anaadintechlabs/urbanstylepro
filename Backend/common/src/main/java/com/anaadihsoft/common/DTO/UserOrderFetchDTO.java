package com.anaadihsoft.common.DTO;

import java.util.List;

import com.anaadihsoft.common.master.Address;
import com.anaadihsoft.common.master.ProductVariant;
import com.anaadihsoft.common.master.UserOrder;
import com.anaadihsoft.common.master.UserOrderProducts;

public class UserOrderFetchDTO {
	
	private UserOrder userOrder;
	private List<UserOrderProducts> userOrderProductList;
	
	//Any further data like Offer, Additional Data can be added here
	public UserOrder getUserOrder() {
		return userOrder;
	}
	public void setUserOrder(UserOrder userOrder) {
		this.userOrder = userOrder;
	}
	public List<UserOrderProducts> getUserOrderProductList() {
		return userOrderProductList;
	}
	public void setUserOrderProductList(List<UserOrderProducts> userOrderProductList) {
		this.userOrderProductList = userOrderProductList;
	}

}