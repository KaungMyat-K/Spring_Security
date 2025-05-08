package com.sec.security.services;

import java.util.Collection;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sec.entity.Employee;

public class UserDetailsImpl implements UserDetails {
	
	private long id;
    private String phone;
    private String password;
    
    public UserDetailsImpl(long id, String phone, String password) {
        this.id = id;
        this.phone = phone;
        this.password = password;
    }
    
    public static UserDetailsImpl build(Employee employee){
        return new UserDetailsImpl(employee.getEmployeeKey(), employee.getPhone(), employee.getPasscode());
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	public long getId(){
        return id;
    }

    public String getPhone(){
        return phone;
    }
	
	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return phone;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
		return true;
		if (o == null || getClass() != o.getClass())
		return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
  }
}
