package com.urbanstyle.user.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anaadihsoft.common.DTO.PasswordDTO;
import com.anaadihsoft.common.external.Filter;
import com.anaadihsoft.common.master.User;
import com.urbanstyle.user.util.CustomException;

@Service
public interface UserService {

	String getCurrentStatusOfUser(long userId);

	List<User> getAllUsers(Filter filter, String userType);

	User updateUser(User user);

	Object changeUserPassword(long userId, PasswordDTO passwordDTO) throws CustomException;

	User getUserById(long userId);

}
