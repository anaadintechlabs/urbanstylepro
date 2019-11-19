package com.urbanstyle.product.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.anaadihsoft.common.DTO.ProductDTO;
import com.anaadihsoft.common.DTO.ProductVariantDTO;
import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.Product;
import com.anaadihsoft.common.master.ProductAttributeDetails;
import com.anaadihsoft.common.master.ProductMeta;
import com.anaadihsoft.common.master.ProductVariant;
import com.urbanstyle.product.repository.ProductRepository;
import com.urbanstyle.product.repository.ProductVariantRepository;

@Service
public class ProductServiceImpl implements ProductService{

	private static final int ACTIVE =	1;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductVariantRepository  productVariantRepository;
	
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
	public Product createProduct(ProductDTO productDTO) {
		Product oldProduct=productRepository.findByProductCode(productDTO.getProduct().getProductCode());
		if(oldProduct!=null) {
			System.out.println("duplicate check");
			return null;
		}
		
		oldProduct=productRepository.save(productDTO.getProduct());
		createProductVariant(productDTO.getProductVariantDTO(),oldProduct);

		return oldProduct;
	}

	private void createProductVariant(List<ProductVariantDTO> productVariantDTOList,Product product) {
		List<ProductAttributeDetails> productAttributeDetails = new ArrayList<ProductAttributeDetails>();
		for(ProductVariantDTO productVariantDTO:productVariantDTOList)
		{
			ProductVariant productVariant=productVariantDTO.getProductVariant();
			productVariant.setProduct(product);
			productVariant=productVariantRepository.save(productVariant);
			if(productVariantDTO.getAttributesMap()!=null)
			{
				saveProductAttributeDetails(productVariantDTO.getAttributesMap(),productVariant,productAttributeDetails);
				saveProductMetaInformation(productVariantDTO.getProductMetaInfo(),productVariant);
				
				if(productAttributeDetails!=null && !productAttributeDetails.isEmpty())
				{
					//save the data here
				}
			}
			
			
		}
		
	}

	private void saveProductMetaInformation(List<ProductMeta> productMetaInfo, ProductVariant productVariant) {
		for(ProductMeta productMeta:productMetaInfo)
		{
			productMeta.setProductVariant(productVariant);
		}
		
	}

	private void saveProductAttributeDetails(Map<Long, String> attributesMap, ProductVariant productVariant, List<ProductAttributeDetails> productAttributeDetails) {
		for(Map.Entry<Long,String> entry:attributesMap.entrySet())
		{
			ProductAttributeDetails pad = new ProductAttributeDetails();
			pad.setAttributeMasterId(entry.getKey());
			pad.setAttributeValue(entry.getValue());
			pad.setProductVariant(productVariant);
			pad.setStatus(1);
			productAttributeDetails.add(pad);
		}
		
		
	}

	@Override
	public Product updateProduct(Product product) {
		Product oldProduct=productRepository.findByProductId(product.getProductId());
		//updatProduct(oldProduct,product);
		return productRepository.save(oldProduct);
	}

//	private void updatProduct(Product oldProduct, Product product) {
//			if(!product.getProductImages().isEmpty()) {
//				oldProduct.setProductImages(product.getProductImages());
//			}
//			if(!product.getProductVariants().isEmpty()) {
//				oldProduct.setProductVariants(product.getProductVariants());
//			}
//			if(!product.getProductVideos().isEmpty()) {
//				oldProduct.setProductVideos(product.getProductVideos());
//			}
//	}
//	
	

}
