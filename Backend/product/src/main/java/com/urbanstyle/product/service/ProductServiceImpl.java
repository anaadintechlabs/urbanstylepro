package com.urbanstyle.product.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.anaadihsoft.common.DTO.ProductDTO;
import com.anaadihsoft.common.DTO.ProductVariantDTO;
import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.Product;
import com.anaadihsoft.common.master.ProductAttributeDetails;
import com.anaadihsoft.common.master.ProductImages;
import com.anaadihsoft.common.master.ProductInventory;
import com.anaadihsoft.common.master.ProductMeta;
import com.anaadihsoft.common.master.ProductVariant;
import com.anaadihsoft.common.master.User;
import com.anaadihsoft.common.master.WarehouseInfo;
import com.urbanstyle.product.repository.ProductAttributeDetailsRepository;
import com.urbanstyle.product.repository.ProductImagesRepository;
import com.urbanstyle.product.repository.ProductInventoryRepo;
import com.urbanstyle.product.repository.ProductMetaRepository;
import com.urbanstyle.product.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{

	private static final int ACTIVE =	1;
	private static final String SLASH = "/";
	   @Value("${application.public.domain}")
		private String applicationPublicDomain;

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
	
	@Autowired
	private ProductVarientService productVarientSerice;
	
	@Autowired 
	private ProductMetaService productMetaService;
	
	@Autowired
	private ProductInventoryRepo productInventRepo;
	


	@Override
	public List<Product> getAllProducts() {
		return (List<Product>) productRepository.findAll();
	}

	@Override
	public Product createProduct(ProductDTO productDTO, MultipartFile[] files,boolean fromUpdate) throws Exception {
		Product oldProduct=productRepository.findByProductCode(productDTO.getProduct().getProductCode());
		if(oldProduct!=null && !fromUpdate) {
			System.out.println("duplicate check"); // for new product 
			return null;
		}
		
		int i=0;

		if(files!=null) {
		 i=files.length;
		}
		productDTO.getProduct().setUser(null);
		oldProduct=productRepository.save(productDTO.getProduct());
		MultipartFile file = null;
		if(i>0)
		{
			file=files[0];

		}
		createProductVariant(productDTO.getProductVariantDTO(),oldProduct,file);
		if(productDTO.getProductMetaInfo()!=null)
		{
			List<ProductMeta> productMetaList = new ArrayList<>();
			saveProductMetaInformation(productDTO.getProductMetaInfo(),oldProduct,productMetaList);
			if(productMetaList!=null && !productMetaList.isEmpty())
			{
				productMetaRepository.saveAll(productMetaList);
			}
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
		
		// save all inventory
		
		//updateInventory(productDTO);

		return oldProduct;
	}

	public String generateFileNameFromMultipart(MultipartFile multiPart) {
	    	String fileName = multiPart.getOriginalFilename().replace("\\",SLASH).replace(" ", "_");
	    	int lastIndex = fileName.lastIndexOf(SLASH);
	    	if(lastIndex!=-1) {
	    		fileName = fileName.substring(lastIndex+1);
	    	}
	        return new Date().getTime() + "-" + fileName;
	    }
	   
		 public String generateFileUri(String fileName) {
		    	return applicationPublicDomain+"/downloadFile/"+fileName;

		    }
		    
		 
	private void createProductVariant(List<ProductVariantDTO> productVariantDTOList,Product product, MultipartFile file) {
		String mainImageUrl=null;
		if(file!=null)
		{
	        String fileName = StringUtils.cleanPath(generateFileNameFromMultipart(file));
	        mainImageUrl=generateFileUri(fileName);
	        
		}
		for(ProductVariantDTO productVariantDTO:productVariantDTOList)
		{
			ProductVariant productVariant=productVariantDTO.getProductVariant();
			productVariant.setProduct(product);
			productVariant.setMainImageUrl(mainImageUrl);
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
	public Product updateProduct(ProductDTO productDTO, MultipartFile[] files) {
		Product oldProduct=productRepository.findByProductId(productDTO.getProduct().getProductId());
		productVariantRepository.deleteAllProductVarient(oldProduct.getProductId());
		try {
			return  createProduct(productDTO, files,true);
		} catch (Exception e) {
			e.printStackTrace();
			return  null;
		}
	}
//
	@Override
	public List<Product> getBestSellingProducts(Filter filter) {

		//Logic
		//Order table 
		return null;
	}

	@Override
	public List<Product> getAllProductOfUser(long userId, Filter filter) {
		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
				filter.getSortingDirection() != null
				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter.getSortingField());
		return productRepository.findByUserId(userId,pagable);
	}

	@Override
	public List<Product> getAllActiveOrInactiveProductOfUser(long userId, Filter filter, int status) {
		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
				filter.getSortingDirection() != null
				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter.getSortingField());
		return productRepository.findByUserIdAndStatus(userId,status,pagable);
	}

	@Override
	public void changeStatusOfProduct(long productId, int status) {
		 productRepository.changeStatusOfProduct(productId,status);
		
	}

	@Override
	public ProductDTO getCompleteProduct(long prodId) {
		ProductDTO productDTO = new ProductDTO();
		Product oldProduct=productRepository.findByProductId(prodId);
		List<ProductVariantDTO> productVarientDTOList = productVarientSerice.getALLProductVarientDTO(1,prodId);
		List<ProductMeta> allproductMetaInfo = productMetaService.findAllMetaInfo(prodId);
		
		productDTO.setProduct(oldProduct);
		productDTO.setProductMetaInfo(allproductMetaInfo);
		productDTO.setProductVariantDTO(productVarientDTOList);
		
		return productDTO;
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
	 private void updateInventory(ProductDTO productDTO) {
		 List<ProductVariantDTO> allVariantsDTO = productDTO.getProductVariantDTO();
		 List<ProductInventory> allInventory = new ArrayList<>();
		 for(ProductVariantDTO prodvrDTO : allVariantsDTO) {
			 ProductInventory inventory = new ProductInventory();
			 inventory.setAvgSalesPrice(100);
			 inventory.setCreatedBy("");
			 inventory.setCreatedDate((java.sql.Date) new Date());
			 inventory.setHoldingBalance(100);
			 inventory.setModifiedBy("");
			 inventory.setProductVariant(prodvrDTO.getProductVariant());
			 inventory.setQty((long) prodvrDTO.getProductVariant().getTotalQuantity());
			 inventory.setReminderPoint(0);
			 inventory.setReservedQty((long) prodvrDTO.getProductVariant().getReservedQuantity());
			 inventory.setStandardSalesPrice(prodvrDTO.getProductVariant().getDisplayPrice());
			 inventory.setStockCost(100);
			 inventory.setUser(new User());
			 inventory.setWarehouse(new WarehouseInfo());
			 allInventory.add(inventory);
		 }
		 productInventRepo.saveAll(allInventory);	  
	}

}
