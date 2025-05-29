/*
Autor Juan Olguin
 */

package com.EduTech.dto;

import com.EduTech.dto.user.RespuestaUsuarioDto;
import com.EduTech.model.Roles;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class RolesDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private Date fechaCreacion;
    List<RespuestaUsuarioDto>usuarioList = new ArrayList<>() ;

    public RolesDTO(Roles roles){
        this.id = roles.getId();;
        this.nombre = roles.getNombre();
        this.descripcion = roles.getDescripcion();
        this.fechaCreacion = roles.getFechaCreacion();
    }

}
