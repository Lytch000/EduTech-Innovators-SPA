package com.EduTech.model;

import com.EduTech.model.Usuario;

import org.junit.jupiter.api.Test;

import com.EduTech.dto.user.UsuarioDTO;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class UsuarioTest {
    
    private Usuario user;
    private Date testDate;
    
    @BeforeEach
    void setUp() {
        user = new Usuario();
        testDate = new Date();
    }
    
    @Test
    void testGettersAndSetters() {
        // Set users data
        user.setId(1L);
        user.setFirstName("Juan");
        user.setLastName("Pérez");
        user.setRut("12.345.678-9");
        user.setPassword("password123");
        user.setEmail("juan@correo.com");
        user.setBirthDate(testDate);
        user.setPhoneNumber(987654321L);
        
        // Set Rol data
        Roles role = new Roles();
        role.setId(1L);
        role.setNombre("Estudiante");

        user.setRoles(role);
        
        // Set Curse data
        List<Curso> cursos = new ArrayList<>();

        Curso curso = new Curso();

        curso.setIdCurso(1L);
        curso.setNombreCurso("Matemáticas");
        cursos.add(curso);

        user.setCursosInscritos(cursos);

        assertEquals(1L, user.getId());
        assertEquals("Juan", user.getFirstName());
        assertEquals("Pérez", user.getLastName());
        assertEquals("12.345.678-9", user.getRut());
        assertEquals("password123", user.getPassword());
        assertEquals("juan@correo.com", user.getEmail());
        assertEquals(testDate, user.getBirthDate());
        assertEquals(987654321L, user.getPhoneNumber());
        assertEquals(role, user.getRoles());
        assertEquals(cursos, user.getCursosInscritos());
    }
    
    @Test
    void testNoArgsConstructor() {
        Usuario emptyUser = new Usuario();

        assertNotNull(emptyUser);
        assertNull(emptyUser.getId());
        assertNull(emptyUser.getFirstName());
        assertNull(emptyUser.getLastName());
        assertNull(emptyUser.getRut());
        assertNull(emptyUser.getPassword());
        assertNull(emptyUser.getEmail());
        assertNull(emptyUser.getBirthDate());
        assertNull(emptyUser.getPhoneNumber());
        assertNull(emptyUser.getRoles());
        assertNotNull(emptyUser.getCursosInscritos());
        assertTrue(emptyUser.getCursosInscritos().isEmpty());
    }
    
    @Test
    void testAllArgsConstructor() {
        Roles role = new Roles();
        role.setId(1L);
        role.setNombre("Profesor");
        
        Usuario fullUser = new Usuario("Franco", "Carrasco", "21817903-7", "fcobreque1204@gmail.com", "12345", testDate, 966746554L);
        
        assertEquals("Franco", fullUser.getFirstName());
        assertEquals("Carrasco", fullUser.getLastName());
        assertEquals("21817903-7", fullUser.getRut());
        assertEquals("fcobreque1204@gmail.com", fullUser.getEmail());
        assertEquals("12345", fullUser.getPassword());
        assertEquals(testDate, fullUser.getBirthDate());
        assertEquals(966746554L, fullUser.getPhoneNumber());
    }
    
    @Test
    void testNullValues() {
        user.setFirstName(null);
        user.setLastName(null);
        user.setRut(null);
        user.setPassword(null);
        user.setEmail(null);
        user.setBirthDate(null);
        user.setPhoneNumber(null);
        user.setRoles(null);
        
        assertNull(user.getFirstName());
        assertNull(user.getLastName());
        assertNull(user.getRut());
        assertNull(user.getPassword());
        assertNull(user.getEmail());
        assertNull(user.getBirthDate());
        assertNull(user.getPhoneNumber());
        assertNull(user.getRoles());
        assertNotNull(user.getCursosInscritos());
    }

    @Test
    void testFromUsuarioDto() {
        UsuarioDTO dto = new UsuarioDTO(1L, "Franco", "Carrasco", "fcobreque1204@gmail.com");

        Usuario user = dto.toUser();

        assertEquals(1L, user.getId());
        assertEquals("Franco", user.getFirstName());
        assertEquals("Carrasco", user.getLastName());
        assertEquals("fcobreque1204@gmail.com", user.getEmail());
        assertNull(user.getRut());
        assertNull(user.getPassword());
        assertNull(user.getPhoneNumber());
        assertNull(user.getBirthDate());
        assertNull(user.getRoles());
        assertNotNull(user.getCursosInscritos());
        assertTrue(user.getCursosInscritos().isEmpty());
    }

    @Test
    void testFullUsuarioDtoToUserConversion() {
        // Create a DTO with all fields set using the constructor that takes a Usuario
        user.setId(3L);
        user.setFirstName("Carlos");
        user.setLastName("Rodríguez");
        user.setRut("11.222.333-4");
        user.setPassword("securepass");
        user.setEmail("carlos@correo.com");
        user.setBirthDate(testDate);
        user.setPhoneNumber(555666777L);
        
        UsuarioDTO dto = new UsuarioDTO(user);
        
        Usuario convertedUser = dto.toUser();
        
        // Verify all fields are correctly converted
        assertEquals(3L, convertedUser.getId());
        assertEquals("Carlos", convertedUser.getFirstName());
        assertEquals("Rodríguez", convertedUser.getLastName());
        assertEquals("11.222.333-4", convertedUser.getRut());
        assertEquals("securepass", convertedUser.getPassword());
        assertEquals("carlos@correo.com", convertedUser.getEmail());
        assertEquals(testDate, convertedUser.getBirthDate());
        assertEquals(555666777L, convertedUser.getPhoneNumber());
        
        // Verify relationships are properly initialized
        assertNull(convertedUser.getRoles());
        assertNotNull(convertedUser.getCursosInscritos());
        assertTrue(convertedUser.getCursosInscritos().isEmpty());
    }
}
