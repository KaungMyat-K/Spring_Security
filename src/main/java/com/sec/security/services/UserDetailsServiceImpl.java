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
		Employee employee = employeeRepository.findByPhone(username)
									.orElseThrow(() -> new BadRequestException("Employee does not have with this phone number : " + username));
		return UserDetailsImpl.build(employee);
	}

}
