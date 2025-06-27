/*
 * DTO para transferir datos de roles entre capas.
 * Incluye información básica del rol y la lista de usuarios asociados.
 * Autor: Juan Olguin
 * @version 1.0
 */
package com.EduTech.dto.roles;

import com.EduTech.dto.user.RespuestaUsuarioDto;
import com.EduTech.model.Roles;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Schema(description = "DTO para transferir datos de roles")
public class RolesDTO {

    @Schema(description = "Identificador único del rol", example = "1")
    private Long id;

    @Schema(description = "Nombre del rol", example = "ADMIN")
    private String nombre;

    @Schema(description = "Descripción del rol", example = "Administrador del sistema")
    private String descripcion;

    @Schema(description = "Fecha de creación del rol", example = "2024-06-24T00:00:00.000+00:00")
    private Date fechaCreacion;

    @Schema(description = "Lista de usuarios asociados a este rol")
    List<RespuestaUsuarioDto> usuarioList = new ArrayList<>();

    public RolesDTO(Roles roles){
        this.id = roles.getId();
        this.nombre = roles.getNombre();
        this.descripcion = roles.getDescripcion();
        this.fechaCreacion = roles.getFechaCreacion();
    }
}
