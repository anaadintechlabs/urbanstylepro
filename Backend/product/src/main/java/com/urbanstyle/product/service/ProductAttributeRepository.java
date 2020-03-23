package com.urbanstyle.product.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.master.ProductAttributeDetails;

@Repository
public interface ProductAttributeRepository extends PagingAndSortingRepository<ProductAttributeDetails,Long>{

	List<ProductAttributeDetails> findByProductVariantProductVariantId(long productVariantId);

	@Query("select am.variationName,pad.attributeValue from  ProductAttributeDetails pad inner join AttributeMaster am "
			+ "on pad.attributeMasterId=am.id where pad.productVariant.productVariantId=?1")
	List<Object[]> findAllAttributeListWithAttributeKey(long prodVarId);
	
	
	
	@Query("select am.variationName,pad.attributeMasterId,pad.attributeValue from  ProductAttributeDetails pad inner join AttributeMaster am "
			+ "on pad.attributeMasterId=am.id where pad.productVariant.product.productId=?1")
	List<Object[]> findAllAttributeListWithAttributeKeyAndValue(long prodVarId);
	
	

}
