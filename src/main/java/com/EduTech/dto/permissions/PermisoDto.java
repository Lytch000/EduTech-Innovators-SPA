package com.EduTech.dto.permissions;

import java.util.Date;

import com.EduTech.model.Permiso;

import lombok.Data;

@Data
public class PermisoDto {

    public PermisoDto(Permiso permiso) {
        this.id = permiso.getId();
        this.nombre = permiso.getNombre();
        this.descripcion = permiso.getDescripcion();
        this.activo = permiso.getActivo();
        this.fechaCreacion = permiso.getFechaCreacion();
        this.fechaActualizacion = permiso.getFechaActualizacion();
    }

    public PermisoDto(CrearPermisoDto crearPermisoDto) {
        this.nombre = crearPermisoDto.getNombre();
        this.descripcion = crearPermisoDto.getDescripcion();
        this.activo = true;
        this.fechaCreacion = new Date();
        this.fechaActualizacion = new Date();
    }

    private Long id;
    private String nombre;
    private String descripcion;
    private Boolean activo;
    private Date fechaCreacion;
    private Date fechaActualizacion;
}
