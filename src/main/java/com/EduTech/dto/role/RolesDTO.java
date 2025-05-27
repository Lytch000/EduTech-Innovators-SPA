package com.EduTech.dto.role;

import com.EduTech.dto.user.UserDto;
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
    List<UserDto> userList = new ArrayList<>();

    public RolesDTO(Roles roles){
        this.id = roles.getId();;
        this.nombre = roles.getNombre();
        this.descripcion = roles.getDescripcion();
        this.fechaCreacion = roles.getFechaCreacion();
    }

}
