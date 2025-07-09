package com.EduTech.dto.permissions;

import java.util.Date;

import com.EduTech.model.Permiso;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO para representar los datos de un permiso.
 * Incluye información básica y de auditoría sobre el permiso.
 * Autor: Franco Carrasco
 * @version 1.0
 */
@Data
@Schema(description = "DTO para representar los datos de un permiso")
public class PermisoDto {
    public PermisoDto() {}

    /**
     * Constructor que transforma una entidad Permiso en un PermisoDto.
     * @param permiso entidad Permiso desde la base de datos
     */
    public PermisoDto(Permiso permiso) {
        this.id = permiso.getId();
        this.nombre = permiso.getNombre();
        this.descripcion = permiso.getDescripcion();
        this.activo = permiso.getActivo();
        this.fechaCreacion = permiso.getFechaCreacion();
        this.fechaActualizacion = permiso.getFechaActualizacion();
    }

    /**
     * Constructor que transforma un CrearPermisoDto en un PermisoDto inicializado.
     * @param crearPermisoDto DTO con los datos para crear un nuevo permiso
     */
    public PermisoDto(CrearPermisoDto crearPermisoDto) {
        this.nombre = crearPermisoDto.getNombre();
        this.descripcion = crearPermisoDto.getDescripcion();
        this.activo = true;
        this.fechaCreacion = new Date();
        this.fechaActualizacion = new Date();
    }

    @Schema(description = "Identificador único del permiso", example = "101")
    private Long id;

    @Schema(description = "Nombre del permiso", example = "VER_USUARIOS")
    private String nombre;

    @Schema(description = "Descripción del permiso", example = "Permite visualizar la lista de usuarios")
    private String descripcion;

    @Schema(description = "Indica si el permiso está activo", example = "true")
    private Boolean activo;

    @Schema(description = "Fecha de creación del permiso", example = "2024-06-24T00:00:00.000+00:00")
    private Date fechaCreacion;

    @Schema(description = "Fecha de última actualización del permiso", example = "2025-06-24T00:00:00.000+00:00")
    private Date fechaActualizacion;
}
