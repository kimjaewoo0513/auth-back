package com.demo.auth.member.exception;

import org.springframework.http.HttpStatus;

public class MemberException extends RuntimeException{
	
	private static final long serialVersionUID = 721314972414738086L;
	private HttpStatus status;

    public MemberException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

}
