package com.urbanstyle.product.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.anaadihsoft.common.DTO.AttributeMiniDTO;
import com.anaadihsoft.common.DTO.FilterDTO;
import com.anaadihsoft.common.DTO.HomePageFilterDTO;
import com.anaadihsoft.common.DTO.InventorySearchDTO;
import com.anaadihsoft.common.DTO.ProductReviewDTO;
import com.anaadihsoft.common.DTO.ProductVariantDTO;
import com.anaadihsoft.common.DTO.ProductVariantMini;
import com.anaadihsoft.common.DTO.ProductVariantUiDTO;
import com.anaadihsoft.common.DTO.ProductVarientPacketDTO;
import com.anaadihsoft.common.DTO.SingleProductDTO;
import com.anaadihsoft.common.DTO.VariantDTO;
import com.anaadihsoft.common.DTO.VariantDTOWithId;
import com.anaadihsoft.common.DTO.VariantPriceUpdateDTO;
import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.Product;
import com.anaadihsoft.common.master.ProductAttributeDetails;
import com.anaadihsoft.common.master.ProductVariant;
import com.anaadihsoft.common.master.ShortCodeGenerator;
import com.mysql.fabric.xmlrpc.base.Array;
import com.urbanstyle.product.DAO.ProductVarientDAO;
import com.urbanstyle.product.repository.ProductImagesRepository;
import com.urbanstyle.product.repository.ProductRepository;
import com.urbanstyle.product.repository.ShortCodeGeneratorRepository;

@Service
public class ProductVarientServiceImpl implements ProductVarientService {

	@Autowired
	private ProductVarientRepository productVarRepo;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private ProductVarientDAO productVarientDAO;
	
	@Autowired
	private ProductAttributeService productAttributeServce;
	
	@Autowired
	private ProductImagesRepository productImagesRepository;
	
	@Autowired
	private ProductReviewService productReviewService; 
	
	@Autowired
	private CategoryMetaService catMetaService;
	
	@Autowired
	private ShortCodeGeneratorRepository shortCodeGeneratorRepository; 
	
	@Override
	public List<ProductVariant> getAllFeaturedProducts() {
		return productVarRepo.findByFetauredProduct(true);
	}

	@Override
	public boolean setFeaturedProduct(long prodVarId,String featured) {
		Optional<ProductVariant> productVarient = productVarRepo.findByProductVariantId(prodVarId);
		if(productVarient.isPresent()) {
			ProductVariant proModel = productVarient.get();
			
			if(featured.equalsIgnoreCase("yes"))
			{
			proModel.setFetauredProduct(true);
			}
			else
			{
				proModel.setFetauredProduct(false);
			}
			productVarRepo.save(proModel);
			return true;
		}else {
			return false;			
		}
	}
	@Override
	public ProductVariant getDealOftheDay() {
		return productVarRepo.findByDealOfTheDay(true);
	}

	@Override
	public boolean setDealOftheDay(long prodId, String deal) {
		Optional<ProductVariant> productVarient = productVarRepo.findByProductVariantId(prodId);
		if(productVarient.isPresent()) {
			ProductVariant proModel = productVarient.get();
			
			if(deal.equalsIgnoreCase("yes"))
			{
			proModel.setDealOfTheDay(true);
			}
			else
			{
				proModel.setDealOfTheDay(false);
			}
			productVarRepo.save(proModel);
			return true;
		}else {
			return false;			
		}
	}

	@Override
	public List<ProductVariant> getAllVarients(int Status, long prodId) {
		return productVarRepo.findByStatusAndProductProductId(Status,prodId);
	}

	@Override
	public ProductVariant addVarientToProduct(ProductVariantDTO productVarientDTO) {
		productVarRepo.save(productVarientDTO.getProductVariant());
		productService.saveProductAttributeDetails(productVarientDTO.getAttributesMap(), productVarientDTO.getProductVariant());
		//productService.saveProductMetaInformation(productVarientDTO.getProductMetaInfo(), productVarientDTO.getProductVariant());
		return productVarientDTO.getProductVariant();
	}

	@Override
	public boolean deleteVarientToProduct(long prodId, long prodVarId) {
		List<ProductVariant> allVarients = productVarRepo.findByProductProductId(prodId);
		if(allVarients != null && allVarients.size()>1) {
			Optional<ProductVariant>  productVariant = productVarRepo.findByProductVariantId(prodVarId);
			if(productVariant.isPresent()) {
				ProductVariant model = productVariant.get();
				model.setStatus(0);
				productVarRepo.save(model);
			}
		}else if(allVarients != null && allVarients.size() == 1) {
			Optional<ProductVariant>  productVariant = productVarRepo.findByProductVariantId(prodVarId);
			if(productVariant.isPresent()) {
				ProductVariant model = productVariant.get();
				model.setStatus(0);
				productVarRepo.save(model);
			}
			Product product = productRepo.findByProductId(prodId);
			product.setStatus(0);
			productRepo.save(product);
		}
		return true;
	}
	
	
	@Override
	public List<ProductVariant> searchProducts(FilterDTO filterDTO) {
		
		 List<ProductVariant> resultsVarient = productVarientDAO.findInProductVarient(filterDTO);
		
		return resultsVarient;
	}

	@Override
	public List<ProductVariant> getAllProducts() {
		return productVarRepo.findByStatus(1);
	}

	@Override
	public List<ProductVariant> getAllProductOfCategory(long catId, Filter filter) {
		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
				filter.getSortingDirection() != null
				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter.getSortingField());
		return productVarRepo.findByProductCategoryIdAndStatus(catId,1,pagable);
	}

	@Override
	public List<ProductVariant> getAllProductVariantOfUser(long userId, Filter filter) {
		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
				filter.getSortingDirection() != null
				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter.getSortingField());
		return productVarRepo.findByProductUserId(userId,pagable);
	}

	@Override
	public List<ProductVariant> getAllActiveOrInactiveProductVariantOfUser(long userId, Filter filter, int status) {
		final Pageable pagable = PageRequest.of(filter.getOffset(), filter.getLimit(),
				filter.getSortingDirection() != null
				&& filter.getSortingDirection().equalsIgnoreCase("DESC") ? Sort.Direction.DESC
						: Sort.Direction.ASC,
						filter.getSortingField());
		return productVarRepo.findByStatusAndProductUserId(status,userId,pagable);
	}

	@Override
	public void changeStatusOfProduct(long productId, int status) {
		productVarRepo.changeStatusOfProduct(productId,status);
		
	}

	@Override
	public List<ProductVariantDTO> getALLProductVarientDTO(int i, long prodId) {
		List<ProductVariantDTO> allDTO = new ArrayList<ProductVariantDTO>();
		List<ProductVariant> allVarients = getAllVarients(1,prodId);
		for(ProductVariant varient : allVarients) {
			ProductVariantDTO DTO = new ProductVariantDTO();
			 Map<Long,String> attributesMap = productAttributeServce.findAllAttributeList(varient.getProductVariantId());
			DTO.setAttributesMap(attributesMap);
			DTO.setProductVariant(varient);
			allDTO.add(DTO);
		}
		return allDTO;
	}
	
	
	@Override
	public List<ProductVariantDTO> getSingleProductVarientDTOList(int i, long prodId, long productVariantId) {
		List<ProductVariantDTO> allDTO = new ArrayList<ProductVariantDTO>();
		Optional<ProductVariant> productVariantOpt=productVarRepo.findById(productVariantId);
		if(productVariantOpt.isPresent())
		{
			ProductVariant productVariant= productVariantOpt.get();
			 Map<Long,String> attributesMap = productAttributeServce.findAllAttributeList(productVariant.getProductVariantId());
			 ProductVariantDTO DTO = new ProductVariantDTO();
			 DTO.setAttributesMap(attributesMap);
				DTO.setProductVariant(productVariant);
				allDTO.add(DTO);
		}
		return allDTO;
	}
	
	@Override
	public List<VariantPriceUpdateDTO> updateVarientDTO(List<VariantPriceUpdateDTO> allVarientDTO) {
		for (VariantPriceUpdateDTO variantPriceUpdateDTO : allVarientDTO) {
			productVarRepo.updateVarientDTO(variantPriceUpdateDTO.getProductVariantId(),variantPriceUpdateDTO.getDisplayPrice());
		}
		return allVarientDTO;
	}

	@Override
	public List<ProductVariant> searchInventory(InventorySearchDTO inventorySearchDTO) {
		return productVarientDAO.searchInventory(inventorySearchDTO);
	}

	@Override
	public SingleProductDTO getSingleProductDetail(String uniqueId) {
		SingleProductDTO singleProductDTO = new SingleProductDTO();
		long affiliateId=0;
		ProductVarientPacketDTO mainProductPacket = new ProductVarientPacketDTO();
		ProductVariant prodVarient =  productVarRepo.findByUniqueprodvarId(uniqueId);
		if(prodVarient==null)
		{
			//check in Short Code table
			ShortCodeGenerator scg=shortCodeGeneratorRepository.findByShortCode(uniqueId);
			if(scg!=null)
			{
				prodVarient=scg.getProdVar();
				affiliateId=scg.getUser().getId();
			}
			else
			{
				//RETURN WITH MESSAGE THAT NO VARIANT EXISTS
				return null;
			}
		}
		if(prodVarient!=null) {
			long prodVarId=prodVarient.getProductVariantId();
			Map<String, String> attrDetails = productAttributeServce.findAllAttributeListWithAttributeKey(prodVarId);
			List<String> allImagesMain = productImagesRepository.findUrlByProductForVariant(prodVarId);
			
			Product product = prodVarient.getProduct();
			singleProductDTO.setVariantTotal(product.getTotalVarients());
			
			List<VariantDTO> variants = new ArrayList<>();
			List<VariantDTOWithId> variantCombinations= new ArrayList<>();
			List<Object[]> attrDetailsTotal= productAttributeServce.getAllAttributeDetailsOfFullProduct(product.getProductId());
			if(attrDetailsTotal!=null && !attrDetailsTotal.isEmpty())
			{
				for(Object[] obj :attrDetailsTotal)
				{
					Optional<VariantDTO> variantOpt = variants.stream().filter(elem -> elem.getVariationName().equals(obj[0])).findAny();
					if(!variantOpt.isEmpty())
					{
						VariantDTO variant=variantOpt.get();
						Optional<AttributeMiniDTO> optAttr=variant.getVariationData().stream().filter(elem->elem.getName().equals(obj[2].toString())).findAny();
						
						AttributeMiniDTO attributeMini= new AttributeMiniDTO();
						attributeMini.setId(obj[1].toString());
						attributeMini.setName(obj[2].toString());

						if(optAttr.isEmpty())
						{					
						variant.getVariationData().add(attributeMini);
						}

					}
					else
					{
						VariantDTO variant = new VariantDTO();
						variant.setVariationName(obj[0].toString());
						Set<AttributeMiniDTO> data = new HashSet<>();
						AttributeMiniDTO attributeMini= new AttributeMiniDTO();
						attributeMini.setId(obj[1].toString());
						attributeMini.setName(obj[2].toString());
						data.add(attributeMini);
						variant.setVariationData(data);
						variants.add(variant);
						
					}
					
					Optional<VariantDTOWithId> variantWithIdOpt = variantCombinations.stream().filter(elem -> elem.getVariationId().equals(obj[3].toString())).findAny();
					if(!variantWithIdOpt.isEmpty())
					{

						System.out.println("same variant");
						VariantDTOWithId variantDTOWithId=	variantWithIdOpt.get();
//						Optional<AttributeMiniDTO> optAttr=variantDTOWithId.getVariationData().stream().filter(elem->elem.getName().equals(obj[2].toString())).findAny();

						AttributeMiniDTO attributeMini= new AttributeMiniDTO();
						attributeMini.setId(obj[1].toString());
						attributeMini.setName(obj[2].toString());
//						if(optAttr.isEmpty())
//						{					
							variantDTOWithId.getVariationData().add(attributeMini);
						//}
					}
					else
					{
						VariantDTOWithId variantWithId= new VariantDTOWithId();
						Set<AttributeMiniDTO> data = new HashSet<>();
						AttributeMiniDTO attributeMini= new AttributeMiniDTO();
						attributeMini.setId(obj[1].toString());
						attributeMini.setName(obj[2].toString());
						data.add(attributeMini);					
						variantWithId.setVariationId(obj[3].toString());
						variantWithId.setVariationData(data);
						variantCombinations.add(variantWithId);
						System.out.println(obj[1].toString()+"  "+ obj[2].toString() +" "+obj[3].toString());
					}
				}
			}
			
			long categoryId = prodVarient.getCategoryId();
			long prodId=prodVarient.getProduct().getProductId();
			//Other variants of same category but does not belong to same Product,
			//Variant of same product will be siblings 
			//List<ProductVariant> allRelatedProducts = getRelatedProducts(prodId,categoryId);
			
			// set mainProductPacket
			ProductVariantUiDTO mainProdDto = new ProductVariantUiDTO();
			mainProdDto.setAttributesMap(attrDetails);
			mainProdDto.setProductVariant(prodVarient);
			mainProductPacket.setAllImages(allImagesMain);
			mainProductPacket.setMainProduct(mainProdDto); 
			

			
			List<ProductReviewDTO> allReviews = productReviewService.getAllReviewsforSPV(prodVarId);
			
			singleProductDTO.setMainProductPacket(mainProductPacket);
			singleProductDTO.setVariants(variants);
			singleProductDTO.setAllReviews(allReviews);
			singleProductDTO.setVariantCombinations(variantCombinations);
			singleProductDTO.setAffiliateId(affiliateId);
			
		}

		return singleProductDTO;
		
	}
	
	public List<ProductVariantMini> getRelatedProducts(String uniqueId){
		ProductVariant prodVarient =  productVarRepo.findByUniqueprodvarId(uniqueId);
		if(prodVarient==null)
		{
			//check in Short Code table
			ShortCodeGenerator scg=shortCodeGeneratorRepository.findByShortCode(uniqueId);
			if(scg!=null)
			{
				prodVarient=scg.getProdVar();
			}
			else
			{
				//RETURN WITH MESSAGE THAT NO VARIANT EXISTS
				return null;
			}
		}
		long categoryId = prodVarient.getCategoryId();
		long prodId=prodVarient.getProduct().getProductId();
		return productVarRepo.getRelatedProducts(prodId,categoryId);
	}


	@Override
	public HomePageFilterDTO applyHomePageFilter(String searchString) {
		return productVarientDAO.applyHomePageFilter(searchString);
	}


	@Override
	public List<ProductVariantDTO> applySideBarFilter(String searchString, HashMap<Long, List<String>> filterData) {
		return productVarientDAO.applySideBarFilter(searchString,filterData);
	}

	@Override
	public ProductVariant findByProdVarId(long prodVarId) {
		Optional<ProductVariant> pv = productVarRepo.findById(prodVarId);
		ProductVariant prodVar = null;
		if(pv.isPresent()) {
			prodVar = pv.get();
		}
		return prodVar;
	}

	@Override
	public HomePageFilterDTO getAllVariantOfCategoryWithFilter(long catId) {
		return productVarientDAO.getAllVariantOfCategoryWithFilter(catId);
	}

	@Override
	public ProductVariant getVariantById(long prodVarId) {
		Optional<ProductVariant> optProd= productVarRepo.findById(prodVarId);
		if(!optProd.isPresent())
		{
			return optProd.get();
		}
		return null;
	}

	@Override
	public List<ProductVariant> getAllVariantsByStatus(int status) {
		return productVarRepo.findByStatus(status);
	}


}
