package com.sec.response;

public class LoginResponse {

	private String accessToken;
	private String refreshToken;
	private String employeeKey;
	
	
	public LoginResponse(String accessToken,String refreshToken, String employeeKey) {
		super();
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.employeeKey = employeeKey;
	}
	
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getEmployeeKey() {
		return employeeKey;
	}

	public void setEmployeeKey(String employeeKey) {
		this.employeeKey = employeeKey;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	
	
	
	
	
}
