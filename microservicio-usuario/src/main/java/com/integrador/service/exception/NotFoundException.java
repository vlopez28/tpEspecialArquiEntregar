package com.integrador.service.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NotFoundException extends RuntimeException {
    private final String message;
    private String entity;
    private Long id;
    
    public NotFoundException( String entity, Long id ){
    	this.entity = entity;
    	this.id = id;
        this.message = String.format( "La entidad %s con id %s no existe.", entity, id );
    }

//	public NotFoundException(String message ) {
//		this.message = message;
//		// TODO Auto-generated constructor stub
//	}

	public String getMessage() {
		return message;
	}

	public String getEntity() {
		return entity;
	}

	public Long getId() {
		return id;
	}
	
	
}