package com.anaadihsoft.common.master;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String addressOne;
	
	private String addressTwo;
	
	@ManyToOne
	private User user;
	
	@ManyToOne 
	private Country country;
	
	@ManyToOne
	private State state;
	
	@ManyToOne
	private City cite;
	
	private String zip;
	
	private boolean primaryAddress;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAddressOne() {
		return addressOne;
	}

	public void setAddressOne(String addressOne) {
		this.addressOne = addressOne;
	}

	public String getAddressTwo() {
		return addressTwo;
	}

	public void setAddressTwo(String addressTwo) {
		this.addressTwo = addressTwo;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public City getCite() {
		return cite;
	}

	public void setCite(City cite) {
		this.cite = cite;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public boolean isPrimaryAddress() {
		return primaryAddress;
	}

	public void setPrimaryAddress(boolean primaryAddress) {
		this.primaryAddress = primaryAddress;
	}
	
	
	
}
