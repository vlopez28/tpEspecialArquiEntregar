package com.integrador.service.dto.auth;

import java.util.List;

import lombok.Data;
@Data
public class AuthUserDTO {
	String email;
    String password;
    List<String> authorities;

    public AuthUserDTO() {
    }

    public AuthUserDTO(String email, String password, List<String> authorities) {
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }
}
