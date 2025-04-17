package com.paymentchain.customer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.paymentchain.customer.common.StandarizeApiExceptionResponse;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@RestControllerAdvice
public class ApiExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleUnknownHostExpection(Exception ex) {
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new StandarizeApiExceptionResponse("Técnico", "Input Output Error", "1024", ex.getMessage()));
	}
}
