package com.EduTech.dto.user;

import java.util.Date;

import com.EduTech.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserDto is a Data Transfer Object (DTO) used for transferring user data between layers in the application.
 * It provides methods to convert between User and UserDto objects.
 *
 * @author Franco Carrasco
 * @version 1.0
 */
@Data
@NoArgsConstructor
public class UserDto {
    public UserDto(User user) {
        this.id = user.getId();
        this.phoneNumber = user.getPhoneNumber();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.rut = user.getRut();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.birthDate = user.getBirthDate();
    }

    public User toUser() {
        User user = new User();
        user.setId(this.id);
        user.setPhoneNumber(this.phoneNumber);
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setRut(this.rut);
        user.setPassword(this.password);
        user.setEmail(this.email);
        user.setBirthDate(this.birthDate);
        return user;
    }

    private Long id, phoneNumber;
    private String firstName, lastName, rut, password, email;
    private Date birthDate;
}
