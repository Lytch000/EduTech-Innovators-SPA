//Victor garces

package com.EduTech.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "instructor")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInstructor;

    @Column(nullable = false)
    private String especialidad;

    @Column(nullable = false)
    private String plataformaEnseñanza;

    @Column(nullable = false)
    private int cursosAsignados;

    @Column(nullable = false)
    private Date fechaIncorporacion;

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Curso> cursos;
}
