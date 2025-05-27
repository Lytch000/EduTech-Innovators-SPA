package com.EduTech.dto.user;

import java.sql.Date;

import lombok.Data;


/**
 * CreateUserDto is a Data Transfer Object (DTO) used for creating new users in the system.
 *
 * @author Franco Carrasco
 * @version 1.0
 */
@Data
public class CreateUserDto {
    private Long phoneNumber, rolId;
    private String firstName, lastName, rut, email, password;
    private Date birthDate;
}
