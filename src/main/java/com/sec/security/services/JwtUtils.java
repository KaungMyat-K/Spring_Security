package com.sec.security.services;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import com.sec.exception.BadRequestException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtUtils {
	
	@Value("${app.accessJwtSecret}")
    private String accessJwtSecret;
	
	@Value("${app.refreshJwtSecret}")
    private String refreshJwtSecret;

    @Value("${app.refreshJWTExpirationMs}")
    private int refreshJWTExpirationMs;
    
    @Value("${app.accessJWTExpirationMs}")
    private int accessJWTExpirationMs;
    
    @Value("${app.accessCookieName}")
    private String acessCookieName;
    
    @Value("${app.refreshCookieName}")
    private String refreshCookieName;
    
    
    public String generateJwtToken(String id,String phone,String type) {
    	int expireTime;
    	Key key;
    	if("refresh".equalsIgnoreCase(type)) {
    		expireTime = refreshJWTExpirationMs;
    		key = refreshKey();
    	}else {
    		expireTime = accessJWTExpirationMs;
    		key = accessKey();
    	}
        Map<String, Object> claims = new HashMap<>();
            claims.put("id", id);
            claims.put("phone", phone);

        return Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date((new Date()).getTime() + expireTime))
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();
    }
      
    private Key accessKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessJwtSecret));
    }
    
    private Key refreshKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshJwtSecret));
    }
    
    public String validateJwtToken(HttpServletRequest request) {
        String path = request.getRequestURI();
        boolean isLogout = "/auth/logout".equals(path);
        Cookie refreshCookie = WebUtils.getCookie(request, refreshCookieName);
        Cookie accessCookie = null;
        if (refreshCookie == null) {
            throw new BadRequestException("Unauthenticated user");
        }
        if (isLogout) {
            return parseToken(refreshCookie.getValue(), refreshKey(), "refresh");
        }
        accessCookie = WebUtils.getCookie(request, acessCookieName);
        if (accessCookie == null) {
            throw new BadRequestException("Access token is expired");
        }
        return parseToken(accessCookie.getValue(), accessKey(), "access");
    }

    private String parseToken(String token, Key key, String tokenType) {
        try {
            Claims claims =  Jwts.parserBuilder()
                            .setSigningKey(key)
                            .build()
                            .parseClaimsJws(token)
                            .getBody();
            String id = claims.get("id", String.class);
            String phone = claims.get("phone", String.class);
                    
            return id+"|"+phone;
        } catch (SignatureException e) {
            throw new BadRequestException("Invalid " + tokenType + " token signature");
        } catch (MalformedJwtException e) {
            throw new BadRequestException("Invalid " + tokenType + " token");
        } catch (ExpiredJwtException e) {
            throw new BadRequestException(tokenType + " token is expired");
        } catch (UnsupportedJwtException e) {
            throw new BadRequestException(tokenType + " token is unsupported");
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(tokenType + " token claims string is empty");
        }
    }
    
    
}
