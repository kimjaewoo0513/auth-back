package com.demo.auth.jwt.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	//private final AuthenticationManager authenticationManager;

	@Override
	protected void doFilterInternal(  HttpServletRequest request
									, HttpServletResponse response
									, FilterChain filterChain) throws ServletException, IOException {

		
	}


}
