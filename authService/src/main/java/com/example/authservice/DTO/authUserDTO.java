package com.example.authservice.DTO;

import lombok.Data;

import java.util.List;

@Data
public class authUserDTO {
    String email;
    String password;
    List<String> authorities;

    public authUserDTO() {
    }

    public authUserDTO(String email, String password, List<String> authorities) {
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }


}
