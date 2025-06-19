package com.EduTech.services;

import com.EduTech.dto.cursoDTO.ProfesorDetalleCursoDTO;
import com.EduTech.dto.user.UsuarioDTO;
import com.EduTech.model.Curso;
import com.EduTech.model.Roles;
import com.EduTech.model.Usuario;
import com.EduTech.repository.CursoRepository;
import com.EduTech.repository.UsuarioRepository;
import com.EduTech.service.UsuarioService;

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

    //Juan Olguin
    @Test
    void login(){
        String email = "prueba@gmail.com";
        String password = "134567";

        Usuario usuario = new Usuario();

        usuario.setEmail(email);
        usuario.setPassword(password);

        when(usuarioRepository.findByEmailAndPassword(email, password)).thenReturn(Optional.of(usuario));

        UsuarioDTO usuarioDTO = usuarioService.login(email, password);
        assertNotNull(usuarioDTO);
        assertEquals(email, usuarioDTO.getEmail());
        assertEquals(password, usuarioDTO.getPassword());
        verify(usuarioRepository, times(1)).findByEmailAndPassword(email, password);
    }
    
    @Test
    void loginEmailVacio(){
        String email = "";
        String password = "134567";

        Usuario usuario = new Usuario();

        usuario.setEmail(email);
        usuario.setPassword(password);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.login(email, password);
        });

        assertEquals("El email no puede estar vacio", ex.getMessage());
        
    }

    @Test
    void loginEmailNulo(){
        Usuario usuario = new Usuario();
        usuario.setEmail(null);
        usuario.setPassword("134567");
        
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.login(usuario.getEmail(), usuario.getPassword());
        });

        assertEquals("El email no puede estar vacio", ex.getMessage());

    }
}