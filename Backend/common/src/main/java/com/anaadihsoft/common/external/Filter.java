package com.anaadihsoft.common.external;

public class Filter {

	private int limit;
	
	private int offset;
	
	private String sortingDirection;
	
	private String sortingField;
	
	private String dateRange;
	
	private String searchString;

	//limit 
	//offset
	//searchString
	//priceRange
	//rating
	//sortingByDate
	//SortingByPrice
	
	
	
	public int getLimit() {
		return limit;
	}

	public String getDateRange() {
		return dateRange;
	}

	public void setDateRange(String dateRange) {
		this.dateRange = dateRange;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public String getSortingDirection() {
		return sortingDirection;
	}

	public void setSortingDirection(String sortingDirection) {
		this.sortingDirection = sortingDirection;
	}

	public String getSortingField() {
		return sortingField;
	}

	public void setSortingField(String sortinigField) {
		this.sortingField = sortinigField;
	}
	
	
	
}
