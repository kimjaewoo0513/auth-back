package com.demo.auth.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.auth.member.dao.MemberDao;
import com.demo.auth.member.domain.Member;
import com.demo.auth.member.domain.Role;
import com.demo.auth.member.dto.MemberSignupDto;
import com.demo.auth.member.dto.MemberSignupResponseDto;
import com.demo.auth.member.exception.MemberException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	
	@Autowired
	private MemberDao dao;
	
	private final PasswordEncoder passwordEncoder;
	

	@Transactional
	public MemberSignupResponseDto addMember(MemberSignupDto memberSignupDto) {

		Member member = new Member();
		member.setName(memberSignupDto.getName());
		member.setEmail(memberSignupDto.getEmail());
		member.setPassword(passwordEncoder.encode( memberSignupDto.getPassword() ));
		
		Role userRole = dao.getRoleByName("ROLE_USER");
		member.addRole(userRole);
		
		// 아이디 중복 확인
		isExistUserId(member.getEmail());
		dao.addMember(member);

		Member afterAddMember = dao.findByEmail(member.getEmail());
		dao.addMemberRole(afterAddMember.getMemberId(),userRole.getRoleId());
		
		MemberSignupResponseDto memberSignupResponseDto = new MemberSignupResponseDto();
		memberSignupResponseDto.setMemberId(afterAddMember.getMemberId());
		memberSignupResponseDto.setEmail(afterAddMember.getEmail());
		memberSignupResponseDto.setName(afterAddMember.getName());
		memberSignupResponseDto.setRegdate(afterAddMember.getRegdate());
		
		return memberSignupResponseDto;
		
	}
	
	private void isExistUserId(String email) {
		Member result = dao.findByEmail(email);
		if (result != null) {
			throw new MemberException("이미 사용중인 아이디입니다.", HttpStatus.BAD_REQUEST);
		}
	}

}
