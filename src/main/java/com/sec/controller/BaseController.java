package com.sec.controller;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.sec.response.DefaultResponse;

abstract class BaseController {
	

	protected ResponseEntity<Object> notFoundResponse(Object o) {
		return new ResponseEntity<Object>(o, HttpStatus.NOT_FOUND);
	}

	protected ResponseEntity<Object> badRequestResponse(String s) {
		DefaultResponse res = new DefaultResponse();
		res.setMessage(s);
		return new ResponseEntity<Object>(res, HttpStatus.BAD_REQUEST);
	}

	protected ResponseEntity<Object> deleteSuccessResponse(String s) {
		DefaultResponse res = new DefaultResponse();
		res.setMessage(s);
		return new ResponseEntity<Object>(res, HttpStatus.OK);
	}

	protected ResponseEntity<Object> successResponse(Object o) {
		return new ResponseEntity<Object>(o, HttpStatus.OK);
	}

	protected void logInfo(String msg) {
		System.out.println(msg);
	}

	protected void logError(Throwable t, String msg) {
		t.printStackTrace();
		System.out.println(msg);
	}
}