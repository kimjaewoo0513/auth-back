package com.demo.auth.member.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.auth.member.dto.MemberLoginDto;
import com.demo.auth.member.dto.MemberLoginResponseDto;
import com.demo.auth.member.dto.MemberSignupDto;
import com.demo.auth.member.dto.MemberSignupResponseDto;
import com.demo.auth.member.dto.RefreshTokenDto;
import com.demo.auth.member.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@SuppressWarnings("unchecked")
@RequestMapping("/user")
public class MemberController {
	
	private final MemberService memberService;
	
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody @Valid MemberLoginDto memberLoginDto, BindingResult bindingResult) {
		log.info("===============   " + memberLoginDto.getEmail() + "   is trying to LOGIN");
		if(bindingResult.hasErrors()){ // 입력값 검증
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		MemberLoginResponseDto memberLoginResponseDto = memberService.login(memberLoginDto); 
		return new ResponseEntity(memberLoginResponseDto, HttpStatus.OK);
	}
	
	@DeleteMapping("/logout")
	public ResponseEntity logout(@RequestBody RefreshTokenDto refreshTokenDto) {
		memberService.logout(refreshTokenDto.getRefreshToken());
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@PostMapping("/signup")
	public ResponseEntity signup(@RequestBody @Valid MemberSignupDto memberSignupDto , BindingResult bindingResult ) {
		log.info("===============   " + memberSignupDto.getEmail() + "   is trying to SIGNUP");
		if(bindingResult.hasErrors()){ // 입력값 검증
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		MemberSignupResponseDto memberSignupResponseDto = memberService.addMember(memberSignupDto);
		return new ResponseEntity(memberSignupResponseDto, HttpStatus.CREATED);
	}
}
