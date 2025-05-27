package com.EduTech.dto.user;

import java.util.Date;

import com.EduTech.model.User;

import lombok.Data;

/**
 * UserResponseDto is a Data Transfer Object (DTO) used for representing user information in the system.
 *
 * @author Franco Carrasco
 * @version 1.0
 * 
 * @apiNote This class only contains fields that can be updated, such as phone number and email.
 */
@Data
public class UserResponseDto {
    public UserResponseDto(User user) {
        this.id = user.getId();
        this.phoneNumber = user.getPhoneNumber();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.rut = user.getRut();
        this.email = user.getEmail();
        this.birthDate = user.getBirthDate();
        this.roleName = user.getRoles().getNombre();

    }

    private Long id, phoneNumber;
    private String firstName, lastName, rut, email;
    private Date birthDate;
    private String roleName;
}
