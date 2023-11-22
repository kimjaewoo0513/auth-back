package com.demo.auth.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.auth.member.dao.MemberDao;
import com.demo.auth.member.domain.Member;
import com.demo.auth.member.dto.MemberSignupDto;

@Service
public class MemberService {
	
	@Autowired
	private MemberDao dao;

	@Transactional
	public Member addMember(MemberSignupDto memberSignupDto) {

		Member member = new Member();
		member.setName(memberSignupDto.getName());
		member.setEmail(memberSignupDto.getEmail());
		member.setPassword(memberSignupDto.getPassword());

		Member saveMember = dao.addMember(member);
		
		return saveMember;
		
	}

}
