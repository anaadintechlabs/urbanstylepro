package com.urbanstyle.product.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.master.AttributeMaster;
import com.anaadihsoft.common.master.Category;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category,Long>{

	

	List<Category> findByStatusAndParentCategory(int active,Category category, Pageable pagable);

	List<Category> findByStatusAndParentCategoryCategoryId(int active, long categoryId);

	@Query(value="Select t from AttributeMaster t left join CategoryAttributeMapping ca on t.id = ca.attributeMaster where ca.category = ?1")
	//@Query(value="select new com.urbanstyle.AttributeMaster(c) from CategoryAttributeMapping c where c.category.id=?! ")
	List<AttributeMaster> fetchallAttributeDtail(long categoryId);

	@Query(value="update Category wl set wl.status =?2 where wl.categoryId =?1 ")
	@Modifying
	@Transactional
	void changeStatusOfCategory(long categoryId, int status);

	//boolean existsByCategoryCode(String categoryCode);

}
