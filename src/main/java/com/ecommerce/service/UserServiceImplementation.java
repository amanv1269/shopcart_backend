package com.ecommerce.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecommerce.Exception.UserException;
import com.ecommerce.config.JwtProvider;
import com.ecommerce.model.User;
import com.ecommerce.reposatory.UserReposatory;

@Service
public class UserServiceImplementation implements UserService {

	private UserReposatory userReposatory;
	private JwtProvider jwtProvider;

	public UserServiceImplementation(UserReposatory userReposatory, JwtProvider jwtProvider) {

		this.userReposatory = userReposatory;
		this.jwtProvider = jwtProvider;
	}

	@Override
	public User findUserById(Long id) throws UserException {
		Optional<User> user = userReposatory.findById(id);
		if (user != null) {
			return user.get();
		}
		throw new UserException("User not Found" + id);
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {

		String email = jwtProvider.getEmailFromToken(jwt);
		User user = userReposatory.findByEmail(email);
		if (user == null) {
			throw new UserException("User Email Not Found");
		}
		user.getEmail();
		return user;
	}

}
