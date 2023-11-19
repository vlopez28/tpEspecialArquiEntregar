package com.integrador.service.dto.usuario;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.integrador.domain.Usuario;
import com.integrador.domain.Cuenta;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class UsuarioResponseDto implements Serializable{

	private  Long id;
    private  String nombre;
    private String apellido;
	private  String celular;
	private  String email;
	private String password;
	private String username;

	@JsonIgnore
	private  List<Cuenta> cuentas;
	
	
	
    
	public UsuarioResponseDto(Usuario u ) {
		if(u != null) {
			this.id = u.getId();
	        this.nombre = u.getNombre();
	        this.apellido = u.getApellido();
	        this.celular = u.getCelular();
	        this.email = u.getEmail();
	        this.password = u.getPassword();
	        this.username = u.getUsername();
	        this.cuentas = u.getCuentas();
		}
        
        
        
	}



	@Override
	public String toString() {
		return "UsuarioResponseDto [apellido=" + apellido + ", cuentas=" + cuentas + "]";
	}
	
	
}
