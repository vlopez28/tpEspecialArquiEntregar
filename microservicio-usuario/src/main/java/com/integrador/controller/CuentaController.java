package com.integrador.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.integrador.domain.Cuenta;
import com.integrador.security.jwt.AuthorityConstants;
import com.integrador.service.CuentaService;
import com.integrador.service.dto.cuenta.CuentaRequestDto;
import com.integrador.service.dto.cuenta.CuentaResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/cuentas")
@RequiredArgsConstructor
public class CuentaController {

	@Autowired
	private  CuentaService cuentaService;
	
	
	
	
	@Operation(summary = "Cuentas find All", description = "Devuelve todas las cuentas.")
	@GetMapping("")
	@PreAuthorize( "hasAuthority( \"" + AuthorityConstants.ADMIN + "\" )" + "|| hasAuthority( \"" + AuthorityConstants.USER + "\" )" )
    public List<CuentaResponseDto> findAll(){
        return this.cuentaService.findAll();
    }
	
	
	@Operation(summary = "Cuenta by Id", description = "Devuelve una cuenta por su Id.")
	@PreAuthorize( "hasAuthority( \"" + AuthorityConstants.ADMIN + "\" )" + "|| hasAuthority( \"" + AuthorityConstants.USER + "\" )" )
	 @GetMapping("/{id}")
	   public ResponseEntity<?> getById(@PathVariable Long id){
	        try{
	            return ResponseEntity.status(HttpStatus.OK).body(cuentaService.findById(id));
	        }catch (Exception e){
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. Cuenta inexistente");
	        }
	        
	   }

	@Operation(summary = "Crear cuenta", description = "Se crea una cuenta.")
    @PostMapping("")
    @PreAuthorize( "hasAuthority( \"" + AuthorityConstants.ADMIN + "\" )" + "|| hasAuthority( \"" + AuthorityConstants.USER + "\" )" )
    public ResponseEntity<?> save( @RequestBody @Validated CuentaRequestDto request ){
        try {
        	return ResponseEntity.status(HttpStatus.OK).body(cuentaService.save(request));
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Ocurrio un error, revise los campos ingresados");
        }
    }
    
    
	@Operation(summary = "Eliminar cuenta", description = "Se elimina una cuenta.")
    @DeleteMapping("/{id}")
    @PreAuthorize( "hasAuthority( \"" + AuthorityConstants.ADMIN + "\" )" + "|| hasAuthority( \"" + AuthorityConstants.USER + "\" )" )
    public ResponseEntity<?> delete(@PathVariable Long id){
        try{
            this.cuentaService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Se elimino correctamente la cuenta con el id: " + id);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. No se pudo eliminar la cuenta con id: " + id +".\"\n\"error\":\""+e.getMessage()+"\"}");
        }
    }
    
	@Operation(summary = "Modificar cuenta", description = "Se modifica una cuenta.")
    //chequear
    @PutMapping("/{id}")
    @PreAuthorize( "hasAuthority( \"" + AuthorityConstants.ADMIN + "\" )" + "|| hasAuthority( \"" + AuthorityConstants.USER + "\" )" )
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Validated CuentaRequestDto request) {
        try {
            Cuenta cuenta = cuentaService.update(id, request);
            CuentaResponseDto response = new CuentaResponseDto(cuenta);

            return ResponseEntity.status(HttpStatus.OK).body(response);
          
        } catch (Exception e) {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la cuenta con el ID proporcionado.");
        }
    }
    
	
}
