package com.homecare.GoldenFutureHomeCare.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homecare.GoldenFutureHomeCare.SpringApplicationContext;
import com.homecare.GoldenFutureHomeCare.request.model.ConsumerLoginRequest;
import com.homecare.GoldenFutureHomeCare.service.ConsumerService;
import com.homecare.GoldenFutureHomeCare.shared.dto.ConsumerDto;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	private final AuthenticationManager authenticationManager;

	public AuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager= authenticationManager;
		
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {
		try {
			ConsumerLoginRequest creds =new ObjectMapper()
					.readValue(req.getInputStream(),ConsumerLoginRequest.class);
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					creds.getEmail(),
					creds.getPassword(),
					new ArrayList<>()) );
			
		}catch(IOException e) {
			throw new RuntimeException(e);
		}
	
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, 
			HttpServletResponse res,
			FilterChain chain,
			Authentication auth) 
					throws IOException, ServletException {
		String userName=((User)auth.getPrincipal()).getUsername();
		//String tokenSecret = new SecurityConstants().getTokenSecret();
		String token =Jwts.builder()
				.setSubject(userName)
				.setExpiration(new Date(System.currentTimeMillis()+SecurityConstants.EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512,SecurityConstants.getTokenSecret())
				.compact();
		//After creating SpringApplicationContext at main package.we write this code.
		ConsumerService consumerService = (ConsumerService) SpringApplicationContext.getBeans("consumerServiceImpl");
		ConsumerDto consumerDto =consumerService.getConsumer(userName);
		res.addHeader(SecurityConstants.HEADER_STRING,SecurityConstants.TOKEN_PREFIX+token);
		res.addHeader("ConsumerId",consumerDto.getConsumerId());
	}

}







