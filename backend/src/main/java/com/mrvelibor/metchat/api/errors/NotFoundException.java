package com.mrvelibor.metchat.api.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

	public NotFoundException() {}
	
	public NotFoundException(String message) {
		super(message);
	}

}
