package com.sec.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.sec.response.DefaultResponse;

public abstract class MBCBaseException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public MBCBaseException(String msg) {
		super(msg);
	}
	
	protected ResponseEntity<Object> badRequestResponse(){
		DefaultResponse res = new DefaultResponse();
		res.setMessage(getMessage());
		return new ResponseEntity<Object>(res,HttpStatus.BAD_REQUEST);
	}
	
	protected ResponseEntity<Object> notFoundResponse(){
		DefaultResponse res = new DefaultResponse();
		res.setMessage(getMessage());
		return new ResponseEntity<Object>(res,HttpStatus.NOT_FOUND);
	}
	
	public abstract ResponseEntity<Object> response();
}