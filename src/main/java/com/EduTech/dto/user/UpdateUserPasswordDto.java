package com.EduTech.dto.user;

import lombok.Data;

@Data
public class UpdateUserPasswordDto {
    private String email, oldPassword, newPassword;
}
