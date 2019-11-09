package com.anaadihsoft.user.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anaadihsoft.common.master.BankDetails;

@Service
public interface BankService {

	public void saveorUpdate(BankDetails bankDetails);
	
	public List<BankDetails> getBankDetails(String bankId);
	
	public void deleteBankDetails(long bankId);

}
