package com.sec.service;

import com.sec.entity.Employee;
import com.sec.request.SignInRequest;

public interface IEmployeeService extends IBaseService<Employee>{

	Object signIn(SignInRequest req);
}
