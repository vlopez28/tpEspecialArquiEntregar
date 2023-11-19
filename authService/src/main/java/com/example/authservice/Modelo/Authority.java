package com.example.authservice.Modelo;


import lombok.Data;

import java.io.Serializable;

@Data
public class Authority implements Serializable {

    private String name;

    public Authority(String name) {
        this.name = name;
    }
}