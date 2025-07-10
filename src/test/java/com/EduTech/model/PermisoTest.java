package com.EduTech.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.EduTech.dto.permissions.CrearPermisoDto;

public class PermisoTest {
    private Permiso permission;
    private Date testDate;
    private Roles testRole;
    
    @BeforeEach
    void setUp() {
        permission = new Permiso();
        testDate = new Date();
        testRole = new Roles();
        testRole.setId(1L);
        testRole.setNombre("Estudiante");
    }
    
    @Test
    void testGettersAndSetters() {
        // Set permissions data
        permission.setId(1L);
        permission.setActivo(true);
        permission.setDescripcion("This is only a test");
        permission.setFechaActualizacion(testDate);
        permission.setFechaCreacion(testDate);
        permission.setNombre("WRITE");
        permission.setRoles(null);

        Set<Roles> roles = new HashSet<>();

        roles.add(testRole);

        permission.setRoles(roles);

        assertEquals(1L, permission.getId());
        assertEquals(true, permission.getActivo());
        assertEquals("This is only a test", permission.getDescripcion());
        assertEquals(testDate, permission.getFechaActualizacion());
        assertEquals(testDate, permission.getFechaCreacion());
        assertEquals("WRITE", permission.getNombre());
        assertEquals(roles, permission.getRoles());
    }
    
    @Test
    void testNoArgsConstructor() {
        Permiso emptyPermission = new Permiso();
        assertNotNull(emptyPermission);
        assertNull(emptyPermission.getId());
        assertNull(emptyPermission.getNombre());
        assertNull(emptyPermission.getDescripcion());
        assertEquals(true, emptyPermission.getActivo());
        assertNull(emptyPermission.getFechaCreacion());
        assertNull(emptyPermission.getFechaActualizacion());
        assertNotNull(emptyPermission.getRoles());
        assertTrue(emptyPermission.getRoles().isEmpty());
    }
    
    @Test
    void testAllArgsConstructor() {
        Set<Roles> roles = new HashSet<>();
        roles.add(testRole);
        
        Permiso fullPermission = new Permiso(1L, "READ", "Permite leer datos", true, testDate, testDate, roles);
        
        assertEquals(1L, fullPermission.getId());
        assertEquals("READ", fullPermission.getNombre());
        assertEquals("Permite leer datos", fullPermission.getDescripcion());
        assertEquals(true, fullPermission.getActivo());
        assertEquals(testDate, fullPermission.getFechaCreacion());
        assertEquals(testDate, fullPermission.getFechaActualizacion());
        assertEquals(roles, fullPermission.getRoles());
    }
    
    @Test
    void testCrearPermisoDtoConstructor() {
        CrearPermisoDto dto = new CrearPermisoDto();
        dto.setNombre("DELETE");
        dto.setDescripcion("Permite eliminar registros");
        
        Permiso dtoPermission = new Permiso(dto);
        
        assertEquals("DELETE", dtoPermission.getNombre());
        assertEquals("Permite eliminar registros", dtoPermission.getDescripcion());
        assertEquals(true, dtoPermission.getActivo());
        assertNotNull(dtoPermission.getFechaCreacion());
        assertNotNull(dtoPermission.getFechaActualizacion());
        assertNotNull(dtoPermission.getRoles());
    }
    
    @Test
    void testNullValues() {
        permission.setNombre(null);
        permission.setDescripcion(null);
        permission.setActivo(null);
        permission.setFechaCreacion(null);
        permission.setFechaActualizacion(null);
        permission.setRoles(null);
        
        assertNull(permission.getNombre());
        assertNull(permission.getDescripcion());
        assertNull(permission.getActivo());
        assertNull(permission.getFechaCreacion());
        assertNull(permission.getFechaActualizacion());
        assertNull(permission.getRoles());
    }
}
