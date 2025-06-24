package com.EduTech.dto.roles;

import java.util.Date;

import com.EduTech.model.Roles;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO para responder con los datos de un rol.
 * Incluye información básica del rol.
 * Autor: Juan Olguin
 * @version 1.0
 */
@Data
@Schema(description = "DTO para responder con los datos de un rol")
public class RespuestaRolDto {

    public RespuestaRolDto(Roles roles) {
        this.id = roles.getId();
        this.nombre = roles.getNombre();
        this.descripcion = roles.getDescripcion();
        this.fechaCreacion = roles.getFechaCreacion();
    }

    @Schema(description = "Identificador único del rol", example = "1")
    private Long id;

    @Schema(description = "Nombre del rol", example = "ADMIN")
    private String nombre;

    @Schema(description = "Descripción del rol", example = "Administrador del sistema")
    private String descripcion;

    @Schema(description = "Fecha de creación del rol", example = "2024-06-24T00:00:00.000+00:00")
    private Date fechaCreacion;
}
