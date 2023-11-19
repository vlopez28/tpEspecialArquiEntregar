package com.example.authservice.security;


import com.example.authservice.DTO.authUserDTO;
import com.example.authservice.Modelo.Authority;
import com.example.authservice.Modelo.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DomainUserDetailsService implements UserDetailsService {

    @Autowired
    private RestTemplate usuarioRest;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        authUserDTO user = this.findByEmail(username);
        System.out.println("loadUserByUsername EN AUTHSERVICE: " + user);
        if (user == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        List<String> stringAuthorities = user.getAuthorities();

        List<Authority> authorities = stringAuthorities.stream().map(Authority::new).collect(Collectors.toList());

        Usuario usuario = new Usuario(user.getPassword(), user.getEmail(), authorities);
        UserDetails userDetails =  this.createSpringSecurityUser(usuario);
        System.out.println("userDetails: " + userDetails);
        return userDetails;
    }

    public authUserDTO findByEmail(String email) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<authUserDTO> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<authUserDTO> response = this.usuarioRest.exchange(
                "http://localhost:8005/api/usuarios/email/" + email,
                HttpMethod.GET,
                requestEntity,
                authUserDTO.class
        );
        return response.getBody();
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(Usuario user) {
        List<GrantedAuthority> grantedAuthorities = user
                .getAuthorities()
                .stream()
                .map(Authority::getName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
    }
}
