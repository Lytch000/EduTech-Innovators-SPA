package com.EduTech.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO para actualizar los datos de un usuario existente.
 * Permite modificar email, teléfono y rol.
 * @author Franco Carrasco
 * @version 1.0
 */
@Data
public class ActualizarUsuarioDto {

    @Schema(description = "Número de teléfono del usuario", example = "987654321")
    private Long phoneNumber;

    @Schema(description = "ID del nuevo rol", example = "2")
    private Long roleId;

    @Schema(description = "Correo electrónico del usuario", example = "juan.perez@email.com")
    private String email;
}
