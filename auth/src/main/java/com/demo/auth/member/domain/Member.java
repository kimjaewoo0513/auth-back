package com.demo.auth.member.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor // 기본생성자가 필요하다.
@Setter
@Getter
public class Member {

	private Long memberId;
	private String password;
	private String name;
	private String email;
	private LocalDateTime regdate;

	private Set<Role> roles = new HashSet<>();

    @Override
    public String toString() {
        return "User{" +
                "memberId=" + memberId +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", regdate=" + regdate +
                '}';
    }

    public void addRole(Role role) {
        roles.add(role);
    }
    
}
