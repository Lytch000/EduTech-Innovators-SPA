package com.EduTech.controller;

import com.EduTech.assemblers.RolesAssembler;
import com.EduTech.dto.roles.RolesDTO;
import com.EduTech.model.Roles;
import com.EduTech.service.RolesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RolesControllerV2.class)
class RolesControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RolesService rolesService;

    @MockBean
    private RolesAssembler assembler;

    @Test
    void listar() throws Exception {
        Roles rol = new Roles();
        rol.setId(1L);
        rol.setNombre("Admin");
        rol.setDescripcion("Administrador del sistema");
        rol.setFechaCreacion(new Date());
        RolesDTO rolDTO = new RolesDTO(rol);

        when(rolesService.listar()).thenReturn(List.of(rolDTO));
        when(assembler.toModel(any(RolesDTO.class))).thenReturn(EntityModel.of(rolDTO));

        mockMvc.perform(get("/api/v2/roles"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.rolesDTOList[0].id").value(1))
            .andExpect(jsonPath("$._embedded.rolesDTOList[0].nombre").value("Admin"))
            .andExpect(jsonPath("$._embedded.rolesDTOList[0].descripcion").value("Administrador del sistema"))
            .andExpect(jsonPath("$._embedded.rolesDTOList[0].fechaCreacion").isNotEmpty());
    }

    @Test
    void listarSinContenido() throws Exception {
        when(rolesService.listar()).thenReturn(List.of());

        mockMvc.perform(get("/api/v2/roles"))
            .andExpect(status().isNoContent());
    }

    @Test
    void addNewRol() throws Exception {
        Roles rol = new Roles();
        rol.setId(1L);
        rol.setNombre("Admin");
        rol.setDescripcion("Administrador del sistema");
        rol.setFechaCreacion(new Date());
        Roles savedRol = new Roles(1L, "Admin", "Administrador del sistema", new Date(), null, null);
        RolesDTO dto = new RolesDTO(savedRol);

        when(rolesService.addNewRol(any(Roles.class))).thenReturn(savedRol);
        when(assembler.toModel(any(RolesDTO.class))).thenReturn(EntityModel.of(dto));

        mockMvc.perform(post("/api/v2/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Admin\", \"descripcion\":\"Administrador del sistema\", \"fechaCreacion\":\"2025-06-16T00:00:00.000+00:00\"}"))
            .andExpect(status().isOk());
    }

    @Test
    void addNewRolCamposVacios() throws Exception {
        when(rolesService.addNewRol(any(Roles.class)))
            .thenThrow(new IllegalArgumentException("Campo vacío"));

        mockMvc.perform(post("/api/v2/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"\", \"descripcion\":\"\", \"fechaCreacion\":null}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void addNewRolDuplicado() throws Exception {
        when(rolesService.addNewRol(any(Roles.class)))
            .thenThrow(new IllegalArgumentException("Rol ya existe"));

        mockMvc.perform(post("/api/v2/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Admin\", \"descripcion\":\"Administrador del sistema\", \"fechaCreacion\":\"2025-06-16T00:00:00.000+00:00\"}"))
            .andExpect(status().isConflict());
    }

    @Test
    void deleteRol() throws Exception {
        when(rolesService.deleteRol(1L)).thenReturn("Rol eliminado correctamente");

        mockMvc.perform(delete("/api/v2/roles/delete/{id}", 1L))
            .andExpect(status().isOk())
            .andExpect(content().string("Rol eliminado correctamente"));
    }

    @Test
    void updateRolOk() throws Exception {
        when(rolesService.updateRol(eq(1L), any(Roles.class)))
            .thenReturn("Rol actualizado correctamente");

        mockMvc.perform(put("/api/v2/roles/update/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Admin\", \"descripcion\":\"Administrador actualizado\", \"fechaCreacion\":\"2025-06-16T00:00:00.000+00:00\"}"))
            .andExpect(status().isOk())
            .andExpect(content().string("Rol actualizado correctamente"));
    }

    @Test
    void updateRolNoEncontrado() throws Exception {
        when(rolesService.updateRol(eq(1L), any(Roles.class)))
            .thenReturn("No se encuentra rol indicado");

        mockMvc.perform(put("/api/v2/roles/update/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Admin\", \"descripcion\":\"Administrador actualizado\", \"fechaCreacion\":\"2025-06-16T00:00:00.000+00:00\"}"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("No se encuentra rol indicado"));
    }
}
