package com.demo.auth.member.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class Role {
	
	private Long roleId;
	
	private String name;
	
	@Override
	public String toString() {
	    return "Role{" +
	            "roleId=" + roleId +
	            ", name='" + name + '\'' +
	            '}';
	}

}
