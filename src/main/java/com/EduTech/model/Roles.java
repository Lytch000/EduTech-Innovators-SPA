/** Autor Juan Olguin
 *
 */

package com.EduTech.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa un rol")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Codigo del rol", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Nombre del rol", example = "Administrador")
    private String nombre;

    @Column(nullable = false)
    @Schema(description = "Descripcion del rol", example = "Acceso completo al sistema y configuración")
    private String descripcion;

    @Column(nullable = false)
    @Schema(description = "Fecha de la creacion del rol", example = "2025-05-24T10:00:00")
    private Date fechaCreacion;

    @OneToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    @JsonIgnore
    @Schema(description = "Lista de usuarios asociados a un rol")
    private List<Usuario> usuarioList = new ArrayList<>();

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    @Schema(description = "Lista de permisos asociados a un rol")
    private Set<Permiso> permisos = new HashSet<>();
}
