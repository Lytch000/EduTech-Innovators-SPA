package com.EduTech.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EduTech.dto.permissions.CrearPermisoDto;
import com.EduTech.dto.permissions.PermisoConRolDto;
import com.EduTech.dto.permissions.PermisoDto;
import com.EduTech.dto.permissions.ActualizarPermisoDto;
import com.EduTech.model.Permiso;
import com.EduTech.model.Roles;
import com.EduTech.repository.PermisoRepository;
import com.EduTech.repository.RolesRepository;

/**
 * @author Franco Carrasco
 */
@Service
public class PermisoService {

    @Autowired
    private PermisoRepository repository;

    @Autowired
    private RolesRepository rolesRepository;

    public List<PermisoDto> getAllPermisos() {
        return repository.findAll().stream().map(PermisoDto::new).collect(Collectors.toList());
    }

    public PermisoDto createPermiso(CrearPermisoDto crearPermisoDto) {
        Permiso permiso = new Permiso(crearPermisoDto);

        return new PermisoDto(repository.save(permiso));
    }

    public PermisoConRolDto assignToRole(Long permisoId, Long roleId) {
        Permiso permiso = repository.findById(permisoId).orElseThrow(() -> new RuntimeException("Permiso no encontrado"));
        Roles role = rolesRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        permiso.getRoles().add(role);

        return new PermisoConRolDto(repository.save(permiso));
    }

    public PermisoDto updatePermiso(Long id, ActualizarPermisoDto actualizarPermisoDto) {
        Permiso permiso = repository.findById(id).orElseThrow(() -> new RuntimeException("Permiso no encontrado"));

        String nombre = actualizarPermisoDto.getNombre();
        String descripcion = actualizarPermisoDto.getDescripcion();

        if (nombre != null) {
            permiso.setNombre(actualizarPermisoDto.getNombre());
        }

        if (descripcion != null) {
            permiso.setDescripcion(descripcion);
        }

        if (nombre != null || descripcion != null) {
            permiso.setFechaActualizacion(new Date());
        }

        return new PermisoDto(repository.save(permiso));
    }

    public PermisoDto softDeletePermiso(Long id) {
        Permiso permiso = repository.findById(id).orElseThrow(() -> new RuntimeException("Permiso no encontrado"));
        
        permiso.setActivo(false);
        permiso.setFechaActualizacion(new Date());

        return new PermisoDto(repository.save(permiso));
    }
}
