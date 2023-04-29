package com.training.security.dto;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

	@NotBlank(message = "should enter a firstname")
	private String firstname;

	@NotBlank(message = "should enter a lastname")
	private String lastname;

	@NotBlank(message = "should enter an email")
	@Email(message = "should enter an email with a valid format")
	private String email;

	@NotBlank(message = "should enter an password")
	@Size(min = 8, message = "must enter a password of at least 8 characters")
	private String password;

	@NotEmpty(message = "should enter authorities")
	private List<String> authorities;

}
