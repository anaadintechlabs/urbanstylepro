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
import org.springframework.web.multipart.MultipartFile;

import com.anaadihsoft.common.DTO.ProductDTO;
import com.anaadihsoft.common.DTO.ProductVariantDTO;
import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.Product;
import com.anaadihsoft.common.master.ProductAttributeDetails;
import com.anaadihsoft.common.master.ProductImages;
import com.anaadihsoft.common.master.ProductMeta;
import com.anaadihsoft.common.master.ProductVariant;
import com.urbanstyle.product.repository.ProductAttributeDetailsRepository;
import com.urbanstyle.product.repository.ProductImagesRepository;
import com.urbanstyle.product.repository.ProductMetaRepository;
import com.urbanstyle.product.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{

	private static final int ACTIVE =	1;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductVarientRepository  productVariantRepository;
	
	@Autowired
	private ProductAttributeDetailsRepository productAttrRepo;
	
	@Autowired
	private ProductMetaRepository productMetaRepository; 
	
	@Autowired
	private FileUploadService fileUploadService;
	@Autowired
	private ProductImagesRepository productImagesRepository;
	


	@Override
	public List<Product> getAllProducts() {
		return (List<Product>) productRepository.findAll();
	}

	@Override
	public Product createProduct(ProductDTO productDTO, MultipartFile[] files) throws Exception {
		Product oldProduct=productRepository.findByProductCode(productDTO.getProduct().getProductCode());
		if(oldProduct!=null) {
			System.out.println("duplicate check");
			return null;
		}

		oldProduct=productRepository.save(productDTO.getProduct());
		createProductVariant(productDTO.getProductVariantDTO(),oldProduct);
		if(productDTO.getProductMetaInfo()!=null)
		{
			List<ProductMeta> productMetaList = new ArrayList<>();
			saveProductMetaInformation(productDTO.getProductMetaInfo(),oldProduct,productMetaList);
			if(productMetaList!=null && !productMetaList.isEmpty())
			{
				productMetaRepository.saveAll(productMetaList);
			}
		}
		
		int i=0;

		if(files!=null) {
		 i=files.length;
		}
		//BYPASSING CHECK AS OF NOW
		if(i>0)
		{
			List<ProductImages> productMedias=fileUploadService.storeMediaForProduct(files,oldProduct);
			if(productMedias!=null && !productMedias.isEmpty())
			{
				productImagesRepository.saveAll(productMedias);
			}
		}

		return oldProduct;
	}

	private void createProductVariant(List<ProductVariantDTO> productVariantDTOList,Product product) {
		for(ProductVariantDTO productVariantDTO:productVariantDTOList)
		{
			ProductVariant productVariant=productVariantDTO.getProductVariant();
			productVariant.setProduct(product);
			productVariant=productVariantRepository.save(productVariant);
			if(productVariantDTO.getAttributesMap()!=null)
			{
				saveProductAttributeDetails(productVariantDTO.getAttributesMap(),productVariant);
				
			}
			
			
		}
		
	}

	public void saveProductMetaInformation(List<ProductMeta> productMetaInfo, Product product,List<ProductMeta> productMetaList) {
		for(ProductMeta productMeta:productMetaInfo)
		{
			if(productMeta.getMetaKey()!=null && !productMeta.getMetaKey().isEmpty() && productMeta.getMetaValue()!=null && !productMeta.getMetaValue().isEmpty())
			{
			productMeta.setProduct(product);
			productMetaList.add(productMeta);
			}
		}
		
	}

	 public void saveProductAttributeDetails(Map<Long, String> attributesMap, ProductVariant productVariant) {
		 List<ProductAttributeDetails> productAttributeDetails = new ArrayList<ProductAttributeDetails>();
		for(Map.Entry<Long,String> entry:attributesMap.entrySet())
		{
			if(entry.getKey()!=0 && entry.getValue()!=null && !entry.getValue().isEmpty())
			{
			ProductAttributeDetails pad = new ProductAttributeDetails();
			pad.setAttributeMasterId(entry.getKey());
			pad.setAttributeValue(entry.getValue());
			pad.setProductVariant(productVariant);
			pad.setStatus(1);
			productAttributeDetails.add(pad);
			}
		}
		if(productAttributeDetails!=null && !productAttributeDetails.isEmpty())
		{
			productAttrRepo.saveAll(productAttributeDetails);
		}
		
	}

	@Override
	public Product updateProduct(Product product) {
		Product oldProduct=productRepository.findByProductId(product.getProductId());
		//updatProduct(oldProduct,product);
		return productRepository.save(oldProduct);
	}
//
	@Override
	public List<Product> getBestSellingProducts(Filter filter) {

		//Logic
		//Order table 
		return null;
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
