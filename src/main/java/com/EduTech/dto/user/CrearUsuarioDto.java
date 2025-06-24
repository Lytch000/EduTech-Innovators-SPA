package com.EduTech.dto.user;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO para la creación de un nuevo usuario.
 * Contiene los datos necesarios para registrar un usuario en el sistema.
 * @author Franco Carrasco
 * @version 1.0
 */
@Data
public class CrearUsuarioDto {

    @Schema(description = "Número de teléfono del usuario", example = "987654321")
    private Long phoneNumber;

    @Schema(description = "ID del rol asociado", example = "2")
    private Long rolId;

    @Schema(description = "Nombre del usuario", example = "Juan")
    private String firstName;

    @Schema(description = "Apellido del usuario", example = "Pérez")
    private String lastName;

    @Schema(description = "RUT del usuario", example = "12345678-9")
    private String rut;

    @Schema(description = "Correo electrónico del usuario", example = "juan.perez@email.com")
    private String email;

    @Schema(description = "Contraseña del usuario")
    private String password;

    @Schema(description = "Fecha de nacimiento del usuario", example = "1990-01-01")
    private Date birthDate;
}
