/** Autor Juan Olguin
 *
 */

package com.EduTech.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"estudiantes", "profesor"})
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 13, nullable = false)
    private String rut;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private int edad;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private int celular;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "id_rol_fk")
    private Roles roles;

    @ManyToMany(mappedBy = "estudiantes")
    @JsonIgnoreProperties({"estudiantes", "profesor"})
    private List<Curso> cursosInscritos = new ArrayList<>();


}
