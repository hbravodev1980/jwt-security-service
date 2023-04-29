package com.training.security;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.training.security.entity.Role;
import com.training.security.entity.User;
import com.training.security.repository.RoleRepository;
import com.training.security.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {

	private final UserRepository userRepository;

	private final RoleRepository roleRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	
	@Override
	public void run(String... args) throws Exception {
		if (roleRepository.count() == 0) {
			roleRepository.saveAll(List.of(
				new Role("ADMIN"),
				new Role("USER")
			));
		}

		if (userRepository.count() == 0) {
			Role admin = roleRepository.findByName("ADMIN").get();
			Role user = roleRepository.findByName("USER").get();

			userRepository.saveAll(List.of(
				new User("Harry", "Bravo", "hbravo@gmail.com", passwordEncoder.encode("pwd12345678"), List.of(admin)),
				new User("Martin", "Vera", "mvera@gmail.com", passwordEncoder.encode("pwd12345678"), List.of(user))
			));
		}
	}

}
