package com.training.security.service;

import java.util.List;

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
		List<Role> authorities = roleRepository.findByNameIn(request.getAuthorities());

		if (authorities.size() != request.getAuthorities().size()) {
			throw new RoleNotFoundException("Role not found");
		}

		var user = User.builder()
				.firstname(request.getFirstname())
				.lastname(request.getLastname())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.authorities(roleRepository.findByNameIn(request.getAuthorities()))
				.build();

		userRepository.save(user);
		var token = jwtService.generateToken(user);
		return AuthenticationResponse.builder().token(token).build();
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(
				request.getEmail(),
				request.getPassword()
			)
		);

		var user = userRepository.findByEmail(request.getEmail())
			.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		var token = jwtService.generateToken(user);
		return AuthenticationResponse.builder().token(token).build();
	}

}
