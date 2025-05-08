package com.sec.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sec.security.services.JwtUtils;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtils jwtUtils;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {		
          String username = null;
          try {
              username = jwtUtils.validateJwtToken(request);
          } catch (Exception e) {
              filterChain.doFilter(request, response);
              return;
          }
          if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
              UserDetails userDetails = userDetailsService.loadUserByUsername(username);
              UsernamePasswordAuthenticationToken authentication =
                      new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
              authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
              SecurityContextHolder.getContext().setAuthentication(authentication);
          }
          filterChain.doFilter(request, response);
	}

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    String path = request.getServletPath();
    return path.equals("/auth/login") || path.equals("/user/create");
  }

}
