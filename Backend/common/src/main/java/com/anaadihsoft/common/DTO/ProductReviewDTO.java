package com.anaadihsoft.common.DTO;

import java.util.Date;

import com.anaadihsoft.common.master.ProductReview;

public class ProductReviewDTO {
	
	private String review;
	
	private String rating;
	
	private String title;
	
	
	private int status;
	
	private Date createdDate;
	
	private String createdBy;
	
	public ProductReviewDTO() {
		
	}
	
	public ProductReviewDTO(ProductReview pr) {
		this.review = pr.getReview();
		this.rating = pr.getRating();
		this.title = pr.getTitle();
		this.status = pr.getStatus();
		this.createdBy = pr.getCreatedBy();
		this.createdDate = pr.getCreatedDate();
	}
	
	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
}