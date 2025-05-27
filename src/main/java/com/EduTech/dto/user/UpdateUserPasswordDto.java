package com.EduTech.dto.user;

import lombok.Data;

/**
 * UpdateUserPasswordDto is a Data Transfer Object (DTO) used for updating user passwords in the system.
 *
 * @author Franco Carrasco
 * @version 1.0
 * 
 * @apiNote Unlike UpdateUserDto, this class contains fields for the user's email, old password, and new password.
 *          It is specifically designed for password change operations.
 */
@Data
public class UpdateUserPasswordDto {
    private String email, oldPassword, newPassword;
}
