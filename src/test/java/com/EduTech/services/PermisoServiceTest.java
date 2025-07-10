package com.EduTech.services;

import com.EduTech.dto.permissions.*;
import com.EduTech.model.Permiso;
import com.EduTech.model.Roles;
import com.EduTech.repository.PermisoRepository;
import com.EduTech.repository.RolesRepository;
import com.EduTech.service.PermisoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PermisoServiceTest {

    @Mock
    private PermisoRepository repository;

    @Mock
    private RolesRepository rolesRepository;

    @InjectMocks
    private PermisoService permisoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPermisosConPermisos() {
        Permiso permiso1 = new Permiso();
        permiso1.setId(1L);
        permiso1.setNombre("CREAR_USUARIO");
        permiso1.setDescripcion("Permite crear usuarios");
        permiso1.setActivo(true);
        permiso1.setFechaCreacion(new Date());
        permiso1.setFechaActualizacion(new Date());

        Permiso permiso2 = new Permiso();
        permiso2.setId(2L);
        permiso2.setNombre("EDITAR_USUARIO");
        permiso2.setDescripcion("Permite editar usuarios");
        permiso2.setActivo(true);
        permiso2.setFechaCreacion(new Date());
        permiso2.setFechaActualizacion(new Date());

        List<Permiso> permisos = Arrays.asList(permiso1, permiso2);

        when(repository.findAll()).thenReturn(permisos);

        List<PermisoDto> resultado = permisoService.getAllPermisos();

        assertEquals(2, resultado.size());
        assertEquals("CREAR_USUARIO", resultado.get(0).getNombre());
        assertEquals("EDITAR_USUARIO", resultado.get(1).getNombre());
        assertEquals("Permite crear usuarios", resultado.get(0).getDescripcion());
        assertEquals("Permite editar usuarios", resultado.get(1).getDescripcion());
        
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetAllPermisosSinPermisos() {
        when(repository.findAll()).thenReturn(Arrays.asList());

        List<PermisoDto> resultado = permisoService.getAllPermisos();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        
        verify(repository, times(1)).findAll();
    }

    @Test
    void testCreatePermisoExitoso() {
        CrearPermisoDto crearPermisoDto = new CrearPermisoDto();
        crearPermisoDto.setNombre("GESTIONAR_ROLES");
        crearPermisoDto.setDescripcion("Permite gestionar roles del sistema");

        Permiso permisoGuardado = new Permiso();
        permisoGuardado.setId(1L);
        permisoGuardado.setNombre("GESTIONAR_ROLES");
        permisoGuardado.setDescripcion("Permite gestionar roles del sistema");
        permisoGuardado.setActivo(true);
        permisoGuardado.setFechaCreacion(new Date());
        permisoGuardado.setFechaActualizacion(new Date());

        when(repository.save(any(Permiso.class))).thenReturn(permisoGuardado);

        PermisoDto resultado = permisoService.createPermiso(crearPermisoDto);

        assertNotNull(resultado);
        assertEquals("GESTIONAR_ROLES", resultado.getNombre());
        assertEquals("Permite gestionar roles del sistema", resultado.getDescripcion());
        assertTrue(resultado.getActivo());
        
        verify(repository, times(1)).save(any(Permiso.class));
    }

    @Test
    void testAssignToRoleExitoso() {
        Long permisoId = 1L;
        Long roleId = 2L;

        Permiso permiso = new Permiso();
        permiso.setId(permisoId);
        permiso.setNombre("CREAR_USUARIO");
        permiso.setDescripcion("Permite crear usuarios");
        permiso.setActivo(true);
        permiso.setFechaCreacion(new Date());
        permiso.setFechaActualizacion(new Date());
        permiso.setRoles(new HashSet<>());

        Roles rol = new Roles();
        rol.setId(roleId);
        rol.setNombre("ADMIN");
        rol.setDescripcion("Administrador del sistema");

        Permiso permisoActualizado = new Permiso();
        permisoActualizado.setId(permisoId);
        permisoActualizado.setNombre("CREAR_USUARIO");
        permisoActualizado.setDescripcion("Permite crear usuarios");
        permisoActualizado.setActivo(true);
        permisoActualizado.setFechaCreacion(new Date());
        permisoActualizado.setFechaActualizacion(new Date());
        Set<Roles> roles = new HashSet<>();
        roles.add(rol);
        permisoActualizado.setRoles(roles);

        when(repository.findById(permisoId)).thenReturn(Optional.of(permiso));
        when(rolesRepository.findById(roleId)).thenReturn(Optional.of(rol));
        when(repository.save(any(Permiso.class))).thenReturn(permisoActualizado);

        PermisoConRolDto resultado = permisoService.assignToRole(permisoId, roleId);

        assertNotNull(resultado);
        assertEquals(permisoId, resultado.getId());
        assertEquals("CREAR_USUARIO", resultado.getNombre());
        assertEquals(1, resultado.getRoles().size());
        
        verify(repository, times(1)).findById(permisoId);
        verify(rolesRepository, times(1)).findById(roleId);
        verify(repository, times(1)).save(any(Permiso.class));
    }

    @Test
    void testAssignToRolePermisoNoEncontrado() {
        Long permisoId = 999L;
        Long roleId = 2L;

        when(repository.findById(permisoId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            permisoService.assignToRole(permisoId, roleId);
        });
        
        verify(repository, times(1)).findById(permisoId);
        verify(rolesRepository, never()).findById(any(Long.class));
        verify(repository, never()).save(any(Permiso.class));
    }

    @Test
    void testAssignToRoleRolNoEncontrado() {
        Long permisoId = 1L;
        Long roleId = 999L;

        Permiso permiso = new Permiso();
        permiso.setId(permisoId);
        permiso.setNombre("CREAR_USUARIO");

        when(repository.findById(permisoId)).thenReturn(Optional.of(permiso));
        when(rolesRepository.findById(roleId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            permisoService.assignToRole(permisoId, roleId);
        });
        
        verify(repository, times(1)).findById(permisoId);
        verify(rolesRepository, times(1)).findById(roleId);
        verify(repository, never()).save(any(Permiso.class));
    }

    @Test
    void testUpdatePermisoExitoso() {
        Long permisoId = 1L;
        Permiso permiso = new Permiso();
        permiso.setId(permisoId);
        permiso.setNombre("CREAR_USUARIO");
        permiso.setDescripcion("Permite crear usuarios");
        permiso.setActivo(true);
        permiso.setFechaCreacion(new Date());
        permiso.setFechaActualizacion(new Date());

        ActualizarPermisoDto actualizarDto = new ActualizarPermisoDto();
        actualizarDto.setNombre("EDITAR_USUARIO");
        actualizarDto.setDescripcion("Permite editar usuarios");

        Permiso permisoActualizado = new Permiso();
        permisoActualizado.setId(permisoId);
        permisoActualizado.setNombre("EDITAR_USUARIO");
        permisoActualizado.setDescripcion("Permite editar usuarios");
        permisoActualizado.setActivo(true);
        permisoActualizado.setFechaCreacion(new Date());
        permisoActualizado.setFechaActualizacion(new Date());

        when(repository.findById(permisoId)).thenReturn(Optional.of(permiso));
        when(repository.save(any(Permiso.class))).thenReturn(permisoActualizado);

        PermisoDto resultado = permisoService.updatePermiso(permisoId, actualizarDto);

        assertNotNull(resultado);
        assertEquals("EDITAR_USUARIO", resultado.getNombre());
        assertEquals("Permite editar usuarios", resultado.getDescripcion());
        
        verify(repository, times(1)).findById(permisoId);
        verify(repository, times(1)).save(any(Permiso.class));
    }

    @Test
    void testUpdatePermisoSoloNombre() {
        Long permisoId = 1L;
        Permiso permiso = new Permiso();
        permiso.setId(permisoId);
        permiso.setNombre("CREAR_USUARIO");
        permiso.setDescripcion("Permite crear usuarios");
        permiso.setActivo(true);
        permiso.setFechaCreacion(new Date());
        permiso.setFechaActualizacion(new Date());

        ActualizarPermisoDto actualizarDto = new ActualizarPermisoDto();
        actualizarDto.setNombre("EDITAR_USUARIO");
        actualizarDto.setDescripcion(null);

        Permiso permisoActualizado = new Permiso();
        permisoActualizado.setId(permisoId);
        permisoActualizado.setNombre("EDITAR_USUARIO");
        permisoActualizado.setDescripcion("Permite crear usuarios");
        permisoActualizado.setActivo(true);
        permisoActualizado.setFechaCreacion(new Date());
        permisoActualizado.setFechaActualizacion(new Date());

        when(repository.findById(permisoId)).thenReturn(Optional.of(permiso));
        when(repository.save(any(Permiso.class))).thenReturn(permisoActualizado);

        PermisoDto resultado = permisoService.updatePermiso(permisoId, actualizarDto);

        assertNotNull(resultado);
        assertEquals("EDITAR_USUARIO", resultado.getNombre());
        assertEquals("Permite crear usuarios", resultado.getDescripcion());
        
        verify(repository, times(1)).findById(permisoId);
        verify(repository, times(1)).save(any(Permiso.class));
    }

    @Test
    void testUpdatePermisoSoloDescripcion() {
        Long permisoId = 1L;
        Permiso permiso = new Permiso();
        permiso.setId(permisoId);
        permiso.setNombre("CREAR_USUARIO");
        permiso.setDescripcion("Permite crear usuarios");
        permiso.setActivo(true);
        permiso.setFechaCreacion(new Date());
        permiso.setFechaActualizacion(new Date());

        ActualizarPermisoDto actualizarDto = new ActualizarPermisoDto();
        actualizarDto.setNombre(null);
        actualizarDto.setDescripcion("Nueva descripción del permiso");

        Permiso permisoActualizado = new Permiso();
        permisoActualizado.setId(permisoId);
        permisoActualizado.setNombre("CREAR_USUARIO");
        permisoActualizado.setDescripcion("Nueva descripción del permiso");
        permisoActualizado.setActivo(true);
        permisoActualizado.setFechaCreacion(new Date());
        permisoActualizado.setFechaActualizacion(new Date());

        when(repository.findById(permisoId)).thenReturn(Optional.of(permiso));
        when(repository.save(any(Permiso.class))).thenReturn(permisoActualizado);

        PermisoDto resultado = permisoService.updatePermiso(permisoId, actualizarDto);

        assertNotNull(resultado);
        assertEquals("CREAR_USUARIO", resultado.getNombre());
        assertEquals("Nueva descripción del permiso", resultado.getDescripcion());
        
        verify(repository, times(1)).findById(permisoId);
        verify(repository, times(1)).save(any(Permiso.class));
    }

    @Test
    void testUpdatePermisoPermisoNoEncontrado() {
        Long permisoId = 999L;
        ActualizarPermisoDto actualizarDto = new ActualizarPermisoDto();
        actualizarDto.setNombre("EDITAR_USUARIO");

        when(repository.findById(permisoId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            permisoService.updatePermiso(permisoId, actualizarDto);
        });
        
        verify(repository, times(1)).findById(permisoId);
        verify(repository, never()).save(any(Permiso.class));
    }

    @Test
    void testSoftDeletePermisoExitoso() {
        Long permisoId = 1L;
        Permiso permiso = new Permiso();
        permiso.setId(permisoId);
        permiso.setNombre("CREAR_USUARIO");
        permiso.setDescripcion("Permite crear usuarios");
        permiso.setActivo(true);
        permiso.setFechaCreacion(new Date());
        permiso.setFechaActualizacion(new Date());

        Permiso permisoEliminado = new Permiso();
        permisoEliminado.setId(permisoId);
        permisoEliminado.setNombre("CREAR_USUARIO");
        permisoEliminado.setDescripcion("Permite crear usuarios");
        permisoEliminado.setActivo(false);
        permisoEliminado.setFechaCreacion(new Date());
        permisoEliminado.setFechaActualizacion(new Date());

        when(repository.findById(permisoId)).thenReturn(Optional.of(permiso));
        when(repository.save(any(Permiso.class))).thenReturn(permisoEliminado);

        PermisoDto resultado = permisoService.softDeletePermiso(permisoId);

        assertNotNull(resultado);
        assertEquals(permisoId, resultado.getId());
        assertEquals("CREAR_USUARIO", resultado.getNombre());
        assertFalse(resultado.getActivo());
        
        verify(repository, times(1)).findById(permisoId);
        verify(repository, times(1)).save(any(Permiso.class));
    }

    @Test
    void testSoftDeletePermisoPermisoNoEncontrado() {
        Long permisoId = 999L;

        when(repository.findById(permisoId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            permisoService.softDeletePermiso(permisoId);
        });
        
        verify(repository, times(1)).findById(permisoId);
        verify(repository, never()).save(any(Permiso.class));
    }
}
