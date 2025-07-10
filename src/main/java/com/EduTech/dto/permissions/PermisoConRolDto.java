package com.EduTech.dto.permissions;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import com.EduTech.dto.roles.RespuestaRolDto;
import com.EduTech.model.Permiso;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO que representa un permiso junto con los roles asociados.
 * Se utiliza para responder con la información completa del permiso y sus relaciones.
 * Autor: Franco Carrasco
 * @version 1.0
 */
@Data
@Schema(description = "DTO que representa un permiso junto con los roles asociados")
public class PermisoConRolDto {
    public PermisoConRolDto() {}

    /**
     * Constructor que transforma una entidad Permiso en un DTO con sus roles asociados.
     * @param permiso Entidad Permiso con relaciones cargadas
     */
    public PermisoConRolDto(Permiso permiso) {
        this.id = permiso.getId();
        this.nombre = permiso.getNombre();
        this.descripcion = permiso.getDescripcion();
        this.activo = permiso.getActivo();
        this.fechaCreacion = permiso.getFechaCreacion();
        this.roles = permiso.getRoles()
                .stream()
                .map(RespuestaRolDto::new)
                .collect(Collectors.toSet());
    }

    @Schema(description = "Identificador único del permiso", example = "101")
    private Long id;

    @Schema(description = "Nombre del permiso", example = "EDITAR_CURSOS")
    private String nombre;

    @Schema(description = "Descripción del permiso", example = "Permite editar información de cursos")
    private String descripcion;

    @Schema(description = "Indica si el permiso está activo", example = "true")
    private Boolean activo;

    @Schema(description = "Fecha de creación del permiso", example = "2024-06-24T00:00:00.000+00:00")
    private Date fechaCreacion;

    @Schema(description = "Conjunto de roles asociados al permiso")
    private Set<RespuestaRolDto> roles;
}
