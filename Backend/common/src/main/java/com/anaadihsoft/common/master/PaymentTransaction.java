package com.anaadihsoft.common.master;

import java.awt.CardLayout;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class PaymentTransaction {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long paymentId;
	
	private String paymentDesc;
	
	private double amount;
	
	private String custId;

	@ManyToOne
	@JoinColumn(name="cardId", nullable=false,updatable=false)
	private BankcardInfo card;
	
	private Date createdDate;
	
	private String createdBy;
	
	public String getPaymentDesc() {
		return paymentDesc;
	}

	public void setPaymentDesc(String paymentDesc) {
		this.paymentDesc = paymentDesc;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}



	public long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(long paymentId) {
		this.paymentId = paymentId;
	}

	public BankcardInfo getCard() {
		return card;
	}

	public void setCard(BankcardInfo card) {
		this.card = card;
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
