package com.EduTech.builders;

import java.sql.Date;

import com.EduTech.model.User;
public class UserBuilder {
    public UserBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder setRut(String rut) {
        this.rut = rut;
        return this;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public UserBuilder setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public User build() {
        return new User(firstName, lastName, rut, email, password, birthDate, phoneNumber);
    }
    
    private Long phoneNumber;
    private String firstName, lastName, rut, email, password;
    private Date birthDate;
}
