package com.anaadihsoft.common.DTO;

import java.util.List;

import com.anaadihsoft.common.master.Address;
import com.anaadihsoft.common.master.ProductVariant;
import com.anaadihsoft.common.master.UserOrder;
import com.anaadihsoft.common.master.UserOrderProducts;

public class UserOrderFetchDTO {
	
	private UserOrder userOrder;
	private UserOrderProducts userOrderProduct;
	private Address address;
	private List<ProductVariant> products;
	public UserOrder getUserOrder() {
		return userOrder;
	}
	public void setUserOrder(UserOrder userOrder) {
		this.userOrder = userOrder;
	}
	public UserOrderProducts getUserOrderProduct() {
		return userOrderProduct;
	}
	public void setUserOrderProduct(UserOrderProducts userOrderProduct) {
		this.userOrderProduct = userOrderProduct;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public List<ProductVariant> getProducts() {
		return products;
	}
	public void setProducts(List<ProductVariant> products) {
		this.products = products;
	}
}
