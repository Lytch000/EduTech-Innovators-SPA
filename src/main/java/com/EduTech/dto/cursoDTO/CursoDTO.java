//Victor garces
package com.EduTech.dto.cursoDTO;

import com.EduTech.model.Curso;
import lombok.Data;

import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class CursoDTO {

    private Long idCurso;
    private String nombreCurso;
    private String descripcion;
    private String categoria;
    private int horasDuracion;
    private Double precioCurso;
    private Date fechaPublicacion;

    public CursoDTO(Curso curso){
        this.idCurso = curso.getIdCurso();
        this.nombreCurso = curso.getNombreCurso();
        this.descripcion = curso.getDescripcion();
        this.categoria = curso.getCategoria();
        this.horasDuracion = curso.getHorasDuracion();
        this.precioCurso = curso.getPrecioCurso();
        this.fechaPublicacion = curso.getFechaPublicacion();
    }



}
