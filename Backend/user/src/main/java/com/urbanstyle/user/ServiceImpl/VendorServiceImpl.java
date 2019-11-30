package com.urbanstyle.user.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.anaadihsoft.common.DTO.VendorSignupDTO;
import com.anaadihsoft.common.external.AuthProvider;
import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.Address;
import com.anaadihsoft.common.master.BankDetails;
import com.anaadihsoft.common.master.User;
import com.urbanstyle.user.Repository.AddressRepository;
import com.urbanstyle.user.Repository.BankRepository;
import com.urbanstyle.user.Repository.UserRepository;
import com.urbanstyle.user.Service.VendorService;

@Service
public class VendorServiceImpl implements VendorService{

	@Autowired
	private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private BankRepository bankRepository; 
    
	@Override
	public List<User> getAllVendors(Filter filter) {
		//return userRepository.findByUserType("VENDOR");
		return null;
	}
	@Override
	public boolean vendorSignUpIntegrated(VendorSignupDTO vendorSignupDTO) {
		
	    // Creating user's account
        User user = new User();
        user.setName(vendorSignupDTO.getSignUp().getName());
        user.setEmail(vendorSignupDTO.getSignUp().getEmail());
        user.setPassword(vendorSignupDTO.getSignUp().getPassword());
        user.setProvider(AuthProvider.local);
        user.setUserType(vendorSignupDTO.getSignUp().getUserType());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User result = userRepository.save(user);
        Address address = vendorSignupDTO.getAddress();
        address.setUser(result);
        addressRepository.save(address);
        
        BankDetails bankDetails = vendorSignupDTO.getBankDetails();
        bankDetails.setUser(result);
        bankRepository.save(bankDetails);
        
        return true;
	}

}
