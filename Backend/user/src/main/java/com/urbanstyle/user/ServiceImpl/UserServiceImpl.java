package com.urbanstyle.user.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.User;
import com.urbanstyle.user.Repository.AddressRepository;
import com.urbanstyle.user.Repository.BankRepository;
import com.urbanstyle.user.Repository.UserRepository;
import com.urbanstyle.user.Service.UserService;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AddressRepository addressRepository; 
	
	@Autowired
	private BankRepository  bankRepository;

	@Override
	public String getCurrentStatusOfUser(long userId) {
		boolean adressExist=addressRepository.existsByUserIdAndStatus(userId, 1) ;
		
		boolean bankExist=bankRepository.existsByUserIdAndStatus(userId, 1);
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
	
	@Override
	public List<User> getAllUsers(Filter filter, String userType) {
		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
				filter.getSortingDirection() != null
				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter.getSortingField());
		return userRepository.getAllUsers(pagable,userType);
	}


}
