package com.urbanstyle.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anaadihsoft.common.master.CategoryMeta;

@Service
public interface CategoryMetaService {

	List<CategoryMeta> getAllMetaOfCategory(long categoryId);

}
