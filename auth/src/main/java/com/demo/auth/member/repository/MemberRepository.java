package com.demo.auth.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.auth.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	
	 Optional<Member> findByEmail(String email);

}
