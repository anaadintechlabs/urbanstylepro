package com.urbanstyle.order.ServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.DTO.AffiliateComissionDTO;
import com.anaadihsoft.common.DTO.OrderUiListingDTO;
import com.anaadihsoft.common.DTO.ReturnUiListDTO;
import com.anaadihsoft.common.external.Filter;

import io.micrometer.core.instrument.util.StringUtils;

@Repository("AffiliateDaoBean")
public class AffiliateDaoImpl implements AffiliateDao{

	@Autowired
	private EntityManager entityManager;
	
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return this.entityManager;
	}
	
	@Override
	public List<OrderUiListingDTO> getOrderForAffialite(long affiliateId, Filter filter) {
		Session session = entityManager.unwrap(Session.class);
		String query = "Select new com.anaadihsoft.common.DTO.OrderUiListingDTO(u.orderProd) FROM AffiliateCommisionOrder u where  u.affiliateId.id = :affiliateId  AND u.returnId=NULL ";
		if(StringUtils.isNotBlank(filter.getDateRange())) {
			String[] dates=filter.getDateRange().split(",");
			Date startDate= new Date(Long.parseLong(dates[0]));
			Date endDate = new Date(Long.parseLong(dates[1]));
			query += " and orderdate between '"+startDate+"' and '"+endDate+"' ";
		}
		if(StringUtils.isNotBlank(filter.getSortingField())) {
			query += " order by "+filter.getSortingField()+" "+filter.getSortingDirection();
		}
		
		Query queryManager = session.createQuery(query);
		queryManager.setParameter("affiliateId", affiliateId);
		queryManager.setFetchSize(filter.getLimit());
		queryManager.setFirstResult(filter.getOffset());
		List<OrderUiListingDTO> returnList = new ArrayList<OrderUiListingDTO>();
		returnList = queryManager.list();
		
		return returnList;
	}

	@Override
	public List<ReturnUiListDTO> getReturnByAffiliate(long affiliateId, Filter filter) {
		Session session = entityManager.unwrap(Session.class);
		String query = "Select new com.anaadihsoft.common.DTO.ReturnUiListDTO(u.returnId) FROM AffiliateCommisionOrder u where  u.affiliateId.id =:affiliateId and u.returnId!=NULL";
		if(StringUtils.isNotBlank(filter.getDateRange())) {
			String[] dates=filter.getDateRange().split(",");
			Date startDate= new Date(Long.parseLong(dates[0]));
			Date endDate = new Date(Long.parseLong(dates[1]));
			query += " and orderdate between '"+startDate+"' and '"+endDate+"' ";
		}
		if(StringUtils.isNotBlank(filter.getSortingField())) {
			query += " order by "+filter.getSortingField()+" "+filter.getSortingDirection();
		}
		
		Query queryManager = session.createQuery(query);
		queryManager.setParameter("affiliateId", affiliateId);
		queryManager.setFetchSize(filter.getLimit());
		queryManager.setFirstResult(filter.getOffset());
		List<ReturnUiListDTO> returnList = new ArrayList<ReturnUiListDTO>();
		returnList = queryManager.list();
		
		return returnList;
	}

	@Override
	public List<AffiliateComissionDTO> getTotalComissionGroupByProduct(long affiliateId, Filter filter) {
		Session session = entityManager.unwrap(Session.class);
		String query = "Select new com.anaadihsoft.common.DTO.AffiliateComissionDTO(sum(u.commision),u.prodvarid) FROM AffiliateCommisionOrder u where  u.affiliateId.id =:affiliateId   AND u.returnId=NULL ";
		if(StringUtils.isNotBlank(filter.getDateRange())) {
			String[] dates=filter.getDateRange().split(",");
			Date startDate= new Date(Long.parseLong(dates[0]));
			Date endDate = new Date(Long.parseLong(dates[1]));
			query += " and orderdate between '"+startDate+"' and '"+endDate+"' ";
		}
		query +=" group by u.prodvarid";
		if(StringUtils.isNotBlank(filter.getSortingField())) {
			query += " order by "+filter.getSortingField()+" "+filter.getSortingDirection();
		}
		Query queryManager = session.createQuery(query);
		queryManager.setParameter("affiliateId", affiliateId);
		queryManager.setFetchSize(filter.getLimit());
		queryManager.setFirstResult(filter.getOffset());
		List<AffiliateComissionDTO> returnList = new ArrayList<AffiliateComissionDTO>();
		returnList = queryManager.list();
		
		return returnList;
	}

	@Override
	public List<OrderUiListingDTO> getOrderForAffialiteAndStatus(long affiliateId, String status, Filter filter) {
		Session session = entityManager.unwrap(Session.class);
		String query = "Select new com.anaadihsoft.common.DTO.OrderUiListingDTO(u.orderProd) FROM AffiliateCommisionOrder u where  u.affiliateId.id =:affiliateId  and u.status=:status AND u.returnId=NULL ";
		if(StringUtils.isNotBlank(filter.getDateRange())) {
			String[] dates=filter.getDateRange().split(",");
			Date startDate= new Date(Long.parseLong(dates[0]));
			Date endDate = new Date(Long.parseLong(dates[1]));
			query += " and orderdate between '"+startDate+"' and '"+endDate+"' ";
		}
		query +=" group by u.prodvarid";
		if(StringUtils.isNotBlank(filter.getSortingField())) {
			query += " order by "+filter.getSortingField()+" "+filter.getSortingDirection();
		}
		Query queryManager = session.createQuery(query);
		queryManager.setParameter("affiliateId", affiliateId);
		queryManager.setParameter("status", status);
		queryManager.setFetchSize(filter.getLimit());
		queryManager.setFirstResult(filter.getOffset());
		List<OrderUiListingDTO> returnList = new ArrayList<OrderUiListingDTO>();
		returnList = queryManager.list();
		
		return returnList;
	}

}
