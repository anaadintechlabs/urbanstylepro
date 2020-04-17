package com.urbanstyle.order.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.DTO.AffiliateComissionDTO;
import com.anaadihsoft.common.DTO.OrderUiListingDTO;
import com.anaadihsoft.common.DTO.ReturnUiListDTO;
import com.anaadihsoft.common.master.AffiliateCommisionOrder;

@Repository
public interface AffiliateCommisionOrderRepo extends PagingAndSortingRepository<AffiliateCommisionOrder, Long> {

	AffiliateCommisionOrder findByOrderProdId(long orderProdId);

	List<AffiliateCommisionOrder> findByAffiliateIdId(long affiliateId);

	@Query("Select new com.anaadihsoft.common.DTO.OrderUiListingDTO(u.orderProd) FROM AffiliateCommisionOrder u where  u.affiliateId.id =?1  AND u.returnId=NULL ")
	List<OrderUiListingDTO> getOrderForAffialite(long affiliateId, Pageable pagable);

	@Query("Select new com.anaadihsoft.common.DTO.OrderUiListingDTO(u.orderProd) FROM AffiliateCommisionOrder u where  u.affiliateId.id =?1 and u.createdDate between ?2 and ?3 AND u.returnId=NULL ")
	List<OrderUiListingDTO> getOrderForAffialiteAndCreatedDateBetween(long affiliateId, Date startDate, Date endDate,
			Pageable pagable);
	
	@Query("Select new com.anaadihsoft.common.DTO.ReturnUiListDTO(u.returnId) FROM AffiliateCommisionOrder u where  u.affiliateId.id =?1 and u.returnId!=NULL")
	List<ReturnUiListDTO> getReturnByAffiliate(long affiliateId, Pageable pagable);

	@Query("Select new com.anaadihsoft.common.DTO.OrderUiListingDTO(u.orderProd) FROM AffiliateCommisionOrder u where  u.affiliateId.id =?1  and u.status=?2 AND u.returnId=NULL ")

	List<OrderUiListingDTO> getOrderForAffialiteAndStatus(long affiliateId, String status, Pageable pagable);

	@Query("Select new com.anaadihsoft.common.DTO.AffiliateComissionDTO(sum(u.commision),u.prodvarid) FROM AffiliateCommisionOrder u where  u.affiliateId.id =?1   AND u.returnId=NULL group by u.prodvarid")
	List<AffiliateComissionDTO> getTotalComissionGroupByProduct(long affiliateId, Pageable pagable);

	

}
