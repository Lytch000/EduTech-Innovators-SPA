package com.EduTech.assembler;

import com.EduTech.assemblers.CursoAssembler;
import com.EduTech.dto.cursoDTO.CursoDTO;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.assertj.core.api.Assertions.assertThat;

class CursoAssemblerTest {

    @Test
    void agregaEnlaceAsignarEstudiante() {
        CursoAssembler assembler = new CursoAssembler();
        CursoDTO dto = new CursoDTO();
        dto.setIdCurso(1L);

        EntityModel<CursoDTO> model = assembler.toModel(dto);

        Link link = model.getLink("asignar-estudiante").orElse(null);
        assertThat(link).isNotNull();
        assertThat(link.getHref()).contains("/api/v2/cursos/asignar-estudiante/1");
    }

    @Test
    void agregaEnlaceRemoverEstudiante() {
        CursoAssembler assembler = new CursoAssembler();
        CursoDTO dto = new CursoDTO();
        dto.setIdCurso(1L);

        EntityModel<CursoDTO> model = assembler.toModel(dto);

        Link link = model.getLink("remover-estudiante").orElse(null);
        assertThat(link).isNotNull();
        assertThat(link.getHref()).contains("/api/v2/cursos/remover-estudiante/1");
    }
}
