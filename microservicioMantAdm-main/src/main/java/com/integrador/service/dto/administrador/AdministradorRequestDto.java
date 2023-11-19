package com.integrador.service.dto.administrador;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdministradorRequestDto {
	
	private Long id;

    private String nombreYApellido;

	public AdministradorRequestDto(String rol, String nombreYApellido) {
		this.nombreYApellido = nombreYApellido;
	}

	    
}
