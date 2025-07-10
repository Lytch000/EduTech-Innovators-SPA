package com.EduTech.assemblers;
import com.EduTech.dto.cursoDTO.CursoDTO;
import com.EduTech.controller.CursoControllerV2;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CursoModelAssembler implements RepresentationModelAssembler<CursoDTO, EntityModel<CursoDTO>> {
    @Override
    public EntityModel<CursoDTO> toModel(CursoDTO curso) {
        return EntityModel.of(
                curso,
                            linkTo(methodOn(CursoControllerV2.class).listar()).withRel("cursos"),
            linkTo(methodOn(CursoControllerV2.class).deleteCurso(curso.getIdCurso())).withRel("eliminar"),
            linkTo(methodOn(CursoControllerV2.class).removerProfesor(curso.getIdCurso())).withRel("removerProfesor"),
            linkTo(methodOn(CursoControllerV2.class).asignarProfesor(curso.getIdCurso(), null)).withRel("asignarProfesor"),
            linkTo(methodOn(CursoControllerV2.class).actualizarCurso(null)).withRel("actualizar")

               // linkTo(methodOn(CursoControllerV2.class).listar()).withRel("cursos"),
               // linkTo(CursoControllerV2.class)
           // .slash("delete")
            //.slash(curso.getIdCurso())
            //.withRel("eliminar"),
            //linkTo(CursoControllerV2.class)
            //.slash("remover-profesor")
            //.slash(curso.getIdCurso())
            //.withRel("removerProfesor"),
           // linkTo(CursoControllerV2.class)
            //.slash("asignar-profesor")
            //.slash(curso.getIdCurso())
            //.slash("{idUsuario}") 
            //.withRel("asignarProfesor"),
            //linkTo(CursoControllerV2.class)
            //.withRel("actualizar")
        );
    }

}
