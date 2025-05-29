/** Autor Juan Olguin
 *
 */

package com.EduTech.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private Date fechaCreacion;

    @OneToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Usuario> usuarioList = new ArrayList<>();

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<Permiso> permisos = new HashSet<>();
}
