package com.sec.service;

import javax.servlet.http.HttpServletResponse;

import com.sec.entity.Employee;
import com.sec.request.LoginRequest;
import com.sec.response.LoginResponse;

public interface IAuthenticationService extends IBaseService<Employee>{

	public Employee getEmployeeByPhone(String phone);
	
	public LoginResponse authenticateUser(LoginRequest req,HttpServletResponse res);
	
	public void logout(HttpServletResponse res);
	
}
