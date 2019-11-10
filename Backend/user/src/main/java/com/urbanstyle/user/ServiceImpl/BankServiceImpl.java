package com.urbanstyle.user.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anaadihsoft.common.master.BankDetails;
import com.urbanstyle.user.Repository.BankRepository;
import com.urbanstyle.user.Repository.UserRepository;
import com.urbanstyle.user.Service.BankService;

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
