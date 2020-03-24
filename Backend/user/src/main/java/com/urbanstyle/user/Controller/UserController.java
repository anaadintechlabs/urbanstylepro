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
import org.springframework.web.multipart.MultipartFile;

import com.anaadihsoft.common.DTO.PasswordDTO;
import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.User;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbanstyle.user.Service.FileUploadService;
import com.urbanstyle.user.Service.UserService;
import com.urbanstyle.user.util.CommonResponseSender;
import com.urbanstyle.user.util.CustomException;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = {"*","http://localhost:4200"}, maxAge = 3600)
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private FileUploadService fileUploadService; 
//	
	
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
		//map.put("status", userService.getCurrentStatusOfUser(userId));
		return CommonResponseSender.createdSuccessResponse(map, response);
		
	}
	
	
	@RequestMapping(value="/getAllUserByUserType",method=RequestMethod.POST)
	public Map<String,Object> getAllUserByUserType(@RequestBody Filter filter,
			@RequestParam(value="userType") String userType,
			HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();
		
		map.put("userList", userService.getAllUsers(filter,userType));
		
		return CommonResponseSender.createdSuccessResponse(map, response);
		
	}
	
	
	@RequestMapping(value="/updateUser",method=RequestMethod.PUT)
	public Map<String,Object> updateUser(@RequestParam(value="file",required=false) MultipartFile files,@RequestParam(value="userString",required=false) String userString,
			HttpServletRequest request,HttpServletResponse response) throws JsonMappingException, JsonProcessingException
	{
		final HashMap<String, Object> map = new HashMap<>();
		ObjectMapper objMapper= new ObjectMapper();
		TypeReference<User> mapType= new TypeReference<User>() {
		};
		User user= objMapper.readValue(userString, mapType);
		user=userService.updateUser(user);
		//photo upload pending
		if(user!=null)
		{
			fileUploadService.saveImageofUser(files,user);
		}
		map.put("user", user);
		
		
		return CommonResponseSender.createdSuccessResponse(map, response);
		
	}
	
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public Map<String, Object> changePassword(
			@RequestBody PasswordDTO passwordDTO,
			@RequestParam(value = "userId", required = true) long userId,
			 HttpServletResponse response,
			HttpServletRequest request) throws CustomException {
		final Map<String, Object> map = new HashMap<>();
		
			map.put("updatedUser", userService.changeUserPassword(userId, passwordDTO));
		
		return CommonResponseSender.updatedSuccessResponse(map, response);
	}
}
