package com.EduTech.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.EduTech.dto.permissions.CrearPermisoDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

/** 
 * @author Franco Carrasco
 * @version 1.0
 */
@Data
@Entity
@Table(name = "permisos")
@AllArgsConstructor
@NoArgsConstructor
public class Permiso {

    public Permiso(CrearPermisoDto nuevoPermiso) {
        this.nombre = nuevoPermiso.getNombre();
        this.descripcion = nuevoPermiso.getDescripcion();
        this.activo = true;
        this.fechaCreacion = new Date();
        this.fechaActualizacion = new Date();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String descripcion;

    @Column(nullable = false)
    private Boolean activo = true;

    @Column(nullable = false)
    private Date fechaCreacion;

    @Column(nullable = false)
    private Date fechaActualizacion;

    @ManyToMany
    @JoinTable(
        name = "roles_permisos",
        joinColumns = @JoinColumn(name = "permiso_id"),
        inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private Set<Roles> roles = new HashSet<>();
}
