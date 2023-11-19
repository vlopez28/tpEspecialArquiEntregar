package com.integrador.service;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.integrador.domain.Cuenta;
import com.integrador.repository.CuentaRepository;
import com.integrador.service.dto.cuenta.CuentaRequestDto;
import com.integrador.service.dto.cuenta.CuentaResponseDto;
import com.integrador.service.exception.NotFoundException;


//carga extensiones de mockito q tambien usan junit5
@ExtendWith(MockitoExtension.class)
public class CuentaServiceTest {
	
	//si para hacer una prueba solo usamos una clase, solo usamos junit, si usamos mas de una clase usamos mockito
	//crea objetos simulados
	//si usamos mockito, lo q hace es hacer una copia un doble
	//por ejemplo del repo, cuando vayamos a pegarle a un metodo del repo
	//lo atrapa mockito y responde lo q nostros configuramos q responda
	//logra q no nos conectemos a la bd xq sino seria un aprueba de integracion
	
	
	//crea un simulacro de repo
	@Mock
	private  CuentaRepository cuentaRepository;
	
	
	//inyecta el mockservice en el mock repo, inyecta obj simulado en otro simulado
	@InjectMocks
	private CuentaService cuentaService;
	
	private CuentaResponseDto result;
	private Cuenta cuenta;
	
	private CuentaRequestDto cuentaRequestDto;

	@BeforeEach
	public void setUp() {
		
		Timestamp fecha1 = Timestamp.valueOf("2023-04-24 10:10:10.0");
		cuentaRequestDto = new CuentaRequestDto(510, fecha1, true);
		
        cuenta = new Cuenta(cuentaRequestDto);
        cuentaRequestDto.setId(1L);
        cuenta.setId(1L);
		//cuentaResponseDto = new CuentaResponseDto(new Cuenta(cr1));
	}

	@Test
	public void testFindAll() {
		List<Cuenta> cuentas = new ArrayList<>();
		cuentas.add(cuenta);
		//simulo un comportamiento con mockito
		when(cuentaRepository.findAll()).thenReturn(cuentas);
		//verifico que el resultado de cuentaService.findAll() no sea nulo 
		Assertions.assertNotNull(cuentaService.findAll());
		
	}
	
	//verifica que el metodo save funcionen correctamente al interactuar con el CuentaRepository
	@DisplayName("Test para guardar una cuenta")
	@Test
	public void testSave() {
		
		when(cuentaRepository.save(any(Cuenta.class))).thenReturn(cuenta);
		
		result = cuentaService.save(cuentaRequestDto);
		//pruebo q el save no sea nulo
		Assertions.assertNotNull(result);
		
	}
	
		
	@DisplayName("Test para buscar una cuenta segun id")
	@Test
    void testFindById() {
 
        when(cuentaRepository.findById(cuenta.getId())).thenReturn(Optional.of(cuenta));

        CuentaResponseDto result = cuentaService.findById(cuenta.getId());
       
        Assertions.assertNotNull(result);
        assertThat(result).isNotNull();
    }
	
	
	@DisplayName("testea q se lance una excepcion si se elimina una entidad que no existe")
	@Test
    void testDeleteEntityNotExist() {
		 when(cuentaRepository.findById(cuenta.getId())).thenReturn(Optional.empty());

		 Assertions.assertThrows(NotFoundException.class, () -> cuentaService.delete(cuenta.getId()));

    }
	
	@DisplayName("testea q no se lance un excepcion si borra correctamente")
	@Test
    void testDelete() {
      
        when(cuentaRepository.findById(cuenta.getId())).thenReturn(Optional.of(cuenta));
        doNothing().when(cuentaRepository).delete(cuenta);

        //si borra correctamente no debe lanzar un exception
        Assertions.assertDoesNotThrow(() -> cuentaService.delete(cuenta.getId()));
        //solo se llama 1 vez a cada metodo
        verify(cuentaRepository, times(1)).findById(cuenta.getId());
        verify(cuentaRepository, times(1)).delete(cuenta);
    }
    
    @Test
    void testUpdate() {
    	
    	
    	when(cuentaRepository.findById(cuenta.getId())).thenReturn(Optional.of(cuenta));
        when(cuentaRepository.save(any())).thenReturn(cuenta);
    	
    	
    	cuentaRequestDto.setSaldo(1000);
    	cuentaRequestDto.setDisponible(false);
    	
    	Cuenta cuentaActualizada = cuentaService.update(cuentaRequestDto.getId(), cuentaRequestDto);
    	
    	Assertions.assertNotNull(cuentaActualizada);
        assertThat(cuentaActualizada.getSaldo()).isEqualTo(1000);
        assertThat(cuentaActualizada.isDisponible()).isEqualTo(false);
        
    }

	
	
	
	
}
