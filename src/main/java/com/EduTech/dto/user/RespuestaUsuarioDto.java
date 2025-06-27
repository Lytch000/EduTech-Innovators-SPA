package com.EduTech.dto.user;

import java.util.Date;

import com.EduTech.model.Usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO para responder con los datos de un usuario.
 * Incluye información personal, de autenticación y rol.
 * @author Franco Carrasco
 * @version 1.0
 */
@Data
public class RespuestaUsuarioDto {
    public RespuestaUsuarioDto(Usuario user) {
        this.id = user.getId();
        this.phoneNumber = user.getPhoneNumber();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.rut = user.getRut();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.birthDate = user.getBirthDate();
        this.rol = user.getRoles().getNombre();
    }

    public RespuestaUsuarioDto(UsuarioDTO user) {
        this.id = user.getId();
        this.phoneNumber = user.getPhoneNumber();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.rut = user.getRut();
    }

    @Schema(description = "Identificador único del usuario", example = "1")
    private Long id;

    @Schema(description = "Número de teléfono del usuario", example = "987654321")
    private Long phoneNumber;

    @Schema(description = "Nombre del usuario", example = "Juan")
    private String firstName;

    @Schema(description = "Apellido del usuario", example = "Pérez")
    private String lastName;

    @Schema(description = "RUT del usuario", example = "12345678-9")
    private String rut;

    @Schema(description = "Contraseña del usuario")
    private String password;

    @Schema(description = "Correo electrónico del usuario", example = "juan.perez@email.com")
    private String email;

    @Schema(description = "Rol del usuario", example = "PROFESOR")
    private String rol;

    @Schema(description = "Fecha de nacimiento del usuario", example = "1990-01-01")
    private Date birthDate;

    // Constructores...
}
