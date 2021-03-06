package com.urbanstyle.product.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.master.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

	User findByUserType(String string);

}