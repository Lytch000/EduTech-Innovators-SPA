package com.EduTech.dto.user;

import lombok.Data;

@Data
public class ActualizarContraseniaDto {
    private String email, oldPassword, newPassword;
}
