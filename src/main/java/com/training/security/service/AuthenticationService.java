package com.training.security.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.training.security.dto.AuthenticationRequest;
import com.training.security.dto.AuthenticationResponse;
import com.training.security.dto.RegisterRequest;
import com.training.security.entity.Role;
import com.training.security.entity.User;
import com.training.security.exceptions.RoleNotFoundException;
import com.training.security.exceptions.UserEmailExistException;
import com.training.security.repository.RoleRepository;
import com.training.security.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final UserRepository userRepository;
	
	private final RoleRepository roleRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	private final JwtService jwtService;
	
	private final AuthenticationManager authenticationManager;

	public AuthenticationResponse register(RegisterRequest request) {
		Optional<User> user = userRepository.findByEmail(request.getEmail());
		if (user.isPresent()) {
			throw new UserEmailExistException("There is a user with the same email");
		}

		List<Role> authorities = roleRepository.findByNameIn(request.getAuthorities());
		if (authorities.size() != request.getAuthorities().size()) {
			throw new RoleNotFoundException("Role not found");
		}

		User userSave = User.builder()
				.firstname(request.getFirstname())
				.lastname(request.getLastname())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.authorities(roleRepository.findByNameIn(request.getAuthorities()))
				.build();

		userRepository.save(userSave);
		String token = jwtService.generateToken(userSave);
		return AuthenticationResponse.builder().token(token).build();
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		Optional<User> user = userRepository.findByEmail(request.getEmail());
		if (user.isEmpty()) {
			throw new UsernameNotFoundException("User not found");
		}

		authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(
				request.getEmail(),
				request.getPassword()
			)
		);
		String token = jwtService.generateToken(user.get());
		return AuthenticationResponse.builder().token(token).build();
	}

}
