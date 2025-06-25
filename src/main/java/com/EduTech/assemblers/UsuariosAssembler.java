package com.EduTech.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import javax.swing.text.html.parser.Entity;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.EduTech.controller.UsuarioController;
import com.EduTech.controller.UsuarioControllerV2;
import com.EduTech.dto.user.LoginRequest;
import com.EduTech.dto.user.UsuarioDTO;

/**
 * Assembler para convertir un RolesDTO en un EntityModel con enlaces HATEOAS.
 */

@Component
public class UsuariosAssembler implements RepresentationModelAssembler<UsuarioDTO, EntityModel<UsuarioDTO>> {
    @Override
    public EntityModel<UsuarioDTO> toModel(UsuarioDTO usuarioDTO){
        return EntityModel.of(
            usuarioDTO,
            linkTo(methodOn(UsuarioControllerV2.class).login(new LoginRequest())).withRel("login")
        );
    }
}
