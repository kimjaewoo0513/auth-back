package com.demo.auth.member.domain;

import lombok.Data;

@Data
public class Member {

	private String id;
	private String pwd;
	private String name;
	private String email;

}
