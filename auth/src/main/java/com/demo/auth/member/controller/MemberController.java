package com.demo.auth.member.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

	@PostMapping("/user/login")
	public String login() {
		return "login";
	}
}
