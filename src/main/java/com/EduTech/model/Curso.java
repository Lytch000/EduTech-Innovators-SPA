//Victor garces
package com.EduTech.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.EduTech.model.Usuario;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Schema(description = "Entidad que representa un curso en el sistema.")
@Entity
@Table(name = "curso")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Curso {

    @Schema(description = "Identificador único del curso", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCurso;

    @Schema(description = "Nombre del curso", example = "Programación en Java")
    @Column(nullable = false)
    private String nombreCurso;

    @Schema(description = "Descripción del curso", example = "Curso introductorio de Java")
    @Column(nullable = false)
    private String descripcion; // Resumen del contenido del curso

    @Schema(description = "Categoría del curso", example = "Programación")
    @Column(nullable = false)
    private String categoria;    //Programacion ,Diseño, Negocios ,etc...

    @Schema(description = "Horas de duración del curso", example = "40")
    @Column(nullable = false)
    private int horasDuracion;    //Tiempo que tomara hacer el curso

    @Schema(description = "Precio del curso", example = "199.99")
    @Column(nullable = false)
    private Double precioCurso;

    @Schema(description = "Fecha de publicación del curso", example = "2025-06-22T00:00:00.000+00:00")
    @Column(nullable = false)
    private Date fechaPublicacion;   //Creacion del curso

    @Schema(description = "Profesor asignado al curso")
    @ManyToOne
    @JoinColumn(name = "id_profesor_fk")
    private Usuario profesor;

    @Schema(description = "Estudiantes inscritos en el curso", hidden = true)
    @ManyToMany
    @JoinTable(
        name = "usuario_curso",
        joinColumns = @JoinColumn(name = "curso_id"),
        inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    @JsonIgnoreProperties({"estudiantes", "profesor"})
    private List<Usuario> estudiantes = new ArrayList<>();

}
