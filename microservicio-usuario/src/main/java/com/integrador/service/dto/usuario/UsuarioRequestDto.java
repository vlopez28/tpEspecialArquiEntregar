package com.integrador.service.dto.usuario;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.integrador.domain.Authority;
import com.integrador.domain.Cuenta;

import jakarta.persistence.Column;
import lombok.Data;

@Data
@JsonIgnoreProperties( ignoreUnknown = true )
public class UsuarioRequestDto implements Serializable{
	
	private Long id;
	private String apellido;
    private String nombre;
    private String email;
    private String celular;
    private String username;
    private String password;
	private Set<Long> cuentas;
    private Set<String> authorities;
    
    
	public UsuarioRequestDto(String apellido, String nombre, String email, String celular, String username,
			String password, Set<Long> cuentas, Set<String> authorities) {
		super();
		
		this.apellido = apellido;
		this.nombre = nombre;
		this.email = email;
		this.celular = celular;
		this.username = username;
		this.password = password;
		this.cuentas = cuentas;
		this.authorities = authorities;
	}
 
    
	
	
//	public Iterable<Long> getCuentas(){
//		return cuentas;
//	}
	
	

}
