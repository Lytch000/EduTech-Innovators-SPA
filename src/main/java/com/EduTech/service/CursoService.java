// Victor garces
package com.EduTech.service;


import com.EduTech.dto.cursoDTO.CursoDTO;
import com.EduTech.dto.cursoDTO.CursoPatchDTO;
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
    public String asignarProfesorACurso(Long idCurso, Long idProfesor) {
        Optional<Curso> cursoOpt = cursoRepository.findById(idCurso);
        if (!cursoOpt.isPresent()) {
            return "Curso no encontrado.";
        }

        Optional<Usuario> profesorOpt = usuarioRepository.findById(idProfesor);
        if (!profesorOpt.isPresent()) {
            return "Profesor no encontrado.";
        }

        Usuario profesor = profesorOpt.get();

        // Validar que tenga el rol de "Profesor"
        if (!profesor.getRoles().getNombre().equalsIgnoreCase("Profesor")) {
            return "El usuario no tiene el rol de Profesor.";
        }

        Curso curso = cursoOpt.get();
        curso.setProfesor(profesor);
        cursoRepository.save(curso);

        return "Profesor asignado al curso correctamente.";
    }



    //Autor Juan Olguin
    public String inscribirEstudianteACurso(Long idCurso, Long idUsuario) {
        Curso curso = cursoRepository.findById(idCurso)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        curso.getEstudiantes().add(usuario);
        cursoRepository.save(curso);

        return "Estudiante inscrito correctamente al curso.";
    }
//Autor Juan Olguin
    public String removerEstudiantedeCurso(Long idCurso, Long idUsuario){
        Curso curso = cursoRepository.findById(idCurso)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        curso.getEstudiantes().remove(usuario);
        cursoRepository.save(curso);

        return "Estudiante removido correctamente";
    }


    // Esto me permitira actualizar
    public void reemplazarCursosDeProfesor(Long idProfesor, List<Long> idsCursos) {
        Usuario profesor = usuarioRepository.findById(idProfesor)
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));

        if (!"Profesor".equalsIgnoreCase(profesor.getRoles().getNombre())) {
            throw new RuntimeException("El usuario no tiene rol de Profesor");
        }

        // Eliminar asignaciones actuales
        List<Curso> actuales = cursoRepository.findByProfesorId(idProfesor);
        actuales.forEach(curso -> curso.setProfesor(null));
        cursoRepository.saveAll(actuales);

        // Asignar los nuevos cursos
        for (Long idCurso : idsCursos) {
            Curso curso = cursoRepository.findById(idCurso)
                    .orElseThrow(() -> new RuntimeException("Curso no encontrado: " + idCurso));
            curso.setProfesor(profesor);
            cursoRepository.save(curso);
        }
    }

    public void modificarCursosDeProfesor(Long idProfesor, CursoPatchDTO dto) {
        Usuario profesor = usuarioRepository.findById(idProfesor)
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));

        if (!"Profesor".equalsIgnoreCase(profesor.getRoles().getNombre())) {
            throw new RuntimeException("El usuario no es un profesor");
        }

        // Agregar cursos
        if (dto.getIdsAgregar() != null && !dto.getIdsAgregar().isEmpty()) {
            cursoRepository.findAllById(dto.getIdsAgregar()).forEach(curso -> {
                curso.setProfesor(profesor);
                cursoRepository.save(curso);
            });
        }

        // Eliminar cursos
        if (dto.getIdsEliminar() != null && !dto.getIdsEliminar().isEmpty()) {
            cursoRepository.findAllById(dto.getIdsEliminar()).forEach(curso -> {
                if (curso.getProfesor() != null && curso.getProfesor().getId().equals(idProfesor)) {
                    curso.setProfesor(null);
                    cursoRepository.save(curso);
                }
            });
        }
    }


}





