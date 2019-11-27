package com.anaadihsoft.common.DTO;

public class FilterDTO {

	private int limit ;
	private String offset;
	private String searchString;
	private String priceRange;
	private String rating;
	private String sortingByDate;
	private String SortingByPrice;
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public String getOffset() {
		return offset;
	}
	public void setOffset(String offset) {
		this.offset = offset;
	}
	public String getSearchString() {
		return searchString;
	}
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	public String getPriceRange() {
		return priceRange;
	}
	public void setPriceRange(String priceRange) {
		this.priceRange = priceRange;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getSortingByDate() {
		return sortingByDate;
	}
	public void setSortingByDate(String sortingByDate) {
		this.sortingByDate = sortingByDate;
	}
	public String getSortingByPrice() {
		return SortingByPrice;
	}
	public void setSortingByPrice(String sortingByPrice) {
		SortingByPrice = sortingByPrice;
	}
	
}
