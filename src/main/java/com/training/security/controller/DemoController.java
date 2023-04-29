package com.training.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo")
public class DemoController {

	@GetMapping("/hello-admin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> sayHelloAdmin() {
		return ResponseEntity.ok("Hello Admin");
	}

	@GetMapping("/hello-user")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<String> sayHelloUser() {
		return ResponseEntity.ok("Hello User");
	}

	@GetMapping("/hello-both")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<String> sayHelloBoth() {
		return ResponseEntity.ok("Hello Admin o User");
	}

}
