// Victor garces
package com.EduTech.service;


import com.EduTech.dto.cursoDTO.CursoDTO;
import com.EduTech.model.Curso;
import com.EduTech.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;


    public List<CursoDTO> listar(){
        List<CursoDTO> cursoDto = cursoRepository.buscarTodosLosCursos();
        return cursoDto;
    }


    // Añade nuevo curso
    public Curso addNewCurso(Curso curso){
        return cursoRepository.save(curso);
    }

    //Actualizamos curso
    public String actualizarCurso(CursoDTO cursoDTO) {
        if (cursoDTO.getIdCurso() == null) {
            return "Error: El ID del curso es obligatorio.";
        }

        Optional<Curso> cursoOptional = cursoRepository.findById(cursoDTO.getIdCurso());

        if (!cursoOptional.isPresent()) {
            return "Error: Curso con ID " + cursoDTO.getIdCurso() + " no encontrado.";
        }

        Curso cursoActualizado = cursoOptional.get();

        cursoActualizado.setNombreCurso(cursoDTO.getNombreCurso());
        cursoActualizado.setDescripcion(cursoDTO.getDescripcion());
        cursoActualizado.setCategoria(cursoDTO.getCategoria());
        cursoActualizado.setHorasDuracion(cursoDTO.getHorasDuracion());
        cursoActualizado.setPrecioCurso(cursoDTO.getPrecioCurso());
        cursoActualizado.setFechaPublicacion(cursoDTO.getFechaPublicacion());

        cursoRepository.save(cursoActualizado);

        return "Curso actualizado correctamente.";
    }

    // Eliminación de curso
        public String deleteCurso(Long idCurso){
            if (!cursoRepository.existsById(idCurso)){
                return  "No se encuentra curso especificado.";
            } else {
                cursoRepository.deleteById(idCurso);
                return "Curso eliminado exitosamente.";
            }
        }
    // Retornara los cursos del instructor
    public List<CursoDTO> listarCursosPorInstructor(Long idInstructor) {
        List<Curso> cursos = cursoRepository.findByInstructor_IdInstructor(idInstructor);
        return cursos.stream().map(CursoDTO::new).toList(); // Convierte a DTO
    }



}





