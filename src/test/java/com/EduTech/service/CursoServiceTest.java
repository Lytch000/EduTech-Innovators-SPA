package com.EduTech.service;

import com.EduTech.repository.*;
import com.EduTech.service.CursoService;
import com.EduTech.dto.cursoDTO.CursoDTO;
import com.EduTech.dto.cursoDTO.CursoPatchDTO;
import com.EduTech.model.Curso;
import com.EduTech.model.Roles;
import com.EduTech.model.Usuario;
import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CursoServiceTest{

    @Mock
    private CursoRepository cursoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private CursoService cursoService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

    }
    @Test
    void testListarCursos(){
        Curso curso = new Curso();
        curso.setIdCurso(1L);
        curso.setNombreCurso("Java Básico");
        curso.setCategoria("Programación");
        curso.setDescripcion("Curso de introducción a Java");
        curso.setHorasDuracion(10);
        curso.setPrecioCurso(100.0);
        curso.setFechaPublicacion(new Date());

        CursoDTO cursoDTO = new CursoDTO(curso);

        List<CursoDTO> cursoDTOList = List.of(cursoDTO);

        when(cursoRepository.buscarTodosLosCursos()).thenReturn(cursoDTOList);

        List<CursoDTO> resultado = cursoService.listar();

        assertEquals(1, resultado.size());
        assertEquals("Java Básico", resultado.get(0).getNombreCurso());
        verify(cursoRepository, times(1)).buscarTodosLosCursos();
    }
    
    @Test
    void testAddNewCurso() {
    
    Roles rolProfesor = new Roles();
    rolProfesor.setNombre("PROFESOR");

    
    Usuario profesor = new Usuario();
    profesor.setId(1L);
    profesor.setRoles(rolProfesor);

    
    Curso curso = new Curso();
    curso.setIdCurso(1L);
    curso.setNombreCurso("Spring Boot");
    curso.setProfesor(profesor);
        

    // Mockear la búsqueda del profesor
    when(usuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(profesor));
    // Mockear el guardado del curso
    when(cursoRepository.save(any(Curso.class))).thenReturn(curso);

    
    Curso resultado = cursoService.addNewCurso(curso);

    // Verificar resultados
    assertNotNull(resultado);
    assertEquals("Spring Boot", resultado.getNombreCurso());
    verify(usuarioRepository, times(1)).findById(1L);
    verify(cursoRepository, times(1)).save(any(Curso.class));
}
// ojo pendiente revision 
@Test
void testAddNewCurso_SinProfesor_LanzaExcepcion() {
    Curso curso = new Curso();
    // No asignas profesor
    Exception exception = assertThrows(RuntimeException.class, () -> cursoService.addNewCurso(curso));
    assertEquals("Debe asignar un profesor al curso.", exception.getMessage());
}

    @Test
    void testAddNewCurso_NoTieneRolProfesor() {
    Usuario profesor = new Usuario();
    profesor.setId(1L);
    Roles rol = new Roles();
    rol.setNombre("ESTUDIANTE"); // No es PROFESOR
    profesor.setRoles(rol);

    Curso curso = new Curso();
    curso.setProfesor(profesor);

    when(usuarioRepository.findById(1L)).thenReturn(Optional.of(profesor));

    Exception ex = assertThrows(RuntimeException.class, () -> cursoService.addNewCurso(curso));
    assertEquals("El usuario asignado no tiene el rol de PROFESOR.", ex.getMessage());
}



    @Test
    void testActualizarCurso(){
        CursoDTO cursoDTO = new CursoDTO();
        cursoDTO.setIdCurso(1L);
        cursoDTO.setNombreCurso("Curso de Python");
        cursoDTO.setCategoria("Programación");
        cursoDTO.setDescripcion("Curso avanzado de Python");
        cursoDTO.setHorasDuracion(20);
        cursoDTO.setPrecioCurso(200.0);
        cursoDTO.setFechaPublicacion(new Date());

        Curso curso = new Curso();
        curso.setIdCurso(1L);
        curso.setNombreCurso("Curso de Python");
        curso.setCategoria("Programación");
        curso.setDescripcion("Curso avanzado de Python");
        curso.setHorasDuracion(20);
        curso.setPrecioCurso(200.0);
        curso.setFechaPublicacion(new Date());

        when(cursoRepository.findById(1L)).thenReturn(java.util.Optional.of(curso));
        when(cursoRepository.save(any(Curso.class))).thenReturn(curso);

        String resultado = cursoService.actualizarCurso(cursoDTO);
        assertEquals("Curso actualizado correctamente.", resultado);

        verify(cursoRepository, times(1)).findById(1L);
        verify(cursoRepository, times(1)).save(any(Curso.class));
    }

    @Test
    void testActualizarCurso_IdCursoObligatorio() {
    CursoDTO cursoDTO = new CursoDTO(); // No se asigna ID
    String resultado = cursoService.actualizarCurso(cursoDTO);
    assertEquals("Error: El ID del curso es obligatorio.", resultado);
    }

    @Test
    void testActualizarCurso_CursoNoEncontrado() {
    CursoDTO cursoDTO = new CursoDTO();
    cursoDTO.setIdCurso(99L); // Un ID cualquiera

    // Simula que el curso no existe en el repositorio
    when(cursoRepository.findById(99L)).thenReturn(Optional.empty());

    String resultado = cursoService.actualizarCurso(cursoDTO);

    assertEquals("Error: Curso con ID 99 no encontrado.", resultado);
    }


    

    @Test
    void testdeleteCurso(){
        Long cursoId = 1L;

        when(cursoRepository.existsById(cursoId)).thenReturn(true);
        String resultado = cursoService.deleteCurso(cursoId);
        assertEquals("Curso eliminado exitosamente.", resultado);
        verify(cursoRepository, times(1)).deleteById(cursoId);
    }

    @Test
    void testDeleteCurso_CursoNoEncontrado() {
    Long idCurso = 123L;

    // Simula que el curso NO existe
    when(cursoRepository.existsById(idCurso)).thenReturn(false);

    String resultado = cursoService.deleteCurso(idCurso);

    assertEquals("No se encuentra curso especificado.", resultado);
    }




    @Test
    void testRemoverProfesorDeCurso(){
        Long cursoId = 1L;

        Curso curso = new Curso();

        curso.setIdCurso(cursoId);
        curso.setProfesor(new Usuario ());
        when(cursoRepository.findById(cursoId)).thenReturn(java.util.Optional.of(curso));
        when(cursoRepository.save(any(Curso.class))).thenReturn(curso);

        String resultado = cursoService.removerProfesorDeCurso(cursoId);
        assertEquals("Profesor removido del curso.", resultado);
        verify(cursoRepository, times(1)).findById(cursoId);
        verify(cursoRepository, times(1)).save(any(Curso.class));
    }

    @Test
    void testRemoverProfesorDeCurso_CursoNoEncontrado() {
    Long idCurso = 123L;

    // Simula que el curso NO existe
    when(cursoRepository.findById(idCurso)).thenReturn(Optional.empty());

    String resultado = cursoService.removerProfesorDeCurso(idCurso);

    assertEquals("Curso no encontrado.", resultado);
    }


    @Test
    void testasignarProfesorACurso(){
        Long cursoId = 1L;
        Long profesorId = 2L;
        
        Curso curso = new Curso();

        curso.setIdCurso(cursoId);
        Usuario profesor = new Usuario();
        profesor.setId(profesorId);
        Roles rolProfesor = new Roles();
        rolProfesor.setNombre("PROFESOR");
        profesor.setRoles(rolProfesor);
        when(cursoRepository.findById(cursoId)).thenReturn(java.util.Optional.of(curso));
        when(usuarioRepository.findById(profesorId)).thenReturn(java.util.Optional.of(profesor));
        when(cursoRepository.save(any(Curso.class))).thenReturn(curso);


        // Asignar el profesor al curso
        curso.setProfesor(profesor);
        when(cursoRepository.save(curso)).thenReturn(curso);
        // Llamar al método de servicio
        
        
        String resultado = cursoService.asignarProfesorACurso(cursoId, profesorId);
        assertEquals("Profesor asignado al curso correctamente.", resultado);
        verify(cursoRepository, times(1)).findById(cursoId);
        verify(usuarioRepository, times(1)).findById(profesorId);
        verify(cursoRepository, times(1)).save(any(Curso.class));
    }

    @Test
    void testAsignarProfesorACurso_CursoNoEncontrado() {
    Long cursoId = 1L;
    Long profesorId = 2L;

    // Simula que el curso NO existe
    when(cursoRepository.findById(cursoId)).thenReturn(Optional.empty());

    String resultado = cursoService.asignarProfesorACurso(cursoId, profesorId);

    assertEquals("Curso no encontrado.", resultado);
    }

    @Test
    void testAsignarProfesorACurso_ProfesorNoEncontrado() {
    Long cursoId = 1L;
    Long profesorId = 2L;

    // Simula que el curso SÍ existe
    Curso curso = new Curso();
    curso.setIdCurso(cursoId);
    when(cursoRepository.findById(cursoId)).thenReturn(Optional.of(curso));

    // Simula que el profesor NO existe
    when(usuarioRepository.findById(profesorId)).thenReturn(Optional.empty());

    String resultado = cursoService.asignarProfesorACurso(cursoId, profesorId);

    assertEquals("Profesor no encontrado.", resultado);
    }

    @Test
    void testAsignarProfesorACurso_UsuarioSinRolProfesor() {
    Long cursoId = 1L;
    Long profesorId = 2L;

    // Simula que el curso SÍ existe
    Curso curso = new Curso();
    curso.setIdCurso(cursoId);
    when(cursoRepository.findById(cursoId)).thenReturn(Optional.of(curso));

    // Simula que el profesor SÍ existe pero NO tiene rol de PROFESOR
    Usuario profesor = new Usuario();
    profesor.setId(profesorId);
    Roles rol = new Roles();
    rol.setNombre("ESTUDIANTE"); // No es PROFESOR
    profesor.setRoles(rol);
    when(usuarioRepository.findById(profesorId)).thenReturn(Optional.of(profesor));

    String resultado = cursoService.asignarProfesorACurso(cursoId, profesorId);

    assertEquals("El usuario no tiene el rol de Profesor.", resultado);
    }



    @Test
    void testReemplazarCursosDeProfesor_CasoExitoso() {
        Curso curso1 = new Curso();
    curso1.setIdCurso(10L);
    Curso curso2 = new Curso();
    curso2.setIdCurso(20L);

    when(cursoRepository.findById(10L)).thenReturn(Optional.of(curso1));
    when(cursoRepository.findById(20L)).thenReturn(Optional.of(curso2));
    Long profesorId = 1L;
    Roles rolProfesor = new Roles();
    rolProfesor.setNombre("PROFESOR");
    Usuario profesor = new Usuario();
    profesor.setId(profesorId);
    profesor.setRoles(rolProfesor);

    List<Long> idsCursos = Arrays.asList(10L, 20L);

    when(usuarioRepository.findById(profesorId)).thenReturn(java.util.Optional.of(profesor));

    // Llama al método (no debe lanzar excepción)
    assertDoesNotThrow(() -> cursoService.reemplazarCursosDeProfesor(profesorId, idsCursos));

    verify(usuarioRepository, times(1)).findById(profesorId);
    }

    @Test
    void testReemplazarCursosDeProfesor_UsuarioSinRolProfesor() {
    Long profesorId = 1L;
    List<Long> idsCursos = Arrays.asList(10L, 20L);

    // Simula que el profesor SÍ existe pero NO tiene rol de PROFESOR
    Usuario profesor = new Usuario();
    profesor.setId(profesorId);
    Roles rol = new Roles();
    rol.setNombre("ESTUDIANTE"); 
    profesor.setRoles(rol);

    when(usuarioRepository.findById(profesorId)).thenReturn(Optional.of(profesor));

    RuntimeException exception = assertThrows(RuntimeException.class, () -> 
        cursoService.reemplazarCursosDeProfesor(profesorId, idsCursos)
    );
    assertEquals("El usuario no tiene rol de Profesor", exception.getMessage());
    }

    @Test
    void testmodificarCursosDeProfesor(){
        
    }


    @Test
    void testModificarCursosDeProfesor_ProfesorNoEncontrado() {
    Long profesorId = 1L;
    CursoPatchDTO dto = new CursoPatchDTO();

    when(usuarioRepository.findById(profesorId)).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class, () ->
        cursoService.modificarCursosDeProfesor(profesorId, dto)
    );
    assertEquals("Profesor no encontrado", exception.getMessage());
    }

    @Test
    void testModificarCursosDeProfesor_UsuarioNoEsProfesor() {
    Long profesorId = 1L;
    CursoPatchDTO dto = new CursoPatchDTO();

    Usuario usuario = new Usuario();
    usuario.setId(profesorId);
    Roles rol = new Roles();
    rol.setNombre("ESTUDIANTE"); // No es PROFESOR
    usuario.setRoles(rol);

    when(usuarioRepository.findById(profesorId)).thenReturn(Optional.of(usuario));

    RuntimeException exception = assertThrows(RuntimeException.class, () ->
        cursoService.modificarCursosDeProfesor(profesorId, dto)
    );
    assertEquals("El usuario no es un profesor", exception.getMessage());
    }

    @Test
    void testModificarCursosDeProfesor_AgregarCursos() {
    Long profesorId = 1L;
    Usuario profesor = new Usuario();
    profesor.setId(profesorId);
    Roles rol = new Roles();
    rol.setNombre("PROFESOR");
    profesor.setRoles(rol);

    CursoPatchDTO dto = new CursoPatchDTO();
    List<Long> idsAgregar = Arrays.asList(10L, 20L);
    dto.setIdsAgregar(idsAgregar);

    Curso curso1 = new Curso();
    curso1.setIdCurso(10L);
    Curso curso2 = new Curso();
    curso2.setIdCurso(20L);

    when(usuarioRepository.findById(profesorId)).thenReturn(Optional.of(profesor));
    when(cursoRepository.findAllById(idsAgregar)).thenReturn(Arrays.asList(curso1, curso2));
    when(cursoRepository.save(any(Curso.class))).thenAnswer(invocation -> invocation.getArgument(0));

    cursoService.modificarCursosDeProfesor(profesorId, dto);

    // Verifica que se asignó el profesor y se guardó cada curso
    assertEquals(profesor, curso1.getProfesor());
    assertEquals(profesor, curso2.getProfesor());
    verify(cursoRepository, times(2)).save(any(Curso.class));
    }

    @Test
    void testModificarCursosDeProfesor_EliminarCursos() {
    Long profesorId = 1L;
    Usuario profesor = new Usuario();
    profesor.setId(profesorId);
    Roles rol = new Roles();
    rol.setNombre("PROFESOR");
    profesor.setRoles(rol);

    CursoPatchDTO dto = new CursoPatchDTO();
    List<Long> idsEliminar = Arrays.asList(30L, 40L);
    dto.setIdsEliminar(idsEliminar);

    Curso curso1 = new Curso();
    curso1.setIdCurso(30L);
    curso1.setProfesor(profesor); // El curso tiene asignado al profesor
    Curso curso2 = new Curso();
    curso2.setIdCurso(40L);
    curso2.setProfesor(profesor); // El curso tiene asignado al profesor

    when(usuarioRepository.findById(profesorId)).thenReturn(Optional.of(profesor));
    when(cursoRepository.findAllById(idsEliminar)).thenReturn(Arrays.asList(curso1, curso2));
    when(cursoRepository.save(any(Curso.class))).thenAnswer(invocation -> invocation.getArgument(0));

    cursoService.modificarCursosDeProfesor(profesorId, dto);

    // Verifica que se quitó el profesor y se guardó cada curso
    assertNull(curso1.getProfesor());
    assertNull(curso2.getProfesor());
    verify(cursoRepository, times(2)).save(any(Curso.class));
    }

    

}