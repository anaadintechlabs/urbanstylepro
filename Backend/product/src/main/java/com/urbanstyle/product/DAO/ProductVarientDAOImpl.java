package com.urbanstyle.product.DAO;

import org.springframework.stereotype.Repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.DTO.FilterDTO;
import com.anaadihsoft.common.DTO.InventorySearchDTO;
import com.anaadihsoft.common.master.ProductVariant;

import io.micrometer.core.instrument.util.StringUtils;

@Repository("ProductVarientDAOImpl")
public class ProductVarientDAOImpl implements ProductVarientDAO {


	@Autowired
	private EntityManager entityManager;
	    	
	
	protected EntityManager getEntityManager() {
		return this.entityManager;
	}


	@Override
	public List<ProductVariant> findInProductVarient(FilterDTO filterDTO) {
		
		 int limit = filterDTO.getLimit();
		 String offset = filterDTO.getOffset();
		 String searchString = filterDTO.getSearchString();
		 String priceRange = filterDTO.getPriceRange();
		 String rating = filterDTO.getRating();
		 String sortingByDate = filterDTO.getSortingByDate();
		 String SortingByPrice = filterDTO.getSortingByPrice();
		 
		 Session session = entityManager.unwrap(Session.class);
		 
		 // Logic for filter in product varient
		 
		 String query = "FROM ProductVariant pv ";
		 
		 if(StringUtils.isNotBlank(rating)) {
			 query += " left join ProductReview pr on pv.productId = pr.product.productId";			 
		 }
		
		 query += "where (pv.prodDesc like :prodDesc or pv.prodName like :prodName)";
		 
		 if(StringUtils.isNotBlank(priceRange)) {
			 query += " and pv.displayPrice between "+priceRange.split(",")[0]+" and "+priceRange.split(",")[1]+" ";
		 }
		 
		 if(StringUtils.isNotBlank(rating)) {
			 query += " and pr.rating  = :rating ";
		 }
		
		 if(StringUtils.isNotBlank(sortingByDate) || StringUtils.isNotBlank(SortingByPrice)) {
			 query += " order by  ";
		 }

		 if(StringUtils.isNotBlank(sortingByDate)) {
			 query += " pv.createdDate " + sortingByDate;
		 }

		 if(StringUtils.isNotBlank(SortingByPrice)) {
			 query += " pv.displayPrice " + SortingByPrice;
		 }
		 System.out.println("Query is "+query);
		 Query managerQuery =  session.createQuery(query);
		 managerQuery.setParameter("prodDesc",  "%"+searchString+"%");
		 managerQuery.setParameter("prodName",  "%"+searchString+"%");
		 if(StringUtils.isNotBlank(rating)) {
			 managerQuery.setParameter("rating", rating);
		 }
		 
		 List<ProductVariant> returnData = managerQuery.list();
		 System.out.println("output of query is "+returnData);
		 // Now check categry if data not exist i varient table 
		 
		 
		 if(returnData == null || returnData.size() == 0) {
			 query = "";
			 query += "FROM ProductVariant where categoryId in(select categoryId FROM Category where categoryName like :categoryName )";
		 
		 
		 managerQuery =  session.createQuery(query);
		 managerQuery.setParameter("categoryName", "%"+searchString+"%");
		 }
		 returnData = managerQuery.list();
		 System.out.println("output of query 2 is "+returnData);
		return returnData;
	}
	
	
	@Override
	public List<ProductVariant> searchInventory(InventorySearchDTO inventorySearchDTO) {
		String search = inventorySearchDTO.getSearch();
		 long grtPrice = inventorySearchDTO.getGrtPrice(); 
		 long lessPrice = inventorySearchDTO.getLessPrice();
		 long grtDate = inventorySearchDTO.getGrtDate();
		 long lessDate = inventorySearchDTO.getLessDate();
		 String status = inventorySearchDTO.getStatus();
		 String sortField = inventorySearchDTO.getSortField();
		 String sortdir = inventorySearchDTO.getSortdir();
		 int limit  = inventorySearchDTO.getLimit();
		 int offset = inventorySearchDTO.getOffset();
		 
		 Session session = entityManager.unwrap(Session.class);
		 
		 String query = "FROM ProductVariant pv WHERE";
		 
		 if(StringUtils.isNotBlank(search)) {
			 query += " (pv.sku like :sku or pv.prodName like :sku or pv.prodDesc like :sku)  ";
		 }
		 
		 if(Long.valueOf(grtPrice) != null) {
			 query += " and pv.actualPrice > :grtPrice";
		 }
		 
		 if(Long.valueOf(lessPrice) != null) {
			 query += " and pv.actualPrice < :lessPrice";
		 }
		 
		 if(Long.valueOf(grtDate) != null) {
			 query += " and pv.createdDate > :grtDate";
		 }
		 
		 if(Long.valueOf(lessDate) != null) {
			 query += " and pv.createdDate < :lessDate";		 
		}
		 
		 if(StringUtils.isNotBlank(status)) {
			 query += " and pv.status = :status";
		 }
		 
		 if(StringUtils.isNotBlank(sortField)) {
			 query += " Order by :sortField";
		 }
		 
		 if(StringUtils.isNotBlank(sortdir)) {
			 query += " "+sortdir;
		 }
		 Query managerQuery =  session.createQuery(query);
		 if(StringUtils.isNotBlank(search)) {
			 managerQuery.setParameter("sku",  "%"+search+"%");			 
		 }
		 if(Long.valueOf(grtPrice) != null) {
			 managerQuery.setParameter("grtPrice",  grtPrice);			
		 }
		 if(Long.valueOf(lessPrice) != null) {
			 managerQuery.setParameter("lessPrice",  lessPrice);			
		 }
		 if(Long.valueOf(grtDate) != null) {
			 managerQuery.setParameter("grtDate",  grtDate);			
		 }
		 if(Long.valueOf(grtPrice) != null) {
			 managerQuery.setParameter("lessDate",  lessDate);			
		 }
		 if(StringUtils.isNotBlank(status)) {
			 managerQuery.setParameter("status",  status);
		 }
		 List<ProductVariant> returnData = managerQuery.list();
		return returnData;
	}



}
