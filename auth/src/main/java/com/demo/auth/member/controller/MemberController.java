package com.demo.auth.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class MemberController {

	@GetMapping("/login")
	public String login() {
		return "login suc";
	}
	
}
