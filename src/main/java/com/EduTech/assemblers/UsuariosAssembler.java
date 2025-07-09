package com.EduTech.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.EduTech.controller.UsuarioControllerV2;
import com.EduTech.dto.user.LoginRequest;
import com.EduTech.dto.user.UsuarioDTO;

/**
 * Assembler para convertir un UsuarioDTO en un EntityModel con enlaces HATEOAS.
 * Proporciona enlaces a todos los endpoints relacionados con usuarios.
 */
@Component
public class UsuariosAssembler implements RepresentationModelAssembler<UsuarioDTO, EntityModel<UsuarioDTO>> {
    
    @Override
    public EntityModel<UsuarioDTO> toModel(UsuarioDTO usuarioDTO) {
        return EntityModel.of(
            usuarioDTO,
            linkTo(methodOn(UsuarioControllerV2.class).getAllUsers()).withRel("usuarios"),
            linkTo(methodOn(UsuarioControllerV2.class).getOneUser(usuarioDTO.getId())).withSelfRel(),
            linkTo(methodOn(UsuarioControllerV2.class).createUser(null)).withRel("crear-usuario"),
            linkTo(methodOn(UsuarioControllerV2.class).updateUser(usuarioDTO.getId(), null)).withRel("actualizar-usuario"),
            linkTo(methodOn(UsuarioControllerV2.class).deleteUser(usuarioDTO.getId())).withRel("eliminar-usuario"),
            linkTo(methodOn(UsuarioControllerV2.class).login(new LoginRequest())).withRel("login"),
            linkTo(methodOn(UsuarioControllerV2.class).obtenerDetalleProfesor(usuarioDTO.getId())).withRel("detalle-profesor")
        );
    }
}
