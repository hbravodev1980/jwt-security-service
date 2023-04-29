package com.training.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

	@NotBlank(message = "should enter an email")
	@Email(message = "should enter an email with a valid format")
	private String email;

	@NotBlank(message = "should enter an password")
	private String password;

}
