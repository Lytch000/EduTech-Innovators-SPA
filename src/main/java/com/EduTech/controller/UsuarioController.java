/** Autor Juan Olguin
 *
 */

package com.EduTech.controller;

import com.EduTech.dto.user.UsuarioDTO;
import com.EduTech.model.Usuario;
import com.EduTech.service.UsuarioService;
import com.EduTech.utils.ApiResponse;
import com.EduTech.utils.ListApiResponse;

import com.EduTech.dto.user.CrearUsuarioDto;
import com.EduTech.dto.user.RespuestaUsuarioDto;
import com.EduTech.dto.user.ActualizarUsuarioDto;

import com.EduTech.dto.user.ActualizarContraseniaDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {
    @GetMapping
    public ApiResponse<List<RespuestaUsuarioDto>> getAllUsers() {
        List<RespuestaUsuarioDto> users = service.getUsers();

        return new ListApiResponse<>(users);
    }

    @PostMapping
    public ApiResponse<RespuestaUsuarioDto> createUser(@RequestBody CrearUsuarioDto newUser) {
        RespuestaUsuarioDto entity = service.createUser(newUser);

        return new ApiResponse<>(entity, 201);
    }

    @PatchMapping("{id}")
    public ApiResponse updateUser(@PathVariable Long id, @RequestBody ActualizarUsuarioDto userFields) {
        try {
            UsuarioDTO entity = service.updateUser(id, userFields);

            return new ApiResponse<>(entity, 200);
        } catch (NoSuchElementException e) {
            return new ApiResponse<String>("User not found", 404);
        } catch (IllegalArgumentException e) {
            return new ApiResponse<String>(e.getMessage(), 400);
        }
    }

    @PatchMapping("change-password")
    public ApiResponse<String> changePassword(@RequestBody ActualizarContraseniaDto userFields) {
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
private UsuarioService service;
}
