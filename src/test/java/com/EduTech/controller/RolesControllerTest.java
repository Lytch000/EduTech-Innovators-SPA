package com.EduTech.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest; // <-- IMPORTANTE
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.List;

import com.EduTech.dto.roles.RolesDTO;
import com.EduTech.model.Roles;
import com.EduTech.repository.RolesRepository;
import com.EduTech.service.RolesService;

@WebMvcTest(RolesController.class)
public class RolesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RolesService rolesService;

    @MockBean
    private RolesRepository rolesRepository;

    @Test
    void addNewRol() throws Exception {
        Roles rol = new Roles();
        rol.setId(1L);
        rol.setNombre("Admin");
        rol.setDescripcion("Administrador del sistema");
        rol.setFechaCreacion(new Date());

        when(rolesService.addNewRol(any(Roles.class))).thenReturn(rol);

        mockMvc.perform(post("/api/v1/roles")
            .contentType("application/json")
            .content("{\"nombre\":\"Admin\", \"descripcion\":\"Administrador del sistema\", \"fechaCreacion\":\"2025-06-16T00:00:00.000+00:00\"}"))
            .andExpect(status().isOk());
    }

     @Test
     void addNewRolDuplicado() throws Exception {
        // Simula que el rol ya existe
        when(rolesService.addNewRol(any(Roles.class)))
            .thenThrow(new IllegalArgumentException("El rol ya existe"));

        mockMvc.perform(post("/api/v1/roles")
            .contentType("application/json")
            .content("{\"nombre\":\"Admin\", \"descripcion\":\"Administrador del sistema\", \"fechaCreacion\":\"2025-06-16T00:00:00.000+00:00\"}"))
            .andExpect(status().isConflict());
     }

    @Test
    void addNewRolSinNombre() throws Exception {
        when(rolesService.addNewRol(any(Roles.class)))
            .thenThrow(new IllegalArgumentException("El nombre del rol no puede estar vacío"));

        mockMvc.perform(post("/api/v1/roles")
            .contentType("application/json")
            .content("{\"descripcion\":\"desc\",\"fechaCreacion\":\"2025-06-16T00:00:00.000+00:00\"}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void addNewRolSinDescripcion() throws Exception {
        when(rolesService.addNewRol(any(Roles.class)))
            .thenThrow(new IllegalArgumentException("La descripción del rol no puede estar vacía"));

        mockMvc.perform(post("/api/v1/roles")
            .contentType("application/json")
            .content("{\"nombre\":\"Admin\",\"fechaCreacion\":\"2025-06-16T00:00:00.000+00:00\"}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void addNewRolSinFechaCreacion() throws Exception {
        when(rolesService.addNewRol(any(Roles.class)))
            .thenThrow(new IllegalArgumentException("La fecha de creación no puede estar vacía"));

        mockMvc.perform(post("/api/v1/roles")
            .contentType("application/json")
            .content("{\"nombre\":\"Admin\",\"descripcion\":\"desc\"}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void listar() throws Exception {
        Roles rol = new Roles();
        rol.setId(1L);
        rol.setNombre("Admin");
        rol.setDescripcion("Administrador del sistema");
        rol.setFechaCreacion(new Date()); 
        RolesDTO rolDTO = new RolesDTO(rol);

        when(rolesService.listar()).thenReturn(List.of(rolDTO));


        mockMvc.perform(get("/api/v1/roles"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].nombre").value("Admin"))
            .andExpect(jsonPath("$[0].descripcion").value("Administrador del sistema"))
            .andExpect(jsonPath("$[0].fechaCreacion").isNotEmpty());

    }

    
}
