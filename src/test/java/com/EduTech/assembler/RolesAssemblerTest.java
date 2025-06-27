package com.EduTech.assembler;

import com.EduTech.assemblers.RolesAssembler;
import com.EduTech.dto.roles.RolesDTO;
import com.EduTech.model.Roles;

import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.assertj.core.api.Assertions.assertThat;

class RolesAssemblerTest {

    @Test
    void agregaEnlaceRoles() {
        // Arrange
        RolesAssembler assembler = new RolesAssembler();
        Roles rol = new Roles();
        rol.setId(1L);
        RolesDTO dto = new RolesDTO(rol);

        // Act
        EntityModel<RolesDTO> model = assembler.toModel(dto);

        // Assert
        Link rolesLink = model.getLink("roles").orElse(null);
        assertThat(rolesLink).isNotNull();
        assertThat(rolesLink.getRel().value()).isEqualTo("roles");
        assertThat(rolesLink.getHref()).contains("/api/v2/roles");
    }

    @Test
    void agregaEnlaceCrear() {
        RolesAssembler assembler = new RolesAssembler();
        Roles rol = new Roles();
        rol.setId(1L);
        RolesDTO dto = new RolesDTO(rol);

        EntityModel<RolesDTO> model = assembler.toModel(dto);

        Link crearLink = model.getLink("crear").orElse(null);
        assertThat(crearLink).isNotNull();
        assertThat(crearLink.getRel().value()).isEqualTo("crear");
        assertThat(crearLink.getHref()).contains("/api/v2/roles");
    }

    @Test
    void agregaEnlaceActualizar() {
        RolesAssembler assembler = new RolesAssembler();
        Roles rol = new Roles();
        rol.setId(1L);
        RolesDTO dto = new RolesDTO(rol);

        EntityModel<RolesDTO> model = assembler.toModel(dto);

        Link actualizarLink = model.getLink("actualizar").orElse(null);
        assertThat(actualizarLink).isNotNull();
        assertThat(actualizarLink.getRel().value()).isEqualTo("actualizar");
        assertThat(actualizarLink.getHref()).contains("/api/v2/roles/update/1");
    }

    @Test
    void agregaEnlaceEliminar() {
        RolesAssembler assembler = new RolesAssembler();
        Roles rol = new Roles();
        rol.setId(1L);
        RolesDTO dto = new RolesDTO(rol);

        EntityModel<RolesDTO> model = assembler.toModel(dto);

        Link eliminarLink = model.getLink("eliminar").orElse(null);
        assertThat(eliminarLink).isNotNull();
        assertThat(eliminarLink.getRel().value()).isEqualTo("eliminar");
        assertThat(eliminarLink.getHref()).contains("/api/v2/roles/delete/1");
    }
}
