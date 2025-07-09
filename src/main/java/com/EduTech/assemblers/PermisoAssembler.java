package com.EduTech.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.EduTech.controller.PermisoControllerV2;
import com.EduTech.dto.permissions.PermisoDto;

/**
 * Assembler para convertir un PermisoDto en un EntityModel con enlaces HATEOAS.
 * Proporciona enlaces a todos los endpoints relacionados con permisos.
 */
@Component
public class PermisoAssembler implements RepresentationModelAssembler<PermisoDto, EntityModel<PermisoDto>> {
    
    @Override
    public EntityModel<PermisoDto> toModel(PermisoDto permisoDto) {
        return EntityModel.of(
            permisoDto,
            linkTo(methodOn(PermisoControllerV2.class).getAllPermisos()).withRel("permisos"),
            linkTo(methodOn(PermisoControllerV2.class).getOnePermiso(permisoDto.getId())).withSelfRel(),
            linkTo(methodOn(PermisoControllerV2.class).createPermiso(null)).withRel("crear-permiso"),
            linkTo(methodOn(PermisoControllerV2.class).updatePermiso(permisoDto.getId(), null)).withRel("actualizar-permiso"),
            linkTo(methodOn(PermisoControllerV2.class).assignToRole(permisoDto.getId(), null)).withRel("asignar-rol"),
            linkTo(methodOn(PermisoControllerV2.class).softDeletePermiso(permisoDto.getId())).withRel("eliminar-permiso")
        );
    }
}
