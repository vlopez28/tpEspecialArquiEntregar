package com.integrador.domain;

import com.integrador.service.dto.administrador.AdministradorRequestDto;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
public class Administrador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdAdministrador;
    @Column
    private String nombreYApellido;

    public Administrador() {
	}

	public Administrador(AdministradorRequestDto request){
		this.IdAdministrador = request.getId();
        this.nombreYApellido= request.getNombreYApellido();
    }
    
}
