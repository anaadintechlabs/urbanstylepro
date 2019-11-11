package com.urbanstyle.user.Service;

import org.springframework.stereotype.Service;

@Service
public interface UserService {

	String getCurrentStatusOfUser(long userId);

}
