//Victor garces
package com.EduTech.dto.cursoDTO;

import com.EduTech.dto.UsuarioDTO;
import com.EduTech.model.Curso;
import com.EduTech.model.Usuario;
import lombok.Data;

import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

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
    private Usuario profesor;
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
