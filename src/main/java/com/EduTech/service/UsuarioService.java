/** Autor Juan Olguin
 *
 */

package com.EduTech.service;

import com.EduTech.dto.UsuarioDTO;
import com.EduTech.dto.cursoDTO.CursoDTO;
import com.EduTech.dto.cursoDTO.CursoPatchDTO;
import com.EduTech.dto.cursoDTO.ProfesorDetalleCursoDTO;
import com.EduTech.model.Curso;
import com.EduTech.model.Roles;
import com.EduTech.model.Usuario;
import com.EduTech.repository.CursoRepository;
import com.EduTech.repository.RolesRepository;
import com.EduTech.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private CursoRepository cursoRepository;

    public List<UsuarioDTO> listar(){

        List<UsuarioDTO> usuarioDTO = usuarioRepository.buscarTodos();
        return usuarioDTO;
    }

    public String addNewUsuario(UsuarioDTO dto){

        Roles rol = rolesRepository.findById(dto.getId_rol_fk())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        Usuario usuario = new Usuario();
        usuario.setRut(dto.getRut());
        usuario.setNombre(dto.getNombre());
        usuario.setEdad(dto.getEdad());
        usuario.setEmail(dto.getEmail());
        usuario.setCelular(dto.getCelular());
        usuario.setRoles(rol);

        usuarioRepository.save(usuario);

        return "Usuario registrado correctamente";

    }

    // Obtendre el profesor con los detalles del curso que tenga asignado
    public ProfesorDetalleCursoDTO obtenerDetalleProfesorConCursos(Long idProfesor) {
        Usuario profesor = usuarioRepository.findById(idProfesor)
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));

        if (!profesor.getRoles().getNombre().equalsIgnoreCase("Profesor")) {
            throw new RuntimeException("El usuario no es un profesor");
        }

        List<Curso> cursosAsignados = cursoRepository.findByProfesorId(idProfesor);

        List<CursoDTO> nombresCursos = cursosAsignados.stream()
                .map(CursoDTO::new)
                .toList();

        return new ProfesorDetalleCursoDTO(
                profesor.getId(),
                profesor.getNombre(),
                profesor.getEmail(),
                profesor.getRut(),
                nombresCursos
        );
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
