package com.EduTech.dto.permissions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO utilizado para la creación de un nuevo permiso.
 * Contiene los campos necesarios para registrar un permiso en el sistema.
 * Autor: Franco Carrasco
 * @version 1.0
 */
@Data
@Schema(description = "DTO utilizado para la creación de un nuevo permiso")
public class CrearPermisoDto {

    @Schema(description = "Nombre del permiso a crear", example = "GESTIONAR_USUARIOS")
    private String nombre;

    @Schema(description = "Descripción detallada del permiso", example = "Permiso para gestionar usuarios en el sistema")
    private String descripcion;
}
