package com.example.authservice.Controller;


import com.example.authservice.DTO.UserRequestDTO;
import com.example.authservice.DTO.authRequestDTO;
import com.example.authservice.security.DomainUserDetailsService;
import com.example.authservice.security.jwt.JWTFilter;
import com.example.authservice.security.jwt.TokenProvider;
import com.example.authservice.service.AuthService;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class authController {

    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    private AuthService authService;

    private final PasswordEncoder passwordEncoder;

    private final DomainUserDetailsService domainUserDetailsService;
    @PostMapping("/login")

    public ResponseEntity<JWTToken> login(@Valid @RequestBody authRequestDTO requestDTO){
        try{
            System.out.println("LOGIN AUTHCONTROLLER");
            System.out.println("requestDTO: " + requestDTO);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(requestDTO.getEmail(), requestDTO.getPassword());
            System.out.println("AuthtenticationToken: " + authenticationToken);
            UserDetails userDetails = this.domainUserDetailsService.loadUserByUsername(requestDTO.getEmail());

            System.out.println("RequestDTOPASSWORD: " + requestDTO.getPassword() + " userDetailsPASSWORD: " + userDetails.getPassword());
            if(passwordEncoder.matches(requestDTO.getPassword(), userDetails.getPassword())){
                System.out.println("PASO EL MATCH");
                Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
                System.out.println( "authentication: " + authentication);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("SecurityContextHolder: " + SecurityContextHolder.getContext());
                final var jwt = tokenProvider.createToken(authentication);
                System.out.println("jwt: " + jwt);
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
                return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Valida el token y devuelve un JSON con nombre de usuario y sus autoridades.
     */
    @GetMapping("/validate")
    public ResponseEntity<ValidateTokenDTO> validateGet() {
        System.out.println("ENTRE EN EL VALIDATE");
        final var user = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("USER: " + user);

        final var authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        System.out.println("authorities: " + authorities);
        return ResponseEntity.ok(
                ValidateTokenDTO.builder()
                        .username( user.getName() )
                        .authorities( authorities )
                        .isAuthenticated( true )
                        .build()
        );
    }
    @Data
    @Builder
    public static class ValidateTokenDTO {
        private boolean isAuthenticated;
        private String username;
        private List<String> authorities;
    }


    @PostMapping("/register")
    public ResponseEntity<?> register( @Valid @RequestBody UserRequestDTO request ){
        System.out.println(request);
        final var newUser = this.authService.crearCuenta(request);
        try {
            return new ResponseEntity<>( newUser, HttpStatus.CREATED );
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(newUser,HttpStatus.BAD_REQUEST);
        }

    }

    static class JWTToken {
        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
