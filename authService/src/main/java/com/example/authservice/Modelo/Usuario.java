package com.example.authservice.Modelo;

import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
public class Usuario {


    private Long id_usuario;

    private String username;

    private String nombre;

    private String apellido;


    private String password;

    private String email;

    private List<Authority> authorities;

    private List<Authority>roles;

    public Usuario(Long id_usuario, String username, String nombre, String apellido, String password, String email, List<Authority> authorities) {
        this.id_usuario = id_usuario;
        this.username = username;
        this.nombre = nombre;
        this.apellido = apellido;
        this.password = password;
        this.email = email;
        this.authorities = authorities;
    }

    public Usuario(String password, String email, List<Authority> authorities) {
        this.password = password;
        this.email = email;
        this.authorities = authorities;
    }
}
