package com.example.authservice.service;

import com.example.authservice.DTO.UserRequestDTO;
import com.example.authservice.DTO.UserResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service("authService")
@RequiredArgsConstructor
@Transactional
public class AuthService {

    @Autowired
    private RestTemplate usuarioRest;

    private final PasswordEncoder passwordEncoder;
    public UserResponseDTO crearCuenta(UserRequestDTO user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<UserRequestDTO> requestEntity = new HttpEntity<>(user,headers);
        ResponseEntity<UserResponseDTO> response = this.usuarioRest.exchange(
                "http://localhost:8005/api/usuarios/register",
                HttpMethod.POST,
                requestEntity,
                UserResponseDTO.class
        );
        return response.getBody();
    }
}
