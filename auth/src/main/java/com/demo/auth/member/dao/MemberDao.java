package com.demo.auth.member.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.demo.auth.member.domain.Member;
import com.demo.auth.member.domain.Role;

@Mapper
@Repository
public interface MemberDao {
	
	// id로 유저 검색
	Member findById(String id);
	
	// email로 유저 검색
	Member findByEmail(String email);
	
	// 권한 검색 
	Role getRoleByName(String role);

	// 신규 유저 추가
	void addMember(Member member);

	void addMemberRole(Role  role);

	void addMemberRole(Long memberId, Long roleId);


}
