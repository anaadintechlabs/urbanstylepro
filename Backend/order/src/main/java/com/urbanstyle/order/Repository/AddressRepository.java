package com.urbanstyle.order.Repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.anaadihsoft.common.master.Address;

public interface AddressRepository extends PagingAndSortingRepository<Address,Long>{

	List<Address> findByUserId(long vendorId);

}
