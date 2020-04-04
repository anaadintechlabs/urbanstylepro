package com.urbanstyle.user.Controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anaadihsoft.common.DTO.VendorSignupDTO;
import com.anaadihsoft.common.external.Filter;
import com.urbanstyle.user.Payload.SignUpRequest;
import com.urbanstyle.user.Repository.BankRepository;
import com.urbanstyle.user.Repository.UserRepository;
import com.urbanstyle.user.Service.VendorService;
import com.urbanstyle.user.util.CommonResponseSender;


@RestController
@RequestMapping("/api/vendor")
@CrossOrigin(origins = {"*","http://localhost:4200"}, maxAge = 3600)
public class VendorController {
	
	@Autowired
	private VendorService vendorService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BankRepository bankRepository;
	
	/**
	 * NOT IN USE
	 * @param filter
	 * @param request
	 * @param response
	 * @return get all vendors 
	 */
	@RequestMapping(value="/getAllVendors",method=RequestMethod.POST)
	public Map<String,Object> getAllVendors(@RequestBody Filter filter,
			HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();
		
		map.put("vendorList", vendorService.getAllVendors(filter));
	
		return CommonResponseSender.createdSuccessResponse(map, response);
		
	}
	
	
	/**
	 * 
	 * @param email
	 * @param ifscCode
	 * @param request
	 * @param response
	 * @return checkk duplicate codde
	 */
	@RequestMapping(value="/checkDuplicateEmailAndIfscCode",method=RequestMethod.GET)
	public Map<String,Object> checkDuplicateEmailAndIfscCode(
			@RequestParam(value="email") String email,
			@RequestParam(value="ifscCode") String ifscCode,
			HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();
		map.put("duplicateUsername", userRepository.existsByEmail(email));
		map.put("duplicateIfscCode", bankRepository.existsByIfscCode(ifscCode));
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	
	/**
	 * sign up integrated
	 * @param vendorSignupDTO
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/vendorSignUpIntegrated",method=RequestMethod.POST)
	public Map<String,Object> vendorSignUpIntegrated(
			@RequestBody VendorSignupDTO vendorSignupDTO,
			HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();
		boolean duplicateUsername=userRepository.existsByEmail(vendorSignupDTO.getSignUp().getEmail());
		boolean duplicateIfscCode= bankRepository.existsByIfscCode(vendorSignupDTO.getBankDetails().getIfscCode());
		if(duplicateUsername || duplicateIfscCode)
		{
			map.put("duplicateUsername", duplicateUsername);
			map.put("duplicateIfscCode", duplicateIfscCode);
		}
		else
		{
		map.put("signup", vendorService.vendorSignUpIntegrated(vendorSignupDTO));
		}
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	
	
	

}
