package com.EduTech.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import java.util.Collections;
import java.util.List;
import com.EduTech.dto.user.CrearUsuarioDto;
import com.EduTech.dto.user.RespuestaUsuarioDto;
import com.EduTech.dto.user.ActualizarUsuarioDto;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

    @Test
    void getAllUsersConUsuarios() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO(1L, "Franco", "Carrasco", "fcobreque1204@gmail.com");

        RespuestaUsuarioDto user = new RespuestaUsuarioDto(usuarioDTO);

        when(service.getUsers()).thenReturn(List.of(user));

        mockMvc.perform(get("/api/v1/usuarios"))
            .andExpect(status().isOk());
    }

    @Test
    void getAllUsersSinUsuarios() throws Exception {
        when(service.getUsers()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/usuarios"))
            .andExpect(status().isNoContent());
    }

    @Test
    void createUser() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO(1L, "Franco", "Carrasco", "fcobreque1204@gmail.com");

        RespuestaUsuarioDto respuesta = new RespuestaUsuarioDto(usuarioDTO);

        when(service.createUser(any(CrearUsuarioDto.class))).thenReturn(respuesta);

        mockMvc.perform(post("/api/v1/usuarios")
            .contentType("application/json")
            .content("{\"phoneNumber\": 987654321,\"rolId\": 2,\"firstName\": \"Franco\",\"lastName\": \"Carrasco\",\"rut\": \"12345678-9\",\"email\": \"fcobreque1204@gmail.com\",\"password\": \"123456\",\"birthDate\": \"1990-01-01\"}"))
            .andExpect(status().isCreated());
    }

    @Test
    void getOneUser() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO(1L, "Franco", "Carrasco", "fcobreque1204@gmail.com");

        RespuestaUsuarioDto respuesta = new RespuestaUsuarioDto(usuarioDTO);

        when(service.getOneUser(1L)).thenReturn(respuesta);

        mockMvc.perform(get("/api/v1/usuarios/id/1"))
            .andExpect(status().isOk());
    }

    @Test
    void updateUser_exitoso() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO(1L, "Franco", "Carrasco", "fcobreque1204@gmail.com");
        RespuestaUsuarioDto respuesta = new RespuestaUsuarioDto(usuarioDTO);

        when(service.updateUser(eq(1L), any(ActualizarUsuarioDto.class))).thenReturn(respuesta);

        mockMvc.perform(patch("/api/v1/usuarios/id/1")
            .contentType("application/json")
            .content("{\"phoneNumber\":987654321,\"roleId\":2,\"email\":\"nuevoemail@gmail.com\"}"))
            .andExpect(status().isOk());
    }

    @Test
    void updateUser_usuarioNoEncontrado() throws Exception {
        when(service.updateUser(eq(1L), any(ActualizarUsuarioDto.class))).thenThrow(new NoSuchElementException());
        mockMvc.perform(patch("/api/v1/usuarios/id/1")
            .contentType("application/json")
            .content("{}"))
            .andExpect(status().isNotFound());
    }

    @Test
    void updateUser_argumentoInvalido() throws Exception {
        when(service.updateUser(eq(1L), any(ActualizarUsuarioDto.class))).thenThrow(new IllegalArgumentException("Datos inválidos"));
        mockMvc.perform(patch("/api/v1/usuarios/id/1")
            .contentType("application/json")
            .content("{}"))
            .andExpect(status().isBadRequest())
            .andExpect(result -> result.getResponse().getContentAsString().contains("Datos inválidos"));
    }

    @Test
    void deleteUser_exitoso() throws Exception {
        when(service.deleteUser(1L)).thenReturn("Usuario eliminado");
        mockMvc.perform(delete("/api/v1/usuarios/1"))
            .andExpect(status().isOk());
    }

    @Test
    void deleteUser_noEncontrado() throws Exception {
        when(service.deleteUser(1L)).thenThrow(new NoSuchElementException());
        mockMvc.perform(delete("/api/v1/usuarios/1"))
            .andExpect(status().isNotFound());
    }
}
