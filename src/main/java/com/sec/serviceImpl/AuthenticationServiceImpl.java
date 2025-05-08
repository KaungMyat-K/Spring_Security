package com.sec.serviceImpl;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sec.entity.Employee;
import com.sec.exception.BadRequestException;
import com.sec.exception.MBCBaseException;
import com.sec.repository.EmployeeRepository;
import com.sec.request.LoginRequest;
import com.sec.response.LoginResponse;
import com.sec.security.services.JwtUtils;
import com.sec.security.services.UserDetailsImpl;
import com.sec.service.IAuthenticationService;
import com.sec.utility.Converter;

@Service
public class AuthenticationServiceImpl implements IAuthenticationService{
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
    AuthenticationManager authenticationManager;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@Value("${app.refreshJWTExpirationMs}")
    private int refreshJWTExpirationMs;
    
    @Value("${app.accessJWTExpirationMs}")
    private int accessJWTExpirationMs;
    
    @Value("${app.env}")
    private String appEnv;
    
    @Value("${app.accessCookieName}")
    private String accessCookieName;
    
    @Value("${app.refreshCookieName}")
    private String refreshCookieName;

	@Override
	public Employee saveData(Employee t) throws MBCBaseException {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public Employee getEmployeeByPhone(String phone) {
			return employeeRepository.findByPhone(phone)
							.orElseThrow(() -> new BadRequestException("Employee does not have with this phone number : " + phone));
	}

	@Override
	public LoginResponse authenticateUser(LoginRequest req) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getPhone(),req.getPasscode()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        String accessToken = jwtUtils.generateJwtToken(userDetailsImpl.getPhone(),"access");
        String refreshToken = jwtUtils.generateJwtToken(userDetailsImpl.getPhone(),"refresh");
		//save refresh token in DB
        return Converter.getLoginRes(accessToken,refreshToken,userDetailsImpl.getId());
	}

	@Override
	public void setCookies(String accessToken,String refreshToken,HttpServletResponse res) {
		
		boolean isProd = "prod".equalsIgnoreCase(appEnv);
		
		ResponseCookie accessCookie = ResponseCookie.from(accessCookieName, accessToken)
													.httpOnly(true)
													.secure(isProd)
													.path("/")
													.maxAge(accessJWTExpirationMs/1000) 
													.build();
		ResponseCookie refreshCookie = ResponseCookie.from(refreshCookieName, refreshToken)
													.httpOnly(true)
													.secure(isProd)
													.path("/")
													.maxAge(refreshJWTExpirationMs/1000) 	
													.build();
		res.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
		res.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
		
	}



	@Override
	public void logout(HttpServletResponse res) {
		boolean isProd = "prod".equalsIgnoreCase(appEnv);
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		//remove refresh token in DB
		SecurityContextHolder.clearContext();
		ResponseCookie accessCookie = ResponseCookie.from(accessCookieName, null)
													.httpOnly(true)
													.secure(isProd)
													.path("/")
													.maxAge(0)
													.build();
		ResponseCookie refreshCookie = ResponseCookie.from(refreshCookieName, null)
													.httpOnly(true)
													.secure(isProd)
													.path("/")
													.maxAge(0)
													.build();
		res.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
		res.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
	}
	
	
	
	
}
