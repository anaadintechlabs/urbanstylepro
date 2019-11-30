package com.urbanstyle.user.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anaadihsoft.common.DTO.VendorSignupDTO;
import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.User;

@Service
public interface VendorService {

	List<User> getAllVendors(Filter filter);

	boolean vendorSignUpIntegrated(VendorSignupDTO vendorSignupDTO);

}
