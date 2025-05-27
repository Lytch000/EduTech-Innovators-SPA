//Victor garces
package com.EduTech.service;

import com.EduTech.dto.instructorDTO.InstructorAsignacionDTO;
import com.EduTech.dto.instructorDTO.InstructorDTO;
import com.EduTech.model.Curso;
import com.EduTech.model.Instructor;
import com.EduTech.repository.CursoRepository;
import com.EduTech.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorService {

    private final InstructorRepository instructorRepository;
    private final CursoRepository cursoRepository;

    @Autowired
    public InstructorService(InstructorRepository instructorRepository, CursoRepository cursoRepository) {
        this.instructorRepository = instructorRepository;
        this.cursoRepository = cursoRepository;
    }


    public List<InstructorDTO> listar(){
        List<InstructorDTO> instructorDto = instructorRepository.buscarTodosLosInstructores();
        return instructorDto;
    }

    // Se crea nuevo instuctor
    public Instructor addNewInstructor(Instructor instructor){
        return instructorRepository.save(instructor);
    }

    // Eliminamos instructor de manera individual
    public String deleteCurso(Long idInstructor){
        if (!instructorRepository.existsById(idInstructor)){
            return "No se encuentra instructor especificado.";
        } else {
            instructorRepository.deleteById(idInstructor);
            return "Instructor eliminado exitosamente.";
        }
    }

    //Asignaremos instructor a un curso o varios cursos
    public String asignarInstructor(InstructorAsignacionDTO dto){
        Instructor instructor = instructorRepository.findById(dto.getIdInstructor()).orElse(null);
        if (instructor == null) {
            return "Instructor no encontrado.";
        }

        List<Curso> cursos = cursoRepository.findAllById(dto.getCursoIds());
        if (cursos.isEmpty()) {
            return "Cursos no encontrados.";
        }

        // Asignar el instructor a cada curso
        for (Curso curso : cursos) {
            curso.setInstructor(instructor);
        }

        // Guardar los cursos con el instructor asignado
        cursoRepository.saveAll(cursos);



//        instructor.setCursos(cursos);  // Asigna los cursos al instructor
//        instructorRepository.save(instructor); // Guarda la relación actualizada
//
//        for (Curso curso : cursos) {
//            curso.setInstructor(instructor);
//            cursoRepository.save(curso); // Guardar cada curso con el instructor asignado
//        }

        return "Instructor asignado correctamente.";
    }

}
