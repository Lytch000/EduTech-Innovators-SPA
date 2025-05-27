package com.EduTech.dto.instructorDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class InstructorAsignacionDTO {
    private Long idInstructor;
    private List<Long> cursoIds;

    public InstructorAsignacionDTO(Long idInstructor, List<Long> cursoIds){
        this.idInstructor = idInstructor;
        this.cursoIds = cursoIds;
    }
}
