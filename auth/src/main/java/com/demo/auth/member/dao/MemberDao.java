package com.demo.auth.member.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.demo.auth.member.domain.Member;

@Mapper
@Repository
public interface MemberDao {
	
	Member findById(String id);

	Member addMember(Member member);

}
