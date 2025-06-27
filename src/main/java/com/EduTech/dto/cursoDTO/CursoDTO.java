//Victor garces
package com.EduTech.dto.cursoDTO;

import com.EduTech.model.Curso;
import com.EduTech.model.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * DTO para transferir datos de curso entre capas.
 * Autor: Victor Garces
 */
@Data
@NoArgsConstructor
@Schema(description = "DTO para transferir datos de curso")
public class CursoDTO {

    @Schema(description = "Identificador único del curso", example = "1")
    private Long idCurso;

    @Schema(description = "Nombre del curso", example = "Programación en Java")
    private String nombreCurso;

    @Schema(description = "Descripción del curso", example = "Curso introductorio de Java")
    private String descripcion;

    @Schema(description = "Categoría del curso", example = "Programación")
    private String categoria;

    @Schema(description = "Horas de duración del curso", example = "40")
    private int horasDuracion;

    @Schema(description = "Precio del curso", example = "199.99")
    private Double precioCurso;

    @Schema(description = "Fecha de publicación del curso", example = "2025-06-22T00:00:00.000+00:00")
    private Date fechaPublicacion;

    @Schema(description = "Profesor asignado al curso")
    private Usuario profesor;

    @Schema(description = "Estudiantes inscritos en el curso")
    private List<Usuario> estudiantes;

    public CursoDTO(Curso curso){
        this.idCurso = curso.getIdCurso();
        this.nombreCurso = curso.getNombreCurso();
        this.descripcion = curso.getDescripcion();
        this.categoria = curso.getCategoria();
        this.horasDuracion = curso.getHorasDuracion();
        this.precioCurso = curso.getPrecioCurso();
        this.fechaPublicacion = curso.getFechaPublicacion();
        this.profesor = curso.getProfesor();
        this.estudiantes = curso.getEstudiantes();
    }



}
