package com.EduTech.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.EduTech.dto.permissions.CrearPermisoDto;

import io.swagger.v3.oas.annotations.media.Schema;

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
@Schema(description = "Entidad que representa un permiso del sistema.")
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

    @Schema(description = "Identificador único del permiso", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Nombre del permiso", example = "CREAR_USUARIO")
    @Column(nullable = false, length = 50)
    private String nombre;

    @Schema(description = "Descripción del permiso", example = "Permite crear usuarios")
    @Column(nullable = false, length = 100)
    private String descripcion;

    @Schema(description = "Indica si el permiso está activo", example = "true")
    @Column(nullable = false)
    private Boolean activo = true;

    @Schema(description = "Fecha de creación del permiso", example = "2025-06-22T00:00:00.000+00:00")
    @Column(nullable = false)
    private Date fechaCreacion;

    @Schema(description = "Fecha de última actualización del permiso", example = "2025-06-22T00:00:00.000+00:00")
    @Column(nullable = false)
    private Date fechaActualizacion;

    @Schema(description = "Roles asociados a este permiso", hidden = true)
    @ManyToMany
    @JoinTable(
        name = "roles_permisos",
        joinColumns = @JoinColumn(name = "permiso_id"),
        inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private Set<Roles> roles = new HashSet<>();
}
