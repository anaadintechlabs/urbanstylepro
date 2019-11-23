package com.urbanstyle.product.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anaadihsoft.common.DTO.ProductVariantDTO;
import com.anaadihsoft.common.master.Product;
import com.anaadihsoft.common.master.ProductVariant;
import com.mysql.fabric.xmlrpc.base.Array;
import com.urbanstyle.product.repository.ProductRepository;

@Service
public class ProductVarientServiceImpl implements ProductVarientService {

	@Autowired
	private ProductVarientRepository productVarRepo;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductRepository productRepo;
	
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
	public List<ProductVariant> getAllVarients(int Status) {
		return productVarRepo.findByStatus(Status);
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
	
	
}
