package com.anaadihsoft.common.DTO;

import com.anaadihsoft.common.master.Address;
import com.anaadihsoft.common.master.BankDetails;

public class VendorSignupDTO {

	private SignUpRequest signUp;

	private Address address;
	
	private BankDetails bankDetails;

	public SignUpRequest getSignUp() {
		return signUp;
	}

	public void setSignUp(SignUpRequest signUp) {
		this.signUp = signUp;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public BankDetails getBankDetails() {
		return bankDetails;
	}

	public void setBankDetails(BankDetails bankDetails) {
		this.bankDetails = bankDetails;
	}
	
	
}
