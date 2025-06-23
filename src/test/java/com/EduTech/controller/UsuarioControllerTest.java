package com.EduTech.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.EduTech.dto.user.UsuarioDTO;
import com.EduTech.repository.UsuarioRepository;
import com.EduTech.service.UsuarioService;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService service;

    @MockBean 
    private UsuarioRepository repository;

    @Test 
    void login() throws Exception {
        
    String email = "curso@prueba.com";
    String password = "1234567890.";

    UsuarioDTO usuarioDTO = new UsuarioDTO(email, password);
    when(service.login(email, password)).thenReturn(usuarioDTO);

        mockMvc.perform(post("/api/v1/usuarios/login")
            .contentType("application/json")
            .content("{\"email\":\"curso@prueba.com\",\"password\":\"1234567890.\"}"))
            .andExpect(status().isOk());
    }

    @Test
    void loginFallido() throws Exception {
        String email = "curso@prueba.com";
        String password = "1234567890.";

        when(service.login(email, password)).thenThrow(new RuntimeException("Credenciales inválidas"));

        mockMvc.perform(post("/api/v1/usuarios/login")
                .contentType("application/json")
                .content("{\"email\":\"curso@prueba.com\",\"password\":\"1234567890.\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(result -> 
                    result.getResponse().getContentAsString().contains("Credenciales inválidas")
                );
    }

    

}
