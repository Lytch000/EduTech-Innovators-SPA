package com.EduTech.dto.permissions;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import com.EduTech.dto.roles.RespuestaRolDto;
import com.EduTech.model.Permiso;
import lombok.Data;

/** 
 * @author Franco Carrasco
 * @version 1.0
 */
@Data
public class PermisoConRolDto {

    public PermisoConRolDto(Permiso permiso) {
        this.id = permiso.getId();
        this.nombre = permiso.getNombre();
        this.descripcion = permiso.getDescripcion();
        this.activo = permiso.getActivo();
        this.fechaCreacion = permiso.getFechaCreacion();
        this.roles = permiso.getRoles().stream().map(RespuestaRolDto::new).collect(Collectors.toSet());
    }

    private Long id;
    private String nombre;
    private String descripcion;
    private Boolean activo;
    private Date fechaCreacion;
    private Set<RespuestaRolDto> roles;
}
