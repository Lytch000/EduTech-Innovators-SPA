package com.EduTech.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EduTech.dto.user.CreateUserDto;
import com.EduTech.dto.user.UpdateUserDto;
import com.EduTech.dto.user.UpdateUserPasswordDto;
import com.EduTech.model.User;
import com.EduTech.service.UserService;
import com.EduTech.utils.ApiResponse;
import com.EduTech.utils.ListApiResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;




/**
 * UserController is a REST controller that handles user-related operations.
 * 
 * @apiNote This controller provides endpoints for creating, updating, retrieving, and deleting users.
 * 
 * @author Franco Carrasco
 * @version 1.0
 */
@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @GetMapping
    public ApiResponse<List<User>> getAllUsers() {
        List<User> users = service.getUsers();

        return new ListApiResponse<>(users);
    }
    
    @PostMapping
    public ApiResponse<User> createUser(@RequestBody CreateUserDto newUser) {
        User entity = service.createUser(newUser);
        
        return new ApiResponse<>(entity, 201);
    }
    
    @PatchMapping("{id}")
    public ApiResponse updateUser(@PathVariable Long id, @RequestBody UpdateUserDto userFields) {
        try {
            User entity = service.updateUser(id, userFields);
            
            return new ApiResponse<>(entity, 200);
        } catch (NoSuchElementException e) {
            return new ApiResponse<String>("User not found", 404);
        } catch (IllegalArgumentException e) {
            return new ApiResponse<String>(e.getMessage(), 400);
        }
    }

    @PatchMapping("change-password")
    public ApiResponse<String> changePassword(@RequestBody UpdateUserPasswordDto userFields) {
        try {
            if (userFields.getEmail() == null || userFields.getOldPassword() == null || userFields.getNewPassword() == null)
                throw new IllegalArgumentException("Email, old password, and new password must be provided");

            String message = service.changePassword(userFields);

            return new ApiResponse<>(message, 200);
        } catch (NoSuchElementException e) {
            return new ApiResponse<>("User not found", 404);
        } catch (IllegalArgumentException e) {
            return new ApiResponse<>(e.getMessage(), 400);
        }
    }

    @DeleteMapping("{id}")
    public ApiResponse<String> deleteUser(@PathVariable Long id) {
        try {
            String message = service.deleteUser(id);
            return new ApiResponse<>(message, 200);
        } catch (NoSuchElementException e) {
            return new ApiResponse<>("The user with ID: " + 1 + " doesn't exist", 404);
        }
    }

    @Autowired
    private UserService service;
}
