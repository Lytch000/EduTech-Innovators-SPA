package com.EduTech.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest; // <-- IMPORTANTE
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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

    @Test
    void listarSinContenido() throws Exception {
        when(rolesService.listar()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/roles"))
            .andExpect(status().isNoContent());
    }

    @Test
    void deleteRol() throws Exception {
        Long id = 1L;
        when(rolesService.deleteRol(id)).thenReturn("Rol eliminado correctamente");

        mockMvc.perform(delete("/api/v1/roles/delete/{id}", id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value("Rol eliminado correctamente"));
    }

    @Test
    void deleteRolNoEncontrado() throws Exception {
        Long id = 1L;
        when(rolesService.deleteRol(id)).thenReturn("No se encuentra rol especificado");

        mockMvc.perform(delete("/api/v1/roles/delete/{id}", id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value("No se encuentra rol especificado"));
    }

    @Test
    void updateRol() throws Exception {
        Long id = 1L;

        when(rolesService.updateRol(org.mockito.Mockito.eq(id), any(Roles.class)))
            .thenReturn("Rol actualizado correctamente");

        mockMvc.perform(put("/api/v1/roles/update/{id}", id)
            .contentType("application/json")
            .content("{\"nombre\":\"Admin\", \"descripcion\":\"Administrador del sistema actualizado\", \"fechaCreacion\":\"2025-06-16T00:00:00.000+00:00\"}"))
            .andExpect(status().isOk())
            .andExpect(content().string("Rol actualizado correctamente"));
    }

    @Test
    void updateRolNoEncontrado() throws Exception {
        Long id = 1L;

        when(rolesService.updateRol(org.mockito.Mockito.eq(id), any(Roles.class)))
            .thenReturn("No se encuentra rol indicado");

        mockMvc.perform(put("/api/v1/roles/update/{id}", id)
            .contentType("application/json")
            .content("{\"nombre\":\"Admin\", \"descripcion\":\"Administrador del sistema actualizado\", \"fechaCreacion\":\"2025-06-16T00:00:00.000+00:00\"}"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("No se encuentra rol indicado"));
    }
    
}
