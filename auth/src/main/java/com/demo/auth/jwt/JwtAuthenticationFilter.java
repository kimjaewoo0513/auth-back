package com.demo.auth.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.IncorrectClaimException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter  extends OncePerRequestFilter {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
	
	private JwtTokenProvider jwtTokenProvider;

	@Override
	protected void doFilterInternal( HttpServletRequest request
								   , HttpServletResponse response
								   , FilterChain filterChain) throws ServletException, IOException {
		
		// 토큰 추출
		String accessToken = resolveToken(request);
		
		try {
			
			if(accessToken != null && jwtTokenProvider.validateAccessToken(accessToken)) {
				
				Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
				SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug("Save authentication in SecurityContextHolder.");
			}else {
				log.info("토큰 검증 실패");
			}
			
		} catch (IncorrectClaimException e) { // 잘못된 토큰일 경우
			SecurityContextHolder.clearContext();
            log.debug("Invalid JWT token.");
            response.sendError(403);
		} catch (UsernameNotFoundException e) { // 회원을 찾을 수 없을 경우
			SecurityContextHolder.clearContext();
			log.debug("Can't find user.");
            response.sendError(403);
		}
			
	}

	// HTTP Request 헤더로부터 토큰 추출
	public String resolveToken(HttpServletRequest request) {
		
		String bearerToken = request.getHeader("Authorization");
		
		if (bearerToken != null && bearerToken.startsWith("Bearer "))
			return bearerToken.substring(7);
		
        return null;
	}
}
