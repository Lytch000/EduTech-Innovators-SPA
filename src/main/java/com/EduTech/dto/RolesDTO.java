package com.EduTech.dto;

import com.EduTech.model.Roles;
import lombok.Data;

import java.util.Date;

@Data
public class RolesDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private Date fechaCreacion;

    public RolesDTO(Roles roles){
        this.id = roles.getId();;
        this.nombre = roles.getNombre();
        this.descripcion = roles.getDescripcion();
        this.fechaCreacion = roles.getFechaCreacion();
    }

}
