/* 
package com.EduTech.services;

import com.EduTech.dto.roles.RolesDTO;
import com.EduTech.model.Roles;
import com.EduTech.repository.RolesRepository;
import com.EduTech.repository.UsuarioRepository;
import com.EduTech.service.RolesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class RolesServiceTest {

    @InjectMocks
    private RolesService rolesService;

    @Mock
    private RolesRepository rolesRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listar() {
        Roles rol = new Roles();
        rol.setId(1L);
        rol.setNombre("Admin");
        rol.setDescripcion("Administrador del sistema");
        rol.setFechaCreacion(new Date());

        RolesDTO rolDTO = new RolesDTO(rol);

        List<RolesDTO> rolesDTOList = List.of(rolDTO);

        when(rolesRepository.buscarTodos()).thenReturn(rolesDTOList);
        when(usuarioRepository.findAllByIdRoles(1L)).thenReturn(new ArrayList<>());

        List<RolesDTO> result = rolesService.listar();

        assertEquals(1, result.size());
        assertEquals("Admin", result.get(0).getNombre());
        verify(rolesRepository, times(1)).buscarTodos();
        verify(usuarioRepository, times(1)).findAllByIdRoles(1L);
    }

    @Test
    void listarSinContenido() {
        when(rolesRepository.buscarTodos()).thenReturn(List.of());

        List<RolesDTO> roles = rolesService.listar();

        assertNotNull(roles);
        assertTrue(roles.isEmpty());
        verify(rolesRepository, times(1)).buscarTodos();
    }

    @Test
    void addNewRol() {
        Roles rol = new Roles();
        rol.setId(2L);
        rol.setNombre("Cliente");
        rol.setDescripcion("Cliente del sistema");
        rol.setFechaCreacion(new Date());

        when(rolesRepository.findByNombre("Cliente")).thenReturn(null);
        when(rolesRepository.save(any(Roles.class))).thenReturn(rol);

        Roles result = rolesService.addNewRol(rol);

        assertNotNull(result);
        assertEquals("Cliente", result.getNombre());
        verify(rolesRepository, times(1)).save(rol);
    }

    @Test
    void addNewRolNombreVacio() {
        Roles rol = new Roles();
        rol.setNombre("");
        rol.setDescripcion("desc");
        rol.setFechaCreacion(new Date());
        Exception ex = assertThrows(IllegalArgumentException.class, () -> rolesService.addNewRol(rol));
        assertEquals("El nombre del rol no puede estar vacío", ex.getMessage());
    }

    @Test
    void addNewRolNombreNull() {
        Roles rol = new Roles();
        rol.setNombre(null);
        rol.setDescripcion("desc");
        rol.setFechaCreacion(new Date());
        Exception ex = assertThrows(IllegalArgumentException.class, () -> rolesService.addNewRol(rol));
        assertEquals("El nombre del rol no puede estar vacío", ex.getMessage());
    }

    @Test
    void addNewRolDescripcionVacia() {
        Roles rol = new Roles();
        rol.setNombre("Admin");
        rol.setDescripcion("");
        rol.setFechaCreacion(new Date());
        Exception ex = assertThrows(IllegalArgumentException.class, () -> rolesService.addNewRol(rol));
        assertEquals("La descripción del rol no puede estar vacía", ex.getMessage());
    }

    @Test
    void addNewRolDescripcionNull() {
        Roles rol = new Roles();
        rol.setNombre("Admin");
        rol.setDescripcion(null);
        rol.setFechaCreacion(new Date());
        Exception ex = assertThrows(IllegalArgumentException.class, () -> rolesService.addNewRol(rol));
        assertEquals("La descripción del rol no puede estar vacía", ex.getMessage());
    }

    @Test
    void addNewRolFechaCreacionNull() {
        Roles rol = new Roles();
        rol.setNombre("Admin");
        rol.setDescripcion("desc");
        rol.setFechaCreacion(null);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> rolesService.addNewRol(rol));
        assertEquals("La fecha de creación no puede estar vacía", ex.getMessage());
    }

    @Test
    void addNewRolDuplicado() {
        Roles rol = new Roles();
        rol.setNombre("Admin");
        rol.setDescripcion("desc");
        rol.setFechaCreacion(new Date());
        when(rolesRepository.findByNombre("Admin")).thenReturn(new Roles());
        Exception ex = assertThrows(IllegalArgumentException.class, () -> rolesService.addNewRol(rol));
        assertEquals("El rol ya existe", ex.getMessage());
    }

    @Test
    void deleteRolRolNoExiste() {
        when(rolesRepository.existsById(99L)).thenReturn(false);

        String result = rolesService.deleteRol(99L);

        assertEquals("No se encuentra rol especificado", result);
        verify(rolesRepository, times(1)).existsById(99L);
        verify(rolesRepository, never()).deleteById(anyLong());
    }

    @Test
    void deleteRol() {
        when(rolesRepository.existsById(1L)).thenReturn(true);
        doNothing().when(rolesRepository).deleteById(1L);

        String result = rolesService.deleteRol(1L);

        assertEquals("Eliminado correctamente", result);
        verify(rolesRepository, times(1)).deleteById(1L);
    }

    @Test
    void updateRolRolNoExiste() {
        when(rolesRepository.existsById(5L)).thenReturn(false);

        Roles nuevoRol = new Roles();
        nuevoRol.setNombre("Nuevo");

        String result = rolesService.updateRol(5L, nuevoRol);

        assertEquals("No se encuentra rol indicado", result);
        verify(rolesRepository, times(1)).existsById(5L);
        verify(rolesRepository, never()).save(any(Roles.class));
    }

    @Test
    void updateRol() {
        when(rolesRepository.existsById(3L)).thenReturn(true);

        Roles nuevoRol = new Roles();
        nuevoRol.setNombre("Actualizado");

        when(rolesRepository.save(any(Roles.class))).thenReturn(nuevoRol);

        String result = rolesService.updateRol(3L, nuevoRol);

        assertEquals("Rol actualizado correctamente", result);
        verify(rolesRepository, times(1)).save(nuevoRol);
    }
}
*/