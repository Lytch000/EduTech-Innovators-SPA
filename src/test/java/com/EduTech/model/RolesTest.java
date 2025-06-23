package com.EduTech.model;

import com.EduTech.model.Roles;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class RolesTest {

    @Test
    void testGettersAndSetters() {
        Roles rol = new Roles();
        Date now = new Date();

        rol.setId(1L);
        rol.setNombre("ADMIN");
        rol.setDescripcion("Administrador");
        rol.setFechaCreacion(now);

        assertEquals(1L, rol.getId());
        assertEquals("ADMIN", rol.getNombre());
        assertEquals("Administrador", rol.getDescripcion());
        assertEquals(now, rol.getFechaCreacion());
        assertNotNull(rol.getUsuarioList());
        assertNotNull(rol.getPermisos());
    }

    @Test
    void testAllArgsConstructor() {
        Date now = new Date();
        Roles rol = new Roles(2L, "USER", "Usuario", now, null, null);

        assertEquals(2L, rol.getId());
        assertEquals("USER", rol.getNombre());
        assertEquals("Usuario", rol.getDescripcion());
        assertEquals(now, rol.getFechaCreacion());
    }
}