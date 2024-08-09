package com.ecommerce.service;

import com.ecommerce.Exception.UserException;
import com.ecommerce.model.User;

public interface UserService {

	public User findUserById(Long id) throws UserException;

	public User findUserProfileByJwt(String jwt) throws UserException;
}
