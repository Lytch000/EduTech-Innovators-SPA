package com.EduTech.controller;

import com.EduTech.assemblers.PermisoAssembler;
import com.EduTech.dto.permissions.*;
import com.EduTech.service.PermisoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/v2/permisos")
public class PermisoControllerV2 {

    private final PermisoService service;
    private final PermisoAssembler assembler;

    public PermisoControllerV2(PermisoService service, PermisoAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @Operation(
        summary = "Obtener todos los permisos con HATEOAS",
        description = "Devuelve una lista de todos los permisos registrados en el sistema con enlaces HATEOAS.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de permisos obtenida correctamente"),
            @ApiResponse(responseCode = "204", description = "No hay permisos registrados")
        }
    )
    @GetMapping()
    public ResponseEntity<CollectionModel<EntityModel<PermisoDto>>> getAllPermisos() {
        List<PermisoDto> permisos = service.getAllPermisos();
        if (permisos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<EntityModel<PermisoDto>> permisoModels = permisos.stream().map(assembler::toModel).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(permisoModels));
    }

    @Operation(
        summary = "Crear nuevo permiso con HATEOAS",
        description = "Crea un nuevo permiso en el sistema con los datos proporcionados y devuelve el permiso creado con enlaces HATEOAS.",
        responses = {
            @ApiResponse(responseCode = "201", description = "Permiso creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o campos requeridos faltantes")
        }
    )
    @PostMapping
    public ResponseEntity<EntityModel<PermisoDto>> createPermiso(@RequestBody CrearPermisoDto crearPermisoDto) {
        PermisoDto permisoCreado = service.createPermiso(crearPermisoDto);
        EntityModel<PermisoDto> permisoModel = assembler.toModel(permisoCreado);
        return ResponseEntity.status(HttpStatus.CREATED).body(permisoModel);
    }

    @Operation(
        summary = "Obtener permiso por ID con HATEOAS",
        description = "Devuelve la información de un permiso específico por su ID con enlaces HATEOAS.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Permiso encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Permiso no encontrado")
        }
    )
    @GetMapping("/id/{id}")
    public ResponseEntity<EntityModel<PermisoDto>> getOnePermiso(
        @Parameter(description = "ID del permiso", required = true, example = "1")
        @PathVariable Long id) {
        try {
            // Como el servicio no tiene método getOnePermiso, usamos getAllPermisos y filtramos
            List<PermisoDto> permisos = service.getAllPermisos();
            PermisoDto permiso = permisos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Permiso no encontrado"));
            
            EntityModel<PermisoDto> permisoModel = assembler.toModel(permiso);
            return ResponseEntity.ok(permisoModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(
        summary = "Actualizar permiso con HATEOAS",
        description = "Actualiza la información de un permiso existente y devuelve el permiso actualizado con enlaces HATEOAS. Solo se actualizan los campos proporcionados.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Permiso actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o validación fallida"),
            @ApiResponse(responseCode = "404", description = "Permiso no encontrado")
        }
    )
    @PatchMapping("/update/{id}")
    public ResponseEntity<EntityModel<PermisoDto>> updatePermiso(
        @Parameter(description = "ID del permiso a actualizar", required = true, example = "1")
        @PathVariable Long id, 
        @RequestBody ActualizarPermisoDto actualizarPermisoDto) {
        try {
            PermisoDto permisoActualizado = service.updatePermiso(id, actualizarPermisoDto);
            EntityModel<PermisoDto> permisoModel = assembler.toModel(permisoActualizado);
            return ResponseEntity.ok(permisoModel);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(
        summary = "Asignar permiso a rol con HATEOAS",
        description = "Asigna un permiso a un rol específico y devuelve el permiso con sus roles asociados y enlaces HATEOAS.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Permiso asignado al rol correctamente"),
            @ApiResponse(responseCode = "404", description = "Permiso o rol no encontrado")
        }
    )
    @PatchMapping("/assign-to-role/{permisoId}/{roleId}")
    public ResponseEntity<EntityModel<PermisoConRolDto>> assignToRole(
        @Parameter(description = "ID del permiso", required = true, example = "1")
        @PathVariable Long permisoId,
        @Parameter(description = "ID del rol", required = true, example = "2")
        @PathVariable Long roleId) {
        try {
            PermisoConRolDto permisoConRol = service.assignToRole(permisoId, roleId);
            EntityModel<PermisoConRolDto> permisoModel = EntityModel.of(permisoConRol);
            return ResponseEntity.ok(permisoModel);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(
        summary = "Eliminar permiso (soft delete) con HATEOAS",
        description = "Realiza un soft delete del permiso, marcándolo como inactivo y devuelve el permiso actualizado con enlaces HATEOAS.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Permiso eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Permiso no encontrado")
        }
    )
    @PatchMapping("/soft-delete/{id}")
    public ResponseEntity<EntityModel<PermisoDto>> softDeletePermiso(
        @Parameter(description = "ID del permiso a eliminar", required = true, example = "1")
        @PathVariable Long id) {
        try {
            PermisoDto permisoEliminado = service.softDeletePermiso(id);
            EntityModel<PermisoDto> permisoModel = assembler.toModel(permisoEliminado);
            return ResponseEntity.ok(permisoModel);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
