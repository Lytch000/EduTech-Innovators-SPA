package com.EduTech.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EduTech.dto.permissions.CrearPermisoDto;
import com.EduTech.dto.permissions.PermisoConRolDto;
import com.EduTech.dto.permissions.PermisoDto;
import com.EduTech.dto.permissions.ActualizarPermisoDto;
import com.EduTech.service.PermisoService;

@RestController
@RequestMapping("api/v1/permisos")
public class PermisoController {
    @GetMapping
    public List<PermisoDto> getAllPermisos() {
        return service.getAllPermisos();
    }

    @PostMapping
    public PermisoDto createPermiso(@RequestBody CrearPermisoDto crearPermisoDto) {
        return service.createPermiso(crearPermisoDto);
    }

    @PatchMapping("update/{id}")
    public PermisoDto updatePermiso(@PathVariable Long id, @RequestBody ActualizarPermisoDto actualizarPermisoDto) {
        return service.updatePermiso(id, actualizarPermisoDto);
    }

    @PatchMapping("assign-to-role/{permisoId}/{roleId}")
    public PermisoConRolDto assignToRole(@PathVariable Long permisoId, @PathVariable Long roleId) {
        return service.assignToRole(permisoId, roleId);
    }

    @PatchMapping("soft-delete/{id}")
    public PermisoDto softDeletePermiso(@PathVariable Long id) {
        return service.softDeletePermiso(id);
    }

    @Autowired
    private PermisoService service;
}
