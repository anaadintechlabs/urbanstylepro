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
public class ProductVideos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long productVideoId;

	@ManyToOne
	@JoinColumn(name="productId", nullable=false)
	private Product product;
	
	private String productVideoUrl;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public long getProductVideoId() {
		return productVideoId;
	}

	public void setProductVideoId(long productVideoId) {
		this.productVideoId = productVideoId;
	}

	public String getProductVideoUrl() {
		return productVideoUrl;
	}

	public void setProductVideoUrl(String productVideoUrl) {
		this.productVideoUrl = productVideoUrl;
	}
	
	
}
