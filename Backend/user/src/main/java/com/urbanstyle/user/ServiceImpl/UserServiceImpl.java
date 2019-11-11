package com.urbanstyle.user.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;

import com.urbanstyle.user.Repository.AddressRepository;
import com.urbanstyle.user.Repository.BankRepository;
import com.urbanstyle.user.Repository.UserRepository;
import com.urbanstyle.user.Service.UserService;

public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AddressRepository addressRepository; 
	
	@Autowired
	private BankRepository  bankRepository;

	@Override
	public String getCurrentStatusOfUser(long userId) {
		boolean adressExist=addressRepository.existsByUserIdAndStatus(userId, "ACTIVE") ;
		
		boolean bankExist=bankRepository.existsByUserIdAndStatus(userId, "ACTIVE");
		if(adressExist &&  bankExist)
		{
			return "0";
		}
		else if(adressExist )
		{
			return "3";
		}
//		else if(bankExist )
//		{
//			return "3";
//		}
		else
		{
		return "2";
		}
		
		
	}

}
