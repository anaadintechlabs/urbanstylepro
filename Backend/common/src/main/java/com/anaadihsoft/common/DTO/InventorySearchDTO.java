package com.anaadihsoft.common.DTO;

public class InventorySearchDTO {

	private String search;              ////           - sku title and desc
	private long grtPrice; 
	private long lessPrice;
	private long grtDate;
	private long lessDate;
	private String status;
	private String sortField;
	private String sortdir;
	private int limit ;
	private int offset;
	
	
	private InventorySearchDTO(String search,long grtPrice,long lessPrice,long grtDate,long lessDate,String status,String sortField,String sortdir,int limit,int offset) {
		
		this.search = search;
		this.grtPrice = grtPrice;
		this.lessPrice = lessPrice;
		this.grtDate = grtDate;
		this.lessDate = lessDate;
		this.status = status;
		this.sortField = sortField;
		this.sortdir = sortdir;
		this.limit = limit;
		this.offset = offset;
	}
	
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public long getGrtPrice() {
		return grtPrice;
	}
	public void setGrtPrice(long grtPrice) {
		this.grtPrice = grtPrice;
	}
	public long getLessPrice() {
		return lessPrice;
	}
	public void setLessPrice(long lessPrice) {
		this.lessPrice = lessPrice;
	}
	public long getGrtDate() {
		return grtDate;
	}
	public void setGrtDate(long grtDate) {
		this.grtDate = grtDate;
	}
	public long getLessDate() {
		return lessDate;
	}
	public void setLessDate(long lessDate) {
		this.lessDate = lessDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSortField() {
		return sortField;
	}
	public void setSortField(String sortField) {
		this.sortField = sortField;
	}
	public String getSortdir() {
		return sortdir;
	}
	public void setSortdir(String sortdir) {
		this.sortdir = sortdir;
	}
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
}
