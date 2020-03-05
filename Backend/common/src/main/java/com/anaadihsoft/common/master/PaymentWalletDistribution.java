package com.anaadihsoft.common.master;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class PaymentWalletDistribution {

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private long perc;
	
	private String source;

	private String distributionTo;
	
	public String getDistributionTo() {
		return distributionTo;
	}

	public void setDistributionTo(String distributionTo) {
		this.distributionTo = distributionTo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPerc() {
		return perc;
	}

	public void setPerc(long perc) {
		this.perc = perc;
	}

	public String getTypeOfuser() {
		return source;
	}

	public void setTypeOfuser(String source) {
		this.source = source;
	}
	
	
}
