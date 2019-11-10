package com.anaadihsoft.user.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anaadihsoft.common.master.BankDetails;
import com.anaadihsoft.user.Repository.BankRepository;
import com.anaadihsoft.user.Repository.UserRepository;
import com.anaadihsoft.user.Service.BankService;

@Service
public class BankServiceImpl implements BankService{

	
	@Autowired
	public BankRepository bankRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public BankDetails saveorUpdate(BankDetails bankDetails) {
		bankDetails.setUser(userRepository.getOne(bankDetails.getUser().getId()));
		return bankRepository.save(bankDetails);
	}
	
	public List<BankDetails> getBankDetails(long userId) {
		List<BankDetails> bankModel = bankRepository.findByUserId(userId);
		return bankModel;
	}

	public void deleteBankDetails(long bankId) {
		bankRepository.deleteById(bankId);
	}
}
