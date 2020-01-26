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

import com.anaadihsoft.common.DTO.FilterDTO;
import com.anaadihsoft.common.DTO.InventorySearchDTO;
import com.anaadihsoft.common.DTO.ProductVariantDTO;
import com.anaadihsoft.common.DTO.VariantPriceUpdateDTO;
import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.Product;
import com.anaadihsoft.common.master.ProductAttributeDetails;
import com.anaadihsoft.common.master.ProductVariant;
import com.mysql.fabric.xmlrpc.base.Array;
import com.urbanstyle.product.DAO.ProductVarientDAO;
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
	public List<VariantPriceUpdateDTO> updateVarientDTO(List<VariantPriceUpdateDTO> allVarientDTO) {
		for (VariantPriceUpdateDTO variantPriceUpdateDTO : allVarientDTO) {
			productVarRepo.updateVarientDTO(variantPriceUpdateDTO.getProductVariantId(),variantPriceUpdateDTO.getActualPrice(),variantPriceUpdateDTO.getDisplayPrice());
		}
		return allVarientDTO;
	}

	@Override
	public List<ProductVariant> searchInventory(InventorySearchDTO inventorySearchDTO) {
		return productVarientDAO.searchInventory(inventorySearchDTO);
	}
}
