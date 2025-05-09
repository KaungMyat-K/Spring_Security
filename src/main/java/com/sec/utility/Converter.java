package com.sec.utility;

import com.sec.entity.Employee;
import com.sec.request.SignInRequest;
import com.sec.response.LoginResponse;

public class Converter {
	

	public static LoginResponse getLoginRes(long employeeKey) {
		return new LoginResponse(String.valueOf(employeeKey));
	}
	
	public static Employee getEmployee(SignInRequest req) {
		Employee  employee = new Employee();
		employee.setEmployeeId(req.getEmployeeID());
		employee.setEmployeeName(req.getEmployeeName());
		employee.setPasscode(req.getPasscode());
		employee.setPhone(req.getPhone());
		employee.setGender(req.getGender());
		employee.setEducation(req.getEducation());
		employee.setEmail(req.getEmail());
		employee.setDob(req.getDob());
		return employee;
	}
}
