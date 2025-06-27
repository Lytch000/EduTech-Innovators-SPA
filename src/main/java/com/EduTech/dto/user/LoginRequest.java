package com.EduTech.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO para la solicitud de login de usuario.
 * @author Franco Carrasco
 * @version 1.0
 */
@Data
public class LoginRequest {

    @Schema(description = "Correo electrónico del usuario", example = "juan.perez@email.com")
    private String email;

    @Schema(description = "Contraseña del usuario")
    private String password;
}