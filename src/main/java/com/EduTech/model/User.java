package com.EduTech.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User is an entity class representing a user in the EduTech application.
 * It contains fields for user information such as first name, last name, RUT, email, password, birth date, and phone number.
 *
 * @author Franco Carrasco
 * @version 1.0
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    public User(String firstName, String lastName, String rut, String email, String password, Date birthDate, Long phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.rut = rut;
        this.email = email;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(unique = true, length = 12, nullable = false)
    private String rut;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(unique = true, length = 50, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private Date birthDate;

    @Column(length = 12, nullable = true)
    private Long phoneNumber;
}
