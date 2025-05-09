package com.sec.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sec.exception.MBCBaseException;
import com.sec.request.LoginRequest;
import com.sec.response.DefaultResponse;
import com.sec.response.LoginResponse;
import com.sec.service.IAuthenticationService;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
public class AuthenticationController extends BaseController {
	
	@Autowired
	private IAuthenticationService authenticationService;


	@PostMapping(path = "/login",produces = MediaType.APPLICATION_JSON_VALUE)
	public synchronized ResponseEntity<Object> login(@RequestBody LoginRequest req,HttpServletResponse response) {
		try {
			LoginResponse res = authenticationService.authenticateUser(req,response);
			return successResponse(res);
		} catch (MBCBaseException e) {
			return e.response();
		}	
	}
	
	@PostMapping(path = "/logout")
	public synchronized ResponseEntity<Object> logout(HttpServletResponse response) {
		try {
			authenticationService.logout(response);
			return successResponse(new DefaultResponse("logout successfully"));
		} catch (MBCBaseException e) {
			return e.response();
		}	
	}

	@PostMapping(path = "/refresh",produces = MediaType.APPLICATION_JSON_VALUE)
	public synchronized ResponseEntity<Object> refreshToken(@RequestBody LoginRequest req) {
		try {
			return null;
		} catch (MBCBaseException e) {
			return e.response();
		}	
	}
	
}
