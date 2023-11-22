package com.demo.auth.member.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.auth.member.domain.Member;
import com.demo.auth.member.dto.MemberSignupDto;
import com.demo.auth.member.service.MemberService;

@RestController
@RequestMapping("/user")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/login")
	public String login() {
		return "login suc";
	}
	
	@PostMapping("/signup")
	public ResponseEntity signup(@RequestBody @Valid MemberSignupDto memberSignupDto , BindingResult bindingResult ) {
		if(bindingResult.hasErrors()){
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		Member member = memberService.addMember(memberSignupDto);
		
		return new ResponseEntity(HttpStatus.OK);
	}
}
