package com.anaadihsoft.user.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anaadihsoft.common.master.BankDetails;
import com.anaadihsoft.user.Service.BankService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="*")
public class BankController {
	
	
	@Autowired
	public BankService bankService;
	
	@RequestMapping(value="/saveBankDetails",method=RequestMethod.POST)
	public  Map<String,Object> saveBankDetails(@RequestBody BankDetails bankDetails) {
		final HashMap<String, Object> map = new HashMap<>();
		try {
			bankService.saveorUpdate(bankDetails);
			map.put("RESPONSE", "SUCCESS");
			map.put("bankDetails", bankDetails);
			return map;
	      }catch(Exception e) {
	    	  map.put("RESPONSE", "ERROR");
	    	  return map;  
	      }
	}
	
	@RequestMapping(value="/getBankDetailsByUser",method=RequestMethod.POST)
	public  Map<String,Object> getBankDetailsByUser(@RequestParam (value = "userId",required=true) String userId) {
		final HashMap<String, Object> map = new HashMap<>();
		try {
			List<BankDetails> bankDetails = bankService.getBankDetails(userId);
			map.put("RESPONSE", "SUCCESS");
			map.put("bankDetails", bankDetails);
			return map;
	      }catch(Exception e) {
	    	  map.put("RESPONSE", "ERROR");
	    	  return map;  
	      }
	}
	
	@RequestMapping(value="/deleteBankDetails",method=RequestMethod.POST)
	public  Map<String,Object> deleteBankDetails(@RequestParam (value = "bankId",required=true) String bankId) {
		final HashMap<String, Object> map = new HashMap<>();
		try {
			bankService.deleteBankDetails(Long.parseLong(bankId));
			map.put("RESPONSE", "SUCCESS");
			return map;
	      }catch(Exception e) {
	    	  map.put("RESPONSE", "ERROR");
	    	  return map;  
	      }
	}
	
}
