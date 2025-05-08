package com.sec.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sec.entity.Employee;
import com.sec.exception.BadRequestException;
import com.sec.exception.MBCBaseException;
import com.sec.repository.EmployeeRepository;
import com.sec.request.SignInRequest;
import com.sec.service.IEmployeeService;
import com.sec.utility.Converter;

@Service
public class EmployeeServiceImpl implements IEmployeeService{
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private PasswordEncoder encoder;

	@Override
	public Employee saveData(Employee t) throws MBCBaseException {
		return employeeRepository.save(t);
	}

	@Override
	public Employee updateData(Employee t) throws MBCBaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteDataById(long id) throws MBCBaseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Employee getDataById(long id) throws MBCBaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Employee> getAllDatas() throws MBCBaseException {
		return employeeRepository.findAll();
	}

	
	
	@Override
	public Object signIn(SignInRequest req) {
		if(employeeRepository.existsByPhone(req.getPhone())){
			new BadRequestException("Employee already exist with this phone number : " + req.getPhone());
		}
		req.setPasscode(encoder.encode(req.getPasscode()));
		saveData(Converter.getEmployee(req));
		return "successfully created";
	}

}
