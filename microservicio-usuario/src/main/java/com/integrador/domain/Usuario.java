package com.integrador.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.integrador.service.dto.usuario.UsuarioRequestDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity
@Data
public class Usuario implements Serializable{
	@Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String nombre;
    @Column
    private String apellido;
    @Column
    private String celular;
    @Column
    private String email;  
    @Column
    private String username;
    @Column
    private String password;
    
    @ManyToMany (fetch = FetchType.LAZY)
    @JoinTable(
            name = "rel_user__account",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "account_id")
    )
    private List<Cuenta> cuentas;
    
    @ManyToMany( fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    @JoinTable(
            name = "rel_user__authority",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    private List<Authority> authorities;
    
    
    
    public Usuario() {
    	
    }
    
    
    public Usuario(UsuarioRequestDto request) {
    	this.nombre = request.getNombre();
    	this.apellido = request.getApellido();
    	this.celular = request.getCelular();
    	this.email = request.getEmail();
    	this.username = request.getUsername();
    	this.password = request.getPassword();
    	this.cuentas = new ArrayList<>();
    	this.authorities = new ArrayList<>();
    }
    
    public void insertarCuenta(Cuenta c) {
    	if(!cuentas.contains(c)) {
    		cuentas.add(c);
    	}
    }
    
    public boolean quitarCuenta(Cuenta c) {
    	if(cuentas.contains(c)) {
    		return cuentas.remove(c);
    	}else {
    		return false;
    	}
    }
    


	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", cuentas=" + cuentas + "]";
	}

    
    
}
