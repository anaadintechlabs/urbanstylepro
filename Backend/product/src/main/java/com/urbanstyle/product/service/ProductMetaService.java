package com.urbanstyle.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anaadihsoft.common.master.ProductMeta;

@Service
public interface ProductMetaService {

	List<ProductMeta> findAllMetaInfo(long prodId);

}
