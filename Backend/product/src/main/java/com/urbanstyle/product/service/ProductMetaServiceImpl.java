package com.urbanstyle.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anaadihsoft.common.master.ProductMeta;
import com.urbanstyle.product.repository.ProductMetaRepository;

@Service
public class ProductMetaServiceImpl implements ProductMetaService {

	@Autowired ProductMetaRepository productMetaRepository; 
	
	@Override
	public List<ProductMeta> findAllMetaInfo(long prodId) {
		return productMetaRepository.findByProductProductId(prodId);
	}

}
