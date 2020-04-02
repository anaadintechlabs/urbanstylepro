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
import com.anaadihsoft.common.DTO.ProductDTOWithImage;
import com.anaadihsoft.common.DTO.ProductVariantDTO;
import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.external.UrlShortner;
import com.anaadihsoft.common.master.CategoryMeta;
import com.anaadihsoft.common.master.Product;
import com.anaadihsoft.common.master.ProductAttributeDetails;
import com.anaadihsoft.common.master.ProductImages;
import com.anaadihsoft.common.master.ProductInventory;
import com.anaadihsoft.common.master.ProductMeta;
import com.anaadihsoft.common.master.ProductVariant;
import com.anaadihsoft.common.master.ShortCodeGenerator;
import com.anaadihsoft.common.master.User;
import com.anaadihsoft.common.master.WarehouseInfo;
import com.urbanstyle.product.repository.CategoryMetaRepository;
import com.urbanstyle.product.repository.ProductAttributeDetailsRepository;
import com.urbanstyle.product.repository.ProductImagesRepository;
import com.urbanstyle.product.repository.ProductInventoryRepo;
import com.urbanstyle.product.repository.ProductMetaRepository;
import com.urbanstyle.product.repository.ProductRepository;
import com.urbanstyle.product.repository.ShortCodeGeneratorRepository;
import com.urbanstyle.product.repository.UserRepository;

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
	@Autowired
	private ShortCodeGeneratorRepository shortCodeGen;
	
	@Autowired
	private UserRepository userRepo;
	
	private UrlShortner urlSShort;
	
	@Autowired
	private CategoryMetaRepository categoryMetaRepository;
	
	@Override
	public List<Product> getAllProducts() {
		return (List<Product>) productRepository.findAll();
	}
//updating single producct
	private Product updateProductForSingleVariant(ProductDTO productDTO, MultipartFile[] files, boolean fromUpdate) {
		// TODO Auto-generated method stub
		List<ProductVariantDTO> pvDTOList=productDTO.getProductVariantDTO();
		if(pvDTOList!=null && !pvDTOList.isEmpty())
		{
			String mainImageUrl=null;
			String fileName=null;
			if(files!=null)
			{
		        fileName = StringUtils.cleanPath(generateFileNameFromMultipart(files[0]));
		        mainImageUrl=generateFileUri(fileName);		        
			}
			
		ProductVariantDTO pvDTO=pvDTOList.get(0);	
		ProductVariant productVariant=pvDTO.getProductVariant();
		productVariant.setProduct(productDTO.getProduct());
		Product product=productDTO.getProduct();
		//values which are copied from Product to each variant
		productVariant.setLegalDisclaimer(product.getLegalDisclaimer());
		productVariant.setCategoryId(product.getCategoryId());
		productVariant.setLongDescription(product.getLongDescription());
		productVariant.setMainImageUrl(mainImageUrl);
		productVariant.setCreatedBy(product.getUser().getId()+"");
		productVariant.setProductCondition(product.getProductCondition());
		
		
		productVariant=productVariantRepository.save(productVariant);
		if(pvDTO.getAttributesMap()!=null)
		{
			saveProductAttributeDetails(pvDTO.getAttributesMap(),productVariant);
			
		}
		//set image and meta info here
		
		int i=0;

		if(files!=null) {
		 i=files.length;
		}
		
		if(productDTO.getProductMetaInfo()!=null)
		{
			List<ProductMeta> productMetaList =	saveProductMetaInformation(productDTO.getProductMetaInfo(),productDTO.getProduct(),productVariant);
			if( !productMetaList.isEmpty())
			{
				System.out.println("about to save meta information");
				productMetaRepository.saveAll(productMetaList);
				productMetaList.clear();
			}
		}
		
		if(i>0)
		{
			List<ProductImages> productMedias=fileUploadService.storeMediaForProduct(files,productDTO.getProduct(),mainImageUrl,productVariant);
			if(productMedias!=null && !productMedias.isEmpty())
			{
				productImagesRepository.saveAll(productMedias);
			}
		}
		
		}
		return null;
	}
	
	//FIRST SAVE ALL INFORMATION REGARDING THE PRODUCT
	// LIKE SAVE META AND IMAGE FOR PRODUCT THEN FOR EACH VARIANT ALSO
	//SAVE BOTH INFO WITH PRODUCT AND PRODUCT VARIANT
	@Override
	public Product createProduct(ProductDTO productDTO, MultipartFile[] files,boolean fromUpdate) throws Exception {
		//Product oldProduct=productRepository.findByProductCode(productDTO.getProduct().getProductCode());
		int i=0;

		if(files!=null) {
		 i=files.length;
		}
		Product product = productDTO.getProduct();
		product.setCreatedBy(product.getUser().getId()+"");
		product.setCreatedDate(new Date());
		product.setTotalVarients(productDTO.getProductVariantDTO() != null ?productDTO.getProductVariantDTO().size():0);
		if(product.getTotalVarients()>1)
		{
			product.setVariantExist(true);
		}
		urlSShort = new UrlShortner();
		String uid = urlSShort.generateUid("PR-", 9);
		product.setUniqueProdId("PR-"+uid);
		
		Product oldProduct=productRepository.save(product);
		
		MultipartFile file = null;
		if(i>0)
		{
			file=files[0];
		}
		if(productDTO.getProductMetaInfo()!=null)
		{
			List<ProductMeta> productMetaList =saveProductMetaInformation(productDTO.getProductMetaInfo(),oldProduct,null);
			if( !productMetaList.isEmpty())
			{
				System.out.println("about to save meta information");
				productMetaRepository.saveAll(productMetaList);
			}
		}
		
		System.out.println("variant dto"+productDTO.getProductVariantDTO());
		String mainImageUrl=createProductVariant(productDTO,oldProduct,file,i,files);
		
		
		if(i>0)
		{
			List<ProductImages> productMedias=fileUploadService.storeMediaForProduct(files,oldProduct,mainImageUrl, null);
			if(productMedias!=null && !productMedias.isEmpty())
			{
				productImagesRepository.saveAll(productMedias);
			}
		}
				
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
		    
		 
	private String createProductVariant(ProductDTO productDTO,Product product, MultipartFile file, int i, MultipartFile[] files) {
		List<ProductVariantDTO> productVariantDTOList = productDTO.getProductVariantDTO();
		String mainImageUrl=null;
		String fileName=null;
		if(file!=null)
		{
	         fileName = StringUtils.cleanPath(generateFileNameFromMultipart(file));
	        mainImageUrl=generateFileUri(fileName);
	        
		}
		for(ProductVariantDTO productVariantDTO:productVariantDTOList)
		{
			ProductVariant productVariant=productVariantDTO.getProductVariant();
			productVariant.setProduct(product);
			productVariant.setStatus(1);
			productVariant.setCategoryId(product.getCategoryId());
			productVariant.setLongDescription(product.getLongDescription());
			productVariant.setFeatures(product.getFeatures());
			productVariant.setLegalDisclaimer(product.getLegalDisclaimer());
			productVariant.setMainImageUrl(mainImageUrl);
			productVariant.setCreatedBy(product.getUser().getId()+"");
			UrlShortner urlSShort = new UrlShortner(productVariant.getVariantCode(), product.getUser().getId(), productVariant.getSku());
			String uid = urlSShort.generateUid("PV-", 9);

			productVariant.setUniqueprodvarId("PV-"+uid);
			productVariant.setProductVarLink(urlSShort.generateLink());
			productVariant=productVariantRepository.save(productVariant);
			if(productVariantDTO.getAttributesMap()!=null)
			{
				saveProductAttributeDetails(productVariantDTO.getAttributesMap(),productVariant);
				
			}
			//NOW SAVE IMAGE AND VARIANT ALSO FOR EVERY VARIANT
			
			if(productDTO.getProductMetaInfo()!=null)
			{
				List<ProductMeta> productMetaList=saveProductMetaInformation(productDTO.getProductMetaInfo(),product,productVariant);
				if( !productMetaList.isEmpty())
				{
					productMetaRepository.saveAll(productMetaList);
					productMetaList.clear();
					
				}
			}
			
			if(i>0)
			{
				List<ProductImages> productMedias=fileUploadService.storeMediaForProduct(files,product,mainImageUrl,productVariant);
				if(productMedias!=null && !productMedias.isEmpty())
				{
					productImagesRepository.saveAll(productMedias);
				}
			}
		}
		return fileName;
	}

	public List<ProductMeta> saveProductMetaInformation(List<ProductMeta> productMetaInfo, Product product, ProductVariant productVariant) {
		List<ProductMeta> productMetaList= new ArrayList<>();
		for(ProductMeta productMeta:productMetaInfo)
		{
			
//			if(productMeta.getMetaKey()!=null && !productMeta.getMetaKey().isEmpty() && productMeta.getMetaValue()!=null && !productMeta.getMetaValue().isEmpty())
//			{
			ProductMeta newProd= new ProductMeta();
			newProd.setMetaKey(productMeta.getMetaKey());
			newProd.setMetaValue(productMeta.getMetaValue());
			List<CategoryMeta> metaList=categoryMetaRepository.findByMetaKey(productMeta.getMetaKey());
			if(metaList!=null && !metaList.isEmpty())
			{
					newProd.setMetaId(metaList.get(0));
			}
			newProd.setProduct(product);
			newProd.setProductVariant(productVariant);
			productMetaList.add(newProd);
			//}
		}
		return productMetaList;
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
		if( !productAttributeDetails.isEmpty())
		{
			productAttrRepo.saveAll(productAttributeDetails);
		}
		
	}

	@Override
	public Product updateProduct(ProductDTO productDTO, MultipartFile[] files) {
		Product oldProduct=productRepository.findByProductId(productDTO.getProduct().getProductId());
		//first delete all meta information
		//then delete all media
		//then all product details
		//then allvariant
		
		productMetaRepository.deleteAllMeta(oldProduct.getProductId());
		
	//roductImagesRepository.deleteAllImage(oldProduct.getProductId());
		
		List<ProductVariant> pvList=productVariantRepository.findByProductProductId(oldProduct.getProductId());
		if(pvList!=null && !pvList.isEmpty())
		{		
		productAttrRepo.deleteAllProductAttribute(pvList);
		}

		//productVariantRepository.deleteAllProductVarient(oldProduct.getProductId());
		try {
			return  createProduct(productDTO, files,true);
		} catch (Exception e) {
			e.printStackTrace();
			return  null;
		}
	}
	
	@Override
	public Product updateSingleProductVariant(ProductDTO productDTO, MultipartFile[] files, long productVariantId) {
		Product oldProduct=productRepository.findByProductId(productDTO.getProduct().getProductId());

		productMetaRepository.deleteAllMeta(oldProduct.getProductId());
		productAttrRepo.deleteSingleVariantProductAttribute(productVariantId);
		//Now delete that variant
		productVariantRepository.deleteById(productVariantId);
		try {
			return  updateProductForSingleVariant(productDTO, files,true);
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
	public ProductDTOWithImage getCompleteProduct(long prodId) {
		ProductDTOWithImage productDTO = new ProductDTOWithImage();
		Product oldProduct=productRepository.findByProductId(prodId);
		List<ProductVariantDTO> productVarientDTOList = productVarientSerice.getALLProductVarientDTO(1,prodId);
		List<ProductMeta> allproductMetaInfo = productMetaService.findAllMetaInfo(prodId);
		List<String> imageUrl=productImagesRepository.findUrlByProduct(prodId);
		productDTO.setProduct(oldProduct);
		productDTO.setProductMetaInfo(allproductMetaInfo);
		productDTO.setProductVariantDTO(productVarientDTOList);
		productDTO.setImageUrls(imageUrl);
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
		 User user = productDTO.getProduct().getUser();
		 for(ProductVariantDTO prodvrDTO : allVariantsDTO) {
			 ProductInventory inventory = new ProductInventory();
			 inventory.setAvgSalesPrice(100);
			 inventory.setCreatedBy("");
			 inventory.setCreatedDate( new Date());
			 inventory.setHoldingBalance(100);
			 inventory.setModifiedBy("");
			 inventory.setProductVariant(prodvrDTO.getProductVariant());
			 inventory.setQty((long) prodvrDTO.getProductVariant().getTotalQuantity());
			 inventory.setReminderPoint(0);
			 inventory.setReservedQty((long) prodvrDTO.getProductVariant().getReservedQuantity());
			 inventory.setStandardSalesPrice(prodvrDTO.getProductVariant().getDisplayPrice());
			 inventory.setStockCost(100);
			 
			 inventory.setUser(user);
			 inventory.setWarehouse(new WarehouseInfo());
			 allInventory.add(inventory);
		 }
		 productInventRepo.saveAll(allInventory);	  
	}

	 /**
	  * Method for getting single variant but in the similar fashion as complete variant
	  * In list there will be only one variant
	  */
	@Override
	public ProductDTOWithImage getCompleteVariant(long productVariantId, long prodId) {
		ProductDTOWithImage productDTO = new ProductDTOWithImage();
		Product oldProduct=productRepository.findByProductId(prodId);
		List<ProductVariantDTO> productVarientDTOList = productVarientSerice.getSingleProductVarientDTOList(1,prodId,productVariantId);
		List<ProductMeta> allproductMetaInfo = productMetaService.findAllMetaInfoForVariant(productVariantId);
		List<String> imageUrl=productImagesRepository.findUrlByProductForVariant(productVariantId);
		productDTO.setProduct(oldProduct);
		productDTO.setProductMetaInfo(allproductMetaInfo);
		productDTO.setProductVariantDTO(productVarientDTOList);
		productDTO.setImageUrls(imageUrl);
		return productDTO;
	}
	
	
	@Override
	public String genAffiliatelink(long prodVarId, long userId) {
		ProductVariant varient = productVarientSerice.findByProdVarId(prodVarId);
		Optional<User> user =  userRepo.findById(userId);
		User loginuser = null;
		if(user.isPresent()) {
			loginuser = user.get();
		}
		UrlShortner urlShorten = new UrlShortner(varient.getSku(),userId,varient.getSku());
		String requiredURL = urlShorten.generateLink();
		ShortCodeGenerator shortcode = new ShortCodeGenerator();
		shortcode.setProdVar(varient);
		shortcode.setShortCode(requiredURL);
		shortcode.setUser(loginuser);
		shortCodeGen.save(shortcode);		
		return requiredURL;
	}



}
