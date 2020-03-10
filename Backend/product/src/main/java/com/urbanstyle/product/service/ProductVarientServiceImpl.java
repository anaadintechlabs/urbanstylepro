package com.urbanstyle.product.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.anaadihsoft.common.DTO.FilterDTO;
import com.anaadihsoft.common.DTO.HomePageFilterDTO;
import com.anaadihsoft.common.DTO.InventorySearchDTO;
import com.anaadihsoft.common.DTO.ProductReviewDTO;
import com.anaadihsoft.common.DTO.ProductVariantDTO;
import com.anaadihsoft.common.DTO.ProductVarientPacketDTO;
import com.anaadihsoft.common.DTO.SingleProductDTO;
import com.anaadihsoft.common.DTO.VariantPriceUpdateDTO;
import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.Product;
import com.anaadihsoft.common.master.ProductAttributeDetails;
import com.anaadihsoft.common.master.ProductVariant;
import com.mysql.fabric.xmlrpc.base.Array;
import com.urbanstyle.product.DAO.ProductVarientDAO;
import com.urbanstyle.product.repository.ProductImagesRepository;
import com.urbanstyle.product.repository.ProductRepository;

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
	
	@Override
	public List<ProductVariant> getAllFeaturedProducts() {
		return productVarRepo.findByFetauredProduct(true);
	}

	@Override
	public boolean setFeaturedProduct(long prodVarId) {
		Optional<ProductVariant> productVarient = productVarRepo.findByProductVariantId(prodVarId);
		if(productVarient.isPresent()) {
			ProductVariant proModel = productVarient.get();
			proModel.setFetauredProduct(true);
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
	public boolean setDealOftheDay(long prodId) {
	 try {
		 productVarRepo.setDealOftheDay(false);
		// productVarRepo.setDealofSelectedProd(true,prodId);
		 return true;
	 }catch(Exception e) {
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
	public SingleProductDTO getSingleProductDetail(long prodVarId) {
		SingleProductDTO singleProductDTO = new SingleProductDTO();
		ProductVarientPacketDTO mainProductPacket = new ProductVarientPacketDTO();
		List<ProductVarientPacketDTO> relatedProductsPackets = new ArrayList<>();
		Optional<ProductVariant> optprodVarient =  productVarRepo.findById(prodVarId);
		ProductVariant prodVarient = null;
		if(optprodVarient.isPresent()) {
			prodVarient = optprodVarient.get();
		}
		Map<Long, String> attrDetails = productAttributeServce.findAllAttributeList(prodVarId);
		List<String> allImagesMain = productImagesRepository.findUrlByProduct(prodVarId);
		
		long categoryId = prodVarient.getCategoryId();
		List<ProductVariant> allRelatedProducts = getRelatedProducts(prodVarId,categoryId);
		
		// set mainProductPacket
		ProductVariantDTO mainProdDto = new ProductVariantDTO();
		mainProdDto.setAttributesMap(attrDetails);
		mainProdDto.setProductVariant(prodVarient);
		mainProductPacket.setAllImages(allImagesMain);
		mainProductPacket.setMainProduct(mainProdDto);
		
		for(ProductVariant prVar : allRelatedProducts) {
			ProductVarientPacketDTO relProductPacket = new ProductVarientPacketDTO();
			ProductVariantDTO relProdDto = new ProductVariantDTO();
			Map<Long, String> relattrDetails = productAttributeServce.findAllAttributeList(prodVarId);
			List<String> relallImagesMain = productImagesRepository.findUrlByProduct(prodVarId);
			relProdDto.setAttributesMap(relattrDetails);
			relProdDto.setProductVariant(prVar);
			relProductPacket.setAllImages(relallImagesMain);
			relProductPacket.setMainProduct(relProdDto);
			relatedProductsPackets.add(relProductPacket);
		}
		
		List<ProductReviewDTO> allReviews = productReviewService.getAllReviewsforSPV(prodVarId);
		
		singleProductDTO.setMainProductPacket(mainProductPacket);
		singleProductDTO.setRelatedProductsPackets(relatedProductsPackets);
		singleProductDTO.setAllReviews(allReviews);
		
		return singleProductDTO;
	}
	
	public List<ProductVariant> getRelatedProducts(long prodVarId,long categoryId){
		List<ProductVariant> allrelatedPoducts = productVarRepo.getRelatedProducts(prodVarId,categoryId);
		return allrelatedPoducts;
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
		if(!optProd.isEmpty())
		{
			return optProd.get();
		}
		return null;
	}
}
