package com.urbanstyle.order.Repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.master.UserOrder;
import com.anaadihsoft.common.master.UserOrderProducts;

@Repository
public interface UserOrderProductRepository extends PagingAndSortingRepository<UserOrderProducts, Long> {

	List<UserOrderProducts> findByUserOrderId(long id);

}
