package com.urbanstyle.order.Repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.master.Category;

@Repository
public interface CategoryRepo extends PagingAndSortingRepository<Category, Long> {

}
