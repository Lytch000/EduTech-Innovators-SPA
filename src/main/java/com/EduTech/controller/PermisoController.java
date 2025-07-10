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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;

/** 
 * @author Franco Carrasco
 * @version 1.0
 */
@RestController
@RequestMapping("api/v1/permisos")
public class PermisoController {
    
    @Operation(
        summary = "Obtener todos los permisos",
        description = "Devuelve una lista de todos los permisos registrados en el sistema.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de permisos obtenida correctamente"),
            @ApiResponse(responseCode = "204", description = "No hay permisos registrados")
        }
    )
    @GetMapping
    public List<PermisoDto> getAllPermisos() {
        return service.getAllPermisos();
    }

    @Operation(
        summary = "Crear nuevo permiso",
        description = "Crea un nuevo permiso en el sistema con los datos proporcionados.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Permiso creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o campos requeridos faltantes")
        }
    )
    @PostMapping
    public PermisoDto createPermiso(@RequestBody CrearPermisoDto crearPermisoDto) {
        return service.createPermiso(crearPermisoDto);
    }

    @Operation(
        summary = "Actualizar permiso",
        description = "Actualiza la información de un permiso existente. Solo se actualizan los campos proporcionados.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Permiso actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o validación fallida"),
            @ApiResponse(responseCode = "404", description = "Permiso no encontrado")
        }
    )
    @PatchMapping("update/{id}")
    public PermisoDto updatePermiso(
        @Parameter(description = "ID del permiso a actualizar", required = true, example = "1")
        @PathVariable Long id, 
        @RequestBody ActualizarPermisoDto actualizarPermisoDto) {
        return service.updatePermiso(id, actualizarPermisoDto);
    }

    @Operation(
        summary = "Asignar permiso a rol",
        description = "Asigna un permiso a un rol específico y devuelve el permiso con sus roles asociados.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Permiso asignado al rol correctamente"),
            @ApiResponse(responseCode = "404", description = "Permiso o rol no encontrado")
        }
    )
    @PatchMapping("assign-to-role/{permisoId}/{roleId}")
    public PermisoConRolDto assignToRole(
        @Parameter(description = "ID del permiso", required = true, example = "1")
        @PathVariable Long permisoId,
        @Parameter(description = "ID del rol", required = true, example = "2")
        @PathVariable Long roleId) {
        return service.assignToRole(permisoId, roleId);
    }

    @Operation(
        summary = "Eliminar permiso (soft delete)",
        description = "Realiza un soft delete del permiso, marcándolo como inactivo y devuelve el permiso actualizado.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Permiso eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Permiso no encontrado")
        }
    )
    @PatchMapping("soft-delete/{id}")
    public PermisoDto softDeletePermiso(
        @Parameter(description = "ID del permiso a eliminar", required = true, example = "1")
        @PathVariable Long id) {
        return service.softDeletePermiso(id);
    }

    @Autowired
    private PermisoService service;
}
