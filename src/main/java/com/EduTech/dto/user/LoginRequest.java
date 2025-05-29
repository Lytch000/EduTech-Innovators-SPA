package com.EduTech.dto.user;

import lombok.Data;

/** 
 * author Juan Olguin
 */
@Data
public class LoginRequest {
    private String email;
    private String password;
} 