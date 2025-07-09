package com.EduTech.controller;

import com.EduTech.dto.user.UsuarioDTO;
import com.EduTech.dto.cursoDTO.ProfesorDetalleCursoDTO;
import com.EduTech.dto.user.LoginRequest;
import com.EduTech.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;

import com.EduTech.dto.user.CrearUsuarioDto;
import com.EduTech.dto.user.RespuestaUsuarioDto;
import com.EduTech.dto.user.ActualizarUsuarioDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

/** 
 * @author Franco Carrasco
 * @version 1.0
 */
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @Operation(
        summary = "Obtener todos los usuarios",
        description = "Devuelve una lista de todos los usuarios registrados en el sistema.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida correctamente"),
            @ApiResponse(responseCode = "204", description = "No hay usuarios registrados")
        }
    )
    @GetMapping()
    public ResponseEntity<List<RespuestaUsuarioDto>> getAllUsers() {
        List<RespuestaUsuarioDto> users = service.getUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @Operation(
        summary = "Crear nuevo usuario",
        description = "Crea un nuevo usuario en el sistema con los datos proporcionados.",
        responses = {
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o campos requeridos faltantes")
        }
    )
    @PostMapping
    public ResponseEntity<RespuestaUsuarioDto> createUser(@RequestBody CrearUsuarioDto newUser) {
        RespuestaUsuarioDto entity = service.createUser(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @Operation(
        summary = "Obtener usuario por ID",
        description = "Devuelve la información de un usuario específico por su ID.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
        }
    )
    @GetMapping("/id/{id}")
    public ResponseEntity<RespuestaUsuarioDto> getOneUser(
        @Parameter(description = "ID del usuario", required = true, example = "1")
        @PathVariable Long id) {
        RespuestaUsuarioDto entity = service.getOneUser(id);
        return ResponseEntity.ok(entity);
    }

    @Operation(
        summary = "Actualizar usuario",
        description = "Actualiza la información de un usuario existente. Solo se actualizan los campos proporcionados.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o validación fallida"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
        }
    )
    @PatchMapping("/id/{id}")
    public ResponseEntity<?> updateUser(
        @Parameter(description = "ID del usuario a actualizar", required = true, example = "1")
        @PathVariable Long id, 
        @RequestBody ActualizarUsuarioDto userFields) {
        try {
            RespuestaUsuarioDto entity = service.updateUser(id, userFields);
            return ResponseEntity.ok(entity);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(
        summary = "Eliminar usuario",
        description = "Elimina permanentemente un usuario del sistema por su ID.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Usuario eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
        }
    )
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(
        @Parameter(description = "ID del usuario a eliminar", required = true, example = "1")
        @PathVariable Long id) {
        try {
            String message = service.deleteUser(id);
            return ResponseEntity.ok(message);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
        summary = "Login de usuario",
        description = "Permite autenticar un usuario con email y contraseña.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Login exitoso"),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
        }
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            UsuarioDTO usuario = service.login(request.getEmail(), request.getPassword());
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }

    @Operation(
        summary = "Obtener detalle de profesor con cursos asignados",
        description = "Devuelve la información del profesor y la lista de cursos que tiene asignados.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Detalle del profesor obtenido correctamente"),
            @ApiResponse(responseCode = "404", description = "Profesor no encontrado o no es un profesor")
        }
    )
    @GetMapping("/profesor/detalle/{id}")
    public ResponseEntity<?> obtenerDetalleProfesor(
        @Parameter(description = "ID del profesor", required = true, example = "1")    
        @PathVariable Long id) {
        try {
            ProfesorDetalleCursoDTO detalle = service.obtenerDetalleProfesorConCursos(id);
            return ResponseEntity.ok(detalle);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
