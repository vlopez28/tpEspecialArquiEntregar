package com.integrador.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

import com.integrador.domain.Authority;
import com.integrador.domain.Usuario;
import com.integrador.domain.clases.Monopatin;
import com.integrador.security.jwt.AuthorityConstants;
import com.integrador.service.UsuarioService;
import com.integrador.service.dto.auth.AuthUserDTO;
import com.integrador.service.dto.monopatin.MonopatinesCercaResponseDto;
import com.integrador.service.dto.usuario.UsuarioRequestDto;
import com.integrador.service.dto.usuario.UsuarioResponseDto;
import com.integrador.service.dto.usuarioCuenta.UsuarioCuentaRequestDto;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
@RestController
@RequestMapping("api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
	@Autowired
	private  UsuarioService usuarioService;
	
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView method() {
	    return new ModelAndView("redirect:/swagger-ui.html");
	}
	
	
	@Operation(summary = "Buscar todos los usuarios", description = "Retorna todos los usuarios.")
	@GetMapping("")
	@PreAuthorize("hasAuthority( \"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> findAll(){
		try {
			System.out.print("hola controler 1");
			return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findAll());
			
		}catch (Exception e){
			System.out.print("hola controler 2");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error");
        }
    }

	@Operation(summary = "Buscar un usuario por id", description = "Retorna usuario por id.")
	 @GetMapping("/{id}")
	 @PreAuthorize("hasAuthority( \"" + AuthorityConstants.ADMIN + "\" )" )
	 public ResponseEntity<?> getById(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findById(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. Usuario inexistente");
     }
        
  }
	 
	@Operation(summary = "Registrar usuario", description = "Se registra un usuario.")
	@PostMapping("/register")
    public ResponseEntity<?>insert(@RequestBody UsuarioRequestDto usuario){
        try{
            System.out.println(usuario);

            return ResponseEntity.status(HttpStatus.OK).body(this.usuarioService.registrar(usuario));
        }
        catch (Exception e){
            System.out.println("Error: " + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error, no se pudo insertar el usuario");
        }
    }
	
	@Operation(summary = "Registrar usuario", description = "Se registra un usuario.")
    @PostMapping("")
    public ResponseEntity<?> save( @RequestBody @Validated UsuarioRequestDto request ){
        try {
        	return ResponseEntity.status(HttpStatus.OK).body(usuarioService.save(request));
        }catch(Exception e) {
        	 e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocurrio un error, revise los campos ingresados");
        }
    }
	@Operation(summary = "Asociar cuenta", description = "Vincula una cuenta a un usuario dado un ID de usuario y un ID de cuenta.")
	@PostMapping("/asociarCuenta")
    @PreAuthorize( "hasAuthority( \"" + AuthorityConstants.ADMIN + "\" )" + "|| hasAuthority( \"" + AuthorityConstants.USER + "\" )" )
    public ResponseEntity<?> asociarCuenta( @RequestBody @Validated UsuarioCuentaRequestDto request ){
        try {
        	return ResponseEntity.status(HttpStatus.OK).body(usuarioService.asociarCuenta(request));
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Ocurrio un error, revise los campos ingresados");
        }
    }
    
	@Operation(summary = "Eliminar cuenta", description = "Se elimina una cuenta.")
    @DeleteMapping("/quitarCuenta")
    @PreAuthorize( "hasAuthority( \"" + AuthorityConstants.ADMIN + "\" )" + "|| hasAuthority( \"" + AuthorityConstants.USER + "\" )" )
    public ResponseEntity<?> quitarCuenta(@RequestBody @Validated UsuarioCuentaRequestDto request){
        try{
            this.usuarioService.quitarCuenta(request);
            return ResponseEntity.status(HttpStatus.OK).body(usuarioService.quitarCuenta(request));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. revise los campos ingresados");
        }
    }
    
	@Operation(summary = "Eliminar cuenta", description = "Se elimina una cuenta.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority( \"" + AuthorityConstants.ADMIN + "\" )" )
    public ResponseEntity<?> delete(@PathVariable Long id){
        try{
            this.usuarioService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Se elimino correctamente usuario con el id: " + id);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. No se pudo eliminar el usuario con id: " + id);
        }
    }
    
	@Operation(summary = "Update cuenta", description = "Se modifica una cuenta.")
    @PutMapping("/{id}")
    @PreAuthorize( "hasAuthority( \"" + AuthorityConstants.ADMIN + "\" )" + "|| hasAuthority( \"" + AuthorityConstants.USER + "\" )" )
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Validated UsuarioRequestDto request) {
        try {
            Usuario usuario = usuarioService.update(id, request);
            UsuarioResponseDto response = new UsuarioResponseDto(usuario);

            return ResponseEntity.status(HttpStatus.OK).body(response);
          
        } catch (Exception e) {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el usuario con el ID proporcionado.");
        }
    }
    
	@Operation(summary = "Obtener monopatines cerca", description = "Devuelve una lista de monopatines cerca de una ubicación "
			+ "pasada por parámetro.")
    @GetMapping("/obtenerMonopatinCerca/{latitud}/{longitud}")
    @PreAuthorize( "hasAuthority( \"" + AuthorityConstants.ADMIN + "\" )" + "|| hasAuthority( \"" + AuthorityConstants.USER + "\" )" )
    public List<MonopatinesCercaResponseDto> ObtenerMonopatinesCerca(@PathVariable double latitud, @PathVariable double longitud){
    	System.out.println("hola controller usuario");
    	try{
            return usuarioService.obtenerMonopatinesCerca(latitud, longitud);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
	@Operation(summary = "Mail usuario", description = "Devuelve el email del usuario.")
    @GetMapping("/email/{email}")
    public ResponseEntity<?> getByEmail(@PathVariable String email) {
        try {
            Usuario usuario = this.usuarioService.findByEmail(email);
            List<String> authorities = usuario.getAuthorities().stream().map(Authority::getName).collect(Collectors.toList());
            AuthUserDTO dto= new AuthUserDTO(usuario.getEmail(),usuario.getPassword(),authorities);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error, no existe el usuario");
        }
    }
    

}
