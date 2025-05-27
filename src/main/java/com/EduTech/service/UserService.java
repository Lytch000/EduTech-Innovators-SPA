package com.EduTech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EduTech.builders.UserBuilder;
import com.EduTech.dto.user.CreateUserDto;
import com.EduTech.dto.user.UpdateUserDto;
import com.EduTech.dto.user.UpdateUserPasswordDto;
import com.EduTech.dto.user.UserDto;
import com.EduTech.model.User;
import com.EduTech.repository.UserRepository;

/**
 * UserService is a service class that provides methods for managing users in the application.
 *
 * @author Franco Carrasco
 * @version 1.0
 */
@Service
public class UserService {
    public List<User> getUsers() {
        List<User> users = repository.findAll();

        return users;
    }

    public User createUser(CreateUserDto newUserDto) {
        User user = new UserBuilder()
                .setBirthDate(newUserDto.getBirthDate())
                .setEmail(newUserDto.getEmail())
                .setFirstName(newUserDto.getFirstName())
                .setLastName(newUserDto.getLastName())
                .setPhoneNumber(newUserDto.getPhoneNumber())
                .setRut(newUserDto.getRut())
                .setPassword(newUserDto.getPassword())
                .build();

        return repository.save(user);
    }

    public User updateUser(Long id, UpdateUserDto userFields) {
        User user = repository.findById(id).orElseThrow();

        String newEmail = userFields.getEmail();
        Long newPhoneNumber = userFields.getPhoneNumber();

        if (newEmail != null) {
            if (newEmail.isEmpty() || newEmail.isBlank())
                throw new IllegalArgumentException("Email cannot be empty or blank");
            
            if (newEmail.equals(user.getEmail()))
                throw new IllegalArgumentException("New email cannot be the same as the current email");
            
            user.setEmail(newEmail);
        }

        if (newPhoneNumber != null) {
            if (newPhoneNumber <= 0)
                throw new IllegalArgumentException("Phone number must be a positive number");
            
            else if (newPhoneNumber.equals(user.getPhoneNumber()))
                throw new IllegalArgumentException("New phone number cannot be the same as the current phone number");

            else if (newPhoneNumber.toString().length() < 9 || newPhoneNumber.toString().length() > 12)
                throw new IllegalArgumentException("Phone number must be between 9 and 12 digits long");

            user.setPhoneNumber(newPhoneNumber);
        }

        return repository.save(user);
    }

    public String changePassword(UpdateUserPasswordDto fields) {
        String email = fields.getEmail();
        String oldPassword = fields.getOldPassword();

        UserDto user = repository.findByEmail(email).orElseThrow();

        String currentUserPassword = user.getPassword();

        if (!currentUserPassword.equals(oldPassword)) 
            throw new IllegalArgumentException("Old password does not match the current password.");
        else if (currentUserPassword.equals(fields.getNewPassword())) 
            throw new IllegalArgumentException("New password cannot be the same as the old password.");
        
        user.setPassword(fields.getNewPassword());
        
        repository.save(user.toUser());

        return "Password for user with email: " + email + " has been changed successfully.";
    }

    public String deleteUser(Long id) {
        User user = repository.findById(id).orElseThrow();

        repository.delete(user);

        return "User with ID: " + id + " has been deleted successfully.";
    }

    @Autowired
    private UserRepository repository;
}
