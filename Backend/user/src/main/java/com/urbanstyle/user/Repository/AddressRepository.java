package com.urbanstyle.user.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.anaadihsoft.common.master.Address;

public interface AddressRepository extends PagingAndSortingRepository<Address,Long>{

	List<Address> findByUserId(long userId);

	List<Address> findByUserIdAndStatus(long userId, int active);
	
	boolean existsByUserIdAndStatus(long userId, int active);
	
	
	@Query(value="Update Address a set a.status=?2 where a.id=?1")
	@Modifying
	@Transactional
	void changeStatusOfAddress(long id, int status);

}
