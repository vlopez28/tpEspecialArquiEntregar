package com.example.authservice.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@JsonIgnoreProperties( ignoreUnknown = true )
public class UserRequestDTO {

	private Long id;
	private String apellido;
    private String nombre;
    private String email;
    private String celular;
    private String username;
    private String password;
	private Set<Long> cuentas;
    private Set<String> authorities;

}