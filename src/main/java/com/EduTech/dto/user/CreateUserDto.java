package com.EduTech.dto.user;

import java.sql.Date;

import lombok.Data;

@Data
public class CreateUserDto {
    private Long phoneNumber;
    private String firstName, lastName, rut, email, password;
    private Date birthDate;
}
