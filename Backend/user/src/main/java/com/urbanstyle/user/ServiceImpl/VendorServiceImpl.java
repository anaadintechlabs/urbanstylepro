package com.urbanstyle.user.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.User;
import com.urbanstyle.user.Repository.UserRepository;
import com.urbanstyle.user.Service.VendorService;

public class VendorServiceImpl implements VendorService{

	@Autowired
	private UserRepository userRepository;
	@Override
	public List<User> getAllVendors(Filter filter) {
		return userRepository.findByUserType("VENDOR");
	}

}
