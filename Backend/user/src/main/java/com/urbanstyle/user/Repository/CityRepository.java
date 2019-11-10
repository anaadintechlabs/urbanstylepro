package com.urbanstyle.user.Repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.anaadihsoft.common.master.City;

public interface CityRepository extends PagingAndSortingRepository<City,Long> {

	List<City> findByStateId(long stateId);

}
