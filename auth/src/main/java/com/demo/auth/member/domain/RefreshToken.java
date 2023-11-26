package com.demo.auth.member.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class RefreshToken {
	
	private Long id;
    private Long memberId;
    private String value;

}
