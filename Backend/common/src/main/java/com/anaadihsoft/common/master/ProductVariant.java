package com.anaadihsoft.common.master;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProductVariant {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long productVariantId;
	
	private long variantId;
	
	private long categoryId;
	
	 @ManyToOne
	 @JoinColumn(name="productId", nullable=false,updatable=false)
	 private Product product;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public long getProductVariantId() {
		return productVariantId;
	}

	public void setProductVariantId(long productVariantId) {
		this.productVariantId = productVariantId;
	}

	public long getVariantId() {
		return variantId;
	}

	public void setVariantId(long variantId) {
		this.variantId = variantId;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}
}
