package com.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecommerce.model.User;
import com.ecommerce.reposatory.UserReposatory;

@Service
public class CustomerUserServiceImplementation implements UserDetailsService {

	private UserReposatory userReposatory;

	public CustomerUserServiceImplementation(UserReposatory userReposatory) {
		this.userReposatory = userReposatory;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = this.userReposatory.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not Found");
		}
		List<GrantedAuthority> authorities = new ArrayList<>();
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);

	}

}
