package com.sec.exception;

import org.springframework.http.ResponseEntity;

public class NotFoundException extends MBCBaseException{
	
	private static final long serialVersionUID = 1L;

	public NotFoundException(String msg) {
		super(msg);
	}
	
	public ResponseEntity<Object> response(){
		return notFoundResponse();
	}
}