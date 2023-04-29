package com.training.security.handler;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.training.security.exceptions.RoleNotFoundException;

@RestControllerAdvice
public class ApplicationExceptionHandler {

	private final static String URI_HTTP_STATUS = "https://developer.mozilla.org/es/docs/Web/HTTP/Status";

	@ExceptionHandler(Exception.class)
	public ProblemDetail handleAllException(Exception ex) {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
			HttpStatus.INTERNAL_SERVER_ERROR,
			ex.getMessage()
		);
		problemDetail.setType(URI.create(URI_HTTP_STATUS));
		return problemDetail;
	}

	@ExceptionHandler(RoleNotFoundException.class)
	public ProblemDetail handleRoleNotFoundException(RoleNotFoundException ex) {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
			HttpStatus.NOT_FOUND,
			ex.getMessage()
		);
		problemDetail.setType(URI.create(URI_HTTP_STATUS));
		return problemDetail;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ProblemDetail handleInvalidArgument(MethodArgumentNotValidException ex) {
		Map<String, String> errorMap = new HashMap<>();

		ex.getBindingResult().getFieldErrors().stream()
			.forEach(error -> errorMap.put(
				error.getField(),
				error.getDefaultMessage())
			);

		ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
		problemDetail.setType(URI.create(URI_HTTP_STATUS));
		problemDetail.setProperty("errors", errorMap);

		return problemDetail;
	}

}
