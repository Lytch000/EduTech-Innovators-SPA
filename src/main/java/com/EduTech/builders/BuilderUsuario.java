package com.EduTech.builders;

import com.EduTech.model.Usuario;
import com.EduTech.model.Roles;
import java.util.Date;

public class BuilderUsuario {
    public BuilderUsuario() {
    }

    public BuilderUsuario firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public BuilderUsuario lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public BuilderUsuario rut(String rut) {
        this.rut = rut;
        return this;
    }

    public BuilderUsuario password(String password) {
        this.password = password;
        return this;
    }

    public BuilderUsuario email(String email) {
        this.email = email;
        return this;
    }

    public BuilderUsuario birthDate(Date birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public BuilderUsuario phoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public BuilderUsuario roles(Roles roles) {
        this.roles = roles;
        return this;
    }

    public Usuario build() {
        Usuario usuario = new Usuario(firstName, lastName, rut, email, password, birthDate, phoneNumber);
        usuario.setRoles(roles);
        return usuario;
    }

    private String firstName, lastName, rut, password, email;
    private Date birthDate;
    private Long phoneNumber;
    private Roles roles = new Roles();
}
