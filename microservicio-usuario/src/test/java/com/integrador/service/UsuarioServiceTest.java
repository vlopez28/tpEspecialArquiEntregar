package com.integrador.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.integrador.domain.Usuario;
import com.integrador.repository.UsuarioRepository;
import com.integrador.service.dto.usuario.UsuarioRequestDto;
import com.integrador.service.dto.usuario.UsuarioResponseDto;
import com.integrador.service.exception.NotFoundException;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

	@Mock
	private  UsuarioRepository usuarioRepository;
	
	
	//inyecta el mockservice en el mock repo, inyecta obj simulado en otro simulado
	@InjectMocks
	private UsuarioService usuarioService;
	
	private UsuarioResponseDto result;
	private Usuario usuario;
	
	private UsuarioRequestDto usuarioRequestDto;

	@BeforeEach
	public void setUp() {
		Set<Long> cuentas = new HashSet<>();
	    Set<String> authorities = new HashSet<>();
	    authorities.add("ADMIN");
	    authorities.add("USER");
		usuarioRequestDto = new UsuarioRequestDto("Lopez", "Victoria", "vlopez@gmail.com", "154443322", "vlopez", "1234", cuentas, authorities);
		usuario = new Usuario(usuarioRequestDto);
        usuarioRequestDto.setId(1L);
        usuario.setId(1L);
	}

	@Test
	public void testFindAll() {
		List<Usuario> usuarios = new ArrayList<>();
		usuarios.add(usuario);
		//simulo un comportamiento con mockito
		when(usuarioRepository.findAll()).thenReturn(usuarios);
		//verifico que el resultado de cuentaService.findAll() no sea nulo 
		Assertions.assertNotNull(usuarioService.findAll());
		
	}
	
	//verifica que el metodo save funcionen correctamente al interactuar con el usuarioRepository
	@DisplayName("Test para guardar unu usuario")
	@Test
	public void testSave() {
		
		when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
		
		result = usuarioService.save(usuarioRequestDto);
		//pruebo q el save no sea nulo
		Assertions.assertNotNull(result);
		
	}
	
		
	@DisplayName("Test para buscar un usuario segun id")
	@Test
    void testFindById() {
 
        when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));

        UsuarioResponseDto result = usuarioService.findById(usuario.getId());
       
        Assertions.assertNotNull(result);
        assertThat(result).isNotNull();
    }
	
	
	@DisplayName("testea q se lance una excepcion si se elimina una entidad que no existe")
	@Test
    void testDeleteEntityNotExist() {
		 when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.empty());

		 Assertions.assertThrows(NotFoundException.class, () -> usuarioService.delete(usuario.getId()));

    }
	
	@DisplayName("testea q no se lance un excepcion si borra correctamente")
	@Test
    void testDelete() {
      
        when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));
        doNothing().when(usuarioRepository).delete(usuario);

        //si borra correctamente no debe lanzar un exception
        Assertions.assertDoesNotThrow(() -> usuarioService.delete(usuario.getId()));
        //solo se llama 1 vez a cada metodo
        verify(usuarioRepository, times(1)).findById(usuario.getId());
        verify(usuarioRepository, times(1)).delete(usuario);
    }
    
    @Test
    void testUpdate() {
    	
    	
    	when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any())).thenReturn(usuario);
    	
    	
        usuarioRequestDto.setApellido("Sanchez");;
        usuarioRequestDto.setNombre("Maria");;
    	
    	Usuario usuarioActualizado = usuarioService.update(usuarioRequestDto.getId(), usuarioRequestDto);
    	
    	Assertions.assertNotNull(usuarioActualizado);
        assertThat(usuarioActualizado.getApellido()).isEqualTo("Sanchez");
        assertThat(usuarioActualizado.getNombre()).isEqualTo("Maria");
        
    }

	
	
}
