package com.EduTech.dto.roles;

import java.util.Date;

import com.EduTech.model.Roles;

import lombok.Data;

@Data
public class RespuestaRolDto {

    public RespuestaRolDto(Roles roles) {
        this.id = roles.getId();
        this.nombre = roles.getNombre();
        this.descripcion = roles.getDescripcion();
        this.fechaCreacion = roles.getFechaCreacion();
    }

    private Long id;
    private String nombre;
    private String descripcion;
    private Date fechaCreacion;
}
