package com.sec.exception;

import org.springframework.http.ResponseEntity;

public class BadRequestException extends MBCBaseException{
	
	private static final long serialVersionUID = 1L;

	public BadRequestException(String msg) {
		super(msg);
	}
	
	public ResponseEntity<Object> response(){
		return badRequestResponse();
	}
}