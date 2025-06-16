package com.EduTech.service;

import com.EduTech.dto.cursoDTO.ProfesorDetalleCursoDTO;
import com.EduTech.model.Curso;
import com.EduTech.model.Roles;
import com.EduTech.model.Usuario;
import com.EduTech.repository.CursoRepository;
import com.EduTech.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private CursoRepository cursoRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerDetalleProfesorConCursos() {
        // Prepara el profesor con rol PROFESOR
        Long profesorId = 1L;
        Usuario profesor = new Usuario();
        profesor.setId(profesorId);
        profesor.setFirstName("Juan");
        profesor.setEmail("juan@correo.com");
        profesor.setRut("12345678-9");
        Roles rol = new Roles();
        rol.setNombre("PROFESOR");
        profesor.setRoles(rol);

        // Prepara los cursos asignados
        Curso curso1 = new Curso();
        curso1.setIdCurso(10L);
        curso1.setNombreCurso("Java Básico");
        Curso curso2 = new Curso();
        curso2.setIdCurso(20L);
        curso2.setNombreCurso("Spring Boot");
        List<Curso> cursos = Arrays.asList(curso1, curso2);

        // Mockea los repositorios
        when(usuarioRepository.findById(profesorId)).thenReturn(Optional.of(profesor));
        when(cursoRepository.findByProfesorId(profesorId)).thenReturn(cursos);

        // Llama al método y verifica
        ProfesorDetalleCursoDTO resultado = usuarioService.obtenerDetalleProfesorConCursos(profesorId);

        assertEquals(profesorId, resultado.getId());
        assertEquals("Juan", resultado.getNombre());
        assertEquals("juan@correo.com", resultado.getEmail());
        assertEquals("12345678-9", resultado.getRut());
        assertEquals(2, resultado.getCursos().size());
        assertEquals("Java Básico", resultado.getCursos().get(0).getNombreCurso());
        assertEquals("Spring Boot", resultado.getCursos().get(1).getNombreCurso());
    }

    @Test
    void testObtenerDetalleProfesorConCursos_UsuarioNoEsProfesor() {
    Long profesorId = 1L;
    Usuario usuario = new Usuario();
    usuario.setId(profesorId);
    usuario.setFirstName("Juan");
    usuario.setEmail("juan@correo.com");
    usuario.setRut("12345678-9");
    Roles rol = new Roles();
    rol.setNombre("ESTUDIANTE"); // No es PROFESOR
    usuario.setRoles(rol);

    when(usuarioRepository.findById(profesorId)).thenReturn(Optional.of(usuario));

    RuntimeException exception = assertThrows(RuntimeException.class, () ->
        usuarioService.obtenerDetalleProfesorConCursos(profesorId)
    );
    assertEquals("El usuario no es un profesor", exception.getMessage());
    }

    
}