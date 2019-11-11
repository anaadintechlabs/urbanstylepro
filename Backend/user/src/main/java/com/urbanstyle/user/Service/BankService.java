package com.urbanstyle.user.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anaadihsoft.common.master.BankDetails;

@Service
public interface BankService {

	public BankDetails saveorUpdate(BankDetails bankDetails);
	
	public List<BankDetails> getBankDetails(long userId);
	
	public void deleteBankDetails(long bankId, String status);

}
