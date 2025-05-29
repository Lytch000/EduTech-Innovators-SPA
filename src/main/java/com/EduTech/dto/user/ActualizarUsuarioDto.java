package com.EduTech.dto.user;

import lombok.Data;

@Data
public class ActualizarUsuarioDto {
    private Long phoneNumber, roleId;
    private String email;
}
