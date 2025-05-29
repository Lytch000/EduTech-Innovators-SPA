// Victor garces
package com.EduTech.service;


import com.EduTech.dto.cursoDTO.CursoDTO;
import com.EduTech.model.Curso;
import com.EduTech.model.Usuario;
import com.EduTech.repository.CursoRepository;
import com.EduTech.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    public List<CursoDTO> listar(){
        List<CursoDTO> cursoDto = cursoRepository.buscarTodosLosCursos();
        return cursoDto;
    }


    // Añade nuevo curso
    public Curso addNewCurso(Curso curso){
        if (curso.getProfesor() == null || curso.getProfesor().getId() == null) {
            throw new RuntimeException("Debe asignar un profesor al curso.");
        }

        Usuario profesor = usuarioRepository.findById(curso.getProfesor().getId())
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado."));

        if (!profesor.getRoles().getNombre().equalsIgnoreCase("PROFESOR")) {
            throw new RuntimeException("El usuario asignado no tiene el rol de PROFESOR.");
        }

        curso.setProfesor(profesor);
        return cursoRepository.save(curso);
    }

    //Actualizamos curso --
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

    //Eliminamos profesor del curso ----
    public String removerProfesorDeCurso(Long idCurso) {
        Optional<Curso> cursoOpt = cursoRepository.findById(idCurso);
        if (!cursoOpt.isPresent()) {
            return "Curso no encontrado.";
        }

        Curso curso = cursoOpt.get();
        curso.setProfesor(null);
        cursoRepository.save(curso);

        return "Profesor removido del curso.";
    }

    // Asignamos profesor ---
    public String asignarProfesorDeCurso(Long idCurso) {
        Optional<Curso> cursoOpt = cursoRepository.findById(idCurso);
        if (!cursoOpt.isPresent()) {
            return "Curso no encontrado.";
        }

        Curso curso = cursoOpt.get();
        curso.setProfesor(null);
        cursoRepository.save(curso);

        return "Profesor asignado al curso.";
    }





}





