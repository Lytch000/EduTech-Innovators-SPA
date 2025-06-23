package com.EduTech.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Entidad que representa un usuario del sistema.")
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

    @Schema(description = "Identificador único del usuario", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Nombre del usuario", example = "Juan")
    @Column(nullable = false, length = 50)
    private String firstName;

    @Schema(description = "Apellido del usuario", example = "Pérez")
    @Column(nullable = false, length = 50)
    private String lastName;

    @Schema(description = "RUT del usuario", example = "12.345.678-9")
    @Column(unique = true, length = 12, nullable = false)
    private String rut;

    @Schema(description = "Contraseña del usuario", example = "password123")
    @Column(length = 100, nullable = false)
    private String password;

    @Schema(description = "Correo electrónico del usuario", example = "juan@correo.com")
    @Column(unique = true, length = 50, nullable = false)
    private String email;

    @Schema(description = "Fecha de nacimiento", example = "1990-01-01T00:00:00.000+00:00")
    @Column(nullable = false)
    private Date birthDate;

    @Schema(description = "Número de teléfono", example = "987654321")
    @Column(length = 12, nullable = true)
    private Long phoneNumber;

    @Schema(description = "Rol asignado al usuario")
    @ManyToOne
    @JoinColumn(name = "id_rol_fk")
    @JsonIgnoreProperties
    private Roles roles;

    @Schema(description = "Cursos en los que está inscrito el usuario", hidden = true)
    @ManyToMany(mappedBy = "estudiantes")
    @JsonIgnoreProperties({"estudiantes", "profesor"})
    private List<Curso> cursosInscritos = new ArrayList<>();

}
