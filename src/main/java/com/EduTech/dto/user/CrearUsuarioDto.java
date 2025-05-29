package com.EduTech.dto.user;

import java.util.Date;

import lombok.Data;

@Data
public class CrearUsuarioDto {

    private Long phoneNumber, rolId;
    private String firstName, lastName, rut, email, password;
    private Date birthDate;
}
