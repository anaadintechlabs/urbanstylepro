package com.anaadihsoft.inventory.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.anaadihsoft.common.master.ProductInventory;
import com.anaadihsoft.inventory.service.InventorySevice;
import com.urbanstyle.inventory.util.CommonResponseSender;


@Controller
public class InvntoryController {

	@Autowired
	private InventorySevice inventoryService;
	
	/*
	 * save data in inventory
	 */
	
	@RequestMapping(value="/saveInventory",method= {RequestMethod.GET,RequestMethod.POST})
	public Map<String,Object> saveInventory(@RequestBody ProductInventory prodInventory,HttpServletRequest request,HttpServletResponse response){
		
		final HashMap<String, Object> map = new HashMap<>();
		map.put("inventoryList", inventoryService.saveInventory(prodInventory));
		return CommonResponseSender.getRecordSuccessResponse(map, response);
		
	}
	
	@RequestMapping(value="/getByVendor",method= {RequestMethod.GET,RequestMethod.POST})
	public Map<String,Object> getByVendor(@RequestParam(value="vendorId") long vendorId,HttpServletRequest request,HttpServletResponse response){
		
		final HashMap<String, Object> map = new HashMap<>();
		map.put("inventoryList", inventoryService.getByVendor(vendorId));
		return CommonResponseSender.getRecordSuccessResponse(map, response);
		
	}
	
	@RequestMapping(value="/getByProductVarient",method= {RequestMethod.GET,RequestMethod.POST})
	public Map<String,Object> getByProductVarient(@RequestParam(value="productVarId") long productVarId,HttpServletRequest request,HttpServletResponse response){
		
		final HashMap<String, Object> map = new HashMap<>();
		map.put("inventoryList", inventoryService.getByProductVarient(productVarId));
		return CommonResponseSender.getRecordSuccessResponse(map, response);
		
	}
}
