//Victor garces
package com.EduTech.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.EduTech.model.Usuario;

import java.util.Date;

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
    private Usuario profesor;

}
