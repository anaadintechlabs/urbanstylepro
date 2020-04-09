package com.urbanstyle.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.DTO.AffiliateLinkDTO;
import com.anaadihsoft.common.master.ProductVariant;
import com.anaadihsoft.common.master.ShortCodeGenerator;
import com.anaadihsoft.common.master.User;

@Repository
public interface ShortCodeGeneratorRepository extends PagingAndSortingRepository<ShortCodeGenerator, Long>{

	ShortCodeGenerator findByShortCode(String uniqueId);

	boolean existsByShortCode(String shortCode);

	boolean existsByUserAndProdVar(User loginuser, ProductVariant varient);

	@Query("Select new com.anaadihsoft.common.DTO.AffiliateLinkDTO(uop) from ShortCodeGenerator uop where uop.user.id =?1 ")
	List<AffiliateLinkDTO> getGeneratedCodeAndLinkOfAffiliate(long userId);

}