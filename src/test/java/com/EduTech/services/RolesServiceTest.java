package com.EduTech.services;

import com.EduTech.dto.roles.RolRequest;
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
    void listarSinContenido_noLlamaUsuarioRepository() {
        when(rolesRepository.buscarTodos()).thenReturn(List.of());

        List<RolesDTO> roles = rolesService.listar();

        assertNotNull(roles);
        assertTrue(roles.isEmpty());
        verify(usuarioRepository, never()).findAllByIdRoles(anyLong());
    }

    // Test: crear rol exitosamente (no existe duplicado)
    @Test
    void addNewRol() {
        RolRequest rolRequest = new RolRequest();
        rolRequest.setNombre("Admin");
        rolRequest.setDescripcion("Administrador del sistema");
        rolRequest.setFechaCreacion(new Date());

        Roles rol = new Roles();
        rol.setId(2L);
        rol.setNombre(rolRequest.getNombre());
        rol.setDescripcion(rolRequest.getDescripcion());
        rol.setFechaCreacion(rolRequest.getFechaCreacion());

        when(rolesRepository.findByNombre("Admin")).thenReturn(null);
        when(rolesRepository.save(any(Roles.class))).thenReturn(rol);

        Roles resultado = rolesService.addNewRol(rolRequest);

        assertNotNull(resultado);
        assertEquals("Admin", resultado.getNombre());
        verify(rolesRepository, times(1)).save(any(Roles.class));
    }

    // Test: crear rol duplicado (debe lanzar excepción)
    @Test
    void addNewRolDuplicado() {
        RolRequest rolRequest = new RolRequest();
        rolRequest.setNombre("Admin");
        rolRequest.setDescripcion("desc");
        rolRequest.setFechaCreacion(new Date());
        when(rolesRepository.findByNombre("Admin")).thenReturn(new Roles());
        Exception ex = assertThrows(IllegalArgumentException.class, () -> rolesService.addNewRol(rolRequest));
        assertEquals("El nombre del rol ya está en uso", ex.getMessage());
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

        RolRequest nuevoRol = new RolRequest();
        nuevoRol.setNombre("Nuevo");
        nuevoRol.setDescripcion("desc");
        nuevoRol.setFechaCreacion(new Date());

        String result = rolesService.updateRol(5L, nuevoRol);

        assertEquals("No se encuentra rol indicado", result);
        verify(rolesRepository, times(1)).existsById(5L);
        verify(rolesRepository, never()).save(any(Roles.class));
    }

    @Test
    void updateRol() {
        when(rolesRepository.existsById(3L)).thenReturn(true);

        RolRequest nuevoRol = new RolRequest();
        nuevoRol.setNombre("Actualizado");
        nuevoRol.setDescripcion("desc");
        nuevoRol.setFechaCreacion(new Date());

        Roles rolActualizado = new Roles();
        rolActualizado.setId(3L);
        rolActualizado.setNombre("Actualizado");
        rolActualizado.setDescripcion("desc");
        rolActualizado.setFechaCreacion(nuevoRol.getFechaCreacion());

        when(rolesRepository.save(any(Roles.class))).thenReturn(rolActualizado);

        String result = rolesService.updateRol(3L, nuevoRol);

        assertEquals("Rol actualizado correctamente", result);
        verify(rolesRepository, times(1)).save(any(Roles.class));
    }

    // Test: actualizar rol exitosamente (nombre igual al mismo id)
    @Test
    void updateRolNombreIgualMismoId_noLanzaExcepcion() {
        when(rolesRepository.existsById(10L)).thenReturn(true);

        RolRequest nuevoRol = new RolRequest();
        nuevoRol.setNombre("Admin");
        nuevoRol.setDescripcion("desc");
        nuevoRol.setFechaCreacion(new Date());

        Roles existente = new Roles();
        existente.setId(10L);
        existente.setNombre("Admin");
        when(rolesRepository.findByNombre("Admin")).thenReturn(existente);

        when(rolesRepository.save(any(Roles.class))).thenReturn(existente);

        String result = rolesService.updateRol(10L, nuevoRol);

        assertEquals("Rol actualizado correctamente", result);
        verify(rolesRepository, times(1)).save(any(Roles.class));
    }

    // Test: actualizar rol con nombre duplicado (otro id)
    @Test
    void updateRolNombreDuplicado() {
        when(rolesRepository.existsById(11L)).thenReturn(true);

        RolRequest nuevoRol = new RolRequest();
        nuevoRol.setNombre("Admin");
        nuevoRol.setDescripcion("desc");
        nuevoRol.setFechaCreacion(new Date());

        Roles existente = new Roles();
        existente.setId(99L); // otro id
        existente.setNombre("Admin");
        when(rolesRepository.findByNombre("Admin")).thenReturn(existente);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> rolesService.updateRol(11L, nuevoRol));
        assertEquals("El nombre del rol ya está en uso", ex.getMessage());
    }
}
