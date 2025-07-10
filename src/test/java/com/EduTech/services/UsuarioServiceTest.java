package com.EduTech.services;

import com.EduTech.dto.cursoDTO.ProfesorDetalleCursoDTO;
import com.EduTech.dto.user.*;
import com.EduTech.model.Curso;
import com.EduTech.model.Roles;
import com.EduTech.model.Usuario;
import com.EduTech.repository.CursoRepository;
import com.EduTech.repository.RolesRepository;
import com.EduTech.repository.UsuarioRepository;
import com.EduTech.service.UsuarioService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private CursoRepository cursoRepository;

    @Mock
    private RolesRepository rolesRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Tests para getUsers()
    @Test
    void testGetUsers_ConUsuarios() {
        // Preparar datos de prueba
        Usuario usuario1 = new Usuario();
        usuario1.setId(1L);
        usuario1.setFirstName("Juan");
        usuario1.setLastName("Pérez");
        usuario1.setEmail("juan@test.com");
        usuario1.setRut("12345678-9");
        usuario1.setPassword("password123");
        usuario1.setBirthDate(new Date());
        usuario1.setPhoneNumber(987654321L);
        
        Roles rol = new Roles();
        rol.setId(1L);
        rol.setNombre("ESTUDIANTE");
        usuario1.setRoles(rol);

        Usuario usuario2 = new Usuario();
        usuario2.setId(2L);
        usuario2.setFirstName("María");
        usuario2.setLastName("García");
        usuario2.setEmail("maria@test.com");
        usuario2.setRut("87654321-0");
        usuario2.setPassword("password456");
        usuario2.setBirthDate(new Date());
        usuario2.setPhoneNumber(123456789L);
        usuario2.setRoles(rol);

        List<Usuario> usuarios = Arrays.asList(usuario1, usuario2);

        // Mockear el repositorio
        when(usuarioRepository.findAll()).thenReturn(usuarios);

        // Ejecutar el método
        List<RespuestaUsuarioDto> resultado = usuarioService.getUsers();

        // Verificar el resultado
        assertEquals(2, resultado.size());
        assertEquals("Juan", resultado.get(0).getFirstName());
        assertEquals("María", resultado.get(1).getFirstName());
        assertEquals("ESTUDIANTE", resultado.get(0).getRol());
        assertEquals("ESTUDIANTE", resultado.get(1).getRol());
        
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void testGetUsers_SinUsuarios() {
        // Mockear el repositorio para devolver lista vacía
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList());

        // Ejecutar el método
        List<RespuestaUsuarioDto> resultado = usuarioService.getUsers();

        // Verificar el resultado
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        
        verify(usuarioRepository, times(1)).findAll();
    }

    // Tests para createUser()
    @Test
    void testCreateUser_Exitoso() {
        // Preparar datos de prueba
        CrearUsuarioDto crearUsuarioDto = new CrearUsuarioDto();
        crearUsuarioDto.setFirstName("Nuevo");
        crearUsuarioDto.setLastName("Usuario");
        crearUsuarioDto.setEmail("nuevo@test.com");
        crearUsuarioDto.setRut("11111111-1");
        crearUsuarioDto.setPassword("password123");
        crearUsuarioDto.setBirthDate(new Date());
        crearUsuarioDto.setPhoneNumber(555555555L);
        crearUsuarioDto.setRolId(1L);

        Roles rol = new Roles();
        rol.setId(1L);
        rol.setNombre("ESTUDIANTE");

        Usuario usuarioGuardado = new Usuario();
        usuarioGuardado.setId(1L);
        usuarioGuardado.setFirstName("Nuevo");
        usuarioGuardado.setLastName("Usuario");
        usuarioGuardado.setEmail("nuevo@test.com");
        usuarioGuardado.setRut("11111111-1");
        usuarioGuardado.setPassword("password123");
        usuarioGuardado.setBirthDate(crearUsuarioDto.getBirthDate());
        usuarioGuardado.setPhoneNumber(555555555L);
        usuarioGuardado.setRoles(rol);

        // Mockear los repositorios
        when(rolesRepository.findById(1L)).thenReturn(Optional.of(rol));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioGuardado);

        // Ejecutar el método
        RespuestaUsuarioDto resultado = usuarioService.createUser(crearUsuarioDto);

        // Verificar el resultado
        assertNotNull(resultado);
        assertEquals("Nuevo", resultado.getFirstName());
        assertEquals("Usuario", resultado.getLastName());
        assertEquals("nuevo@test.com", resultado.getEmail());
        assertEquals("ESTUDIANTE", resultado.getRol());
        
        verify(rolesRepository, times(1)).findById(1L);
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void testCreateUser_RolNoEncontrado() {
        // Preparar datos de prueba
        CrearUsuarioDto crearUsuarioDto = new CrearUsuarioDto();
        crearUsuarioDto.setRolId(999L);

        // Mockear el repositorio para que no encuentre el rol
        when(rolesRepository.findById(999L)).thenReturn(Optional.empty());

        // Ejecutar el método y verificar que lance excepción
        assertThrows(RuntimeException.class, () -> {
            usuarioService.createUser(crearUsuarioDto);
        });
        
        verify(rolesRepository, times(1)).findById(999L);
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    // Tests para getOneUser()
    @Test
    void testGetOneUser_Exitoso() {
        // Preparar datos de prueba
        Long userId = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(userId);
        usuario.setFirstName("Juan");
        usuario.setLastName("Pérez");
        usuario.setEmail("juan@test.com");
        usuario.setRut("12345678-9");
        usuario.setPassword("password123");
        usuario.setBirthDate(new Date());
        usuario.setPhoneNumber(987654321L);
        
        Roles rol = new Roles();
        rol.setId(1L);
        rol.setNombre("ESTUDIANTE");
        usuario.setRoles(rol);

        // Mockear el repositorio
        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));

        // Ejecutar el método
        RespuestaUsuarioDto resultado = usuarioService.getOneUser(userId);

        // Verificar el resultado
        assertNotNull(resultado);
        assertEquals(userId, resultado.getId());
        assertEquals("Juan", resultado.getFirstName());
        assertEquals("Pérez", resultado.getLastName());
        assertEquals("ESTUDIANTE", resultado.getRol());
        
        verify(usuarioRepository, times(1)).findById(userId);
    }

    @Test
    void testGetOneUser_UsuarioNoEncontrado() {
        // Preparar datos de prueba
        Long userId = 999L;

        // Mockear el repositorio para que no encuentre el usuario
        when(usuarioRepository.findById(userId)).thenReturn(Optional.empty());

        // Ejecutar el método y verificar que lance excepción
        assertThrows(RuntimeException.class, () -> {
            usuarioService.getOneUser(userId);
        });
        
        verify(usuarioRepository, times(1)).findById(userId);
    }

    // Tests para updateUser()
    @Test
    void testUpdateUser_Exitoso() {
        // Preparar datos de prueba
        Long userId = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(userId);
        usuario.setFirstName("Juan");
        usuario.setLastName("Pérez");
        usuario.setEmail("juan@test.com");
        usuario.setPhoneNumber(987654321L);
        
        Roles rolActual = new Roles();
        rolActual.setId(1L);
        rolActual.setNombre("ESTUDIANTE");
        usuario.setRoles(rolActual);

        ActualizarUsuarioDto actualizarDto = new ActualizarUsuarioDto();
        actualizarDto.setEmail("nuevo@test.com");
        actualizarDto.setPhoneNumber(123456789L);
        actualizarDto.setRoleId(2L);

        Roles nuevoRol = new Roles();
        nuevoRol.setId(2L);
        nuevoRol.setNombre("PROFESOR");

        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setId(userId);
        usuarioActualizado.setFirstName("Juan");
        usuarioActualizado.setLastName("Pérez");
        usuarioActualizado.setEmail("nuevo@test.com");
        usuarioActualizado.setPhoneNumber(123456789L);
        usuarioActualizado.setRoles(nuevoRol);

        // Mockear los repositorios
        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(rolesRepository.findById(2L)).thenReturn(Optional.of(nuevoRol));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioActualizado);

        // Ejecutar el método
        RespuestaUsuarioDto resultado = usuarioService.updateUser(userId, actualizarDto);

        // Verificar el resultado
        assertNotNull(resultado);
        assertEquals("nuevo@test.com", resultado.getEmail());
        assertEquals(123456789L, resultado.getPhoneNumber());
        assertEquals("PROFESOR", resultado.getRol());
        
        verify(usuarioRepository, times(1)).findById(userId);
        verify(rolesRepository, times(1)).findById(2L);
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void testUpdateUser_EmailVacio() {
        // Preparar datos de prueba
        Long userId = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(userId);
        usuario.setEmail("juan@test.com");

        ActualizarUsuarioDto actualizarDto = new ActualizarUsuarioDto();
        actualizarDto.setEmail("");

        // Mockear el repositorio
        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));

        // Ejecutar el método y verificar que lance excepción
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.updateUser(userId, actualizarDto);
        });
        
        assertEquals("Email cannot be empty or blank", exception.getMessage());
        verify(usuarioRepository, times(1)).findById(userId);
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void testUpdateUser_EmailIgual() {
        // Preparar datos de prueba
        Long userId = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(userId);
        usuario.setEmail("juan@test.com");

        ActualizarUsuarioDto actualizarDto = new ActualizarUsuarioDto();
        actualizarDto.setEmail("juan@test.com");

        // Mockear el repositorio
        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));

        // Ejecutar el método y verificar que lance excepción
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.updateUser(userId, actualizarDto);
        });
        
        assertEquals("New email cannot be the same as the current email", exception.getMessage());
        verify(usuarioRepository, times(1)).findById(userId);
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void testUpdateUser_TelefonoNegativo() {
        // Preparar datos de prueba
        Long userId = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(userId);
        usuario.setPhoneNumber(987654321L);

        ActualizarUsuarioDto actualizarDto = new ActualizarUsuarioDto();
        actualizarDto.setPhoneNumber(-1L);

        // Mockear el repositorio
        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));

        // Ejecutar el método y verificar que lance excepción
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.updateUser(userId, actualizarDto);
        });
        
        assertEquals("Phone number must be a positive number", exception.getMessage());
        verify(usuarioRepository, times(1)).findById(userId);
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void testUpdateUser_TelefonoCorto() {
        // Preparar datos de prueba
        Long userId = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(userId);
        usuario.setPhoneNumber(987654321L);

        ActualizarUsuarioDto actualizarDto = new ActualizarUsuarioDto();
        actualizarDto.setPhoneNumber(12345678L); // 8 dígitos, menos de 9

        // Mockear el repositorio
        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));

        // Ejecutar el método y verificar que lance excepción
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.updateUser(userId, actualizarDto);
        });
        
        assertEquals("Phone number must be between 9 and 12 digits long", exception.getMessage());
        verify(usuarioRepository, times(1)).findById(userId);
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void testUpdateUser_TelefonoLargo() {
        // Preparar datos de prueba
        Long userId = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(userId);
        usuario.setPhoneNumber(987654321L);

        ActualizarUsuarioDto actualizarDto = new ActualizarUsuarioDto();
        actualizarDto.setPhoneNumber(1234567890123L); // 13 dígitos, más de 12

        // Mockear el repositorio
        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));

        // Ejecutar el método y verificar que lance excepción
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.updateUser(userId, actualizarDto);
        });
        
        assertEquals("Phone number must be between 9 and 12 digits long", exception.getMessage());
        verify(usuarioRepository, times(1)).findById(userId);
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void testUpdateUser_RolNoEncontrado() {
        // Preparar datos de prueba
        Long userId = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(userId);

        ActualizarUsuarioDto actualizarDto = new ActualizarUsuarioDto();
        actualizarDto.setRoleId(999L);

        // Mockear los repositorios
        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        when(rolesRepository.findById(999L)).thenReturn(Optional.empty());

        // Ejecutar el método y verificar que lance excepción
        assertThrows(RuntimeException.class, () -> {
            usuarioService.updateUser(userId, actualizarDto);
        });
        
        verify(usuarioRepository, times(1)).findById(userId);
        verify(rolesRepository, times(1)).findById(999L);
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    // Tests para deleteUser()
    @Test
    void testDeleteUser_Exitoso() {
        // Preparar datos de prueba
        Long userId = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(userId);
        usuario.setFirstName("Juan");
        usuario.setLastName("Pérez");

        // Mockear el repositorio
        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));
        doNothing().when(usuarioRepository).delete(usuario);

        // Ejecutar el método
        String resultado = usuarioService.deleteUser(userId);

        // Verificar el resultado
        assertEquals("User with ID: " + userId + " has been deleted successfully.", resultado);
        
        verify(usuarioRepository, times(1)).findById(userId);
        verify(usuarioRepository, times(1)).delete(usuario);
    }

    @Test
    void testDeleteUser_UsuarioNoEncontrado() {
        // Preparar datos de prueba
        Long userId = 999L;

        // Mockear el repositorio para que no encuentre el usuario
        when(usuarioRepository.findById(userId)).thenReturn(Optional.empty());

        // Ejecutar el método y verificar que lance excepción
        assertThrows(RuntimeException.class, () -> {
            usuarioService.deleteUser(userId);
        });
        
        verify(usuarioRepository, times(1)).findById(userId);
        verify(usuarioRepository, never()).delete(any(Usuario.class));
    }

    // Tests existentes para obtenerDetalleProfesor y login (mantener como están)
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

    @Test
    void loginPasswordVacio(){
        Usuario usuario = new Usuario();
        usuario.setEmail("correo@prueba.com");
        usuario.setPassword("");

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.login(usuario.getEmail(), usuario.getPassword());
        });

        assertEquals("El password no puede estar vacio", ex.getMessage());
        
    }

    @Test
    void loginPasswordNulo(){
        Usuario usuario = new Usuario();
        usuario.setEmail("correo@prueba.com");
        usuario.setPassword(null);
        
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.login(usuario.getEmail(), usuario.getPassword());
        });

        assertEquals("El password no puede estar vacio", ex.getMessage());

    }
}