package com.EduTech.dto.cursoDTO;

import com.EduTech.model.Usuario;
import lombok.Data;

import java.util.List;

@Data
public class ProfesorDetalleCursoDTO {
    private Long id;
    private String nombre;
    private String email;
    private String rut;
    private List<CursoDTO> cursos;  // ----->> me permitira traerme una lista con los cursos previamente asignados

    public ProfesorDetalleCursoDTO(Long id, String nombre, String email, String rut, List<CursoDTO> cursos){
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.rut = rut;
        this.cursos = cursos;

    }
}
