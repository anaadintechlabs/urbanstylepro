package com.anaadihsoft.user.Repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.anaadihsoft.common.master.State;

public interface StateRepository extends PagingAndSortingRepository<State, Long> {

	List<State> findByCountryId(long countryId);

}
