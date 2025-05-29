//Victor garces
package com.EduTech.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.EduTech.model.Usuario;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "curso")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCurso;

    @Column(nullable = false)
    private String nombreCurso;

    @Column(nullable = false)
    private String descripcion; // Resumen del contenido del curso

    @Column(nullable = false)
    private String categoria;    //Programacion ,Diseño, Negocios ,etc...

    @Column(nullable = false)
    private int horasDuracion;    //Tiempo que tomara hacer el curso

    @Column(nullable = false)
    private Double precioCurso;

    @Column(nullable = false)
    private Date fechaPublicacion;   //Creacion del curso

    @ManyToOne
    @JoinColumn(name = "id_profesor_fk")
    @JsonIgnoreProperties({"estudiantes", "profesor"})
    private Usuario profesor;

    //Autor Juan Olguin
    @ManyToMany
    @JoinTable(
            name = "usuario_curso",
            joinColumns = @JoinColumn(name = "curso_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    @JsonIgnoreProperties({"estudiantes", "profesor"})
    private List<Usuario> estudiantes = new ArrayList<>();

}
