package com.EduTech.controller;

import com.EduTech.assemblers.UsuariosAssembler;
import com.EduTech.dto.user.UsuarioDTO;
import com.EduTech.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioControllerV2.class)
public class UsuarioControllerV2Test {

    @MockBean
    private UsuarioService service;

    @MockBean
    private UsuariosAssembler assembler;

    @Autowired
    private MockMvc mock;

    @Test
    void login() throws Exception {
        String email = "curso@prueba.com";
        String password = "1234567890.";

        UsuarioDTO dto = new UsuarioDTO(email, password);

        // Simula el assembler devolviendo un EntityModel con el DTO
        when(service.login(email, password)).thenReturn(dto);
        when(assembler.toModel(dto)).thenReturn(EntityModel.of(dto));

        mock.perform(post("/api/v2/usuarios/login")
                .contentType("application/json")
                .content("{\"email\":\"curso@prueba.com\",\"password\":\"1234567890.\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value(email));
    }
}
