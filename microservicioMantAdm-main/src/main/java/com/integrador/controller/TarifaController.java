package com.integrador.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.integrador.domain.Tarifa;
import com.integrador.security.jwt.AuthorityConstants;
import com.integrador.service.TarifaService;
import com.integrador.service.dto.tarifa.TarifaRequestDto;
import com.integrador.service.dto.tarifa.TarifaResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/tarifas")
public class TarifaController {
	
	@Autowired
	private TarifaService tarifaService;
	
	@GetMapping("")
	@PreAuthorize("hasAuthority( \"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> findAll(){
		try {
			System.out.print("hola controler 1");
			return ResponseEntity.status(HttpStatus.OK).body(tarifaService.findAll());
			
		}catch (Exception e){
			System.out.print("hola controler 2");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error");
        }
    }

	
	 @GetMapping("/{id}")
	 @PreAuthorize("hasAuthority( \"" + AuthorityConstants.ADMIN + "\" )" )
	 public ResponseEntity<?> getById(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(tarifaService.findById(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. Tarifa inexistente");
        }
	        
	  }

    
    @PostMapping("")
    @PreAuthorize("hasAuthority( \"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> save( @RequestBody @Validated TarifaRequestDto request ){
        try {
        	return ResponseEntity.status(HttpStatus.OK).body(tarifaService.save(request));
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Ocurrio un error, revise los campos ingresados");
        }
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority( \"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> delete(@PathVariable Long id){
        try{
            this.tarifaService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Se elimino correctamente tarifa con el id: " + id);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. No se pudo eliminar la tarifa con id: " + id);
        }
    }
    
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority( \"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Validated TarifaRequestDto request) {
        try {
            Tarifa tarifa = tarifaService.update(id, request);
            TarifaResponseDto response = new TarifaResponseDto(tarifa);

            return ResponseEntity.status(HttpStatus.OK).body(response);
          
        } catch (Exception e) {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la tarifa con el ID proporcionado.");
        }
    }
	

	
}
