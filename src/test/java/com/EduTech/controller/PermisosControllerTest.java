package com.EduTech.controller;

import com.EduTech.dto.permissions.CrearPermisoDto;
import com.EduTech.dto.permissions.PermisoConRolDto;
import com.EduTech.dto.permissions.PermisoDto;
import com.EduTech.dto.permissions.ActualizarPermisoDto;
import com.EduTech.service.PermisoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PermisoController.class)
public class PermisosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PermisoService permisoService;

    @Test
    void getAllPermisos() throws Exception {
        PermisoDto permiso = new PermisoDto();
        Mockito.when(permisoService.getAllPermisos()).thenReturn(List.of(permiso));
        mockMvc.perform(get("/api/v1/permisos"))
                .andExpect(status().isOk());
    }

    @Test
    void createPermiso() throws Exception {
        PermisoDto permiso = new PermisoDto();
        Mockito.when(permisoService.createPermiso(any(CrearPermisoDto.class))).thenReturn(permiso);

        mockMvc.perform(post("/api/v1/permisos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"CREAR_USUARIO\",\"descripcion\":\"Permite crear usuarios\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void updatePermiso() throws Exception {
        PermisoDto permiso = new PermisoDto();
        Mockito.when(permisoService.updatePermiso(eq(1L), any(ActualizarPermisoDto.class))).thenReturn(permiso);

        mockMvc.perform(patch("/api/v1/permisos/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"EDITAR_USUARIO\",\"descripcion\":\"Permite editar usuarios\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void assignToRole() throws Exception {
        PermisoConRolDto permisoConRol = new PermisoConRolDto();
        Mockito.when(permisoService.assignToRole(1L, 2L)).thenReturn(permisoConRol);

        mockMvc.perform(patch("/api/v1/permisos/assign-to-role/1/2"))
                .andExpect(status().isOk());
    }

    @Test
    void softDeletePermiso() throws Exception {
        PermisoDto permiso = new PermisoDto();
        Mockito.when(permisoService.softDeletePermiso(1L)).thenReturn(permiso);

        mockMvc.perform(patch("/api/v1/permisos/soft-delete/1"))
                .andExpect(status().isOk());
    }
}