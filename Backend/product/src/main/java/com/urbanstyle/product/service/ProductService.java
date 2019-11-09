package com.urbanstyle.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.Product;

@Service
public interface ProductService {

	List<Product> getAllMainProductsOfUser(long userId, Filter filter);

	List<Product> getAllVariantProductsOfProductOfUser(long userId, long productId, Filter filter);

}
