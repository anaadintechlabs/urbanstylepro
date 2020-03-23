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

	@Override
	public Map<String, String> findAllAttributeListWithAttributeKey(long prodVarId) {
		Map<String,String> allData = new HashMap<>();
		List<Object[]> attributeDetails=prodAttributeRepo.findAllAttributeListWithAttributeKey(prodVarId);
		for(Object[] attr : attributeDetails) {
			allData.put(attr[0].toString(), attr[1].toString());
		}
		
		return allData;
	}

	@Override
	public List<Object[]> getAllAttributeDetailsOfFullProduct(long productId) {
		return prodAttributeRepo.findAllAttributeListWithAttributeKeyAndValue(productId);
	}

}
