package com.anaadihsoft.user.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.anaadihsoft.common.master.Country;

public interface CountryRepository extends PagingAndSortingRepository<Country,Long>,JpaRepository<Country, Long>{

}
