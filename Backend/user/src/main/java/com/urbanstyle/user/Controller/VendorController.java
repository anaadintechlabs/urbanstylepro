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

import com.anaadihsoft.common.external.Filter;
import com.urbanstyle.user.Service.VendorService;
import com.urbanstyle.user.util.CommonResponseSender;


@RestController
@RequestMapping("/api/vendor")
@CrossOrigin(origins = {"*","http://localhost:4200"}, maxAge = 3600)
public class VendorController {
	
	@Autowired
	private VendorService vendorService;
	
	@RequestMapping(value="/getAllVendors",method=RequestMethod.POST)
	public Map<String,Object> getAllVendors(@RequestBody Filter filter,
			HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();
		
		map.put("vendorList", vendorService.getAllVendors(filter));
	
		return CommonResponseSender.createdSuccessResponse(map, response);
		
	}

}
