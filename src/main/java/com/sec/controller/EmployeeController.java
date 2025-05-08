package com.sec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sec.exception.MBCBaseException;
import com.sec.request.SignInRequest;
import com.sec.service.IEmployeeService;

@RestController
@RequestMapping("/user")
public class EmployeeController extends BaseController{
    
    @Autowired
	private IEmployeeService employeeService;

    @PostMapping(path = "/create",produces = MediaType.APPLICATION_JSON_VALUE)
	public synchronized ResponseEntity<Object> createUser(@RequestBody SignInRequest req) {
		try {
			
			return successResponse(employeeService.signIn(req));
		} catch (MBCBaseException e) {
			return e.response();
		}	
	}

    @GetMapping(path = "/getUsers",produces = MediaType.APPLICATION_JSON_VALUE)
	public synchronized ResponseEntity<Object> getUsers() {
		try {
			
			return successResponse(employeeService.getAllDatas());
		} catch (MBCBaseException e) {
			return e.response();
		}	
	}
}
