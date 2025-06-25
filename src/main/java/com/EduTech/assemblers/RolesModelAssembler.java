package com.EduTech.assemblers;

import com.EduTech.controller.RolesControllerV2;
import com.EduTech.dto.roles.RolesDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

/**
 * Assembler para convertir un RolesDTO en un EntityModel con enlaces HATEOAS.
 */
@Component
public class RolesModelAssembler implements RepresentationModelAssembler<RolesDTO, EntityModel<RolesDTO>> {

    @Override
    public EntityModel<RolesDTO> toModel(RolesDTO rolDTO) {
        return EntityModel.of(
                rolDTO,
                // Link al recurso actual (self)
                linkTo(methodOn(RolesControllerV2.class).getRolById(rolDTO.getId())).withSelfRel(),
                // Link a la colección de roles
                linkTo(methodOn(RolesControllerV2.class).getAllRoles()).withRel("roles")
        );
    }
}
