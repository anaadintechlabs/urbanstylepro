package com.urbanstyle.order.Contoller;

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
import com.urbanstyle.order.Service.ReturnService;
import com.urbanstyle.order.util.CommonResponseSender;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"*","http://localhost:4200"})
public class ReturnController {

	@Autowired
	private ReturnService returnService;
	
	@RequestMapping(value= {"/getReturnByUser"},method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> getReturnByUser(HttpServletRequest request,
			@RequestBody Filter filter,HttpServletResponse response,@RequestParam(value="userId",required=true) String userId ){
		Map<String, Object> resultMap = new HashMap<String,Object>();
		//try {
			resultMap.put("returnList",returnService.getReturnByUser(Long.parseLong(userId),filter));
			resultMap.put("RESPONSE", "SUCCESS");
		//}catch(Exception e) {
		//	resultMap.put("RESPONSE", "ERROR");	
		//}
		return CommonResponseSender.getRecordSuccessResponse(resultMap, response);

		//return resultMap;
	}
	
}
