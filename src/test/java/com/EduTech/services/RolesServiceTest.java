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
    void listar_debeRetornarListaDeRolesDTO() {
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
    void addNewRol_debeGuardarYRetornarRol() {
        Roles rol = new Roles();
        rol.setId(2L);
        rol.setNombre("Cliente");

        when(rolesRepository.save(rol)).thenReturn(rol);

        Roles result = rolesService.addNewRol(rol);

        assertNotNull(result);
        assertEquals("Cliente", result.getNombre());
        verify(rolesRepository, times(1)).save(rol);
    }

    @Test
    void deleteRol_rolNoExiste_debeRetornarMensajeNoEncontrado() {
        when(rolesRepository.existsById(99L)).thenReturn(false);

        String result = rolesService.deleteRol(99L);

        assertEquals("No se encuentra rol especificado", result);
        verify(rolesRepository, times(1)).existsById(99L);
        verify(rolesRepository, never()).deleteById(anyLong());
    }

    @Test
    void deleteRol_rolExiste_debeEliminarYRetornarMensajeCorrecto() {
        when(rolesRepository.existsById(1L)).thenReturn(true);
        doNothing().when(rolesRepository).deleteById(1L);

        String result = rolesService.deleteRol(1L);

        assertEquals("Eliminado correctamente", result);
        verify(rolesRepository, times(1)).deleteById(1L);
    }

    @Test
    void updateRol_rolNoExiste_debeRetornarMensajeNoEncontrado() {
        when(rolesRepository.existsById(5L)).thenReturn(false);

        Roles nuevoRol = new Roles();
        nuevoRol.setNombre("Nuevo");

        String result = rolesService.updateRol(5L, nuevoRol);

        assertEquals("No se encuentra rol indicado", result);
        verify(rolesRepository, times(1)).existsById(5L);
        verify(rolesRepository, never()).save(any(Roles.class));
    }

    @Test
    void updateRol_rolExiste_debeActualizarYRetornarMensajeCorrecto() {
        when(rolesRepository.existsById(3L)).thenReturn(true);

        Roles nuevoRol = new Roles();
        nuevoRol.setNombre("Actualizado");

        when(rolesRepository.save(any(Roles.class))).thenReturn(nuevoRol);

        String result = rolesService.updateRol(3L, nuevoRol);

        assertEquals("Rol actualizado correctamente", result);
        verify(rolesRepository, times(1)).save(nuevoRol);
    }
}
