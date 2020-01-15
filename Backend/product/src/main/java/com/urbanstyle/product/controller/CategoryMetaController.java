package com.urbanstyle.product.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.urbanstyle.product.util.CommonResponseSender;
@RestController
@RequestMapping("/meta")
@CrossOrigin(origins="*")
public class CategoryMetaController {

//	@RequestMapping(value="/getAllMetaOfCategory",method=RequestMethod.GET)
//	public Map<String,Object> getAllMetaOfCategory(
//			@RequestParam(value="categoryId") long categoryId,
//			HttpServletRequest request,HttpServletResponse response)
//	{
//		final HashMap<String, Object> map = new HashMap<>();
//		map.put("metaList", categoryVariationService.getAllVariationOfCategory(categoryId));
//		return CommonResponseSender.createdSuccessResponse(map, response);
//		
//	}
}
