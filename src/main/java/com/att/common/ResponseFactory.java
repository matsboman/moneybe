package com.att.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseFactory {
	private ResponseFactory() {
	}

	public static ResponseEntity<Object> response(String message, HttpStatus status) {
		Map<String, String> response = new HashMap<>();
		response.put("message", message);
		return new ResponseEntity<>(response, status);
	}

	public static ResponseEntity<Object> response(Object object, HttpStatus status) {
		return new ResponseEntity<>(object, status);
	}

	public static ResponseEntity<Object> response(HttpStatus status) {
		return new ResponseEntity<>(status);
	}
}
