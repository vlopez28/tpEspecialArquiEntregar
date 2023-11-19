package com.example.authservice.DTO;


import lombok.Data;

@Data
public class UserResponseDTO {

    private long id;
    private String nombre;
    private String apellido;

    public UserResponseDTO( UserRequestDTO user,long id ){
        this.id = id;
        this.nombre = user.getNombre();
        this.apellido = user.getApellido();

    }

    public UserResponseDTO(){
    }

}
