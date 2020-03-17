package com.urbanstyle.order.Repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadihsoft.common.master.User;
import com.anaadihsoft.common.master.UserWallet;

@Repository
public interface UserWalletRepo extends PagingAndSortingRepository<UserWallet, Long> {

	UserWallet findByUserId(Long long1);

	List<UserWallet> findTop5ByUserUserType(String userType);

}
