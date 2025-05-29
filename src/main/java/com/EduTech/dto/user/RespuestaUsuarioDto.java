package com.EduTech.dto.user;

import java.util.Date;

import com.EduTech.model.Usuario;

import lombok.Data;

@Data
public class RespuestaUsuarioDto {
    public RespuestaUsuarioDto(Usuario user) {
        this.id = user.getId();
        this.phoneNumber = user.getPhoneNumber();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.rut = user.getRut();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.birthDate = user.getBirthDate();
        this.rol = user.getRoles().getNombre();
    }

    public RespuestaUsuarioDto(UsuarioDTO user) {
        this.id = user.getId();
        this.phoneNumber = user.getPhoneNumber();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.rut = user.getRut();
    }

    private Long id, phoneNumber;
    private String firstName, lastName, rut, password, email, rol;
    private Date birthDate;
}
