package com.demo.auth.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.auth.member.domain.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{

}
