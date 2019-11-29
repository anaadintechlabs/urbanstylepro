package com.urbanstyle.user.ServiceImpl;

import java.util.List;
import java.util.Optional;

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
	private static final int ACTIVE=1;

	
	@Override
	public BankDetails saveorUpdate(BankDetails bankDetails) {
		bankDetails.setUser(userRepository.getOne(bankDetails.getUser().getId()));
		return bankRepository.save(bankDetails);
	}
	
	public List<BankDetails> getBankDetails(long userId) {
		List<BankDetails> bankModel = bankRepository.findByUserIdAndStatus(userId,ACTIVE);
		return bankModel;
	}

	public void deleteBankDetails(long bankId,int status) {
		bankRepository.changeStatusOfBankDetails(bankId,status);
		//bankRepository.deleteById(bankId);
	}

	@Override
	public BankDetails getBankDetailsById(long bankId) {
	Optional<BankDetails> bankOpt=bankRepository.findById(bankId);
	return bankOpt.isPresent()?bankOpt.get():null;
	}

	@Override
	public boolean checkDuplicateIFSC(String ifscCode) {
		return bankRepository.existsByIfscCode(ifscCode);
	}
}
