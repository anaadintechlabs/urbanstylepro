package com.urbanstyle.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anaadihsoft.common.master.Product;
import com.anaadihsoft.common.master.ProductVariant;

@Service
public class ProductVarientServiceImpl implements ProductVarientService {

	@Autowired
	private ProductVarientRepository productVarRepo;
	
	@Override
	public List<ProductVariant> getAllFeaturedProducts() {
		return productVarRepo.findByFetauredProduct(true);
	}

	@Override
	public boolean setFeaturedProduct(long prodId) {
		Optional<ProductVariant> productVarient = productVarRepo.findByProductId(prodId);
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
		 productVarRepo.setDealofSelectedProd(true,prodId);
		 return true;
	 }catch(Exception e) {
		 return false;		 
	 }
	}

	@Override
	public List<ProductVariant> getAllVarients(int Status) {
		return productVarRepo.findByStatus(Status);
	}
	
	
}
