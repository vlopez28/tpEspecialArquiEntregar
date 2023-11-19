package com.integrador.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.integrador.domain.Monopatin;
import com.integrador.service.MonopatinService;
import com.integrador.service.dto.monopatin.*;
import com.integrador.service.dto.monopatinConViajes.MonopatinConViajesResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/monopatines")
@RequiredArgsConstructor
public class MonopatinController {
	
	@Autowired
	private  MonopatinService monopatinService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView method() {
	    return new ModelAndView("redirect:/swagger-ui.html");
	}
	
	
	
	@Operation(summary = "Monopatines find All", description = "Devuelve todos los monopatines.")
	@GetMapping("")
    public List<MonopatinResponseDto> findAll(){
        return this.monopatinService.findAll();
    }

	@Operation(summary = "Obtiene un administrador por su ID", description = "Devuelve un administrador basado en su ID.")
	 @GetMapping("/{id}")
	   public ResponseEntity<?> getById(@PathVariable Long id){
	        try{
	            return ResponseEntity.status(HttpStatus.OK).body(monopatinService.findById(id));
	        }catch (Exception e){
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. Monopatin inexistente");
	        }
	        
	  }

	@Operation(summary = "Alta monopatin", description = "Da de alta un monopatin.")
    @PostMapping("")
    public ResponseEntity<?> save( @RequestBody @Validated MonopatinRequestDto request ){
		 System.out.println(request);
        try {
        	return ResponseEntity.status(HttpStatus.OK).body(monopatinService.save(request));
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Ocurrio un error, revise los campos ingresados");
        }
    }
    

	@Operation(summary = "Eliminar un monopatin", description = "Elimina un monopatin basado en su ID.")
    @DeleteMapping("eliminarMonopatin/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try{
        	System.out.println("ELIMINAR MONOPATIN MONOPATINCONTROLLER");
            this.monopatinService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Se elimino correctamente monopatin con el id: " + id);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. No se pudo eliminar el monopatin con id: " + id);
        }
    }
	
	
	@Operation(summary = "Update monopatin", description = "Edita un monopatin basado en su ID.")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Validated MonopatinRequestDto request) {
		 System.out.println("up"+request);

    	try {
            Monopatin monopatin = monopatinService.update(id, request);
            MonopatinResponseDto response = new MonopatinResponseDto(monopatin);

            return ResponseEntity.status(HttpStatus.OK).body(response);
          
        } catch (Exception e) {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el monopatin con el ID proporcionado.");
        }
    }
    
    //traer monopatines por km, trae los monopatines con mas de cierta cant de km
	  @Operation(summary = "Monopatines por Km.", description =  " Lista monopatines por Km.")
    @GetMapping("/porKm/{cantKm}")
	   public ResponseEntity<?> getMonopatinesPorKm(@PathVariable Long cantKm){
	        try{
	            return ResponseEntity.status(HttpStatus.OK).body(monopatinService.getMonopatinesPorKm(cantKm));
	        }catch (Exception e){
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error, revise los campos nuevamente");
	        }
	        
	  }
	
  //traer monopatines por tiempo sin pausa, trae los monopatines con mas de cierto tiempo sin contar las pausas
	  @Operation(summary = "Monopatines por Tiempo sin pausa", description =  "Listado de monopatines por tiempo sin pausa")
	  @GetMapping("/porTiempoSinPausa/{cantTiempo}")
	   public ResponseEntity<?> getMonopatinesPorTiempoSinPausa(@PathVariable Long cantTiempo){
	        try{
	            return ResponseEntity.status(HttpStatus.OK).body(monopatinService.getMonopatinesPorTiempoSinPausa(cantTiempo));
	        }catch (Exception e){
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error, revise los campos nuevamente");
	        }
	        
	  }
    
  //trae los monopatines con mas de cierto tiempo contando las pausas
	@Operation(summary = "Monopatines por tiempo con pausa.", description = "Listado de monopatines por tiempo con pausa.")  
    @GetMapping("/porTiempoConPausa/{cantTiempo}")
	   public ResponseEntity<?> getMonopatinesPorTiempoConPausa(@PathVariable Long cantTiempo){
	        try{
	            return ResponseEntity.status(HttpStatus.OK).body(monopatinService.getMonopatinesPorTiempoConPausa(cantTiempo));
	        }catch (Exception e){
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error, revise los campos nuevamente");
	        }
	        
	  }

  //trae los monopatines con cierta cant de viajes en un anio dado
	  @Operation(summary = "Monopatines con cantidad de viajes por año.", description = " Lista los monopatines con cantidad de viajes "
	  		+ "en un determinado período")
	@GetMapping("/conViajes/{cantViajes}/{anio}")
	   public List<MonopatinConViajesResponseDto> getMonopatinesConViajes(@PathVariable Long cantViajes, @PathVariable Integer anio){
	        try{
	        	return this.monopatinService.getMonopatinesConViajes(cantViajes, anio);
	        }catch (Exception e){
	             e.printStackTrace();
	             return null;
	        }
	        
	  }
    
 // trae los monopatines en operacion vs en manteniminetno
	  @Operation(summary = "Monopatines en operacion/mantenimiento.", description = " Detalla la cantidad de monopatines que"
	  		+ "estan en operación y los que estan en mantenimiento "
		  		+ "en un determinado período")
    @GetMapping("/enOperacionMantenimiento")
	   public MonopatinesCantidadResponseDto getMonopatinesEnOperacionMantenimiento(){
    	
    	return this.monopatinService.getMonopatinesEnOperacionMantenimiento();

	  }
    
	  
	  @Operation(summary = "Monopatines cerca.", description = " Lista los monopatines que se encuentran cerca de una "
	  		+ "ubicación pasada por parámetro") 
    @GetMapping("/obtenerMonopatinesCerca/{latitud}/{longitud}")
   	public List<MonopatinesCercaResponseDto> getMonopatinesCerca(@PathVariable double latitud, @PathVariable double longitud){
   		try{
   			return this.monopatinService.getMonopatinesCerca(latitud, longitud);
   		  }catch(Exception e){
   				e.printStackTrace();
   				return null;
   		  }
   	  }

    
}
