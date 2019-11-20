package com.anaadihsoft.order.Repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.master.UserOrder;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<UserOrder, Long> {

	List<UserOrder> findByUserId(long userId, Pageable pagable);

}
