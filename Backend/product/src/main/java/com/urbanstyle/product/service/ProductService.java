
package com.urbanstyle.product.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.anaadihsoft.common.DTO.ProductDTO;
import com.anaadihsoft.common.DTO.ProductVariantDTO;
import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.Product;
import com.anaadihsoft.common.master.ProductAttributeDetails;
import com.anaadihsoft.common.master.ProductMeta;
import com.anaadihsoft.common.master.ProductVariant;

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
	
	Product createProduct(ProductDTO productDTO, MultipartFile[] files,boolean fromUpdate) throws Exception;

	Product updateProduct(ProductDTO productDTO, MultipartFile[] files);

	List<Product> getBestSellingProducts(Filter filter);
	
	 void saveProductAttributeDetails(Map<Long, String> attributesMap, ProductVariant productVariant);

	List<Product> getAllProductOfUser(long userId, Filter filter);

	List<Product> getAllActiveOrInactiveProductOfUser(long userId, Filter filter, int status);

	void changeStatusOfProduct(long productId, int status);

	ProductDTO getCompleteProduct(long prodId);
	 

}

