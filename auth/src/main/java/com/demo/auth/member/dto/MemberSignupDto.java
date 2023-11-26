package com.demo.auth.member.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberSignupDto {
	
	@NotEmpty
	@Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$"
			,message = "올바른 이메일 형식이 아닙니다.")
	String email;
	
	@NotEmpty
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{7,16}$"
			,message = "비밀번호는 영문+숫자+특수문자를 포함한 8~20자여야 합니다")
	String password;
	
	@NotEmpty
	@Pattern(regexp = "^[a-zA-Z가-힣\\\\s]{2,15}"
			,message = "이름은 영문자, 한글, 공백포함 2글자부터 15글자까지 가능합니다.")
	String name;

}
