package com.urbanstyle.user.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.anaadihsoft.common.DTO.PasswordDTO;
import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.User;
import com.urbanstyle.user.Repository.AddressRepository;
import com.urbanstyle.user.Repository.BankRepository;
import com.urbanstyle.user.Repository.UserRepository;
import com.urbanstyle.user.Service.UserService;
import com.urbanstyle.user.util.CustomException;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AddressRepository addressRepository; 
	
	@Autowired
	private BankRepository  bankRepository;
	
	 @Autowired
	    private PasswordEncoder passwordEncoder;

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
		return userRepository.getAllUsers(userType,pagable);
	}

	@Override
	public User updateUser(User user) {
		Optional<User> useropt=userRepository.findById(user.getId());
		if(useropt.isPresent()) {
			User prevuser = useropt.get();
			prevuser.setBio(user.getBio());
			prevuser.setPhoneNumber(user.getPhoneNumber());
			prevuser.setName(user.getName());
			return userRepository.save(prevuser);
		}
		return user;
	}
	
	@Override
	public Object changeUserPassword(long userId, PasswordDTO passwordDTO) throws CustomException {
		Optional<User> useropt=userRepository.findById(userId);
		if(useropt.isPresent()) {
			User prevuser = useropt.get();
			//Check for Previous password , check for new pasword
			Boolean match = passwordEncoder.matches(passwordDTO.getOldPassword(), prevuser.getPassword());
			System.out.println("Match "+match);
			if(match) {
			prevuser.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
			return userRepository.save(prevuser);
			}
			else
			{
				throw new CustomException("Entered Password is wrong");
			}
			
		}
		return null;
	}


}
