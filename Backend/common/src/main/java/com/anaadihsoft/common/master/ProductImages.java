package com.anaadihsoft.common.master;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProductImages {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long productImageId;
	
	
	private String fileName;
	
	private String fileType;
	
	private long size;
	
	private String productImageUrl;
	
	 @ManyToOne
	 @JoinColumn(name="productId", nullable=false,updatable=false)
	 private Product product;

	 
	public ProductImages(String fileName2, String generateFileUri, String contentType, long size2, Product oldProduct) {
		this.fileName=fileName2;
		this.productImageUrl=generateFileUri;
		this.fileType=contentType;
		this.size=size2;
		this.product=oldProduct;
	}

	public ProductImages()
	{
		
	}
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public long getProductImageId() {
		return productImageId;
	}

	public void setProductImageId(long productImageId) {
		this.productImageId = productImageId;
	}

	public String getProductImageUrl() {
		return productImageUrl;
	}

	public void setProductImageUrl(String productImageUrl) {
		this.productImageUrl = productImageUrl;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
	 
	 

}
