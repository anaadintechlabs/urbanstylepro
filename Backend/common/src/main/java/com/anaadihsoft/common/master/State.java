package com.anaadihsoft.common.master;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class State {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String stateName;

	private String stateCode;
	
	@ManyToOne
	@JoinColumn(name = "country_id")
	private Country country;
	
}
