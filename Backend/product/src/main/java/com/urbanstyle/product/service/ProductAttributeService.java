package com.urbanstyle.product.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.anaadihsoft.common.master.ProductAttributeDetails;

@Service
public interface ProductAttributeService {

	Map<Long, String> findAllAttributeList(long productVariantId);

	Map<String, String> findAllAttributeListWithAttributeKey(long prodVarId);

	List<Object[]> getAllAttributeDetailsOfFullProduct(long productId);

}
