package com.urbanstyle.user.Controller;

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

import com.anaadihsoft.common.master.BankDetails;
import com.urbanstyle.user.Service.BankService;
import com.urbanstyle.user.util.CommonResponseSender;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="*")
public class BankController {
	
	
	@Autowired
	public BankService bankService;
	
	/**
	 * 
	 * @param bankDetails
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/saveBankDetails",method=RequestMethod.POST)
	public  Map<String,Object> saveBankDetails(@RequestBody BankDetails bankDetails,
			HttpServletRequest request,HttpServletResponse response) {
		final HashMap<String, Object> map = new HashMap<>();
		try {
			
			boolean duplicate =false;
			if(bankDetails.getId()==0)
			{
				duplicate= bankService.checkDuplicateIFSC(bankDetails.getIfscCode());	
			}
			if(duplicate)
			{
				map.put("duplicate",duplicate);

			}
			else
			{
				
			map.put("bankDetails",bankService.saveorUpdate(bankDetails));
			}
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
	@RequestMapping(value="/getBankDetailsByUser",method=RequestMethod.POST)
	public  Map<String,Object> getBankDetailsByUser(@RequestParam (value = "userId",required=true) long userId,HttpServletRequest request,HttpServletResponse response) {
		final HashMap<String, Object> map = new HashMap<>();
		try {
			List<BankDetails> bankDetails = bankService.getBankDetails(userId);
			map.put("bankDetails", bankDetails);
			map.put("count", bankService.getCountByUser(userId));
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
	@RequestMapping(value="/deleteBankDetails",method=RequestMethod.DELETE)
	public  Map<String,Object> deleteBankDetails(@RequestParam (value = "bankId",required=true) String bankId,
			@RequestParam (value = "userId",required=true) long userId,
			@RequestParam (value = "status",required=true) int status,
			HttpServletRequest request,HttpServletResponse response) {
		final HashMap<String, Object> map = new HashMap<>();
		try {
			bankService.deleteBankDetails(Long.parseLong(bankId),status);
			List<BankDetails> bankDetails = bankService.getBankDetails(userId);
			map.put("bankDetails", bankDetails);
			 return CommonResponseSender.recordDeleteSuccessResponse(map, response);
	      }catch(Exception e) {
	    	  return CommonResponseSender.errorResponse(map, response);
	      }
	}
	
	
	@RequestMapping(value="/getBankDetailsById",method=RequestMethod.GET)
	public  Map<String,Object> getBankDetailsById(@RequestParam("bankId")long bankId,
			HttpServletRequest request,HttpServletResponse response) {
		final HashMap<String, Object> map = new HashMap<>();
		try {
			map.put("bankDetails",bankService.getBankDetailsById(bankId));
			return CommonResponseSender.createdSuccessResponse(map, response);
	      }catch(Exception e) { 
	    	  return CommonResponseSender.errorResponse(map, response);
	      }
	}
	
}

