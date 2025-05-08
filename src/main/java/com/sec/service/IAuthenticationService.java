package com.sec.service;

import javax.servlet.http.HttpServletResponse;

import com.sec.entity.Employee;
import com.sec.request.LoginRequest;
import com.sec.response.LoginResponse;

public interface IAuthenticationService extends IBaseService<Employee>{

	public Employee getEmployeeByPhone(String phone);
	
	public LoginResponse authenticateUser(LoginRequest req);
	
	public void logout(HttpServletResponse res);

	public void setCookies(String accessToken,String refreshToke,HttpServletResponse res);
	
}
