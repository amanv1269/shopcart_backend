package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.Exception.UserException;
import com.ecommerce.config.JwtProvider;
import com.ecommerce.model.Cart;
import com.ecommerce.model.User;
import com.ecommerce.reposatory.UserReposatory;
import com.ecommerce.request.LoginRequest;
import com.ecommerce.response.AuthResponse;
import com.ecommerce.service.CartService;
import com.ecommerce.service.CustomerUserServiceImplementation;

@RestController
@RequestMapping("auth")
public class AuthController {

	private UserReposatory userReposatory;
	private JwtProvider jwtProvider;
	private PasswordEncoder passwordEncoder;
	private CustomerUserServiceImplementation customerUserServiceImplementation;
	@Autowired
	private CartService cartService;

	public AuthController(UserReposatory userReposatory, PasswordEncoder passwordEncoder, JwtProvider jwtProvider,
			CustomerUserServiceImplementation customerUserServiceImplementation) {
		this.userReposatory = userReposatory;
		this.jwtProvider = jwtProvider;
		this.passwordEncoder = passwordEncoder;
		this.customerUserServiceImplementation = customerUserServiceImplementation;
	}

	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {

		String email = user.getEmail();
		String password = user.getPassword();
		String firstName = user.getFirstName();
		String lastName = user.getLastName();

		User isUserExist = userReposatory.findByEmail(email);

		if (isUserExist != null) {

			throw new UserException("This Email Is Allready Userd");

		}
		password = passwordEncoder.encode(password);

		User createUser = new User();
		createUser.setEmail(email);
		createUser.setPassword(password);
		createUser.setFirstName(firstName);
		createUser.setLastName(lastName);

		User savedUser = userReposatory.save(createUser);
		Cart cart = cartService.createCart(savedUser);

		Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(),
				savedUser.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtProvider.token(authentication);
		AuthResponse authResponse = new AuthResponse();
		authResponse.setJwt(token);
		authResponse.setMessage("signup success");

		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
	}

	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> userLoginHandler(@RequestBody LoginRequest loginrequest) {
		String username = loginrequest.getEmail();
		String password = loginrequest.getPassword();

		Authentication authentication = authenticate(username, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtProvider.token(authentication);
		AuthResponse authResponse = new AuthResponse();
		authResponse.setJwt(token);
		authResponse.setMessage("signin success");
		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);

	}

	private Authentication authenticate(String username, String password) {
		UserDetails userDetails = customerUserServiceImplementation.loadUserByUsername(username);
		if (userDetails == null) {
			throw new BadCredentialsException("...invalid Username");
		}
		if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("....invalid Password");
		}
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}

}
