package com.sec.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sec.entity.Employee;
import com.sec.exception.BadRequestException;
import com.sec.repository.EmployeeRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String phone;
		String id = null;
		if (username.contains("|")) {
			String[] parts = username.split("\\|");
			if (parts.length != 2) {
				throw new BadRequestException("Invalid username format");
			}
			id = parts[0];
			phone = parts[1];
		} else {
			phone = username;
		}
		Employee employee = employeeRepository.findByPhone(phone)
								.orElseThrow(() -> new BadRequestException("No employee found with phone number: " + phone));

		if (id != null && !id.equals(String.valueOf(employee.getEmployeeKey()))) {
			throw new BadRequestException("Employee ID mismatch: " + id);
		}
		return UserDetailsImpl.build(employee);
	}

}
