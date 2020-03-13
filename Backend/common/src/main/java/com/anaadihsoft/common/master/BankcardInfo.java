package com.anaadihsoft.common.master;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class BankcardInfo {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private User user;
	
    @Column(nullable = false)
	private String cardNumber;
	
    @Column(nullable = false)
	private String year;
	
    @Column(nullable = false)
	private String month;
	
    @Column(nullable = false)
	private String  typeOfCard;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getTypeOfCard() {
		return typeOfCard;
	}

	public void setTypeOfCard(String typeOfCard) {
		this.typeOfCard = typeOfCard;
	}
}
