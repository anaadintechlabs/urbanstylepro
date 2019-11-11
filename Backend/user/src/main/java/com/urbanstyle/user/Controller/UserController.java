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
import com.urbanstyle.user.Service.UserService;
import com.urbanstyle.user.util.CommonResponseSender;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = {"*","http://localhost:4200"}, maxAge = 3600)
public class UserController {

	@Autowired
	private UserService userService;
	
	
	/**
	 * 
	 * @param filter
	 * @param userId
	 * @param request
	 * @param response
	 * @return method for gettung all product for display
	 */
	@RequestMapping(value="/getCurrentStatusOfUser",method=RequestMethod.GET)
	public Map<String,Object> getCurrentStatusOfUser(
			@RequestParam(value="userId") long userId,
			HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();
		map.put("status", userService.getCurrentStatusOfUser(userId));
		return CommonResponseSender.createdSuccessResponse(map, response);
		
	}
}
