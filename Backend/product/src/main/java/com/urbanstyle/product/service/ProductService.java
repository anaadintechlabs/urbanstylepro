
package com.urbanstyle.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anaadihsoft.common.DTO.ProductDTO;
import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.Product;

@Service
public interface ProductService {

//	/**
//	 * 
//	 * @param userId
//	 * @param filter
//	 * @return
//	 */
//	List<Product> getAllMainProductsOfUser(long userId, Filter filter);
//
//	/**
//	 * 
//	 * @param userId
//	 * @param productId
//	 * @param filter
//	 * @return
//	 */
//	List<Product> getAllVariantProductsOfProductOfUser(long userId, long productId, Filter filter);
//
//	/**
//	 * 
//	 * @param categoryId
//	 * @param filter
//	 * @return
//	 */
//	List<Product> getAllProductOfCategory(long categoryId, Filter filter);
//
//	/**
//	 * 
//	 * @param id
//	 * @return
//	 */
//	Product getProductById(long id);
	
	
	List<Product> getAllProducts();
	
	Product createProduct(ProductDTO productDTO);

	Product updateProduct(Product product);

	List<Product> getBestSellingProducts(Filter filter);

}

