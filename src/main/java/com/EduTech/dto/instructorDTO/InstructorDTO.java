// Victor garces
package com.EduTech.dto.instructorDTO;

import com.EduTech.model.Instructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class InstructorDTO {

    private Long idInstructor;
    private String especialidad;
    private String plataformaEnseñanza;
    private int cursosAsignados;
    private Date fechaIncorporacion;

    public  InstructorDTO(Instructor instructor){
        this.idInstructor = instructor.getIdInstructor();
        this.especialidad = instructor.getEspecialidad();
        this.plataformaEnseñanza = instructor.getPlataformaEnseñanza();
        this.cursosAsignados = instructor.getCursosAsignados();
        this.fechaIncorporacion = instructor.getFechaIncorporacion();
    }
}
