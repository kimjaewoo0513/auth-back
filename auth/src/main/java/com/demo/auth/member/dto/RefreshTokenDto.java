package com.demo.auth.member.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class RefreshTokenDto {
	
    @NotEmpty
    String refreshToken;

}
