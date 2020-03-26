package com.urbanstyle.user.Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.multipart.MultipartFile;

import com.anaadihsoft.common.master.Address;
import com.anaadihsoft.common.master.BankDetails;
import com.anaadihsoft.common.master.Category;
import com.anaadihsoft.common.master.City;
import com.anaadihsoft.common.master.Country;
import com.anaadihsoft.common.master.State;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanstyle.user.Service.AddressService;
import com.urbanstyle.user.Service.BankService;
import com.urbanstyle.user.util.CommonResponseSender;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"*","http://localhost:4200"})
public class AddressController {

	@Autowired
	public AddressService addressService;
	
	/**
	 * 
	 * @param bankDetails
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/saveAddressDetails",method=RequestMethod.POST)
	public  Map<String,Object> saveAddressDetails(@RequestBody Address address,
			HttpServletRequest request,HttpServletResponse response) {
		final HashMap<String, Object> map = new HashMap<>();
		try {
			map.put("addressDetails",addressService.saveorUpdate(address));
			return CommonResponseSender.createdSuccessResponse(map, response);
	      }catch(Exception e) { 
	    	  return CommonResponseSender.errorResponse(map, response);
	      }
	}
	
	@RequestMapping(value="/getAddressById",method=RequestMethod.GET)
	public  Map<String,Object> getAddressById(@RequestParam("addressId")long addressId,
			HttpServletRequest request,HttpServletResponse response) {
		final HashMap<String, Object> map = new HashMap<>();
		try {
			map.put("address",addressService.getAddressById(addressId));
			return CommonResponseSender.createdSuccessResponse(map, response);
	      }catch(Exception e) { 
	    	  return CommonResponseSender.errorResponse(map, response);
	      }
	}
	
	/**
	 * 
	 * @param userId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getAddressDetailsByUser",method=RequestMethod.GET)
	public  Map<String,Object> getAddressDetailsByUser(@RequestParam (value = "userId",required=true) long userId,HttpServletRequest request,HttpServletResponse response) {
		final HashMap<String, Object> map = new HashMap<>();
		try {
			System.out.println("address details");
			List<Address> addressDetails = addressService.getAddressDetails(userId);
			map.put("addressDetails", addressDetails);
			map.put("count", addressService.getCountByUserId(userId));
			return CommonResponseSender.createdSuccessResponse(map, response);
	      }catch(Exception e) {
	    	  return CommonResponseSender.errorResponse(map, response);
	      }
	}
	
	/**
	 * 
	 * @param bankId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/deleteAddressDetails",method=RequestMethod.DELETE)
	public  Map<String,Object> deleteAddressDetails(@RequestParam (value = "addressId",required=true) String addressId,
			@RequestParam (value = "userId",required=true) long userId,
			@RequestParam (value = "status",required=true) int status,
			HttpServletRequest request,HttpServletResponse response) {
		final HashMap<String, Object> map = new HashMap<>();
		try {
			System.out.println("addressId"+addressId+"userId"+userId+"status"+status);
			addressService.deleteAddressDetails(Long.parseLong(addressId),status);
			List<Address> addressDetails = addressService.getAddressDetails(userId);
			map.put("addressDetails", addressDetails);
			 return CommonResponseSender.recordDeleteSuccessResponse(map, response);
	      }catch(Exception e) {
	    	  return CommonResponseSender.errorResponse(map, response);
	      }
	}
	
	
	/**
	 * 
	 * @param userId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getAllCountries",method=RequestMethod.POST)
	public  Map<String,Object> getAllCountries(HttpServletRequest request,HttpServletResponse response) {
		final HashMap<String, Object> map = new HashMap<>();
		try {
			List<Country> countryList = addressService.getAllCountries();
			map.put("countryList", countryList);
			return CommonResponseSender.createdSuccessResponse(map, response);
	      }catch(Exception e) {
	    	  return CommonResponseSender.errorResponse(map, response);
	      }
	}
	
	
	/**
	 * 
	 * @param userId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getAllStatesOfCountry",method=RequestMethod.POST)
	public  Map<String,Object> getAllStatesOfCountry(@RequestParam (value = "countryId",required=true) long countryId,
			HttpServletRequest request,HttpServletResponse response) {
		final HashMap<String, Object> map = new HashMap<>();
		try {
			List<State> stateList = addressService.getAllStatesOfCountry(countryId);
			map.put("stateList", stateList);
			return CommonResponseSender.createdSuccessResponse(map, response);
	      }catch(Exception e) {
	    	  return CommonResponseSender.errorResponse(map, response);
	      }
	}
	
	/**
	 * 
	 * @param userId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getAllCityOfState",method=RequestMethod.POST)
	public  Map<String,Object> getAllCityOfState(@RequestParam (value = "stateId",required=true) long stateId,
			HttpServletRequest request,HttpServletResponse response) {
		final HashMap<String, Object> map = new HashMap<>();
		try {
			List<City> cityList = addressService.getAllCityOfStates(stateId);
			map.put("cityList", cityList);
			return CommonResponseSender.createdSuccessResponse(map, response);
	      }catch(Exception e) {
	    	  return CommonResponseSender.errorResponse(map, response);
	      }
	}
}
