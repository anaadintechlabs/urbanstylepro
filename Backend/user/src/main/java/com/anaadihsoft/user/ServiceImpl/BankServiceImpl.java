package com.anaadihsoft.user.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anaadihsoft.common.master.BankDetails;
import com.anaadihsoft.user.Repository.BankRepository;
import com.anaadihsoft.user.Service.BankService;

@Service
public class BankServiceImpl implements BankService{

	
	@Autowired
	public BankRepository bankRepository;
	
	@Override
	public void saveorUpdate(BankDetails bankDetails) {
		bankRepository.save(bankDetails);
	}
	
	public List<BankDetails> getBankDetails(String userId) {
		List<BankDetails> bankModel = bankRepository.findByuser(userId);
		return bankModel;
	}

	public void deleteBankDetails(long bankId) {
		bankRepository.deleteById(bankId);
	}
}
