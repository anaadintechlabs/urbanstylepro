package com.anaadihsoft.common.external;

public class Filter {

	private int limit;
	
	private int offset;
	
	private String sortingDirection;
	
	private String sortingField;

	public int getLimit() {
		return limit;
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
