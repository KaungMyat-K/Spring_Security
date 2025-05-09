package com.sec.response;

public class LoginResponse {

	private String employeeKey;
	
	
	public LoginResponse( String employeeKey) {
		super();

		this.employeeKey = employeeKey;
	}

	public String getEmployeeKey() {
		return employeeKey;
	}

	public void setEmployeeKey(String employeeKey) {
		this.employeeKey = employeeKey;
	}
	
}
