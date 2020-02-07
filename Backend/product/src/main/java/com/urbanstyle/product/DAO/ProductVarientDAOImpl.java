package com.urbanstyle.product.DAO;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.DTO.FilterDTO;
import com.anaadihsoft.common.DTO.HomePageFilterDTO;
import com.anaadihsoft.common.DTO.InventorySearchDTO;
import com.anaadihsoft.common.DTO.ProductVariantDTO;
import com.anaadihsoft.common.master.CategoryAttributeMapping;
import com.anaadihsoft.common.master.ProductAttributeDetails;
import com.anaadihsoft.common.master.ProductVariant;
import com.urbanstyle.product.repository.CategoryVariationRepository;
import com.urbanstyle.product.repository.ProductAttributeDetailsRepository;
import com.urbanstyle.product.service.ProductVarientRepository;

import io.micrometer.core.instrument.util.StringUtils;

@Repository("ProductVarientDAOImpl")
public class ProductVarientDAOImpl implements ProductVarientDAO {


	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private CategoryVariationRepository catAttRep;
	
	@Autowired
	private ProductAttributeDetailsRepository prodAttrMap; 
	
	@Autowired
	private ProductVarientRepository prdVarRepo;
	    	
	
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


	@Override
	public HomePageFilterDTO applyHomePageFilter(String searchString) {

		HomePageFilterDTO homePageDTO = new HomePageFilterDTO();
		
		Session session = entityManager.unwrap(Session.class);
		HashMap<String, List<Long>> allVarAndCat = getAllVarientsAndCategories(searchString);
		
		List<Long> ListCat = new ArrayList<>();
		
		ListCat = allVarAndCat.get("CATEGORIES");
		
		List<Long> ListVarId = new ArrayList<>();
		
		ListVarId = allVarAndCat.get("VARIENTS");
		
		// now find all attributes id in cat attribute table
		List<CategoryAttributeMapping> catAttr= new ArrayList<>();
		if(ListCat!=null && !ListCat.isEmpty())
		{
		catAttr= catAttRep.findAllAttribute(ListCat);
		}
		HashMap<Long, String> attrVal = new HashMap<>(); 
		for(CategoryAttributeMapping cam : catAttr) {
			attrVal.put(cam.getAttributeMaster().getId(), cam.getAttributeMaster().getVariationName());
		}
		
		Set<Long> allAttributeIdList = attrVal.keySet();
		
		List<ProductAttributeDetails> allattrData = prodAttrMap.findByAttrIdAndVarId(allAttributeIdList,ListVarId);
		
		HashMap<Long,Map<String,  List<String>>> attributeDataMap  = new HashMap<Long,Map<String,  List<String>>>();
		for(ProductAttributeDetails pad : allattrData) {
		  if(attributeDataMap.containsKey(pad.getAttributeMasterId())) {
			  Map<String,  List<String>> oldDataMap =  attributeDataMap.get(pad.getAttributeMasterId());
			  String attrName = attrVal.get(pad.getAttributeMasterId());
			  List<String> oldVal = oldDataMap.get(attrName);
			  oldVal.add(pad.getAttributeValue());
		  }else {
			  List<String> newList = new ArrayList<>();
			  newList.add(pad.getAttributeValue());
			  Map<String,List<String>> newMap = new HashMap<>();
			  String attrName = attrVal.get(pad.getAttributeMasterId());
			  newMap.put(attrName, newList);
				attributeDataMap.put(pad.getAttributeMasterId(), newMap);
		  }
		}
		
		List<ProductVariant> allVarients = new ArrayList<>();
		Iterable<ProductVariant> allIterVar = prdVarRepo.findAllById(ListVarId);
		
		allVarients.forEach(allVarients :: add);
		
		homePageDTO.setAttributeDataMap(attributeDataMap);
		return null;
	}


	private HashMap<String, List<Long>> getAllVarientsAndCategories(String searchString) {
		Session session = entityManager.unwrap(Session.class);
		
		HashMap<String, List<Long>> resultData = new HashMap<>();
		
		List<Long> ListCat = new ArrayList<>();
		
		List<Long> ListVarId = new ArrayList<>();
		 
		 String query = "FROM ProductVariant pv WHERE (pv.sku like :sku or pv.prodName like :sku or pv.prodDesc like :sku) ";
		 Query managerQuery =  session.createQuery(query);
		 managerQuery.setParameter("sku", searchString);
		 
		 List<ProductVariant> returnData = managerQuery.list();
		 for(ProductVariant pv : returnData) {
			 if(!ListCat.contains(pv.getCategoryId())) {
				 ListCat.add(pv.getCategoryId());
			 }
			 if(!ListVarId.contains(pv.getProductVariantId())) {
				 ListVarId.add(pv.getProductVariantId());
			 }
		 }
		 
		 resultData.put("CATEGORIES", ListCat);
		 resultData.put("VARIENTS", ListVarId);
		 
		return resultData;
		 
	}



}
