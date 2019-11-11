package com.urbanstyle.user.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.anaadihsoft.common.master.Address;

public interface AddressRepository extends PagingAndSortingRepository<Address,Long>{

	List<Address> findByUserId(long userId);

	List<Address> findByUserIdAndStatus(long userId, String active);
	
	boolean existsByUserIdAndStatus(long userId, String active);
	
	
	@Query(value="Update Address a set a.status=?2 where a.id=?1")
	void changeStatusOfAddress(long id, String status);

}
