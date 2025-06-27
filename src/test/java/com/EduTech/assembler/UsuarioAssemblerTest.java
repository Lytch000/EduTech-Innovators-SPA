package com.EduTech.assembler;

import com.EduTech.assemblers.UsuariosAssembler;
import com.EduTech.dto.user.UsuarioDTO;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.assertj.core.api.Assertions.assertThat;

class UsuarioAssemblerTest {

    @Test
    void agregaEnlaceLogin() {
        UsuariosAssembler assembler = new UsuariosAssembler();

        UsuarioDTO dto = new UsuarioDTO(1L, "test", "test", "test@correo.com");

        EntityModel<UsuarioDTO> model = assembler.toModel(dto);

        Link loginLink = model.getLink("login").orElse(null);
        assertThat(loginLink).isNotNull();
        assertThat(loginLink.getHref()).contains("/api/v2/usuarios/login");
    }

}
