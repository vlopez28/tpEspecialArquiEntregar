package com.integrador.controller;


import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.integrador.domain.Cuenta;
import com.integrador.service.CuentaService;
import com.integrador.service.dto.cuenta.CuentaRequestDto;
import com.integrador.service.dto.cuenta.CuentaResponseDto;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;


//anotacion q indica que se probara una clase de tipo controlador
//@WebMvcTest(CuentaController.class)
@ExtendWith(MockitoExtension.class)


//anotacion q indica que se probara una clase de tipo controlador
//@WebMvcTest(CuentaController.class)
@ExtendWith(MockitoExtension.class)

//anotacion q indica que se probara una clase de tipo controlador
//@WebMvcTest(CuentaController.class)
@ExtendWith(MockitoExtension.class)
public class CuentaControllerTest {
	

	
	//UNA CLASE QUE NO PERMITE HACER LLAMADAS HTTP	
//	@Autowired
//	private MockMvc mockMVC;
//	
//	@MockBean
//	private CuentaService cuentaService;
	
	
	@Mock
    private CuentaService cuentaService;

    @InjectMocks
    private CuentaController cuentaController;

    @Test
    public void getByIdTest() {
        // Mock data
        Long id = 1L;
        //creo una fecha 
        Timestamp fecha1 = Timestamp.valueOf("2023-04-24 10:10:10.0");
        CuentaRequestDto cr1 = new CuentaRequestDto(510, fecha1, true);
        CuentaResponseDto mockCuenta = new CuentaResponseDto(new Cuenta(cr1));
        
        when(cuentaService.findById(id)).thenReturn(mockCuenta);

        // Call the controller method
        ResponseEntity<?> result = cuentaController.getById(id);
        System.out.println("rdo"+result);
        // Verify the result
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assertions.assertEquals(mockCuenta, result.getBody());
    }
    
    
	
}
