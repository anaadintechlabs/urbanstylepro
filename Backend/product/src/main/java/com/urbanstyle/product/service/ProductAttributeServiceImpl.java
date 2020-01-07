package com.urbanstyle.product.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anaadihsoft.common.master.ProductAttributeDetails;

@Service
public class ProductAttributeServiceImpl implements ProductAttributeService{

	
	@Autowired
	private ProductAttributeRepository prodAttributeRepo;
	
	@Override
	public Map<Long,String> findAllAttributeList(long productVariantId) {
		Map<Long,String> allData = new HashMap<Long,String>();
		List<ProductAttributeDetails> allAttributeDetails =  prodAttributeRepo.findByProductVariantProductVariantId(productVariantId);
		for(ProductAttributeDetails attr : allAttributeDetails) {
			allData.put(attr.getAttributeMasterId(), attr.getAttributeValue());
		}
		return allData;
	}

}
