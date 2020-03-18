package com.urbanstyle.product.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.master.ShortCodeGenerator;

@Repository
public interface ShortCodeGeneratorRepository extends PagingAndSortingRepository<ShortCodeGenerator, Long>{

}