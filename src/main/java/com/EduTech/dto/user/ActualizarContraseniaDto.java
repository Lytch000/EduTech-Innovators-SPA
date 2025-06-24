package com.EduTech.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO para actualizar la contraseña de un usuario.
 * Incluye email, contraseña actual y nueva contraseña.
 * @author Franco Carrasco
 * @version 1.0
 */
@Data
public class ActualizarContraseniaDto {

    @Schema(description = "Correo electrónico del usuario", example = "juan.perez@email.com")
    private String email;

    @Schema(description = "Contraseña actual del usuario")
    private String oldPassword;

    @Schema(description = "Nueva contraseña del usuario")
    private String newPassword;
}
