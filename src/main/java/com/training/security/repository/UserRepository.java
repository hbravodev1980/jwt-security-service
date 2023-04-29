package com.training.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.training.security.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

}
