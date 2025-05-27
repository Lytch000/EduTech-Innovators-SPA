package com.EduTech.dto.user;

import lombok.Data;

/**
 * UpdateUserDto is a Data Transfer Object (DTO) used for updating user information in the system.
 *
 * @author Franco Carrasco
 * @version 1.0
 * 
 * @apiNote This class only contains fields that can be updated, such as phone number and email.
 */
@Data
public class UpdateUserDto {
    private Long phoneNumber;
    private String email;
}
