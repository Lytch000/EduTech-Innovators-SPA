package com.EduTech.dto.user;

import lombok.Data;

/** 
 * @author Franco Carrasco
 * @version 1.0
 */
@Data
public class LoginRequest {
    private String email;
    private String password;
} 