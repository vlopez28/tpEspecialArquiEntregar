package com.integrador.service.dto.administrador;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.integrador.domain.Administrador;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class AdministradorResponseDto {
	
	private Long id;

    private String nombreYApellido;

    public AdministradorResponseDto(Administrador a){
    	this.id = a.getIdAdministrador();
        this.nombreYApellido=a.getNombreYApellido();
    }
}
