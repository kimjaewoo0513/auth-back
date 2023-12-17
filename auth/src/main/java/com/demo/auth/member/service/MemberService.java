package com.demo.auth.member.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.auth.jwt.util.JwtTokenizer;
import com.demo.auth.member.domain.Member;
import com.demo.auth.member.domain.RefreshToken;
import com.demo.auth.member.domain.Role;
import com.demo.auth.member.dto.MemberLoginDto;
import com.demo.auth.member.dto.MemberLoginResponseDto;
import com.demo.auth.member.dto.MemberSignupDto;
import com.demo.auth.member.dto.MemberSignupResponseDto;
import com.demo.auth.member.exception.MemberException;
import com.demo.auth.member.repository.MemberRepository;
import com.demo.auth.member.repository.RefreshTokenRepository;
import com.demo.auth.member.repository.RoleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
	
	private final JwtTokenizer jwtTokenizer;
	private final PasswordEncoder passwordEncoder;
	private final MemberRepository memberRepository;
	private final RoleRepository roleRepository;
	private final RefreshTokenRepository refreshTokenRepository;
	
	@Transactional
	public MemberSignupResponseDto addMember(MemberSignupDto memberSignupDto) {
		log.info("===============   " + memberSignupDto.getEmail() + "   is enter service." );
		
		/* 가입 이메일 중복 검증
		 * 409 Conflict는 리소스의 충돌을 의미하는 상태코드입니다. 
		 * ID 중복이라는 것은 결국 ID라는 PK 자원을 점유한 것에 대한 충돌이기 때문에 
		 * 이 상태코드가 가장 적합하다고 생각하여 409 상태코드를 반영하기로 했습니다.
		 */
		if(!memberRepository.findByEmail(memberSignupDto.getEmail()).isEmpty()){
			throw new MemberException("===============   중복 이메일 존재.", HttpStatus.CONFLICT);
		}

		Member member = new Member();
		member.setName(memberSignupDto.getName());
		member.setEmail(memberSignupDto.getEmail());
		member.setPassword(passwordEncoder.encode( memberSignupDto.getPassword()));
		
		Optional<Role> userRole = roleRepository.findByName("ROLE_USER");
        member.addRole(userRole.get());
        Member saveMember = memberRepository.save(member);

        MemberSignupResponseDto memberSignupResponseDto = new MemberSignupResponseDto();
        memberSignupResponseDto.setMemberId(saveMember.getMemberId());
        memberSignupResponseDto.setName(saveMember.getName());
        memberSignupResponseDto.setRegdate(saveMember.getRegdate());
        memberSignupResponseDto.setEmail(saveMember.getEmail());
		
		return memberSignupResponseDto;
		
	}
	
	@Transactional
	public MemberLoginResponseDto login(MemberLoginDto memberLoginDto) {
		log.info("===============   " + memberLoginDto.getEmail() + "   is enter service." );
		
		// email이 없을 경우 Exception이 발생한다. Global Exception에 대한 처리가 필요하다.
        Member member = findByEmail(memberLoginDto.getEmail());
        if(!passwordEncoder.matches(memberLoginDto.getPassword(), member.getPassword())){
        	throw new MemberException("===============   이메일이 존재하지 않음.", HttpStatus.UNAUTHORIZED);
        }
        
        List<String> roles = member.getRoles().stream().map(Role::getName).collect(Collectors.toList());
		
		// JWT 토큰 생성
        String accessToken = jwtTokenizer.createAccessToken(member.getMemberId(), member.getEmail(), roles);
        String refreshToken = jwtTokenizer.createRefreshToken(member.getMemberId(), member.getEmail(), roles);
		
        log.info("===============   accessToken 생성  " + accessToken);
        log.info("===============   refreshToken 생성  " + refreshToken);
        
        // 추후 redis 사용으로 변경
        RefreshToken refreshTokenEntity = new RefreshToken();
        refreshTokenEntity.setValue(refreshToken);
        refreshTokenEntity.setMemberId(member.getMemberId());
        refreshTokenEntity = refreshTokenRepository.save(refreshTokenEntity);
        
        log.info(refreshTokenEntity.toString());
        
        MemberLoginResponseDto loginResponse = MemberLoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .memberId(member.getMemberId())
                .name(member.getName())
                .build();
        
        log.info(loginResponse.toString());
		
		return loginResponse;
	}
	
	@Transactional(readOnly = true)
    public Member findByEmail(String email){
        return memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));
    }

	@Transactional
	public void logout(String refreshToken) {
		refreshTokenRepository.findByValue(refreshToken).ifPresent(refreshTokenRepository::delete);
	}

}
