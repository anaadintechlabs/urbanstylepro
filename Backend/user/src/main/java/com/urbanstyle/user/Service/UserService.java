package com.urbanstyle.user.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.User;

@Service
public interface UserService {

	String getCurrentStatusOfUser(long userId);

	List<User> getAllUsers(Filter filter, String userType);

}
