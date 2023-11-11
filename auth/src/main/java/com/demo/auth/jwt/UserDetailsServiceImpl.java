package com.demo.auth.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demo.auth.member.dao.MemberDao;
import com.demo.auth.member.domain.Member;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	private final MemberDao dao;
	
	public UserDetailsServiceImpl(MemberDao dao) {
        this.dao = dao;
    }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Member member = dao.findById(username);
		
		if (member == null) {
            throw new UsernameNotFoundException(String.format("'%s'는 존재하지 않는 사용자입니다.", username));
        }

        return new UserDetailsImpl(member);
	}

}
