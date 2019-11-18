package com.urbanstyle.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.Product;
import com.urbanstyle.product.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{

	private static final String ACTIVE ="ACTIVE";
	private ProductRepository productRepository;
	
//	@Override
//	public List<Product> getAllMainProductsOfUser(long userId, Filter filter) {
//		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
//				filter.getSortingDirection() != null
//				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
//						: Sort.Direction.ASC,
//						filter.getSortingField());
//		
//		return productRepository.findByStatusAndVariantFalse(ACTIVE,pagable);
//	}
//
//	@Override
//	public List<Product> getAllVariantProductsOfProductOfUser(long userId, long productId, Filter filter) {
//		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
//				filter.getSortingDirection() != null
//				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
//						: Sort.Direction.ASC,
//						filter.getSortingField());
//
//		return null;
//		//return productRepository.findByStatusAndParentProductId(ACTIVE,pagable);
//	}
//
//	@Override
//	public List<Product> getAllProductOfCategory(long categoryId, Filter filter) {
//		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
//				filter.getSortingDirection() != null
//				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
//						: Sort.Direction.ASC,
//						filter.getSortingField());
//		return productRepository.findByStatusAndCategoryCategoryId(ACTIVE,categoryId,pagable);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see com.urbanstyle.product.service.ProductService#getProductById(long)
//	 */
//	@Override
//	public Product getProductById(long prodId) {
//		Optional<Product> optProd= productRepository.findById(prodId);
//		
//		return optProd.isPresent() ? optProd.get():null;
//		
//	}

	@Override
	public List<Product> getAllProducts() {
		return (List<Product>) productRepository.findAll();
	}

	@Override
	public Product createProduct(Product product) {
		Product oldProduct=productRepository.findByProductName(product.getProductName());
		if(oldProduct!=null) {
			return null;
		}
		product=productRepository.save(product);
		return product;
	}

	@Override
	public Product updateProduct(Product product) {
		Product oldProduct=productRepository.findByProductId(product.getProductId());
		updatProduct(oldProduct,product);
		return productRepository.save(oldProduct);
	}

	private void updatProduct(Product oldProduct, Product product) {
			if(!product.getProductImages().isEmpty()) {
				oldProduct.setProductImages(product.getProductImages());
			}
			if(!product.getProductVariants().isEmpty()) {
				oldProduct.setProductVariants(product.getProductVariants());
			}
			if(!product.getProductVideos().isEmpty()) {
				oldProduct.setProductVideos(product.getProductVideos());
			}
	}
	
	

}
