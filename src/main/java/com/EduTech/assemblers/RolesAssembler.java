package com.EduTech.assemblers;

import com.EduTech.controller.RolesControllerV2;
import com.EduTech.dto.roles.RolesDTO;
import com.EduTech.model.Roles;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

/**
 * Assembler para convertir un RolesDTO en un EntityModel con enlaces HATEOAS.
 */
@Component
public class RolesAssembler implements RepresentationModelAssembler<RolesDTO, EntityModel<RolesDTO>> {

    @Override
    public EntityModel<RolesDTO> toModel(RolesDTO rolDTO) {
        Long id = rolDTO.getId();

        return EntityModel.of(
                rolDTO,
                linkTo(methodOn(RolesControllerV2.class).listar()).withRel("roles"),
                linkTo(methodOn(RolesControllerV2.class).addNewRol(null)).withRel("crear"),
                linkTo(methodOn(RolesControllerV2.class).updateRol(id, null)).withRel("actualizar"),
                linkTo(methodOn(RolesControllerV2.class).deleteRol(id)).withRel("eliminar")
        );
    }
}
