package com.EduTech.dto.permissions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO utilizado para actualizar un permiso existente.
 * Permite modificar el nombre y la descripción del permiso.
 * Autor: Franco Carrasco
 * @version 1.0
 */
@Data
@Schema(description = "DTO utilizado para actualizar un permiso existente")
public class ActualizarPermisoDto {

    @Schema(description = "Nuevo nombre del permiso", example = "GESTIONAR_ROLES")
    private String nombre;

    @Schema(description = "Nueva descripción del permiso", example = "Permite la administración de roles del sistema")
    private String descripcion;
}
