package com.EduTech.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

/** 
 * @author Franco Carrasco
 * @version 1.0
 */
@Entity
@Table(name = "usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    public Usuario(String firstName, String lastName, String rut, String email, String password, Date birthDate, Long phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.rut = rut;
        this.email = email;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(unique = true, length = 12, nullable = false)
    private String rut;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(unique = true, length = 50, nullable = false)
    private String email;

    @Column(nullable = false)
    private Date birthDate;

    @Column(length = 12, nullable = true)
    private Long phoneNumber;

    @ManyToOne
    @JoinColumn(name = "id_rol_fk")
    @JsonIgnoreProperties
    private Roles roles;

    @ManyToMany(mappedBy = "estudiantes")
    @JsonIgnoreProperties({"estudiantes", "profesor"})
    private List<Curso> cursosInscritos = new ArrayList<>();

}
