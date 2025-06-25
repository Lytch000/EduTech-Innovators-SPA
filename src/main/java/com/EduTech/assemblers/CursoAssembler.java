package com.EduTech.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.EduTech.controller.CursoControllerV2;
import com.EduTech.dto.cursoDTO.CursoDTO;

/**
 * Assembler para convertir un CursoDTO en un EntityModel con enlaces HATEOAS.
 */
@Component
public class CursoAssembler implements RepresentationModelAssembler<CursoDTO, EntityModel<CursoDTO>> {
    @Override
    public EntityModel<CursoDTO> toModel(CursoDTO cursoDTO){
        Long idCurso = cursoDTO.getIdCurso();

        return EntityModel.of(
            cursoDTO,
            linkTo(methodOn(CursoControllerV2.class).asignarEstudiante(idCurso, null)).withRel("asignar-estudiante"),
            linkTo(methodOn(CursoControllerV2.class).removerEstudiante(idCurso, null)).withRel("remover-estudiante")
        );
    }
}

